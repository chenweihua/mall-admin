<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href='/css/style.css' rel="stylesheet" >

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
			<div class="box-content">
				<form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">					
						
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">数量</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="count" name="count" >
							<input type="hidden" id="recordId" name="recordId" >
				        </div>
					</div>
					
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">价格</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="price" name="price" >
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">供应商</label>
				       	<div class="col-sm-3">
				       		<select  class="form-control" id="sellerId" name="sellerId">
								<c:forEach var="seller" items="${sellerList}">
									<c:choose> 
										<c:when test="${seller.isDel eq 0}">
											<option value="${seller.sellerId}">${seller.sellerName}</option>
										</c:when>
									</c:choose>
								</c:forEach>	
		              		</select>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">备注</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="remark" name="remark" style="width:300px" placeholder="请输入库存调整原因">
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

<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>	
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<script type="text/javascript">

$('#btnSubmitEdit').click(function() {
	
    var msg = checkParam();
	    if(msg != null){
			 alert (msg);
			 return ;
	}
	$.post("modifyrecord", $("#editForm").serialize(), function(response){
		if (response.code == "0") {
			alert("修改成功");
	        $('#modalEdit').modal('hide');
	        table.ajax.reload(null, false);	    	
		} else {
			alert(response.msg);
		}
		document.getElementById("loading-editgood").style.display="none";		
	});
});
    
$('#btnSubmitPut').click(function() {
    	$.post("defaultstorage",{"dstorageId":$("#dstorageId").val()},function(response){
    		
    		if (response.code == -1) {
    			alert(response.msg);  
    			window.location.href=urlPrefix+"home/index";
    		}	
    	},"json");
    	document.getElementById("loading-editgood").style.display="";
	$.post("modifyrecord", $("#modifyRecordForm").serialize(), function(response){
		if (response.code == "0") {
			alert("修改成功");
	        $('#modalEdit').modal('hide');
	        table.ajax.reload(null, false);	    	
		} else {
			alert(response.msg);
		}
		document.getElementById("loading-editgood").style.display="none";
		
	});
});
     
</script>
