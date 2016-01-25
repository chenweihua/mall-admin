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
					<div class="form-group" id="storageiddiv">
				    	<label for="selleridlabel" class="col-sm-3 control-label">仓库编码</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="storageid" name="storageid" >
				        	<input type="hidden" id="method" name="method">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">仓库名称</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="storagename" name="storagename" style="width:300px">
				        </div>
					</div>
					
				    <div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">仓库类型</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="storagetype" name="storagetype" style="width:150px" onchange="showCity(this.options[this.options.selectedIndex].value)">
		                        	<option value="0">RDC</option>
		                        	<option value="1">LDC</option>
		                        	<option value="2">虚拟仓</option>
				            </select>
				        </div>
					</div>
					
					<div class="form-group" id="ldcTypeDiv">
				    	<label for="ldcType" class="col-sm-3 control-label">LDC显示类型</label>
				        <div class="col-sm-3">
				        	<input id="ldcTypeId" type="checkbox" name="ldcType" value=""> 隐藏29分钟达
				        </div>
					</div>
					
					<div class="form-group" id="ldcOwnerTypeDiv">
				    	<label for="ldcOwnerType" class="col-sm-3 control-label">LDC所属类型</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="ldcOwnerType" name="ldcOwnerType" style="width:150px">
		                        	<option value="1">小麦</option>
		                        	<option value="2">第三方商超</option>
				            </select>
				        </div>
					</div>
					
					<div class="form-group" id="citydiv">
				    	<label for="description" class="col-sm-3 control-label">所属城市</label>
				        <div class="col-sm-3">
				        
				        	<select class="form-control" id="cityid" name="cityid" style="width:150px">
				        		<c:forEach var="city" items="${cityList}">
									<option value="${city.cityId}">${city.cityName}</option>
								</c:forEach>	
				            </select>
				        </div>
					</div>
					<div id="vFreight">
						<div class="form-group" id="freightType">
					    	<label for="description" class="col-sm-3 control-label">邮费类型</label>
					        <div class="col-sm-6" style="padding-top: 8px">
						        <input type="radio" name="freightType" value="1" checked="checked">自定义类型&nbsp;&nbsp;
						        <input type="radio" name="freightType" value="2">卖家承担邮费
					        </div>
						</div>
						
						<div class="form-group" id="freightSubType">
					    	<label for="description" class="col-sm-3 control-label">计价方式</label>
					        <div class="col-sm-6"  style="padding-top: 8px">
						        <input type="radio" name="freightSubType" value="1" checked="checked">按金额&nbsp;&nbsp;
						        <input type="radio" name="freightSubType" value="2">满免邮费
					        </div>
						</div>
						
						<div class="form-group" id="freightSubContent">
					        <label for="description" class="col-sm-3 control-label"></label>
					        <div id="freightSubContent1" class="col-sm-6"  style="padding-top: 8px;display: none">
						                 配送费<input type="text" id="freight1" name="freight1" value="" style="width: 50px">元
					        </div>
					        <div id="freightSubContent2" class="col-sm-6"  style="padding-top: 8px;display: none">
						                 配送费<input type="text" id="freight2" name="freight2" value="" style="width:50px">元，
						                 满<input type="text" id="freightSub" name="freightSub" value="" style="width:50px">包邮
					        </div>
						</div>
					</div>
					<div class="form-group" id="vmstoreiddiv">
				    	<label for="description" class="col-sm-3 control-label">虚拟门店id</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="vmstoreid" name="vmstoreid" style="width:150px">
				        </div>
					</div>
					
					<div class="form-group" id="vmcollegeiddiv">
				    	<label for="description" class="col-sm-3 control-label">虚拟学校id</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="vmcollegeid" name="vmcollegeid" style="width:150px">
				        </div>
					</div>
					
					<div class="form-group" id="onoff">
				    	<label for="description" class="col-sm-3 control-label">开启/关闭</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="isclose" name="isclose" style="width:150px" >
		                        	<option value="0">开启</option>
		                        	<option value="1">关闭</option>
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
	var freightSubTypeVal = 1;
	var freightTypeVal = 1;
	var storageType = 0;

	$(document).ready(function(){
		showCity(storageType);
		freightTypeChange(freightTypeVal);
		freightSubTypeChange(freightSubTypeVal);
	})


    
   $('#btnSubmitEdit').click(function() {
	   
    var msg = checkParam();
   	    if(msg != null){
   			 alert (msg);
   			 return ;
   		}
   	 if($("#ldcTypeId").prop("checked")){
   		$("#ldcTypeId").val('1');//1为选中隐藏，0未选中
   	 } 
   	 else {
   		$("#ldcTypeId").attr('value',"0");
   	 }
   	 
   	 $('#btnSubmitEdit').attr('disabled',true);
    	if($("#method").val() == 0){
    		$.post("editStorage", $("#editForm").serialize(), function(response){
    			if (response.code == 0) {
    				alert(response.msg);
    	        	table.ajax.reload(null, false);
    	        	$('#modalEdit').modal('hide');
    	        	$('#btnSubmitEdit').attr('disabled',false);
    			}else if(response.code == 1){
    				alert(response.msg);  				
    				$('#btnSubmitEdit').attr('disabled',false);
    			}
    			else if(response.code == 2){
    				alert(response.msg);
    				$("#vmcollegeiddiv").show();
    				$('#btnSubmitEdit').attr('disabled',false);
    			} 
    			else {
    				alert(response.msg);
    				$('#btnSubmitEdit').attr('disabled',false);
    			}
    		});
    	}
    	if($("#method").val() == 1){
    		$.post("addStorage", $("#editForm").serialize(), function(response){
    			if (response.code == 0) {
    				alert(response.msg);
    	        	table.ajax.reload(null, false);;; 
    	        	$('#modalEdit').modal('hide');
    	        	$('#btnSubmitEdit').attr('disabled',false);
    			} else if(response.code == 2){
    				alert(response.msg);
    				$("#vmcollegeiddiv").show();
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
   
   function showCity(type){
		if(type == 0){
			$("#citydiv").hide();
			$("#vmstoreiddiv").hide();
			$("#ldcTypeDiv").hide();
			$("#ldcOwnerTypeDiv").hide();
			$("#vFreight").hide();
		}
		else if(type == 1){
			$("#citydiv").show();
			$("#vmstoreiddiv").show();
			$("#ldcTypeDiv").show();
			$("#ldcOwnerTypeDiv").show();
			$("#vFreight").hide();
		}else if(type == 2){
			$("#citydiv").hide();
			$("#ldcTypeDiv").hide();
			$("#ldcOwnerTypeDiv").hide();
			$("#vmstoreiddiv").hide();
			$("#vFreight").show();
		}
	}

   $('input[name=freightType]').click(function(){
	  freightTypeChange($(this).val());
   });
   
   $('input[name=freightSubType]').click(function(){
	   freightSubTypeChange($(this).val());
   });
   
   function freightTypeChange(freightTypeVal){
	  if(freightTypeVal == 1){
		  $('#freightSubType').show();
		  $('#freightSubContent').show();
	  }else{
		  $('#freightSubType').hide();
		  $('#freightSubContent').hide();
	  }
   }
   function freightSubTypeChange(freightSubTypeVal){
	   $('#freightSubContent1').hide();
	   $('#freightSubContent2').hide();
	   $('#freight1').val('');
	   $('#freight2').val('');
	   $('#freightSub').val('');
	   $('#freightSubContent'+freightSubTypeVal).show();
   }
</script>
