<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
  .modal-dialog {
  width: 900px;
  margin: 30px auto;
}
</style>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css"
	type="text/css">

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
	
<div class="modal fade" id="skuEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">选择单品(SKU)</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<table id="goodsTable"
					class="table table-striped table-bordered bootstrap-datatable datatable">
					<thead>
						<tr>
							<th>商品名称 </th>
							<th>商品条码</th>
							<th>商品描述</th>
							<th>商品备注</th>
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
				<button type="button" class="btn btn-primary" id="skuSubmit">提交</button>
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
        	url: "/goods/extend/bgGoodsDtos/get",
        	dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			var id = item.bgGoods.bgGoodsId ==''? 0 : item.bgGoods.bgGoodsId;
                    var name = item.bgGoods.bgGoodsName==''?"--":item.bgGoods.bgGoodsName;
                    var wmsGoodsGbm =  item.wmsGoodsGbms==''?"--":item.wmsGoodsGbms;
                    var description =  item.bgGoods.description==''?"--":item.bgGoods.description;
                    var remark =  item.bgGoods.remark==''?"--":item.bgGoods.remark;
            		newData[newData.length] = [
           			                           name,
           			                           wmsGoodsGbm,
           			                           description,
           			                           remark,
           			                           "<input type='radio' value='"+id+"' name='staff'>"];
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'f>r>t<'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "请输入SKU名称或条码",
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
$("#skuSubmit").click(function(){
	var bgGoodsId = $('input[name="staff"]:checked').val();
	if(bgGoodsId == undefined){
		alert("请先选择SKU");
		return;
	}
	$.post("/goods/extend/bgGoodsDto/get?bgGoodsId="+bgGoodsId, function(ret){
		
		if(ret.code != 0){
			alert(ret.msg);
		}else{
			var bgGoodsDto = ret.data;
			var isExist = false;
			
			$('input[name="bg_sku_id"]').each(function(){
				if($(this).val() == bgGoodsDto.bgSkuId){
					alert("sku已经被添加，请选择其它商品！");
					isExist = true;
					return false;
				}
			});
			
			if(!isExist){
				//添加到table
				$('#skuEdit').modal('hide');
				var index = $('#skuTable tr').length;
				var s = '<tr><td>'+index+'</td><td><a class="blueletter" href="javascript:void(0)" onclick="modifyBgGoods('+bgGoodsDto.bgGoods.bgGoodsName+')">'+bgGoodsDto.bgGoods.bgGoodsName+'</a></td><td>'+bgGoodsDto.wmsGoodsGbms+'</td><td>'+bgGoodsDto.bgGoods.remark+'<input type="hidden" id="bg_sku_id'+bgGoodsDto.bgSkuId+'" name="bg_sku_id" value="'+bgGoodsDto.bgSkuId+'"/></td><td><a href="javascript:void(0)" onclick="removeTr(this);">删除</a></td></tr>';
				$('#skuTable').append(s);
				$('#skuTable').show();
			}	
		}		
	},"json");
	
});

function removeTr(obj){
	var tr=$(obj).parent().parent();
	var child = tr.remove();
/* 	var trNum=$('#skuTable tr').length;
	if(trNum < 2){
		$('#skuTable').hide();
	} */
}
</script>