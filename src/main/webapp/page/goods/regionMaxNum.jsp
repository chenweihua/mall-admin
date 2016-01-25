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

		<table id="rdcPrice"
			class="table table-striped table-bordered bootstrap-datatable datatable">
			<thead>
				<tr>
					<th></th>
					<th>RDC仓</th>
					<th>最大购买数</th>
					<th>操作</th>
				</tr>
			</thead>
		</table>
		
		<br>
		
		<table id="ldcPrice"
			class="table table-striped table-bordered bootstrap-datatable datatable">
			<thead>
				<tr>
					<th></th>
					<th>LDC城市</th>
					<th>最大购买数</th>
					<th>操作</th>
				</tr>
			</thead>
		</table>
		
		<table id="vmPrice"
			class="table table-striped table-bordered bootstrap-datatable datatable">
			<thead>
				<tr>
					<th></th>
					<th>虚拟仓</th>
					<th>最大购买数</th>
					<th>操作</th>
				</tr>
			</thead>
		</table>
<%@ include file="modify_maxNum.jsp" %>

<script type="text/javascript">
var rdcTable = null;
var ldcTable = null;
var vmTable = null;

var tableMap = {};

$(document).ready(function() {	
	    rdcTable = $('#rdcPrice').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: "/goods/price/getRdcStorage4Price/byBgGoodsId?bgGoodsId=null",
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
	    
	    vmTable = $('#vmPrice').DataTable({
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
	    
	    ldcTable = $('#ldcPrice').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: "/goods/price/getLdcCity4Price/byBgGoodsId?bgGoodsId=null",
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
    $('#rdcPrice tbody').on('click', '.details-control', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
        var tr = $(this).closest('tr');
        var row = rdcTable.row( tr );

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
                		var collegeDto = null;
                		var goodsId = 0;
                		for (var i=0, len = json.data.length; i<len; i++) {
        					collegeDto = json.data[i];
        					if(goodsId != null){
        						goodsId = collegeDto.goodsId;
        						newData[newData.length] = [
        						                           collegeDto.college.collegeName,
        						                           '<label id="maxNum_rdc_college'+goodsId+'">'+collegeDto.maxNum+'</label>',
        						                           '<a href="javascript:editRdcCollegePrice('+goodsId+');">修改</a>'];
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
    $('#vmPrice tbody').on('click', '.details-control', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
        var tr = $(this).closest('tr');
        var row = vmTable.row( tr );

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
                		var goodsId = 0;
                		for (var i=0, len = json.data.length; i<len; i++) {
        					collegeDto = json.data[i];
        					if(collegeDto != null){
        						goodsId = collegeDto.goodsId;
        						newData[newData.length] = [
        						                           collegeDto.college.collegeName,
        						                           '<label id="maxNum_vm_college'+goodsId+'">'+collegeDto.maxNum+'</label>',
        						                           '<a href="javascript:editVmCollegePrice('+goodsId+');">修改</a>'];
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
    $('#ldcPrice tbody').on('click', '.details-control', function () {
    	var bgGoodsId = $('#bgGoodsId').val();
        var tr = $(this).closest('tr');
        var row = ldcTable.row( tr );

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
    $('#ldcPrice tbody').on('click','.details-control-3', function () {
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
        						skuId = collegeDto.goodsId;
        						newData[newData.length] = [
        						                           collegeDto.college.collegeName,
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
            '<th>最大购买数</th>'+
            '<th>操作</th>'+
        '</tr></thead>'+
    '</table>';
}

</script>