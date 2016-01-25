package com.mall.admin.vo.goods;

import java.sql.Timestamp;

import com.mall.admin.util._;

/**
 * 属性类别，便于属性的选择
 *
 * @date 2015年7月14日 下午4:19:58
 * @author zhangshuai
 */
public class PropertyCategory {
/*	Field                   Type          Collation           Null    Key     Default  Extra           Privileges                       Comment       
	----------------------  ------------  ------------------  ------  ------  -------  --------------  -------------------------------  --------------
	property_category_id    int(11)       (NULL)              NO      PRI     (NULL)   auto_increment  select,insert,update,references                
	property_category_name  varchar(255)  utf8mb4_unicode_ci  YES                                      select,insert,update,references  类目名称  
	level                   tinyint(4)    (NULL)              YES             (NULL)                   select,insert,update,references  层数        
	pid                     tinyint(4)    (NULL)              YES             (NULL)                   select,insert,update,references  父节点     
	weight                  tinyint(4)    (NULL)              YES             (NULL)                   select,insert,update,references  权重        
	is_del                  tinyint(4)    (NULL)              YES             (NULL)                   select,insert,update,references                
	create_time             timestamp     (NULL)              YES             (NULL)                   select,insert,update,references                
	update_time             timestamp     (NULL)              YES             (NULL)                   select,insert,update,references                
	operator                int(11)       (NULL)              YES             0                        select,insert,update,references                
*/
	public PropertyCategory(){
		this.createTime = _.currentTime();
		this.updateTime = _.currentTime();
	}
	public static final long PROPERTY_CATEGORY_PARENT = 0L;
	private long propertyCategoryId;

	private String propertyCategoryName;

	private int level;

	private int pid;

	private int weight;

	private int isDel;

	private Timestamp createTime;

	private Timestamp updateTime;

	private long operator;

	public long getPropertyCategoryId() {
		return propertyCategoryId;
	}

	public void setPropertyCategoryId(long propertyCategoryId) {
		this.propertyCategoryId = propertyCategoryId;
	}

	public String getPropertyCategoryName() {
		return propertyCategoryName;
	}

	public void setPropertyCategoryName(String propertyCategoryName) {
		this.propertyCategoryName = propertyCategoryName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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
}