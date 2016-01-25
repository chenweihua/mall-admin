package com.mall.admin.vo.goods;

import java.sql.Timestamp;

import com.mall.admin.util._;

/**
 * 后台sku和gbm的关系，连接了后台sku和实际库存（storage_goods_stock）
 *
 * @date 2015年7月14日 下午3:46:34
 * @author zhangshuai
 */
public class BgSkuGbm {
	/*
	 * Field Type Collation Null Key Default Extra Privileges Comment
	 * ------------ ------------------- --------- ------ ------ ------- ------
	 * ------------------------------- -----------------------------------
	 * sku_gbm_id int(11) unsigned (NULL) NO PRI (NULL)
	 * select,insert,update,references Sku_gbm_id sku_id int(11) unsigned (NULL)
	 * NO (NULL) select,insert,update,references 商品id wms_goods_id int(11)
	 * unsigned (NULL) NO (NULL) select,insert,update,references 对应供应链商品id num
	 * int(11) unsigned (NULL) NO 1 select,insert,update,references
	 * 数量(1个sku对应的gbm个数) create_time timestamp (NULL) NO (NULL)
	 * select,insert,update,references 创建时间 update_time timestamp (NULL) NO
	 * (NULL) select,insert,update,references 更新时间 operator int(11) (NULL) NO 0
	 * select,insert,update,references 操作id is_del tinyint(3) unsigned (NULL) NO
	 * 0 select,insert,update,references 软删除
	 */
	private long skuGbmId;

	private long bgSkuId;

	private long wmsGoodsId;
	
	private long num;

	private Timestamp createTime;

	private Timestamp updateTime;

	private long operator;

	private int isDel;
	
	private String wmsGoodsGbm;
	private String wmsGoodsName;

	public BgSkuGbm() {
		super();
		this.createTime = _.currentTime();
		this.updateTime = _.currentTime();
		this.isDel = 0;
	}

	public long getSkuGbmId() {
		return skuGbmId;
	}

	public void setSkuGbmId(long skuGbmId) {
		this.skuGbmId = skuGbmId;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public long getWmsGoodsId() {
		return wmsGoodsId;
	}

	public void setWmsGoodsId(long wmsGoodsId) {
		this.wmsGoodsId = wmsGoodsId;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getWmsGoodsGbm() {
		return wmsGoodsGbm;
	}

	public void setWmsGoodsGbm(String wmsGoodsGbm) {
		this.wmsGoodsGbm = wmsGoodsGbm;
	}

	public String getWmsGoodsName() {
		return wmsGoodsName;
	}

	public void setWmsGoodsName(String wmsGoodsName) {
		this.wmsGoodsName = wmsGoodsName;
	}
}