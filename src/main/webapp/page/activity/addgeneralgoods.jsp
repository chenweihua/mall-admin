<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link
	href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
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
								
								<div class="form-group">
							    	<label for="sellerId1" class="col-sm-3 control-label">所属活动</label>
							        <div class="col-sm-3">
							        	<select class="form-control" id="activity" name="activity" style="width:150px">
							        		<c:forEach var="activity" items="${activitylist}">
							                	<option value="${activity.activityId}" >${activity.activityName}</option>
							                </c:forEach>
							            </select>
							        </div>
								</div>
								
								<div class="form-group">
							    	<label for="sellerId1" class="col-sm-3 control-label">商品类型</label>
							        <div class="col-sm-3">
							        	<select class="form-control" id="storageType" name="storageType" style="width:150px">
							                	<option value="0" selected="selected">RDC商品</option>
							                	<option value="1" >LDC商品</option>
							                	<option value="2" >第三方商品</option>
							            </select>
							        </div>
								</div>
								
								<!-- <div class="input-group">
									<span class="input-group-addon">活动开始时间</span>
									<div class="col-sm-4">
										<input type="text" style="width: 180px" id="begintime"
											name="begintime" class="form-control">
									</div>
								</div>
								<br>

								<div class="input-group">
									<span class="input-group-addon">活动结束时间</span>
									<div class="col-sm-4">
										<input type="text" style="width: 180px" id="endtime"
											name="endtime" class="form-control">
									</div>
								</div>
								<br>
								
								<div class="input-group">
									<span class="input-group-addon">活动显示时间</span>
									<div class="col-sm-4">
										<input type="text" style="width: 180px" id="showtime"
											name="showtime" class="form-control">
									</div>
								</div> -->

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

<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
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
    format: 'yyyy-mm-dd hh:ii:ss'
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
      format: 'yyyy-mm-dd hh:ii:ss'
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
    format: 'yyyy-mm-dd hh:ii:ss'
});
	$('#btnSubmitEdit').click(
			function() {
				var info = [];
				var bggoodsIdStr=$("#bgGoodsId").val();
				if(bggoodsIdStr==""){
					alert("请选择商品~");
					return;
				}
			/* 	var begintime = $("#begintime").val();
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
				} */
				 var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
				
				var bggoodsIds = bggoodsIdStr.split(",");
				for(var i=0;i<bggoodsIds.length;i++){
					var bggoodsId=bggoodsIds[i];
					if(bggoodsId==""){
						continue;
					}
					var weight = $("#weight"+bggoodsId).val();
					if(weight==''||!/^[0-9]*$/.test(weight)||weight<1){
						alert("权重错误~");
						$('#modalEdit').modal('hide');
						$("#weight"+bggoodsId).focus();
						return;
					}
					var maxnum = $("#maxnum"+bggoodsId).val();
					if(weight==''||!/^[0-9]*$/.test(maxnum)||maxnum<1){
						alert("限购数量错误~");
						$('#modalEdit').modal('hide');
						$("#maxnum"+bggoodsId).focus();
						return;
					}
					//遍历商品下sku信息
					var skuInfo= getSkuInfo(bggoodsId);
					if(skuInfo.length==0){
						var originprice = $("#originPrice"+bggoodsId).val();
						if(originprice==''||!exp.test(originprice)||originprice<=0){
							alert("原价错误~");
							$('#modalEdit').modal('hide');
							$("#originPrice"+bggoodsId).focus();
							return;
						}
						
						var activityprice = $("#activityprice"+bggoodsId).val();
						if(activityprice==''||!exp.test(activityprice)||activityprice<=0){
							alert("活动价错误~");
							$('#modalEdit').modal('hide');
							$("#activityprice"+bggoodsId).focus();
							return;
						}
						
						var stock = $("#stock"+bggoodsId).val();
						if(stock==''||!/^[0-9]*$/.test(stock)||stock<0){
							alert("限售数量错误~");
							$('#modalEdit').modal('hide');
							$("#stock"+bggoodsId).focus();
							return;
						}
						info.push({'bgGoodsId':bggoodsId,'stock':stock,'weight':weight,'activityPrice':activityprice,'originPrice':originprice,'maxNum':maxnum});
					}else{
						info.push({'bgGoodsId':bggoodsId,'stock':0,'weight':weight,'activityPrice':0,'originPrice':0,'maxNum':maxnum,'skuListBean':skuInfo});
					}
					
				}
				var bgGoodsInfo = JSON.stringify(info);
				//alert($("#activity").val());
				//alert($("#storageType").val());
				$('#btnSubmitEdit').attr('disabled',"true");
				$.post("addbggoods",
					{"activityType":0,"bgGoodsInfo":bgGoodsInfo,"activityId":$("#activity").val(),"storageType":$("#storageType").val()},
					function(response) {
						$('#btnSubmitEdit').removeAttr("disabled");
					if (response.code == 0) {
						alert(response.msg);
						$('#modalEdit').modal('hide');
					} else {
						alert(response.msg);

					}
				},"json");

			});
	
	function getSkuInfo(bggoodsId){
		var skuinfo = [];
		var skuInfoList = $('#block'+bggoodsId).find('.skuinfo');
		if(!skuInfoList && typeof(skuInfoList)!="undefined"){
			return skuinfo;
		}
		 var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		for(var i=0;i<skuInfoList.length;i++){
	         var child =skuInfoList.eq(i).find('td');
	         var skuid = child.eq(0).find('input').eq(0).val();
	         var originprice=  child.eq(3).find('input').eq(0).val();
	         if(originprice==''||!exp.test(originprice)||originprice<=0){
					alert("原价错误~");
					$('#modalEdit').modal('hide');
					child.eq(3).find('input').eq(0).focus();
					return;
			}
			 var activityprice = child.eq(4).find('input').eq(0).val();
	         if(activityprice==''||!exp.test(activityprice)||activityprice<=0){
					alert("活动价错误~");
					$('#modalEdit').modal('hide');
					child.eq(4).find('input').eq(0).focus();
					return;
			}
	         var stock =  child.eq(5).find('input').eq(0).val();
	         if(stock==''||!/^[0-9]*$/.test(stock)||stock<=0){
					alert("限售数量错误~");
					$('#modalEdit').modal('hide');
					child.eq(5).find('input').eq(0).focus();
					return;
			}
	         skuinfo.push({'skuId':skuid,'stock':stock,'activityPrice':activityprice,'originPrice':originprice})
		}
		return skuinfo
	}
</script>
