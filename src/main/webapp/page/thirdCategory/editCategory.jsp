<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href='/css/style.css' rel="stylesheet" >


<div class="modal fade" id="thirdModalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="categoryModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
			<div class="box-content">
				<form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
					<input type="hidden" id="propertyCategoryId" name="propertyCategoryId" value="0">	
					<input type="hidden" id="pid" name="pid" value="0">
					<input type="hidden" id="level" name="level" value="0">				
					<div class="form-group">
				    	<label for="categoryName" class="col-sm-3 control-label">类目名称：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control categoryRequest" id="propertyCategoryName" name="propertyCategoryName" placeholder="类目名称" style="width:300px">
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
    $("#btnSubmitEdit").click(function(){ 
   		if (checkParam()) {
   			$.post("addEditCategory", $("#editForm").serialize(), function(json) {
   				if (json.code != 0) {
   					alert(json.msg);
   				} else {
   					$('#thirdModalEdit').modal('hide');
   					alert("操作成功");
   					table.ajax.reload();
   					if($("#level").val() == 3) {
   						setTimeout('getThirdCategory('+$('#pid').val()+')', 1000);
   					}
   				}
   			});
   		}
    	
    });
    
    //验证数据格式
    function checkParam(){
    	for (var i = 0; i < $(".getThirdCategory").length; i++) {
			if ($.trim($(".getThirdCategory").eq(i).val()).length == 0) {
				alert($(".getThirdCategory").eq(i).attr('placeholder') + "是必填项");
				return false;
			}
		}
		return true;

    }
    
</script>
