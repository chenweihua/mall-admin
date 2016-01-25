<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 优惠券发放记录
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
				<table id="couponGiveLogList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>优惠券批次号</th>
							<th>用户ID</th>
							<th>手机号</th>
							<th>金额</th>
							<th>状态</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
		<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">返回</button>
	</div>
	<!--/span-->


</div>
<!--/row-->
<%@ include file="../../footer.jsp"%>


<script>

	var table = null;

	function getStatusDesc(status) {
		var statusDesc = '';
		switch(status) {
			case '0':
				statusDesc = "未发送";
				break;
			case '1':
				statusDesc = "已发送";
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
						table = $('#couponGiveLogList')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											ajax : {
												url : "queryLog?couponGiveId=${requestScope.couponGiveId}",
												dataSrc : function(json) {
													var newData = [];
													for ( var i = 0, len = json.data.length; i < len; i++) {
														var couponGiveLog = json.data[i];
														
														var statusDesc = getStatusDesc(couponGiveLog.status);
														
														newData[newData.length] = [
																couponGiveLog.couponBatchId,
																couponGiveLog.userId,
																couponGiveLog.phoneNo,
																(couponGiveLog.money / 100),
																statusDesc ];
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
	

	
</script>