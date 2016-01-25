<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script src="/bower_components/bootstrap/dist/js/bootstrap.js"></script>
<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

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
					<input type="hidden" id="methodtype">											
					<div class="form-group" id="citydiv4add">
				    	<label for="description" class="col-sm-3 control-label">地区</label>
				        <div class="col-sm-3">
				            
				          <div class="input-group">
		              		<div class="btn-group">
		                		<button type="button" class="btn btn-default dropdown-toggle"
		                  		data-toggle="dropdown">
		                  			<span id="editCityText">
		                  			${districtList.get(0).city.cityName} ${districtList.get(0).areas.get(0).cityName}
		                  			</span> <span
		                    		class="caret"></span>
		                		</button>
								<ul class="dropdown-menu" role="menu" id="editUlcity">
									<c:forEach var="district" items="${districtList}">
									<li class="dropdown-submenu"><a href="javascript:void(0);" class="editCity"
													data-val="${district.city.cityId}">${district.city.cityName}</a>
 										 <ul class="dropdown-menu">
									 		<c:forEach var="city" items="${district.areas}">
									 				<c:choose>
									 					<c:when test="${city.pid != 0}">
															<li><a href="javascript:void(0);" class="editArea"
															data-val="${city.cityId}">${city.cityName}</a></li>
														</c:when>
														<c:otherwise>
															<li><a href="javascript:void(0);" class="disabledArea"
															data-val="${city.cityId}">暂无区,请添加</a></li>
														</c:otherwise>
													</c:choose>
									 		</c:forEach>
									 	</ul>
									</li>
									</c:forEach>			
								</ul>	
								<input type="hidden" id="cityid" name="cityid">	
								<input type="hidden" id="areaid" name="areaid">	
		              	   </div>
		            	 </div>    
				      </div>
					</div>
					
					<div class="form-group" id="citydiv4edit">
						<label for="description" class="col-sm-3 control-label">地区</label>
						<div class="col-sm-3" >
				        	<input type="text" class="form-control" id="city_edit" style="width:200px">
				        </div>
				    </div>
					
					<div class="form-group" id="rdcdiv4add">
				    	<label for="description" class="col-sm-3 control-label">RDC</label>
				        <div class="col-sm-3" >
				        	<select class="form-control" id="rdc" name="rdcstorageid" style="width:150px" >
				        			<option value="0">无</option>
		                    		<c:forEach var="item" items="${rdcStorageList}">
									 	<option value="${item.storageId}">${item.storageName}</option>
									</c:forEach>	
				            </select>
				        </div>
					</div>
					<div class="form-group" id="rdcdiv4edit">
						<label for="description" class="col-sm-3 control-label">RDC</label>
						<div class="col-sm-3" >
				        	<input type="text" class="form-control" id="rdc_edit" >
				        </div>
				    </div>
					
					<div class="form-group"  id="ldcdiv4add">
				    	<label for="description" class="col-sm-3 control-label">LDC</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="ldc" name="ldcstorageid" style="width:150px" >
				        		<option value="0">无</option>
		                      	<c:forEach var="item" items="${ldcStorageList}">
									 <option value="${item.storageId}">${item.storageName}</option>
								</c:forEach>
				            </select>
				        </div>
					</div>
					
					<div class="form-group" id="ldcdiv4edit">
						<label for="description" class="col-sm-3 control-label">LDC</label>
						<div class="col-sm-3" >
				        	<input type="text" class="form-control" id="ldc_edit" >
				        	<input type="hidden" id="ldcstorageid4edit" name = "ldcstorageid4edit" >
				        </div>
				    </div>
				    				        							
					<div class="form-group" id="storeiddiv">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">门店id</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="storeid_edit" name="storeid" >
				        	<input type="hidden" id="method" name="method">
				        </div>
					</div>
					
					<div class="form-group" id="collegeiddiv">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">学校id</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="collegeid_edit" name="collegeid" >
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">学校名称</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="collegename_edit" name="collegename" style="width:300px">
				        </div>
					</div>
										
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">经度</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="longitude" name="longitude" placeholder="范围是0~180">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">纬度</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="latitude"  name="latitude" placeholder="范围是0~90">
				        </div>
					</div>
					
					<div class="form-group" id="div4Open">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">是否开启</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="isOpen" name="isOpen" style="width:150px" >
								<option value="1">正常营业</option>	
								<option value="0">休息中</option>
				            </select>
				        </div>
					</div>
					
					<div class="form-group" id="div4Show">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">是否展示</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="isShow" name="isShow" style="width:150px" >
								<option value="1">可见</option>	
								<option value="0">不可见</option>
				            </select>
				        </div>
					</div>
					
					<div class="form-group" >
				    	<label for="collegeidlabel" class="col-sm-3 control-label">rdc配送方式</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="rdcDeliveryType" name="rdcDeliveryType" style="width:150px" >
				        		<option value="3">无</option>
								<option value="0">自提</option>	
							<!--<option value="1">配送</option>
								<option value="2">自提+配送</option> -->
				            </select>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">rdc自提地址</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="rdcDeliveryAddress" name="rdcDeliveryAddress" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">ldc配送方式</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="ldcDeliveryType" name="ldcDeliveryType" style="width:150px" >
				        		<option value="3">无</option>
								<option value="0">自提</option>	
								<option value="1">配送</option>
								<option value="2">自提+配送</option>
				            </select>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">ldc自提地址</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="ldcDeliveryAddress" name="ldcDeliveryAddress" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">第三方配送地址</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="collegeAddr" name="collegeAddr" style="width:300px">
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


var editcityid = ${districtList.get(0).city.cityId};
var editareaid = ${districtList.get(0).areas.get(0).cityId};
var test;
$('.editArea').click(function(){
//	editcityid =$(this).data("val");
	editcityid=$(this).parent().parent().parent().find('a').first().data("val");
	var areaid = $(this).data("val");
	$('#cityid').val(editcityid);
	$('#areaid').val(areaid);
    $('#editCityText').html($(this).parent().parent().parent().find('a').first().text()+" "+$(this).text());


	$.post("ajaxLdcByCityId", {"cityId":editcityid}, function(response){
		if (response.code == 1) {
			alert(response.msg);
			$('#cityid').val(editcityid);
			$('#ldc').html("");
			$('#ldc').append('<option value="0" >无</option>');
		} else {
			var item = response.ldcStorages;
			$('#ldc').html("");
			$('#ldc').append('<option value="0" >无</option>');
			watch = item;
			for(var i=0;i<item.length;i++){
				$('#ldc').append('<option value="'+item[i].storageId+'" >'+item[i].storageName+'</option>');
			}
		}
	});
    
});

$('#btnSubmitEdit').click(function() {
	$('#cityid').val(editcityid);
	var msg = checkParam();
	    if(msg != null){
			 alert (msg);
			 return ;
		}
	    
	$('#btnSubmitEdit').attr('disabled',true);
	
	if($("#method").val() == 0){
		$.post("editCollege", $("#editForm").serialize(), function(response){
			if (response.code == 0) {
				alert(response.msg);
	        	table.ajax.reload(null, false);
	        	$('#modalEdit').modal('hide');
	        	$('#btnSubmitEdit').attr('disabled',false);
			} else if(response.code == 2){
				alert(response.msg);
				$("#collegeiddiv").show();
				$('#btnSubmitEdit').attr('disabled',false);
			} else if(response.code == 1){
				alert(response.msg);
				$('#btnSubmitEdit').attr('disabled',false);
			}
			else {
				alert(response.msg);
			}
		});
	}
	if($("#method").val() == 1){		
		$.post("addCollege", $("#editForm").serialize(), function(response){
			if (response.code == 0) {
				alert(response.msg);
	        	table.ajax.reload(null, false);
	        	$('#modalEdit').modal('hide');
	        	$('#btnSubmitEdit').attr('disabled',false);
			}else if(response.code == 2){
				alert(response.msg);
				$("#collegeiddiv").show();
				$('#btnSubmitEdit').attr('disabled',false);
			} else if(response.code == 1){
				alert(response.msg);
				$('#btnSubmitEdit').attr('disabled',false);
				
			}
			else {
				alert(response.msg);
				$('#btnSubmitEdit').attr('disabled',false);
			}
		});
	}
	
	
});

function checkParam(){
	
	var positveNumReg = /^[0-9]*[1-9][0-9]*$/ ;
	
	//七位小数
	var numReg =  /^-?\d+\.?\d{0,7}$/;
	
	if($('#storeid_edit').val().trim().length== 0){
		return "请输入门店id";
	}
	if($('#collegename_edit').val().trim().length== 0){
		return "请输入学校名称";
	}
	if(!positveNumReg.test($('#storeid_edit').val())){
		return "学校编码错误";
	}

	if($('#latitude').val().trim().length==0 || $('#longitude').val().trim().length==0){
		return "经纬度不能为空";
	}
	
	if(!numReg.test($('#latitude').val()) || !numReg.test($('#longitude').val()) || $('#latitude').val()>90 || $('#longitude').val()>180){
		return "经纬度格式不正确,小数点后不要超过七位";
	}
	//如果是添加，则必须选择rdc仓或者ldc仓
	if(	$("#methodtype").val()==0){
		if($('#ldc').val() == 0 && $('#rdc').val() == 0){
			return "rdc仓或ldc仓必须选一个";
		}
	}
	
//	if($('#ldc').val() != 0){
//		if($('#ldcDeliveryType').val() == 3){
//			return "ldc配送方式不能为空";
//		}
//		if($('#ldcDeliveryAddress').val().trim().length == 0){
//			return "ldc配送地址不能为空";
//		}
//	}
//	
//	if($('#rdc').val() != 0){
//		if($('#rdcDeliveryType').val() == 3){
//			return "rdc配送方式不能为空"
//		}
//		if($('#rdcDeliveryAddress').val().trim().length == 0){
//			return "rdc配送地址不能为空";
//		}
//	}
	
	return null;
}
    
</script>
