<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.enumdata.MerchantStatus"%>

<c:set var="MerchantStatus" value="<%=MerchantStatus.values()%>"/>

<%@ include file="../../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
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
					<i class="glyphicon glyphicon-banner">订单管理</i>
				</h2>
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
					<a href="#"	class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
					<a href="#"	class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="tabbable" id="tabs-677904">
				<ul class="nav nav-tabs">
					<li class="active"><a id="a_1" contenteditable="false" data-toggle="tab" href="#panel-1">订单记录</a></li>
					<li ><a id="a_2" contenteditable="false" data-toggle="tab" href="#panel-2">财务汇总</a></li>
				</ul>
				<br>
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="panel-1">
				        	<div class="row" style="margin-bottom:20px ">
					        	<div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">选择商户</span> 
						              <input type="text" id="merchantNames" class="form-control" value="${merchant.merchantName}" placeholder=""  onfocus="checkMerchant(1)" readonly="readonly">
						              <input type="hidden" id="merchantNos"  value="${merchant.merchantNo}">
						            </div>
						        </div>
						        <div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">单号</span> 
						              <input type="text" id="orderCode" class="form-control" value="" placeholder="单号精确查询">
						            </div>
						        </div>
						     </div>
						     <div class="row">
						        <div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">订单开始时间：</span> 
						              <input type="text" id="beginTime" class="form-control" value="" placeholder="" readonly="readonly">
						            </div>
						        </div>
						        <div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">订单结束时间：</span> 
						              <input type="text" id="endTime" class="form-control" value="" placeholder="" readonly="readonly">
						            </div>
						        </div>
						        <div class="col-lg-3">
						            <div class="input-group">
							              <button type ="button" onclick="gettoday(0)" >今天</button>
							              <button type ="button" onclick="getyestoday(0)" >昨天</button>
							              <button type ="button" onclick="get7day(0)" >最近7天</button>
							              <button type ="button" onclick="get30day(0)" >最近30天</button>
						            </div>
						        </div>
					          	<div class="col-lg-2">
						            <div class="input-group">
						              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
						              <button type="button" id="exportOrder" class="btn btn-default">导出</button>
						            </div>
					          	</div>
							</div>
						
					        <br />
			        		<br />
							<div class="tabbable" id="tabs-677904">
								<div class="tab-content">
									<div class="tab-pane active" contenteditable="false" id="merchatListDiv">
									<table id="orderList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
										<thead>
											<tr>
													<th style="width: 5%">入账时间</th>
													<th style="width: 5%">订单号</th>
													<th style="width: 5%">商户名称</th>
													<th style="width: 5%">支付方式</th>
													<th style="width: 5%">应收金额</th>
													<th style="width: 5%">实收金额</th>
													<th style="width: 5%">状态</th>
											</tr>
										</thead>
									</table>
								</div>
								
								</div>
							</div>
						</div>
						<div class="tab-pane" contenteditable="false" id="panel-2">
							<div class="row" style="margin-bottom:20px ">
					        	<div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">选择商户</span> 
						              <input type="text" id="merchantNames_all" class="form-control" value="${merchant.merchantName}" placeholder="" onfocus="checkMerchant(2)" readonly="readonly">
						              <input type="hidden" id="merchantNos_all"  value="${merchant.merchantNo}">
						            </div>
						        </div>
						     </div>
						     <div class="row">
						        <div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">订单开始时间：</span> 
						              <input type="text" id="beginTime_all" class="form-control" value="" placeholder="" readonly="readonly">
						            </div>
						        </div>
						        <div class="col-lg-3">
						            <div class="input-group">
						              <span class="input-group-addon">订单结束时间：</span> 
						              <input type="text" id="endTime_all" class="form-control" value="" placeholder="" readonly="readonly">
						            </div>
						        </div>
						        <div class="col-lg-3">
						            <div class="input-group">
							              <button type ="button" onclick="gettoday(1)" >今天</button>
							              <button type ="button" onclick="getyestoday(1)" >昨天</button>
							              <button type ="button" onclick="get7day(1)" >最近7天</button>
							              <button type ="button" onclick="get30day(1)" >最近30天</button>
						            </div>
						        </div>
					          	<div class="col-lg-2">
						            <div class="input-group">
						              <button type="button" id="btnSubmit_all" class="btn btn-default">搜索</button>
						              <button type="button" id="exportSummaryOrder" class="btn btn-default">导出</button>
						            </div>
					          	</div>
							</div>
						
					        <br />
			        		<br />
							<div class="tabbable" id="tabs-677904">
								<div class="tab-content">
									<div class="tab-pane active" contenteditable="false" id="merchatListDiv">
									<table id="summaryOrderList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
										<thead>
											<tr>
													<th style="width: 5%">商户名称</th>
													<th style="width: 5%">日期</th>
													<th style="width: 5%">应收总金额</th>
													<th style="width: 5%">实付总金额</th>
													<th style="width: 5%">交易笔数</th>
													<th style="width: 5%">操作</th>
											</tr>
										</thead>
									</table>
								</div>
								
								</div>
							</div>
						
						</div>
					</div>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->
<!--/row-->

<c:if test="${userflag==1}">
<%@ include file="checkmerchant.jsp"%>
</c:if>


<%@ include file="../../footer.jsp"%>

<script>

$('#beginTime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:00'
});

$('#endTime').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'yyyy-mm-dd hh:ii:00'
  });
  
$('#beginTime_all').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    minView:"day",
    format: 'yyyy-mm-dd'
});

$('#endTime_all').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      minView:"day",
      format: 'yyyy-mm-dd'
  });

var userFlag=${userflag};
//设置ajax同步执行
$.ajaxSetup({
	  async: false
});

var status=0;
var table;
var table_all;

var searchType=1;


$("#btnSubmit").click(function(){
		table.ajax.reload(null,true);
});
$("#btnSubmit_all").click(function(){
		table_all.ajax.reload(null,true);
});

$(document).ready(function() {
	
	var beginTime = new Date().Format("yyyy-MM-dd");
	var endTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
	
	$("#beginTime").val(beginTime+" 00:00:00");
	$("#endTime").val(endTime);
	
	var beginTime_all = new Date().Format("yyyy-MM-dd");
	var endTime_all = new Date().Format("yyyy-MM-dd");	
	$("#beginTime_all").val(beginTime_all);
	$("#endTime_all").val(endTime_all);
	
	table = $('#orderList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "queryorder",
        	data: function(d) {
                d.merchantNoList = $("#merchantNos").val();
                d.beginTime = $("#beginTime").val();
                d.endTime = $("#endTime").val();
                d.orderCode = $("#orderCode").val();
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var order = json.data[i];
					var statusStr = "";
					var payType = order.onlinePayType;
					var payTypeStr = "未知";
					if(payType==1){
						payTypeStr="微信";
					}else if(payType==2){
						payTypeStr="支付宝";
					}else if(payType=="3"){
						payTypeStr="余额支付";
					}
					var status = order.status;
					var statusStr="未知";
					if(status==5){
						statusStr="支付成功";
					}else if(status==6){
						statusStr="退款中";
					}else if(status==7){
						statusStr="退款完成";
					}
					
					newData[newData.length] = [
        									   			new Date(order.createTime).Format("yyyy-MM-dd hh:mm:ss"),
        									   			order.orderCode,
        									   			order.merchantName,
        									   			payTypeStr,
        									   			order.totalPay/100.0,
        									   			order.onlinePay/100.0,
        									   			statusStr
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
	
	table_all = $('#summaryOrderList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "querysummaryorder",
        	data: function(d) {
                d.merchantNoList = $("#merchantNos_all").val();
                d.beginTime = $("#beginTime_all").val();
                d.endTime = $("#endTime_all").val();
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var summaryOrderInfo = json.data[i];
        			var recordDate = new Date(	summaryOrderInfo.recordDate).Format("yyyy-MM-dd");
        			var operator ="";
        			if(summaryOrderInfo.sumTotalPay>0){
        				operator = '<a href="javascript:exprotDetail(\''+ summaryOrderInfo.merchantNo+'\',\''+recordDate+' 00:00:00\',\''+recordDate +' 23:59:59\',\'\');">详情</a>';
        			}
        			
					newData[newData.length] = [
														summaryOrderInfo.merchantName,
														recordDate,
        									   			summaryOrderInfo.sumTotalPay/100.0,
        									   			summaryOrderInfo.sumOnlinePay/100.0,
        									   			summaryOrderInfo.sumCount,
        									   			operator
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

function checkMerchant(flag){
	
	if(userFlag==0){
		return;
	}
	
	isAllCheck=false;
	
	$("#checkAll").prop("checked",false);

	$("input[name='mechantCheck']").each(function(){
//		   $(this).prop("checked",false);
		   $(this).removeAttr("disabled");
		   
	});
	
	searchType=flag;
	$("#modalEdit").modal();
}

function gettoday(type){
	setDate(0,type);
}
function getyestoday(type){
	setDate(1,type);
}
function get7day(type){
	setDate(7,type);
}
function get30day(type){
	setDate(30,type);
}

function setDate(day,type){
	var beginTimeStr = new Date().getTime()-day*24*60*60*1000;
	var beginTimeDate = new Date(beginTimeStr).Format("yyyy-MM-dd");
	if(type==0){
		$("#beginTime").val(beginTimeDate+" 00:00:00");
	}
	if(type==1){
		$("#beginTime_all").val(beginTimeDate);
	}
}

$("#exportOrder").click(function(){
    var merchantNoList = $("#merchantNos").val();
    var beginTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    var orderCode = $("#orderCode").val();
/* 	//$.post("exportorder",{"merchantNoList":merchantNoList,"beginTime":beginTime,"endTime":endTime,"orderCode":orderCode});
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","exportorder");
	
	var input1=$("<input>");
	input1.attr("type","hidden");
	input1.attr("name","merchantNoList");
	input1.attr("value",merchantNoList);
	
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","beginTime");
	input2.attr("value",beginTime);
	
	var input3=$("<input>");
	input3.attr("type","hidden");
	input3.attr("name","endTime");
	input3.attr("value",endTime);
	
	var input4=$("<input>");
	input4.attr("type","hidden");
	input4.attr("name","orderCode");
	input4.attr("value",orderCode);
	
	$("body").append(form);//将表单放置在web中
	form.append(input1);
	form.append(input2);
	form.append(input3);
	form.append(input4);
	form.submit();//表单提交  */
	
	exprotDetail(merchantNoList,beginTime,endTime,orderCode)
});


$("#exportSummaryOrder").click(function(){
     var merchantNoList = $("#merchantNos_all").val();
    var beginTime = $("#beginTime_all").val();
    var endTime = $("#endTime_all").val();
    //$.post("exportorder",{"merchantNoList":merchantNoList,"beginTime":beginTime,"endTime":endTime,"orderCode":orderCode});
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","exportsummaryorder");
	
	var input1=$("<input>");
	input1.attr("type","hidden");
	input1.attr("name","merchantNoList");
	input1.attr("value",merchantNoList);
	
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","beginTime");
	input2.attr("value",beginTime);
	
	var input3=$("<input>");
	input3.attr("type","hidden");
	input3.attr("name","endTime");
	input3.attr("value",endTime);
	
	$("body").append(form);//将表单放置在web中
	form.append(input1);
	form.append(input2);
	form.append(input3);
	form.submit();//表单提交  
	
});

function exprotDetail(merchantNoList,beginTime,endTime,orderCode){

	//$.post("exportorder",{"merchantNoList":merchantNoList,"beginTime":beginTime,"endTime":endTime,"orderCode":orderCode});
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","exportorder");
	
	var input1=$("<input>");
	input1.attr("type","hidden");
	input1.attr("name","merchantNoList");
	input1.attr("value",merchantNoList);
	
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","beginTime");
	input2.attr("value",beginTime);
	
	var input3=$("<input>");
	input3.attr("type","hidden");
	input3.attr("name","endTime");
	input3.attr("value",endTime);
	
	var input4=$("<input>");
	input4.attr("type","hidden");
	input4.attr("name","orderCode");
	input4.attr("value",orderCode);
	
	$("body").append(form);//将表单放置在web中
	form.append(input1);
	form.append(input2);
	form.append(input3);
	form.append(input4);
	form.submit();//表单提交
	return;
}
</script>