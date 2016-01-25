package com.mall.admin.service.taobao.api.internal.parser.xml;

import com.mall.admin.service.taobao.api.ApiException;
import com.mall.admin.service.taobao.api.TaobaoParser;
import com.mall.admin.service.taobao.api.TaobaoResponse;
import com.mall.admin.service.taobao.api.internal.mapping.Converter;

/**
 * 单个XML对象解释器。
 * 
 * @author carver.gu
 * @since 1.0, Apr 11, 2010
 */
public class ObjectXmlParser<T extends TaobaoResponse> implements TaobaoParser<T> {

	private Class<T> clazz;

	public ObjectXmlParser(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public T parse(String rsp) throws ApiException {
		Converter converter = new XmlConverter();
		return converter.toResponse(rsp, clazz);
	}

	@Override
	public Class<T> getResponseClass() {
		return clazz;
	}

}
