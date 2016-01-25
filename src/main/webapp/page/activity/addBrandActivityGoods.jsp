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
<style>
.col-sm-goods{
	float:left;
}

#skuinfo,#skuinfo tr,#skuinfo tr td{
	
	border-color: grey;
    border-width: 1px;
    border-style: solid;
	
}
</style>

<%@ include file="../header.jsp"%>

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>

	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i>品牌活动
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
			  <form class="form-horizontal" id="protoForm" role="form"  action="/activitygoods/saveTemplateActivity" enctype="multipart/form-data" method="post">
			    <input type="hidden" name="activityId" id="activityId" value="${activityId }" />
			    <input type="hidden" name="goodsIds" id="goodsIds" value="" />
			    <input type="hidden" name="goodsInfo" id="goodsInfo" value="" />
				<div class="form-group">
			    	<label for="batchId" class="col-sm-2 control-label">选择模板</label>
			        <div class="col-sm-3" style="padding-top:9px;">
						<select	name="activityTemplateId">
			        		<c:forEach var="activityTemplate" items="${activityTemplates}">
								<option value="${activityTemplate.activityTemplateId}" <c:if test="${activityTemplate.activityTemplateId == templateActivity.activityTemplateId}">selected</c:if> >${activityTemplate.templateName}</option>
							</c:forEach>
			        	</select>
			        </div>
				</div>
				
				
				<div class="form-group">
			    	<label for="skuName" class="col-sm-2 control-label">每栏目商品数</label>
			        <div class="col-sm-3" style="padding-top:9px;">
			        	<input type="radio" name="goodsNum" value="2" <c:if test="${2 == templateActivity.goodsNum}">checked</c:if> />2个
			        	<input type="radio" name="goodsNum" value="4" <c:if test="${4 == templateActivity.goodsNum}">checked</c:if> />4个
			        	<input type="radio" name="goodsNum" value="6" <c:if test="${6 == templateActivity.goodsNum}">checked</c:if> />6个
			        </div>
				</div>
				
				<div class="form-group" style="margin-right:50px;float:right;">
					<a href="javascript:addNullCategory();">添加栏目</a>&nbsp;
				<!-- 	<a href="javascript:showGoodsInfoArr();">测试使用</a>   -->
				</div>
			<hr />
			<div id="categoryArea">
				
			</div>
				
				<div class="form-group">
			    	<label for="skuName" class="col-sm-2 control-label">活动规则</label>
			        <div class="col-sm-3" style="padding-top:9px;">
			        	<textarea rows="5" cols="50" id="activityRule" name="activityRule">${templateActivity.activityRule }</textarea>
			        </div>
				</div>
			</form>	
			
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">返回</button>
				<button type="button" class="btn btn-primary" id="btnSaveTemplateActivity">保存</button>
			</div>
	</div>
</div>

<div class="modal fade" id="editGoodsMoal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="height:600px;overflow-y:auto;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="editBrandForm">
							<input type="hidden" name="bgGoodsId" id="bgGoodsId" />
							
							<div class="form-group">
								<label for="exampleInputPassword1">商品类型</label> 
								<select class="form-control" id="storageType" name="storageType" style="width:150px;display:inline;" disabled="disabled" style="width:30%;display:inline;">
										<option value=""></option>
					                	<option value="0">RDC商品</option>
					                	<option value="1" >LDC商品</option>
					                	<option value="2" >第三方商品</option>
					            </select>
							</div>
							
							<div class="form-group">
								<label for="exampleInputPassword1">商品状态</label> 
							<!-- 	<input class="radiobutton" type="radio" name="goodsStatus" value="1" />待售   -->
								<input class="radiobutton" type="radio" name="goodsStatus" value="2" />在售
								<input class="radiobutton" type="radio" name="goodsStatus" value="3" />售罄
							</div>
						
				      		<div id="skuDiv">
				      			<div class="form-group">
								<label for="exampleInputPassword1">价格</label>
				      		
				      		</div>
							
							
							
						</form>
		
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="btnSubmitEditGoods">提交</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../footer.jsp"%>
<%@ include file="goodsChooser.jsp"%>

<script id="addCategoryTemplate" type="text/template">
		<div class="thisisaCategory">
		<div class="form-group">
			    	<label for="skuName" class="col-sm-2 control-label">栏目名称</label>
			        <div class="col-sm-3" style="padding-top:9px; width:50%">
			        	<input type="text" name="categoryName" value="{{categoryName}}" />&nbsp;&nbsp;<a class="delCategory">删除栏目</a>
			        </div>
				</div>
				
				<div class="form-group">
			    	<label for="skuName" class="col-sm-2 control-label">商品</label>
			        <div class="col-sm-3 goodsArea" style="padding-top:9px;width:80%;">
			        	{{{goodsInfo}}}
			        	<a class="addGoods">添加商品</a>
			        	<div style="clear:both"></div>
			        </div>
				</div>
			  <hr />
			 </div>
</script>

<script id="addGoodsTemplate" type="text/template">
	<div class="col-sm-goods aGoods" style="margin-right:15px;">
		<img src="{{{imageUrl}}}" style="height:10%;" />
		<p><a class="editGoods">{{choosedGoodsName}}</a></p>
		<input type="hidden" name="choosedGoodsId" value="{{bgGoodsId}}" />
		<input type="hidden" name="activityBgGoodsId" value="{{activityBgGoodsId}}" />
		<p><a class="delGoods">删除</a></p>
	</div>
</script>

<script id="skuJhpTableTemplate" type="text/template">
		<table border='1' id="skuinfo"><tr class="title"><td>商品名称</td><td>单位</td><td>描述</td><td>原价</td><td>活动价</td></tr>
				{{#bgSkuInfo}}
            	<tr class="skuinfo">
					<td><input type="hidden" name="skuId" value="{{skuId}}" /> {{bgGoodsName}}</td>
					<td> {{unit}}</td>
					<td> {{describe}}</td>
					<td><input type="text" name="originprice" value="{{originPrice}}"/></td>
					<td><input type="text" name="activityprice" value="{{activityPrice}}"/></td>
				</tr>
				{{/bgSkuInfo}}
		</table>
</script>

<script id="skuDpTableTemplate" type="text/template">
		<table border='1' id="skuinfo"><tr class="title"><td>商品名称</td><td>原价</td><td>活动价</td></tr>
				{{#bgSkuInfo}}
            	<tr class="skuinfo">
					<td>{{bgGoodsName}}</td>
					<td><input type="text" id="originprice" value="{{originPrice}}"/></td>
					<td><input type="text" id="activityprice" value="{{activityPrice}}"/></td>
				</tr>
				{{/bgSkuInfo}}
		</table>
</script>

<script type="text/javascript">

var addCategoryTemplate = document.getElementById('addCategoryTemplate').innerHTML;

var addGoodsTemplate = document.getElementById('addGoodsTemplate').innerHTML;

var skuJhpTableTemplate = document.getElementById('skuJhpTableTemplate').innerHTML;

var skuDpTableTemplate = document.getElementById('skuDpTableTemplate').innerHTML;


var $currentCategory;//当前操作的category，用于记录添加商品回填位置

var bgGoodsForActivity = {};

var goodsInfoArr = new Array();

//测试使用
function showGoodsInfoArr() {
	//alert(JSON.stringify(goodsInfoArr));
	console.log(JSON.stringify(goodsInfoArr));
	console.log("size=" + goodsInfoArr.length);
}

//添加空栏目
function addNullCategory() {
	
	var categoryInfo = Mustache.render(addCategoryTemplate);
	$("#categoryArea").append(categoryInfo);
}

//添加栏目
function addCategory(categoryName,goodsIds) {
	var param = {};
	param.categoryName = categoryName;
	
	var goodsInfo = "";
	var length = goodsIds.length;
	for(var i = 0; i < length; i++) {
		var goodsParam = {};
		if(bgGoodsForActivity['info' + goodsIds[i]]) {
			goodsParam.imageUrl = bgGoodsForActivity['info' + goodsIds[i]].imageUrl;
			goodsParam.choosedGoodsName = bgGoodsForActivity['info' + goodsIds[i]].bgGoodsName;
			goodsParam.bgGoodsId = goodsIds[i];
			goodsParam.activityBgGoodsId = bgGoodsForActivity['info' + goodsIds[i]].activityBgGoodsId;
			var eachGoodsInfo = Mustache.render(addGoodsTemplate,goodsParam);
			goodsInfo += (eachGoodsInfo);
		}
	}
	
	param.goodsInfo = goodsInfo;
	
	var categoryInfo = Mustache.render(addCategoryTemplate,param);
	$("#categoryArea").append(categoryInfo);
}


function chooseGoods() {
	
	clearGoodsChooser();
	$("#goodsChooserModal").modal('show');
}

function setParentPageField(bgGoodsInfo,storageTypeEdit,choosedGoodsName,imageUrl) {
	for(var i = 0; i < goodsInfoArr.length; i++) {
		if(goodsInfoArr[i].bgGoodsId == bgGoodsInfo.bgGoodsId) {
			alert("您已经选过该商品了，不允许重复选择！");
			return;
		}
	}
	
	$.post("../activitygoods/validateGoodsCollegeContainsActivityColleges", {
		"bgGoodsId":bgGoodsInfo.bgGoodsId,
		"activityId":$("#activityId").val(),
		"storageType":storageTypeEdit
		}, 
		function(response){
			if (response.code == 0) {
				bgGoodsInfo.storageType = storageTypeEdit;
				bgGoodsInfo.goodsStatus = '2'; //默认添加一个商品为在售状态，可再编辑修改
				bgGoodsInfo.bgGoodsName = choosedGoodsName; //如果是单品组合品，只有这一个商品名称；如果是聚合品，则读取其中sku的
				goodsInfoArr.push(bgGoodsInfo);
				
				var param = {};
				param.imageUrl = imageUrl;
				param.choosedGoodsName = choosedGoodsName;
				param.bgGoodsId = bgGoodsInfo.bgGoodsId;
				param.activityBgGoodsId = '';
				var goodsInfo = Mustache.render(addGoodsTemplate,param);
				$currentCategory.find(".addGoods").before(goodsInfo);
			} else {
				alert(response.msg);
			}
		}
	);
	
	
	/*
	$.post("../activitygoods/addActivityBrandGoods", {
		"activityId":$("#activityId").val(),
		"activityGoodsId":bgGoodsId,
		"originPrice":originPriceEdit,
		"nowPrice":nowPriceEdit,
		"storageType":storageTypeEdit
		}, 
		function(response){
			if (response.code == 0) {
				alert(response.msg);
				var param = {};
				param.imageUrl = imageUrl;
				param.choosedGoodsName = choosedGoodsName;
				param.bgGoodsId = bgGoodsId;
				param.activityBgGoodsId = response.data;
				var goodsInfo = Mustache.render(addGoodsTemplate,param);
				$currentCategory.find(".addGoods").before(goodsInfo);
				//$currentCategory.find(".addGoods").before('<div class="col-sm-goods aGoods" style="margin-right:5px;"><img src="' + imageUrl + '" style="height:10%;" /><p>' + choosedGoodsName + '</p><input type="hidden" name="choosedGoodsId" value="' + bgGoodsId + '" /></div>');
				
			} else {
				alert(response.msg);
			}
		}
	);
	*/
	
}

//统计页面goodsIds
function computeGoodsIds() {
	var goodsIdsArr = new Array();
	$(".thisisaCategory").each(function(){
		var eachCategoryGoodsId = new Array();
		$(this).find("input[name=choosedGoodsId]").each(function(){
			eachCategoryGoodsId[eachCategoryGoodsId.length] = $(this).val();
		});
		goodsIdsArr[goodsIdsArr.length] = eachCategoryGoodsId.join(",");
	});
	return goodsIdsArr.join(";")
}

//清空编辑商品信息框
function clearEditGoods() {
	$("#storageType option:first").attr("selected","selected");
	$("input[name=goodsStatus]").prop("checked",false);
	$("#skuDiv table").remove();
}

var currentJhp;  //记录当前编辑的商品是聚合品还是单品（组合品）

$(document).ready(function(){

	//进入当前页面，如果goodsNum没有默认选中，则默认选中第一个
	var goodsNumCheck = ($("input[name=goodsNum]:checked").size() == 0);
	if(goodsNumCheck) {
		$("input[name=goodsNum]:first").attr("checked","checked");
	}
	
	$(".box-inner").delegate("a.addGoods","click",function(){
		$currentCategory = $(this).parents(".thisisaCategory");
		var alreayNum = $currentCategory.find("div.aGoods").size();
		var shouldNum = $("input[name=goodsNum]:checked").val();
		if(alreayNum >= shouldNum) {
			alert("已达到最大值，不能再添加了！");
			return;
		}
		chooseGoods();
		
	});
	
	
	//删除栏目 
	//在栏目下有商品时不允许删除
	$(".box-inner").delegate("a.delCategory","click",function(){
		if(!confirm("确认要删除该栏目?")) {
			return;
		}
		var goodsSize = $(this).parents(".thisisaCategory").find(".aGoods").size();
		if(goodsSize > 0) {
			alert("该栏目下有商品，不能删除");
			return;
		}
		
		$(this).parents(".thisisaCategory").remove();
	});
	
	
	$(".box-inner").delegate("a.editGoods","click",function(){
		
		/*
		var activityBgGoodsId = $(this).parents(".aGoods").find("input[name=activityBgGoodsId]").val();
		if(activityBgGoodsId == "") {
			alert("找不到要编辑的后台活动商品ID");
			return;
		}
		*/
		
		clearEditGoods();
		
		/*
		$.post("/activitygoods/queryBrandActivity", {
			"activityGoodsId" : activityBgGoodsId,
		}, function(response) {
			if (response.code == 0) {
				$("#activityBgGoodsId").val(activityBgGoodsId);
				$("#originPrice").val(response.data.bgSkuForActivity.originPrice);
				$("#nowPrice").val(response.data.bgSkuForActivity.activityPrice);
				$("#storageType option[value=" + response.data.bgSkuForActivity.distributeType + "]").attr("selected","selected");
			} else {
				alert(response.msg);
			}
		});
		*/
		var choosedGoodsId = $(this).parents(".aGoods").find("input[name=choosedGoodsId]").val();
		$("#bgGoodsId").val(choosedGoodsId);
		//填写编辑商品信息div
		var bgSkuInfo = new Array();
		var storageType = "";
		var goodsStatus = "";
		var isJhp = true; //是否为聚合品
		for(var i = 0; i < goodsInfoArr.length; i++) {
				if(goodsInfoArr[i].bgGoodsId == choosedGoodsId) {
					storageType = goodsInfoArr[i].storageType;
					goodsStatus = goodsInfoArr[i].goodsStatus;
					if(goodsInfoArr[i].skuListBean && goodsInfoArr[i].skuListBean.length > 0) { //聚合品
						for(var j = 0; j < goodsInfoArr[i].skuListBean.length; j++) {
							bgSkuInfo.push({
								"skuId" : goodsInfoArr[i].skuListBean[j].skuId,
								"originPrice" : goodsInfoArr[i].skuListBean[j].originPrice, 
								"activityPrice" : goodsInfoArr[i].skuListBean[j].activityPrice,
								"bgGoodsName" : goodsInfoArr[i].skuListBean[j].bgGoodsName,
								"unit" : goodsInfoArr[i].skuListBean[j].unit,
								"describe" : goodsInfoArr[i].skuListBean[j].describe
							});
						}
					} else {  //单品组合品
						bgSkuInfo.push({
							"originPrice" : goodsInfoArr[i].originPrice,
							"activityPrice" : goodsInfoArr[i].activityPrice,
							"bgGoodsName" : goodsInfoArr[i].bgGoodsName
						});
						isJhp = false;
					}
					break;
				}
		}
		var tableTemplateData = {};
		
		tableTemplateData.bgSkuInfo = bgSkuInfo;
		var editSkuDiv = "";
		if(isJhp) {
			editSkuDiv = Mustache.render(skuJhpTableTemplate, tableTemplateData);
			currentJhp = true;
		} else {
			editSkuDiv = Mustache.render(skuDpTableTemplate, tableTemplateData);
			currentJhp = false;
		}
		
		$("#skuDiv").append(editSkuDiv);
		
		$("select[name=storageType] option[value=" + storageType + "]").attr("selected","selected");
		$("input[name=goodsStatus][value=" + goodsStatus + "]").prop("checked",true);
		
		$("#editGoodsMoal").modal('show');
		
	});
	
	
	$(".box-inner").delegate("a.delGoods","click",function(){
		
			
			var goodsName = $(this).parents(".aGoods").find("p:first").text();
		
			if(!confirm("确定要删除商品"+goodsName+"?")) {
				return;
			}
			
			/*
			var activityBgGoodsId = $(this).parents(".aGoods").find("input[name=activityBgGoodsId]").val();
			if(activityBgGoodsId == "") {
				alert("找不到要删除的后台活动商品ID");
				return;
			}
			*/
			
			
			var bgGoodsId = $(this).parents(".aGoods").find("input[name=choosedGoodsId]").val();
			
			var delSuccess = false;
			for(var i = 0; i < goodsInfoArr.length; i++) {
				if(goodsInfoArr[i].bgGoodsId == bgGoodsId) {
					goodsInfoArr.splice(i,1); //删除第i项--从第i项起，删除1项 <%-- splice使用方法见：http://www.cnblogs.com/Joans/p/3981122.html --%>
					delSuccess = true;
					break;
				}
			}
			
			if(delSuccess) {
				$(this).parents(".aGoods").remove();
			}
			
			
			
			/*
			var $thisHolder = $(this)
			
			$.post("/activitygoods/manager/delete", {
				"activityBgGoodsId" : activityBgGoodsId,
			}, function(response) {
				if (response.code == 0) {
					alert(response.msg);
					$thisHolder.parents(".aGoods").remove();
				} else {
					alert(response.msg);
				}
			});
			*/
		
	});
	
	var activityId = $("#activityId").val();
	if(activityId != "") {
		<c:forEach var="bgGoodsForActivity" items="${bgGoodsForActivitys}">
			bgGoodsForActivity.info${bgGoodsForActivity.bgGoodsId} = {'imageUrl' : '${bgGoodsForActivity.imageUrl}','bgGoodsName' : '${bgGoodsForActivity.bgGoodsName}','activityBgGoodsId' : '${bgGoodsForActivity.activityBgGoodsId}'};
			
			<c:choose>
			   <c:when test="${bgGoodsForActivity.goodsType == 3}">  <!--聚合品 -->
				   <c:if test="${not empty bgGoodsForActivity.bgSkuForActivitys}">
						var skuinfo${bgGoodsForActivity.bgGoodsId} = [];
						<c:forEach var="bgSkuForActivity" items="${bgGoodsForActivity.bgSkuForActivitys}">
							skuinfo${bgGoodsForActivity.bgGoodsId}.push({'skuId':${bgSkuForActivity.bgSkuId},'activityPrice':${bgSkuForActivity.activityPrice / 100.0},'originPrice':${bgSkuForActivity.originPrice / 100.0},'bgGoodsName':'${bgSkuForActivity.bgGoodsName}','unit':'${bgSkuForActivity.unit}','describe':'${bgSkuForActivity.describe}'});
						</c:forEach>
						goodsInfoArr.push({'bgGoodsId':${bgGoodsForActivity.bgGoodsId},'activityPrice':0,'originPrice':0,'storageType':'${bgGoodsForActivity.storageType}','goodsStatus' : '${bgGoodsForActivity.goodsStatus}','skuListBean':skuinfo${bgGoodsForActivity.bgGoodsId}});
					</c:if>
			   </c:when>			     
			   <c:otherwise>  <!-- 单品和组合品 -->
			   		goodsInfoArr.push({'bgGoodsId':${bgGoodsForActivity.bgGoodsId},'activityPrice':${bgGoodsForActivity.bgSkuForActivitys[0].activityPrice / 100.0},'originPrice':${bgGoodsForActivity.bgSkuForActivitys[0].originPrice  / 100.0},'storageType':'${bgGoodsForActivity.storageType}','goodsStatus' : '${bgGoodsForActivity.goodsStatus}','bgGoodsName' : '${bgGoodsForActivity.bgGoodsName}'});
			   </c:otherwise>  
			</c:choose>  
			
			
		</c:forEach>
		
		
		
		var categoryNamesArr = '${templateActivity.categoryNames}'.split(",");
		var goodsIdsArr = '${templateActivity.goodsIds}'.split(";");
		var length = categoryNamesArr.length;
		for(var i = 0; i < length; i++) {
			addCategory(categoryNamesArr[i],goodsIdsArr[i].split(","))
		}
		
		
	} else {
		addNullCategory();
	}
	
	$("#btnSaveTemplateActivity").click(function(){
		var pass = true;
		$("input[name=categoryName]").each(function(){
			if($.trim($(this).val()) == "") {
				pass = false;
				return false;
			}
			if($.trim($(this).val()).indexOf(";") != -1 || $.trim($(this).val()).indexOf(",") != -1) {
				pass = false;
				return false;
			}
		});
		
		if(!pass) {
			alert("栏目名称不能为空且不能包含;,符号");
			return;
		}
		
		var shouldNum = $("input[name=goodsNum]:checked").val();
		
		$(".thisisaCategory").each(function(){
			if($(this).find(".aGoods").size() != shouldNum) {
				pass = false;
				alert("\"" + $(this).find("input[name=categoryName]").val() + "\"栏目数量不为" + shouldNum);
				return false;
			}
		
		});
		
		if(!pass) {
			return;
		}
		
		$("#goodsIds").val(computeGoodsIds());
		
		$("#goodsInfo").val(JSON.stringify(goodsInfoArr));
		
		$("#protoForm").submit();
		
	});
	
	$("#btnSubmitEditGoods").click(function(){
		
		//alert($("#bgGoodsId").val());
		var bgGoodsId = $("#bgGoodsId").val();
		for(var i = 0; i < goodsInfoArr.length; i++) {
			if(goodsInfoArr[i].bgGoodsId == bgGoodsId) {
				goodsInfoArr[i].goodsStatus = $("input[name=goodsStatus]:checked").val();
				if(currentJhp) {
					$("#skuinfo tr:not(:first)").each(function(){
						if($(this).find("input[name=activityprice]").val() > 999999 || $(this).find("input[name=originprice]").val() > 999999) {
							alert("价格字段不能大于999999");
							return false;
						}
						
						var skuId = $(this).find("input[name=skuId]").val();
						if(goodsInfoArr[i].skuListBean && goodsInfoArr[i].skuListBean.length > 0) { //聚合品
							for(var j = 0; j < goodsInfoArr[i].skuListBean.length; j++) {
								if(goodsInfoArr[i].skuListBean[j].skuId == skuId) {
									goodsInfoArr[i].skuListBean[j].activityPrice = $(this).find("input[name=activityprice]").val();
									goodsInfoArr[i].skuListBean[j].originPrice = $(this).find("input[name=originprice]").val();
								}
							}
						}
						
					});		
					
					
				} else {
					//alert($("#activityprice").val());
					//alert($("#originprice").val());
					if($("#activityprice").val() > 999999 || $("#originprice").val() > 999999) {
						alert("价格字段不能大于999999");
						return;
					}
					goodsInfoArr[i].activityPrice = $("#activityprice").val();
					goodsInfoArr[i].originPrice = $("#originprice").val();
				}
				break;
			}
		}
		
		$('#editGoodsMoal').modal('hide');
		
		/*
		$.post("../activitygoods/updateBrandActivity", $("#editBrandForm").serialize(), function(response){
			if (response.code == 0) {
				alert("操作成功");
		        $('#editGoodsMoal').modal('hide');
			} else {
				alert(response.msg);
			}
		});
		*/
		
	});
	
	var code = '${param.code}';
	if(code == "0") {
		alert("保存成功");
	} else {
		var message = '${param.message}';
		if(message != "") {
			alert("以下商品保存失败：" + decodeURIComponent(message));
		} 
	}

});

</script>