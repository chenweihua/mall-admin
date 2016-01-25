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
					<i class="glyphicon glyphicon-banner"></i> 优惠劵管理
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
		        <div class="row">
		        
			        <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">平台</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="platformTypeText">全部</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="btnPlatformType"
			                      data-val="0">全部</a></li>
			                  <li><a href="javascript:void(0);" class="btnPlatformType"
			                      data-val="1">APP</a></li>
			                  <li><a href="javascript:void(0);" class="btnPlatformType"
			                      data-val="2">H5</a></li>
			                </ul>
			              </div>
			            </div>
			          </div>
			          
			          <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">发券方式</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="deliverTypeText">全部</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="btnDeliverType"
			                      data-val="0">全部</a></li>
			                  <!-- 
			                  <li><a href="javascript:void(0);" class="btnDeliverType"
			                      data-val="1">发送至账户</a></li>
			                    -->
			                  <li><a href="javascript:void(0);" class="btnDeliverType"
			                      data-val="1">发送到手机</a></li>
			                  <li><a href="javascript:void(0);" class="btnDeliverType"
			                      data-val="2">区域发送</a></li>
			                  <!--
			                  <li><a href="javascript:void(0);" class="btnDeliverType"
			                      data-val="4">用户自领</a></li>
			                  -->
			                </ul>
			              </div>
			            </div>
			          </div>
			          
			          <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">优惠劵状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="statusText">全部</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="btnStatus"
			                      data-val="0">全部</a></li>
			                  <li><a href="javascript:void(0);" class="btnStatus"
			                      data-val="1">新建</a></li>
			                  <li><a href="javascript:void(0);" class="btnStatus"
			                      data-val="2">领取中</a></li>
			                  <li><a href="javascript:void(0);" class="btnStatus"
			                      data-val="3">领取完</a></li>
			                  <li><a href="javascript:void(0);" class="btnStatus"
			                      data-val="4">领取结束</a></li>
			                </ul>
			              </div>
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
			          
					
					</div>
					<div  class="row">
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">批次号</span> <input type="text"
			                id="batchId" class="form-control" value="${batchId}" placeholder="">
			            </div>
			          </div>
			          
			           <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">优惠券名称</span> <input type="text"
			                id="batchName" class="form-control" value="${batchName}" placeholder="">
			            </div>
			          </div>
			          
			           <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">优惠券面值</span> <input type="text"
			                id="money" class="form-control" value="${money}" placeholder="">
			            </div>
			          </div>
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
		        </div>
				
		        <br>
        		<br>
				<table id="couponList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>批次号</th>
							<th>优惠券名称</th>
							<th>发行总额</th>
							<th>发行面值</th>
							<th>发行数</th>
	             			<th>已发行数</th>
							<th>平台</th>
							<th>状态</th>
							<th>优惠券有效日期</th>
							<th>操作</th>
						</tr>
					</thead>
					
				</table>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>
<!--/row-->

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width:800px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="form1">
							
							<input type="hidden" name="addPlatFormType" id="addPlatFormType" value="0" />
							<div class="form-group">
								<label for="lableCoupon">优惠券批次号</label> <input
									 name="addCouponId" id="addCouponId"
									value="${couponBatchId}" readonly="readonly">
							</div>
							
							<div class="form-group">
								<label for="lableCoupon">优惠券名称</label> <input
									 name="addCouponName" id="addCouponName"
									placeholder="优惠券名称">
							</div>
							
				            <div class="form-group">
				              <label for="lableCoupon">平台类型</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="platFormTypeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="platFormType-add"
				                      data-val="0">全部</a></li>
				                  <li><a href="javascript:void(0);" class="platFormType-add"
				                      data-val="1">APP</a></li>
		                          <li><a href="javascript:void(0);" class="platFormType-add"
				                      data-val="2">H5</a></li>
				                </ul>
				              </div>
				            </div>
							<br>
							
							<div class="form-group">
								<label for="lableCoupon">优惠券总额</label> <input
									name="addCouponTotalMoney" id="addCouponTotalMoney">元
							</div>
							
						<%-- 	<button id="couponTableAdd" class="col-md-offset-1">添加优惠券</button>
							<table id="couponTable_add"  class="couponTable_add col-md-offset-1">
								<tr><th>卷的面值</th><th>卷的张数</th><th>操作</th></tr>
								<c:forEach var="item" items="${wmsGoods}">
									<tr><td>${item.wms_goods_name}</td>
									<td>${item.wms_goods_gbm}</td>
									<td><input name='${item.wms_goods_id}num' value='1'/>
									<input type='hidden' name='wms_goods_id' value='${item.wms_goods_id}'/>
									</td><td><a href='javascript:void(0)' onclick='addTr(this);'> 添加</a><a href='javascript:void(0)' onclick='removeTr(this);'>删除</a></td></tr>
								</c:forEach>
							</table>  --%>
							
							<div class="form-group" id="couponMoneyTabless"><!--  <a href="javascript:addCouponMoneyRow();">添加</a> -->
								<div>
								优惠劵面值 <input	 name="addCouponMoney" id="addCouponMoney">
								元<input  name="addCouponCount" id="addCouponCount">张
								<!-- <a href="javascript:delCouponMoneyRow(this);">删除</a> -->
								</div>
							</div>
							
							
							<div class="form-group">
								<div id="selectTimeDiv">
								劵的有效期
								<input style="width: 150px; display: inline-block;"
									class="form-control" id="addStartDate" name="addStartDate" readonly> 
								至：<input style="width: 150px; display: inline-block;"
									class="form-control" id="addEndDate" name="addEndDate" readonly>
								</div>
							</div>
							
								
								使用限制
								<label class="checkbox">
								   <input name ="checkItem" style="position:relative;margin-left:0px;" type="checkbox" value="1"> 仅首次购买可用
								</label>
								
								<%-- <label class="checkbox">
								   <input name ="checkItem" type="checkbox" value="2"> 不可用的活动类目
								</label>
								
								<div class="controls" style="width:500px;">
								<select id="acctivity_category" name="acctivity_category" multiple
								class="form-control" data-rel="chosen" style="width:500px;">
								<c:forEach var="item" items="${activityList }">
									<option id="acctivity_category_id" value="${item.activityId }"
										<c:if test="${acctivity_category.contains(item.activityId)}">selected</c:if>>${item.activityName }
									</option>
								</c:forEach>
								</select>
								</div>  --%>
								
								<!-- <select data-placeholder="选择不可用的活动类目" style="width:250px;" class="acctivity_category">
								</select> -->
								<label class="checkbox">
								   <input name ="checkItem" style="position:relative;margin-left:0px;" type="checkbox" value="3"> 满减使用
								   <input
									 name="discountValue" id="discountValue"
									>元 &nbsp<span style='color:red;'>(备注：当订单状态满足所设金额方可使用优惠券)</span>
								</label>
								<label class="checkbox">
								   <input name ="checkItem" style="position:relative;margin-left:0px;" type="checkbox" value="4"> 限商品类目
								</label>
								<!-- <select multiple="multiple">
									
								</select> -->
								
								<!-- <select data-placeholder="限商品类目" style="width:250px;" class="coupon_category">
								</select> -->
								
								<div class="controls">
								<select data-placeholder="请选择商品类目"  style="width:700px;" id="coupon_category" name="coupon_category" multiple
								class="form-control" data-rel="chosen"  >
								
								<c:forEach var="item" items="${categoryList }">
									<option value="${item.categoryId }"
										<c:if test="${coupon_category.contains(item.categoryId)}">selected</c:if>>${item.categoryName }</option>
								</c:forEach>
								</select>
								</div> 
								
								<!-- <label class="checkbox">
								    优惠券有效星期
								</label>
								<div>
								   <input type="checkbox" id="checkItemAll"> 全部
								   <input name ="checkItem" type="checkbox" value="5" checked> 周一
								   <input name ="checkItem" type="checkbox" value="6" checked> 周二
								   <input name ="checkItem" type="checkbox" value="7" checked> 周三
								   <input name ="checkItem" type="checkbox" value="8" checked> 周四
								   <input name ="checkItem" type="checkbox" value="9" checked> 周五
								   <input name ="checkItem" type="checkbox" value="10" checked> 周六
								   <input name ="checkItem" type="checkbox" value="11" checked> 周日
								</div>
								<label class="radio">
								   <input type="radio" checked> 设置有效时间段
								</label>
								
								周一<input style="width: 70px; display: inline-block;"
									 value="00:00" id="oneTimeStart" name="oneTimeStart">
								至<input style="width: 70px;  display: inline-block;"
									class="form-control" value="23:59" id="oneTimeEnd" name="oneTimeEnd">
								周二<input style="width: 70px; display: inline-block;"
									class="form-control" value="00:00" id="secondTimeStart" name="secondTimeStart">
								至<input style="width: 70px;  display: inline-block;"
									class="form-control" value="23:59" id="secondTimeEnd" name="secondTimeEnd">
								周三<input style="width: 70px; display: inline-block;"
									class="form-control" value="00:00" id="thirdTimeStart" name="thirdTimeStart">
								至<input style="width: 70px;  display: inline-block;"
									class="form-control" value="23:59" id="thirdTimeEnd" name="thirdTimeEnd">
								周四<input style="width: 70px; display: inline-block;"
									class="form-control" value="00:00" id="fourTimeStart" name="fourTimeStart">
								至<input style="width: 70px;  display: inline-block;"
									class="form-control" value="23:59" id="fourTimeEnd" name="fourTimeEnd">
								周五<input style="width: 70px; display: inline-block;"
									class="form-control" value="00:00" id="fiveTimeStart" name="fiveTimeStart">
								至<input style="width:70px;  display: inline-block;"
									class="form-control" value="23:59" id="fiveTimeEnd" name="fiveTimeEnd">
								周六<input style="width: 70px; display: inline-block;"
									class="form-control" value="00:00" id="sixTimeStart" name="sixTimeStart">
								至<input style="width: 70px;  display: inline-block;"
									class="form-control" value="23:59" id="sixTimeEnd" name="sixTimeEnd">
								周日<input style="width: 70px; display: inline-block;"
									class="form-control" value="00:00" id="SunTimeStart" name="SunTimeStart">
								至<input style="width: 70px;  display: inline-block;"
									class="form-control" value="23:59" id="SunTimeEnd" name="SunTimeEnd">
							</div> -->
							
							<div class="btn-group">
								<label for="lableCoupon">优惠券预览展示</label>
								<textarea rows="6" cols="30" name="addCouponDesc" id="addCouponDesc"></textarea> <!-- <input t
									class="form-control" name="addCouponDesc" id="addCouponDesc"> -->
							</div>
							<div>
								<span id="timeOverTip"></span>
							</div>
						</form>
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
      <button type="button" class="btn btn-primary" id="btnSubmitEdit">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" id="btnCancle">取消</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../../footer.jsp"%>
<%@ include file="../../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>

/* $('#example-multiple-selected').multiselect(); */

function addCouponMoneyRow(){
	$("#couponMoneyTabless").append('<div>卷的面值 <input	 name="addCouponMoney" id="addCouponMoney">'
			+ '卷的张数<input  name="addCouponCount" id="addCouponCount"><a href="javascript:delCouponMoneyRow(this);">删除</a><div>');
}

function delCouponMoneyRow(target) {
	alert($(target).parent("div"));
	$(target).parents("div").css("background-color","red");
}



$('.couponTableAdd').click(function(){
	$("#couponTable_add tr:last").after(newRow);
});


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

$('#addStartDate').datetimepicker({
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

$('#addEndDate').datetimepicker({
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
  
$('#oneTimeStart').datetimepicker({
	language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'hh:ii'
});

$('#oneTimeEnd').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'hh:ii'
  });

var urlPrefix = "";
  
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
		2: "仅微信商城可用"
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

var cityList ;
var collegeMap ;
//for search select change
var type = 0;
var platformType = 0;
var deliverType = 0;
var status = 0;
var city = 0;
var college = 0;
var platFormType_add =0;
var discount;
$('.btnPlatformType').click(function(){
	platformType = $(this).data("val");
    $('#platformTypeText').html(platformTypeMap[platformType]);
});

$('.btnDeliverType').click(function(){
	deliverType = $(this).data("val");
    $('#deliverTypeText').html(deliverTypeMap[deliverType]);
});


$('.btnStatus').click(function(){
	status = $(this).data("val");
    $('#statusText').html(statusMap[status]);
});

/* $('.platFormType-add').click(function(){
	platFormType_add = $(this).data("val");
	$('#addPlatFormType').val(platFormType_add);
    $('#platFormTypeText-add').html(platformTypeMap[platFormType_add]);
}); */

function changeCity(obj){
	city = $(obj).data("val");
    $('#btnCityText').html($(obj).html());
    
    college = -1;//$(obj).data("val");
    $('#btnCollegeText').html("全部");
    
    if(collegeMap[city]) {
    	//console.log(collegeMap[city]);
    	$("#CollegeList").html("");
    	$("#CollegeList").append('<li><a href="javascript:void(0);" class="btnCollege" data-val="-1" onclick="changeCollege(this)">全部</a></li>');
    	for (var i=0, len = collegeMap[city].length; i<len; i++) {
			$("#CollegeList").append('<li><a href="javascript:void(0);" class="btnCollege" data-val="'+collegeMap[city][i].collegeId+'" onclick="changeCollege(this)">'+collegeMap[city][i].collegeName+'</a></li>');
		}
    }
};

function changeCollege(obj){
	college = $(obj).data("val");
    $('#btnCollegeText').html($(obj).html());
};

var desc;
var table = null;

var typeEdit;
//edit
var operation = "add";
var couponBatchList ;
$('#addButton').click(function () {
	operation = "add";
    
    /* $("#btnActivityTypeText-add").html(activityTypeMap[0]);
    $("#btnActivityPlatformText-add").html(activityPlatformTypeMap[0]);
    
    $("#btnActivityTimeText-add").html(activityTimeMap[0]);
    
    $("#btnClickTypeText-add").html(activityClickTypeMap[0]); */
    
    $.getJSON(urlPrefix+"getSequenceId", function (response) {
        if (response.sequenceId==0) {
            alert("批次号生成失败");
        }else {
        	  $("#addCouponId").val(response.sequenceId);
        }
    });
    
    $("#addCouponName").val("");
	
	$("#platFormType-add").val(0);
    $('#platFormTypeText-add').html(platformTypeMap[0]);
    
    $("#addCouponTotalMoney").val("");
    $("#addCouponMoney").val("");
    $("#addCouponCount").val("");
    $("#discountValue").val("");
   /*  $("#addCouponDesc").val(""); */
	$("#addCouponDesc").val(platformTypeDescMap[0]);
    
    $("#addStartDate").val("");
	$("#addEndDate").val("");	
	$("#btnSubmitEdit").show();
	
	$("input[name=checkItem]").prop("checked",false);
	$("#acctivity_category").val("").trigger("chosen:updated");
	$("#coupon_category").val("").trigger("chosen:updated");
	discount  ="";
    type=0;
	desc="";
	$('#modalEdit').modal();
});

/* $(function(){
	
	$(".acctivity_category").trigger("liszt:updated");
	$(".acctivity_category").chosen();  
	
	$(".coupon_category").trigger("liszt:updated");
	$(".coupon_category").chosen();  
}); */

function cleanAll(){
	$("#addCouponName").val("");
	$("#platFormType-add").val(0);
    $('#platFormTypeText-add').html(platformTypeMap[0]);
    
    $("#addCouponTotalMoney").val("");
    $("#addCouponMoney").val("");
    $("#addCouponCount").val("");
    $("#discountValue").val("");
    discount ="";
    type=0;
	desc="";
    /* $("#addCouponDesc").val(""); */
    $("#addCouponDesc").val(platformTypeDescMap[0]);
    
    $("#addStartDate").val("");
	$("#addEndDate").val("");	
	$("#btnSubmitEdit").show();
	
	$("input[name=checkItem]").prop("checked",false);
	$("#acctivity_category").val("").trigger("chosen:updated");
	$("#coupon_category").val("").trigger("chosen:updated");
	/* $('#modalEdit').modal(); */
}

function overTipEndDate(){
	var nowEndDate = $("#addEndDate").val();
		var nowDate = new Date();
		var arrDate,objDate1,objDate2,numOverDay;
	    arrDate=nowEndDate.split("-");
	    objDate1=new Date(arrDate[1]+'-'+arrDate[2]+'-'+arrDate[0]);
	    objDate2=new Date((nowDate.getMonth()+1)+"-"+nowDate.getDate()+"-"+nowDate.getFullYear());
	    if(objDate1<objDate2){
	    	$("#timeOverTip").html("当前日期是" + nowDate.Format("yyyy-MM-dd") + "  <span style='color:red;'>已过期</span>");
	    }else{
	    	 numOverDay=parseInt(Math.abs(objDate2-objDate1)/1000/60/60/24)+1;
	 		$("#timeOverTip").html("当前日期是" + nowDate.Format("yyyy-MM-dd") + "  <span style='color:red;'>还有" + numOverDay + "天过期</span>");
	    }
	}

function checkParam(){
	
	var numReg = /^\d+(?=\.{0,1}\d+$|$)/;
	
	
	var addCouponName = $("#addCouponName").val();
	var addCouponTotalMoney = $("#addCouponTotalMoney").val();
	var addCouponMoney = $("#addCouponMoney").val();
	var addCouponCount = $("#addCouponCount").val();
	var addStartDate = $("#addStartDate").val();
	var addEndDate = $("#addEndDate").val();
	
	/* var startTime = $('#startDate').val();
	var endTime = $('#endDate').val(); */
	
	//alert(startTime + ":" + $.trim(startTime).length);
	
	if($.trim(addCouponName).length < 1 || $.trim(addCouponName).length > 18) {
		return "优惠券名称不为空且不能超过9个字";
	}
	
	if(addCouponTotalMoney<=0||!checkInteger(addCouponTotalMoney)) {
		return "总金额请输入正整数";
	}
	
	if(addCouponMoney<=0||!checkInteger(addCouponMoney)) {
		return "面值请输入正整数";
	}
	
	if(addCouponCount<=0||!checkInteger(addCouponCount)) {
		return "张数请输入正整数";
	}
	if($.trim(addStartDate).length < 1) {
		return "开始时间不能为空";
	}
	if($.trim(addEndDate).length < 1) {
		return "结束时间不能为空";
	}
	
	if(addStartDate >= addEndDate) {
		return "开始时间不得早于结束时间";
	}
	
	/* var acctivity = $("input[name=checkItem][value=2]").get(0).checked;
	if(acctivity&&$.trim($("#acctivity_category").val()) == ''){
		return "不可使用活动类目不能为空";
	} */
	
	var manjian = $("input[name=checkItem][value=3]").get(0).checked;
	var d = $("#discountValue").val();
	if(manjian && ($.trim(d) == ''||d<=0||!checkInteger(d))) {
		return "满减不能为空且为整数";
	}
	
	var category = $("input[name=checkItem][value=4]").get(0).checked;
	if(category&&$.trim($("#coupon_category").val()) == ''){
		return "限商品类目不能为空";
	}
	
	return null;
}


$('#btnSubmitEdit').click(function(){
	
	var msg = checkParam();
	if(msg != null){
		alert (msg)
		return ;
	}
	$.post(urlPrefix+"saveCoupon"+"?operation="+operation, $("#form1").serialize(), function(response){
		if (response.code == 0) {
			alert("操作成功");
			$("#form1")[0].reset();
			cleanAll();
	        table.ajax.reload(null, true);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.msg);
		}
	});
	
	return false;
});

$('#btnCancle').click(function(){
	cleanAll();
	$('#modalEdit').modal('hide');
});

function lookCouponBatch (index) {
	var  couponBatchEdit = couponBatchList[index];
	$("#addCouponId").val(couponBatchEdit.batchId);
	$("#addCouponName").val(couponBatchEdit.batchName);
	$("#platFormType-add").val(couponBatchEdit.platformType);
	type = couponBatchEdit.platformType;
    $('#platFormTypeText-add').html(platformTypeMap[couponBatchEdit.platformType]);
	$("#addCouponTotalMoney").val(couponBatchEdit.totalMoney/100);
	$("#addCouponMoney").val(couponBatchEdit.money/100);
	$("#addCouponCount").val(couponBatchEdit.totalNumber);
	var startDate = new Date();
	var endDate = new Date();
	startDate.setTime(couponBatchEdit.startTime);
	endDate.setTime(couponBatchEdit.endTime);
	$("#addStartDate").val(startDate.Format("yyyy-MM-dd"));
	$("#addEndDate").val(endDate.Format("yyyy-MM-dd"));
	$("#addCouponDesc").val(couponBatchEdit.batchDesc);
	$("#btnSubmitEdit").hide();
	overTipEndDate();
	
	if(couponBatchEdit.featureMap!=null){
		$.each(couponBatchEdit.featureMap,function(key,value){
			$("input[name=checkItem][value="+key+"]").prop("checked","checked");
			var v1;
			if(value!=null){
				v1 =value.split(",");
			}
			if(key==2){
				$.each(v1,function(v2,v3){

					$("#acctivity_category option[value="+v3+"]").prop("selected","selected"); 
				});
				
				$("#acctivity_category").trigger("chosen:updated");
			}
			if(key==3){
				$("#discountValue").val(value);
				discount=value;
			}
			if(key==4){
				$.each(v1,function(v2,v3){
					$("#coupon_category option[value="+v3+"]").prop("selected","selected");  
				});
				$("#coupon_category").trigger("chosen:updated");
			}
			if(key==5){
				$("#oneTimeStart").val(v1[0]);
				$("#oneTimeEnd").val(v1[1]);
			}
			if(key==6){
				$("#secondTimeStart").val(v1[0]);
				$("#secondTimeEnd").val(v1[1]);
			}
			if(key==7){
				$("#thirdTimeStart").val(v1[0]);
				$("#thirdTimeEnd").val(v1[1]);
			}
			if(key==8){
				$("#fourTimeStart").val(v1[0]);
				$("#fourTimeEnd").val(v1[1]);
			}
			if(key==9){
				$("#fiveTimeStart").val(v1[0]);
				$("#fiveTimeEnd").val(v1[1]);
			}
			if(key==10){
				$("#sixTimeStart").val(v1[0]);
				$("#sixTimeEnd").val(v1[1]);
			}
			if(key==11){
				$("#sunTimeStart").val(v1[0]);
				$("#sunTimeEnd").val(v1[1]);
			}
			
		});
	}
	$('#modalEdit').modal();
}


function editCouponBatch (index) {
	operation = "edit";
	var  couponBatchEdit = couponBatchList[index];
	if(couponBatchEdit.status!=1){
		alert("只有新建批次优惠劵可以编辑");
		return ;
	}
	
	$("#addCouponId").val(couponBatchEdit.batchId);
	$("#addCouponName").val(couponBatchEdit.batchName);
	$("#platFormType-add").val(couponBatchEdit.platformType);
	type=couponBatchEdit.platformType;
    $('#platFormTypeText-add').html(platformTypeMap[couponBatchEdit.platformType]);
	$("#addCouponTotalMoney").val(couponBatchEdit.totalMoney/100);
	$("#addCouponMoney").val(couponBatchEdit.money/100);
	$("#addCouponCount").val(couponBatchEdit.totalNumber);
	var startDate = new Date();
	var endDate = new Date();
	startDate.setTime(couponBatchEdit.startTime);
	endDate.setTime(couponBatchEdit.endTime);
	$("#addStartDate").val(startDate.Format("yyyy-MM-dd"));
	$("#addEndDate").val(endDate.Format("yyyy-MM-dd"));
	$("#addCouponDesc").val(couponBatchEdit.batchDesc);
	$("#btnSubmitEdit").show();
	overTipEndDate();
	if(couponBatchEdit.featureMap!=null){
		$.each(couponBatchEdit.featureMap,function(key,value){
			$("input[name=checkItem][value="+key+"]").prop("checked","checked");
			var v1;
			if(value!=null){
				v1 =value.split(",");
			}
			if(key==2){
				$.each(v1,function(v2,v3){

					$("#acctivity_category option[value="+v3+"]").prop("selected","selected"); 
				});
				
				$("#acctivity_category").trigger("chosen:updated");
			}
			if(key==3){
				$("#discountValue").val(value);
				discount=value;
			}
			if(key==4){
				$.each(v1,function(v2,v3){
					$("#coupon_category option[value="+v3+"]").prop("selected","selected");  
				});
				$("#coupon_category").trigger("chosen:updated");
			}
			if(key==5){
				$("#oneTimeStart").val(v1[0]);
				$("#oneTimeEnd").val(v1[1]);
			}
			if(key==6){
				$("#secondTimeStart").val(v1[0]);
				$("#secondTimeEnd").val(v1[1]);
			}
			if(key==7){
				$("#thirdTimeStart").val(v1[0]);
				$("#thirdTimeEnd").val(v1[1]);
			}
			if(key==8){
				$("#fourTimeStart").val(v1[0]);
				$("#fourTimeEnd").val(v1[1]);
			}
			if(key==9){
				$("#fiveTimeStart").val(v1[0]);
				$("#fiveTimeEnd").val(v1[1]);
			}
			if(key==10){
				$("#sixTimeStart").val(v1[0]);
				$("#sixTimeEnd").val(v1[1]);
			}
			if(key==11){
				$("#sunTimeStart").val(v1[0]);
				$("#sunTimeEnd").val(v1[1]);
			}
			
		});
	}
	$('#modalEdit').modal();
}


function endCouponBatch (couponBatchId) {	
    if (confirm("结束领取优惠券将不能继续领取，请及时对所推广的广告进行下线，是否确认进行此操作")) {

        $.getJSON(urlPrefix+"endCouponBatch?couponBatchId="+couponBatchId, function (response) {
            if (response.code==1) {
            	alert("结束成功");
            	table.ajax.reload();
            }else {
            	alert("结束失败");
            }
        });
    }
}


function openActivity(activityId, status) {
	
	var msg = "";
	if(status == 0) {
		msg = "开启";
	} else {
		msg = "关闭";
	}
	
    if (confirm("确定"+msg+"此活动")) {

        $.getJSON(urlPrefix+"open?activityId="+activityId + "&status=" + status, function (ret) {
            if (ret.code != null && ret.code != "success") {
                alert(ret.msg);
            }else {
            	alert(msg+"成功");
                table.ajax.reload();
            }
        });
    }
}


//提交搜索条件
$('#btnSubmit').click(function(){
	
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if($.trim(startTime).length < 1&&$.trim(endTime).length < 1){
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

$(document).ready(function() {
	//datatable
    //$('#btnTypeText').html(typeMap[type]);
	table = $('#couponList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: urlPrefix+"ajaxList",
        	data: function(d) {
        		d.batchId = $("#batchId").val();
        		d.startDate = $('#startTime').val();
                d.endDate = $('#endTime').val();
                d.batchName = $('#batchName').val();
                d.money = $('#money').val();
                d.platformType = platformType;
                d.status = status;
                d.deliverType =deliverType;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		couponBatchList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var couponBatch = json.data[i];
        			var openOpera = "";
        			var timeMsg = "";
        			var startDate = new Date();
        			var endDate = new Date();
    				startDate.setTime(couponBatch.startTime);
        			endDate.setTime(couponBatch.endTime);
        			if(couponBatch.startTime && couponBatch.endTime) {
        				timeMsg =startDate.Format("yyyy-MM-dd") + "/" + endDate.Format("yyyy-MM-dd");	
        			}
        			var operator = '<a href="javascript:lookCouponBatch('+i+');">查看</a>&nbsp;&nbsp;';
        			
		        			if(couponBatch.status == 1){
								operator += '<a href="javascript:editCouponBatch('+i+');">编辑</a>&nbsp;&nbsp;';
							}
        							
  							if(couponBatch.status != 4){
  								operator +='<a href="javascript:endCouponBatch('+couponBatch.batchId+');">结束领取</a>&nbsp;&nbsp;';
  							}
                    				 
		        			if(couponBatch.status == 1 && couponBatch.activityId == 0) {
		        				operator += '<a href="give?couponBatchId='+ couponBatch.batchId +'">优惠券发放</a>';
		        			}
        			/* if(couponBatch.status == 5) {
        				operator = "&nbsp;";
        			} */
        			newData[newData.length] = [couponBatch.batchId,
        			                           couponBatch.batchName,
        			                           couponBatch.totalMoney/100,
        			                           couponBatch.money/100,
        			                           couponBatch.totalNumber,
        			                           couponBatch.usedNumber,
        			                           platformTypeMap[couponBatch.platformType],
        			                           /* 这块强制转换，是给前台看的，*/
        			                           statusMap[couponBatch.status==5?2:couponBatch.status],
        			                           timeMsg,
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

	$.getJSON(urlPrefix+"ajaxMateData", function (ret) {
		cityList = ret.citiList;
		collegeMap = ret.collegeMap;
		for (var i=0, len = cityList.length; i<len; i++) {
			$("#cityList").append('<li><a href="javascript:void(0);" class="btnCity" data-val="'+cityList[i].cityId+'" onclick="changeCity(this)">'+cityList[i].cityName+'</a></li>');
		}
		console.log(ret);
    });
	
	
	
	$("#checkItemAll").click(function(){
		/* $(this).siblings 获取父类 */
		if(this.checked) {
			$("input[name=checkItem][value =5]").prop("checked","checked");
			$("input[name=checkItem][value =6]").prop("checked","checked");
			$("input[name=checkItem][value =7]").prop("checked","checked");
			$("input[name=checkItem][value =8]").prop("checked","checked");
			$("input[name=checkItem][value =9]").prop("checked","checked");
			$("input[name=checkItem][value =10]").prop("checked","checked");
			$("input[name=checkItem][value =11]").prop("checked","checked");
		} else {
			$("input[name=checkItem][value =5]").removeAttr("checked");
			$("input[name=checkItem][value =6]").removeAttr("checked");
			$("input[name=checkItem][value =7]").removeAttr("checked");
			$("input[name=checkItem][value =8]").removeAttr("checked");
			$("input[name=checkItem][value =9]").removeAttr("checked");
			$("input[name=checkItem][value =10]").removeAttr("checked");
			$("input[name=checkItem][value =11]").removeAttr("checked");
			/* $("input[name=checkItem]").removeAttr("checked"); */
		}
	});
	
	$("#addEndDate").change(function(){
		overTipEndDate();
		/* var nowEndDate = $(this).val();
		var nowDate = new Date();
		var arrDate,objDate1,objDate2,numOverDay;
	    arrDate=nowEndDate.split("-");
	    objDate1=new Date(arrDate[1]+'-'+arrDate[2]+'-'+arrDate[0]);
	    numOverDay=parseInt(Math.abs(nowDate-objDate1)/1000/60/60/24);
		$("#timeOverTip").html("当前日期是" + nowDate.Format("yyyy-MM-dd") + "  <span style='color:red;'>还有" + numOverDay + "天过期</span>");
		 */
	});
	
	 $(".platFormType-add").click(function(){
		desc = $("#addCouponDesc").val();
		var type1 = $(this).data("val");
		$('#addPlatFormType').val(type1);
	    $('#platFormTypeText-add').html(platformTypeMap[type1]);
		if(type1==type){
		}else{
				if(desc==null||desc==''){
					type=type1;
					desc = platformTypeDescMap[type1];
					/* if(type1!=0){ */
						$("#addCouponDesc").val(desc);
					/* } */
				}else{
					var array = desc.split("\n");
					var sb="";
					var d1 = platformTypeDescMap[type];
					var dd = platformTypeDescMap[type1];
					type = type1;
					$.each(array,function(v2,v3){
						if(d1==v3){
							/* if(type1!=0){ */
								array.splice(v2,1,dd);
							/* }else{
								array.splice(v2,1);
							} */
							
						}/* else{
							sb=sb+"\n"+v3;
						} */
					});
					var s =array.join(",");
					var tt =s.replace(/\,/g,"\n");
					$("#addCouponDesc").val(tt);
				}
		}
	 });
		
	$("input[name=checkItem][value=1]").click(function(){
		desc = $("#addCouponDesc").val();
		var dd ="仅首次购买可用";
		if(this.checked){
			if(desc==null||desc==''){
				desc = dd;
			}else{
				desc = desc+"\n"+dd;
			}
			$("#addCouponDesc").val(desc);
		}else{
			var array = desc.split("\n");
			var sb="";
			$.each(array,function(v2,v3){
				if(dd==v3){
					array.splice(v2,1);
				}/* else{
					sb=sb+"\n"+v3;
				} */
			});
			var s =array.join(",");
			var tt =s.replace(/\,/g,"\n");
			$("#addCouponDesc").val(tt);
		}
	});
	
	/* $("input[name=checkItem][value=2]").click(function(){
		desc = $("#addCouponDesc").val();
		var dd =$("input[name=checkItem][value=1]").val();
		if(this.checked){
			if(desc==null){
				desc = dd;
			}else{
				desc = desc+"\n"+dd;
			}
			$("#addCouponDesc").val(desc);
		}else{
			var array = desc.split("\n");
			$.each(array,function(v2,v3){
				if(dd==v3){
					array.splice(v2);
				}
			});
			$("#addCouponDesc").val(array);
		}
	}); */
	
	
	$("input[name=checkItem][value=3]").click(function(){
		desc = $("#addCouponDesc").val();
		var dis =$("#discountValue").val();
		if(dis!=null&&dis!=0&&checkInteger(dis)){
			var dd ="满"+dis+"元可用";
			if(this.checked){
				if(desc==null||desc==''){
					desc = dd;
				}else{
					desc = desc+"\n"+dd;
				}
				$("#addCouponDesc").val(desc);
			}else{
				var array = desc.split("\n");
				var sb="";
				$.each(array,function(v2,v3){
					if(dd==v3){
						array.splice(v2,1);
					}/* else{
						sb=sb+"\n"+v3;
					} */
					
				});
				var s =array.join(",");
				var tt =s.replace(/\,/g,"\n");
				$("#addCouponDesc").val(tt);
			}
		}
	});
	
	$("#discountValue").change(function(){
		desc = $("#addCouponDesc").val();
		var dis =$("#discountValue").val();
		var c =$("input[name=checkItem][value=3]").get(0).checked;
		if(c&&dis!=''&&dis!=0&&checkInteger(dis)){
			var dd ="满"+dis+"元可用";
			if(discount==null||discount==0||discount==''){
				if(desc==null||desc==''){
					desc = dd;
				}else{
					desc = desc+"\n"+dd;
				}
				discount = dis;
				$("#addCouponDesc").val(desc);
			}else{
				var d1 ="满"+discount+"元可用";
				var dd ="满"+dis+"元可用";
				discount = dis;
				if(desc==null||desc==''){
					desc = dd;
					$("#addCouponDesc").val(desc);
				}else{
					var array = desc.split("\n");
					var sb="";
					$.each(array,function(v2,v3){
						if(d1==v3){
							array.splice(v2,1,dd);
						}
					});
					var s =array.join(",");
					var tt =s.replace(/\,/g,"\n");
					$("#addCouponDesc").val(tt);
				}
				
			}
		}
	});
	
	
	/* $("#acctivity_category_chosen").trigger("chosen:updated"); */
    
} );
var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode, clickFlag){
				
			}
		}
	};
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

//确定
$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	var info = JSON.stringify(nodes);
	 $.post("setregion",{"couponRuleId":couponRuleIdTemp,"region":info}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~")
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
});

//判断正整数
function checkInteger(input) {
     var re = /^[1-9]+[0-9]*]*$/;

     if (!re.test(input)) {
        return false;
     }
     
     return true;
}

 $(document).ready(function(){
	
	$("#acctivity_category_chosen").css("width","700px");
	$("#coupon_category_chosen").css("width","700px"); 
	
});
</script>