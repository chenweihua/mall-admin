<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<link
	href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/print.js"></script>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i> 订单管理
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
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">开始时间</span> <input type="text"
								id="start-time" class="form-control" value="${startDate}">
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">结束时间</span> <input type="text"
								id="end-time" class="form-control" value="${endDate}">
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">子订单号</span> <input type="text"
								id="orderCode" class="form-control">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">支付方式</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="onlinePayTypeText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="javascript:void(0);" class="onlinePayType"
										data-val="">全部</a></li>
									<li><a href="javascript:void(0);" class="onlinePayType"
										data-val="1">微信</a></li>
									<li><a href="javascript:void(0);" class="onlinePayType"
										data-val="2">支付宝</a></li>
									<li><a href="javascript:void(0);" class="onlinePayType"
										data-val="3">余额支付</a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">供应商名称</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="storageText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="storageMenu">
										<li><a href="javascript:void(0);" class="storage"
											data-val="">全部</a></li>
									<c:forEach var="storage" items="${storageList}">
										<li><a href="javascript:void(0);" class="storage"
											data-val="${storage.storageId}">${storage.storageName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">物流状态</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="deliverStatusText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="javascript:void(0);" class="deliverStatus"
										data-val="">全部</a></li>
									<li><a href="javascript:void(0);" class="deliverStatus"
										data-val="0">待发货</a></li>
									<li><a href="javascript:void(0);" class="deliverStatus"
										data-val="1">已发货</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">物流单号</span>
							<input type="text" id="deliverSheetCode" class="form-control">
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">状态</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="btnStatusText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="javascript:void(0);" class="btnStatus"
										data-val="0">全部</a></li>
									<li><a href="javascript:void(0);" class="btnStatus"
										data-val="1">未支付</a></li>
									<li><a href="javascript:void(0);" class="btnStatus"
										data-val="2">订单无效</a></li>
									<li><a href="javascript:void(0);" class="btnStatus"
										data-val="3">订单超时</a></li>
									<li><a href="javascript:void(0);" class="btnStatus"
										data-val="4">支付中</a></li>
									<li><a href="javascript:void(0);" class="btnStatus"
										data-val="5">支付成功</a></li>
									<!-- <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="6">支付超时</a></li> -->
								</ul>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group">
							<button type="button" id="btnSubmit" class="btn">查&nbsp;&nbsp;询</button>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group" style="overflow-x:auto;">
							<button type="button" id="btnExportThirdSellerOrders" class="btn">导出订单</button>
						</div>
					</div>
				</div>
				<br> <br>
				<div style="overflow-x:auto;">
				<div>
					<span id="orderTotalCountId"></span>
					<span id="orderTotalAmountId"></span>
				</div>
				<table id="orderList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>子订单号</th>
							<th>订单金额</th>
							<th>支付方式</th>
							<th>订单状态</th>
							<th>物流状态</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
				</div>
			</div>
		</div>
	</div>
	<!--/span-->

</div>
<!--/row-->

<%@ include file="../footer.jsp"%>

<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>


<script>
	var table = null;
	var status = <c:out value="${status}" />;
	var deliverStatus = '';
	var storageId = '';
	var orderList;
	var onlinePayType = '';

	var urlPrefix = "";
	//1:未支付，2:因sku没有库存或sku超过秒杀时间导致的订单创建失败，3:订单超时，4:支付中，5:支付成功',?
	var statusMap = {
		'null':"",
		0 : "全部",
		/* 1:"成功",
		2:"失败" */
		1 : "未支付",
		2 : "订单无效",
		3 : "订单超时",
		4 : "支付中",
		5 : "支付成功"
	};
	var payTypeMap = {
		'' : "全部",
		0 : "",
		1 : "微信",
		2 : "支付宝",
		3 : "余额支付"
	};

	var deliverStatusMap = {
			'' : "全部",
			'0' : "待发货",
			'1' : "已发货"
		}
	
	var storageMap = {
		<c:forEach var="storage" items="${storageList}">
			'${storage.storageId}' : '${storage.storageName}',
		</c:forEach>
		'' : '全部'			
	};

	$('#start-time').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		format : 'yyyy-mm-dd hh:ii:ss'
	});

	$('#end-time').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		format : 'yyyy-mm-dd hh:ii:ss'
	});

	$('.onlinePayType').click(function() {
		onlinePayType = $(this).data("val");
		var payType = '全部';
		if(onlinePayType == '') {
			payType = payTypeMap[''];
		}
		else {
			payType = payTypeMap[onlinePayType];
		}
		$('#onlinePayTypeText').html(payType);
	});
	
	$('.btnStatus').click(function() {
		status = $(this).data("val");
		$('#btnStatusText').html(statusMap[status]);
	});
	
	$('.deliverStatus').click(function() {
		deliverStatus = $(this).data("val");
		$('#deliverStatusText').html(deliverStatusMap[deliverStatus]);
	});

	$('.storage').click(function() {
		storageId = $(this).data("val");
		$('#storageText').html(storageMap[storageId]);
	});
	
	//提交搜索条件
	$('#btnSubmit').click(function() {
		startTime = $('#start-time').val();
		endTime = $('#end-time').val();
		table.ajax.reload();
	});

	$('#btnExportThirdSellerOrders').click(
			function() {
				startDate = $('#start-time').val();
				endDate = $('#end-time').val();
				onlinePayType = onlinePayType;
				status = status;
				orderCode = $('#orderCode').val();
				deliverSheetCode = $("#deliverSheetCode").val();
				storageId = storageId;
				deliverStatus = deliverStatus;
				window.location.href = urlPrefix + "exportThirdSellerOrders?startDate=" + startDate + "&endDate=" + endDate
						+ "&status=" + status + "&orderCode=" + orderCode + "&deliverSheetCode=" + deliverSheetCode
						+ "&storageId=" + storageId + "&deliverStatus=" + deliverStatus +"&onlinePayType=" + onlinePayType;
			}
	);
	
	$(document)
			.ready(
					function() {

						$('#btnStatusText').html(statusMap[status]);
						table = $('#orderList')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											searching : false,
											ajax : {
												url : urlPrefix
														+ "ajaxListData",
												data : function(d) {
													//时间
													d.startDate = $(
															'#start-time')
															.val();
													d.endDate = $('#end-time')
															.val();
													d.status = status;
													d.orderCode = $('#orderCode').val();
													d.deliverSheetCode = $("#deliverSheetCode").val();
													d.storageId = storageId;
													d.deliverStatus = deliverStatus;
													d.onlinePayType = onlinePayType;
												},
												dataSrc : function(json) {
													var newData = [];
													orderList = json.data;
													$('#orderTotalCountId').html("订单总数："+json.recordsTotal+" 条;");
													$('#orderTotalAmountId').html("订单金额："+json.orderTotalAmount+" 元;");
													for (var i = 0, len = json.data.length; i < len; i++) {
														var order = json.data[i];
														var createTime = new Date();
														createTime.setTime(order.createTime);

														var deliverStatusDesc;
														if(order.deliverSheetCode == null || order.deliverSheetCode == "") {
															deliverStatusDesc = deliverStatusMap['0'];
														} else {
															deliverStatusDesc = deliverStatusMap['1'];
														}
														var row = [
																order.childOrderCode,
																order.totalPay / 100.0,
																payTypeMap[order.onlinePayType] != null ? payTypeMap[order.onlinePayType] : "",
																statusMap[order.status] != null ? statusMap[order.status] : "",
																deliverStatusDesc,
																createTime.Format("yyyy-MM-dd hh:mm:ss"),
																'<a href="javascript:getOrderDetail(' + order.childOrderId + ');">查看</a>'
																];
														newData[newData.length] = row;
													}
													json.data = newData;
													return newData;

												}
											},
											sDom : "<'row'<'col-md-4'f>r>t<'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
											sPaginationType : "bootstrap",
											oLanguage : {
												sLengthMenu : "_MENU_ 记录每页",
												sSearch : "按手机号搜索 _INPUT_",
												sZeroRecords : "暂无数据",
												sProcessing : "正在处理...",
												sEmptyTable : "暂无数据",
												sInfo : "_START_ - _END_ (共_TOTAL_条)",
												sInfoFiltered : "",
												oPaginate : {
													sFirst : "第一页",
													sLast : "最后一页",
													sNext : "下一页",
													sPrevious : "前一页",
												},

											}
										});

					});

	
	function getOrderDetail(childOrderId) {
		window.location.href = urlPrefix + "getChildOrderDetail?childOrderId=" + childOrderId;
	}
	
	function ignoreDeliveryStatus(childOrderCode) {
		
		if(confirm("您确定要忽略该物流异常？")) {
			$.post(urlPrefix+"ignoreDeliveryStatus?childOrderCode="+childOrderCode, null, function(response){
				if (response.code == 0) {
					alert("操作成功");
			        table.ajax.reload(null, true);
			        $('#modalDeliverManager').modal('hide');
				} else {
					alert(response.msg);
				}
			});
		}
		
	}
	
</script>