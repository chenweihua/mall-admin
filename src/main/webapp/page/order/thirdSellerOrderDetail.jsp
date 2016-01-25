<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<%@ include file="../header.jsp"%>

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

<!-- Modal 发货管理-->
<div class="modal fade" id="modalDeliverManager" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
	<input type="hidden" id="deliverChildOrderCode" />
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">请填写配送信息：</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="title" class="control-label">&nbsp;&nbsp;&nbsp;&nbsp;物流商:</label> 
					<select id="deliverCompany" style="width:30%;">
							<option value="">请选择</option>
						<c:forEach var="deliverCompany" items="${deliverCompanyList}">
							<option value="${deliverCompany.deliverCompanyCode }">${deliverCompany.deliverCompanyName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="title" class="control-label">物流单号:</label> 
					<input name="title" id="deliverNum" placeholder="">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="btnSubmitDelivery">提交</button>
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
					<i class="glyphicon glyphicon-banner"></i> 订单管理>订单详情
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
						<div class="input-group" style="width:180px;height:50px;padding-top:10px;">
							订单名称：
							<c:forEach var="item" items="${orderDetails}" varStatus="status">   
								     ${item.skuName}   
							</c:forEach>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="input-group" style="width:180px;height:50px;padding-top:10px;">
							子订单号码：${childOrder.childOrderId}
						</div>
					</div>
					<div class="col-lg-2">
						<div class="input-group">
							<button type="button" onclick="javascript:manageDelivery();" class="btn btn-default">物流管理</button>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="input-group" style="overflow-x:auto;">
							<button type="button" onclick="javascript:showOrderDetail();" class="btn btn-default">申请退款</button>
						</div>
					</div>
					<div class="col-lg-50">
							<div class="input-group" style="padding-top:10px;">
								<a href="#" onclick="javascript:void(0);backUrl();">返回</a>
							</div>
					</div>
				</div>
				<div style="overflow-x:auto;">
				<div>
					<a href="javascript:void(0);" id="orderWithdrawId" style="display:none">退款详情</a>
					<table id="orderWithdrawTableId" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					</table>
				</div>
				<div>订单信息</div>
				<table id="orderList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<td width="70px">小麦账户:</td>
							<td width="100px">${ucUser.username}</td>
							<td width="70px">母订单号:</td>
							<td width="120px">${childOrder.orderId}</td>
							<td width="70px">供应商：</td>
							<td width="100px">${suppiler.shopName}</td>
							<td width="100px">供应商订单号：</td>
							<td width="50px"></td>
						</tr>
						<tr>
							<td>创建时间:</td>
							<td><fmt:formatDate value="${childOrder.createTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
							<td>支付时间:</td>
							<td><fmt:formatDate value="${order.payTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
							<td>商品名称：</td>
							<td>
								<c:forEach var="item" items="${orderDetails}" varStatus="status">   
								     ${item.skuName}   
								</c:forEach> 
							</td>
							<td>支付平台:</td>
							<td>
								<c:if test="${order.onlinePayType eq '1'}">
									微信
								</c:if>
								<c:if test="${order.onlinePayType eq '2'}">
									支付宝
								</c:if>
								<c:if test="${order.onlinePayType eq '3'}">
									余额支付
								</c:if>
							</td>
						</tr>
						<tr>
							<td>订单来源:</td>
							<td>
								<c:if test="${order.device eq '0'}">
									APP
								</c:if>
								<c:if test="${order.device eq '1'}">
									H5
								</c:if>
							</td>
							<td>订单金额:</td>
							<td>${childOrder.totalPay/100.0}</td>
							<td>支付金额：</td>
							<td>${childOrder.totalPay/100.0}</td>
							<td>优惠券金额：</td>
							<td>${order.couponPay/100.0}</td>
						</tr>
						<tr>
							<td>支付流水号:</td>
							<td>${order.onlinePayId}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</thead>
				</table>
				<div>商品信息</div>
				<table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<td>商品名称</td>
							<td>子订单号</td>
							<td>商品规格</td>
							<td>商品类型</td>
							<td>数量</td>
							<td>价格</td>
							<td>订单状态</td>
							<td>备注</td>
						</tr>
						<c:forEach var="item" items="${orderDetails}" varStatus="status">   
							<tr>
								<td>${item.skuName}</td>
								<td>${childOrder.childOrderId}</td>
								<td>
									<c:forEach var="skuItem" items="${item.skuPropertySpecList}" varStatus="status">
										${skuItem}
									</c:forEach>
								</td>
								<td>${item.thirdCategory.propertyCategoryName}</td>
								<td>${item.skuNum}</td>
								<td>${item.skuPrice/100}</td>
								<td>
									<c:choose> 
										 <c:when test="${not empty withdraws}">
										 	   退款中
										 </c:when>
										 <c:when test="${not empty childOrder.deliverSheetCode}">   
										  	   已发货  
										 </c:when> 
										 <c:otherwise>   
										                待发货  
										 </c:otherwise> 
									</c:choose> 
								</td>
								<td>
									<c:if test="${not empty withdraws}">
										<c:forEach var="item1" items="${withdraws}" varStatus="status">   
										    <c:if test="${item.skuId eq item1.skuId}">
											     ${item1.reason}   
										    </c:if>
										</c:forEach> 
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</thead>
				</table>
				<div>优惠券信息</div>
				<table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<td>类型:</td>
							<td>优惠券</td>
							<td>优惠券金额:</td>
							<td>${order.couponPay}</td>
						</tr>
					</thead>
				</table>
				<div>物流信息</div>
				<table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<td>物流名称:</td>
							<td>${childOrder.deliverCompanyName}</td>
							<td>物流单号:</td>
							<td>${childOrder.deliverSheetCode}</td>
						</tr>
					</thead>
					<thead>
						<tr>
							<td>物流状态:</td>
							<td>
								<c:choose> 
									 <c:when test="${not empty childOrder.deliverSheetCode}">   
									  	   已发货  
									 </c:when> 
									 <c:otherwise>   
									                待发货  
									 </c:otherwise> 
								</c:choose> 
							</td>
							<td></td>
							<td></td>
						</tr>
					</thead>
				</table>
				<div>联系人信息</div>
				<table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<td>联系人:</td>
							<td>${childOrder.receiverName}</td>
							<td>电话:</td>
							<td>${childOrder.receiverPhone}</td>
						</tr>
					</thead>
					<thead>
						<tr>
							<td>收货地址:</td>
							<td colspan="3">${childOrder.deliveryAddress}</td>
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
<script>
	var urlPrefix = "";
	$("#btnSubmitDelivery").click(function(){
		var childOrderCode = $("#deliverChildOrderCode").val();
		var deliverCompanyCode = $("#deliverCompany").val();
		var deliverSheetCode = $("#deliverNum").val();
		if(childOrderCode == "" || deliverCompanyCode == "" || $.trim(deliverSheetCode) == "") {
			alert("请填写完整物流商和物流单号!");
			return;
		}
		$.post(urlPrefix+"manageDelivery?childOrderCode="+childOrderCode+"&deliverCompanyCode="+deliverCompanyCode+"&deliverSheetCode="+deliverSheetCode, null, function(response){
			if (response.code == 0) {
				alert("操作成功");
				window.location.href = urlPrefix + "getChildOrderDetail?childOrderId=" + '${childOrder.childOrderId}';
			} else {
				alert(response.msg);
			}
		});
		
	});
	
	function manageDelivery() {
		var childOrderCode = '${childOrder.childOrderCode}';
		var deliverCompanyCode = '${childOrder.deliverCompanyCode}';
		var deliverSheetCode = '${childOrder.deliverSheetCode}';
		$("#modalDeliverManager").modal();
		$("#deliverChildOrderCode").val(childOrderCode);
		$("#deliverCompany option[value=" + deliverCompanyCode  + "]").attr("selected","selected");
		$("#deliverNum").val(deliverSheetCode);
	}
	
	function showOrderDetail() {
		var orderId = '${childOrder.childOrderId}';
		var	onlinePayId = '${order.onlinePayId}';
		$('#orderWithdrawTableId').children().remove();
		$
				.post(
						"ajaxOrderDetail",
						{
							orderId : orderId
						},
						function(data) {
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
															date.setTime(withdraw.createtime);
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
							$('#orderWithdrawTableId').append(detailTable);
							$('#orderWithdrawId').show();
							$('#orderWithdrawTableId').show();
						});
	}
	
	$('#orderWithdrawId').click(function() {
		$('#orderWithdrawTableId').hide();
		$('#orderWithdrawId').hide();
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
				$('#modalEdit1').modal('hide');
				showOrderDetail();
			} else {
				alert(response.msg);
			}
		});

		$('#selectAll').attr('checked', false);
		$('#reason').val('');

	});
	
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

	function backUrl() {
		window.location.href = urlPrefix + "list";
	}
</script>