package com.mall.admin.vo.goods;

import java.sql.Timestamp;

/**
 * 后台goods模板
 * 
 * @date 2015年7月14日 下午3:43:42
 * @author zhangshuai
 */
public class BgGoods {
	
/*	Field             Type          Collation           Null    Key     Default  Extra           Privileges                       Comment                                                           
	----------------  ------------  ------------------  ------  ------  -------  --------------  -------------------------------  ------------------------------------------------------------------
	bg_goods_id       int(11)       (NULL)              NO      PRI     (NULL)   auto_increment  select,insert,update,references                                                                    
	bg_goods_name     varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  商品名称                                                      
	bg_goods_subname  varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  商品二级名称                                                
	description       varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  描述                                                            
	remark            varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  备注                                                            
	category_id       int(11)       (NULL)              NO              (NULL)                   select,insert,update,references  类目id                                                          
	unit              varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  单位                                                            
	image_url         varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  商品图片                                                      
	weight            int(11)       (NULL)              NO              (NULL)                   select,insert,update,references  权重                                                            
	max_num           int(11)       (NULL)              NO              (NULL)                   select,insert,update,references  限购数量                                                      
	goods_type        tinyint(4)    (NULL)              NO              1                        select,insert,update,references  商品类型:1、单一；2：组合；3、聚合                 
	goods_status      tinyint(4)    (NULL)              NO              1                        select,insert,update,references  商品状态：1、待售；2、上架；3、售罄；4、下架  
	is_del            tinyint(4)    (NULL)              NO              0                        select,insert,update,references                                                                    
	create_time       timestamp     (NULL)              NO              (NULL)                   select,insert,update,references                                                                    
	update_time       timestamp     (NULL)              NO              (NULL)                   select,insert,update,references                                                                    
	operator          int(11)       (NULL)              NO              0                        select,insert,update,references                                                                    
*/
	private long bgGoodsId;

	private String bgGoodsName;

	private String bgGoodsSubname;

	private String description;

	private String remark;

	private long categoryId;
	
	private long propertyCategoryId;

	private String unit;

	private String imageUrl;
	/**
	 * 商品详情页图片
	 */
	private String detailImageUrl;

	private long weight;

	private long maxNum;

	private int goodsType;
	public static final int GOODS_TYPE_SINGLE = 1;
	public static final int GOODS_TYPE_GROUP = 2;
	public static final int GOODS_TYPE_MULTI = 3;

	private int goodsStatus;

	private int isDel;

	private Timestamp createTime;

	private Timestamp updateTime;

	private long operator;
	
	private String saleSpec;
	
	private String originPlace;

	/**
	 * 产品包装
	 */
	private String packageSpec;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 保质期
	 */
	private String shelfLife;
	
	private long storageId;

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public String getBgGoodsName() {
		return bgGoodsName;
	}

	public void setBgGoodsName(String bgGoodsName) {
		this.bgGoodsName = bgGoodsName;
	}

	public String getBgGoodsSubname() {
		return bgGoodsSubname;
	}

	public void setBgGoodsSubname(String bgGoodsSubname) {
		this.bgGoodsSubname = bgGoodsSubname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getPropertyCategoryId() {
		return propertyCategoryId;
	}

	public void setPropertyCategoryId(long propertyCategoryId) {
		this.propertyCategoryId = propertyCategoryId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public long getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(long maxNum) {
		this.maxNum = maxNum;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
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

	public String getSaleSpec() {
		return saleSpec;
	}

	public void setSaleSpec(String saleSpec) {
		this.saleSpec = saleSpec;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getPackageSpec() {
		return packageSpec;
	}

	public void setPackageSpec(String packageSpec) {
		this.packageSpec = packageSpec;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public String getDetailImageUrl() {
		return detailImageUrl;
	}

	public void setDetailImageUrl(String detailImageUrl) {
		this.detailImageUrl = detailImageUrl;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}

}