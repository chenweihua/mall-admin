package com.mall.admin.service.taobao.api;

import com.mall.admin.service.taobao.api.internal.cluster.ClusterManager;
import com.mall.admin.service.taobao.api.internal.cluster.DnsConfig;
import com.mall.admin.service.taobao.api.internal.util.WebUtils;

public class AutoRetryClusterTaobaoClient extends AutoRetryTaobaoClient {

	static {
		WebUtils.setIgnoreHostCheck(true);
	}

	public AutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret) throws ApiException
	{
		super(serverUrl, appKey, appSecret);
		ClusterManager.initRefreshThread(this);
	}

	public AutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret, String format)
			throws ApiException
	{
		super(serverUrl, appKey, appSecret, format);
		ClusterManager.initRefreshThread(this);
	}

	public AutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret, String format,
			int connectTimeout, int readTimeout) throws ApiException
	{
		super(serverUrl, appKey, appSecret, format, connectTimeout, readTimeout);
		ClusterManager.initRefreshThread(this);
	}

	public AutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret, String format,
			int connectTimeout, int readTimeout, String signMethod) throws ApiException
	{
		super(serverUrl, appKey, appSecret, format, connectTimeout, readTimeout, signMethod);
		ClusterManager.initRefreshThread(this);
	}

	@Override
	protected String getServerUrl(String serverUrl, String apiName, String session) {
		DnsConfig dnsConfig = ClusterManager.GetDnsConfigFromCache();
		if (dnsConfig == null) {
			return serverUrl;
		} else {
			return dnsConfig.getBestVipUrl(serverUrl, apiName, session);
		}
	}

	@Override
	protected String getSdkVersion() {
		return Constants.SDK_VERSION_CLUSTER;
	}

}
