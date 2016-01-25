<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<link href='../../css/goods.css' rel='stylesheet'>

<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css" type="text/css">

<style>
select {
	width: 236px;
}
</style>
<%@ include file="../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>


<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>

	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i>添加广告
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
			
			<div class="tabbable" id="tabs-677904">
				<ul class="nav nav-tabs">
					<li class="active"><a id="a_1" contenteditable="false" data-toggle="tab" href="#panel-1">基础信息</a></li>
					<li id="tab_2"><a id="a_2" contenteditable="false" data-toggle="tab" href="#panel-2">投放区域</a></li>
				</ul>
				
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="panel-1">
						<br>
						 <form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
  						    <input type="hidden" id="bannerId" name="bannerId" value="${banner.bannerId}">
							<div class="form-group">
						    	<label for="skuName" class="col-sm-2 control-label">选择平台</label>
						        <div class="col-sm-3">
						        	<input type="radio" name="bannerType" <c:if test="${banner == null || banner.bannerType == 0}">checked="checked"
						        		</c:if> value="0">全部
						        	<input type="radio" name="bannerType" <c:if test="${banner.bannerType == 1}">checked="checked"
						        		</c:if> value="1">H5
						        	<input type="radio" name="bannerType" <c:if test="${banner.bannerType == 2}">checked="checked"
						        		</c:if> value="2">APP
						        </div>
							</div>
							
							<div class="form-group">
						    	<label for="skuName" class="col-sm-2 control-label">选择页面</label>
						        <div class="col-sm-6">
						        <label id="bannerPositionText" style="display: none">${banner.bannerPosition}</label>
						        	<select id="bannerPosition" name="bannerPosition">
						        		<option <c:if test="${banner == null || banner.bannerPosition == 0}">selected="selected"
						        				</c:if> value="0">首页</option>
						        		<option <c:if test="${banner.bannerPosition == 1}">selected="selected"
						        				</c:if> value="1">活动页</option>
						        		<option <c:if test="${banner.bannerPosition == 2}">selected="selected"
						        				</c:if> value="2">类目页</option>
						        	</select>
						        	
						        	<label id="connectIdText" style="display: none">${banner.connectId}</label>
						        	<select id="connectId" name="connectId" style="display: none">
						        	</select>
						        </div>
							</div>
							
							<div class="form-group">
						    	<label for="bannerName" class="col-sm-2 control-label">广告名称</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="bannerName" name="bannerName" value="${banner.bannerName}" placeholder="广告名称">
						        </div>
							</div>
							
							<div class="form-group">
								<label for="imageUrl1" class="col-sm-2 control-label">广告图片</label>
								<div class="col-sm-6">
									<img id="uploadPreview1" src="${banner.imageUrl}" style="width:200px" alt="广告图片"/><br />
									<input id="uploadImage1" type="file" name="p1" onchange="PreviewImage(1);" />
  									<input type="hidden" id="imageUrl1" name="imageUrl" value="${banner.imageUrl}">
								</div>
							</div>
							
							<div class="form-group">
						    	<label for="weight" class="col-sm-2 control-label">广告权重</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="weight" name="weight" value="${banner.weight}" placeholder="广告权重，必须数字">
						        </div>
							</div>
							
							<div class="form-group">
						    	<label for="bannerDesc" class="col-sm-2 control-label">广告描述</label>
						        <div class="col-sm-3">
						        	<input type="text" class="form-control request" id="bannerDesc" name="bannerDesc" value="${banner.bannerDesc}" placeholder="广告描述">
						        </div>
							</div>
							
							<div class="form-group">
						    	<label for="skuName" class="col-sm-2 control-label">链接选择</label>
						    	<input type="hidden" name="hrefType" id="hrefType" value="${hrefType}">
 						    	<div class="tabbable col-sm-6" id="tabs-677904">
									<ul class="nav nav-tabs" style="width: 70%;">
										<li id="mall_li"><a id="a_mall" contenteditable="false" data-toggle="tab" href="#panel-mall">商城模块</a></li>
										<li id="category_li"><a id="a_category" contenteditable="false" data-toggle="tab" href="#panel-category">商城类目</a></li>
										<li id="activity_li"><a id="a_activity" contenteditable="false" data-toggle="tab" href="#panel-activity">商城活动</a></li>
										<li id="url_li" ><a id="a_url" contenteditable="false" data-toggle="tab" href="#panel-url">URL链接</a></li>
										<li id="goods_li" ><a id="a_goods" contenteditable="false" data-toggle="tab" href="#panel-goods">商城商品</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane" style="margin-top: 20px" contenteditable="false" id="panel-mall">
											选择模块：
											<select id="mall_model" name="mall_model">
							        			<c:forEach items="${mallModelList}" var="model">
							                		<option <c:if test="${model.selected == true}">selected="selected"
						        							</c:if> value="${model.value}">${model.name}</option>
							               		</c:forEach>
							        		</select>
							        		<br><br>
							        		二级页面：
							        		<select id="mallPage" name="mallPage">
							        			<c:forEach items="${mallPageList}" var="page">
							                		<option <c:if test="${page.selected == true}">selected="selected"
						        							</c:if> value="${page.value}">${page.name}</option>
							               		</c:forEach>
							        		</select>
										</div>
										<div class="tab-pane" style="margin-top: 20px" contenteditable="false" id="panel-category">
											商城类目：
											<select id="hrefCategoryId" name="hrefCategoryId">
						        			<c:forEach items="${categoryList}" var="category">
						                		<option <c:if test="${category.selected == true}">selected="selected"
						        						</c:if> value="${category.value}">${category.name}</option>
						               		</c:forEach>
							        		</select>
										</div>
										<div class="tab-pane" style="margin-top: 20px" contenteditable="false" id="panel-activity">
											商城活动：
											<select id="hrefActivityId" name="hrefActivityId">
						        			<c:forEach items="${activityList}" var="activity">
						                		<option <c:if test="${activity.selected == true}">selected="selected"
						        						</c:if> value="${activity.value}">${activity.name}</option>
						               		</c:forEach>
							        		</select>
										</div>
										<div class="tab-pane" style="margin-top: 20px" contenteditable="false" id="panel-goods">
											商品类型：
											<select id="hrefUrl2" name="hrefUrl2">
						        			<c:forEach items="${goodsTypeList}" var="goodsType">
						                		<option <c:if test="${goodsType.selected == true}">selected="selected"
						        						</c:if> value="${goodsType.value}">${goodsType.name}</option>
						               		</c:forEach>
							        		</select>
							        		<br><br>
											选择类目：
											<select id="hrefUrl2" name="hrefUrl2">
						        			<c:forEach items="${categoryList}" var="category">
						                		<option <c:if test="${category.selected == true}">selected="selected"
						        						</c:if> value="${category.value}">${category.name}</option>
						               		</c:forEach>
							        		</select>
							        		<br><br>
											选择商品：
											<input type="hidden" id="goodsId" name="goodsId">
											<input type="text" style="width: 240px" id="goodsName" name="goodsName" value="${goods.name}"  placeholder="商品名称"><a>&nbsp;添加商品</a>
										</div>
										<div class="tab-pane" style="margin-top: 20px" contenteditable="false" id="panel-url">
										        链接标题：
											<input type="text" style="width: 240px" id="webViewName" name="webViewName" value="${webViewName}"  placeholder="链接标题">
										   	<br><br>
										    URL链接：
											<input type="text" style="width: 240px" id="webViewUrl" name="webViewUrl" value="${webViewUrl}"  placeholder="URL链接">
										</div>
									</div>
								</div>
						    	
						       <%--  <div class="col-sm-3">
						        	<label id="hrefUrlText" style="display: none">${banner.hrefUrl }</label>
						        	<input type="radio" name="actionType"<c:if test="${banner == null || banner.actionType == 1}"> checked="checked"
						        				</c:if> value="1">活动指向  
						        	<select id="hrefUrl1" name="hrefUrl1">
						        		<c:forEach items="${activities}" var="activity">
					                		<option value="${activity.activityId}">${activity.activityName}</option>
					               		</c:forEach>
						        	</select>
						        	<br>
						        	<input type="radio" name="actionType" <c:if test="${banner.actionType == 2}"> checked="checked"
						        				</c:if> value="2">类目指向
						        	<select id="hrefUrl2" name="hrefUrl2">
					        			<c:forEach items="${categories}" var="category">
					                		<option value="${category.categoryId}">${category.categoryName}</option>
					               		</c:forEach>
						        	</select>
						        	<br>
						        	<input type="radio" name="actionType" <c:if test="${banner.actionType == 3}"> checked="checked"
						        				</c:if> value="3">URL链接
						        	<input type="text" style="width: 240px" id="hrefUrl3" name="hrefUrl3" value="${banner.hrefUrlStr}"  placeholder="URL链接">
						        </div> --%>
							</div>
							
							<div class="form-group">
						    	<label for="skuName" class="col-sm-2 control-label">需要登陆</label>
						        <div class="col-sm-3" style="margin-top: 8px">
						        	<input type="radio" name="needLogin" 
						        		<c:if test="${banner.needLogin == 0 || banner.needLogin == null}">checked="checked"
						        		</c:if> value="0">否
						        	<input type="radio" name="needLogin" <c:if test="${banner.needLogin == 1}">checked="checked"
						        		</c:if>value="1">是
						        </div>
							</div>
							
							<div class="form-group">
								<label for="skuName" class="col-sm-2 control-label">投放时间</label>
								<div class="col-lg-3">
									<input type="text" id="startTime" name="startTime" class="form-control" value="${banner.startTimeStr}">
								</div>
							</div>
							
							<div class="form-group">
								<label for="skuName" class="col-sm-2 control-label">结束时间</label>
								<div class="col-lg-3">
									<input type="text" id="endTime" name="endTime" class="form-control" value="${banner.endTimeStr}">
								</div>
							</div>
							
						    <br>
							
						</form>
		     			<button type="button" class="btn btn-primary" id="nextButton1">下一步</button>
					</div>
					
					<!-- tab第二部分 -->
					<div class="tab-pane" contenteditable="false" id="panel-2">
						<div class="box-content">
							<div id="treeWrap">
								<ul id="selectTree" class="ztree"></ul>
							</div>
						</div>
						<%-- <%@ include file="region_tree_forGoods.jsp"%> --%>
						
						<button type="button" class="btn btn-default" data-dismiss="modal" id="beforeButton">上一步</button>
		     			<button type="button" class="btn btn-primary" id="nextButton2">保存</button>
					</div>
				</div>
			</div>
			
			</div>
		</div>
	</div>


<%@ include file="../footer.jsp"%>

<script type="text/javascript">
var modelIdName = {
	5:"score",
	6:"usercenter",
	7:"discovery"
}

$(document).ready(function(){
	$('#bannerPosition').trigger("change");
	
	var hrefType = $('#hrefType').val();
	if("discovery" == hrefType || "usercenter" == hrefType || "score" == hrefType || "mall" == hrefType ){
		$('#mall_li').addClass('active');
		$('#panel-mall').addClass('active');
	}else if("category" == hrefType){
		$('#category_li').addClass('active');
		$('#panel-category').addClass('active');
	}else if("activity" == hrefType){
		$('#activity_li').addClass('active');
		$('#panel-activity').addClass('active');
	}else if("goodsdetail" == hrefType){
		$('#goods_li').addClass('active');
		$('#panel-goods').addClass('active');
	}else if("webview" == hrefType){
		$('#url_li').addClass('active');
		$('#panel-url').addClass('active');
	}
	/* 
	//老版本跳转控制
	var hrefUrlStr = $('#hrefUrlText').text();
	var actionTypeId = $("input[name='actionType'][checked='checked']").val();
	
	if(actionTypeId == 1){
		$('#hrefUrl1').children().each(function(){
			if($(this).val() == hrefUrlStr){
				$(this).attr('selected','selected');
			}
		});
		$('#hrefUrl3').val("");
	}else if(actionTypeId == 2){
		$('#hrefUrl2').children().each(function(){
			if($(this).val() == hrefUrlStr){
				$(this).attr('selected','selected');
			}
		})
		$('#hrefUrl3').val("");
	} */
});

$('#startTime').datetimepicker({
	language : 'zh-CN',
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	forceParse : 0,
	showMeridian : 1,
	format : 'yyyy-mm-dd hh:ii:00'
});
$('#endTime').datetimepicker({
	language : 'zh-CN',
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	forceParse : 0,
	showMeridian : 1,
	format : 'yyyy-mm-dd hh:ii:00'
});

var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
};

$('#nextButton1').click(function() {
    if(checkParam()){
		$.post("/banner/edit", $("#editForm").serialize(), function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				$('#bannerId').val(json.data);
				$('#a_2').trigger('click');
			}
		});
	} 
});


//仓库管理
$('#beforeButton').click(function() {
	$('#a_1').trigger('click');
});

$('#nextButton2').click(function() {
	var bannerId = $('#bannerId').val();
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	var info = JSON.stringify(nodes);
	$.post("/banner/setRegion/byBannerId",{"bannerId":bannerId,"treeInfo": info}, function(json){
		if (json.code != 0) {
			alert(json.msg);
		}else{
			alert(json.msg);
			window.location.href='/banner/list';
		}
	});
});

$('#a_1').click(function(){})

$('#a_2').click(function() {
	var bannerId = $('#bannerId').val();
	if(bannerId == ''){
		alert("请首先填写基础信息，并单击保存或下一步");
		return false;
	}

	//为范围设值
	$.post("/banner/getRegion/byBannerId",{"bannerId":bannerId}, function(json){
		if (json.code != 0) {
			alert(json.msg);
		} else {
			var zNodes = json.data;
			$.fn.zTree.init($("#selectTree"), setting, zNodes);
		}
	});
	
});
//显示位置
$('#bannerPosition').change(function(){
	var defaultConnectId = $('#connectIdText').text();
	var positionId = $('#bannerPosition option:selected').val();
	if(positionId == 0){
		$('#connectId').hide();
	}else if(positionId == 1){
		$.post('/banner/activity/all',function(json){
			if(json.code == 0){
				var activities = json.data;
				var activity = null;
				var str = '';
				for(var i = 0;i< activities.length;i++){
					activity = activities[i];
					if(activity != null){
						if(activity.activityId == defaultConnectId){
							str += '<option selected="selected" value="'+activity.activityId+'">'+activity.activityName+'</option>';
						}else{
							str += '<option value="'+activity.activityId+'">'+activity.activityName+'</option>';
						}
					}
				}
				$('#connectId option').remove();
				$('#connectId').append(str);
				$('#connectId').show();
			}else{
				alert(json.msg);
			}
		},'json');
	}else if(positionId == 2){
		$.post('/banner/category/all',function(json){
			if(json.code == 0){
				var categories = json.data;
				var category = null;
				var str = '';
				for(var i = 0;i< categories.length;i++){
					category = categories[i];
					if(category != null){
						if(category.categoryId == defaultConnectId){
							str += '<option selected="selected" value="'+category.categoryId+'">'+category.categoryName+'</option>'
						}else{
							str += '<option value="'+category.categoryId+'">'+category.categoryName+'</option>'
						}
					}
				}
				$('#connectId option').remove();
				$('#connectId').append(str);
				$('#connectId').show();
			}else{
				alert(json.msg);
			}
		},'json');
	}
});

//banner链接
$('#a_mall').click(function(){
	$('#hrefType').val('mall');
	$.post('/banner/model/all',function(ret){
		if(ret.code == 0){
			var models = ret.data;
			var model = null;
			var str = '';
			for(var i = 0;i< models.length;i++){
				model = models[i];
				if(model != null){
					if(model.selected == true){
						str += '<option value="'+model.value+'" selected="selected">'+model.name+'</option>';
					}else{
						str += '<option value="'+model.value+'">'+model.name+'</option>';
					}
					
				}
			}
			$('#mall_model option').remove();
			$('#mall_model').append(str);
		}else{
			alert(ret.msg);
		}
	},'json');
})
$('#a_category').click(function(){
	$('#hrefType').val('category');
	$.post('/banner/category/all',function(ret){
		if(ret.code == 0){
			var categories = ret.data;
			var category = null;
			var str = '';
			for(var i = 0;i< categories.length;i++){
				category = categories[i];
				if(category != null){
					str += '<option value="'+category.categoryId+'">'+category.categoryName+'</option>'
				}
			}
			$('#hrefCategoryId option').remove();
			$('#hrefCategoryId').append(str);
		}else{
			alert(ret.msg);
		}
	},'json');
})
$('#a_activity').click(function(){
	$('#hrefType').val('activity');
	$.post('/banner/activity/all',function(ret){
		if(ret.code == 0){
			var activities = ret.data;
			var activity = null;
			var str = '';
			for(var i = 0;i< activities.length;i++){
				activity = activities[i];
				if(activity != null){
					str += '<option value="'+activity.activityId+'">'+activity.activityName+'</option>'
				}
			}
			$('#hrefActivityId option').remove();
			$('#hrefActivityId').append(str);
		}else{
			alert(ret.msg);
		}
	},'json');
})
$('#a_goods').click(function(){
	$('#hrefType').val('goodsdetail');
})
$('#a_url').click(function(){
	$('#hrefType').val('webview');
})

$('#mall_model').change(function(){
	var modelId = $('#mall_model option:selected').val();
	$('#hrefType').val(modelIdName[modelId]);
	$.post('/banner/mallPage/all',{"type":modelId},function(ret){
		if(ret.code == 0){
			var models = ret.data;
			var model = null;
			var str = '';
			for(var i = 0;i< models.length;i++){
				model = models[i];
				if(model != null){
					str += '<option value="'+model.value+'">'+model.name+'</option>'
				}
			}
			$('#mallPage option').remove();
			$('#mallPage').append(str);
		}else{
			alert(ret.msg);
		}
	},'json');
});


function checkParam(){
	for(var i=0;i<$(".request").length;i++){
		if($.trim($(".request").eq(i).val()).length==0){
			alert($(".request").eq(i).attr('placeholder')+"是必填项");
			return false;
		}
	}
	return true;
}

</script>