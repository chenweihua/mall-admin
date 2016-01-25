<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href='/css/style.css' rel="stylesheet">

<link
	href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<form class="form-horizontal" id="editForm" role="form"
								onsubmit="return false;">
								<input type="hidden" id="bgGoodsId"/>			
								
								<div class="input-group">
									<span class="input-group-addon">发布时间</span>
									<div class="col-sm-4">
										<input type="text" style="width: 180px" id="showtime"
											name="showtime" class="form-control">
									</div>
								</div>					
								<br>
								<div class="input-group">
									<span class="input-group-addon">开始时间</span>
									<div class="col-sm-4">
										<input type="text" style="width: 180px" id="begintime"
											name="begintime" class="form-control">
									</div>
								</div>
								<br>

								<div class="input-group">
									<span class="input-group-addon">结束时间</span>
									<div class="col-sm-4">
										<input type="text" style="width: 180px" id="endtime"
											name="endtime" class="form-control">
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



<script type="text/javascript">
$('#showtime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:00'
});

$('#begintime').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'yyyy-mm-dd hh:ii:00'
  });

$('#endtime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:00'
});
	$('#btnSubmitEdit').click(
			function() {
				var bggoodsIdStr=$("#bgGoodsId").val();
				if(bggoodsIdStr==""){
					alert("请选择商品~");
					return;
				}
				var begintime = $("#begintime").val();
				if(begintime==''){
					alert("请选择开始时间~");
					return;
				}
				var endTime = $("#endtime").val();
				if(endTime==''){
					alert("请选择结束时间~");
					return;
				}
				var showTime = $("#showtime").val();
				if(showTime==''){
					alert("请选择显示时间~");
					return;
				}
				$.post("modifytime",
					{"activityBgGoodsIdsStr":bggoodsIdStr,"beginTime":begintime,"endTime":endTime,"showTime":showTime},
					function(response) {
						$("#checkAll").prop("checked", false);
					if (response.code == 0) {
						alert(response.msg);
						table.ajax.reload();
						$('#modalEdit').modal('hide');
					} else {
						alert(response.msg);
					}
				},"json");

			});

</script>
