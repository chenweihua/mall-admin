<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<link href='../../css/goods.css' rel='stylesheet'>
<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css"
	type="text/css">

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<style>
.gbmTable {
	margin: 10px 0 10px 0;
}

.gbmTable th{
	border: 1px solid #ccc;
	text-align:center;
	width:200px
}

.gbmTable td{
	border: 1px solid #ccc;
	text-align:center;
}
</style>
<%@ include file="../header.jsp"%>

	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i>单品上单
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
			
			<div class="tabbable" id="tabs-677904">
				<ul class="nav nav-tabs">
					<li class="active"><a id="a_1" contenteditable="false" data-toggle="tab" href="#panel-1">商品信息</a></li>
					<li id="tab_2"><a id="a_2" contenteditable="false" data-toggle="tab" href="#panel-2">区域管理</a></li>
					<li ><a id="a_3" contenteditable="false" data-toggle="tab" href="#panel-3">价格管理</a></li>
				</ul>
				
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="panel-1">
						<br>
						 <form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
							<input type="hidden" id="bgGoodsId" name="bgGoodsId" value="${bgGoods.bgGoodsId}">
							<!-- <input type="hidden" id="bgGoodsId" name="bgGoodsId" value="18"> -->
							<div class="form-group">
						    	<label for="skuName" class="col-sm-2 control-label">商品名称</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="bgGoodsName" name="bgGoodsName" value="${bgGoods.bgGoodsName}" placeholder="商品名称">
						        </div>
							</div>
							
							<%-- <div class="form-group">
						    	<label for="skuName" class="col-sm-2 control-label">二级名称</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control" id="bgGoodsSubname" name="bgGoodsSubname" value="${bgGoods.bgGoodsSubname}" placeholder="二级名称">
						        </div>
							</div> --%>
							
							<button id="addGbm" class="col-md-offset-1">添加商品</button>
							<table id="gbmTable" class="gbmTable col-md-offset-1">
								<tr><th>商品名称</th><th>商品条码</th><th>打包数量</th><th>操作</th></tr>
								<c:forEach var="item" items="${bgSkuGbmList}">
									<tr><td>${item.wmsGoodsName}</td><td>${item.wmsGoodsGbm}</td><td><input name='${item.wmsGoodsId}num' value='${item.num}'/><input type='hidden' name='wms_goods_id' value='${item.wmsGoodsId}'/></td><td><a href='javascript:void(0)' onclick='removeTr(this);'>删除</a></td></tr>
								</c:forEach>
							</table> 
							
							<div class="form-group">
						    	<label for="catgId" class="col-sm-2 control-label">所属类目</label>
						        <div class="col-sm-3">
						        	<select class="form-control request" id="categoryId" name="categoryId">
						        		<c:forEach items="${categories}" var="category">
						                	<option value="${category.categoryId}" <c:if test="${bgGoods.categoryId==category.categoryId}">selected='selected'</c:if> >${category.categoryName}</option>
						                </c:forEach>
						            </select>
						        </div>
							</div>
							
							<div class="form-group">
						    	<label for="subName" class="col-sm-2 control-label">商品单位</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="unit" name="unit" value="${bgGoods.unit}" placeholder="商品单位">
						        </div>
							</div>
							<div class="form-group">
						    	<label for="subName" class="col-sm-2 control-label">销售规格</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="saleSpec" name="saleSpec" value="${bgGoods.saleSpec}" placeholder="销售规格">
						        </div>
							</div>
							<div class="form-group">
						    	<label for="subName" class="col-sm-2 control-label">原产地</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="originPlace" name="originPlace" value="${bgGoods.originPlace}" placeholder="原产地">
						        </div>
							</div>
							<div class="form-group">
						    	<label for="subName" class="col-sm-2 control-label">产品包装</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="packageSpec" name="packageSpec" value="${bgGoods.packageSpec}" placeholder="产品包装">
						        </div>
							</div>
							<div class="form-group">
						    	<label for="subName" class="col-sm-2 control-label">品牌</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="brand" name="brand" value="${bgGoods.brand}" placeholder="品牌">
						        </div>
							</div>
							<div class="form-group">
						    	<label for="subName" class="col-sm-2 control-label">保质期</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="shelfLife" name="shelfLife" value="${bgGoods.shelfLife}" placeholder="保质期">
						        </div>
							</div>
							
							 <div class="form-group">
						    	<label for="description" class="col-sm-2 control-label">商品描述</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="description" name="description" value="${bgGoods.description}" placeholder="商品描述">
						        </div>
							</div>
							
							<div class="form-group">
						    	<label for="description" class="col-sm-2 control-label">商品备注</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control" id="remark" name="remark" value="${bgGoods.remark}" placeholder="商品备注">
						        </div>
							</div>
						    <br>
							<div class="form-group">
								<label for="imageUrl1" class="col-sm-2 control-label">商品大图</label>
								<div class="col-sm-6">
									<input id="uploadImage1" type="file" name="p1" onchange="PreviewMutiImage('uploadImage1','uploadPreview','imageUrl',5);" />
									<span id="uploadPreview1">
										<img src="${bgGoods.imageUrl}" style="width:200px" alt="商品大图"/>
										<c:if test="${not empty bgGoods.imageUrl}">
											<a href="javascript:void(0);" onclick="removeElement('uploadPreview1');">删除</a>
										</c:if>
										<input type="hidden" id="imageUrl1" name="imageUrl" value="${bgGoods.imageUrl}">
									</span><br />
									<c:forEach var="item" items="${commerceImagelist}" varStatus="status">
										<span id="uploadPreview${status.count+1}">
											<img src="${item.imageUrl}" style="width:200px" alt="商品大图"/>
											<a href="javascript:void(0);" onclick="removeElement('uploadPreview${status.count+1}');">删除</a><br />
											<input type="hidden" name="imageUrl" value="${item.imageUrl}">
										</span>
									</c:forEach>
								</div>
							</div>
							<div class="form-group">
								<label for="imageUrl2" class="col-sm-2 control-label">商品详情</label>
								<div class="col-sm-6">
									<input id="uploadImage2" type="file" name="p2" onchange="PreviewMutiImage('uploadImage2','uploadDetailPreview','detailImageUrl',10);" />
									<c:if test="${empty detailImagelist}">
										<span id="uploadDetailPreview1">
											<img src="" style="width:200px" alt="商品大图"/>
											<input type="hidden" name="detailImageUrl" value="">
										</span><br />
									</c:if>
									<c:forEach var="item" items="${detailImagelist}" varStatus="status">
										<span id="uploadDetailPreview${status.count}">
											<img src="${item.imageUrl}" style="width:200px" alt="商品大图"/>
											<a href="javascript:void(0);" onclick="removeElement('uploadDetailPreview${status.count}');">删除</a><br />
											<input type="hidden" id="imageUrl2" name="detailImageUrl" value="${item.imageUrl}">
										</span>
									</c:forEach>
								</div>
							</div>
						</form>
					 	<!-- <button type="button" class="btn btn-default" data-dismiss="modal" id="save1">保存</button> -->
		     			<button type="button" class="btn btn-primary" id="nextButton1">下一步</button>
					</div>
					
					<!-- tab第二部分 -->
					<div class="tab-pane" contenteditable="false" id="panel-2">
						<div class="box-content">
							<div id="treeWrap">
								<ul id="selectTree" class="ztree"></ul>
							</div>
						</div>
						<%-- <%@ include file="region_tree_forGoods.jsp"%> --%>
						
						<button type="button" class="btn btn-default" data-dismiss="modal" id="save2">上一步</button>
		     			<button type="button" class="btn btn-primary" id="nextButton2">下一步</button>
					</div>
					
					<!-- tab第三部分 -->
					<div class="tab-pane" contenteditable="false" id="panel-3">
						<%@ include file="regionPrice.jsp" %>
						<button type="button" class="btn btn-primary" id="nextButton3">完成</button>
					</div>
				</div>
			</div>
			
			</div>
		</div>
	</div>


<%@ include file="../footer.jsp"%>

<%@ include file="add_gbm.jsp"%>

<script type="text/javascript">
/* $(document).ready(function(){
	var trNum=$('#gbmTable tr').length;
	if(trNum > 1){
		$('#gbmTable').show();
	}
}); */

var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};

//商品信息
$('#save1').click(function() {
    if(checkParam()){
		$.post("/goods/singleProduct/edit", $("#editForm").serialize(), function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				$('#bgGoodsId').val(json.data);
				alert(json.msg);
			}
		});
	} 
});
$('#nextButton1').click(function() {
   /*  if(checkParam()){
		$.post("/goods/singleProduct/edit", $("#editForm").serialize(), function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				$('#bgGoodsId').val(json.data);
				$('#a_2').trigger('click');
			}
		});
	}  */
	if(checkParam()){
		$('#a_2').trigger('click');
	}
});


//仓库管理
$('#save2').click(function() {
	$('#a_1').trigger('click');
});

$('#nextButton2').click(function() {
	//添加基础信息
	if(checkParam()){
		$.post("/goods/singleProduct/edit", $("#editForm").serialize(), function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				$('#bgGoodsId').val(json.data);
				//$('#a_2').trigger('click');
				//设置范围
				var bgGoodsId = $('#bgGoodsId').val();
				var treeObj=$.fn.zTree.getZTreeObj("selectTree");
				var nodes=treeObj.getNodes();
				var info = JSON.stringify(nodes);
				$.post("/goods/region/setRegion/byBgGoodsId",{"bgGoodsId":bgGoodsId,"treeInfo": info}, function(json){
					if (json.code != 0) {
						alert(json.msg);
					} else {
						$('#a_3').trigger('click');
					}
				});
			}
		});
	 } 
});

$('#nextButton3').click(function() {
	if(checkPrice()){
		window.location.href="/goods/manager";
	}
});

$('#a_1').click(function() {
	
});

$('#a_2').click(function() {
	var bgGoodsId = $('#bgGoodsId').val();
	/* if(bgGoodsId == ''){
		alert("请首先填写商品信息，并单击保存或下一步");
		return false;
	} */
 	var trNum=$('#gbmTable tr').length;
	if(trNum < 2){
		alert("商品信息中，必须选择添加商品");
		return false;
	}
	var wmsGoodsIds = '';
	$('input[name="wms_goods_id"]').each(function(){
		wmsGoodsIds += ":" + $(this).val();
	}); 
	//为范围设值
	$.post("/goods/region/getRegion/byWmsGoodsIds",{"bgGoodsId":bgGoodsId,"wmsGoodsIds":wmsGoodsIds}, function(json){
		if (json.code != 0) {
			alert(json.msg);
		} else {
			var zNodes = json.data;
			$.fn.zTree.init($("#selectTree"), setting, zNodes);
		}
	});
});

//价格管理
$('#a_3').click(function() {
	var bgGoodsId = $('#bgGoodsId').val();
	if(bgGoodsId == ''){
		alert("请首先填写商品信息，并单击保存或下一步");
		return false;
	}
	//为价格设值
	rdcTable.ajax.url("/goods/price/getRdcStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
	ldcTable.ajax.url("/goods/price/getLdcCity4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
	vmTable.ajax.url("/goods/price/getVmStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
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

function checkPrice(){
	for(var i=0;i<$(".priceCheck").length;i++){
		if($.trim($(".priceCheck").eq(i).text()) < 0.01){
			alert("所有学校价格必须设置，且大于零");
			return false;
		}
	}
	return true;
}

$('#addGbm').click(function() {
	$("#gbmEdit").modal('show');
});

</script>