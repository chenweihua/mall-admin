<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<%@ include file="../header.jsp"%>
<link id="bs-css" href="/css/style.css" rel="stylesheet">
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 产品列表
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
				<table id="goodsTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th><input type="checkBox" id="checkAll">序号</th>
							<th>商品名称</th>
							<th>商品条码</th>
							<th>商品简称</th>
							<th>品牌名称</th>
							<th>商品类别</th>
							<th>销售规格</th>
							<th>单位</th>
							<th>包装规格</th>
							<th>原产地</th>
							<th>保质期</th>
							<!-- 							<th>状态</th> -->
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->

	<div class="pl15">
 		<span	class="input-file btn btn-primary btn-sm">导入商品<input type="file" id="importfile" name="myfiles" 	onchange="importfile('importfile',2)" /></span>
		<button type="button" class="btn btn-primary btn-sm" id="addButton">添加商品</button>
		<button type="button" class="btn btn-primary btn-sm" id="batchSetButton">批量设置范围</button>
	</div>

</div>
<!--/row-->
<%@ include file="editwmsgoods.jsp"%>
<%@ include file="addwmsgoods.jsp"%>
<%@ include file="../region_tree.jsp"%>
<%@ include file="../footer.jsp"%>
<script id="skuStockTableTemplate" type="text/template">
		<table border='1'><tr><td>仓库</td><td>数量</td></tr>
        {{#goodsStorage}}
            <tr>	
				<td> {{storagename}}</td>
				<td> {{stock}} </td>
			</tr>
        {{/goodsStorage}}
	</table>
</script>

<script type="text/javascript">

//全选
$("#checkAll").click(function() {
	if ($("#checkAll").prop("checked") == true) {
		$("input[name='wmsgoodsId']").each(function() {
			$(this).prop("checked", true);
		});
		//alert();
	} else {
		$("input[name='wmsgoodsId']").each(function() {
			$(this).prop("checked", false);
		});
	}
});

/**
批量修改价格
*/
$("#batchSetButton").click(function(){
	//获取勾选的资产的ID集合
	var wmsgoodsIds = "";
	$("input[name=wmsgoodsId]").each(function() {
		if ($(this).prop("checked")) {
			wmsgoodsIds += $(this).val() + ",";
		}
	});
	if (wmsgoodsIds == "") {
		alert("请选择要批量设置范围的商品~");
		return;
	}
	getRegion(wmsgoodsIds,1);
});

var table = null;

$(document).ready(function() {
	//datatable
	table = $("#goodsTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: true,
        ajax: {
        	url: "query",
        	dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		var start = json.start+1;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			var id = item.wms_goods_id==''?"--":item.wms_goods_id;
                    var name = item.wms_goods_name==''?"--":item.wms_goods_name;
/*                     var showName= '<a class="blueletter" href="javascript:void(0)" onclick="showStorageInfo(this,'+id + ')">'+ name+'</a>'; */
                    var showName=  item.wms_goods_name==''?"--":item.wms_goods_name;
                    var gbm=item.wms_goods_gbm==''?"--":item.wms_goods_gbm;
                    var shortname=item.short_name==''?"--":item.short_name;
                    var brand =item.brand==''?"--":item.brand;
                    var catgName = item.catg_name==''?"--":item.catg_name;
                    var saleSpec=item.sale_spec==''?"--":item.sale_spec;
                    var unit =item.unit==''?"--":item.unit;
                    var packageSpec= item.package_spec==''?"--":item.package_spec;
                    var originPlace= item.origin_place==''?"--":item.origin_place;
                    var shelfLife = item.shelf_life==''?"--":item.shelf_life;
        			var operatorstr= '<a href="javascript:void(0)" onclick="editgoods(this,'+item.wms_goods_id+');">【修改】</a><a href="javascript:void(0)" onclick="getRegion('+ item.wms_goods_id+',0);">【范围设置】</a>';
            			newData[newData.length] = [
            			                           '<input type="checkBox" name="wmsgoodsId" value="'+item.wms_goods_id+'">'+(start+i),
            			                           showName,
            			                           gbm,
            			                           shortname,
            			                           brand,
            			                           catgName,
            			                           saleSpec,
            			                           unit,
            			                           packageSpec,
            			                           originPlace,
            			                           shelfLife,
//             			                           status,
            			                           operatorstr];
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-4'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "请输入商品名称或条码",
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

function editgoods(obj,id){
	
	$("#wms_goods_gbm").attr("readonly", "readonly");
	
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var name=$(child[1]).text();
	var gbm=$(child[2]).text();
	var shortname=$(child[3]).text();
	var brand=$(child[4]).text();
	var catgName=$(child[5]).text();
	var saleSpec=$(child[6]).text();
	var unit=$(child[7]).text();
	var packageSpec=$(child[8]).text();
	var originPlace=$(child[9]).text();
	var shelfLife=$(child[10]).text();

	$("#wms_goods_id").val(id);
	$("#wms_goods_gbm").val(gbm);
	$("#wms_goods_name").val(name);
	$("#short_name").val(shortname);
	$("#brand").val(brand);
	$("#catg_name").val(catgName);
	$("#sale_spec").val(saleSpec);
	$("#unit").val(unit);
	$("#package_spec").val(packageSpec);
	$("#origin_place").val(originPlace);
	$("#shelf_life").val(shelfLife);
	$("#btnSubmitEdit").removeAttr("disabled");
	$("#modalEdit").modal();
	
}


$("#addButton").click(function(){
	
	$("#wms_goods_gbm").removeAttr("readonly");
	
	$("#wms_goods_id").val("-1");
	$("#wms_goods_gbm").val("");
	$("#wms_goods_name").val("");
	$("#short_name").val("");
	$("#brand").val("");
	$("#catg_name").val("");
	$("#sale_spec").val("");
	$("#unit").val("");
	$("#package_spec").val("");
	$("#origin_place").val("");
	$("#shelf_life").val("");
	$("#btnSubmitEdit").removeAttr("disabled");
	$("#modalEdit").modal();
	
});




function importfile(imgfileId,type) {

  $.ajaxFileUpload({  
    fileElementId: imgfileId,  
    url: "import?type="+type, 
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
}  

var wms_goods_id_temp;
function getRegion(wms_goods_id,batchtype){
	$('#treeEdit').modal('show');
	wms_goods_id_temp=wms_goods_id
	var goodsId = wms_goods_id;
	if(batchtype==1){
		//批量修改 上传的id值为0
		goodsId=0;
	}
	/* $.fn.zTree.init($("#selectTree"), setting, zNodes); */
    $.post("getregion", {"wms_goods_id":goodsId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     	 }
	},"json"); 
}


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
 /*初始化树数据格式*/
var zNodes =[
		{ id:1, pId:0, name:"RDC", open:true,icon:"/img/ztree/rdc.png"},
		{ id:11, pId:1, name:"RDC所有仓库",open:true,icon:"/img/ztree/storage.png"},
		{ id:111, pId:11, name:"北京仓",icon:"/img/ztree/storage.png"},
		{ id:112, pId:11, name:"武汉仓",icon:"/img/ztree/storage.png"},
		{ id:1111, pId:111, name:"北京大学",icon:"/img/ztree/college.png"},
		{ id:1112, pId:111, name:"清华大学",icon:"/img/ztree/college.png"},
		{ id:2, pId:0, name:"LDC", checked:true, open:true,icon:"/img/ztree/ldc.png"},
		{ id:21, pId:2, name:"所有城市",open:true,icon:"/img/ztree/storage.png"},
		{ id:221, pId:21, name:"北京市", checked:true,icon:"/img/ztree/storage.png"},
		{ id:222, pId:21, name:"上海市",icon:"/img/ztree/storage.png"},
		{ id:2221, pId:222, name:"小麦店1",icon:"/img/ztree/storage.png"},
		{ id:2222, pId:222, name:"小麦店2",icon:"/img/ztree/storage.png"},
		{ id:23, pId:2222, name:"北京大学",icon:"/img/ztree/college.png"}
	];
$("#selectBtn").click(function(){
	$.fn.zTree.init($("#selectTree"), setting, zNodes);
});


//确定
$("#treeSubmit").click(function(){
var treeObj=$.fn.zTree.getZTreeObj("selectTree");
var nodes=treeObj.getNodes();
var info = JSON.stringify(nodes);
 $.post("setregion",{"wms_goods_id":wms_goods_id_temp,"region":info}, function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("设置成功~")
			 $('#treeEdit').modal('hide');
			$("input[name='wmsgoodsId']").each(function() {
				$(this).prop("checked", false);
			});
     	 }
	},"json");  

});
    
</script>