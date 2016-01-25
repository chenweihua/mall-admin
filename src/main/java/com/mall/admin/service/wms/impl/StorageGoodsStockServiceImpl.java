package com.mall.admin.service.wms.impl;

import static com.mall.admin.base._.isEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.StroageConstant;
import com.mall.admin.model.dao.wms.StorageGoodsStockDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.seller.SellerService;
import com.mall.admin.service.storage.RecordTypeService;
import com.mall.admin.service.storage.StorageGoodsRecordService;
import com.mall.admin.service.wms.StorageGoodsStockService;
import com.mall.admin.service.wms.WmsGoodsService;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.seller.Seller;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

@Service
public class StorageGoodsStockServiceImpl implements StorageGoodsStockService {
	@Autowired
	StorageGoodsStockDao storageGoodsStockDao;

	@Autowired
	RecordTypeService recordTypeService;

	@Autowired
	WmsGoodsService wmsGoodsService;

	@Autowired
	StorageGoodsRecordService storageGoodsRecordService;

	@Autowired
	SellerService sellerService;

	@Override
	public List<StorageGoodsStock> getStorageGoodsStock(StorageGoodsStock storageGoods,
			PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub

		return null;
	}

	
	@Override
	public int insertOrUpdateWithStock(WmsGoods wmsGoods) {
		StorageGoodsStock storageGoodsStock = new StorageGoodsStock();
		storageGoodsStock.setWms_goods_id(wmsGoods.getWms_goods_id());
		storageGoodsStock.setStorage_id(wmsGoods.getStorageId());
		storageGoodsStock.setStorage_type(wmsGoods.getStorageType());
		storageGoodsStock.setStock(wmsGoods.getStock());
		storageGoodsStock.setCreator(wmsGoods.getOperator_id());
		storageGoodsStock.setOperator(wmsGoods.getOperator_id());
		StorageGoodsStock storageGoodsStockTemp = storageGoodsStockDao.getStorageGoodsByGoodsIdAndStorageId(
				wmsGoods.getWms_goods_id(), wmsGoods.getStorageId());
		int result = 0;
		if (storageGoodsStockTemp == null) {
			result = storageGoodsStockDao.insertWithStock(storageGoodsStock);
		} else {
			result = storageGoodsStockDao.update(storageGoodsStock);
		}
		return result;
	}


	/**
	 * 插入记录时，需要先判断是否存在，如果存在，则更新为开启状态。如果不存在，才添加该商品
	 */
	@Override
	public int insert(long wms_goods_id, long storage_id, int storage_type, long user_id) {
		StorageGoodsStock storageGoodsStock = storageGoodsStockDao.getStorageGoodsByGoodsIdAndStorageId(
				wms_goods_id, storage_id);
		int result = 0;
		if (storageGoodsStock == null) {
			storageGoodsStock = new StorageGoodsStock();
			storageGoodsStock.wms_goods_id = wms_goods_id;
			storageGoodsStock.storage_id = storage_id;
			storageGoodsStock.storage_type = storage_type;
			storageGoodsStock.creator = user_id;
			storageGoodsStock.operator = user_id;
			result = storageGoodsStockDao.insert(storageGoodsStock);
		} else {
			result = storageGoodsStockDao.updateWmsGoodsInStorageStatus(wms_goods_id, storage_id, 0,
					user_id);
		}
		return result;
	}

	@Override
	public int update(StorageGoodsStock storageGoods) {
		return storageGoodsStockDao.update(storageGoods);
	}

	@Override
	public StorageGoodsStock queryById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StorageGoodsStock> getByQuery(long wms_goods_id, long storage_Id, int storagetype,
			PaginationInfo paginationInfo) {
		return storageGoodsStockDao.getStorageGoodsByQuery(storage_Id, wms_goods_id, storagetype);
	}

	@Override
	public int updateGoodsInStorageStatus(long wms_goods_id, long storage_id, int is_del, long user_id) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.updateWmsGoodsInStorageStatus(wms_goods_id, storage_id, is_del, user_id);
	}

	@Override
	public String importGoods(List<String[]> data, long operstorid, long storageId) {
		StringBuffer message = new StringBuffer("");
		List<StorageGoodsStock> storageGoodsList = new ArrayList<StorageGoodsStock>();
		List<StorageGoodsRecord> goodsRecordList = new ArrayList<StorageGoodsRecord>();
		Map<String, Long> sellerMap = new HashMap<String, Long>();
		List<Seller> sellerList = sellerService.getAllSeller();
		for (Seller seller : sellerList) {
			sellerMap.put(seller.sellerName, seller.sellerId);
		}
		Map<Long,Long> gbmStock = new ConcurrentHashMap<>();
		for (int i = 0; i < data.size(); i++) {
			String[] rowData = data.get(i);
			if (rowData.length != 7) {
				message.append("第" + (i + 2) + "行记录的数据不正确~\n");
			}
			if (isEmpty(rowData[1])) {
				message.append("第" + (i + 2) + "行记录的商品名称为空~\n");
			}
			if (isEmpty(rowData[2])) {
				message.append("第" + (i + 2) + "行记录的商品条码为空~\n");
			}
			if (isEmpty(rowData[3])) {
				message.append("第" + (i + 2) + "行记录的数量为空~\n");
			}
			if (isEmpty(rowData[4])) {
				message.append("第" + (i + 2) + "行记录的单价为空~\n");
			}
			if (isEmpty(rowData[5])) {
				message.append("第" + (i + 2) + "行记录的供应商为空~\n");
			}
			if (isEmpty(rowData[6])) {
				message.append("第" + (i + 2) + "行记录的入库类型为空~\n");
			}
			if (!rowData[3].matches("[1-9]|[1-9][0-9]{0,}")) {
				message.append("第" + (i + 2) + "行记录的数量格式错误，只能是数字~\n");
			}
			if (rowData[4] != null && !rowData[4].matches("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$")) {
				message.append("第" + (i + 2) + "行记录的单价格式错误~\n");
			}
			// if(!("采购".equals(rowData[6].trim())||"回收".equals(rowData[6].trim()))){
			// return "第"+(i+2)+"行记录的入库类型错误，只能是【采购】或【回收】~";
			// }
			if ("采购入库".equals(rowData[6].trim())) {
				if (isEmpty(rowData[0])) {
					message.append("第" + (i + 2) + "行记录的单号为空，采购入库必须输入单号~\n");
				}
			}
			if (sellerMap.get(rowData[5]) == null) {
				message.append("第" + (i + 2) + "行记录的供应商未找到\n");
			}
			// 回收不再作是否需要单号判断
			// else if("回收".equals(rowData[6].trim())){
			// if(!isEmpty(rowData[0])){
			// return "第"+(i+2)+"行记录的单号不为空，回收入库不能输入单号~";
			// }
			// }
			Long sellerId = sellerMap.get(rowData[5].trim());
			if (sellerId == null || sellerId == 0) {
				message.append("第" + (i + 2) + "行记录的代理商不存在~\n");
				continue;
			}
			Storage temp = StroageConstant.getStorageById(storageId);
			List<WmsGoods> goodsList = null;
			if(temp.getStorageType() == Storage.VM_STORAGE){
				goodsList = wmsGoodsService.getByGbmAndStorageId(rowData[2].trim(),storageId);
			}else{
				goodsList = wmsGoodsService.getByGbmAndStorageId(rowData[2].trim(),0L);
			}
			WmsGoods goods = null;
			if (goodsList == null || goodsList.size() == 0) {
				message.append("第" + (i + 2) + "行记录的商品条码没有对应的商品~\n");
				continue;
			}else if(goodsList.size() == 1){
				goods = goodsList.get(0);
			}else if(goodsList.size() > 1){
				message.append("第" + (i + 2) + "行记录的商品条码对应多条商品~\n");
				continue;
			}
			StorageGoodsStock storageGoodsStock = getByGoodsIdAndStorageId(goods.getWms_goods_id(),
					storageId);
			if (storageGoodsStock == null) {
				message.append("该库中不存在第" + (i + 2) + "行记录的商品信息~\n");
				continue;
			}
			//将数据放入map中
			String price = rowData[4];
			/** 获取调整类型,默认flag为1 */
			RecordType type = new RecordType();
			type = recordTypeService.getByName(rowData[6].trim());
			if (type == null) {
				return "第" + (i + 2) + "行记录的类型不存在~";
			}
			StorageGoodsStock storagegoods = new StorageGoodsStock();
			storagegoods.wms_goods_id = goods.wms_goods_id;
			storagegoods.storage_id = storageId;
			storagegoods.stock = NumberUtils.toLong(rowData[3], 0) * type.flag;
			if(gbmStock.containsKey(goods.getWms_goods_id())){
				gbmStock.put(goods.getWms_goods_id(),gbmStock.get(goods.getWms_goods_id()) + storagegoods.getStock());
			}else{
				gbmStock.put(goods.getWms_goods_id(), storageGoodsStock.getStock() + storagegoods.getStock());
			}
			if (gbmStock.get(goods.getWms_goods_id()) < 0) {
				message.append("第" + (i + 2) + "行记录的商品库存不能减为负~\n");
			}

			StorageGoodsRecord record = new StorageGoodsRecord();
			record.storageId = storageId;
			record.sellerId = sellerId;
			record.goodsId = goods.wms_goods_id;
			record.recordcode = rowData[0] == null ? "" : rowData[0];
			record.recordtype = type.id;// "采购".equals(rowData[6].trim())?1:2;
			record.operator = operstorid;
			record.creator = operstorid;
			record.num =  NumberUtils.toInt(rowData[3], 0);
			record.price = MoneyUtils.yuan2Fen(NumberUtils.toDouble(price, 0));
			goodsRecordList.add(record);
			storageGoodsList.add(storagegoods);
		}
		if (message.toString().trim().length() > 0) {
			return message.toString();
		}
		int flag = 1;
		for (int i = 0; i < goodsRecordList.size(); i++) {
			flag++;
			// 添加入库记录
			StorageGoodsRecord record = goodsRecordList.get(i);
			storageGoodsRecordService.insert(record);
			// 更新库存信息
			StorageGoodsStock storageGoods = storageGoodsList.get(i);
			StorageGoodsStock stockList = getByGoodsId(storageGoods.wms_goods_id, storageGoods.storage_id);
			if (stockList != null) {
				StorageGoodsStock temp = stockList;
				storageGoods.storage_goods_id = temp.storage_goods_id;
				LogConstant.mallLog.info(new Date() + "[update stock:import]wmsGoodsId:"+storageGoods.wms_goods_id+"|storageId:"+storageGoods.storage_id+"|originStock:"+temp.stock+"|addStock:"+storageGoods.stock+"|totalStock:"+storageGoods.stock + temp.stock);
				storageGoods.stock += temp.stock;
				update(storageGoods);
			} else {
				insert(storageGoods);
			}
		}
		return "0";
	}

	@Override
	public List<StorageGoodsStock> getStorageGoodsStockByGoodsId(long wms_goods_id) {
		return storageGoodsStockDao.getStorageGoodsByGoodsId(wms_goods_id);
	}

	@Override
	public int insert(StorageGoodsStock storageGoodsStock) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.insert(storageGoodsStock);
	}

	@Override
	public StorageGoodsStock getByGoodsId(long wms_goods_id, long storageId) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.getStorageGoodsByGoodsIdAndStorageId(wms_goods_id, storageId);
	}

	@Override
	public List<Long> getStorageIdByWmsGoodsId(long wmsGoodsId, int storageType) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.getStorageByWmsGoodsId(wmsGoodsId, storageType);
	}
	
	@Override
	public List<Long> getCollegeIdsBybgGoodsIdWithRdc(long wmsGoodsId) {
		return storageGoodsStockDao.getCollegeIdsBybgGoodsIdWithRdc(wmsGoodsId);
	}
	
	@Override
	public List<Long> getCollegeIdsBybgGoodsIdWithLdc(long wmsGoodsId) {
		return storageGoodsStockDao.getCollegeIdsBybgGoodsIdWithLdc(wmsGoodsId);
	}

	@Override
	public List<Long> getCollegeIdsBybgGoodsIdWithThirddc(long wmsGoodsId) {
		return storageGoodsStockDao.getCollegeIdsBybgGoodsIdWithThirddc(wmsGoodsId);
	}

	@Override
	public Pair<Long, PaginationList<StorageGoodsStock>> getPageStorageGoodsStock(PaginationInfo paginationInfo,
			Map paramMap) {
		Pair<Long, PaginationList<StorageGoodsStock>> p = storageGoodsStockDao.getPageStorageGoodsStock(
				paginationInfo, paramMap);
		return p;
	}

	@Override
	public StorageGoodsStock getStorageGoodsStockByStorageGoodsId(long storagegoodsid) {
		return storageGoodsStockDao.getStorageGoodsStockByStorageGoodsId(storagegoodsid);
	}

	@Override
	public int updateStock(long storageGoodsStockId, long stock) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.updateStock(storageGoodsStockId, stock);
	}

	@Override
	public int setStorageStockIsDel(long storageId) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.setStorageStockIsDel(storageId);
	}

	@Override
	public StorageGoodsStock getByGoodsIdAndStorageId(long wms_goods_id, long storageId) {
		// TODO Auto-generated method stub
		return storageGoodsStockDao.getByGoodsIdAndStorageId(wms_goods_id, storageId);
	}

}
