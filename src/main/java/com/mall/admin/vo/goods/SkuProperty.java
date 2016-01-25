package com.mall.admin.vo.goods;

/**
 * sku属性集，每个sku对应具体的属性
 *
 * @date 2015年7月14日 下午4:22:10
 * @author zhangshuai
 */
public class SkuProperty {
/*	Field              Type     Collation  Null    Key     Default  Extra   Privileges                       Comment            
	-----------------  -------  ---------  ------  ------  -------  ------  -------------------------------  -------------------
	sku_property_id    int(11)  (NULL)     NO      PRI     (NULL)           select,insert,update,references  商品的属性id  
	bg_sku_id          int(11)  (NULL)     NO              0                select,insert,update,references  Sku id             
	property_value_id  int(11)  (NULL)     NO              0                select,insert,update,references  属性值id        
*/
	
	private long skuPropertyId;

	private long bgGoodsId;

	private long bgSkuId;

	private long propertyValueId;
	
	private long propertyNameId;

	public long getSkuPropertyId() {
		return skuPropertyId;
	}

	public void setSkuPropertyId(long skuPropertyId) {
		this.skuPropertyId = skuPropertyId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public long getPropertyValueId() {
		return propertyValueId;
	}

	public void setPropertyValueId(long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}

	public long getPropertyNameId() {
		return propertyNameId;
	}

	public void setPropertyNameId(long propertyNameId) {
		this.propertyNameId = propertyNameId;
	}
	
}