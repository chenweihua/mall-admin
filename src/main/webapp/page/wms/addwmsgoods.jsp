<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">


<div class="modal fade" id="modalPutInStorage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
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
				<form class="form-horizontal" id="putInStorageForm" role="form" onsubmit="return false;">
						
					
					
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">商品名称</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="addname" name="addname"  readonly="readonly">
							<input type="hidden" id="addid" name="addid" value="-1">
				        </div>
					</div>
					
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">供应商</label>
				        <div class="col-sm-3">
				        	<select class="form-control" id="sellerid" name="sellerid">
				        		<c:forEach items="${sellerList}" var="seller">
				                		<option value="${seller.id}"  >${seller.name}</option>
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
					
					<div class="form-group">
				    	<label for="description" class="col-sm-3 control-label">数量</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control" id="count" name="count" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">调整类型</label>
				        <div class="col-sm-3">
								<select class="form-control" name="recordtype" id="recordtype">
										<c:forEach items="${typeList}" var="recordtype">
				                			<option value="${recordtype.id}"  >${recordtype.name}</option>
				                		</c:forEach>
								</select>
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="collegeidlabel" class="col-sm-3 control-label">入库单号</label>
				        <div class="col-sm-3">
								<input type="text" class="form-control" id="recordcode" name="recordcode" style="width:300px">
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
        <button type="button" class="btn btn-primary" id="btnSubmitPut">提交</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>	
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<script type="text/javascript">

$("#recordtype").change(function(){
	var type=$("#recordtype").val();
	/* 不再判断是否需要输入单号
	if(type==2){
		$("#recordcode").attr("disabled","disabled");//再改成disabled  
		$("#recordcode").val("");
	}else{
		$("#recordcode").removeAttr("disabled");//要变成Enable，JQuery只能这么写  
	}*/
});

$('#btnSubmitPut').click(function() {
		
	   $.post(urlPrefix+"judgestorage/defaultstorage",{"dstorageId":$("#dstorageId").val()},function(response){    		
    	if (response.code == -1) {
    		alert(response.msg);  
    		window.location.href=urlPrefix+"home/index";
    	}	
    	},"json");
	   $("#btnSubmitPut").attr("disabled",true);
	$.post(urlPrefix+"goods/putinstorage", $("#putInStorageForm").serialize(), function(response){
		$("#btnSubmitPut").removeAttr("disabled");
		if (response.code == "0") {
			alert("调整成功");
	        $('#modalPutInStorage').modal('hide');
	        table.ajax.reload(null, false);
// 	        var goodsId = document.getElementById('addid').value;
// 	        if(goodsId!='-1'){
// 	        	var blockId = "#block" + goodsId;
		    	
// 	        	$.post("getstoragecountinfo",{goodsId:goodsId},function(data){
// 		    		var skuStockTable = fillTable(data.data);
// 		    		if(!($(blockId)==undefined||$(blockId)=='')){
// 		    			$(blockId).html(skuStockTable); 
// 		    		}
			    	
// 		    	});
// 	        }
	    	
		} else {
			alert(response.msg);
		}		
	});

});
</script>
