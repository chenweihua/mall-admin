package com.mall.admin.constant;

public class Constants {
	public static final String COOKIE_USER_TOKEN = "_xiaomai_admin_userToken";

	public static final String COOKIE_USER_ID = "_xiaomai_admin_userId_";

	public static final String AUTHENTICATE_KEY = "AAA25CE9DAD833D8B7BB6463A5668133";

	/** 获得退款信息 */
	public static final String PAY_REFUND_INFO = "http://pay.test.imxiaomai.com/refund/malladmin/getrefundinfo";// Config.of("pay_refund_info",
	// "http://pay.test.imxiaomai.com/refund/malladmin/getrefundinfo").get();
	public static final String PAY_USER = "refund";// Config.of("pay_user",
	// "refund").get();
	public static final String PAY_TOKEN = "40c85ef86dbc6c90";// Config.of("pay_token",
	// "40c85ef86dbc6c90").get();

	public static final String CUSTOMER_KEY = "12345678abcdefgh12345678abcdefgh";// Config.of("customer_key",
	// "12345678abcdefgh12345678abcdefgh").get();
	public static final String CUSTOMER_WARNING = "1000";// Config.of("customer_warningnum",
	// "1000").get();

	public static final String MALLADMIN_USER = "user";
	public static final String USER_MENU = "menulist";

	public static final String ICON_RDC = "/img/ztree/rdc.png";
	public static final String ICON_LDC = "/img/ztree/ldc.png";
	public static final String ICON_STORAGE = "/img/ztree/storage.png";
	public static final String ICON_COLLEGE = "/img/ztree/college.png";

	public static final String ZTREECHECKED = "true";

	public static final String LDC_STORAGE_NAME = "LDC仓";
	public static final String RDC_STORAGE_NAME = "RDC仓";
	public static final String VM_STORAGE_NAME = "虚拟仓";
	public static final String All_STORAGE_NAME = "全国仓";
	public static final String All_CITY = "全国";

	/** 范围类型 0 所有LDC仓或RDC仓 */
	public static final int ALLSTORAGE_REGION_TYPE = 0;
	/** 单个仓类型 */
	public static final int STORAGE_REGION_TYPE = 1;
	/** 城市类型 */
	public static final int CITY_REGION_TYPE = 2;
	/** 所有RDC仓范围的id */
	public static final int ALLRDCSTORAGEID = 0;
	/** 所有LDC仓范围的id */
	public static final int ALLLDCSTORAGEID = 1;

	/** rdc 仓库id */
	public static final String RDC_STORAGE = "0";
	/** ldc 仓库id */
	public static final String LDC_STORAGE = "1";
	/** VM仓库id */
	public static final String VM_STORAGE = "2";
	/** 全部仓库id */
	public static final String ALL_STORAGE = "-1";

	// zhangshuai
	public static final int GOOD_MAXNUM = 0;
	public static final int GOOD_STATUS_ONSALE = 1;// 代售
	public static final int GOOD_STATUS_SALING = 2;// 在售
	public static final int GOOD_STATUS_STOPSALE = 3;// 售罄
	public static final int GOOD_STATUS_DEFAULT = 1;// 下架
	public static final int GOOD_WEIGHT_DEFAULT = 0;// 权重

	public static final int SKU_STATUS_ONSALE = 1;// 待售
	public static final int SKU_STATUS_DEFAULT = 1;// 下架
	public static final int SKU_ORIGIN_PRICE_DEFAULT = 0;
	public static final int SKU_APP_PRICE_DEFAULT = 0;
	public static final int SKU_WEB_PRICE_DEFAULT = 0;
	public static final int SKU_STOCK_DEFAULT = 0;// 学校限售
	public static final int SKU_STOCK_NAN = 9999999;// 不限售
	public static final int BGGOODSREGION_STOCK_NAN = 9999999;// 不限售

	// 金额元分转化
	public static final int YUAN_FEN_CHANGE = 100;

	public static final int ADMIN_FLAG = 1;
	/*** 是否秒杀 */
	public static final int ISSECKILL = 1;
	/** 普通商品 */
	public static final int ISGENERAL = 0;

	public static final String ACTIVITY_SECKILL_FLAG = "activity_secKill:";
	public static final String ACTIVITY_NORMAL_FLAG = "activity_normal:";

	public static final String CATEGORY_FLAG = "category:";
	// 第三方商品条码前缀
	public static final String SF_WMS_PREFIX = "SF";
	// 小麦货品仓ID
	public static final Long XM_WMS_STORAGE_ID = 0L;
	// 所有货品仓库ID标识
	public static final Long ALL_WMS_STORAGE_ID = -2L;

	public static final String REFRESHBEAN = "refreshbean";
	
	//主从标识，0：功能失效；1：使用主库；2：从库；
	public static final String MASTER_SLAVE_FLAG = "masterSlaveFlag";
	public static final int MASTER_FLAG_VALUE = 1;
	public static final int SLAVE_FLAG_VALUE = 2;
	public static final int MASTER_SLAVE_FAIL = 0;

}
