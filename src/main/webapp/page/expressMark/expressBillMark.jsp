<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="modal fade" id="markModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog" style="width: 1000px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">面单标注</h4>
      </div>
      
      <div class="modal-body">
		<div class="row">
			<div class="box col-md-12">
				<div class="box-content">
					<img id="imageShow" alt="面单图片" src="">
					<br><br>
					<form class="form-horizontal" id="markForm" role="form" onsubmit="return false;">
						<div class="form-group">
					    	<label for="collegeidlabel" class="col-sm-2 control-label">姓名</label>
					        <div class="col-sm-4">
					        	<input type="text" class="form-control" id="name" name="name">
					        </div>
						</div>
						<div class="form-group">
					    	<label for="collegeidlabel" class="col-sm-2 control-label">手机号</label>
					        <div class="col-sm-4">
					        	<input type="text" class="form-control" id="phoneNo" name="phoneNo">
					        </div>
						</div>
						<div class="form-group">
					    	<label for="collegeidlabel" class="col-sm-2 control-label">学校</label>
					        <div class="col-sm-4">
					        	<input type="text" class="form-control" id="schoolName" name="schoolName">
					        </div>
						</div>
						<div class="form-group">
					    	<label for="collegeidlabel" class="col-sm-2 control-label">地址</label>
					        <div class="col-sm-4">
					        	<input type="text" class="form-control" id="address" name="address">
					        </div>
						</div>
					    <!-- 隐藏字段 -->
					    <input type="hidden" id="rowkey" name="rowkey">
					    <input type="hidden" id="mendianId" name="mendianId">
					    <input type="hidden" id="kuaidiId" name="kuaidiId">
					    <input type="hidden" id="kuaidiNo" name="kuaidiNo">
					    <input type="hidden" id="picurl" name="picurl">
					    <input type="hidden" id="time" name="time">
					</form>
				</div>
			</div>
		</div>
      </div>
      <div class="modal-footer">
         <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
         <button type="button" class="btn btn-primary" id="btnSubmitPut">提交</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">

$('#btnSubmitPut').click(function() {
	$.post('/express/bill/mark',$('#markForm').serialize(),function(ret){
		if(ret.code == 0){
			alert(ret.msg);
			window.location.href = "/express/bill/list";
		}else{
			alert(ret.msg);
		}
	});
});
</script>
