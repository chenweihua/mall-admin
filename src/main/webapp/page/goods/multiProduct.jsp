<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css"
	type="text/css">

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>

<style>
.propertyCategory{
	float: left;
	margin-top:20px;
	width:120px;
	border: 1px solid #ccc;
}

.propertyCategory ul{
	margin: 0 15px;
}

.propertyCategory li{
	list-style-type:none;
	margin-top: 5px;
	cursor: pointer;
}

.propertyCategory h4{
	margin:5px;
}

.propertyCategory h6{
	margin-left:60px;
	margin-top:10px;
	cursor: pointer;
	color: blue;
}

.skuTable {
	margin: 10px 0 10px 0;
}

.skuTable th{
	border: 1px solid #ccc;
	text-align:center;
	width:150px
}

.skuTable td{
	border: 1px solid #ccc;
	text-align:center;
}

.pnTitle{
	font-weight: bold;
}

.pnTable{
	margin: 20px 0 20px 0;
}

.pnTable td{
	border: 1px solid #ccc;
	text-align: center;
}

.pnTable img{
	width: 10px;
	height: 10px;
	margin: 0 0 5px 10px;
}
</style>

<%@ include file="../header.jsp"%>
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i>聚合上单
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
					<li ><a id="a_3" contenteditable="false" data-toggle="tab" href="#panel-3">限购管理</a></li>
				</ul>
				
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="panel-1">
					<br>
			        <form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
 						<input type="hidden" id="bgGoodsId" name="bgGoodsId" value="${bgGoods.bgGoodsId}">
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
					
						<button id="addSku" class="col-md-offset-1">添加商品</button>
						<table id="skuTable" class="skuTable col-md-offset-1">
							<tr><th>序号</th><th>sku名称</th><th>商品条码</th><th>备注</th><th>操作</th></tr>
							<c:forEach var="bgGoodsDto" items="${bgGoodsDtos}" varStatus="status">
									<tr><td>${status.index+1}</td><td><a class="blueletter" href="javascript:void(0)" onclick="modifyBgGoods(${bgGoodsDto.bgGoods.bgGoodsId})">${bgGoodsDto.bgGoods.bgGoodsName}</a></td><td>${bgGoodsDto.wmsGoodsGbms}</td><td>${bgGoodsDto.bgGoods.remark}<input type='hidden' id="bg_sku_id${bgGoodsDto.bgSkuId}" name='bg_sku_id' value="${bgGoodsDto.bgSkuId}"/></td><td><a href='javascript:void(0)' onclick='removeTr(this);'>删除</a></td></tr>
							</c:forEach>
						</table> 
						
						<div id="createProperty" class="form-group">
							<label class="col-md-2 control-label">创建规格</label>
							<div>
								<div class="propertyCategory">
									<h4>商品属性</h4>
									<ul id="pCategory1">
										<c:forEach var="item" items="${pCategoryList}">
											<li pcId="${item.propertyCategoryId }">${item.propertyCategoryName}</li>
										</c:forEach>
									</ul>
									<h6 id="proCateAdd1">+添加</h6>
									<input id="pCategory1Checked" type="hidden"/>
								</div>
								
								<div class="propertyCategory col-md-offset-1">
									<h4>商品类型</h4>
									<ul id="pCategory2">
									</ul>
									<h6 id="proCateAdd2">+添加</h6>
									<input id="pCategory2Checked" type="hidden"/>
								</div>
								
								<div class="propertyCategory col-md-offset-1">
									<h4>规格属性</h4>
									<ul id="pName">
									</ul>
									<h6 id="proNameAdd">+添加</h6>
									<input id="pNameChecked" type="hidden"/>
								</div>
								
								<div class="propertyCategory col-md-offset-1">
									<h4>规格值</h4>
									<ul id="pValue">
									</ul>
									<h6 id="proValueAdd">+添加</h6>
								</div>
							</div>
							
							<br style="clear:both;">
							<%-- <c:forEach var="propertyDto" items="propertyDtos">
								<div id="pnPanel${propertyDto.propertyNameId}"><label class="col-md-2 control-label">&nbsp;</label>
								   <table id="pnTable${propertyDto.propertyNameId}" class="pnTable col-md-offset-1">
									  <tbody>
										 <tr><td><lable class="pnTitle">规格值-${propertyDto.propertyName}<input type="hidden" name="pnId" value="${propertyDto.propertyNameId}"/></lable><a href="javascript:void(0)" onclick="deletePropertyName(this);"><img src="../../img/delete_property.png"></a></td></tr>
										 <c:forEach var="pValue" items="${propertyDto.pValues}">
										 	 <tr><td id="pvtr${pValue.propertyValueId}">${pValue.propertyValue}<input type="hidden" name="pvId" value="${pValue.propertyValueId}"><a href="javascript:void(0)" onclick="deletePropertyValue(this);"><img src="../../img/delete_property.png"></a></td>
										 		<c:forEach var="bgSku" items="${pValue.bgSkus}">
										 			 <td>${bgSku.bgSkuName}<input type="hidden" name="sku${bgSku.bgSkuId}" value="${pValue.propertyValueId}"><a href="javascript:void(0)" onclick="deletePropertySku(this);"><img src="../../img/delete_property.png"></a></td>
										 		</c:forEach>
											 </tr>
										 </c:forEach>
									  </tbody>
								   </table>
								</div>
							</c:forEach> --%>
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
					        	<input type="text" class="form-control request" id="remark" name="remark" value="${bgGoods.remark}" placeholder="商品备注">
					        </div>
						</div>
						
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
										<input type="hidden" id="imageUrl1" name="imageUrl" class="request" value="${bgGoods.imageUrl}" placeholder="商品大图">
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
				 	<!-- <button type="button" class="btn btn-default" data-dismiss="modal" id="save">保存</button> -->
	     			<button type="button" class="btn btn-primary" id="nextButton">下一步</button>
	     		</div>	
	     			
     			<!-- tab第二部分 -->
				<div class="tab-pane" contenteditable="false" id="panel-2">
					<div class="box-content">
						<div id="treeWrap">
							<ul id="selectTree" class="ztree"></ul>
						</div>
					</div>
					<%-- <%@ include file="region_tree_forGoods.jsp"%> --%>
					
					<button type="button" class="btn btn-default" data-dismiss="modal" id="beforeButton">上一步</button>
	     			<button type="button" class="btn btn-primary" id="save2">下一步</button>
				</div>
				
				<!-- tab第三部分 -->
				<div class="tab-pane" contenteditable="false" id="panel-3">
				<!-- 第三部分 -->
					<%@ include file="regionMaxNum.jsp" %>
					<button type="button" class="btn btn-primary" id="nextButton3">完成</button>
				</div>
				
				</div>
			</div>
		</div>
		</div>
	</div>
<%@ include file="../footer.jsp"%>
<%@ include file="add_sku.jsp"%>
<%@ include file="add_property_category.jsp"%>
<%@ include file="add_property_category2.jsp"%>
<%@ include file="add_property_name.jsp"%>
<%@ include file="add_property_value.jsp"%>

<script type="text/javascript">
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

$('#save').click(function(){
	$.post('/goods/multiProduct/edit',$('#editForm').serialize(),function(json){
		if(json.code == 0){
			$('#bgGoodsId').val(json.data);
			alert(json.msg);
		}else{
			alert(json.msg);
		}
	});
});

$('#nextButton').click(function(){
	/* $.post('/goods/multiProduct/edit',$('#editForm').serialize(),function(json){
		if(json.code == 0){
			$('#bgGoodsId').val(json.data);
			$('#a_2').trigger('click');
		}else{
			alert(json.msg);
		}
	}); */
	if(checkParam()){
		$('#a_2').trigger('click');
	}
});

$('#beforeButton').click(function() {
	$('#a_1').trigger('click');
});

$('#save2').click(function() {
	//保存基础信息
	$.post('/goods/multiProduct/edit',$('#editForm').serialize(),function(json){
		if(json.code != 0){
			alert(json.msg);
		}else{
			$('#bgGoodsId').val(json.data);
			//$('#a_2').trigger('click');
			
			//上单，设置范围
			var bgGoodsId = $('#bgGoodsId').val();
			var treeObj=$.fn.zTree.getZTreeObj("selectTree");
			var nodes=treeObj.getNodes();
			var info = JSON.stringify(nodes);
			$.post("/goods/region/setRegion/byBgGoodsId",{"bgGoodsId":bgGoodsId,"treeInfo": info}, function(json){
				if(json.code != 0){
					alert(json.msg);
				}else{
					$('#a_3').trigger('click');
				}
			});
		}
	});
});

$('#nextButton3').click(function() {
	window.location.href="/goods/manager";
});

$('#a_1').click(function() {});

$('#a_2').click(function() {
	var bgGoodsId = $('#bgGoodsId').val();
	/* if(bgGoodsId == ''){
		alert("请首先填写商品信息，并单击保存或下一步");
		return false;
	} */
	var trNum=$('#skuTable tr').length;
	if(trNum < 2){
		alert("商品信息中，必须添加商品");
		return false;
	}
	var bgSkuIds = '';
	$('input[name="bg_sku_id"]').each(function(){
		bgSkuIds += ":" + $(this).val();
	}); 
	//为范围设值
	$.post("/goods/region/getRegion/multi/byBgSkuIds",{"bgGoodsId":bgGoodsId,"bgSkuIds":bgSkuIds}, function(json){
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
		alert("请首先填写商品信息，选择售卖区域，并单击下一步");
		return false;
	}
	//为价格设值
	rdcTable.ajax.url("/goods/price/getRdcStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
	ldcTable.ajax.url("/goods/price/getLdcCity4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
	vmTable.ajax.url("/goods/price/getVmStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
});


$('#addSku').click(function() {
	$("#skuEdit").modal('show');
});


$('#pCategory1').on('click','li',function(){
	$(this).parent().children().each(function(){
		$(this).css({border:'none'});
	});
	$(this).css({border:'1px solid black'});
	var id = $(this).attr('pcId');
	
	
	$('#pName').find("li").remove(); 
	$('#pValue').find("li").remove(); 
	$.post('/goods/property/category/getByPid',{'pid':id},function(json){
		if(json.code == 0){
			var items = json.data;
			var item = null;
			var str = '';
			for(var i = 0;i < items.length; i++){
				item = items[i];
				if(item != null)
				str += '<li pcId="'+item.propertyCategoryId+'">'+item.propertyCategoryName+'</li>';
			}
			$('#pCategory2').find("li").remove(); 
			$('#pCategory2').append(str);
		}else{
			alert(json.msg);
		}
	});
	$('#pCategory2Checked').val('');
	$('#pNameChecked').val('');
	$('#pCategory1Checked').val(id);
});   

$('#pCategory2').on('click','li',function(){
	$(this).parent().children().each(function(){
		$(this).css({border:'none'});
	});
	$(this).css({border:'1px solid black'});
	var id = $(this).attr('pcId');
	
	$('#pValue').find("li").remove(); 
	
	$.post('/goods/property/name/getByCategoryId',{'categoryId':id},function(json){
		if(json.code == 0){
			var items = json.data;
			var item = null;
			var str = '';
			for(var i = 0;i < items.length; i++){
				item = items[i];
				if(item != null)
				str += '<li id="pn'+item.propertyNameId+'" pnId="'+item.propertyNameId+'">'+item.propertyName+'</li>';
			}
			$('#pName').find("li").remove(); 
			$('#pName').append(str);
		}else{
			alert(json.msg);
		}
	});
	$('#pNameChecked').val('');
	$('#pCategory2Checked').val(id);
});   

$('#pName').on('click','li',function(){
	$(this).parent().children().each(function(){
		$(this).css({border:'none'});
	});
	$(this).css({border:'1px solid black'});
	var id = $(this).attr('pnId');
	
	$.post('/goods/property/value/getByNameId',{'nameId':id},function(json){
		if(json.code == 0){
			var items = json.data;
			var item = null;
			var str = '';
			for(var i = 0;i < items.length; i++){
				item = items[i];
				if(item != null)
				str += '<li pvId="'+item.propertyValueId+'">'+item.propertyValue+'</li>';
			}
			$('#pValue').find("li").remove(); 
			$('#pValue').append(str);
		}else{
			alert(json.msg);
		}
	});
	$('#pNameChecked').val(id);
});  

$('#pValue').on('click','li',function(){
	$(this).parent().children().each(function(){
		$(this).css({border:'none'});
	});
	$(this).css({border:'1px solid black'});
	var pnId = $('#pNameChecked').val();
	var pName = $('#pn'+pnId).text();
	var pvId = $(this).attr('pvId');
	var pValue = $(this).text();
	/* 
	表格附加实现方式
	$('#skuTable tr').each(function(){
		var flag = true;
		//判重
		$(this).children().each(function(){
			if($(this).children().attr('val') == id){
				flag = false;
				alert('规格属性已添加');
			}
			return;
		});
		if(flag){
			$(this).append('<td><lable val="'+id+'">'+name+'</lable></td>');
		}
		return false;
	}); */
	var skuTrs = $('#skuTable tr');
	if(skuTrs.length < 2){
		alert('请首先添加商品');
		return;
	}
	var skuStr = '';
	for(var i = 1;i<skuTrs.length;i++){
		var skuTds = skuTrs.eq(i).children();
		skuStr += '<td>'+skuTds.eq(1).text()+'('+skuTds.eq(0).text()+')<input type="hidden" name="sku'+skuTds.eq(3).children().val()+'" value="'+pvId+'"><a href="javascript:void(0)" onclick="deletePropertySku(this);"><img src="../../img/delete_property.png"></a></td>';
	}
	
	if($('#pvtr'+pvId).length == 0){
		if($('#pnTable'+pnId).length == 0){
			var str = '<div id="pnPanel'+pnId+'"><label class="col-md-2 control-label">&nbsp;</label>'
				 +'<table id="pnTable'+pnId+'" class="pnTable col-md-offset-1">'
					+'<tbody>'
						+'<tr><td><lable class="pnTitle">规格值-'+pName+'<input type="hidden" name="pnId" value="'+pnId+'"/></lable><a href="javascript:void(0)" onclick="deletePropertyName(this);"><img src="../../img/delete_property.png"></a></td></tr>'
						+'<tr><td id="pvtr'+pvId+'">'+pValue+'<input type="hidden" name="pvId" value="'+pvId+'"><a href="javascript:void(0)" onclick="deletePropertyValue(this);"><img src="../../img/delete_property.png"></a></td>'+skuStr+'</tr>'
					+'</tbody>'
				 +'</table></div>';
			$('#createProperty').append(str);
		}else{
			var str = '<tr><td id="pvtr'+pvId+'">'+pValue+'<input type="hidden" name="pvId" value="'+pvId+'"><a href="javascript:void(0)" onclick="deletePropertyValue(this);"><img src="../../img/delete_property.png"></a></td>'+skuStr+'</tr>';
			$('#pnTable'+pnId).append(str);
		}
	}else{
		alert('规格值已添加');
		return;
	}
	
	
	
});   

$('#proCateAdd1').click(function(){
	$('#pid').val(0);
	$('#level').val(1);
	$('#propertyCategoryEdit').modal('show');
});

$('#proCateAdd2').click(function(){
	if($('#pCategory1Checked').val() == ''){
		alert('请先选择商品属性');
		return;
	}
	$('#pid2').val($('#pCategory1Checked').val());
	$('#level2').val(2);
	$('#propertyCategoryEdit2').modal('show');
});

$('#proNameAdd').click(function(){
	if($('#pCategory2Checked').val() == ''){
		alert('请先选择商品类型');
		return;
	}
	$('#propertyCategoryId').val($('#pCategory2Checked').val());
	$('#propertyNameEdit').modal('show');
});

$('#proValueAdd').click(function(){
	if($('#pNameChecked').val() == ''){
		alert('请先选择规格属性');
		return;
	}
	$('#propertyNameId').val($('#pNameChecked').val());
	$('#propertyValueEdit').modal('show');
});


/* 属性赋值 */
 
function addProperty2Sku(obj){
	var tr = $(obj).closest('tr');
	var tds = tr.children();
	var pvId = tds.eq(0).attr('id');
	tds.eq(tds.length-1).before('<td>水果<input type="hidden" name="sku+id" value="'+pvId+'"></td>');
	
	
	
	/* tr.children().each(function(){
		alert($(this).attr('id'));
	}); */
	
	/* alert(tds[0].attr('id'));
	for(var i = 0;i<tds.length;i++){
		var tdArr = tds[i].children();
		alert(tdArr[0].text());
	} */
}

function deletePropertyName(obj){
	var pnId = $(obj).prev().children().val();
	$('#pnPanel'+pnId).remove();
}

function deletePropertyValue(obj){
	var pnId = $(obj).parent().parent().remove();
}

function deletePropertySku(obj){
	var pnId = $(obj).parent().remove();
}


$(document).ready(function(){
	var bgGoodsId = $('#bgGoodsId').val();
	if(bgGoodsId != ''){
		$.post('/goods/extend/propertyNameDto/get',{"bgGoodsId":bgGoodsId},function(json){
			if(json.code == 0){
				var propertyDtos = json.data;
				var str = '';
				
				for(var i = 0;i<propertyDtos.length;i++){
					var propertyDto = propertyDtos[i];
					str += '<div id="pnPanel'+propertyDto.propertyNameId+'"><label class="col-md-2 control-label">&nbsp;</label>'
					 +'<table id="pnTable'+propertyDto.propertyNameId+'" class="pnTable col-md-offset-1">'
						+'<tbody>'
							+'<tr><td><lable class="pnTitle">规格值-'+propertyDto.propertyName+'<input type="hidden" name="pnId" value="'+propertyDto.propertyNameId+'"/></lable><a href="javascript:void(0)" onclick="deletePropertyName(this);"><img src="../../img/delete_property.png"></a></td></tr>';
					for(var j = 0;j<propertyDto.pValues.length;j++){
							var pValue = propertyDto.pValues[j];
							str += '<tr><td id="pvtr'+pValue.propertyValueId+'">'+pValue.propertyValue+'<input type="hidden" name="pvId" value="'+pValue.propertyValueId+'"><a href="javascript:void(0)" onclick="deletePropertyValue(this);"><img src="../../img/delete_property.png"></a></td>';
							for(var k = 0;k<pValue.bgSkus.length;k++){
								var bgSku = pValue.bgSkus[k];
								var index = $('#bg_sku_id'+bgSku.bgSkuId).closest('tr').children().eq(0).text();
								str += '<td>'+bgSku.bgSkuName+'('+index+')<input type="hidden" name="sku'+bgSku.bgSkuId+'" value="'+pValue.propertyValueId+'"><a href="javascript:void(0)" onclick="deletePropertySku(this);"><img src="../../img/delete_property.png"></a></td>';
							}
							str += '</tr>';
					}
					str += '</tbody>'
					 +'</table></div>';	
				}
			$('#createProperty').append(str);
		}
		},'json');
	}
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

function modifyBgGoods(bgGoodsId){
	window.location.href='/goods/singleProduct?bgGoodsId='+bgGoodsId;
}
</script>