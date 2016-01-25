<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 日常专题上单
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i
						class="glyphicon glyphicon-cog"></i></a> <a href="#"
						class="btn btn-minimize btn-round btn-default"><i
						class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
						class="btn btn-close btn-round btn-default"><i
						class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>

			<div class="box-content">
				<!-- <div class="alert alert-info">
					For help with such table please check <a
						href="http://datatables.net/" target="_blank">http://datatables.net/</a>
				</div> -->
				<table id="bgGoodsList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th><input type="checkBox" id="checkAll">序号</th>
							<th>商品名称</th>
							<th>备注</th>
							<th>图片</th>
							<th>类型</th>
							<th style='width:50px'>原价</th>
							<th style='width:50px'>活动价</th>
							<th style='width:50px'>限售数量</th>
							<th style='width:50px'>权重</th>
							<th style='width:50px'>限购数量</th>
							<th>描述</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	<!--/span-->

	<div class="pl15">
		<button type="button" class="btn btn-primary btn-sm" id="addButton">批量添加至活动</button>
	</div>

</div>
<!--/row-->
<%@ include file="addgeneralgoods.jsp"%>
<%@ include file="../footer.jsp"%>

<script id="saleInCollegeTableTemplate" type="text/template">
		<p>商品信息：</p>
		<table border='1' id="skuinfo"><tr class="title"><td>商品名称</td><td>单位</td><td>描述</td><td>原价</td><td>活动价</td><td>限售数量</td></tr>
       		 {{#bgSkuInfo}}
            	<tr class="skuinfo">	
					<td><input type="hidden" id="skuid" value='{{bgSkuId}}'/> {{bg_goods_name}}</td>
					<td> {{unit}}</td>
					<td> {{description}}</td>
					<td><input type="text" id="originprice" value="{{origPriceDouble}}"/></td>
					<td><input type="text" id="activityprice"/></td>
					<td><input type="text" id="stock"/></td>
				</tr>
       		{{/bgSkuInfo}}
		</table>
</script>
<script type="text/javascript" src="/js/mustache.js"></script>
<script>

var tableTemplateData = {};

tableTemplateData.origPriceDouble = function() {
	return this.originPrice/100.0;
};

var tableTemplate = document.getElementById('saleInCollegeTableTemplate').innerHTML;

function fillTable(bgSkuInfo) {
	tableTemplateData.bgSkuInfo = bgSkuInfo;
	return Mustache.render(tableTemplate, tableTemplateData);
}

function fillDiv(bggoodsId, bgSkuInfo) {
	var skuInfoTable = null;
	if (bgSkuInfo == null || bgSkuInfo.length == 0) {
		skuInfoTable = "没有商品";
	} else {
		skuInfoTable = fillTable(bgSkuInfo);
	}
	var divStr = '<tr class="yellowbox padding10"><td colspan="14" align="center">'
			+ '<table>'
			+ '<tr><td></td></tr>'
			+ '<tr><td id=block' + bggoodsId + '>'
			+ skuInfoTable
			+ '</td></tr></table>';
	return divStr;
}

function showSkuInfo(obj, bggoodsId,goodsType) {
	$("#originPrice"+bggoodsId).attr("disabled",true);
	$("#activityprice"+bggoodsId).attr("disabled",true);
	$("#stock"+bggoodsId).attr("disabled",true);
	$(obj).attr("onclick", "hideSkuInfo(this," + bggoodsId +","+goodsType+ ")");
	$.post("getskuinfo", {
		"bggoodsId" : bggoodsId,
		"goodsType":goodsType,
	}, function(result) {
		var saleInCollegeDiv = fillDiv(bggoodsId, result.data.bgSkuInfo);

		var nextTr = $(obj).parent().parent().next();

		var trClass = nextTr.attr("class");
		if (trClass == undefined || trClass.indexOf("yellowbox") < 0) {
			$(obj).parent().parent().after(saleInCollegeDiv);
		}
	});
}

function hideSkuInfo(obj, bggoodsId,goodsType) {
	$("#originPrice"+bggoodsId).attr("disabled",false);
	$("#activityprice"+bggoodsId).attr("disabled",false);
	$("#stock"+bggoodsId).attr("disabled",false);
	$(obj).attr("onclick", "showSkuInfo(this,'" + bggoodsId +"','"+goodsType+ "')");
	var nextTr = $(obj).parent().parent().next();
	console.log(nextTr);
	if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
		nextTr.remove();
	}
}


	var table;
	$(document)
			.ready(
					function() {
						//datatable
						table = $('#bgGoodsList')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											searching : true,
											ajax : {
												url : "querybggoods",
												data : function(d) {
													d.activityType = 0;
												},
												dataSrc : function(json) {
													var newData = [];
													var seq = json.start + 1;

													for ( var i = 0, len = json.data.length; i < len; i++) {
														var bgGoods = json.data[i];
														var bgGoodsName = bgGoods.bgGoodsName;
														var goodsType = "未知";
														if (bgGoods.bgGoodsType == 1) {
															goodsType = "单品"
														} else if (bgGoods.bgGoodsType == 2) {
															goodsType = "组合品"
														} else if (bgGoods.bgGoodsType == 3) {
															goodsType = "聚合品"
															bgGoodsName = '<a class="blueletter" href="javascript:void(0)" onclick="showSkuInfo(this,'
																	+ bgGoods.bgGoodsId+','+bgGoods.bgGoodsType
																	+ ')">'
																	+ bgGoods.bgGoodsName
																	+ '</a>';
														}
														newData[newData.length] = [
																'<input type="checkBox" name="bggoodsId" value="'+bgGoods.bgGoodsId+'">'
																		+ (seq + i),
																bgGoodsName,
																bgGoods.bgGoodsRemark,

																"<a href="+bgGoods.imageUrl+">查看图片</a>",
																goodsType,
																"<input type='text' style='width:50px' value='"+bgGoods.originPrice+"' id='originPrice"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' id='activityprice"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' id='stock"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' id='weight"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' value='1' id='maxnum"+bgGoods.bgGoodsId+"'>",
																bgGoods.bgGoodsDesc,
																'<a class="blueletter" href="javascript:void(0)" onclick="addToActivity('
																		+ bgGoods.bgGoodsId
																		+ ')">添加至活动</a>' ];
													}
													json.data = newData;
													return newData;
												}
											},
											sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
											sPaginationType : "bootstrap",
											oLanguage : {
												sLengthMenu : "_MENU_ 记录每页",
												sSearch : "按名称搜索 _INPUT_",
												sZeroRecords : "暂无数据",
												sProcessing : "正在处理...",
												sEmptyTable : "暂无数据",
												sInfo : "_START_ - _END_ (共_TOTAL_条)",
												sInfoFiltered : "",
												oPaginate : {
													sFirst : "第一页",
													sLast : "最后一页",
													sNext : "下一页",
													sPrevious : "前一页",
												},

											}
										});

					});

	function addToActivity(goodsId) {
		$('#bgGoodsId').val(goodsId);
		$('#modalEdit').modal();
	}

	//全选
	$("#checkAll").click(function() {
		if ($("#checkAll").prop("checked") == true) {
			$("input[name='bggoodsId']").each(function() {
				$(this).prop("checked", true);
			});
			//alert();
		} else {
			$("input[name='bggoodsId']").each(function() {
				$(this).prop("checked", false);
			});
		}
	});

$("#addButton").click(function(){
		//获取勾选的资产的ID集合
		var bggoodsIds = "";
		$("input[name=bggoodsId]").each(function() {
			if ($(this).prop("checked")) {
				bggoodsIds += $(this).val() + ",";
			}
		});
		if (bggoodsIds == "") {
			alert("请选择要批量修改商品~");
			return;
		}
		$('#bgGoodsId').val(bggoodsIds);
		$('#modalEdit').modal();

	});
</script>