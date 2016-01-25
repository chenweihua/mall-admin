<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<style>
.details-control {
	display:block;
	
    background: url('../../img/details_open.png') no-repeat center center;
    cursor: pointer;
}
tr.shown .details-control {
    background: url('../../img/details_close.png') no-repeat center center;
}
.details-control-3 {
	display:block;
	
    background: url('../../img/details_open.png') no-repeat center center;
    cursor: pointer;
}
tr.shown .details-control-3 {
    background: url('../../img/details_close.png') no-repeat center center;
}
</style>
<div class="modal fade" id="priceEditPop" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog" style="width: 950px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">价格管理</h4>
			</div>
			<div class="modal-body" style="height: 500px;overflow-y: auto;">
				<input type="hidden" id="bgGoodsId4tree">
				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<input type="hidden" id="bgGoodsId">
							
							<table id="batchPricePop"
								class="table table-striped table-bordered bootstrap-datatable datatable">
								<thead>
									<tr>
										<th  width="10%">全国仓</th>
										<th width="20%">原价</th>
										<th width="20%">H5售价</th>
										<th width="20%">APP售价</th>
										<th width="20%">最大购买数</th>
										<th width="10%">操作</th>
									</tr>
									<tr>
										<td>全国仓</td>
										<td><input type="text" id="batchOriginPrice" size="8"/></td>
										<td><input type="text" id="batchWapPrice" size="8"/></td>
										<td><input type="text" id="batchAppPrice" size="8"/></td>
										<td><input type="text" id="batchMaxNum" size="8"/></td>
										<td><input type="button" id="batchSet" value="修改"/></td>
									</tr>
								</thead>
							</table>
							<br>
							<table id="rdcPricePop"
								class="table table-striped table-bordered bootstrap-datatable datatable">
								<thead>
									<tr>
										<th></th>
										<th>RDC仓</th>
										<th>原价</th>
										<th>H5售价</th>
										<th>APP售价</th>
										<th>最大购买数</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
							
							<br>
							
							<table id="ldcPricePop"
								class="table table-striped table-bordered bootstrap-datatable datatable">
								<thead>
									<tr>
										<th></th>
										<th>LDC城市</th>
										<th>原价</th>
										<th>H5售价</th>
										<th>APP售价</th>
										<th>最大购买数</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
							<br>
		
							<table id="vmPricePop"
								class="table table-striped table-bordered bootstrap-datatable datatable">
								<thead>
									<tr>
										<th></th>
										<th>虚拟仓</th>
										<th>原价</th>
										<th>H5售价</th>
										<th>APP售价</th>
										<th>最大购买数</th>
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
			</div>
		</div>
	</div>
</div>
		
<%@ include file="modify_price.jsp" %>

<script type="text/javascript">
var rdcTablePop = null;
var ldcTablePop = null;
var vmTablePop = null;
var tableMap = {};


$(document).ready(function() {	
	    rdcTablePop = $('#rdcPricePop').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: "/goods/price/getRdcStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId,
        	dataSrc: function ( json ) {
        		var newData = [];
        		var storageDto = null;
        		var storageId = 0;
        		for (var i=0, len = json.data.length; i<len; i++) {
					storageDto = json.data[i];
					if(storageDto != null){
						storageId = storageDto.storage.storageId;
						newData[newData.length] = [
						                           '<label id="rdc_storage_'+storageId+'" class="details-control">&nbsp;</label>',
													storageDto.storage.storageName,
						                           '<label id="originPrice_rdc_storage'+storageId+'">'+storageDto.originPrice/100+'</label>',
						                           '<label id="wapPrice_rdc_storage'+storageId+'">'+storageDto.wapPrice/100+'</label>',
						                           '<label id="appPrice_rdc_storage'+storageId+'">'+storageDto.appPrice/100+'</label>',
						                           '<label id="maxNum_rdc_storage'+storageId+'">'+storageDto.maxNum+'</label>',
						                           '<a href="javascript:editRdcStoragePrice('+storageDto.storage.storageId+');">修改</a>'];
					}
        		}
        		json.data = newData;
        		return newData;
        	}
        },
    	sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p> ",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "按名称或条码搜索 _INPUT_",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...", 
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页",
            }
        }
    });
	    
	    vmTablePop = $('#vmPricePop').DataTable({
			ordering: false,
	        processing: true,
	        serverSide: true,
	        searching: false,
	        ajax: {
	        	url: "/goods/price/getVmStorage4Price/byBgGoodsId?bgGoodsId=null",
	        	dataSrc: function ( json ) {
	        		var newData = [];
	        		var storageDto = null;
	        		var storageId = 0;
	        		for (var i=0, len = json.data.length; i<len; i++) {
						storageDto = json.data[i];
						if(storageDto != null){
							storageId = storageDto.storage.storageId;
							newData[newData.length] = [
							                           '<label id="vm_storage_'+storageId+'" class="details-control">&nbsp;</label>',
														storageDto.storage.storageName,
							                           '<label id="originPrice_vm_storage'+storageId+'">'+storageDto.originPrice/100+'</label>',
							                           '<label id="wapPrice_vm_storage'+storageId+'">'+storageDto.wapPrice/100+'</label>',
							                           '<label id="appPrice_vm_storage'+storageId+'">'+storageDto.appPrice/100+'</label>',
							                           '<label id="maxNum_vm_storage'+storageId+'">'+storageDto.maxNum+'</label>',
							                           '<a href="javascript:editVmStoragePrice('+storageDto.storage.storageId+');">修改</a>'];
						}
	        		}
	        		json.data = newData;
	        		return newData;
	        	}
	        },
	    	sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p> ",
	        sPaginationType: "bootstrap",
	        oLanguage: {
	            sLengthMenu: "_MENU_ 记录每页",
	            sSearch: "按名称或条码搜索 _INPUT_",
	            sZeroRecords: "暂无数据",
	            sProcessing: "正在处理...", 
	            sEmptyTable : "暂无数据",
	            sInfo : "_START_ - _END_ (共_TOTAL_条)",
	            sInfoFiltered: "",
	            oPaginate: {
	                sFirst: "第一页",
	                sLast: "最后一页",
	                sNext : "下一页",
	                sPrevious : "前一页",
	            }
	        }
	    });
	    
	    ldcTablePop = $('#ldcPricePop').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: "/goods/price/getLdcCity4Price/byBgGoodsId?bgGoodsId="+bgGoodsId,
        	dataSrc: function ( json ) {
        		var newData = [];
        		var cityDto = null;
        		var cityId = 0;
        		for (var i=0, len = json.data.length; i<len; i++) {
					cityDto = json.data[i];
					if(cityDto != null){
						cityId = cityDto.city.cityId;
						newData[newData.length] = [
						                           '<label id="ldc_city_'+cityId+'" class="details-control">&nbsp;</label>',
						                           cityDto.city.cityName,
						                           '<label id="originPrice_ldc_city'+cityId+'">'+cityDto.originPrice/100+'</label>',
						                           '<label id="wapPrice_ldc_city'+cityId+'">'+cityDto.wapPrice/100+'</label>',
						                           '<label id="appPrice_ldc_city'+cityId+'">'+cityDto.appPrice/100+'</label>',
						                           '<label id="maxNum_ldc_city'+cityId+'">'+cityDto.maxNum+'</label>',
						                           '<a href="javascript:editLdcCityPrice('+cityId+');">修改</a>'];
					}
        		}
        		json.data = newData;
        		return newData;
        	}
        },
		sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "按名称或条码搜索 _INPUT_",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...", 
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页",
            }
        }
    });
	    
	    
  //rdc仓展开 
    $('#rdcPricePop tbody').on('click', '.details-control', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
        var tr = $(this).closest('tr');
        var row = rdcTablePop.row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            var storageIdStr = $(this).attr('id');
            row.child(format(storageIdStr)).show();
            tr.addClass('shown');
            
            $('#'+storageIdStr+'_table').DataTable({
        		ordering: false,
                processing: true,
                serverSide: true,
                searching: false,
                ajax: {
                	url: '/goods/price/getRdcCollege4Price/byBgGoodsIdStorageId?bgGoodsId='+bgGoodsId+'&storageId='+storageIdStr.substr(12),
                	dataSrc: function ( json ) {
                		var newData = [];
                		var cityDto = null;
                		var cityId = 0;
                		for (var i=0, len = json.data.length; i<len; i++) {
        					collegeDto = json.data[i];
        					if(collegeDto != null){
        						skuId = collegeDto.skuId;
        						newData[newData.length] = [
        						                           collegeDto.college.collegeName,
        						                           '<label id="originPrice_rdc_college'+skuId+'">'+collegeDto.originPrice/100+'</label>',
        						                           '<label id="wapPrice_rdc_college'+skuId+'">'+collegeDto.wapPrice/100+'</label>',
        						                           '<label id="appPrice_rdc_college'+skuId+'">'+collegeDto.appPrice/100+'</label>',
        						                           '<label id="maxNum_rdc_college'+skuId+'">'+collegeDto.maxNum+'</label>',
        						                           '<a href="javascript:editRdcCollegePrice('+skuId+');">修改</a>'];
        					}
                		}
                		json.data = newData;
                		return newData;
                	}
                },
        		sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
                sPaginationType: "bootstrap",
                oLanguage: {
                    sLengthMenu: "_MENU_ 记录每页",
                    sSearch: "按名称或条码搜索 _INPUT_",
                    sZeroRecords: "暂无数据",
                    sProcessing: "正在处理...", 
                    sEmptyTable : "暂无数据",
                    sInfo : "_START_ - _END_ (共_TOTAL_条)",
                    sInfoFiltered: "",
                    oPaginate: {
                        sFirst: "第一页",
                        sLast: "最后一页",
                        sNext : "下一页",
                        sPrevious : "前一页",
                    }
                }
            });
        }
    } );    
	    
  //虚拟仓展开 
    $('#vmPricePop tbody').on('click', '.details-control', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
        var tr = $(this).closest('tr');
        var row = vmTablePop.row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            var storageIdStr = $(this).attr('id');
            row.child(formatVM(storageIdStr)).show();
            tr.addClass('shown');
            
            $('#'+storageIdStr+'_table').DataTable({
        		ordering: false,
                processing: true,
                serverSide: true,
                searching: false,
                ajax: {
                	url: '/goods/price/getVmCollege4Price/byBgGoodsIdStorageId?bgGoodsId='+bgGoodsId+'&storageId='+storageIdStr.substr(12),
                	dataSrc: function ( json ) {
                		var newData = [];
                		var collegeDto = null;
                		var skuId = 0;
                		for (var i=0, len = json.data.length; i<len; i++) {
        					collegeDto = json.data[i];
        					if(collegeDto != null){
        						skuId = collegeDto.skuId;
        						newData[newData.length] = [
        						                           collegeDto.college.collegeName,
        						                           '<label id="originPrice_vm_college'+skuId+'" class="priceCheck">'+collegeDto.originPrice/100+'</label>',
        						                           '<label id="wapPrice_vm_college'+skuId+'" class="priceCheck">'+collegeDto.wapPrice/100+'</label>',
        						                           '<label id="appPrice_vm_college'+skuId+'" class="priceCheck">'+collegeDto.appPrice/100+'</label>',
        						                           '<label id="maxNum_vm_college'+skuId+'">'+collegeDto.maxNum+'</label>',
        						                           '<a href="javascript:editVmCollegePrice('+skuId+');">修改</a>'];
        					}
                		}
                		json.data = newData;
                		return newData;
                	}
                },
        		sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
                sPaginationType: "bootstrap",
                oLanguage: {
                    sLengthMenu: "_MENU_ 记录每页",
                    sSearch: "按名称或条码搜索 _INPUT_",
                    sZeroRecords: "暂无数据",
                    sProcessing: "正在处理...", 
                    sEmptyTable : "暂无数据",
                    sInfo : "_START_ - _END_ (共_TOTAL_条)",
                    sInfoFiltered: "",
                    oPaginate: {
                        sFirst: "第一页",
                        sLast: "最后一页",
                        sNext : "下一页",
                        sPrevious : "前一页",
                    }
                }
            });
        }
    } );        
	    
	    
  //ldc城市展开 
    $('#ldcPricePop tbody').on('click', '.details-control', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
        var tr = $(this).closest('tr');
        var row = ldcTablePop.row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            var cityIdStr = $(this).attr('id');
            row.child(formatLdcStorage(cityIdStr)).show();
            tr.addClass('shown');
            
            tableMap[cityIdStr+'_table'] = $('#'+cityIdStr+'_table').DataTable({
        		   ordering: false,
                processing: true,
                serverSide: true,
                searching: false,
                ajax: {
                	url: '/goods/price/getLdcStorage4Price/byBgGoodsIdCityId?bgGoodsId='+bgGoodsId+'&cityId='+cityIdStr.substr(9),
                	dataSrc: function ( json ) {
                		var newData = [];
                		var storageDto = null;
                		var storageId = 0;
                		for (var i=0, len = json.data.length; i<len; i++) {
                			storageDto = json.data[i];
        					if(storageDto != null){
        						storageId = storageDto.storage.storageId;
        						newData[newData.length] = [
        						                    '<label id="ldc_storage_'+storageId+'" cityId="'+cityIdStr+'" class="details-control-3">&nbsp;</label>',
    												storageDto.storage.storageName,
    												'<label id="originPrice_ldc_storage'+storageId+'">'+storageDto.originPrice/100+'</label>',
    												'<label id="wapPrice_ldc_storage'+storageId+'">'+storageDto.wapPrice/100+'</label>',
    												'<label id="appPrice_ldc_storage'+storageId+'">'+storageDto.appPrice/100+'</label>',
    												'<label id="maxNum_ldc_storage'+storageId+'">'+storageDto.maxNum+'</label>',
    												'<a href="javascript:editLdcStoragePrice('+storageDto.storage.storageId+');">修改</a>'];
    												}
                		}
                		json.data = newData;
                		return newData;
                	}
                },
        		sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
                sPaginationType: "bootstrap",
                oLanguage: {
                    sLengthMenu: "_MENU_ 记录每页",
                    sSearch: "按名称或条码搜索 _INPUT_",
                    sZeroRecords: "暂无数据",
                    sProcessing: "正在处理...", 
                    sEmptyTable : "暂无数据",
                    sInfo : "_START_ - _END_ (共_TOTAL_条)",
                    sInfoFiltered: "",
                    oPaginate: {
                        sFirst: "第一页",
                        sLast: "最后一页",
                        sNext : "下一页",
                        sPrevious : "前一页",
                    }
                }
            });
        }
    } );
  
  
  
    //ldc仓展开 
    $('#ldcPricePop tbody').on('click','.details-control-3', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
    	var cityIdStr = $(this).attr('cityId');
    	var storageIdStr = $(this).attr('id');
        var tr = $(this).closest('tr');
        var row = tableMap[cityIdStr+'_table'].row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            
            row.child(formatLdcCollege(storageIdStr)).show();
            tr.addClass('shown');
            
            var table = $('#'+storageIdStr+'_table').DataTable({
        		   ordering: false,
                processing: true,
                serverSide: true,
                searching: false,
                ajax: {
              //  	url: '/goods/price/getRdcCollege4Price/byBgGoodsIdStorageId?bgGoodsId="+bgGoodsId+'&storageId='+storageId,
                	url: '/goods/price/getLdcCollege4Price/byBgGoodsIdStorageId?bgGoodsId='+bgGoodsId+'&storageId='+storageIdStr.substr(12),
                	dataSrc: function ( json ) {
                		var newData = [];
                		var cityDto = null;
                		var cityId = 0;
                		for (var i=0, len = json.data.length; i<len; i++) {
        					collegeDto = json.data[i];
        					if(collegeDto != null){
        						skuId = collegeDto.skuId;
        						newData[newData.length] = [
        						                           collegeDto.college.collegeName,
        						                           '<label id="originPrice_ldc_college'+skuId+'">'+collegeDto.originPrice/100+'</label>',
        						                           '<label id="wapPrice_ldc_college'+skuId+'">'+collegeDto.wapPrice/100+'</label>',
        						                           '<label id="appPrice_ldc_college'+skuId+'">'+collegeDto.appPrice/100+'</label>',
        						                           '<label id="maxNum_ldc_college'+skuId+'">'+collegeDto.maxNum+'</label>',
        						                           '<a href="javascript:editLdcCollegePrice('+skuId+');">修改</a>'];
        					}
                		}
                		json.data = newData;
                		return newData;
                	}
                },
        		sDom: "<'row'r>    t      <'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
                sPaginationType: "bootstrap",
                oLanguage: {
                    sLengthMenu: "_MENU_ 记录每页",
                    sSearch: "按名称或条码搜索 _INPUT_",
                    sZeroRecords: "暂无数据",
                    sProcessing: "正在处理...", 
                    sEmptyTable : "暂无数据",
                    sInfo : "_START_ - _END_ (共_TOTAL_条)",
                    sInfoFiltered: "",
                    oPaginate: {
                        sFirst: "第一页",
                        sLast: "最后一页",
                        sNext : "下一页",
                        sPrevious : "前一页",
                    }
                }
            });
          
        }
    } );   
});




function format(storageIdStr) {
    // `d` is the original data object for the row
    return '<table id="'+storageIdStr+'_table" class="table table-striped table-bordered bootstrap-datatable datatable" style="padding-left:50px;">'+
        '<thead><tr>'+
            '<th>RDC学校</th>'+
            '<th>原价</th>'+
            '<th>H5售价</th>'+
            '<th>APP售价</th>'+
            '<th>最大购买数</th>'+
            '<th>操作</th>'+
        '</tr></thead>'+
    '</table>';
}
function formatVM(storageIdStr) {
    // `d` is the original data object for the row
    return '<table id="'+storageIdStr+'_table" class="table table-striped table-bordered bootstrap-datatable datatable" style="padding-left:50px;">'+
        '<thead><tr>'+
            '<th>虚拟学校</th>'+
            '<th>原价</th>'+
            '<th>H5售价</th>'+
            '<th>APP售价</th>'+
            '<th>最大购买数</th>'+
            '<th>操作</th>'+
        '</tr></thead>'+
    '</table>';
}
function formatLdcCollege(storageIdStr) {
    // `d` is the original data object for the row
    return '<table id="'+storageIdStr+'_table" class="table table-striped table-bordered bootstrap-datatable datatable" style="padding-left:50px;">'+
        '<thead><tr>'+
            '<th>LDC学校</th>'+
            '<th>原价</th>'+
            '<th>H5售价</th>'+
            '<th>APP售价</th>'+
            '<th>最大购买数</th>'+
            '<th>操作</th>'+
        '</tr></thead>'+
    '</table>';
}




function formatLdcStorage(cityIdStr) {
    // `d` is the original data object for the row
    return '<table id="'+cityIdStr+'_table" class="table table-striped table-bordered bootstrap-datatable datatable" style="padding-left:50px;">'+
        '<thead><tr>'+
            '<th></th>'+
            '<th>LDC仓</th>'+
            '<th>原价</th>'+
            '<th>H5售价</th>'+
            '<th>APP售价</th>'+
            '<th>最大购买数</th>'+
            '<th>操作</th>'+
        '</tr></thead>'+
    '</table>';
}


$("#batchSet").click(function(){
	var bgGoodsId = $("#bgGoodsId").val();
	var originPrice = $("#batchOriginPrice").val();
	var wapPrice = $("#batchWapPrice").val();
	var appPrice = $("#batchAppPrice").val();
	var maxNum = $("#batchMaxNum").val();
	$.post("/goods/price/batchEditPrice",{"bgGoodsId":bgGoodsId,"originPrice":originPrice,"wapPrice":wapPrice,"appPrice":appPrice,"maxNum":maxNum},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
			alert("设置成功~");
			rdcTablePop.ajax.reload(null, false);
			ldcTablePop.ajax.reload(null, false);
     	 }
	  },"json");
});


</script>