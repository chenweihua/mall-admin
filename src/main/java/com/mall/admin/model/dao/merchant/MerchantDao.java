package com.mall.admin.model.dao.merchant;

import java.util.List;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.merchant.MerChantSeq;
import com.mall.admin.vo.merchant.Merchant;

public interface MerchantDao {

	public List<Merchant> getMerchantList(String merchantName, String shopOwner, int status,
			PaginationInfo paginationInfo);

	/**
	 * 添加门店信息
	 * 
	 * @param merchant
	 * @return
	 */
	public int insert(Merchant merchant);

	/**
	 * 更新门店信息
	 * 
	 * @param merchant
	 * @return
	 */
	public int update(Merchant merchant);

	/**
	 * 修改门店的使用状态
	 * 
	 * @param status
	 *                1：使用中；2：停用中
	 * @param merchantId
	 * @return
	 */
	public int setStatus(int status, long merchantId);

	/**
	 * 根据门店id，获得门店信息
	 * 
	 * @return
	 */
	public Merchant getMerchantById(long merchantId);

	/**
	 * 获得最大的序列值
	 * 
	 * @param seqId
	 * @return
	 */
	public MerChantSeq getMaxSeq(long seqId);

	/**
	 * 更新序列的最大值
	 * 
	 * @param seaId
	 * @return
	 */
	public int updateMaxSeq(long seqId);

	/**
	 * 根据用户id获取商户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Merchant getMerchantByUserId(long userId);

	/**
	 * 根据商户名称查询商户信息
	 * 
	 * @param merchantName
	 * @return
	 */
	public Merchant getMerchantByName(String merchantName);
}
