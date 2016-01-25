package com.mall.admin.service.taobao.api.internal.util.json;

public interface JSONErrorListener {
	void start(String text);

	void error(String message, int column);

	void end();
}
