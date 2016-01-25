<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">


<div class="modal fade" id="copysku" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
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
				<form class="form-horizontal" id="copyskuform" role="form" onsubmit="return false;">
						
			        <input type="hidden" id="newcollege"/>
			       <div class="form-group">
			              <label for="status" class="col-sm-3 control-label">学校</label>
			              <div class="col-sm-3">
				        	<select class="form-control" id="college" name="deliveryType" style="width:200px">

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
<!--         <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
        <button type="button" class="btn btn-primary" id="btnCopySku">复制</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>	
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<script type="text/javascript">

    $("#btnCopySku").click(function(){
//     	alert($("#newcollege").val());
//     	alert($("#college").val());
    	var newcollege = $("#newcollege").val();
    	var fromcollege = $("#college").val();
    	$.post("copy",{"from":fromcollege,"newcollege":newcollege},function(result){
    	    if(result.code==0){
    	    	alert(result.msg);
    	        table.ajax.reload(null, false);
    	        $('#copysku').modal('hide');
    	    }else{
    	    	alert(result.msg);
    	    }
    	  },"json");	
    });
</script>
