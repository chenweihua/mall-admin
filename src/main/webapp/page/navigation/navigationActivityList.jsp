<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.vo.navigation.NavigationActivityType" %>

<%@ include file="../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<style>
<!--
.control-label {
	width : 10%;
	text-align : right;
	margin-right : 10px;
}
-->
</style>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i>
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
					<a href="#"	class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
					<a href="#"	class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">
		        <div class="row">
		        
		        	<div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">上级菜单</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="navMenuIdQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="navMenuIdQuery"  data-val="">不限</a></li>
			                  <c:forEach items="${menus}" var="menu">
			                  	<li><a href="javascript:void(0);" class="navMenuIdQuery"  data-val="${menu.navMenuId }">${menu.menuName }</a></li>
			                  </c:forEach>
			                </ul>
			              </div>
			            </div>
			        </div>
		        	<div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">活动名称</span> 
			              <input type="text" id="activityNameQuery" class="form-control" value="${activityNameQuery}" placeholder="">
			            </div>
			        </div>
			          
			        <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">活动类型</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="activityTypeQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="activityTypeQuery"  data-val="">不限</a></li>
			                  <c:forEach items="${NavigationActivityType}" var="navigationActivityType">
			                  	<li><a href="javascript:void(0);" class="activityTypeQuery"  data-val="${navigationActivityType.code }">${navigationActivityType.name }</a></li>
			                  </c:forEach>
			                </ul>
			              </div>
			            </div>
			         </div>
			        <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">生失效状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="statusQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="">不限</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="1">生效</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="2">失效</a></li>
			                </ul>
			              </div>
			            </div>
			         </div>
		        			          
			          <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
					
					
					
				
		        <br />
        		<br />
				<table id="navigationActivityList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
								<th style="width: 5%">序号</th>
								<th style="width: 5%">活动类型</th>
								<th style="width: 5%">活动名称</th>
								<th style="width: 5%">权重</th>
								<th style="width: 5%">图片</th>
								<th style="width: 5%">控制方式</th>
								<th style="width: 5%">状态</th>
								<th style="width: 5%">活动开始时间</th>
								<th style="width: 5%">活动结束时间</th>
								<th style="width: 5%">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>

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
					<div class="box-content">
						<form role="form" id="form1">
							<input type="hidden" name="navActivityId" id="navActivityId" />
							
							<div class="form-group">
								<label for="navMenuId" class="control-label">上级菜单:</label>
								 <select name="navMenuId" id="navMenuId" style="width:30%;">
								 		<option value="">不限</option>
								 	<c:forEach items="${menus}" var="menu">
								 		<option value="${menu.navMenuId }">${menu.menuName }</option>
								 	</c:forEach>
								 </select>
							</div>
							<div class="form-group">
								<label for="activityName" class="control-label">活动名称:</label>
								<input name="activityName" id="activityName" value="${navigationActivity.activityName}" placeholder="" style="width:50%;">
							</div>
							<div class="form-group">
				              <label for="lableCoupon" class="control-label">活动类型:</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				                  <span id="activityTypeEditText"></span>&nbsp;
				                  <span class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                		<li><a href="javascript:void(0);" class="activityTypeEdit" data-val="">不限</a></li>
				                  <c:forEach items="${NavigationActivityType}" var="navigationActivityType">
			                 	  		<li><a href="javascript:void(0);" class="activityTypeEdit" data-val="${navigationActivityType.code }">${navigationActivityType.name }</a></li>
			                 	  </c:forEach>
				                </ul>
				              </div>
				            </div>
							<div class="form-group">
								<label for="activityUrl" class="control-label">活动地址:</label>
								<input name="activityUrl" id="activityUrl" value="${navigationActivity.activityUrl}" placeholder="" style="width:50%;">
							</div>
							<div class="form-group">
								<label for="weight" class="control-label">权重:</label>
								<input name="weight" id="weight" value="${navigationActivity.weight}" placeholder="" style="width:50%;">
							</div>
							<div class="form-group">
								<label for="imageUrl" class="control-label">图片:</label>
								<img id="uploadPreview1" src="" style="width:200px" alt="头部图片"/>
								<input id="uploadImage1" type="file" name="p1" onchange="PreviewImage(1);" style="display:inline;" />
  								<input type="hidden" id="imageUrl1" name="imageUrl" value="${navigationActivity.imageUrl}">
							</div>
							
							<div class="form-group">
								<label for="title" class="control-label">控制方式:</label>
								<input type="radio" name="openType" value="1" id="openTypeManual" />手动控制
								<input type="radio" name="openType" value="2" id="openTypeAuto" />自动控制
							</div>
							
							
							<div class="form-group" id="manualArea">
								<label for="title" class="control-label">状态:</label> 
								<input type="radio"  name="status" value="1" />开启
								<input type="radio"  name="status" value="0" />关闭
							</div>
							
							
							<div class="form-group" id="autoArea" style="display:none;">
				              <label for="lableCoupon" class="control-label">开始时间:</label>
				              <input style="width: 160px; display: inline-block;" class="form-control" id="beginTime" name="beginTime" readonly /> 
				              <label for="lableCoupon" class="control-label">结束时间:</label>
				              <input style="width: 160px; display: inline-block;" class="form-control" id="endTime" name="endTime" readonly />
				            </div>
							
							
							<div class="form-group">
								<label for="remark" class="control-label">备注:</label>
								<textarea rows="5" cols="47" name="remark" id="remark">${navigationActivity.remark}</textarea>
							</div>
							
				            
						</form>
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" id="btnSubmitEdit">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" id="btnCancle">取消</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../footer.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>

var activityTypeMap = {
	<c:forEach items="${NavigationActivityType}" var="navigationActivityType">
		"${navigationActivityType.code}":"${navigationActivityType.name}",
	</c:forEach>
	"":"不限"
};

var openTypeMap = {
	"":"不限",
	"1":"手动控制",
	"2":"自动控制"
};

var stateMap = {
		"1" : "生效",	
		"2" : "失效",	
		"" : "不限"
	};

var statusMap = {
	"0" : "关闭",	
	"1" : "开启",	
	"" : "不限"
};

var navMenuIdMap = {
	<c:forEach items="${menus}" var="menu">
 		"${menu.navMenuId }" : "${menu.menuName }",
 	</c:forEach>
	"":"不限"
};


var activityTypeQuery;
$('.activityTypeQuery').click(function(){
	activityTypeQuery = $(this).data("val") + "";
    $('#activityTypeQueryText').html(activityTypeMap[activityTypeQuery]);
});

var statusQuery;
$('.statusQuery').click(function(){
	statusQuery = $(this).data("val") + "";
    $('#statusQueryText').html(stateMap[statusQuery]);
});

var navMenuIdQuery;
$('.navMenuIdQuery').click(function(){
	navMenuIdQuery = $(this).data("val") + "";
    $('#navMenuIdQueryText').html(navMenuIdMap[navMenuIdQuery]);
});


var activityTypeEdit = "";
$('.activityTypeEdit').click(function(){
	activityTypeEdit = $(this).data("val") + "";
    $('#activityTypeEditText').html(activityTypeMap[activityTypeEdit]);
});




var urlPrefix = '';
var table = null;


var navigationActivityList ;


$('#addButton').click(function () {
	clearAll();
	$('#modalEdit').modal();
});

$('#beginTime,#beginTimeQuery,#endTime,#endTimeQuery').datetimepicker({
	
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: "yyyy-mm-dd hh:ii:ss"
});

/*
$('#endTime').datetimepicker({
	 language:  'zh-CN',
     weekStart: 1,
     todayBtn:  1,
     autoclose: 1,
     todayHighlight: 1,
     startView: 2,
     forceParse: 0,
     showMeridian: 1,
     format: "yyyy-mm-dd hh:ii:ss"
});
*/




$('#btnSubmitEdit').click(function(){
	
	
	
	
	if(!checkParam()) {
		return;
	}
	
	$.post(urlPrefix+"save"+"?activityType="+activityTypeEdit, $("#form1").serialize(), function(response){
		if (response.code == 0) {
			alert("操作成功");
			$("#form1")[0].reset();
			clearAll();
	        table.ajax.reload(null, true);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.msg);
		}
	});
	
	return false;
	
});

$('#btnCancle').click(function(){
	clearAll();
	$('#modalEdit').modal('hide');
});


function checkParam() {
	
	if($.trim($("#navMenuId").val()) == "") {
		alert("上级菜单不能为空！");
		return false;
	}
	if($.trim($("#activityName").val()) == "") {
		alert("活动名称不能为空！");
		return false;
	}
	if($.trim($("#activityUrl").val()) == "") {
		alert("活动地址不能为空！");
		return false;
	}
	if($.trim($("#weight").val()) == "") {
		alert("权重不能为空！");
		return false;
	}
	if(!checkInteger($("#weight").val())) {
		alert("权重只能为数字");
		return false;
	}
	if($.trim($("#remark").val()) == "") {
		alert("备注不能为空！");
		return false;
	}
	if($.trim($("#imageUrl1").val()) == "") {
		alert("图片url不能为空！");
		return false;
	}
	
	if($("input[name=openType]:checked").size() == 0) {
		alert("请选择控制方式!");
		return false;
	}
	
	var openType = $("input[name=openType]:checked").val();
	if(openType == "1") { //手动
		
		if($("input[name=status]:checked").size() == 0) {
			alert("请选择状态!");
			return false;
		}
		
	} else if(openType == "2") {  //自动
		if($("#beginTime").val() == "" || $("#endTime").val() == "") {
			alert("请选择开始时间和结束时间");
			return false;
		}
		
		if($("#beginTime").val() > $("#beginTime").val()) {
			alert("开始时间不能大于结束时间");
			return false;
		}
	
	} else {
		alert("控制方式不合法：" + openType);
		return false;
	}
	
	if(activityTypeEdit == '') {
		alert("请选择活动类型");
		return false;
	}
	
	return true;
}


function clearAll() {

	$("#navActivityId").val("");
	$("#navMenuId").val("");
	$("#activityName").val("");
	$("#activityUrl").val("");
	$("#weight").val("");
	$("#remark").val("");
	$("#imageUrl1").val("");
	$("#uploadPreview1").attr("src","");
	$("#status").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
	
	//$("input[name=openType]").prop("checked",false);
	$("#openTypeManual").prop("checked",true);
	$("#manualArea").show();
	$("#autoArea").hide();
	
	activityTypeEdit = '';
	$("#activityTypeEditText").html(activityTypeMap[""]);
	
	
}


function deleteNavigationActivity(index) {
	var navigationActivity = navigationActivityList[index];
	if(!confirm("您确认要删除\"" + navigationActivity.activityName +"\"吗？" )) {
		return;
	}
	$.post("delete", {
		"navActivityId" : navigationActivity.navActivityId
	}, function(response) {
		if (response.code == 0) {
			alert(response.msg);
			table.ajax.reload();
		} else {
			alert(response.msg);
		}
	});
}


function edit (index) {
	
	
	var navigationActivity = navigationActivityList[index];
	$("#navActivityId").val(navigationActivity.navActivityId);
	
	$("#navMenuId").val(navigationActivity.navMenuId);
	$("#activityName").val(navigationActivity.activityName);
	$("#activityUrl").val(navigationActivity.activityUrl);
	$("#weight").val(navigationActivity.weight);
	$("#remark").val(navigationActivity.remark);
	$("#imageUrl1").val(navigationActivity.imageUrl);
	$("#uploadPreview1").attr("src",navigationActivity.imageUrl);
	
	$("input[name=openType][value=" + navigationActivity.openType + "]").prop("checked",true);
	$("input[name=status][value=" + navigationActivity.status + "]").prop("checked",true);
	
	activityTypeEdit = navigationActivity.activityType;
	$("#activityTypeEditText").html(activityTypeMap[activityTypeEdit]);
	
	if(navigationActivity.openType == 2) {
		$("#beginTime").val(new Date(navigationActivity.beginTime).Format("yyyy-MM-dd hh:mm:ss"));
		$("#endTime").val(new Date(navigationActivity.endTime).Format("yyyy-MM-dd hh:mm:ss"));
	}
	if(navigationActivity.openType == 1) {
		$("#manualArea").show();
		$("#autoArea").hide();
	} else if(navigationActivity.openType == 2) {
		$("#manualArea").hide();
		$("#autoArea").show();
	}
	
	$('#modalEdit').modal();
}






//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload(null, true);
	
});

$(document).ready(function() {
	
	table = $('#navigationActivityList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: urlPrefix+"query",
        	data: function(d) {
                d.navMenuId = navMenuIdQuery;
                d.activityName = $("#activityNameQuery").val();
                d.activityType = activityTypeQuery;
                d.status = statusQuery;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		navigationActivityList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var navigationActivity = json.data[i];
        			var imgShow = "";
        			if(navigationActivity.imageUrl != null && navigationActivity.imageUrl != "") {
        				imgShow = '<img src="' + navigationActivity.imageUrl + '" style="height:10%;" />';
        			}
        			newData[newData.length] = [
        									   			i + 1,
        									   			activityTypeMap[navigationActivity.activityType],
        									   			navigationActivity.activityName,
        									   			navigationActivity.weight,
        									   			imgShow,
        									   			openTypeMap[navigationActivity.openType],
        									   			navigationActivity.openType == 1 ? statusMap[navigationActivity.status] : "",
        									   			navigationActivity.openType == 2 ? (typeof(navigationActivity.beginTime) == "undefined" ? '' : new Date(navigationActivity.beginTime).Format("yyyy-MM-dd hh:mm:ss")) : "",
        									   			navigationActivity.openType == 2 ? (typeof(navigationActivity.endTime) == "undefined" ? '' : new Date(navigationActivity.endTime).Format("yyyy-MM-dd hh:mm:ss")) : "",
        			                           '<a href="javascript:edit(' + i + ');">编辑</a>&nbsp;&nbsp;<a href="javascript:deleteNavigationActivity(' + i + ');">删除</a>'
        			                          ]; 
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...", 
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页"

            }
                                           
        }
    } );

	
	$("#openTypeManual,#openTypeAuto").click(function(){
		
		if($("input[name=openType]:checked").val() == "1") {
			$("#manualArea").show();
			$("#autoArea").hide();
		} else {
			$("#manualArea").hide();
			$("#autoArea").show();
		}
		
	});
	
} );

//判断正整数
function checkInteger(input) {
     var re = /^[1-9]+[0-9]*]*$/;

     if (!re.test(input)) {
        return false;
     }
     
     return true;
}

</script>