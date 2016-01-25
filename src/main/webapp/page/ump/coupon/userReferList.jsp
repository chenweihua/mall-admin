<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i> 个人信息查询
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
		        <div  class="row">
			          
			           <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">手机号</span> <input type="text"
			                id="phone" class="form-control" value="${phone}" placeholder="">
			            </div>
			          </div>
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
		        </div>
				
        		用户信息
        		<table id="userList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>用户名</th>
							<th>手机号</th>
							<th>用户id</th>
							<th>openId</th>
						</tr>
					</thead>
				</table>
        		
        		用户优惠券使用情况
				<table id="batchList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>批次号</th>
							<th>名称</th>
							<th>发行总额</th>
							<th>发行面值</th>
	             			<th>已发行数</th>
							<th>平台</th>
							<th>状态</th>
							<th>有效日期</th>
							<th>活动id</th>
							<th>限制条件</th>
							<th>发放人id</th>
							<th>订单id</th>
							<th>来源</th>
							<th>学校id</th>
							<th>总付款</th>
							<th>线上付款</th>
							<th>订单状态</th>
							<th>订单时间</th>
							<th>类目id</th>
							<th>商品名称</th>
						</tr>
					</thead>
				</table>
				
				用户邀请的人使用优惠券情况
				<table id="rewardsList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>批次号</th>
							<th>名称</th>
							<th>发行总额</th>
							<th>发行面值</th>
	             			<th>已发行数</th>
							<th>平台</th>
							<th>状态</th>
							<th>有效日期</th>
							<th>活动id</th>
							<th>限制条件</th>
							<th>发放人id</th>
							<th>订单id</th>
							<th>来源</th>
							<th>学校id</th>
							<th>总付款</th>
							<th>线上付款</th>
							<th>订单状态</th>
							<th>订单时间</th>
							<th>类目id</th>
							<th>商品名称</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	</div>


<%@ include file="../../footer.jsp"%>
<%@ include file="../../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>

  
var typeMap = {
		0:"",
		1:"",
		2:""
}

var platformTypeMap = {
		0: "全部",
		1: "APP",
		2: "H5"
	};
	
var platformTypeDescMap = {
		0: "全平台通用",
		1: "仅APP可用",
		2: "仅H5商城可用"
	};
	
var deliverTypeMap = {
		"0": "全部",
		"1": "发送到手机",
		"2": "发送到区域"
		/*
		3: "区域发送",
		4: "用户自领"
		*/
	};
	
var statusMap = {
		0: "全部",
		1: "新建",
		2: "领取中",
		3: "领取完",
		4: "领取结束",
		5: "锁定"
	};
var payStatusMap = {
		1: "未支付",
		2: "订单失败",
		3: "订单超时",
		4: "支付中",
		5: "支付成功"
	};
var sourceMap = {
		1: "邀请",
		2: "裂变",
		3: "系统发",
		4: "老批次",
		5: "普通 ",
		6: "推广 ",
		7: "推广文章"
	};

var table = null;
var urlPrefix = "";
var couponBatchList ;
var userList;
var rewardsList;

//提交搜索条件
$('#btnSubmit').click(function(){
	var phone = $("#phone").val();
	if(!checkInteger(phone)&&$.trim(phone).length != 11){
		alert("手机号不正确");
		return ;
	}
	$.post(urlPrefix+"getUserAllByPhone"+"?phone="+phone, function(json){
		couponBatchList = json.batchList;
		userList = json.user;
		rewardsList = json.rewardsList;
		$("#userList tr:gt(0)").remove();
		$("#batchList tr:gt(0)").remove();
		$("#rewardsList tr:gt(0)").remove();
		$("#userList").append('<tr><td>' + userList.realname + '</td><td>' + userList.mobilephone + '</td>'+
				'<td>' + userList.id + '</td><td>' + userList.openId + '</td></tr>');
		for (var i=0, len = couponBatchList.length; i<len; i++) {
			var couponBatch = couponBatchList[i];
			var timeMsg="";
			var startDate = new Date();
			var endDate = new Date();
			startDate.setTime(couponBatch.startTime);
			endDate.setTime(couponBatch.endTime);
			
			var timeMsg1="";
			var startDate1 = new Date();
			var endDate1 = new Date();
			startDate1.setTime(couponBatch.payCreateTime);
			endDate1.setTime(couponBatch.payUpdateTime);
			if(couponBatch.startTime && couponBatch.endTime) {
				timeMsg =startDate.Format("yyyy-MM-dd") + "/" + endDate.Format("yyyy-MM-dd");	
			}
			
			if(couponBatch.payCreateTime && couponBatch.payUpdateTime) {
				timeMsg1 =startDate1.Format("yyyy-MM-dd") + "/" + endDate1.Format("yyyy-MM-dd");	
			}
			
			$("#batchList").append('<tr><td>' + couponBatch.batchId + '</td><td>' + couponBatch.batchName + '</td>'+
					'<td>' + couponBatch.totalMoney/100 + '</td><td>' + couponBatch.money/100 + '</td>'+
					'</td><td>' + couponBatch.usedNumber + '</td>'+
					'<td>' + platformTypeMap[couponBatch.platformType] + '</td><td>' + statusMap[couponBatch.status] + '</td>'+
					'<td>' + timeMsg + '</td><td>' + couponBatch.activityId+ '</td>'+
					'<td>' + couponBatch.feature + '</td><td>' + couponBatch.releaseBusinessId + '</td>'+
					'<td>' + couponBatch.orderId + '</td><td>' + sourceMap[couponBatch.source] + '</td>'+
					'<td>' + couponBatch.collegeId + '</td><td>' + couponBatch.totalPay/100 + '</td>'+
					'<td>' + couponBatch.onlinePay/100 + '</td><td>' + payStatusMap[couponBatch.payStatus] + '</td>'+
					 '</td><td>' + timeMsg1 + '</td>'+
					'<td>' + couponBatch.categoryId + '</td><td>' + couponBatch.skuNum + '</td><tr>');
		}
		
		for (var i=0, len = rewardsList.length; i<len; i++) {
			var couponBatch = rewardsList[i];
			var timeMsg="";
			var startDate = new Date();
			var endDate = new Date();
			startDate.setTime(couponBatch.startTime);
			endDate.setTime(couponBatch.endTime);
			
			var timeMsg1="";
			var startDate1 = new Date();
			var endDate1 = new Date();
			startDate1.setTime(couponBatch.payCreateTime);
			endDate1.setTime(couponBatch.payUpdateTime);
			if(couponBatch.startTime && couponBatch.endTime) {
				timeMsg =startDate.Format("yyyy-MM-dd") + "/" + endDate.Format("yyyy-MM-dd");	
			}
			
			if(couponBatch.payCreateTime && couponBatch.payUpdateTime) {
				timeMsg1 =startDate1.Format("yyyy-MM-dd") + "/" + endDate1.Format("yyyy-MM-dd");	
			}
			
			$("#rewardsList").append('<tr><td>' + couponBatch.batchId + '</td><td>' + couponBatch.batchName + '</td>'+
					'<td>' + couponBatch.totalMoney/100 + '</td><td>' + couponBatch.money/100 + '</td>'+
					'</td><td>' + couponBatch.usedNumber + '</td>'+
					'<td>' + platformTypeMap[couponBatch.platformType] + '</td><td>' + statusMap[couponBatch.status] + '</td>'+
					'<td>' + timeMsg + '</td><td>' + couponBatch.activityId+ '</td>'+
					'<td>' + couponBatch.feature + '</td><td>' + couponBatch.releaseBusinessId + '</td>'+
					'<td>' + couponBatch.orderId + '</td><td>' + sourceMap[couponBatch.source] + '</td>'+
					'<td>' + couponBatch.collegeId + '</td><td>' + couponBatch.totalPay/100 + '</td>'+
					'<td>' + couponBatch.onlinePay/100 + '</td><td>' + payStatusMap[couponBatch.payStatus] + '</td>'+
					'</td><td>' + timeMsg1 + '</td>'+
					'<td>' + couponBatch.categoryId + '</td><td>' + couponBatch.skuNum + '</td><tr>');
		}
		
	});
	
});

	$.getJSON(urlPrefix+"ajaxMateData", function (ret) {
		cityList = ret.citiList;
		collegeMap = ret.collegeMap;
		for (var i=0, len = cityList.length; i<len; i++) {
			$("#cityList").append('<li><a href="javascript:void(0);" class="btnCity" data-val="'+cityList[i].cityId+'" onclick="changeCity(this)">'+cityList[i].cityName+'</a></li>');
		}
		console.log(ret);
    });
	
var couponRuleIdTemp = 0;
function getRegion(couponRuleId){
	couponRuleIdTemp = couponRuleId
	/* $.fn.zTree.init($("#selectTree"), setting, zNodes); */
    $.post("getregion", {"activityId":couponRuleId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
        	 $('#treeEdit').modal('show');
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     	 }
	},"json"); 
}

function checkInteger(input) {
    var re = /^[1-9]+[0-9]*]*$/;

    if (!re.test(input)) {
       return false;
    }
    
    return true;
}


</script>