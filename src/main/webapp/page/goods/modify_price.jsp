<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
<div class="modal fade" id="priceEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog" style="width: 300px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">价格修改</h4>
			</div>
			<div class="modal-body">
				 <form class="form-horizontal" id="modifyPriceForm" role="form" onsubmit="return false;">
					<label id="targetFlag" style="display:none"></label>
					<input type="hidden" id="bgGoodsId1" name="bgGoodsId" value="">
					<input type="hidden" id="regionId" name="regionId" value="">
					<input type="hidden" id="regionType" name="regionType" value="">
					<div class="form-group">
				    	<label for="skuName" class="col-sm-5 control-label">原价</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="originPrice" name="originPrice" value="" placeholder="原价">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-5 control-label">H5售价</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="wapPrice" name="wapPrice" value="" placeholder="H5售价">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-5 control-label">App售价</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="appPrice" name="appPrice" value="" placeholder="App售价">
				        </div>
					</div>
					
					<div class="form-group">
				    	<label for="skuName" class="col-sm-5 control-label">最大购买数</label>
				        <div class="col-sm-7">
				        	<input type="text" class="form-control" id="maxNum" name="maxNum" value="" placeholder="最大购买数">
				        </div>
					</div>
				 </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="priceSubmit">提交</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">

function editRdcStoragePrice(storageId){
	
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(storageId);
	$('#regionType').val(1);
	$('#originPrice').val($('#originPrice_rdc_storage'+storageId).text());
	$('#wapPrice').val($('#wapPrice_rdc_storage'+storageId).text());
	$('#appPrice').val($('#appPrice_rdc_storage'+storageId).text());
	$('#maxNum').val($('#maxNum_rdc_storage'+storageId).text());
	$('#targetFlag').text('_rdc_storage');
	
	$('#priceEdit').modal('show');
}
function editVmStoragePrice(storageId){
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(storageId);
	$('#regionType').val(5);
	$('#originPrice').val($('#originPrice_vm_storage'+storageId).text());
	$('#wapPrice').val($('#wapPrice_vm_storage'+storageId).text());
	$('#appPrice').val($('#appPrice_vm_storage'+storageId).text());
	$('#maxNum').val($('#maxNum_vm_storage'+storageId).text());
	$('#targetFlag').text('_vm_storage');
	
	$('#priceEdit').modal('show');
}

function editLdcCityPrice(cityId){
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(cityId);
	$('#regionType').val(2);
	$('#originPrice').val($('#originPrice_ldc_city'+cityId).text());
	$('#wapPrice').val($('#wapPrice_ldc_city'+cityId).text());
	$('#appPrice').val($('#appPrice_ldc_city'+cityId).text());
	$('#maxNum').val($('#maxNum_ldc_city'+cityId).text());
	$('#targetFlag').text('_ldc_city');
	
	$('#priceEdit').modal('show');
}

function editLdcStoragePrice(storageId){
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(storageId);
	$('#regionType').val(4);
	$('#originPrice').val($('#originPrice_ldc_storage'+storageId).text());
	$('#wapPrice').val($('#wapPrice_ldc_storage'+storageId).text());
	$('#appPrice').val($('#appPrice_ldc_storage'+storageId).text());
	$('#maxNum').val($('#maxNum_ldc_storage'+storageId).text());
	$('#targetFlag').text('_ldc_storage');
	
	$('#priceEdit').modal('show');
}

function editLdcCollegePrice(collegeId){
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(collegeId);
	$('#regionType').val(3);
	$('#originPrice').val($('#originPrice_ldc_college'+collegeId).text());
	$('#wapPrice').val($('#wapPrice_ldc_college'+collegeId).text());
	$('#appPrice').val($('#appPrice_ldc_college'+collegeId).text());
	$('#maxNum').val($('#maxNum_ldc_college'+collegeId).text());
	$('#targetFlag').text('_ldc_college');
	
	$('#priceEdit').modal('show');
}

function editRdcCollegePrice(collegeId){
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(collegeId);
	$('#regionType').val(3);
	$('#originPrice').val($('#originPrice_rdc_college'+collegeId).text());
	$('#wapPrice').val($('#wapPrice_rdc_college'+collegeId).text());
	$('#appPrice').val($('#appPrice_rdc_college'+collegeId).text());
	$('#maxNum').val($('#maxNum_rdc_college'+collegeId).text());
	$('#targetFlag').text('_rdc_college');
	
	$('#priceEdit').modal('show');
}

function editVmCollegePrice(collegeId){
	$('#bgGoodsId1').val($('#bgGoodsId').val());
	$('#regionId').val(collegeId);
	$('#regionType').val(3);
	$('#originPrice').val($('#originPrice_vm_college'+collegeId).text());
	$('#wapPrice').val($('#wapPrice_vm_college'+collegeId).text());
	$('#appPrice').val($('#appPrice_vm_college'+collegeId).text());
	$('#maxNum').val($('#maxNum_vm_college'+collegeId).text());
	$('#targetFlag').text('_vm_college');
	
	$('#priceEdit').modal('show');
}

$('#priceSubmit').click(function(){
	$.post("/goods/price/editPrice", $("#modifyPriceForm").serialize(), function(json){
		if (json.code != 0) {
			alert(json.msg);
		} else {
			alert(json.msg);
			var targetFlag = $('#targetFlag').text();
			var regionId = $('#regionId').val();
			$('#originPrice'+targetFlag+regionId).text($('#originPrice').val());
			$('#wapPrice'+targetFlag+regionId).text($('#wapPrice').val());
			$('#appPrice'+targetFlag+regionId).text($('#appPrice').val());
			$('#maxNum'+targetFlag+regionId).text($('#maxNum').val());
			
			$('#priceEdit').modal('hide');
		}
	});
});

</script>