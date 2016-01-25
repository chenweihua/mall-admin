package com.mall.admin.vo.goods;

import java.util.List;

/**
 * 属性名
 *
 * @date 2015年7月14日 下午4:20:34
 * @author zhangshuai
 */
public class PropertyName {
/*	Field                 Type          Collation           Null    Key     Default  Extra           Privileges                       Comment                                      
	--------------------  ------------  ------------------  ------  ------  -------  --------------  -------------------------------  ---------------------------------------------
	property_name_id      int(11)       (NULL)              NO      PRI     (NULL)   auto_increment  select,insert,update,references                                               
	property_name         varchar(255)  utf8mb4_unicode_ci  NO                                       select,insert,update,references  属性名                                    
	property_category_id  int(11)       (NULL)              NO              0                        select,insert,update,references  属性归属属性类目                     
	show_order                tinyint(4)    (NULL)              NO              0                        select,insert,update,references  是否sku属性 0：不是 1：是           
	need_pic              tinyint(4)    (NULL)              NO              0                        select,insert,update,references  是否需要图片 0：不需要 1：需要  
	is_del                tinyint(4)    (NULL)              NO              0                        select,insert,update,references                                               
*/
	
	private long propertyNameId;

	private String propertyName;

	private long propertyCategoryId;

	private long showOrder;

	private int needPic;

	private int isDel;
	
	private int index;
	
	private List<PropertyValue> propertyVauleList;
	//每5个一组
	private Object[]  propertyVauleListArray;

	public long getPropertyNameId() {
		return propertyNameId;
	}

	public void setPropertyNameId(long propertyNameId) {
		this.propertyNameId = propertyNameId;
	}

	public String getPropertyName(){
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public long getPropertyCategoryId() {
		return propertyCategoryId;
	}

	public void setPropertyCategoryId(long propertyCategoryId) {
		this.propertyCategoryId = propertyCategoryId;
	}

	public long getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(long showOrder) {
		this.showOrder = showOrder;
	}

	public int getNeedPic() {
		return needPic;
	}

	public void setNeedPic(int needPic) {
		this.needPic = needPic;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<PropertyValue> getPropertyVauleList() {
		return propertyVauleList;
	}

	public void setPropertyVauleList(List<PropertyValue> propertyVauleList) {
		this.propertyVauleList = propertyVauleList;
	}

	public Object[] getPropertyVauleListArray() {
		return propertyVauleListArray;
	}

	public void setPropertyVauleListArray(Object[] propertyVauleListArray) {
		this.propertyVauleListArray = propertyVauleListArray;
	}
}