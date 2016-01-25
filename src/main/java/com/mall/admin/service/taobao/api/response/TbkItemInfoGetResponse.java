package com.mall.admin.service.taobao.api.response;

import java.util.List;

import com.mall.admin.service.taobao.api.TaobaoResponse;
import com.mall.admin.service.taobao.api.domain.NTbkItem;
import com.mall.admin.service.taobao.api.internal.mapping.ApiField;
import com.mall.admin.service.taobao.api.internal.mapping.ApiListField;

/**
 * TOP API: taobao.tbk.item.info.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class TbkItemInfoGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 3348225993996496394L;

	/**
	 * 淘宝客商品
	 */
	@ApiListField("results")
	@ApiField("n_tbk_item")
	private List<NTbkItem> results;

	public void setResults(List<NTbkItem> results) {
		this.results = results;
	}

	public List<NTbkItem> getResults() {
		return this.results;
	}

}
