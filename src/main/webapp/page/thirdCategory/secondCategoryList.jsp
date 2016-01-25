<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ include file="../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 一级类目：
					<c:if test="${not empty thirdCategory}">
						${thirdCategory.propertyCategoryName}
					</c:if>
					<a href="javascript:void(0)" id="backUrlListId" style="margin-left:700px;">返回</a>
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
				<button type="button" class="btn btn-primary btn-sm" id="addButton" >添加二级分类</button>
				<table id="categoryTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>编号ID</th>
							<th>二级类目名称</th>
							<th>三级类目名称</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
</div>
<!--/row-->
<%@ include file="editCategory.jsp" %>
<%@ include file="../footer.jsp"%>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript">
var statusMap = {
		0 : "启用",
		1 : "关闭"
	};
	
$('#btnSubmit').click(function () {
	table.ajax.reload();
}); 

var table = null;
var itemList;
$(document).ready(function() {
	//datatable
	table = $("#categoryTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
	       	url:"queryCategory",
	       	data: function(d) {
        		d.level = 2;
        		d.categoryId = '${thirdCategory.propertyCategoryId}'
            },
 			dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
					var operatorstr = '<a href="javascript:void(0)" onclick="editCategory('+item.propertyCategoryId+');">【编辑】</a><a href="javascript:void(0)" onclick="deleteCategory('+item.propertyCategoryId+','+item.level+','+item.pid+');">【删除】</a>';
					var createTime=new Date(item.createTime);
					/* var operateIsDel;
					if(item.isDel == 0) {//开启
						operateIsDel = 1;
					}
					else {
						operateIsDel = 0;
					} */
					//var statusStr = statusMap[item.isDel]+'&nbsp;&nbsp;<a href="javascript:void(0);" onclick="operateCategory('+item.propertyCategoryId+', '+operateIsDel+');">['+statusMap[operateIsDel]+']</a>';
                    newData[newData.length] = [
            			               item.propertyCategoryId,
            			               '<a href="javascript:void(0);" onclick="getThirdCategory('+item.propertyCategoryId+');" id=category'+item.propertyCategoryId+'>'+item.propertyCategoryName+'</a>',
            			               '<a href="javascript:void(0);" onclick="addThirdCategory('+item.propertyCategoryId+', 3);" id=addThirdCategoryId'+item.propertyCategoryId+'>添加三级类目</a>',
            			               createTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               operatorstr
            							];
        		}
        		json.data = newData;
        		return newData;
        	}
        },
        sDom : "<'row'<'col-md-4'f>r>t<'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
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

function getThirdCategory(categoryId){
	$.getJSON("queryCategory?categoryId="+categoryId+"&level=3&draw=0", function (ret) {
       	var tr = $('#category' + categoryId).parent().parent();
        if (ret.data != null && ret.data != '') {
        	var resultData = ret.data;
        	for(var i=0; i<resultData.length; i++) {
        		var item = resultData[i];
        		var createTime=new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss");
        		var operatorstr = '<a href="javascript:void(0)" onclick="editCategory('+item.propertyCategoryId+');">【编辑】</a><a href="javascript:void(0)" onclick="deleteCategory('+item.propertyCategoryId+','+item.level+','+item.pid+');">【删除】</a>';
	        	tr.after('<tr class="thirdCategoryId'+categoryId+'" role="row" class="even"><td></td><td></td><td>'+item.propertyCategoryName+'</td><td>'+createTime+'</td><td>'+operatorstr+'</td></tr>');
        	}
        }else {
        	alert("没有三级分类数据");
        }
        $('#category' + categoryId).attr("onclick", 'hideThirdCategory('+ categoryId +')');
    });
	
}

function hideThirdCategory(categoryId) {
	var categoryTr = $('.thirdCategoryId' + categoryId);
	categoryTr.remove();
	$('#category' + categoryId).attr("onclick", 'getThirdCategory('+ categoryId +')');
}

function editCategory(categoryId) {
	$.getJSON("getCategoryById?categoryId="+categoryId, function (ret) {
        if (ret.code == 0) {
        	var resultData = ret.data;
        	if(!checkParamLevle(resultData.level)) {
        		return;
        	}
        	$('#propertyCategoryName').val(resultData.propertyCategoryName);
        	$('#propertyCategoryId').val(resultData.propertyCategoryId);
        	$('#pid').val(resultData.pid);
        	$('#level').val(resultData.level);
        	$("#thirdModalEdit").modal();
        }else {
        	alert("操作失败");
        }
    });
}

function checkParamLevle(level) {
	if(level == 3) {
		$('#categoryModalLabel').text('添加/修改三级分类');
	}
	else if (level == 2) {
		$('#categoryModalLabel').text('添加/修改二级分类');
	}
	else if (level == 1) {
		$('#categoryModalLabel').html('添加/修改一级分类');
	}
	else {
		alert("获取数据有误");
		return false;
	}
	return true;
}

function addThirdCategory(categoryId, level) {
	if(!checkParamLevle(level)) {
		return;
	}
	$('#propertyCategoryName').val("");
	$('#propertyCategoryId').val("0");
	$('#pid').val(categoryId);
	$('#level').val(level);
	$("#thirdModalEdit").modal();
}

function deleteCategory(categoryId, level, pid){
	if(confirm("您确定要删除类目么？")) {
	    $.getJSON("deleteCategory?categoryId="+categoryId, function (ret) {
	        if (ret.code == 0) {
	            alert(ret.msg);
	            table.ajax.reload();
	            if(level == 3) {
	            	setTimeout('getThirdCategory('+pid+')', 1000);
				}
	        }else {
	        	alert("操作失败");
	        }
	    });
	}
}



$("#addButton").click(function(){
	var firstCategoryId = '${thirdCategory.propertyCategoryId}';
	addThirdCategory(firstCategoryId, 2);
});

$("#backUrlListId").click(function(){
	window.location.href = "view";
});

</script>