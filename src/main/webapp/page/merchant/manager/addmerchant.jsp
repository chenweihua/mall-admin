<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mall.admin.enumdata.MerchantType"%>
<c:set var="MerchantType" value="<%=MerchantType.values()%>"/>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width:800px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
				<form action="" id="merchantForm">
					<div class="tabbable" id="tabs-677904">
						<ul class="nav nav-tabs">
							<li class="active"><a id="a_1" contenteditable="false" data-toggle="tab" href="#panel-1">基本信息</a></li>
							<li ><a id="a_2" contenteditable="false" data-toggle="tab" href="#panel-2">详细信息</a></li>
						</ul>
						<br>
						<div class="tab-content">
							<div class="tab-pane active" contenteditable="false" id="panel-1">
									<div class="form-group">
										<label for="merchantNameLabel" class="control-label">商户名称:</label>
										<input name="merchantName" id="merchantName" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
										<input type="hidden" id="merchantId" name="merchantId">
									</div>
									
									<div class="form-group">
						    			<label for="merchantTypeLabel" class="col-sm-3 control-label">业务类型</label>
								        	<select class="form-control" id="merchantType" name="merchantType" style="width:150px">
						                        	<c:forEach var="merchantType" items="${MerchantType}">
						                        		<option value="${merchantType.type}">${merchantType.name}</option>
						                        	</c:forEach>
								            </select>
									</div>
									<div class="form-group">
										<label for="cityLabel" class="control-label">所在地区:</label>
										<select class="form-control" id="merchantCityId" name="merchantCityId" style="width:150px;display: inline-block;">
												<c:forEach var="rootCity" items="${rootCityList}">
													<option value="${rootCity.cityId}">${rootCity.cityName }</option>
												</c:forEach>
						                        
								        </select>
								        <select class="form-control" id="merchantAreaId" name="merchantAreaId" style="width:150px;display: inline-block;">
								        </select>
									</div>
									<div class="form-group" id="navigationUrlDIV">
										<label for="addrLabel" class="control-label">详细地址:</label>
										<input name="merchantAddr" id="merchantAddr" value="" placeholder="商户地址" style="width:50%;"></input><span style="color:red;">*</span>
									</div>
									<div class="form-group">
										<label for="merchantIdLabel" class="control-label">商户ID:</label>
										<input name="merchantNo" id="merchantNo" value="" placeholder="" style="width:50%;" readonly="readonly" >
									</div>
									<div class="form-group">
										<label for="payAddrLabel" class="control-label">收银地址:</label>
										<input name="mechantPayAddr" id="mechantPayAddr" value="" placeholder="" style="width:50%;" readonly="readonly">
									</div>
									<div class="form-group">
										<label for="weight" class="control-label">店长信息:</label>
									</div>
									<div class="form-group">
										<label for="shopOwnerLabel" class="control-label">店长姓名:</label>
										<input name="shopOwner" id="shopOwner" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
									</div>
									<div class="form-group">
										<label for="phoneLabel" class="control-label">联系电话:</label>
										<input name="ownerPhone" id="ownerPhone" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
									</div>	
									<div class="form-group">
										<label for="emailLabel" class="control-label">邮箱:</label>
										<input name="merchantEmail" id="merchantEmail" value="" placeholder="" style="width:50%;">
									</div>								
							</div>
												<!-- tab第二部分 -->
							<div class="tab-pane" contenteditable="false" id="panel-2">
									<div class="form-group">
										<label for="accountLabel" class="control-label">登陆账号:</label>
										<input name="merchantAccount" id="merchantAccount" value="" placeholder="请输入6-10位数字或字母组合" style="width:50%;"><span style="color:red;">*</span>
									</div>	
									<div class="form-group">
										<label for="weight" class="control-label">财务信息:</label>
									</div>
									<div class="form-group">
										<label for="bankAccountLabel" class="control-label">银行账号:</label>
										<input name="bankAccount" id="bankAccount" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
									</div>
									<div class="form-group">
										<label for="bankLabel" class="control-label">银行:</label>
										<input name="bankName" id="bankName" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
									</div>
									<div class="form-group">
										<label for="branchLabel" class="control-label">开户支行:</label>
										<input name="branchName" id="branchName" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
									</div>
									<div class="form-group">
										<label for="bankUserLabel" class="control-label">收款人:</label>
										<input name="bankUserName" id="bankUserName" value="" placeholder="" style="width:50%;"><span style="color:red;">*</span>
									</div>
								<button type="button" class="btn btn-primary btn-sm" id="addMerchant">确定</button>
								
							</div>
						</div>
					</div>
				</form>
			</div>
			<!--/span-->
		
		</div>
      </div>
  </div>
</div>
<script>
//切换城市，同时需要获取该城市下的地区
$("#merchantCityId").change(function(){
	var cityId = $("#merchantCityId").val();
	referArea(cityId);
});
$("#addMerchant").click(function(){
	if(!checkParam()){
		return;
	}
	$.post("addmerchant",$("#merchantForm").serialize(),function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("添加成功~");
			table.ajax.reload(null, true);
			$('#modalEdit').modal('hide');
     	 }
	},"json")
});


function checkParam(){
	var merchantName = $("#merchantName").val();
	if(merchantName.trim()==''||merchantName.trim().length>30){
		alert("商户名称不能为空，并且长度不能大于30~");
		$("#merchantName").focus();
		return false;
	}
	var merchantAddr = $("#merchantAddr").val();
	if(merchantAddr.trim()==''){
		alert("商户详细地址不能为空~");
		$("#merchantAddr").focus();
		return false;
	}
	var shopOwner = $("#shopOwner").val();
	if(shopOwner.trim()==''||shopOwner.trim().length>8){
		alert("店主不能为空，并且店主姓名长度不能大于8~");
		$("#shopOwner").focus();
		return false;
	}
	var ownerPhone = $("#ownerPhone").val();
	if(ownerPhone.trim()==''||ownerPhone.trim().length!=11){
		alert("店长联系电话不能为空，并且要求长度等于11~");
		$("#ownerPhone").focus();
		return false;
	}
/* 	var merchantEmail = $("#merchantEmail").val();
	if(merchantEmail.trim()==''||merchantEmail.trim().length>50){
		alert("店长邮箱不能为空，并且长度不能大于50~");
		$("#merchantEmail").focus();
		return false;
	} */
	var merchantAccount = $("#merchantAccount").val();
	if(merchantAccount.trim()==''||merchantAccount.trim().length>10||merchantAccount.trim().length<6||!istrue(merchantAccount)){
		alert("登陆账号不能为空,只能是数字和字母，并且长度在6-10");
		$("#merchantAccount").focus();
		return false;
	}
	var bankAccount = $("#bankAccount").val();
	if(bankAccount.trim()==''||bankAccount.trim().length>30||!isnum(bankAccount)){
		alert("银行账号不能为空，只能是数字，并且长度不能大于30~");
		$("#bankAccount").focus();
		return false;
	}
	var bankName = $("#bankName").val();
	if(bankName.trim()==''||bankName.trim().length>20){
		alert("银行名称不能为空，并且长度不能大于20~");
		$("#bankName").focus();
		return false;
	}
	var branchName = $("#branchName").val();
	if(branchName.trim()==''||branchName.trim().length>50){
		alert("开户支行不能为空，并且长度不能大于50~");
		$("#branchName").focus();
		return false;
	}
	var bankUserName = $("#bankUserName").val();
	if(bankUserName.trim()==''||bankUserName.trim().length>10){
		alert("收款人不能为空，并且长度不能大于10~");
		$("#bankUserName").focus();
		return false;
	}
	var merchantNo = $("#merchantNo").val();
	if(merchantNo.trim()==''||merchantNo.trim().length!=10){
		alert("商户ID不正确~");
		return false;
	}
	var mechantPayAddr = $("#mechantPayAddr").val();
	if(mechantPayAddr.trim()==''){
		alert("收银地址错误~");
		return false;
	}
	return true;
}

function istrue(a){ var reg=/^[a-z0-9]*$/; return reg.test(a); }
function isnum(a){ var reg=/^[0-9]*$/; return reg.test(a); }

</script>