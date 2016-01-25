<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<div class="modal fade" id="propertyValueEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog" style="width:400px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">创建规格值</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							 <form class="form-horizontal" id="addPropertyValue" role="form" onsubmit="return false;">
								<input type="hidden" id="propertyNameId" name="propertyNameId" value="">
								
								<div class="form-group">
							    	<label for="skuName" class="col-sm-4 control-label">规格值</label>
							        <div class="col-sm-8">
							        	<input type="text" class="form-control" id="propertyValue" name="propertyValue" value="" placeholder="属性值">
							        </div>
								</div>
								
								<div class="form-group">
							    	<label for="skuName" class="col-sm-4 control-label">展示顺序</label>
							        <div class="col-sm-8">
							        	<input type="text" class="form-control" id="showOrder2" name="showOrder" value="" placeholder="展示顺序">
							        </div>
								</div>
								
							 </form>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="propertyValueSubmit">提交</button>
			</div>
		</div>
	</div>
</div>
<script>
$('#propertyValueSubmit').on('click',function(){
	$.post('/goods/property/value/add',$('#addPropertyValue').serialize(),function(json){
		if(json.code == 0){
			var item = json.data;
			var str = '<li pvId="'+item.propertyValueId+'">'+item.propertyValue+'</li>';
			$('#pValue').append(str);
			$('#propertyValueEdit').modal('hide');
			$('#propertyValue').val("");
			$('#showOrder2').val("");
		}else{
			alert(json.msg);
		}
	});
});
</script>