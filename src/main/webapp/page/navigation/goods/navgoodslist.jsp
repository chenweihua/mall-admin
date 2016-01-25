<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.enumdata.NavigationType"%>

<%@ include file="../../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<style>
<!--
.control-label {
	width : 10%;
	text-align : right;
	margin-right : 10px;
}
-->
</style>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner">淘精品商品管理</i>
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
					<a href="#"	class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
					<a href="#"	class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">
		        <div class="row">
		        
		        	<div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">商品名称</span> 
			              <input type="text" id="navigationNameQuery" class="form-control" value="${navigationNameQuery}" placeholder="">
			            </div>
			        </div>
			        
			       <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">类目</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="menuQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="menuQuery"  data-val="0">不限</a></li>
			                  <c:forEach items="${navmenulist}" var="navmenu">
			                  		<li><a href="javascript:void(0);" class="menuQuery"  data-val="${navmenu.navMenuId}">${navmenu.showName}</a></li>
			                  </c:forEach>
			                </ul>
			              </div>
			            </div>
			         </div>
			          
			        <div class="col-lg-2" id="isOpenDiv">
			            <div class="input-group">
			              <span class="input-group-addon">状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="statusQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="isOpen">
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="-1">不限</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="0">关闭</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="1">开启</a></li>
			                </ul>
			              </div>
			            </div>
			         </div>
		        			          
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
					
					</div>
				
		        <br />
        		<br />
				<div class="tabbable" id="tabs-677904">
				<ul class="nav nav-tabs">
					<li class="active"><a id="a_1" contenteditable="false" data-toggle="tab" href="#panel-1">选品池</a></li>
					<li id="tab_2"><a id="a_2" contenteditable="false" data-toggle="tab" href="#panel-2">售卖池</a></li>
				</ul>
				
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="panel-1">
						<table id="selectGoodsPool" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
							<thead>
								<tr>
										<th style="width: 5%"><input type="checkbox" id="selectPoolBatchCheck" /></th>
										<th style="width: 5%">商品ID</th>
										<th style="width: 5%">商品名称</th>
										<th style="width: 5%">淘宝名称</th>
										<th style="width: 5%">商品价格</th>
										<th style="width: 5%">优惠价格</th>
										<th style="width: 5%">图片</th>
										<th style="width: 5%">所属类目</th>
										<th style="width: 5%">添加时间</th>
										<th style="width: 5%">操作</th>
								</tr>
							</thead>
						</table>
						<button type="button" class="btn btn-primary btn-sm" id="addButton">添加商品</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary btn-sm" id="moveToSaleBatchButton">批量售卖</button>
					</div>
					
					<!-- tab第二部分 -->
					<div class="tab-pane" contenteditable="false" id="panel-2">
						<table id="saleGoodsPool" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
							<thead>
								<tr>
										<th style="width: 5%"><input type="checkbox" id="salePoolBatchCheck" /></th>
										<th style="width: 5%">商品ID</th>
										<th style="width: 5%">商品名称</th>
										<th style="width: 5%">淘宝名称</th>
										<th style="width: 5%">商品价格</th>
										<th style="width: 5%">优惠价格</th>
										<th style="width: 5%">图片</th>
										<th style="width: 5%">所属类目</th>
										<th style="width: 5%">添加时间</th>
										<th style="width: 5%">开启状态</th>
										<th style="width: 5%">操作</th>
								</tr>
							</thead>
						</table>
						<button type="button" class="btn btn-primary btn-sm" id="moveToSelectBatchButton">批量选品</button>
					</div>
					
				</div>
			</div>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->
<!--/row-->


<%@ include file="addnavgoods.jsp"%>

<%@ include file="../../footer.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
/**查询类型 1：查询选品池，2：查询售卖池*/
var searchType=1;
var isOpen=-1;
var navMenuId=0;
var selectGoodsPool;
var saleGoodsPool;
var selectNavGoodsList;
$("#isOpenDiv").hide();

$('.statusQuery').click(function() {
	isOpen = $(this).data("val");
	$('#statusQueryText').html($(this).text());
});

$('.menuQuery').click(function() {
	navMenuId = $(this).data("val");
	$('#menuQueryText').html($(this).text());
});

$("#a_1").click(function(){
	searchType=1;
	$("#isOpenDiv").hide();
});

$("#a_2").click(function(){
	searchType=2;
	$("#isOpenDiv").show();
});

$("#btnSubmit").click(function(){
	if(searchType==1){
		selectGoodsPool.ajax.reload(null,true);
	}else{
		saleGoodsPool.ajax.reload(null,true);
	}
});

$(document).ready(function() {
	selectGoodsPool = $('#selectGoodsPool').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "query",
        	data: function(d) {
                d.navGoodsName = $("#navigationNameQuery").val();
                d.goodsStatus = 1;
                d.navMenuId=navMenuId;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		selectNavGoodsList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var navGoods = json.data[i];
        			newData[newData.length] = [
        			                           			'<input type="checkbox" name="selectGoodsCheck" value="' + navGoods.navGoodsId + '" />',
        									   			navGoods.goodsId,
        									   			navGoods.navGoodsName,
        									   			navGoods.goodsName,
        									   			navGoods.reservePrice/100.0,
        									   			navGoods.zkFinalPrice/100.0,
        									   			'<img src="'+navGoods.imageUrl+'" height="100" width="100"></img>',
        									   			navGoods.navMenuName,
        									   			new Date(navGoods.createTime).Format("yyyy-MM-dd"),
        			                           '<a href="javascript:edit(' + i + ');">编辑</a>&nbsp;&nbsp;<a href="javascript:deleteNavGoods('+ navGoods.navGoodsId +');">删除</a>&nbsp;&nbsp;<a href="javascript:movsalepool('+ navGoods.navGoodsId +');">添加至售卖</a>'
        			                          ]; 
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...", 
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页"

            }
                                           
        }
    } );

	saleGoodsPool = $('#saleGoodsPool').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "query",
        	data: function(d) {
                d.navGoodsName = $("#navigationNameQuery").val();
                d.goodsStatus = 2;
                d.navMenuId=navMenuId;
                d.isOpen=isOpen;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var navGoods = json.data[i];
        			newData[newData.length] = [
												'<input type="checkbox" name="saleGoodsCheck" value="' + navGoods.navGoodsId + '" />',
									   			navGoods.goodsId,
									   			navGoods.navGoodsName,
									   			navGoods.goodsName,
									   			navGoods.reservePrice/100.0,
									   			navGoods.zkFinalPrice/100.0,
									   			'<img src="'+navGoods.imageUrl+'" height="100" width="100"></img>',
									   			navGoods.navMenuName,
									   			new Date(navGoods.createTime).Format("yyyy-MM-dd"),
									   			navGoods.isOpen==0?'关闭&nbsp;&nbsp;<a href="javascript:setopen(' + navGoods.navGoodsId + ',1);">开启</a>':'开启&nbsp;&nbsp;<a href="javascript:setopen(' + navGoods.navGoodsId + ',0);">关闭</a>',
			                         			'<a href="javascript:deleteNavGoods('+ navGoods.navGoodsId +');">删除</a>&nbsp;&nbsp;<a href="javascript:moveSelectPool('+ navGoods.navGoodsId +');">移到选品池</a>'
			                          ]; 
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...", 
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页"

            }
                                           
        }
    } );
	
	
	$("#selectPoolBatchCheck").click(function(){
		
		$("input[name=selectGoodsCheck]").prop("checked",this.checked);
	
	});
	
	$("#salePoolBatchCheck").click(function(){
		
		$("input[name=saleGoodsCheck]").prop("checked",this.checked);
	
	});
	
	$("#moveToSaleBatchButton").click(function(){
		
		var checkIds = "";
		$("input[name=selectGoodsCheck]:checked").each(function(){
			
			checkIds += ($(this).val() + ",");
			
		});
		
		if(checkIds != "") {
			checkIds = checkIds.substring(0,checkIds.length - 1);
		} else {
			alert("请至少选择一项");
			return;
		}
		
		$.post("moveToSaleBatch",{"navGoodsIds":checkIds}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("批量移动到售卖池成功~");
				selectGoodsPool.ajax.reload(null,false);
				saleGoodsPool.ajax.reload(null,false);
	     	 }
		},"json");
	});
	
	$("#moveToSelectBatchButton").click(function(){
		
		
		var checkIds = "";
		$("input[name=saleGoodsCheck]:checked").each(function(){
			
			checkIds += ($(this).val() + ",");
			
		});
		
		if(checkIds != "") {
			checkIds = checkIds.substring(0,checkIds.length - 1);
		} else {
			alert("请至少选择一项");
			return;
		}
		
		$.post("moveToSelectBatch",{"navGoodsIds":checkIds}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("批量移动到选品池成功~");
				selectGoodsPool.ajax.reload(null,false);
				saleGoodsPool.ajax.reload(null,false);
	     	 }
		},"json");
		
	});
	
	
} );
/**
 * 添加商品
 */
$('#addButton').click(function () {
	$("#navGoodsId").val("-1");
	$("#navGoodsName").val("");
	$("#navMenuId").val("-1");
	$("#itemUrl").val("");
	$("#clickUrl").val("");
	$("#weight").val("");
	$('#modalEdit').modal();
});

function edit(index){
	var navGoods = selectNavGoodsList[index];
	$("#navGoodsId").val(navGoods.navGoodsId);
	$("#navGoodsName").val(navGoods.navGoodsName);
	$("#navMenuId").val(navGoods.navMenuId);
	$("#itemUrl").val(navGoods.itemUrl);
	$("#clickUrl").val(navGoods.clickUrl);
	$("#weight").val(navGoods.weight);
	$('#modalEdit').modal();
}

/**
 * 删除商品
 */
function deleteNavGoods(navGoodsId){
	$.post("delete",{"navGoodsId":navGoodsId}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("删除成功~");
			if(searchType==1){
				selectGoodsPool.ajax.reload(null,false);
			}else{
				saleGoodsPool.ajax.reload(null,false);
			}
     	 }
	},"json"); 
}

/***
 * 将商品转移到售卖池中
 */
function movsalepool(navGoodsId){
	$.post("movsalepool",{"navGoodsId":navGoodsId}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("转移成功~");
			selectGoodsPool.ajax.reload(null,false);
			saleGoodsPool.ajax.reload(null,false);
     	 }
	},"json"); 
}

/**
 * 将商品移动到选品池中
 */
function moveSelectPool(navGoodsId){
	$.post("moveSelectPool",{"navGoodsId":navGoodsId}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("转移成功~");
			selectGoodsPool.ajax.reload(null,false);
			saleGoodsPool.ajax.reload(null,false);
     	 }
	},"json"); 
}

/**
 * 设置商品的开启和关闭状态
 */
function setopen(navGoodsId,isOpen){
	$.post("setopen",{"navGoodsId":navGoodsId,"isOpen":isOpen}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("修改成功~");
			saleGoodsPool.ajax.reload(null, false);
     	 }
	},"json"); 
}
</script>