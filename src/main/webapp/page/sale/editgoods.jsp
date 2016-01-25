<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link
	href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">


<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<form class="form-horizontal" id="goodsEditForm" role="form"
								onsubmit="return false;">

								<div class="form-group">
									<label for="collegeidlabel" class="col-sm-3 control-label">权重</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="weight" name="weight"> 
											<input type="hidden" id="modifytype" name="modifytype">
											<input type="hidden" id="goodsId" name="goodsId">
											<input type="hidden" id="storageId" name="storageId">
											<input type="hidden" id="storageType" name="storageType">
											<input type="hidden" id="collegeId" name="collegeId">
									</div>
								</div>

								<div class="form-group">
									<label for="collegeidlabel" class="col-sm-3 control-label">限售</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="stock"
											name="stock"><input type="checkbox"  id="limitstock" name="limitstock" style="float: left; zoom: 2;"/><p style="margin:9px">不限制</p>
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">状态</label>
									<div class="col-sm-3">
										<select id="status" name="status">
											<option value="1">待售</option>
											<option value="2">在售</option>
											<option value="3">售罄</option>
										</select>
									</div>
								</div>

							</form>
						</div>
					</div>
					<!--/span-->

				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="btnSubmitEdit">提交</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<script type="text/javascript">
var oldStock=0;
$("#limitstock").change(function(){
	if($("#limitstock").prop("checked")==true){
    	oldStock=$("#stock").val();
		$("#stock").prop("readonly",true);
		$("#stock").val("9999999");
	}else{
		$("#stock").prop("readonly",false);
		$("#stock").val(oldStock);
	}
});

	$("#btnSubmitEdit").click(function() {
		$("#btnSubmitEdit").attr("disabled", true);
		if($("#status").val()!=1&&$("#status").val()!=2&&$("#status").val()!=3){
			alert("请选择待修改的状态~");
			$("#btnSubmitEdit").removeAttr("disabled");
			return;
		}
		if($("#weight").val()==""||!/^[0-9]*$/.test($("#weight").val())||$("#weight").val()<0){
			alert("权重值错误~");
			$("#btnSubmitEdit").removeAttr("disabled");
			return;
		}
		if($("#stock").val()==""||!/^[0-9]*$/.test($("#stock").val())||$("#stock").val()<1){
			alert("限售数量错误~");
			$("#btnSubmitEdit").removeAttr("disabled");
			return;
		}
		
		if($("#modifytype").val()!="1"&&$("#modifytype").val()!="2"){
			alert("修改类型错误~");
			$("#btnSubmitEdit").removeAttr("disabled");
			return;
		}
		
		$.post("modify", $("#goodsEditForm").serialize(), function(result) {
			$("#btnSubmitEdit").removeAttr("disabled");
			if (result.code == 0) {
				alert(result.msg);
				if($("#modifytype").val()==1){
					var bggoodsId = result.data.bggoodsId;
			    	var blockId = "#block" + bggoodsId;
			    	var skuStockTable=fillTable(result.data.saleInCollege,result.data.saleSkuInfo);
				    $(blockId).html(skuStockTable); 
				}else{
					table.ajax.reload(null,false);
				}
				$('#modalEdit').modal('hide');
			} else {
				alert(result.msg);
			}
		}, "json");
	});
</script>
