<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<link
	href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/print.js"></script>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i> 订单查询
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
							<span class="input-group-addon">手机号</span> <input type="text"
								id="phone" class="form-control" value="${phone}" placeholder="">
						</div>
					</div>

					<div class="col-lg-2">
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
									<!-- <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="1">成功</a></li>
		                    <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="2">失败</a></li> -->
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
							<span class="input-group-addon">订单编号</span> <input type="text"
								id="orderCode" class="form-control">
						</div>
					</div>

					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">母订单编号</span> <input type="text"
								id="porderCode" class="form-control">
						</div>
					</div>

					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">订单类型</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="btnPayTypeText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="javascript:void(0);" class="btnPayType"
										data-val="-1">全部</a></li>
									<li><a href="javascript:void(0);" class="btnPayType"
										data-val="0">RDC</a></li>
									<li><a href="javascript:void(0);" class="btnPayType"
										data-val="1">LDC</a></li>
								</ul>
							</div>
						</div>
					</div>

					<div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
						</div>
					</div>

					<div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnExport" class="btn btn-default">退货订单导出</button>
						</div>
					</div>

				</div>

				<br> <br>
				<table id="orderList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>订单号</th>
							<th>母订单号</th>
							<th>收货人姓名</th>
							<th>联系方式</th>
							<th>收货地址</th>
							<th>订单金额【子】</th>
							<th>在线支付金额【总】</th>
							<th>促销免减金额【总】</th>
							<th>首单免减金额【总】</th>
							<th>优惠金额【总】</th>
							<th>运费</th>
							<th>优惠运费</th>
							<th>支付方式</th>
							<th>订单类型<!-- 提货方式 --></th>
							<th>p2p配送开始时间</th>
							<th>p2p配送结束时间</th>
							<th>当前状态</th>
							<th>备注</th>
							<th>生成时间</th>
							<th>有无退款</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	<!--/span-->

</div>
<!--/row-->


<!-- Modal 驳回原因-->
<div class="modal fade" id="modalRemark" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">请填写备注信息：</h4>
			</div>
			<div class="modal-body">
				<textarea id="remark"
					style="margin: 0px; width: 565px; height: 65px;"></textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="btnSubmitRemark">提交</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="modalEdit1" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<input id="modalOrderId" type="hidden" value="">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">
				<table class="withdrawTable" border="1">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAll"></th>
							<th>名称</th>
							<th>单价</th>
							<th>数量</th>
							<th>支付金额</th>
							<th>退货数量</th>
							<th>已退货数量</th>
						</tr>
					</thead>
					<tbody id="withdrawList"></tbody>
				</table>
				<br />
				<div>
					<label name="reason">退货原因</label>
					<br>
					<label id="reasonFirstLabel">一级原因：</label>
					<select id="reasonFirst" name="reasonFirst">
					</select> 
					
					<label id="reasonSecondLabel">二级原因：</label>
					<select id="reasonSecond" name="reasonSecond">
					</select>
					<textarea class="form-control" rows="5" id="reasonSecondOther" name="reasonSecondOther" style="display: none"></textarea>
				</div>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					id="btnSubmitCancel1">取消</button>
				<button type="button" class="btn btn-primary" id="btnSubmitEdit1">提交</button>
			</div>
		</div>
	</div>
</div>

<script id="withdrawTemplate" type="text/x-jsrender">
	{{if orderDetails.length}}
		{{for orderDetails}}
			<tr style="background-color : #d1d1d1;"><td style="display:none;">{{>childOrderDetailId}}</td><td><input type="checkbox"></td><td>{{>skuName}}</td><td>{{>skuPrice/100}}/{{>skuSubName}}</td><td>{{>skuNum}}</td><td>{{>(skuPrice*skuNum)/100}}</td><td><input type="text" value={{>skuNum}}   disabled style="background-color : #d1d1d1;"></td><td>{{>skuWithdrawedNum}}</td></tr>
		{{/for}}
	{{/if}}
</script>

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
	var payType = '-1';

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
		'null':"",
		0 : "",
		1 : "微信支付",
		2 : "支付宝"
	};

	var deliveryTypeMap = {
		0 : "自提",
		1 : "配送",
	   '-1': "第三方配送"
	};

	var orderTypeMap = {
		'-1' : "全部",
		0 : "RDC",//"p2p",
		1 : "LDC",//"自提"
		2 : "第三方"
	}

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

	$('.btnStatus').click(function() {
		status = $(this).data("val");
		$('#btnStatusText').html(statusMap[status]);
	});

	$('.btnPayType').click(function() {
		//debugger;
		payType = $(this).data("val");
		$('#btnPayTypeText').html(orderTypeMap[payType]);
	});

	//提交搜索条件
	$('#btnSubmit').click(function() {
		startTime = $('#start-time').val();
		endTime = $('#end-time').val();
		//var phone = $('#phone').val();
		//var orderCode=$('orderCode').val();
		table.ajax.reload();

		// window.location.href = urlPrefix+"orderManagement/list?startDate="+startTime+"&endDate="+endTime+"&phone="+phone+"&status="+status+"&payType="+payType;
	});

	$('#selectAll')
			.change(
					function() {
						if ($(this).is(':checked')) {
							$('tbody tr td input[type="checkbox"]').prop(
									'checked', true);
							$('tbody tr td input[type="text"]').prop(
									'disabled', false);
							$('#withdrawList').children('tr').css(
									'background-color', '');
							$('tbody tr td input[type="text"]').css(
									'background-color', '');
						} else {
							$('tbody tr td input[type="checkbox"]').prop(
									'checked', false);
							$('tbody tr td input[type="text"]').prop(
									'disabled', true);
							$('#withdrawList').children('tr').css(
									'background-color', '#d1d1d1');
							$('tbody tr td input[type="text"]').css(
									'background-color', '#d1d1d1');
						}
					});

	$('#withdrawList').on('change', 'input[type="checkbox"]', function() {
		var tr = $(this).parent().parent();
		console.log(tr);
		if ($(this).is(':checked')) {
			tr.find('input[type="text"]').css('background-color', '');
			tr.find('input[type="text"]').prop('disabled', false);
			tr.css('background-color', '');
		} else {
			tr.find('input[type="text"]').css('background-color', '#d1d1d1');
			tr.find('input[type="text"]').prop('disabled', true);
			tr.css('background-color', '#d1d1d1');
		}
	});

	$('#btnSubmitCancel1').click(function() {
		$('#selectAll').attr('checked', false);
		$('#reason').val('');

	});

	$('#btnSubmitEdit1').click(function() {
		var detailList = [];
		var selectedNum = 0;
		$("#withdrawList tr").each(function() {
			if ($(this).find('input[type="checkbox"]').is(":checked")) {
				selectedNum++;
				var orderDetailId = $(this).find('td').eq(0).text();
				var withdrawNum = $(this).find('input[type="text"]').val();
				detailList.push({
					'orderDetailId' : orderDetailId,
					'withdrawNum' : withdrawNum
				});
			}
		});

		if (selectedNum == 0) {
			alert("木有选择物品啦~~");
			return;
		}
		var reasonFirst = $('#reasonFirst option:selected').text();
		var reasonFirstCode = $('#reasonFirst option:selected').val();
		var reasonSecond = "";
		if(reasonFirstCode == 0){
			reasonSecond = $('#reasonSecondOther').val();
			if ($.trim(reasonSecond).length < 0) {
				alert("请填写退款原因")
				return;
			}
		}else{
			reasonSecond = $('#reasonSecond option:selected').text();
		}
		
		var reason = reasonFirst + "--" + reasonSecond;
		
		$.post(urlPrefix + "withdraw", {data : JSON.stringify({'detailList' : detailList,
				'reason' : reason
			})
		}, function(response) {
			console.log(response);
			if (response.status == "success") {
				alert("操作成功");
				table.ajax.reload(null, false);
				$('#modalEdit1').modal('hide');
			} else {
				alert(response.msg);
			}
		});

		$('#selectAll').attr('checked', false);
		$('#reason').val('');

	});

	$('#btnExport').click(
			function() {
				var startTime = $('#start-time').val();
				var endTime = $('#end-time').val();
				window.location.href = urlPrefix + "export?strStartDate="
						+ startTime + "&strEndDate=" + endTime;
			});

	$(document)
			.ready(
					function() {

						$('#btnStatusText').html(statusMap[status]);
						$('#btnPayTypeText').html(orderTypeMap[payType]);

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
													d.phone = $('#phone').val();
													d.status = status;
													d.orderType = payType;
													d.orderCode = $(
															'#orderCode').val();
													d.porderCode = $(
															'#porderCode')
															.val();

												},
												dataSrc : function(json) {
													var newData = [];
													for (var i = 0, len = json.data.length; i < len; i++) {
														var order = json.data[i];
														//console.log(order);
														var detailLink = '<a class="blueletter" href="javascript:void(0)" onclick="showOrderDetail(this,'
																+ order.childOrderId
																+ ',\''
																+ order.onlinePayId
																+ '\')">'
																+ order.childOrderCode
																+ '</a>';
														var withdraw = "无退款";
														if (order.isWithdraw == 1) {
															withdraw = "有退款";
														}

														var createTime = new Date();
														createTime
																.setTime(order.createTime);

														var p2pStart = "";
														var p2pEnd = "";

														if (order.deliveryType == 1) {
															var p2pDeliveryStartTime = new Date();
															p2pDeliveryStartTime
																	.setTime(order.deliveryBeginTime);
															p2pStart = p2pDeliveryStartTime
																	.Format("yyyy-MM-dd hh:mm:ss");

															var p2pDeliveryEndTime = new Date();
															p2pDeliveryEndTime
																	.setTime(order.deliveryEndTime);
															p2pEnd = p2pDeliveryEndTime
																	.Format("yyyy-MM-dd hh:mm:ss");
														}

														/* var deliverStr = "";
														if(order.type == 1) {
															deliverStr = "("+ deliveryTypeMap[order.deliveryType] +")";
														} */
														
														
														var row = [
																detailLink,
																order.orderCode,
																order.receiverName,
																order.receiverPhone,
																order.selfPickUpAddress+order.deliveryAddress,
																order.totalPay / 100.0,
																order.onlinePay / 100.0,
																order.fullSub / 100.0,//order.deratePay,
																order.firstSub / 100.0,
																order.couponPay / 100.0,
																order.freight/100.0,
																order.freightSub/100.0,
																payTypeMap[order.onlinePayType],
																orderTypeMap[order.distributeType]
																		+ "("
																		+ deliveryTypeMap[order.deliveryType]
																		+ ")",
																p2pStart,
																p2pEnd,
																statusMap[order.orderStatus],
																order.remark,
																createTime
																		.Format("yyyy-MM-dd hh:mm:ss"),
																withdraw ];
														//console.log(row);
														newData[newData.length] = row;
													}
													json.data = newData;
													return newData;

												}
											},
											sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
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

	function showOrderDetail(obj, orderId, onlinePayId) {

		$
				.post(
						"ajaxOrderDetail",
						{
							orderId : orderId
						},
						function(data) {
							$(obj).attr(
									"onclick",
									"hideOrderDetail(this," + orderId + ",\'"
											+ onlinePayId + "\')");
							//console.log(data);
							var detailTable = "<tr class='yellowbox padding10'><td colspan='16' align='center'>"
									+ "<table><tr><td>支付ID："
									+ onlinePayId
									+ "</td></tr><tr><td>";
							if (data.orderDetails == null
									|| data.orderDetails.length == 0) {
								detailTable += "没有详情</td></tr>";
							} else {
								var withdrawDict = {};
								for (i in data.withdraws) {
									//console.log(">>>>>>\t" + i + "\t" + data.withdraws[i]);
									var withdraw = data.withdraws[i];
									withdrawDict[withdraw.orderDetailId] = withdraw;
								}

								detailTable += "<table border='1'><tr><td>名称</td><td>sku信息</td><td>单价</td><td>数量</td><td>创建时间</td><td>退款数量</td><td>应退金额</td><td>退款时间</td><td>退款原因</td></tr>";
								$
										.each(
												data.orderDetails,
												function(i, orderDetail) {
													var withdraw = withdrawDict[orderDetail.childOrderDetailId];
													var withdrawDate = '';
													if (typeof (withdraw) === 'undefined') {
														withdraw = {};
														withdraw.skuWithdrawNum = 0;
														withdraw.createTime = 0;
														withdraw.reason = '';
														withdraw.skuUnitPrice = 0;
													} else {
														if (withdraw.createTime != 0) {
															var date = new Date();
															date
																	.setTime(withdraw.createtime);
															//console.log(withdraw.createtime);
															withdrawDate = date
																	.Format("yyyy-MM-dd hh:mm:ss");
														}
													}
													
													var wmsGoods4BgSkuInfo;
													
													var wmsGoods4BgSkuList=orderDetail.wmsGoods4BgSkuList;
													for(var t=0;t<wmsGoods4BgSkuList.length;t++){
														var wmsGoods4BgSku = wmsGoods4BgSkuList[t];
														if(t==0){
															wmsGoods4BgSkuInfo=wmsGoods4BgSku.wmsGoodsName+"("+wmsGoods4BgSku.wmsGoodsGbm+")*"+wmsGoods4BgSku.skuNum
														}else{
															wmsGoods4BgSkuInfo=wmsGoods4BgSkuInfo+"<br>"+wmsGoods4BgSku.wmsGoodsName+"("+wmsGoods4BgSku.wmsGoodsGbm+")*"+wmsGoods4BgSku.skuNum
														}
														
													}


													var createTime = new Date();
													createTime
															.setTime(orderDetail.createTime);
													detailTable += "<tr>"
															+ "<td>"
															+ orderDetail.skuName
															+ "</td>"
															+ "<td>"
															+ wmsGoods4BgSkuInfo
															+ "</td>"
															+ "<td>"
															+ orderDetail.skuPrice / 100.0
															+ ""
															+ /* "/" + orderDetail.skuSubName+ */"</td>"
															+ "<td>"
															+ orderDetail.skuNum
															+ "</td>"
															+
															/* "<td>"+orderDetail.skuPrice/100.0+"</td>" + */
															/* "<td>"+orderDetail.detailCode+"</td>" + */
															"<td>"
															+ createTime
																	.Format("yyyy-MM-dd hh:mm:ss")
															+ "</td>"
															+ "<td>"
															+ withdraw.skuWithdrawNum
															+ "</td>"
															+ "<td>"
															+ withdraw.skuWithdrawNum
																	* withdraw.skuUnitPrice
																	/ 100.0
															+ "</td>"
															+ "<td>"
															+ withdrawDate
															+ "</td>"
															+ "<td>"
															+ withdraw.reason
															+ "</td>";
													"</tr>";
												});
								detailTable += "</table></td></tr>";
								detailTable += '<tr><td><input type="button"  value="退款" onclick="withdraw(\''
										+ orderId + '\');" ></td></tr>'
							}
							if (data.refundinfos == null
									|| data.refundinfos.length == 0) {
								detailTable += "<tr><td>没有退款信息</td></tr>";
							} else {
								detailTable += "<tr><td>退款记录:</td></tr><tr><td><table border='1'><tr><td>申请退款时间</td><td>申请退款金额</td><td>实际退款金额</td><td>退款原因</td><td>退款结果</td><td>退款信息</td><td>退款时间</td></tr>";
								var summoney = 0;
								var notifysummoney = 0;
								$
										.each(
												data.refundinfos,
												function(i, refundinfo) {
													summoney += refundinfo.refundMoney;
													notifysummoney += refundinfo.notifyMondy;
													detailTable += "<tr>"
															+ "<td>"
															+ refundinfo.applyDate
															+ "</td>"
															+ "<td>"
															+ refundinfo.refundMoney
															/ 100.0
															+ "</td>"
															+ "<td>"
															+ refundinfo.notifyMondy
															/ 100.0
															+ "</td>"
															+ "<td>"
															+ refundinfo.refundReason
															+ "</td>"
															+ "<td>"
															+ refundinfo.refundResult
															+ "</td>"
															+ "<td>"
															+ refundinfo.refundMessage
															+ "</td>"
															+ "<td>"
															+ refundinfo.notifyDate
															+ "</td>";
													"</tr>";
												});
								detailTable += "<tr>" + "<td>合计</td>" + "<td>"
										+ summoney / 100.0 + "</td>" + "<td>"
										+ notifysummoney / 100.0 + "</td>"
										+ "<td></td>" + "<td></td>"
										+ "<td></td>" + "<td></td>";
								"</tr>";
								detailTable += "</table></td></tr>";
							}

							var nextTr = $(obj).parent().parent().next();
							var nextObjClassName = nextTr.prop("class");
							if (nextObjClassName == undefined
									|| nextObjClassName.indexOf("yellowbox") < 0) {
								$(obj).parent().parent().after(detailTable);
							}
						});
	}

	function hideOrderDetail(obj, orderId, onlinePayId) {
		$(obj)
				.attr(
						"onclick",
						"showOrderDetail(this,'" + orderId + "','"
								+ onlinePayId + "')");
		var nextTr = $(obj).parent().parent().next();
		if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
			nextTr.remove();
		}
	}

	function withdraw(orderId) {
		$.post("ajaxOrderDetail", {orderId : orderId}, function(data) {
			$('#withdrawList').html($('#withdrawTemplate').render(data));
			var str = "";
			var item = null;
			var reasonFirst = data.reasonFirst;
			var reasonSecond = data.reasonSecond;
			for(var i = 0;i< reasonFirst.length;i++){
				item = reasonFirst[i];
				if(item != null){
					str += '<option value="'+item.withdrawReasonId+'">'+item.withdrawReasonName+'</option>';
				}
			}
			str += '<option value="0">其它</option>';
			$('#reasonFirst option').remove();
			$('#reasonFirst').append(str);
			str = "";
			for(var i = 0;i< reasonSecond.length;i++){
				item = reasonSecond[i];
				if(item != null){
					str += '<option value="'+item.withdrawReasonId+'">'+item.withdrawReasonName+'</option>';
				}
			}
			$('#reasonSecond option').remove();
			$('#reasonSecond').append(str);
			
			$('#modalEdit1').modal();
		},'json');
	}
	

	$("#reasonFirst").change(function(){
		var secondId = $('#reasonFirst option:selected').val();
		if(secondId == 0){
			$('#reasonSecond').hide();
			$('#reasonSecondOther').show();
		}else{
			$.post('/order/getWithdrawReasonByPid',{"pid":secondId},function(json){
				if(json.code == 0){
					var reasons = json.data;
					var item = null;
					var str = "";
					for(var i = 0;i< reasons.length;i++){
						item = reasons[i];
						if(item != null){
							str += '<option value="'+item.withdrawReasonId+'">'+item.withdrawReasonName+'</option>';
						}
					}
					$('#reasonSecond option').remove();
					$('#reasonSecond').append(str);
					$('#reasonSecondOther').hide();
					$('#reasonSecond').show();
				}else{
					alert(json.msg);
				}
			},'json');
		}
	});
</script>