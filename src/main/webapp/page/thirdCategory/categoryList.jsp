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
					<i class="glyphicon glyphicon-item"></i> 商家类目列表
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
				<div class="row">	
		          	<div class="col-lg-3">
		            	<div class="input-group">
		             	 <span class="input-group-addon">类目名称</span> <input type="text"
		                	id="searchStr" class="form-control"  placeholder="一级类目名称">
		           	 	</div>
		          	</div>
		          	
		          	<div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnSubmit" class="btn btn-default">查询</button>
						</div>
					</div> 	
		          	
		   		 </div>		   		    		 
		    </div>
			
			<div class="box-content">
				<button type="button" class="btn btn-primary btn-sm" id="addButton" >添加一级类目</button>
				<table id="categoryTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>编号ID</th>
							<th>一级类目名称</th>
							<th>创建人</th>
							<th>最后维护人</th>
							<th>创建时间</th>
							<th>更新时间</th>
							<th>分类管理</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
</div>
<!--/row-->

<%@ include file="editCategory.jsp"%>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">

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
        		d.searchStr=$("#searchStr").val();
        		d.level = 1
            },
 			dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
					var operatorstr = '<a href="javascript:void(0)" onclick="managerCategory('+item.propertyCategoryId+');">【管理】</a><a href="javascript:void(0)" onclick="editCategory('+item.propertyCategoryId+');">【编辑】</a><a href="javascript:void(0)" onclick="deleteCategory('+item.propertyCategoryId+');">【删除】</a>';
					var createTime=new Date(item.createTime);
                    var updateTime = new Date(item.updateTime);
                    if(updateTime < createTime){
                    	updateTime = createTime;
                    }
                    newData[newData.length] = [
            			               item.propertyCategoryId,
            			               item.propertyCategoryName,
            			               item.creator != null ? item.creator : "",
            			               item.modifyUser != null ? item.modifyUser: "",
            			               createTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               updateTime.Format("yyyy-MM-dd hh:mm:ss"),
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

function managerCategory(categoryId){
	window.location.href = "secondCategoryList?categoryId=" + categoryId;
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

function deleteCategory(categoryId){
	if(confirm("您确定要删除类目么？")) {
	    $.getJSON("deleteCategory?categoryId="+categoryId, function (ret) {
	        if (ret.code == 0) {
	            alert(ret.msg);
	            table.ajax.reload();
	        }else {
	        	alert("操作失败");
	        }
	    });
	}
}

$("#addButton").click(function(){
	if(!checkParamLevle(1)) {
		return;
	}
	$('#propertyCategoryName').val("");
	$('#propertyCategoryId').val("0");
	$('#pid').val("0");
	$('#level').val("1");
	$("#thirdModalEdit").modal();
});

</script>