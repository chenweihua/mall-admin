package com.mall.admin.model.dao.shard;

import com.mall.admin.model.yshard.TableShard;


@TableShard(tableName = "tb_delivery", shardType = "%1024", shardBy = "deliveryCompanyCode,deliverySheetCode")
public class DeliveryShard {

}
