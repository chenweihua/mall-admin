<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<div class="modal fade" id="add2ActivityEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog" style="width: 500px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">添加至活动</h4>
			</div>
			<div class="modal-body">
				 <form class="form-horizontal" id="add2ActivityForm" role="form" onsubmit="return false;">
					<input type="hidden" id="bgGoodsId4Activity" name="bgGoodsId" value="">
					
					<div class="form-group">
				    	<label for="activityType" class="col-sm-4 control-label">活动类型</label>
				        <div class="col-sm-8">
				        	<select id="activityType" name="activityType" style="padding:5px 3px;margin-top: 5px;width: 200px;">
				        		<option <c:if test="${activity == null || activity.activityType == 1}">selected="selected"
				        				</c:if> value="1">日常活动</option>
				        		<option <c:if test="${activity.activityType == 0}">selected="selected"
				        				</c:if> value="0">秒杀活动</option>
				        	</select>  </div>
					</div>
					
					<div class="form-group">
				    	<label for="activityId" class="col-sm-4 control-label">活动名称</label>
				        <div class="col-sm-7">
				        	<select id="activityId" name="activityId" style="padding:5px 3px;margin-top: 5px;width: 200px;">
				        	</select>  
				        </div>
					</div>
					<div class="form-group">
				    	<label for="" class="col-sm-4 control-label">商品类型</label>
				        <div class="col-sm-7">
				        	<select id="storageType" name="storageType" style="padding:5px 3px;margin-top: 5px;width: 200px;">
				                	<option value="0" selected="selected">RDC商品</option>
				                	<option value="1" >LDC商品</option>
				                	<option value="2" >第三方商品</option>
				            </select>
				        </div>
					</div>
					
					<div id="isSecKill" style="display: none">
						<div class="form-group">
							<label for="showTime" class="col-sm-4 control-label">活动显示时间</label>
							<div class="col-sm-7">
								<input type="text"  id="showTime" name="showTime" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label for="showTime" class="col-sm-4 control-label">活动开始时间</label>
							<div class="col-sm-7">
								<input type="text" id="beginTime" name="beginTime" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label for="showTime" class="col-sm-4 control-label">活动结束时间</label>
							<div class="col-sm-7">
								<input type="text" id="endTime"	name="endTime" class="form-control">
							</div>
						</div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-4 control-label">商品原价</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="originPrice4Activity" name="originPrice" value="" placeholder="原价">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-4 control-label">商品活动价</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="activityPrice" name="price" value="" placeholder="活动价">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-4 control-label">权重</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="weight4Activity" name="weight" value="" placeholder="权重">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-4 control-label">限售数量</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="skuStock" name="skuStock" value="" placeholder="限售数量">
				        </div>
					</div>
					<div class="form-group">
				    	<label for="skuName" class="col-sm-4 control-label">限购数量</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="maxNum4Activity" name="maxNum" value="" placeholder="限购数量">
				        </div>
					</div>
				 </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="add2ActivitySubmit">提交</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$('#showTime').datetimepicker({
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

$('#beginTime').datetimepicker({
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

$('#endTime').datetimepicker({
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

$('#activityType').change(function(){
	var activityType = $('#activityType option:selected').val();
	$.post('/activity4MultiGoods/getActivities/byActivityType',{"activityType":activityType},function(json){
		if(json.code == 0){
			var activities = json.data;
			var activity = null;
			var str = '';
			for(var i = 0;i< activities.length;i++){
				activity = activities[i];
				if(activity != null){
					str += '<option value="'+activity.activityId+'">'+activity.activityName+'</option>';
				}
			}
			$('#activityId option').remove();
			$('#activityId').append(str);
		}else{
			alert(json.msg);
		}
	},'json');
	
	if(activityType == 0){
		//秒杀
		$('#isSecKill').show();
	}else{
		$('#isSecKill').hide();
	}
});

$('#add2ActivitySubmit').click(function(){
	if(checkParam()){
		$.post("/activity4MultiGoods/addMultiBgGoods", $("#add2ActivityForm").serialize(), function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				alert(json.msg);
				$('#add2ActivityEdit').modal('hide');
			}
		});
	}
});

function checkParam(){
	var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	var activityType = $('#activityType option:selected').val();
	if(activityType == 0){
		//秒杀
		if($("#showTime").val() ==''){
			alert("请选择显示时间~");
			return false;
		}
		if($("#beginTime").val() ==''){
			alert("请选择开始时间~");
			return false;
		}
		if($("#endTime").val() ==''){
			alert("请选择结束时间~");
			return false;
		}
	}
	var originPrice = $("#originPrice4Activity").val();
	if(originPrice == ''||!exp.test(originPrice) || originPrice<=0){
		alert("原价错误~");
		$("#originPrice").focus();
		return false;
	}
	var price = $("#activityPrice").val();
	if(price == ''||!exp.test(price) || price<=0){
		alert("活动价错误~");
		$("#price").focus();
		return false;
	}
	
	var weight = $("#weight4Activity").val();
	if(weight == '' || !/^[0-9]*$/.test(weight) || weight < 1){
		alert("权重错误~");
		$("#weight").focus();
		return false;
	}
	
	var stock = $("#skuStock").val();
	if(stock==''||!/^[0-9]*$/.test(stock)||stock<0){
		alert("限售数量错误~");
		$("#stock").focus();
		return false;
	}
	
	var maxNum = $("#maxNum4Activity").val();
	if(maxNum == '' || !/^[0-9]*$/.test(maxNum) || maxNum < 1 ){
		alert("限购数量错误~");
		$("#maxNum").focus();
		return false;
	}
	return true;
}
</script>