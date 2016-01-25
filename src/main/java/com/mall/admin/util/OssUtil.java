package com.mall.admin.util;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;


/**
 *  与阿里云OSS文件系统交互
 */
public class OssUtil {
	
	private static String accessKeyId = "hcJqYNdBCKOHp5cF";
	private static String accessKeySecret = "PNAc5gnKqofWuLa7jbNPgbXOSCzNuE";
	private static String endpoint = "http://oss-cn-beijing-internal.aliyuncs.com";
	
	
	/**
	 *  bucketName 命名空间
	 *  Bucket是OSS上的命名空间，也是计费、权限控制、日志记录等高级功能的管理实体；Bucket名称在整个OSS服务中具有全局唯一性，且不能修改；
	 *  存储在OSS上的每个Object必须都包含在某个Bucket中。一个应用，例如图片分享网站，可以对应一个或多个Bucket。
	 *  一个用户最多可创建10个Bucket，但每个bucket中存放的object的数量没有限制，存储容量每个buckte最高支持2PB。
	 *  
	 *  key 文件名 如果有/，则可以模拟文件夹（OSS本身没有文件夹的概念）
	 *  
	 *  content 放入的文件流
	 *  
	 *  content putObject会在其内部关闭，无需调用者再处理
	 */
	public static void putObject(String bucketName, String key, InputStream content) throws Exception {

	    // 初始化OSSClient
	    OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);

	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(content.available());

	    // 上传Object.
	    client.putObject(bucketName, key, content, meta);
	    //PutObjectResult result = client.putObject(bucketName, key, content, meta);

	    // 打印ETag
	    //System.out.println(result.getETag());
	}
	
	
	/**
	 * bucketName 命名空间
	 * key 文件名
	 * 详见上面的注释
	 * 调用端需注意自行关闭返回的inputstream
	 */
	public static InputStream getObject(String bucketName, String key) throws Exception {

	    // 初始化OSSClient
		OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);

	    // 获取Object，返回结果为OSSObject对象
	    OSSObject object = client.getObject(bucketName, key);

	    // 获取ObjectMeta
	    //ObjectMetadata meta = object.getObjectMetadata();

	    // 获取Object的输入流
	    InputStream objectContent = object.getObjectContent();

	    return  objectContent;
	}

	

}
