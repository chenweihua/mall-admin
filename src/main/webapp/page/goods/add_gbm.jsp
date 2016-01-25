<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css"
	type="text/css">

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
	
<div class="modal fade" id="gbmEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">选择供应链商品</h4>
			</div>
			<div class="modal-body" style="height: 500px;overflow-y: auto;">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<table id="goodsTable"
					class="table table-striped table-bordered bootstrap-datatable datatable">
					<thead>
						<tr>
							<th>商品编号</th>
							<th>商品名称 </th>
							<th>商品条码</th>
							<th>商品简称</th>
							<th>品牌名称</th>
							<th>商品类别</th>
							<th>销售规格</th>
							<th>单位 </th>
							<th>包装规格</th>
							<th>原产地</th>
							<th>保质期</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
						</div>
					</div>

				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="gbmSubmit">提交</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">

var table = null;

$(document).ready(function() {
	//datatable
	table = $("#goodsTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: true,
        ajax: {
        	url: "/goods/wms/query",
        	dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			var id = item.wms_goods_id==''?"--":item.wms_goods_id;
                    var name = item.wms_goods_name==''?"--":item.wms_goods_name;
/*                     var showName= '<a class="blueletter" href="javascript:void(0)" onclick="showStorageInfo(this,'+id + ')">'+ name+'</a>'; */
                    var showName=  item.wms_goods_name==''?"--":item.wms_goods_name;
                    var gbm=item.wms_goods_gbm==''?"--":item.wms_goods_gbm;
                    var shortname=item.short_name==''?"--":item.short_name;
                    var brand =item.brand==''?"--":item.brand;
                    var catgName = item.short_name==''?"--":item.short_name;
                    var saleSpec=item.sale_spec==''?"--":item.sale_spec;
                    var unit =item.unit==''?"--":item.unit;
                    var packageSpec= item.package_spec==''?"--":item.package_spec;
                    var originPlace= item.origin_place==''?"--":item.origin_place;
                    var shelfLife = item.shelf_life==''?"--":item.shelf_life;
            			newData[newData.length] = [
            			                           id,
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
            			                           "<input type='radio' value='"+id+"' name='staff'>"];
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-8'f>r>t<'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
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

//确定
$("#gbmSubmit").click(function(){
	var wmsId = $('input[name="staff"]:checked').val();
	if(wmsId == undefined){
		alert("请先选择商品");
		return;
	}
	$.post("/wms/goods/get?wms_goods_id="+wmsId, function(ret){
		
		if(ret.code != 0){
			alert(ret.msg);
		}else{
			var isExist = false;
			
			$('input[name="wms_goods_id"]').each(function(){
				if($(this).val() == ret.data.wms_goods_id){
					alert("商品已经被添加，请选择其它商品！");
					isExist = true;
					return false;
				}
			});
			
			if(!isExist){
				//添加到table
				$('#gbmEdit').modal('hide');
				var s = "<tr><td>"+ret.data.wms_goods_name+"</td><td>"+ret.data.wms_goods_gbm+"</td><td><input name='"+ret.data.wms_goods_id+"num' value='1'/><input type='hidden' name='wms_goods_id' value='"+ret.data.wms_goods_id+"'/></td><td><a href='javascript:void(0)' onclick='removeTr(this);'>删除</a></td></tr>";
				$('#gbmTable').append(s);
				$('#gbmTable').show();
				$("#unit").val(ret.data.unit);
				$("#saleSpec").val(ret.data.sale_spec);
				$("#originPlace").val(ret.data.origin_place);
				$("#packageSpec").val(ret.data.package_spec);
				$("#brand").val(ret.data.brand);
				$("#shelfLife").val(ret.data.shelf_life);
			}	
		}		
	},"json");
	
});

function removeTr(obj){
	var tr=$(obj).parent().parent();
	var child = tr.remove();
/* 	var trNum=$('#gbmTable tr').length;
	if(trNum < 2){
		$('#gbmTable').hide();
	} */
}
</script>