<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.enumdata.NavigationType"%>

<%@ include file="../header.jsp"%>

<c:set var="navigationTypes" value="<%=NavigationType.values()%>"/>

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
					<i class="glyphicon glyphicon-banner">导航管理</i>
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
			              <span class="input-group-addon">导航名称</span> 
			              <input type="text" id="navigationNameQuery" class="form-control" value="${navigationNameQuery}" placeholder="">
			            </div>
			        </div>
			          
			        <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="statusQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="">不限</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="0">关闭</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="1">开启</a></li>
			                </ul>
			              </div>
			            </div>
			         </div>
		        			          
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
					
					</div>
				
		        <br />
        		<br />
				<table id="navigationList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
								<th style="width: 5%">导航名称</th>
								<th style="width: 5%">导航描述</th>
								<th style="width: 5%">导航类型</th>
								<th style="width: 5%">权重</th>
								<th style="width: 5%">创建时间</th>
								<th style="width: 5%">更新时间</th>
								<th style="width: 5%">是否展示</th>
								<th style="width: 5%">状态</th>
								<th style="width: 5%">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>
<!--/row-->

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
							<input type="hidden" name="navigationId" id="navigationId" />
							
							<div class="form-group">
								<label for="navigationName" class="control-label">导航名称:</label>
								<input name="navigationName" id="navigationName" value="${navigation.navigationName}" placeholder="" style="width:50%;">
							</div>
							
							<div class="form-group">
				    			<label for="description" class="col-sm-3 control-label">导航类型</label>
						        	<select class="form-control" id="navigationType" name="navigationType" style="width:150px" onchange="navigationTypeChange()">
				                        	<c:forEach var="nagationtype" items="${navigationTypes}">
				                        		<option value="${nagationtype.type}">${nagationtype.name}</option>
				                        	</c:forEach>
						            </select>
						            <input name="navigationType_temp" id="navigationType_temp" type="hidden">
							</div>
							<div class="form-group">
								<label for="navigationDesc" class="control-label">导航描述:</label>
								<textarea rows="5" cols="48" name="navigationDesc" id="navigationDesc">${navigation.navigationDesc}</textarea>
							</div>
							<div class="form-group" id="navigationUrlDIV">
								<label for="navigationUrl" class="control-label">链接地址:</label>
								<textarea rows="5" cols="48" name="navigationUrl" id="navigationUrl">${navigation.navigationUrl}</textarea>
							</div>
							<div class="form-group" id="insideUrlDIV">
								<label for="insideUrl" class="control-label">内部链接:</label>
								<input name="insideUrl" id="insideUrl" value="${navigation.insideUrl}" placeholder="" style="width:50%;">
							</div>
							<div class="form-group">
								<label for="weight" class="control-label">权重:</label>
								<input name="weight" id="weight" value="${navigation.weight}" placeholder="" style="width:50%;">
							</div>
							
							<div class="form-group">
								<label for="title" class="control-label">是否展示:</label>
								<input type="radio" name="isShow" value="0" />不展示
								<input type="radio" name="isShow" value="1" />展示
							</div>
							
							<div class="form-group">
								<label for="title" class="control-label">状态:</label>
								<input type="radio" name="status" value="0" />关闭
								<input type="radio" name="status" value="1" />开启
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
<%@ include file="../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>


var statusMap = {
	"":"不限",
	"0":"关闭",
	"1":"开启"
};

var statusQuery;
$('.statusQuery').click(function(){
	statusQuery = $(this).data("val") + "";
    $('#statusQueryText').html(statusMap[statusQuery]);
});



var urlPrefix = '';
var table = null;


var navigationList ;


$('#addButton').click(function () {
	$("#navigationId").val("");
	$("#navigationType").removeAttr("disabled");
	var navigationType = $('#navigationType').val();
	if(navigationType==1){
		$("#navigationUrlDIV").show();
		$("#insideUrlDIV").show();
	}else{
		$("#navigationUrlDIV").hide();
		$("#insideUrlDIV").hide();
	}
	$("#navigationType_temp").val(0);
	clearAll();
	$("input[name=status][value=1]").prop("checked",true);
	$("input[name=isShow][value=1]").prop("checked",true);
	$('#modalEdit').modal();
});

function navigationTypeChange(){
	
	var navigationType = $('#navigationType').val();
	if(navigationType==1){
		$("#navigationUrlDIV").show();
		$("#insideUrlDIV").show();
	}else{
		$("#navigationUrlDIV").hide();
		$("#insideUrlDIV").hide();
	}
}

$('#btnSubmitEdit').click(function(){
	
	if(!checkParam()) {
		return;
	}
	$.post(urlPrefix+"save", $("#form1").serialize(), function(response){
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
	//超技返才检查链接地址。淘精品不检查连接地址。
	if($("#navigationType").val()==1){
		if($.trim($("#navigationUrl").val()) == "") {
			alert("链接地址不能为空！");
			return false;
		}
	}
	if($.trim($("#navigationName").val()) == "") {
		alert("导航名称不能为空！");
		return false;
	}

	if($.trim($("#navigationName").val()).length > 6) {
		alert("导航名称太长，请控制在6个字符以内！");
		return false;
	}
	if($.trim($("#navigationDesc").val()).length > 250) {
		alert("导航描述请控制在250个字符以内！");
		return false;
	}
	

	if($.trim($("#navigationUrl").val()).length > 250) {
		alert("链接地址请控制在250个字符以内！");
		return false;
	}
	if($.trim($("#insideUrl").val()).length > 100) {
		alert("内部链接请控制在100个字符以内！");
		return false;
	}
	if($.trim($("#weight").val()) == "") {
		alert("权重不能为空！");
		return false;
	}
	if($.trim($("#weight").val()).length > 5) {
		alert("权重不能超过5位！");
		return false;
	}
	if(!checkInteger($.trim($("#weight").val()))) {
		alert("权重请输入数字！");
		return false;
	}
	
	
	if($("input[name=status]:checked").size() == 0) {
		alert("请选择状态!");
		return false;
	}
	
	
	return true;
}


function clearAll() {

	$("#navigationName").val("");
	$("#navigationDesc").val("");
	$("#navigationUrl").val("");
	$("#insideUrl").val("");
	$("#weight").val("");
	
	$("input[name=status]").prop("checked",false);

}


function edit (index) {
	
	var navigation = navigationList[index];
	if(navigation.navigationType==1){
		$("#navigationUrlDIV").show();
		$("#insideUrlDIV").show();
	}else{
		$("#navigationUrlDIV").hide();
		$("#insideUrlDIV").hide();
	}
	$("#navigationType").prop("disabled", "disabled");
	$("#navigationType").val(navigation.navigationType);
	$("#navigationType_temp").val(navigation.navigationType);
	
	$("#navigationId").val(navigation.navigationId);
	
	$("#navigationName").val(navigation.navigationName);
	$("#navigationDesc").val(navigation.navigationDesc);
	$("#navigationUrl").val(navigation.navigationUrl);
	$("#insideUrl").val(navigation.insideUrl);

	$("#weight").val(navigation.weight);
	
	$("input[name=status][value=" + navigation.status + "]").prop("checked",true);
	$("input[name=isShow][value=" + navigation.isShow + "]").prop("checked",true);

	$('#modalEdit').modal();
}


var navigationIdForRelation;

function getRegion(navigationId){
    $.post("getNavigationCollege", {"navigationId":navigationId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {
        	 navigationIdForRelation = navigationId;
        	 $('#treeEdit').modal('show');
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     	 }
	},"json"); 
}


$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	
	var nodes=treeObj.getCheckedNodes();
	var checkNodeIdArr = new Array();
	for(var i =0; i < nodes.length; i++) {
		var checkNodeId = nodes[i].id;
		if(checkNodeId.indexOf('college_') == 0) {
			checkNodeId = checkNodeId.substring(checkNodeId.lastIndexOf('_') + 1,checkNodeId.length);
			checkNodeIdArr[checkNodeIdArr.length] = checkNodeId;
		}
	}
	if(checkNodeIdArr.length == 0) {
		alert("请至少选择一个学校节点");
		return;
	}
	
	 $.post("setNavigationCollege",{"navigationId":navigationIdForRelation,"collegeIds":checkNodeIdArr.join(",")}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~")
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
});


//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload(null, true);
	
});

function setstatus(navigationId,status){
	$.post("setstatus",{"navigationId":navigationId,"status":status}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("设置成功~");
			table.ajax.reload(null, false);
     	 }
	},"json"); 
}

$(document).ready(function() {
	
	table = $('#navigationList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: urlPrefix+"query",
        	data: function(d) {
                d.navigationName = $("#navigationNameQuery").val();
                d.status = statusQuery;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		navigationList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var navigation = json.data[i];
        			var navigationType = navigation.navigationType;
        			var navigationTypeStr="未知";
        			var setMenuHref="";
        			if(navigationType==1){
        				navigationTypeStr="超级返";
        			}else if(navigationType==2){
        				navigationTypeStr="淘精品";
        				setMenuHref='<a  href="menuinfo?navigationId=' + navigation.navigationId + '">设置</a>'
        			}
        			var openStr = "";
        			if(navigation.status==0){
        				openStr="关闭&nbsp;&nbsp;"+'<a  href="javascript:setstatus(' + navigation.navigationId + ',1);">【开启】</a>'
        			}else{
        				openStr="开启&nbsp;&nbsp;"+'<a  href="javascript:setstatus(' + navigation.navigationId + ',0);">【关闭】</a>'
        			}
        			newData[newData.length] = [
        									   			navigation.navigationName,
        									   			navigation.navigationDesc,
        									   			navigationTypeStr,
        									   			navigation.weight,
        									   			new Date(navigation.createTime).Format("yyyy-MM-dd"),
        									   			new Date(navigation.updateTime).Format("yyyy-MM-dd"),
        									   			navigation.isShow==0?"否":"是",
        									   			openStr,		
        			                           '<a href="javascript:edit(' + i + ');">编辑</a>&nbsp;&nbsp;<a href="javascript:getRegion('+ navigation.navigationId +');">学校设置</a>&nbsp;&nbsp;'+setMenuHref
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