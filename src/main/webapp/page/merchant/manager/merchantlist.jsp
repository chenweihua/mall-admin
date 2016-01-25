<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.enumdata.MerchantStatus"%>

<c:set var="MerchantStatus" value="<%=MerchantStatus.values()%>"/>

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
					<i class="glyphicon glyphicon-banner">商户管理</i>
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
			              <span class="input-group-addon">商户名称</span> 
			              <input type="text" id="merchantNameSearch" class="form-control" value="" placeholder="">
			            </div>
			        </div>
			        
			        <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">联系人</span> 
			              <input type="text" id="shopOwnerSearch" class="form-control" value="${navigationNameQuery}" placeholder="">
			            </div>
			        </div>
			        
			       <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">商户状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="statusQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="merchantStatus">
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="0">不限</a></li>
			                  <c:forEach items="${MerchantStatus}" var="merchantStatus">
			                  		<li><a href="javascript:void(0);" class="statusQuery"  data-val="${merchantStatus.type}">${merchantStatus.name}</a></li>
			                  </c:forEach>
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
				
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="merchatListDiv">
						<table id="merchantList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
							<thead>
								<tr>
										<th style="width: 5%">商户名称</th>
										<th style="width: 5%">商户类型</th>
										<th style="width: 5%">商户ID</th>
										<th style="width: 5%">联系人</th>
										<th style="width: 5%">联系电话</th>
										<th style="width: 5%">商户状态</th>
										<th style="width: 5%">操作</th>
								</tr>
							</thead>
						</table>
						<button type="button" class="btn btn-primary btn-sm" id="addButton">添加商户</button>
					</div>
					
				</div>
			</div>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->
<!--/row-->


<%@ include file="addmerchant.jsp"%>

<%@ include file="../../footer.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
//设置ajax同步执行
$.ajaxSetup({
	  async: false
});

var status=0;
var table;

var merchantList;

$('.statusQuery').click(function() {
	status = $(this).data("val");
	$('#statusQueryText').html($(this).text());
});

$("#btnSubmit").click(function(){
	table.ajax.reload(null,true);
});

$(document).ready(function() {
	table = $('#merchantList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "query",
        	data: function(d) {
                d.merchantname = $("#merchantNameSearch").val();
                d.shopowner = $("#shopOwnerSearch").val();
                d.status=status;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		merchantList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var merchant = json.data[i];
					var statusStr = "";
					if(merchant.status==1){
						statusStr="使用中【"+ '<a href="javascript:setstatus(' + merchant.merchantId + ',2);">关闭】</a>'
					}else{
						statusStr="停用中【"+ '<a href="javascript:setstatus(' + merchant.merchantId + ',1);">开启】</a>'
					}
        			newData[newData.length] = [
        									   			merchant.merchantName,
        									   			merchant.merchantTypeStr,
        									   			merchant.merchantNo,
        									   			merchant.shopOwner,
        									   			merchant.ownerPhone,
        									   			statusStr,
        			                           			'<a href="getdetail?merchantId='+merchant.merchantId+'">详情</a>&nbsp;&nbsp;<a href="javascript:edit('+ i +');">编辑</a>'
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
	
} );

/**
 * 设置商品的开启和关闭状态
 */
function setstatus(merchantId,status){
	$.post("setstatus",{"merchantId":merchantId,"status":status}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("修改成功~");
			table.ajax.reload(null, false);
     	 }
	},"json"); 
}

function edit(index){
	var merchant = merchantList[index];
	$("#merchantId").val(merchant.merchantId);
	$("#merchantName").val(merchant.merchantName);
	
	$("#merchantAddr").val(merchant.merchantAddr);
	$("#shopOwner").val(merchant.shopOwner);
	$("#ownerPhone").val(merchant.ownerPhone);
	$("#merchantEmail").val(merchant.merchantEmail);
	$("#merchantAccount").val(merchant.merchantAccount);
	
	$("#bankAccount").val(merchant.bankAccount);
	$("#bankName").val(merchant.bankName);
	$("#branchName").val(merchant.branchName);
	$("#bankUserName").val(merchant.bankUserName);
	$("#merchantCityId").val(merchant.merchantCityId);
	referArea(merchant.merchantCityId);
	$("#merchantAreaId").val(merchant.merchantAreaId);
	$("#merchantNo").val(merchant.merchantNo);
	$("#mechantPayAddr").val(merchant.mechantPayAddr);
	
	$("#merchantName").attr("readonly","readonly")//将input元素设置为readonly
	$("#merchantAccount").attr("readonly","readonly")//将input元素设置为readonly
	
	$('#modalEdit').modal();
}

$("#addButton").click(function(){
	
	$("#merchantId").val("0");
	$("#merchantName").val("");
	$("#merchantAddr").val("");
	$("#shopOwner").val("");
	$("#ownerPhone").val("");
	$("#merchantEmail").val("");
	$("#merchantAccount").val("");
	$("#bankAccount").val("");
	$("#bankName").val("");
	$("#branchName").val("");
	$("#bankUserName").val("");
	var cityId = $("#merchantCityId option:first").val();
	$("#merchantCityId").val(cityId);
	
	$("#merchantName").removeAttr("readonly")//去掉input元素的readonly属性
	$("#merchantAccount").removeAttr("readonly")//去掉input元素的readonly属性
	
	referArea(cityId);
	getMerchantNo();
	
	$('#modalEdit').modal();
});

/**
 * 刷新地区选择栏
 */
function referArea(cityId){
	$("#merchantAreaId").empty();
	$.post('/city/json/secCityList',{"cityId":cityId},function(ret){
		 if (ret.code != 0) {
           alert(ret.msg);
       }else {	
			var areaList = ret.data.secCityListInCity;
			for(var i=0;i<areaList.length;i++){
				var area = areaList[i];
				$("#merchantAreaId").append("<option value='"+area.cityId+"'>"+area.cityName+"</option>");
			}
   	 }
	},"json")
}

function getMerchantNo(){
	$.post("getmerchantno",function(ret){
		if(ret.code!=0){
			alert("获取商户ID失败~");
		}else{
			var merchantNo = ret.data.merchantNo;
			var merchantPayAddr = ret.data.mechantPayAddr;
			$("#merchantNo").val(merchantNo);
			$("#mechantPayAddr").val(merchantPayAddr);
		}
	},"json");
}
</script>