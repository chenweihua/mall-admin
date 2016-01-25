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
				    	<label for="selleridlabel" class="col-sm-3 control-label">新密码</label>
				        <div class="col-sm-3">
				        	<input type="password" class="form-control" id="password1" name="password" >
				        	<input type="hidden" id="modifyuserId" name="modifyuserId">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">重复密码</label>
				        <div class="col-sm-3">
				      	  <input type="password" class="form-control" id="repassword1" name="repassword" >
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

<script type="text/javascript">
    
   $('#btnSubmitEdit').click(function() {
	   
   		$.post("modifypassword", $("#editForm").serialize(), function(response){
   			if (response.code == 0) {
   				alert(response.msg);
   				$('#modalEdit').modal('hide');
   			} else {
   				alert(response.msg);
   				
   			}
   		});
    	
    });
   
  
    
</script>
