<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">


<div class="modal fade" id="applyPayModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
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
				<form class="form-horizontal" id="applyPayForm" role="form" onsubmit="return false;">
						
					
					
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">申请人</label>
				        <div class="col-sm-3">
				        	<p style="padding-top:8px; ">${user.user_name}</p>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">仓库</label>
				       	<div class="col-sm-3">
				        		<p style="padding-top:8px;" id="applystorage"></p>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">供应商</label>
				        <div class="col-sm-3">
				        	<p style="padding-top:8px;" id="applyseller"></p>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">记录数</label>
				        <div class="col-sm-3">
				        	<p style="padding-top:8px;" id="applynum"></p>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">总额</label>
				        <div class="col-sm-3">
				        	<p style="padding-top:8px;" id="applysummoney"></p>
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
        <button type="button" class="btn btn-primary" id="applypayBut">申请结算</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>	
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<script type="text/javascript">



$('#applycheck').click(function() {
	$("#applystorage").html($("#storageText").text());
	$.post("applycheck",{"searchStr":$("#searchStr").val().trim(),"storageid":storageid,"recordtype":recordtype,"startDate":$('#start-time').val(),"endDate":$('#end-time').val()},function(response){
		if (response.code == "0") {
			var data = response.data;	
			$("#applyseller").text(data.sellername);
			$("#applynum").text(data.num);
			$("#applysummoney").text(data.summoney/100.0);
			
	        $('#applyPayModel').modal();

		} else {
			alert(response.msg);
		}
	});
});

$('#applypayBut').click(function() {
	$.post("applypay", {"searchStr":$("#searchStr").val().trim(),"storageId":storageid,"recordtype":recordtype,"startDate":$('#start-time').val(),"endDate":$('#end-time').val()}, function(response){
		if (response.code == "0") {
			alert("提交成功");
	        $('#applyPayModel').modal('hide');
	        table.ajax.reload(null, false);
	        locktable.ajax.reload(null, true);

		} else {
			alert(response.msg);
		}
	});
});
</script>
