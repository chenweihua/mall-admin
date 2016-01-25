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
        <h4 class="modal-title" id="myModalLabel">添加/编辑供应商</h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
			<div class="box-content">
				<form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
					<input type="hidden" id="shopId" name="shopId" value="0">	
					<input type="hidden" id="storageId" name="storageId" value="0">				
					<div class="form-group">
				    	<label for="shopName" class="col-sm-3 control-label">供应商名称：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control supplierRequest" id="shopName" name="shopName" placeholder="供应商名称" style="width:300px">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="sellerCode" class="col-sm-3 control-label">供应商编码：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control supplierRequest" id="sellerCode" name="sellerCode" placeholder="供应商编码" style="width:300px">
				        </div>
					</div>	
					<div class="form-group">
				    	<label for="sellerId" class="col-sm-3 control-label">供应商ID：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control supplierRequest" id="sellerId" name="sellerId" placeholder="供应商ID" style="width:300px">
				        </div>
					</div>	
					<div class="form-group">
				    	<label for="shopKeeper" class="col-sm-3 control-label">供应商联系人：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control supplierRequest" id="shopKeeper" name="shopKeeper" placeholder="供应商联系人" style="width:300px">
				        </div>
					</div>	
					<div class="form-group">
				    	<label for="shopPhone" class="col-sm-3 control-label">供应商电话：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control supplierRequest" id="shopPhone" name="shopPhone" placeholder="供应商电话" style="width:300px">
				        </div>
					</div>	
					<div class="form-group">
				    	<label for="shopWeight" class="col-sm-3 control-label">供应商权重：</label>
				        <div class="col-sm-3">
				        	<input type="text" class="form-control supplierRequest" id="shopWeight" name="shopWeight" placeholder="供应商权重" style="width:300px">
				        </div>
					</div>	
					<div class="form-group">
				    	<label for="shopType" class="col-sm-3 control-label">供应商类型：</label>
				        <div class="col-sm-7" style="margin-top: 8px">
							<input type="radio" name="shopType" value="1" checked="checked">o2o模式
				        	<input type="radio" name="shopType" value="2">电商模式
						</div>
					</div>	
					<div class="form-group">
				    	<label for="imageUrl" class="col-sm-3 control-label">供应商图片：</label>
				        <div class="col-sm-7">
							<input id="uploadImage1" style="margin-top:8px" type="file" name="p1" onchange="PreviewImage(1);"/> 
							<img id="uploadPreview1" class="imageUrlShow" src="" style="width: 200px" alt="展示图片" />
							<br/> 
							<input type="hidden" id="imageUrl1" class="supplierRequest" name="imageUrl" placeholder="展示图片" value="">
						</div>
					</div>	
					<div class="form-group">
				    	<label for="shopIsOpen" class="col-sm-3 control-label">供应商状态：</label>
				        <div class="col-sm-7" style="margin-top: 8px">
							<input type="radio" name="shopIsOpen" value="1" checked="checked">开启
				        	<input type="radio" name="shopIsOpen" value="0">关闭
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
    $("#btnSubmitEdit").click(function(){ 
   		if (checkParam()) {
   			$.post("addEditSupplier", $("#editForm").serialize(), function(json) {
   				if (json.code != 0) {
   					alert(json.msg);
   				} else {
   					$('#modalEdit').modal('hide');
   					table.ajax.reload();
   				}
   			});
   		}
    	
    });
    
    //验证数据格式
    function checkParam(){
    	for (var i = 0; i < $(".supplierRequest").length; i++) {
			if ($.trim($(".supplierRequest").eq(i).val()).length == 0) {
				alert($(".supplierRequest").eq(i).attr('placeholder') + "是必填项");
				return false;
			}
		}
    	var shopWeight = $('#shopWeight').val().trim();
    	if(!(isNum(shopWeight))) {
    		alert("供应商权重必须是整数,不能超过10位");
    		return false;
    	}
    	if(!checkPhone($('#shopPhone').val().trim())) {
    		alert("供应商电话格式不对，正常手机号11位，座机xxx-xxxxxxxx");
    		return false;
    	}
		return true;
    }
    
    function isNum(num){
    	var reNum=/^\d{1,10}$/;
    	return(reNum.test(num));
    }
    
    function checkPhone(phone) {
    	var rePhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
   		var reMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    	if(rePhone.test(phone) || reMobile.test(phone)) {
    		return true;
    	}
    	return false;
    }
    
</script>
