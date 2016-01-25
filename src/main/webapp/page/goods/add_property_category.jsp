<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<div class="modal fade" id="propertyCategoryEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog" style="width:400px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">创建商品属性/类型</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							 <form class="form-horizontal" id="addPropertyCategory" role="form" onsubmit="return false;">
								<input type="hidden" id="pid" name="pid" value="">
								<input type="hidden" id="level" name="level" value="">
								
								<div class="form-group">
							    	<label for="skuName" class="col-sm-4 control-label">商品属性名称</label>
							        <div class="col-sm-8">
							        	<input type="text" class="form-control" id="propertyCategoryName" name="propertyCategoryName" value="" placeholder="商品属性名称">
							        </div>
								</div>
								<div class="form-group">
							    	<label for="skuName" class="col-sm-4 control-label">商品属性权重</label>
							        <div class="col-sm-8">
							        	<input type="text" class="form-control" id="weight" name="weight" value="" placeholder="商品属性权重">
							        </div>
								</div>
							 </form>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="propertyCategorySubmit">提交</button>
			</div>
		</div>
	</div>
</div>
<script>
$('#propertyCategorySubmit').on('click',function(){
	$.post('/goods/property/category/add',$('#addPropertyCategory').serialize(),function(json){
		if(json.code == 0){
			var item = json.data;
			var level = $('#level').val();
			var str = '<li pcId="'+item.propertyCategoryId+'">'+item.propertyCategoryName+'</li>';
			if(level == 1){
				$('#pCategory1').append(str);
			}else if(level == 2){
				$('#pCategory2').append(str);
			}
			$('#propertyCategoryEdit').modal('hide');
			$('#propertyCategoryName').val("");
			$('weight').val("");
		}else{
			alert(json.msg);
		}
	});
});
</script>