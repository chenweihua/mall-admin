/**
 * 
 */
/**
 * 原分表的配置信息在各个Dao类上，而直接通过mybatis上的sql信息无法获得该dao的路径信息，从而写死在interceptor中,如果多个路径，则不方便
 * 这里固定一个地址，所有需要分表的，在此建立一个类(*Shard)，并提供分表元数据信息
 *
 */
package com.mall.admin.model.dao.shard;