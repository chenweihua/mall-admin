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
					<i class="glyphicon glyphicon-item"></i> 商家列表
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
		             	 <span class="input-group-addon">供应商名称</span> <input type="text"
		                	id="searchStr" class="form-control"  placeholder="供应商名称">
		           	 	</div>
		          	</div>
		          	<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">状态</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="shopIsOpenText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="shopIsOpenMenu">
									<li><a href="javascript:void(0);" class="shopIsOpen"
										data-val="-1">全部</a></li>
									<li><a href="javascript:void(0);" class="shopIsOpen"
										data-val="0">关闭</a></li>
									<li><a href="javascript:void(0);" class="shopIsOpen"
										data-val="1">开启</a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">供应商类型</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="shopTypeText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="shopTypeMenu">
									<li><a href="javascript:void(0);" class="shopType"
										data-val="-1">全部</a></li>
									<li><a href="javascript:void(0);" class="shopType"
										data-val="1">O2O</a></li>
									<li><a href="javascript:void(0);" class="shopType"
										data-val="2">电商</a></li>
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
		    </div>
			
			<div class="box-content">
				<table id="supplierTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>供应商ID</th>
							<th>供应商名称</th>
							<th>供应商编码</th>
							<th>状态</th>
							<th>创建时间</th>
							<th>修改时间</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
	<button type="button" class="btn btn-primary btn-sm" id="addButton" >添加供应商</button>
</div>
<!--/row-->

<%@ include file="editSupplier.jsp"%>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">

var shopIsOpenMap = {
		1: "开启",
		0: "关闭"
	};

/* 供应商状态 */
var shopIsOpen = -1;
$('#shopIsOpenMenu').delegate("li a","click",function() {
	shopIsOpen= $(this).data("val");
	$('#shopIsOpenText').html($(this).text());
	table.ajax.reload();
});

/* 供应商类型 */
var shopType = -1;
$('#shopTypeMenu').delegate("li a","click",function() {
	shopType= $(this).data("val");
	$('#shopTypeText').html($(this).text());
	table.ajax.reload();
});

$('#btnSubmit').click(function () {
	table.ajax.reload();
}); 

var table = null;

$(document).ready(function() {
	//datatable
	table = $("#supplierTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
	       	url:"querySupplier",
	       	data: function(d) {
        		d.searchStr=$("#searchStr").val();
        		d.shopType=shopType;
        		d.shopIsOpen=shopIsOpen
            },
 			dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			var shopIsOpenOprator;
        			if(item.shopIsOpen == 0) {
        				shopIsOpenOprator = 1; 
        			}
        			else {
        				shopIsOpenOprator = 0; 
        			}
        			shopIsOpenMap[item.shopIsOpen]
					var operatorstr = '<a href="javascript:void(0)" onclick="editSupplier('+item.shopId+');">【编辑】</a><a href="javascript:void(0)" onclick="operateSupplier('+item.shopId+','+item.shopIsOpen+');">【'+shopIsOpenMap[shopIsOpenOprator]+'】</a>';
					var createTime=new Date(item.createTime);
                    var updateTime = new Date(item.updateTime);
                    if(updateTime < createTime){
                    	updateTime = createTime;
                    }
                    newData[newData.length] = [
            			               item.shopId,
            			               item.shopName,
            			               item.sellerCode,
            			               shopIsOpenMap[item.shopIsOpen],
            			               createTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               updateTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               operatorstr
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

function editSupplier(shopId){
	$.getJSON("querySupplierById?shopId="+shopId, function (ret) {
        if (ret.code == 0) {
        	var resultData = ret.data;
        	$('#shopId').val(resultData.shopId);
        	$('#shopName').val(resultData.shopName);
        	$('#sellerCode').val(resultData.sellerCode);
        	$('#sellerId').val(resultData.sellerId);
        	$('#shopKeeper').val(resultData.shopKeeper);
        	$('#shopPhone').val(resultData.shopPhone);
        	$('#shopWeight').val(resultData.shopWeight);
        	$('#storageId').val(resultData.storageId);
        	$('#imageUrl1').val(resultData.imageUrl);
        	$('#uploadPreview1').prop('src', resultData.imageUrl);
        	$('input[name="shopType"][value='+resultData.shopType+']').prop('checked',true);
        	$('input[name="shopIsOpen"][value='+resultData.shopIsOpen+']').prop('checked',true);
        	$("#modalEdit").modal();
        }else {
        	alert("操作失败");
        }
    });
	
}

function operateSupplier(shopId, shopIsOpen){
	var isOpen;
	if(shopIsOpen == 0) {
		isOpen = 1;
	}
	else {
		isOpen = 0;
	}
    $.getJSON("editSupplierStatus?shopId="+shopId+"&shopIsOpen="+isOpen, function (ret) {
        if (ret.code == 0) {
            alert(ret.msg);
            table.ajax.reload();
        }else {
        	alert("操作失败");
        }
    });
}



$("#addButton").click(function(){
	$('#shopName').val("");
	$('#sellerCode').val("");
	$('#sellerId').val("");
	$('#shopKeeper').val("");
	$('#shopPhone').val("");
	$('#shopWeight').val("");
	$('#imageUrl1').val("");
	$('#uploadPreview1').prop("src", "");
	$('#shopId').val("0");
	$('#storageId').val("0");
	$('input[name="shopType"][value="1"]').prop('checked',true);
	$('input[name="shopIsOpen"][value="1"]').prop('checked',true);
	$("#modalEdit").modal();
});

$('#start-time').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:ss'
});

$('#end-time').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'yyyy-mm-dd hh:ii:ss'
  });
    
</script>