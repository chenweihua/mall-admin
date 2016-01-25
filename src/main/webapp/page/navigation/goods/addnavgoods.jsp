
<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width:800px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="form1">
							<input type="hidden" name="navigationId" id="navigationId" />
							
							<div class="form-group">
								<label for="navGoodsName" class="control-label">商品名称:</label>
								<input name="navGoodsName" id="navGoodsName" value="" placeholder="" style="width:50%;">
								<input name="navGoodsId" id="navGoodsId" type="hidden">
							</div>
							
							<div class="form-group">
				    			<label for="description" class="col-sm-3 control-label">类目</label>
						        	<select class="form-control" id="navMenuId" name="navMenuId" style="width:150px">
						        			<option value="-1" selected="selected">请选择类目</option>
				                        	<c:forEach var="navmenu" items="${navmenulist}">
				                        		<option value="${navmenu.navMenuId}">${navmenu.showName}</option>
				                        	</c:forEach>
						            </select>
							</div>
							<div class="form-group">
								<label for="navigationDesc" class="control-label">商品链接:</label>
								<textarea rows="5" cols="48" name="itemUrl" id="itemUrl"></textarea>
							</div>
							<div class="form-group" id="navigationUrlDIV">
								<label for="navigationUrl" class="control-label">推广链接:</label>
								<textarea rows="5" cols="48" name="clickUrl" id="clickUrl"></textarea>
							</div>
							<div class="form-group">
								<label for="weight" class="control-label">权重:</label>
								<input name="weight" id="weight" value="" placeholder="" style="width:50%;">
							</div>
						</form>
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" id="btnSubmitEdit">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" id="btnCancle">取消</button>
      </div>
    </div>
  </div>
</div>
<script>
$('#btnCancle').click(function(){
	$('#modalEdit').modal('hide');
});

$('#btnSubmitEdit').click(function(){
	
	if(!checkParam()) {
		return;
	}
	$.post("save", $("#form1").serialize(), function(response){
		if (response.code == 0) {
			alert("操作成功");
			selectGoodsPool.ajax.reload(null, true);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.msg);
		}
	});
	return false;
});

function checkParam(){
	if($("#navGoodsName").val()==""){
		alert("请输入商品名称");
		$("#navGoodsName").focus();
		return false;
	}
	if($("#navMenuId").val()==-1){
		alert("请选择类目");
		$("#navMenuId").focus();
		return false;
	}
	if($("#itemUrl").val()==""){
		alert("请输入商品链接地址");
		$("#itemUrl").focus();
		return false;
	}
	if($("#clickUrl").val()==""){
		alert("请输入商品推广链接地址");
		$("#clickUrl").focus();
		return false;
	}
	if($("#weight").val()==""){
		alert("请输入商品权重");
		$("#weight").focus();
		return false;
	}
	if (isNaN($("#weight").val())) { 
　　　　alert("权重请输入数字"); 
　　　　$("#weight").focus();
　　　　return false;
	} 
	return true;
}

</script>