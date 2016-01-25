<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<link href='../../css/goods.css' rel='stylesheet'>
<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css"
	type="text/css">


<%@ include file="../../header.jsp"%>

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>

	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i>优惠券发放
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i
						class="glyphicon glyphicon-cog"></i></a> <a href="#"
						class="btn btn-minimize btn-round btn-default"><i
						class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
						class="btn btn-close btn-round btn-default"><i
						class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			
			<div class="box-content">
			  <form class="form-horizontal" id="protoForm" role="form"  action="/coupon/giveSubmit" enctype="multipart/form-data" method="post">
			    <input type="hidden" name="couponGiveId" id="couponGiveId" value="${requestScope.couponGive.id }" />
			    <input type="hidden" name="giveWay" id="giveWay" value="" />
			    <input type="hidden" name="checkAreaId" id="checkAreaId" />
				<div class="form-group">
			    	<label for="batchId" class="col-sm-2 control-label">优惠券批次号</label>
			        <div class="col-sm-3">
			        	<input type="text" class="form-control request" id="couponBatchId" name="couponBatchId" value="${requestScope.couponGive.couponBatchId}" readonly="readonly">
			        </div>
				</div>
				
				<!-- 
				<div class="form-group">
			    	<label for="receiveLimit" class="col-sm-2 control-label">领取限制</label>
			        <div class="col-sm-3">
			        	<input type="text" class="form-control" id="receiveLimit" name="receiveLimit" value="${requestScope.couponGive.receiveLimit}" placeholder="该批次内一个用户最多可领取几张优惠券" <c:if test="${not empty requestScope.couponGive.id}">readonly="readonly"</c:if>>
			        </div>
				</div>
				 -->
				
				<div class="form-group">
			    	<label for="skuName" class="col-sm-2 control-label">用户类型</label>
			        <div class="col-sm-3" style="padding-top:9px;">
			        	<select name="userType" <c:if test="${not empty requestScope.couponGive.id}">disabled="disabled"</c:if>>
			        		<option value="0" <c:if test="${0 eq requestScope.couponGive.userType}">selected</c:if>>全部</option>
			        		<option value="1" <c:if test="${1 eq requestScope.couponGive.userType}">selected</c:if>>新用户</option>
			        		<option value="2" <c:if test="${2 eq requestScope.couponGive.userType}">selected</c:if>>老用户</option>
			        	</select>
			        </div>
				</div>
				
				<c:if test="${not empty requestScope.couponGive.id}">
					<c:if test='${requestScope.couponGive.status eq "2" || requestScope.couponGive.status eq "3"}'>
						<div class="form-group">
					    	<label for="skuName" class="col-sm-2 control-label">处理结果信息</label>
					        <div class="col-sm-3" style="width:50%;color:red;padding-top:9px;">
					        	${requestScope.couponGive.msg }
					        </div>
						</div>
					</c:if>
				</c:if>
			
			<div class="tabbable" id="tabs-677904">
				<ul class="nav nav-tabs">
					<li  id="tab_1"><a id="a_1" contenteditable="false" data-toggle="tab" href="#panel-1">根据手机号发送</a></li>
					<li  id="tab_2"><a id="a_2" contenteditable="false" data-toggle="tab" href="#panel-2">根据区域发送</a></li>
				<!-- 	<li  id="tab_3"><a id="a_3" contenteditable="false" data-toggle="tab" href="#panel-3">全部</a></li>   -->
				</ul>
				
				<div class="tab-content">
				    <!-- tab第一部分 -->
					<div class="tab-pane active" contenteditable="false" id="panel-1">
						<br />
							<c:if test="${empty requestScope.couponGive.id }">
								<div style="margin-left:50px;">
							    	<input type="file" name="phoneNoFile" id="phoneNoFile">
								</div>
							</c:if>
							<c:if test="${not empty requestScope.couponGive.id}">
								<div style="margin-left:50px;">
							    	<a href="downloadPhoneNoFile?couponGiveId=${requestScope.couponGive.id }">${requestScope.orginFileName }</a>
								</div>
							</c:if>
						<br />	
					 	<button type="button" class="btn btn-default" data-dismiss="modal" id="save1" <c:if test="${not empty requestScope.couponGive.id}">disabled="disable"</c:if>>保存</button>
					 	<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">返回</button>
						&nbsp;&nbsp;&nbsp;一次最多导入5万个号码
					</div>
					
					<!-- tab第二部分 -->
					<div class="tab-pane" contenteditable="false" id="panel-2">
						<div class="box-content">
							<div id="treeWrap">
								<ul id="selectTree" class="ztree"></ul>
							</div>
						</div>
						
						<input type="button" class="btn btn-default" data-dismiss="modal" id="save2" value="保存" <c:if test="${not empty requestScope.couponGive.id}">disabled="disable"</c:if> />
						<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">返回</button>
						&nbsp;&nbsp;&nbsp;
						一次最多选择5个区域级节点
					</div>
					
					<!-- tab第三部分 -->
					<!--
					<div class="tab-pane" contenteditable="false" id="panel-3">
						<br />
						<button type="button" class="btn btn-default" data-dismiss="modal" id="save3" <c:if test="${not empty requestScope.couponGive.id}">disabled="disable"</c:if>>保存</button>
						<br />
					</div>
					-->
				</div>
			</div>
			</form>
			</div>
		</div>
	</div>


<%@ include file="../../footer.jsp"%>


<script type="text/javascript">

$(document).ready(function(){
	
	var giveWay = '${requestScope.couponGive.giveWay}';
	if(giveWay == '') {
		giveWay = '1';
	}
	$("#a_" + giveWay).trigger("click");
	
	<c:if test="${not empty requestScope.couponGive.id}">
	    
		$("a[id^=a_]").not("#a_" + giveWay).click(function(){
			//alert("不能切换");
			return false;
		});
	</c:if>
	
	var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "s", "N": "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
		
	};
	
	var couponGiveId = $("#couponGiveId").val();
	
	$.post("/coupon/getCityArea",{"couponGiveId":couponGiveId}, function(json){
		if (json.code != "0") {
			alert(json.msg);
		} else {
			//alert(json.msg);
			var zNodes = json.data;
			//alert(zNodes);
			$.fn.zTree.init($("#selectTree"), setting, zNodes);
		}
	});
	
});

//根据手机号发送
<c:if test="${empty requestScope.couponGive.id}">
$('#save1').click(function() {
	if(!checkParamPhoneNoType()) {
		alert("请输入完整内容");
		return;
	}
	
	$("#giveWay").val("1");
    
	/*
	$.ajaxFileUpload({  
	    fileElementId: imgfileId,  
	    url: urlPrefix+"import?type="+type, 
	    dataType: 'content',  

	    success: function (data, textStatus) {  
	    	var reg = /<pre.+?>(.+)<\/pre>/g;  
	    	var result = data.match(reg);  
	    	data = RegExp.$1;
	    	var obj = eval('(' + data + ')'); 
	    	if(obj.code==0){
	    		alert("导入成功~");
	    		table.ajax.reload(null, true);
	    	}else{
	    		alert(obj.msg);
	    	}
	    },  
	    error: function (XMLHttpRequest, textStatus, errorThrown) {  
	    	alert(errorThrown);
	    }
	  });  
	*/
	
	$("#protoForm").submit();
	
});



//根据区域发送
$('#save2').click(function() {
	if(!checkParamAreaType()) {
		alert("请输入完整内容");
		return;
	}
	
	$("#giveWay").val("2");
    
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getCheckedNodes();
	var checkNodeIdArr = new Array();
	for(var i =0; i < nodes.length; i++) {
		var checkNodeId = nodes[i].id;
		if(checkNodeId.indexOf('city_') == 0) {
			checkNodeId = checkNodeId.substring(checkNodeId.lastIndexOf('_') + 1,checkNodeId.length);
			checkNodeIdArr[checkNodeIdArr.length] = checkNodeId;
		}
	}
	
	
	if(checkNodeIdArr.length == 0) {
		alert("请至少选择一个区县级节点");
		return;
	}
	
	var allowAreaNum = ${requestScope.allowAreaNum};
	if(checkNodeIdArr.length > allowAreaNum) {
		alert("一次最多允许选择" + allowAreaNum + "个区县级节点，您现在选择了" + checkNodeIdArr.length	+ "个");
		return;
	}
	
	var checkNodeIdStr = checkNodeIdArr.join(",");
	$("#checkAreaId").val(checkNodeIdStr);
	
	$("#protoForm").submit();
});


//全部发送
$('#save3').click(function() {
	
	if(!checkCommonArea()) {
		alert("请输入完整内容");
		return;
	}
	
	$("#giveWay").val("3");
    
	$("#protoForm").submit();
});



//校验根据手机号发送输入项
function checkParamPhoneNoType(){
	
	if(!checkCommonArea()) {
		return false;
	}
	
	if($("#phoneNoFile").val() == "") {
		return false;
	}
		
	
	return true;
}

//校验按区域发送
function checkParamAreaType() {
	
	if(!checkCommonArea()) {
		return false;
	}
	
	return true;
}


//校验公共部分
function checkCommonArea() {
	
	/*
	//判断领取限制
	var receiveLimit = $.trim($("#receiveLimit").val());
	if(receiveLimit == '') {
		return false;
	}
	if(!checkInteger(receiveLimit)) {
		return false;
	}
	*/
	
	
	return true;
}


//判断正整数
function checkInteger(input) {
     var re = /^[1-9]+[0-9]*]*$/;

     if (!re.test(input)) {
        return false;
     }
     
     return true;
}
</c:if>

</script>