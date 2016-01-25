<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 秒杀专题上单
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
				<table id="bgGoodsList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th><input type="checkBox" id="checkAll">序号</th>
							<th>商品名称</th>
							<th>备注</th>
							<th>图片</th>
							<th>类型</th>
							<th style='width:50px'>原价</th>
							<th style='width:50px'>活动价</th>
							<th style='width:50px'>限售数量</th>
							<th style='width:50px'>权重</th>
							<th>描述</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	<!--/span-->

	<div class="pl15">
		<button type="button" class="btn btn-primary btn-sm" id="addButton">批量设置时间并添加至活动</button>
	</div>

</div>
<!--/row-->
<%@ include file="addseckillgoods.jsp"%>
<%@ include file="../footer.jsp"%>


<script>
	var table;
	$(document)
			.ready(
					function() {
						//datatable
						table = $('#bgGoodsList')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											searching : true,
											ajax : {
												url : "querybggoods",
												data : function(d) {
													d.activityType = 1;
												},
												dataSrc : function(json) {
													var newData = [];
													var seq = json.start + 1;
													for ( var i = 0, len = json.data.length; i < len; i++) {
														var bgGoods = json.data[i];
														var bgGoodsName = bgGoods.bgGoodsName;
														var goodsType = "未知";
														if (bgGoods.bgGoodsType == 1) {
															goodsType = "单品"
														} else if (bgGoods.bgGoodsType == 2) {
															goodsType = "组合品"
														} 
														newData[newData.length] = [
																'<input type="checkBox" name="bggoodsId" value="'+bgGoods.bgGoodsId+'">'
																		+ (seq + i),
																bgGoodsName,
																bgGoods.bgGoodsRemark,
																"<a href="+bgGoods.imageUrl+">查看图片</a>",
																goodsType,
																"<input type='text' style='width:50px' value='"+bgGoods.originPrice+"' id='originPrice"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' id='activityprice"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' id='stock"+bgGoods.bgGoodsId+"'>",
																"<input type='text' style='width:50px' id='weight"+bgGoods.bgGoodsId+"'>",
																bgGoods.bgGoodsDesc,
																'<a class="blueletter" href="javascript:void(0)" onclick="addToActivity('
																		+ bgGoods.bgGoodsId
																		+ ')">添加至活动</a>' ];
													}
													json.data = newData;
													return newData;
												}
											},
											sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
											sPaginationType : "bootstrap",
											oLanguage : {
												sLengthMenu : "_MENU_ 记录每页",
												sSearch : "按名称搜索 _INPUT_",
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

	function addToActivity(goodsId) {
		if(!checkDate(goodsId.toString())){
			return;
		}
		$("#showtime").val('');
		$("#begintime").val('');
		$("#endtime").val('');
		$('#bgGoodsId').val(goodsId);
		$('#modalEdit').modal();
	}

	//全选
	$("#checkAll").click(function() {
		if ($("#checkAll").prop("checked") == true) {
			$("input[name='bggoodsId']").each(function() {
				$(this).prop("checked", true);
			});
			//alert();
		} else {
			$("input[name='bggoodsId']").each(function() {
				$(this).prop("checked", false);
			});
		}
	});

$("#addButton").click(function(){
	$("#showtime").val('');
	$("#begintime").val('');
	$("#endtime").val('');
		//获取勾选的资产的ID集合
		var bggoodsIds = "";
		$("input[name=bggoodsId]").each(function() {
			if ($(this).prop("checked")) {
				bggoodsIds += $(this).val() + ",";
			}
		});
		if (bggoodsIds == "") {
			alert("请选择要批量修改商品~");
			return;
		}
		if(!checkDate(bggoodsIds.toString())){
			return;
		}
		$('#bgGoodsId').val(bggoodsIds);
		$('#modalEdit').modal();

	});
function checkDate(bggoodsIdStr){
	//金额正则表达式
	var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	 
	var bggoodsIds = bggoodsIdStr.split(",");
	for(var i=0;i<bggoodsIds.length;i++){
		var bggoodsId=bggoodsIds[i];
		if(bggoodsId==""){
			continue;
		}
		var originprice = $("#originPrice"+bggoodsId).val();
		if(originprice==''||!exp.test(originprice)||originprice<=0){
			alert("原价错误~");
			$('#modalEdit').modal('hide');
			$("#originPrice"+bggoodsId).focus();
			return false;
		}
		
		var activityprice = $("#activityprice"+bggoodsId).val();
		if(activityprice==''||!exp.test(activityprice)||activityprice<=0){
			alert("活动价错误~");
			$('#modalEdit').modal('hide');
			$("#activityprice"+bggoodsId).focus();
			return false;
		}
		
		var stock = $("#stock"+bggoodsId).val();
		if(stock==''||!/^[0-9]*$/.test(stock)||stock<1){
			alert("限售数量错误~");
			$('#modalEdit').modal('hide');
			$("#stock"+bggoodsId).focus();
			return false;
		}
		
		var weight = $("#weight"+bggoodsId).val();
		if(weight==''||!/^[0-9]*$/.test(weight)||weight<1){
			alert("权重错误~");
			$('#modalEdit').modal('hide');
			$("#weight"+bggoodsId).focus();
			return false;
		}
	}
	return true;
}
</script>