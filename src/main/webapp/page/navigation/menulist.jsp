<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.mall.admin.enumdata.NavigationType"%>
<%@ page import="com.mall.admin.enumdata.NavMenuType"%>
<c:set var="navigationMenus" value="<%=NavMenuType.values()%>"/>

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
					<i class="glyphicon glyphicon-banner">导航类目管理</i>
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
					<a href="#"	class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
					<a href="#"	class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">
				<!-- <button type="button" class="btn btn-primary btn-sm" id="addButton1">添加</button>  -->
				<table id="navigationList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
								<th style="width: 5%">序号</th>
								<th style="width: 5%">类目名称</th>
								<th style="width: 5%">子类目名称</th>
								<th style="width: 5%">权重</th>
								<th style="width: 5%">类型</th>
								<th style="width: 5%">状态</th>
								<th style="width: 5%">操作</th>
						</tr>
					</thead>
				</table>
				<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-primary btn-sm" id="goBackBtn">返回</button>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->

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
													
							<div class="form-group" id="pidNameDiv">
								<label for="pidName" class="control-label">父类目:</label>
								<input name="pidName" id="pidName" value="" disabled="disabled">
							</div>
							
							<div class="form-group">
								<label for="navigationName" class="control-label">类目名称:</label>
								<input name="menuName" id="menuName" value="" placeholder="" style="width:50%;">
								<input name="navigationId" id="navigationId" type="hidden" value="${navigationId }"/>
								<input name="pid" id="pid" type="hidden"/>
							</div>
							
							<div class="form-group" id="menuTypeDiv">
				    			<label for="description" class="col-sm-3 control-label">类目类型</label>
						        	<select class="form-control" id="menuType" name="menuType" style="width:150px" >
				                        	<c:forEach var="navigationMenu" items="${navigationMenus}">
				                        		<option value="${navigationMenu.type}">${navigationMenu.name}</option>
				                        	</c:forEach>
						            </select>
							</div>
							<div class="form-group">
								<label for="weight" class="control-label">权重:</label>
								<input name="weight" id="weight" value="" placeholder="" style="width:50%;">
							</div>
							<div class="form-group">
								<label for="title" class="control-label">状态:</label>
								<input type="radio" name="isShow" value="0" />关闭
								<input type="radio" name="isShow" value="1" />开启
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

var table = null;


var navigationMenuList ;

$("#goBackBtn").click(function(){
	
	history.go(-1);
	
});

$('#addButton1').click(function () {
	$("#pid").val("0");
	$("#menuTypeDiv").show();
	$("#pidNameDiv").hide();
	$("#menuName").val('');
	$("#weight").val('');
	$("input[name=isShow][value=1]").prop("checked",true);
	$('#modalEdit').modal();
});

$('#addButton').click(function () {
	$("#pid").val("0");
	$("#menuTypeDiv").show();
	$("#pidNameDiv").hide();
	$("#menuName").val('');
	$("#weight").val('');
	$("input[name=isShow][value=1]").prop("checked",true);
	$('#modalEdit').modal();
});

function addChildMenu(pid){
	var menuParentName="";
	for(var i=0;i<navigationMenuList.length;i++){
		 if(navigationMenuList[i].navMenuId==pid){
			 menuParentName=navigationMenuList[i].menuName;
			 break;
		 }
	}
	if(menuParentName==''){
		alert("添加失败，没有对应的父类目~");
		return;
	}
	
	$("#pid").val(pid);
	$("#menuTypeDiv").hide();
	$("#pidNameDiv").show();
	$("#pidName").val(menuParentName);
	$("#menuName").val('');
	$("#weight").val('');
	$("input[name=isShow][value=1]").prop("checked",true);
	$('#modalEdit').modal();
}

$('#btnSubmitEdit').click(function(){
	
	if(!checkParam()) {
		return;
	}
	$.post("saveMenu", $("#form1").serialize(), function(response){
		if (response.code == 0) {
			alert("操作成功");
	        table.ajax.reload(null, true);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.msg);
		}
	});
	
	return false;
	
});

$('#btnCancle').click(function(){
	$('#modalEdit').modal('hide');
});


function checkParam() {
	
	if($.trim($("#menuName").val()) == "") {
		alert("类目名称不能为空！");
		return false;
	}
	
	if($.trim($("#menuName").val()).length > 4) {
		alert("类目名称长度不能大于4！");
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
	
	if($("input[name=isShow]:checked").size() == 0) {
		alert("请选择状态!");
		return false;
	}

	return true;
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
	$("#navigationId").val(navigation.navigationId);
	
	$("#navigationName").val(navigation.navigationName);
	$("#navigationDesc").val(navigation.navigationDesc);

	$("#weight").val(navigation.weight);
	
	$("input[name=status][value=" + navigation.status + "]").prop("checked",true);
	$("input[name=isShow][value=" + navigation.isShow + "]").prop("checked",true);

	$('#modalEdit').modal();
}


function setIsShow(navMenuId,isShow){
	$.post("setmenuisshow",{"navMenuId":navMenuId,"isShow":isShow}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("修改成功~");
			table.ajax.reload(null, false);
     	 }
	},"json"); 
}

var goodsCount = 0;

function deleteMenu(menuId,pid){
	if(pid==0){
		getGoodsNumByMenuId(menuId,1);
		if(goodsCount==-1){
			alert("查询类目下商品异常，删除类目失败");
			return;
		}
		if(goodsCount>0){
			if(!confirm("父类目下的子类目有商品，删除该类目同时会删除子类目和商品，确定要删除吗？")){
				return;
			}
		}else{
			if(!confirm("删除父类目同时会删除子类目，确定要删除吗？")){
				return;
			}
		}
		
	}else{
		getGoodsNumByMenuId(menuId,2);
		if(goodsCount==-1){
			alert("查询类目下商品异常，删除类目失败");
			return;
		}
		if(goodsCount>0){
			if(!confirm("该类目下有商品，删除该类目时会同时删除商品，确定要删除吗？")){
				return;
			}
		}
	}
	$.post("deleteMenu",{"menuId":menuId,"pid":pid}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("删除成功~");
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
        bPaginate : false, 
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "querymenu",
        	data: function(d) {
                d.navigationId = $("#navigationId").val();
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		navigationMenuList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var navigationMenu = json.data[i];
        			var menuName="";
        			var childMenuName="";
        			if(navigationMenu.pid==0){
        				menuName=navigationMenu.menuName;
        				childMenuName="";
        			}else{
        				childMenuName=navigationMenu.menuName;
        				menuName="";
        			}
        			var openStr = "";
        			if(navigationMenu.isShow==0){
        				openStr="关闭&nbsp;&nbsp;"+'<a  href="javascript:setIsShow(' + navigationMenu.navMenuId + ',1);">【开启】</a>'
        			}else{
        				openStr="开启&nbsp;&nbsp;"+'<a  href="javascript:setIsShow(' + navigationMenu.navMenuId + ',0);">【关闭】</a>'
        			}
        			var menuType="未知";
        			if(navigationMenu.menuType==1){
        				menuType="活动";
        			}else if(navigationMenu.menuType==2){
        				menuType="商品"
        			}
        			var addChildMenu="";
        			if(navigationMenu.pid==0&&navigationMenu.menuType==2){
        				addChildMenu='<a href="javascript:addChildMenu(' +navigationMenu.navMenuId+');">添加子类目</a>'
        			}
        			newData[newData.length] = [
        									   			i+1,
        									   			menuName,
        									   			childMenuName,
        									   			navigationMenu.weight,
        									   			menuType,
        									   			openStr,
        									   			'<a href="javascript:deleteMenu(' + navigationMenu.navMenuId+ ','+navigationMenu.pid+');">删除</a>&nbsp;&nbsp;'+addChildMenu
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

$.ajaxSetup({   
    async : false  
}); 

function getGoodsNumByMenuId(menuId,menuType){
	$.post("/navgoods/goodscountbymenuid",{"menuId":menuId,"menuType":menuType}, function(ret){
		 if (ret.code == 0) {
			 goodsCount=ret.data;
        }else {	
        	alert("查询类目下商品失败");
        	goodsCount = -1;
    	 }
	},"json"); 
}

</script>