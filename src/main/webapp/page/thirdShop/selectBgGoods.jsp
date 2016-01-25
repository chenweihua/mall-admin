<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
  .modal-dialog {
  width: 900px;
  margin: 30px auto;
}

.skuTable{
	margin: 10px;
}

.skuTable th{
	background-color:#FFE7BA;
	width:120px;
	border: 1px solid #ccc;
}

.skuTable td{
	width:120px;
	border: 1px solid #ccc;
	text-align: left;
}

.skuTable td input{
	width:110px;
}
</style>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
	
<div class="modal fade" id="bgGoodsSelectPage" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">选择货品</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<table id="goodsTable"
								class="table table-striped table-bordered bootstrap-datatable datatable">
								<thead>
									<tr>
										<th>ID</th>
										<th>名称</th>
										<th>条码</th>
										<th>类别</th>
										<th>库存</th>
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
        	url: "/bgGoods/third/query4Select",
        	data : function(d) {
				d.storageId = storageId;
			},
        	dataSrc: function ( json ) {
        		var newData = [];
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var goodsInfo = json.data[i];
            		newData[newData.length] = [
           			        goodsInfo.bgGoodsId,
							goodsInfo.bgGoodsName,    
							goodsInfo.wmsGoodsGbm == null ? "" : goodsInfo.wmsGoodsGbm,
							goodsInfo.categoryName == null ? "" : goodsInfo.categoryName,
							goodsInfo.stock,
           			        "<input type='radio' value='"+goodsInfo.bgGoodsId+"' name='staff'>"];
        		}
        		json.data = newData;
        		return newData;
        	}
        },
        sDom: "<'row'<'col-md-6'f>r>t<'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "请输入货品名称或条码",
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
});

//确定
$("#skuSubmit").click(function(){
	var bgGoodsId = $('input[name="staff"]:checked').val();
	if(bgGoodsId == undefined){
		alert("请先选择货品");
		return;
	}
	$.post("/bgGoods/third/selectBgGoods",{"bgGoodsId":bgGoodsId}, function(ret){
		if(ret.code != 0){
			alert(ret.msg);
		}else{
			var bgGoods = ret.data.bgGoods;
			var selectedPnList = ret.data.selectedPnList;
			var bgSkuList = ret.data.bgSkuList;
			var selectedPn = {};
			
			$('#bgGoodsSelectPage').modal('hide');
			var tableData = [];
			tableData.push('<table id="bgSkuTable" class="skuTable"><tr>');
			for(var i=0,len=selectedPnList.length;i<len;i++){
				var propertyName = selectedPnList[i];
				tableData.push('<th>'+propertyName.propertyName+'</th>');
				selectedPn[i] = propertyName.propertyNameId;
			}
			tableData.push('<th>商品条码</th><th>原价</th><th>售卖价</th><th>库存</th><th>操作</th></tr>');
			for(var i=0,len1=bgSkuList.length;i<len1;i++){
				var bgSku = bgSkuList[i];
				var spMap = bgSku.skuPropertyMap;
				tableData.push('<tr>');
				for(var j=0,len2=selectedPnList.length;j<len2;j++){
					tableData.push('<td>'+spMap[selectedPn[j]].propertyValue+'</td>');
				}
				tableData.push('<td>'+bgSku.wmsGoodsGbms+'</td>');
				tableData.push('<td>'+bgSku.originPrice+'</td>');
				tableData.push('<td><input name="originPrice'+bgSku.bgSkuId+'" type="hidden" value="'+bgSku.originPrice/100+'"><input name="price'+bgSku.bgSkuId+'"></td>');
				/* tableData.push('<td><input name="maxNum'+bgSku.bgSkuId+'"></td>'); */
				tableData.push('<td><input name="bgSkuId" type="hidden" value="'+bgSku.bgSkuId+'">'+bgSku.stock+'</td>');
				tableData.push('<td><a href="javascript:void(0)" onclick="removeTr(this);">删除</a></td></tr>');
			}
			tableData.push('</table>');
			$('#bgSkuTableContainer').empty();
			$('#bgSkuTableContainer').append(tableData.join(' '));
			
			//赋值商品信息
			$('#bgGoodsId').val(bgGoods.bgGoodsId);
			$('#bgGoodsName').val(bgGoods.bgGoodsName);
			$('#brand').val(bgGoods.brand);
			$('#description').val(bgGoods.description);
			$('#unit').val(bgGoods.unit);
			$('#saleSpec').val(bgGoods.saleSpec);
			$('#originPlace').val(bgGoods.originPlace);
			$('#packageSpec').val(bgGoods.packageSpec);
			$('#shelfLife').val(bgGoods.shelfLife);
			$('#remark').val(bgGoods.remark);
		}		
	},"json");
	
});

function removeTr(obj){
	var tr=$(obj).parent().parent();
	var child = tr.remove();
 	var trNum=$('#bgSkuTable tr').length;
	if(trNum < 2){
		$('#bgSkuTableContainer').empty();
		//赋值商品信息
		$('#bgGoodsId').val('');
		$('#bgGoodsName').val('');
		$('#brand').val('');
		$('#description').val('');
		$('#unit').val('');
		$('#saleSpec').val('');
		$('#originPlace').val('');
		$('#packageSpec').val('');
		$('#shelfLife').val('');
		$('#remark').val('');
	} 
}
</script>