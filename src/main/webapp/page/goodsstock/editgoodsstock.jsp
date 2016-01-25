<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href='/css/style.css' rel="stylesheet" >
<script type="text/javascript" src="/js/ajaxfileupload.js"></script> 


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
					<div class="form-group">
				    	<label for="selleridlabel" class="col-sm-3 control-label">商品名称</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="goodsname" name="goodsname" >
				        	<input type ="hidden" id="goodsid" name="goodsid" >
				        	<input type="hidden" id="method" name="method">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">仓库</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="storage" name="storage" style="width:300px">
				        	<input type ="hidden" id="storageid" name="storageid" >
				        </div>
					</div>
					
				    <div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">供应商</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="seller" name="seller" style="width:150px" >
		                        <c:forEach var="seller" items="${sellerList}">
		                        	<c:choose> 
										<c:when test="${seller.isDel eq 0}">
											<option value="${seller.sellerId}">${seller.sellerName}</option>
										</c:when>
									</c:choose>
								</c:forEach>	
				            </select>
				        </div>
					</div>
					
					
					<div class="form-group" id="type_0">
				    	<label for="description" class="col-sm-3 control-label">类型</label>
				        <div class="col-sm-3" id = "record">
							<input type="text" class="form-control" id="recordname" name="recordname" value="采购入库" readonly="true">
				        	<input type ="hidden" id="recordtype" name="recordtype" value="1">
				        </div>
					</div>
					
					<div class="form-group" id="type_1">
				    	<label for="description" class="col-sm-3 control-label">类型</label>
				        <div class="col-sm-3" id = "record">
							<select class="form-control" id="selectedtype" name="selectedtype" style="width:150px" >
		                        	<c:forEach var="item" items="${recordTypeMap}">   
		                        		<option value="${item.key}">${item.value.name}</option>   
									</c:forEach>  
				            </select>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">单价</label>
				        <div class="col-sm-3">
							<input type="text" class="form-control" id="price" name="price" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group" >
				    	<label for="description" class="col-sm-3 control-label">数量</label>
				        <div class="col-sm-3">
							<input type="text" class="form-control" id="num" name="num" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">单号</label>
				        <div class="col-sm-3">
							<input type="text" class="form-control" id="recordcode" name="recordcode" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group" id="onoff">
				    	<label for="description" class="col-sm-3 control-label">备注</label>
				        <div class="col-sm-3">
							<input type="text" class="form-control" id="remark" name="remark" style="width:300px" placeholder="请输入库存调整原因">
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
    
   $('#btnSubmitEdit').click(function() {
	   
	    var msg = checkParam();
   	    if(msg != null){
   			 alert (msg);
   			 return ;
   		}
	   
    	if($("#method").val() == 0){
    		$.post("addStock", $("#editForm").serialize(), function(response){
    			if (response.code == 0) {
    				alert(response.msg);
    	        	table.ajax.reload(null, false);
    	        	$('#modalEdit').modal('hide');
    			} else {
    				alert(response.msg);
    			}
    		});
    	}
    	
    	if($("#method").val() == 1){
    		
    		if($('#remark').val().trim().length == 0){
    			alert("备注不能为空");
    			return;
    		}
    		
    		$.post("ajustStock", $("#editForm").serialize(), function(response){
    			if (response.code == 0) {
    				alert(response.msg);
    	        	table.ajax.reload(null, false);
    	        	$('#modalEdit').modal('hide');
    			} else {
    				alert(response.msg);
    			}
    		});
    	}
    	
    });
     
</script>
