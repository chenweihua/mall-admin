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
					<div class="form-group" id="sellerdiv">
				    	<label for="selleridlabel" class="col-sm-3 control-label">编码</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="sellerid" name="id" >
				        	<input type="hidden" id="method" name="method">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">卖家名称</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="sellername" name="name" style="width:300px">
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
	var temp ;
    $("#btnSubmitEdit").click(function(){ 
    	var msg = checkSkuParam();
    	if(msg != null){
    		alert (msg);
    		return ;
    	}
    	if($("#method").val() == 0){
    		$.post("editSeller",{"sellerId":$("#sellerid").val(),"sellerName":$("#sellername").val()},function(response){	
    			temp = response;
    			if (response.code == 0) {
    				alert(response.msg);  
    	        	table.ajax.reload(null, false);
    	        	$('#modalEdit').modal('hide');
    			}
    			else if (response.code == 1){
    				alert(response.msg); 
    			}
    		},"json"); 
    	}
    	
    	if($("#method").val() == 1){
    		$.post("addSeller",{"sellerId":$("#sellerid").val(),"sellerName":$("#sellername").val()},function(response){		
    			if (response.code == 0) {
    				alert(response.msg);  
    	        	table.ajax.reload(null, false);
    	        	$('#modalEdit').modal('hide');
    			}	
    			else if (response.code == 1){
    				alert(response.msg); 
    			}
    		},"json"); 
    	}
    });
    
    //验证数据格式
    function checkSkuParam(){

    	if($('#sellername').val().trim().length== 0){
    		return "卖家名称不能为空";
    	}	

    	return null;
    }
    
</script>
