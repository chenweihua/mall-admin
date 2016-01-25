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
					<i class="glyphicon glyphicon-banner"></i> 邀请奖励明细
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
			          
			           <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">有效期</span> 
			              	<input type="text" id="startTime" class="form-control" value="${startTime}" readonly="readonly">
	
			            </div>
			          </div>
			          
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">至</span>
			               <input type="text" id="endTime" class="form-control" value="${endTime}" readonly="readonly">
	
			            </div>
			          </div>
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
			          <div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnExport" class="btn btn-default">导出</button>
						</div>
					</div>
		        </div>
				
				<table id="rewardList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>批次号</th>
							<th>邀请者手机</th>
							<th>被邀请者手机</th>
							<th>状态</th>
							<th>拉新日期</th>
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
		1: "未使用",
		2: "成功"
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
	
$('#startTime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 0,
    minView: 'month',
    format: "yyyy-mm-dd",
    formatDate:'yyyy-mm-dd',
    autoclose:true
});

$('#endTime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 0,
    minView: 'month',
    format: "yyyy-mm-dd",
    formatDate:'yyyy-mm-dd',
    autoclose:true
});

var table = null;
var urlPrefix = "";
var rewardList;

//提交搜索条件
$('#btnSubmit').click(function(){
	var phone = $("#phone").val();
	if(phone!=""){
		if(!checkInteger(phone)&&$.trim(phone).length != 11){
			alert("手机号不正确");
			return ;
		}
	}
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if($.trim(startTime).length < 1&&$.trim(endTime).length < 1){
		alert("时间不能为空");
		return;
	}else{
		if($.trim(endTime).length >= 1) {
			if($.trim(startTime).length < 1){
				alert("开始时间不能为空");
				return ;
			}
			if(startTime >= endTime) {
				alert("开始时间不得早于结束时间");
				return ;
			}
		}
	}
	table.ajax.reload(null, true);
});

$('#btnExport').click(function() {
		var phone = $("#phone").val();
		if(phone!=""){
			if(!checkInteger(phone)&&$.trim(phone).length != 11){
				alert("手机号不正确");
				return ;
			}
		}
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if($.trim(startTime).length < 1||$.trim(endTime).length < 1){
			alert("时间不能为空");
			return;
		}else{
			if($.trim(endTime).length >= 1) {
				if($.trim(startTime).length < 1){
					alert("开始时间不能为空");
					return ;
				}
				if(startTime >= endTime) {
					alert("开始时间不得早于结束时间");
					return ;
				}
			}
		}
		window.location.href = urlPrefix + "export?strStartDate="
					+ startTime + "&strEndDate=" + endTime + "&phone="+phone;
		});

$(document).ready(function() {
	table = $('#rewardList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: urlPrefix+"getPushRewardList",
        	data: function(d) {
        		d.phone = $("#phone").val();
        		d.startDate = $('#startTime').val();
                d.endDate = $('#endTime').val();
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		rewardList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var couponBatch = json.data[i];
        			var openOpera = "";
        			var timeMsg = "";
        			var startDate = new Date();
        			var endDate = new Date();
    				startDate.setTime(couponBatch.createTime);
        			if(couponBatch.createTime) {
        				timeMsg =startDate.Format("yyyy-MM-dd hh:mm:ss");	
        			}
        			newData[newData.length] = [couponBatch.batchId,
        			                           couponBatch.releaseBussinessMobile,
        			                           couponBatch.bussinessMobile,
        			                           statusMap[couponBatch.status==4?2:couponBatch.status],
        			                           timeMsg
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