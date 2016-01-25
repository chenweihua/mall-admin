<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.enumdata.MerchantType"%>
<%@ page import="com.mall.admin.enumdata.MerchantStatus"%>

<c:set var="MerchantType" value="<%=MerchantType.values()%>"/>
<c:set var="MerchantStatus" value="<%=MerchantStatus.values()%>"/>

<%@ include file="../../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<style type="text/css">
.labelinfo{
	 font-weight: normal;

</style>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner">商户信息</i>
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
					<a href="#"	class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
					<a href="#"	class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">
				<li style="font-weight: bold; font-size: 16px;">商户信息</li>
				<label>商户名称:</label><label class="labelinfo">${merchant.merchantName}</label><br>
				<label>商户ID:</label><label  class="labelinfo">${merchant.merchantNo}</label><br>
				<label>收银地址:</label><label style="word-wrap: break-word;"  class="labelinfo">${merchant.mechantPayAddr}</label><br>
				<label>业务类型:</label><label class="labelinfo">
					<c:forEach var="merchantType" items="${MerchantType}">
						<c:if test="${merchant.merchantType==merchantType.getType()}">
							<c:out value="${merchantType.name }"></c:out>
						</c:if>
					</c:forEach>
				</label><br>	
				<label>门店地址:</label><label class="labelinfo">${merchant.merchantAddr}</label><br>
				<li style="font-weight: bold; font-size: 16px;">店长信息：</li>
				<label>店长姓名:</label><label class="labelinfo">${merchant.shopOwner}</label><br>
				<label>联系电话:</label><label class="labelinfo">${merchant.ownerPhone}</label><br>
				<label>邮箱:</label><label class="labelinfo">${merchant.merchantEmail}</label><br>
				<li style="font-weight: bold; font-size: 16px;">登陆信息：</li>
				<label>账号:</label><label class="labelinfo">${merchant.merchantAccount}</label><br>
				<li style="font-weight: bold; font-size: 16px;">财务信息：</li>
				<label>收款人:</label><label class="labelinfo">${merchant.bankUserName}</label><br>
				<label>银行:</label><label class="labelinfo">${merchant.bankName}</label><br>
				<label>开户行:</label><label class="labelinfo">${merchant.branchName}</label><br>
				<label>账户:</label><label class="labelinfo">${merchant.bankAccount}</label><br>
			</div>
			
		</div>
		<button type="button" class="btn btn-primary btn-sm" id="back">返回</button>
	</div>
	</div>
	<!--/span-->

<%@ include file="../../footer.jsp"%>

<script>
$("#back").click(function(){
	window.location.href="/merchant/querymerchantview";
	
});
</script>