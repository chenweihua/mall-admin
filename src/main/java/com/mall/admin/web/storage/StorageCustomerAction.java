package com.mall.admin.web.storage;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.ApplyPayUtil;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.seller.SellerService;
import com.mall.admin.service.storage.ApplyPayLogService;
import com.mall.admin.service.storage.StorageGoodsRecordService;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.seller.Seller;
import com.mall.admin.vo.storage.ApplyPayLog;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.storage.ps.ApplyPayDetailBean;
import com.mall.admin.vo.storage.ps.ApplyPayInfoBean;
import com.mall.admin.vo.storage.ps.ApplyPayInfoListBean;
import com.mall.admin.vo.storage.ps.ApplyPayRecordDetailBean;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.WmsGoods;

@Controller
@RequestMapping({ "/admin/goods", "/selleradmin/goods" })
public class StorageCustomerAction extends BaseController {

	@Autowired
	private ApplyPayLogService applyPayLogService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private StorageGoodsRecordService storageGoodsRecordService;
	@Autowired
	private StorageGoodsStockService storageGoodsStockService;
	@Autowired
	private WmsGoodsService wmsGoodsService;

	/**
	 * 确认申请的支付信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("applycheck")
	@ResponseBody
	public Object applycheck(HttpServletRequest request, HttpServletResponse response, @RequestParam long storageid) {

		User loginInfo = (User) request.getAttribute("user");

		long sumMoney = 0;
		String sellerName = "未知";
		long sellerId = 0;
		int num = 0;

		try {
			checkState(loginInfo != null, "请先登录");
			checkArgument(storageid > 0, "参数错误，storageid[%s]", storageid);

			List<StorageGoodsRecord> goodsRecordList = storageGoodsRecordService
					.getGoodsRecordByLockUserId(loginInfo.user_id, storageid);

			if (goodsRecordList != null && goodsRecordList.size() > 0) {
				num = goodsRecordList.size();
				for (StorageGoodsRecord goodsRecord : goodsRecordList) {
					sumMoney += goodsRecord.num * goodsRecord.price;
					sellerId = goodsRecord.sellerId;
				}
			}
			Seller seller = sellerService.getSellerById(sellerId);// Seller.getById(sellerId);
			if (seller != null) {
				sellerName = seller.getSellerName();
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("统计结算池信息失败", e);
			buildJson(1, "统计结算池信息失败，" + e.getMessage());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("summoney", sumMoney);
		map.put("sellername", sellerName);
		map.put("num", num);

		return buildJson(0, "添加成功~", map);
	}

	/**
	 * 申请结算
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("applypay")
	@ResponseBody
	public Object applyPay(HttpServletRequest request, HttpServletResponse response, @RequestParam long storageid) {

		User loginInfo = (User) request.getAttribute("user");
		String applyPayCode = ApplyPayUtil.applyCode();

		long sumMoney = 0;
		long sellerId = 0;
		int num = 0;
		try {
			List<StorageGoodsRecord> goodsRecordList = storageGoodsRecordService
					.getGoodsRecordByLockUserId(loginInfo.user_id, storageid);
			checkState(goodsRecordList != null && goodsRecordList.size() > 0,
					"统计结算池信息失败，结算池中没有数据，请刷新页面后重新提交~");

			if (goodsRecordList != null && goodsRecordList.size() > 0) {
				num = goodsRecordList.size();
				for (StorageGoodsRecord goodsRecord : goodsRecordList) {
					sumMoney += goodsRecord.num * goodsRecord.price;
					sellerId = goodsRecord.sellerId;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogConstant.mallLog.error("统计结算池信息失败", e);
			buildJson(1, "统计结算池信息失败，错误代码：" + e.getMessage());

		}

		ApplyPayLog payInfo = new ApplyPayLog();
		payInfo.setApplyCode(applyPayCode);// applycode=applyPayCode;
		payInfo.setStorageId(storageid); // storageid=loginInfo.storage.id;
		payInfo.setSellerId(sellerId);// sellerid=sellerId;
		payInfo.setSumMoney(sumMoney);// summoney=sumMoney;
		payInfo.setRecordCount(num);// recordcount=num;
		payInfo.setApplyUserId(loginInfo.getUser_id()); // applyuserid=loginInfo.user.id;
		payInfo.setCreateTime(new Date());

		try {
			int count = storageGoodsRecordService.setRecordPayingStatus(loginInfo.getUser_id(),
					applyPayCode, storageid);
			checkState(count > 0, "更新入库记录信息失败~");
			applyPayLogService.insert(payInfo);
			// payInfo.insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogConstant.mallLog.info("添加申请记录信息失败，需要回退入库记录更新的信息，申请支付编码:" + applyPayCode);
			return buildJson(1, "添加申请记录失败，请管理员整理入库记录更新记录，申请支付编码是:" + applyPayCode + "~");
		}
		return buildJson(0, "添加成功，结算单号是:" + applyPayCode);
	}

	// ============ 提供给磐石的接口 ============

	/**
	 * 磐石系统通过该接口获得申请支付订单列表的信息。
	 * 
	 * @param request
	 * @param response
	 * @throws SQLException
	 */
	@RequestMapping("applypaylist")
	@ResponseBody
	public Object getApplyPayInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String statusStr = request.getParameter("status");
		String applyPayCode = request.getParameter("applycode");
		String startStr = request.getParameter("start");
		String numStr = request.getParameter("num");
		String beginStr = request.getParameter("begintime");
		String endStr = request.getParameter("endtime");
		String apply_User = request.getParameter("applyUser");

		ApplyPayInfoListBean bean = new ApplyPayInfoListBean();
		try {
			int status = _.toInt(statusStr, -1);
			int start = _.toInt(startStr);
			int limit = _.toInt(numStr);
			Date begin = _.toDate(beginStr);
			Date end = _.toDate(endStr);

			List<ApplyPayInfoBean> infoList = new ArrayList<ApplyPayInfoBean>();
			Pair<Integer, List<ApplyPayLog>> pair = applyPayLogService.getApplyPayLogList(status,
					applyPayCode, begin, end, apply_User, start, limit); // ApplyPayInfo.getApplyPayInfoBySearch(query,
												// start,
												// num);

			List<Storage> storageList = storageService.getAllStorage(); // Storage.getAllStorage();
			List<Seller> sellerList = sellerService.getAllSeller(); // Seller.getAll();

			int count = 0;

			List<ApplyPayLog> applyPayLogs = pair.getRight();
			bean.sum = pair.getLeft();

			if (!_.isEmpty(applyPayLogs)) {
				for (ApplyPayLog info : applyPayLogs) {

					ApplyPayInfoBean record = new ApplyPayInfoBean();
					// JsonObject applyInfoJson = new
					// JsonObject();
					Map<String, Object> map = new HashMap<String, Object>();
					boolean storageExit = false;
					boolean sellerExit = false;

					for (Storage storage : storageList) {
						if (storage.getStorageId() == info.getStorageId()) {
							// applyInfoJson.addProperty("storageName",
							// storage.name);
							record.storageName = storage.getStorageName();
							storageExit = true;
						}
					}
					for (Seller seller : sellerList) {
						if (seller.getSellerId() == info.getSellerId()) {
							// applyInfoJson.addProperty("sellerName",
							// seller.name);
							record.sellerName = seller.getSellerName();
							sellerExit = true;
						}
					}
					if (!storageExit) {
						// applyInfoJson.addProperty("storageName",
						// "未知");
						record.storageName = "未知";
					}
					if (!sellerExit) {
						// applyInfoJson.addProperty("sellerName",
						// "未知");
						record.sellerName = "未知";
					}
					String applyUser = info.getApplyUserName();// info.applyuserid+"";
					// AdminUser user =
					// AdminUser.getAllUserById(info.applyuserid);
					// if(user!=null){
					// applyUser=user.userName;
					// }
					// applyInfoJson.addProperty("applyCode",
					// info.applycode);
					// applyInfoJson.addProperty("status",
					// info.status==1?"已结算":"未结算");
					// applyInfoJson.addProperty("applyTime",
					// format.format(info.createtime));
					// applyInfoJson.addProperty("payTime",
					// format.format(info.paytime));
					// applyInfoJson.addProperty("applyUser",
					// applyUser);
					// applyInfoJson.addProperty("payUserId",
					// info.payuserid);

					record.applyCode = info.getApplyCode();
					record.status = info.getStatus() == 1 ? "已结算" : "未结算";
					record.applyTime = _.formatDate(info.getCreateTime());// format.format(info.createtime);
					if (info.getPayTime() == null)
						record.payTime = "未支付";
					else
						record.payTime = _.formatDate(info.getPayTime());// format.format(info.paytime);
					record.applyUser = applyUser;
					record.payUserId = info.getPayUserId() == null ? 0 : info.getPayUserId();
					record.accountmoney = info.getAccountMoney() == null ? 0 : info
							.getAccountMoney();// accountmoney;
					record.summoney = info.getSumMoney() == null ? 0 : info.getSumMoney();// summoney;
					if (info.getStatus() == 0)
						record.adjustmoney = 0;
					else
						record.adjustmoney = info.getAccountMoney() - info.getSumMoney(); // info.accountmoney-info.summoney;//调整金额

					// infoMap.put("", value)
					// applyListJson.add("payinfo"+count,
					// applyInfoJson);
					infoList.add(record);
					count++;

				}
				bean.infoList = infoList;
				bean.count = count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// allJson.addProperty("sum", 0);
			bean.sum = 0;
		}
		return bean;
	}

	/**
	 * 磐石系统通过该接口获得申请支付订单的信息。
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("applypaydetail")
	@ResponseBody
	public Object getApplyPayInfoDetail(HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String applyPayCode = request.getParameter("applycode");
		// PayInfoQuery query = new PayInfoQuery();
		ApplyPayDetailBean bean = new ApplyPayDetailBean();
		try {

			ApplyPayLog applyPayLog = applyPayLogService.getApplyPayLogByCode(applyPayCode);// getApplyPayLogList(-1,
													// applyPayCode,
													// null,
													// null,
													// null,
													// -1,
													// -1);
													// //
													// ApplyPayInfo.getApplyPayInfoBySearch(query,
													// 0,
													// 0);

			if (applyPayLog == null) {
				bean.count = 0;
			} else {

				ApplyPayLog info = applyPayLog;// pair.getRight().get(0);
				bean.count = 1;
				bean.applyCode = info.getApplyCode();// info.applycode;
				bean.status = info.getStatus(); // info.status;
				bean.statusStr = info.getStatus() == 1 ? "已结算" : "未结算";
				bean.applyTime = _.formatDate(info.getCreateTime());// format.format(info.createtime);
				if (info.getPayTime() == null)
					bean.payTime = "未支付";
				else
					bean.payTime = _.formatDate(info.getPayTime());// format.format(info.paytime);
				bean.remark = info.getRemark();

				// String applyUser = info.applyuserid+"";
				// AdminUser user =
				// AdminUser.getAllUserById(info.applyuserid);
				// if(user!=null){
				// applyUser=user.userName;
				// }
				bean.applyUser = info.getApplyUserName();// applyUser;

				Storage storage = storageService.getStorageById(info.getStorageId());// Storage.get(info.storageid);
				if (storage != null) {
					bean.storageName = storage.getStorageName();
				} else {
					bean.storageName = "未知";
				}
				Seller seller = sellerService.getSellerById(info.getSellerId());// Seller.getById(info.sellerid);
				if (seller != null) {
					bean.sellerName = seller.getSellerName();
				} else {
					bean.sellerName = "未知";
				}

				List<StorageGoodsRecord> recordList = storageGoodsRecordService
						.getStorageGoodsRecordsByApplyPayCode(applyPayCode);// GoodsRecord.getGoodsRecordByApplyPayCode(info.applycode);
				if (recordList == null) {
					bean.recordNum = 0;
				} else {
					bean.recordNum = recordList.size();
					List<ApplyPayRecordDetailBean> recordDetailList = new ArrayList<ApplyPayRecordDetailBean>();
					for (StorageGoodsRecord record : recordList) {
						ApplyPayRecordDetailBean detailBean = new ApplyPayRecordDetailBean();
						detailBean.id = record.id;
						WmsGoods wmsGoods = wmsGoodsService.getWmsGoodsByStorageGoodId(record
								.getGoodsId());// Goods.queryGoodsById(record.goodsId);
						if (wmsGoods == null) {
							detailBean.goodsName = "未知";
							detailBean.goodsGbm = "未知";
							detailBean.goodsUnit = "未知";
						} else {
							// Goods goods =
							// goodsList.get(0);
							detailBean.goodsName = wmsGoods.getWms_goods_name();// .name;
							detailBean.goodsGbm = wmsGoods.getWms_goods_gbm();// goods.gbm;
							detailBean.goodsUnit = wmsGoods.getUnit();// goods.unit==null?"":goods.unit;
						}
						// 支付单价
						detailBean.recordPrice = record.getAccount_price();// record.accountPrice;
						// 支付数量
						detailBean.recordNum = record.getAccount_num();// record.accountNum;
						// 应付金额
						detailBean.recordMoney = record.getAccount_price()
								* record.getAccount_num();// record.accountPrice*record.accountNum;
						// 实付金额
						detailBean.recordRealMoney = record.getAccount_money();// record.accountMoney;
						// 调整金额
						// if (bean.status == 0)
						// detailBean.adjustMoney = 0;
						// else
						detailBean.adjustMoney = detailBean.recordRealMoney
								- detailBean.recordMoney;

						recordDetailList.add(detailBean);
					}
					bean.recordDetailList = recordDetailList;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bean.count = 0;
		}

		return bean;
	}

	/**
	 * 会计修改支付金额
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("modifyrecordmoney")
	@ResponseBody
	public Object modifyRecordMoney(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 被修改的记录
			String recordId = request.getParameter("recordId");
			// 会计修改后的金额
			String accountMoneyStr = request.getParameter("money");
			String remark = request.getParameter("remark");

			double money = _.toDouble(accountMoneyStr, 0);
			long recordIdLong = _.toLong(recordId, 0);

			checkArgument(money > 0);
			checkArgument(recordIdLong > 0);

			long accountMoney = MoneyUtils.yuan2Fen(Double.parseDouble(accountMoneyStr));
			boolean updateResult = storageGoodsRecordService.updateAccountMoney(recordIdLong, accountMoney,
					remark);
			if (updateResult) {
				return buildJson(0, "修改成功~");
			} else {
				return buildJson(1, "修改失败~");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return buildJson(1, "修改失败~");
	}

	/**
	 * 确认支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("confirmpay")
	@ResponseBody
	public Object confirmPay(HttpServletRequest request, HttpServletResponse response) {

		String applyPayCode = request.getParameter("applypaycode");
		String remark = request.getParameter("remark");
		String payUserId = request.getParameter("payuserid");
		String oaCode = request.getParameter("oacode");

		try {

			long payUserIdLong = _.toLong(payUserId, -1L);
			checkArgument(payUserIdLong > 0);

			ApplyPayLog payInfo = applyPayLogService.getApplyPayLogByCode(applyPayCode);// ApplyPayInfo.getApplyPayInfoByCode(applyPayCode);
			if (payInfo == null) {
				return buildJson(1, "没有找到对应的申请信息~");
			}

			List<StorageGoodsRecord> recordList = storageGoodsRecordService
					.getStorageGoodsRecordsByApplyPayCode(applyPayCode);// GoodsRecord.getGoodsRecordByApplyPayCode(applyPayCode);
			if (recordList == null) {
				return buildJson(1, "支付失败，入库记录为空~");
			}
			long sumAccountMoney = 0;
			for (StorageGoodsRecord record : recordList) {
				sumAccountMoney += record.getAccount_money();// accountMoney;
			}
			payInfo.setAccountMoney(sumAccountMoney); // accountmoney=sumAccountMoney;
			payInfo.setPayUserId(payUserIdLong); // payuserid =
								// Long.parseLong(payUserId);
			payInfo.setRemark(remark); // remark=remark;
			payInfo.setOaCode(oaCode);
			int count = applyPayLogService.update2ConfirmPay(payInfo);

			if (count > 0) {

				count = storageGoodsRecordService.updateRecordPayedStatus(applyPayCode);

				if (count > 0) {
					return buildJson(0, "支付成功~");
				} else {
					return buildJson(1, "支付失败，更新入库信息失败~");
				}
			} else {
				return buildJson(1, "支付失败，更新支付信息失败~");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(1, "支付失败，错误信息：" + e.getMessage());
		}
	}

}
