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
								<input id="storageId" name="storageId" type="hidden" value="0">
								
								<div class="form-group">
									<label for="collegeidlabel" class="col-sm-3 control-label">商品名称</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="wms_goods_name"
											name="wms_goods_name"> <input type="hidden"
											id="wms_goods_id" name="wms_goods_id" value="-1">
									</div>
								</div>

								<div class="form-group">
									<label for="collegeidlabel" class="col-sm-3 control-label">商品简称</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="short_name"
											name="short_name">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">商品条码</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="wms_goods_gbm" name="wms_goods_gbm"
											style="width: 300px">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">品牌名称</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="brand"
											name="brand" style="width: 300px">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">商品类别</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="catg_name"
											name="catg_name" style="width: 300px">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">销售规格</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="sale_spec"
											name="sale_spec" style="width: 300px">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">单位</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="unit" name="unit"
											style="width: 300px">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">包装规格</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="package_spec"
											name="package_spec" style="width: 300px">
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">原产地</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="origin_place"
											name="origin_place" style="width: 300px">
									</div>
								</div>
								<div class="form-group">
									<label for="description" class="col-sm-3 control-label">保质期</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="shelf_life"
											name="shelf_life" style="width: 300px">
									</div>
								</div>

								<!-- 					<div class="form-group"> -->
								<!-- 			              <label for="status" class="col-sm-3 control-label">开启状态</label> -->
								<!-- 			              <div class="col-sm-3"> -->
								<!-- 				        	<select class="form-control" id="isDel" name="isDel"> -->
								<!-- 				        			<option value="0">开启</option> -->
								<!-- 				                	<option value="1">关闭</option> -->
								<!-- 				            </select> -->
								<!-- 				        </div> -->
								<!-- 			        </div> -->

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
	$("#btnSubmitEdit").click(function() {
		$("#btnSubmitEdit").attr("disabled", true);
		$.post("addoredit", $("#goodsEditForm").serialize(), function(result) {
			$("#btnSubmitEdit").removeAttr("disabled");
			if (result.code == 0) {
				alert(result.msg);
				table.ajax.reload(null, false);
				$('#modalEdit').modal('hide');
			} else {
				alert(result.msg);
			}
		}, "json");
	});
</script>
