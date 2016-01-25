package com.mall.admin.vo.goods;

/**
 * 属性值
 *
 * @date 2015年7月14日 下午4:20:53
 * @author zhangshuai
 */
public class PropertyValue {
/*	Field              Type          Collation           Null    Key     Default  Extra           Privileges                       Comment            
	-----------------  ------------  ------------------  ------  ------  -------  --------------  -------------------------------  -------------------
	property_value_id  int(11)       (NULL)              NO      PRI     (NULL)   auto_increment  select,insert,update,references                     
	property_value     varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  属性值          
	property_name_id   int(11)       (NULL)              NO              (NULL)                   select,insert,update,references  属性属性名id  
	is_del             tinyint(4)    (NULL)              NO              0                        select,insert,update,references                     
*/
	
	private long propertyValueId;

	private String propertyValue;

	private long propertyNameId;
	
	private String imageUrl;
	
	private int showOrder;

	private int isDel;

	public long getPropertyValueId() {
		return propertyValueId;
	}

	public void setPropertyValueId(long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public long getPropertyNameId() {
		return propertyNameId;
	}

	public void setPropertyNameId(long propertyNameId) {
		this.propertyNameId = propertyNameId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
}