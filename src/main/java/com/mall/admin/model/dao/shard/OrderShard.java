package com.mall.admin.model.dao.shard;

import com.mall.admin.model.yshard.TableShard;


@TableShard(tableName = "tb_child_order", shardType = "%1024", shardBy = "userId")
public class OrderShard {

}
