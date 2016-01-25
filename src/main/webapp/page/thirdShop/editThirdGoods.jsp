<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<style>
.goodsInfo{
	margin: 2 20px;
	background-color: #FFF5EE;
}
.hint{
	text-align: center;
}
</style>

<%@ include file="../header.jsp"%>
<div class="box-inner">
	<div class="box-header well" data-original-title="">
		<h2>
			<i class="glyphicon glyphicon-banner"></i>商品管理
		</h2>
		<div class="box-icon">
			<a href="#"	class="btn btn-minimize btn-round btn-default">
				<i class="glyphicon glyphicon-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="box-content">
		<form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
			<div class="row">
				<div class="col-md-1 hint">选择商品</div>
			</div>
			<!-- 选择商品 -->
			<div class="row">
				<div class="goodsInfo col-md-10 col-md-offset-1">
					<div id="selectButtonContainer">
					<button id="selectGoods">选择商品</button>
					<br><br></div>
					<div id="bgSkuTableContainer">
					
					</div>
				</div>
			</div>
			<!-- 商品信息 -->
			<div class="row">
				<div class="col-md-1 hint">商品信息</div>
			</div>
			<div class="row">
				<div class="goodsInfo col-md-10 col-md-offset-1">
					<input type="hidden" id="bgGoodsId" name="bgGoodsId" readonly="readonly"
					value="${bgGoodsId}">
					<input type="hidden" id="activityBgGoodsId" name="activityBgGoodsId" readonly="readonly"
					value="${bgGoods.activityBgGoodsId}">
					<input type="hidden" id="storageId" name="storageId" readonly="readonly"
						value="${storageId}">
						
					<div class="form-group">
						<label for="skuName" class="col-sm-2 control-label">售卖状态</label>
						<div class="col-sm-3">
							<select class="form-control request" id="goodsStatus"
								name="goodsStatus">
								<option value="2" <c:if test="${bgGoods.goodsStatus == 2 }">selected="selected"</c:if>>开启</option>
								<option value="1" <c:if test="${bgGoods.goodsStatus == 1 }">selected="selected"</c:if>>关闭</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="skuName" class="col-sm-2 control-label">商品限购</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="maxNum"
								name="maxNum" value="${bgGoods.maxNum}"
								placeholder="限购">
						</div>
					</div>
					<div class="form-group">
						<label for="skuName" class="col-sm-2 control-label">商品权重</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="weight"
								name="weight" value="${bgGoods.weight}"
								placeholder="权重">
						</div>
					</div>
					<div class="form-group">
						<label for="skuName" class="col-sm-2 control-label">商品名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="bgGoodsName" readonly="readonly"
								name="bgGoodsName" value="${bgGoods.bgGoodsName}"
								placeholder="商品名称">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">品牌名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="brand" readonly="readonly"
								name="brand" value="${bgGoods.brand}" placeholder="品牌名称">
						</div>
					</div>
					
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">商品描述</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="description" readonly="readonly"
								name="description" value="${bgGoods.description}"
								placeholder="商品描述">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">单位</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="unit" readonly="readonly"
								name="unit" value="${bgGoods.unit}" placeholder="商品单位">
						</div>
					</div>
		
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">销售规格</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="saleSpec" readonly="readonly"
								name="saleSpec" value="${bgGoods.saleSpec}" placeholder="销售规格">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">原产地</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="originPlace" readonly="readonly"
								name="originPlace" value="${bgGoods.originPlace}"
								placeholder="原产地">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">产品包装</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="packageSpec" readonly="readonly"
								name="packageSpec" value="${bgGoods.packageSpec}"
								placeholder="产品包装">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">保质期</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="shelfLife" readonly="readonly"
								name="shelfLife" value="${bgGoods.shelfLife}" placeholder="保质期">
						</div>
					</div>
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">商品备注</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="remark" readonly="readonly"
								name="remark" value="${bgGoods.remark}" placeholder="商品备注">
						</div>
					</div>
				</div>	
			</div>		
		</form>
		<button type="button" class="btn btn-default" id="cancel">取消</button>
		<button type="button" class="btn btn-primary" id="save">保存</button>
	</div>
</div>

<%@ include file="../footer.jsp"%>
<%@ include file="selectBgGoods.jsp"%>

<script type="text/javascript">
var storageId = ${storageId};

$(document).ready(function(){
	var activityBgGoodsId = $('#activityBgGoodsId').val();
	var bgGoodsId = $('#bgGoodsId').val();
	if(activityBgGoodsId != '' && bgGoodsId != ''){
		$('#selectButtonContainer').hide();
		$.post("/bgGoods/third/selectBgGoods",{"bgGoodsId":bgGoodsId,"activityBgGoodsId":activityBgGoodsId}, function(ret){
			if(ret.code != 0){
				alert(ret.msg);
			}else{
				var initActivitySkuInfo = {};
				var bgSkuForActivities = ret.data.bgSkuForActivities;
				for(var i=0,len=bgSkuForActivities.length;i<len;i++){
					var bgActivitySku = bgSkuForActivities[i]
					initActivitySkuInfo[bgActivitySku.bgSkuId] = bgActivitySku;
				}
				var bgGoods = ret.data.bgGoods;
				var selectedPnList = ret.data.selectedPnList;
				var bgSkuList = ret.data.bgSkuList;
				var selectedPn = {};
				
				$('#bgGoodsSelectPage').modal('hide');
				var tableData = [];
				tableData.push('<table id="bgSkuTable" class="skuTable"><tr>');
				for(var i=0,len1=selectedPnList.length;i<len1;i++){
					var propertyName = selectedPnList[i];
					tableData.push('<th>'+propertyName.propertyName+'</th>');
					selectedPn[i] = propertyName.propertyNameId;
				}
				tableData.push('<th>商品条码</th><th>原价</th><th>售卖价</th><th>库存</th></tr>');
				for(var i=0,len2=bgSkuList.length;i<len2;i++){
					var bgSku = bgSkuList[i];
					var initInfo = initActivitySkuInfo[bgSku.bgSkuId];
					if(initInfo != null){
						var spMap = bgSku.skuPropertyMap;
						tableData.push('<tr>');
						for(var j=0,len=selectedPnList.length;j<len;j++){
							tableData.push('<td>'+spMap[selectedPn[j]].propertyValue+'</td>');
						}
						tableData.push('<td>'+bgSku.wmsGoodsGbms+'</td>');
						tableData.push('<td>'+bgSku.originPrice+'</td>');
						tableData.push('<td><input name="originPrice'+bgSku.bgSkuId+'" type="hidden" value="'+bgSku.originPrice/100+'"><input name="price'+bgSku.bgSkuId+'" value="'+initInfo.activityPrice/100+'"></td>');
						tableData.push('<td><input name="bgSkuId" type="hidden" value="'+bgSku.bgSkuId+'"><input name="activityBgSkuId'+bgSku.bgSkuId+'" type="hidden" value="'+initInfo.activityBgSkuId+'">'+bgSku.stock+'</td>');
						tableData.push('</tr>');
					}
				}
				tableData.push('</table>');
				$('#bgSkuTableContainer').empty();
				$('#bgSkuTableContainer').append(tableData.join(' '));
			}		
		},"json");
	}
});



$('#save').click(function(){
	$.post('/goods/third/edit',$('#editForm').serialize(),function(json){
		if(json.code == 0){
			$('#bgGoodsId').val(json.data);
			alert(json.msg);
			window.location.href = "/goods/third/list";
		}else{
			alert(json.msg);
		}
	});
});

$('#cancel').click(function(){
	window.location.href = "/goods/third/list";
});

$('#selectGoods').click(function(){
	$("#bgGoodsSelectPage").modal('show');
});



function checkParam(){
	for(var i=0;i<$(".request").length;i++){
		if($.trim($(".request").eq(i).val()).length==0){
			alert($(".request").eq(i).attr('placeholder')+"是必填项");
			return false;
		}
	}
	return true;
}
</script>