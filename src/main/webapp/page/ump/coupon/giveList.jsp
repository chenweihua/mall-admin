<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 优惠券发放列表
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
				<!-- <div class="alert alert-info">
					For help with such table please check <a
						href="http://datatables.net/" target="_blank">http://datatables.net/</a>
				</div> -->
				<table id="couponGiveList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>优惠券批次号</th>
							<th>用户类型</th>
							<th>发送方式</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	<!--/span-->


</div>
<!--/row-->
<%@ include file="../../footer.jsp"%>


<script>

	var table = null;

	function editCouponGive(couponGiveId) {
		window.location.href = "editCouponGive";
	}
	
	function viewCouponGive(couponGiveId) {
		window.location.href = "view?couponGiveId=" + couponGiveId;
	}
	
	function viewCouponGiveLog(couponGiveId) {
		window.location.href = "viewCouponGiveLog?couponGiveId=" + couponGiveId;
	}
	
	
	function delCouponGive(couponGiveId) {
		
		if (confirm("确定作废此记录")) {

			$.getJSON("del?couponGiveId=" + couponGiveId, function(ret) {
				if (ret.code != 0) {
					alert(ret.msg);
				} else {
					table.ajax.reload();
				}
			});
		}
	}
	
	function enableCouponGive(couponGiveId) {
		if (confirm("确定激活此记录")) {

			$.getJSON("enable?couponGiveId=" + couponGiveId, function(ret) {
				if (ret.code != 0) {
					alert(ret.msg);
				} else {
					table.ajax.reload();
				}
			});
		}
	}
	
	function getUserTypeDesc(userType) {
		var userTypeDesc = '';
		switch(userType) {
			case '0':
				userTypeDesc = "无限制";
				break;
			case '1':
				userTypeDesc = "新用户";
				break;
			case '2':
				userTypeDesc = "老用户";
				break;
			default:
				break;
		}
		return userTypeDesc;
	}
	
	function getGiveWayDesc(giveWay) {
		var giveWayDesc = '';
		switch(giveWay) {
			case '1':
				giveWayDesc = "按手机号发送";
				break;
			case '2':
				giveWayDesc = "按区域发送";
				break;
			case '3':
				giveWayDesc = "全部发送";
				break;
			default:
				break;
		}
		return giveWayDesc;
	}
	
	function getStatusDesc(status) {
		var statusDesc = '';
		switch(status) {
			case '0':
				statusDesc = "已激活待处理";
				break;
			case '1':
				statusDesc = "校验处理中";
				break;
			case '2':
				statusDesc = "校验失败";
				break;
			case '3':
				statusDesc = "处理失败";
				break;
			case '4':
				statusDesc = "处理完成";
				break;
			case '8':
				statusDesc = "待激活";
				break;
			case '9':
				statusDesc = "废弃";
				break;
			default:
				break;
		}
		return statusDesc;
	}
	

	$(document)
			.ready(
					function() {
						//datatable
						table = $('#couponGiveList')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											ajax : {
												url : "query",
												dataSrc : function(json) {
													var newData = [];
													for ( var i = 0, len = json.data.length; i < len; i++) {
														var couponGive = json.data[i];
														
														var userTypeDesc = getUserTypeDesc(couponGive.userType);
														var giveWayDesc = getGiveWayDesc(couponGive.giveWay);
														var statusDesc = getStatusDesc(couponGive.status);
														
														var operator = "";
														var status = couponGive.status;
														if(status == '8') {
															operator += '&nbsp;&nbsp;<a href="javascript:enableCouponGive('
																+ couponGive.id
																+ ');">激活</a>';
														} 
														if(status == '8' || status == '2' || status == '3') {
															operator += '&nbsp;&nbsp;<a href="javascript:delCouponGive('
																+ couponGive.id
																+ ');">作废</a>';
														}
														operator += '&nbsp;&nbsp;<a href="javascript:viewCouponGive('
																+ couponGive.id
																+ ');">查看</a>';
														
														operator += '&nbsp;&nbsp;<a href="javascript:viewCouponGiveLog(' 
															+ couponGive.id
															+');">查看发放记录</a>';
														
														newData[newData.length] = [
																couponGive.couponBatchId,
																//couponGive.receiveLimit,
																userTypeDesc,
																giveWayDesc,
																statusDesc,
																operator ];
													}
													json.data = newData;
													return newData;
												}
											},
											sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
											sPaginationType : "bootstrap",
											oLanguage : {
												sLengthMenu : "_MENU_ 记录每页",
												sSearch : "按优惠券批次号搜索 _INPUT_",
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
	

	
</script>