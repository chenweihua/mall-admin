<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>

<style>
.goodsInfo{
	margin: 2 20px;
	background-color: #FFF5EE;
}
.hint{
	text-align: center;
}

.propertyNameValue{
	margin: 10px;
}

.propertyNameValue th{
	background-color:#FFE7BA;
	width:120px;
	border: 1px solid #ccc;
}

.propertyNameValue td{
	width:120px;
	border: 1px solid #ccc;
	text-align: left;
}

.propertyNameValue td input{
	width:110px;
}

.valueSelectTd{
	width: 200px;
}

.propertyImage{
	margin: 10px;
}

.propertyImage th{
	background-color:#FFE7BA;
	border: 1px solid #ccc;
}

.propertyImage td{
	border: 1px solid #ccc;
	text-align: left;
}

.propertyImage td input{
	width:110px;
}
</style>

<%@ include file="../header.jsp"%>
<div class="box-inner">
	<div class="box-header well" data-original-title="">
		<h2>
			<i class="glyphicon glyphicon-banner"></i>库存管理->货品编辑
		</h2>
		<div class="box-icon">
			<a href="#"	class="btn btn-minimize btn-round btn-default">
				<i class="glyphicon glyphicon-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="box-content">
		<form class="form-horizontal" id="editForm" role="form" onsubmit="return false;">
			<div class="row">
				<div class="col-md-1 hint">商品信息</div>
			</div>
			<div class="row">
				<div class="goodsInfo col-md-10 col-md-offset-1">
					<input type="hidden" id="bgGoodsId" name="bgGoodsId"
					value="${bgGoods.bgGoodsId}">
					<input type="hidden" id="storageId" name="storageId"
						value="${storageId}">
					<input type="hidden" id="categoryId" name="categoryId"
						value="${categoryId}">
					<input type="hidden" id="propertyCategoryId" name="propertyCategoryId"
						value="${propertyCategoryId}">
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">品牌名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="brand"
								name="brand" value="${bgGoods.brand}" placeholder="品牌名称">
						</div>
					</div>
					<div class="form-group">
						<label for="skuName" class="col-sm-2 control-label">商品名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="bgGoodsName"
								name="bgGoodsName" value="${bgGoods.bgGoodsName}"
								placeholder="商品名称">
						</div>
					</div>
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">商品描述</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="description"
								name="description" value="${bgGoods.description}"
								placeholder="商品描述">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">单位</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="unit"
								name="unit" value="${bgGoods.unit}" placeholder="商品单位">
						</div>
					</div>
		
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">销售规格</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="saleSpec"
								name="saleSpec" value="${bgGoods.saleSpec}" placeholder="销售规格">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">原产地</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="originPlace"
								name="originPlace" value="${bgGoods.originPlace}"
								placeholder="原产地">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">产品包装</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="packageSpec"
								name="packageSpec" value="${bgGoods.packageSpec}"
								placeholder="产品包装">
						</div>
					</div>
					<div class="form-group">
						<label for="subName" class="col-sm-2 control-label">保质期</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="shelfLife"
								name="shelfLife" value="${bgGoods.shelfLife}" placeholder="保质期">
						</div>
					</div>
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">商品备注</label>
						<div class="col-sm-3">
							<input type="text" class="form-control request" id="remark"
								name="remark" value="${bgGoods.remark}" placeholder="商品备注">
						</div>
					</div>
					
					<br>
					<div class="form-group">
						<label for="imageUrl1" class="col-sm-2 control-label">商品图片</label>
						<div class="col-sm-6">
							<input id="uploadImage1" type="file" name="p1"
								onchange="PreviewMutiImage('uploadImage1','uploadPreview','imageUrl',5);" />
							<span id="uploadPreview1"> <img src="${bgGoods.imageUrl}"
								style="width: 200px" alt="商品图片" /> <c:if
									test="${not empty bgGoods.imageUrl}">
									<a href="javascript:void(0);"
										onclick="removeElement('uploadPreview1');">删除</a>
								</c:if> <input type="hidden" id="imageUrl1" name="imageUrl"
								class="request" value="${bgGoods.imageUrl}" placeholder="商品图片">
							</span><br />
							<c:forEach var="item" items="${commerceImagelist}"
								varStatus="status">
								<span id="uploadPreview${status.count+1}"> <img
									src="${item.imageUrl}" style="width: 200px" alt="商品大图" /> <a
									href="javascript:void(0);"
									onclick="removeElement('uploadPreview${status.count+1}');">删除</a><br />
									<input type="hidden" name="imageUrl" value="${item.imageUrl}">
								</span>
							</c:forEach>
						</div>
					</div>
					
					<div class="form-group">
						<label for="imageUrl2" class="col-sm-2 control-label">商品详情</label>
						<div class="col-sm-6">
							<input id="uploadImage2" type="file" name="p2" onchange="PreviewMutiImage('uploadImage2','uploadDetailPreview','detailImageUrl',10);" />
							<c:if test="${empty detailImagelist}">
								<span id="uploadDetailPreview1">
									<img src="" style="width:200px" alt="商品大图"/>
									<input type="hidden" name="detailImageUrl" value="">
								</span><br />
							</c:if>
							<c:forEach var="item" items="${detailImagelist}" varStatus="status">
								<span id="uploadDetailPreview${status.count}">
									<img src="${item.imageUrl}" style="width:200px" alt="商品大图"/>
									<a href="javascript:void(0);" onclick="removeElement('uploadDetailPreview${status.count}');">删除</a><br />
									<input type="hidden" id="imageUrl2" name="detailImageUrl" value="${item.imageUrl}">
								</span>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-1 hint">商品规格</div>
			</div>
			<div class="row">
				<div class="goodsInfo col-md-10 col-md-offset-1">
					<c:choose>
						<c:when test="${propertyNameList.size() == 0}">
							已选择的类别下没有属性
						</c:when>
						<c:when test="${propertyNameList.size() > 0}">
							<c:forEach var="propertyName" items="${propertyNameList}" varStatus="status">
								<div class="row">
									<div class="col-md-1 hint" id="${propertyName.propertyNameId}">${propertyName.propertyName}</div>
								</div>
								
								<div class="row">
									<div class="col-md-10 col-md-offset-1">
										<c:choose>
											<c:when test="${propertyName.propertyVauleList.size() == 0}">
												该属性没有值
											</c:when>
											<c:when test="${propertyName.propertyVauleList.size() > 0}">
												<table>
													<c:forEach var="propertyValueIndex" items="${propertyName.propertyVauleListArray}">
														<tr>
															<c:forEach var="propertyValue" items="${propertyValueIndex}">
																<td class="valueSelectTd">
																	<input id="pv${propertyValue.propertyValueId}" type="checkbox" class="propertyValueSelect"
																	 index="${propertyName.index}"
																	 needPic="${propertyName.needPic}"
																	 propertyNameId="${propertyName.propertyNameId}"
																	 propertyName="${propertyName.propertyName}"
																	 propertyValueId="${propertyValue.propertyValueId}"
																	 propertyValue="${propertyValue.propertyValue}" >
																	${propertyValue.propertyValue}
																</td>
															</c:forEach>
															<c:if test="${propertyValueIndex.size() < 5}">
																<c:forEach begin="1" end="${5-propertyValueIndex.size()}">
																	<td class="valueSelectTd"></td>
																</c:forEach>
															</c:if>
														</tr>
													</c:forEach>
												</table>
											</c:when>
										</c:choose>
									</div>
								</div>
							</c:forEach>
						</c:when>
					</c:choose>
					<div id="propertyImageContainer" class="row">
					</div>
					<div id="propertyNameValueContainer" class="row">
					</div>
				</div>
			</div>
			<input id="selectedIamgeValues" type="hidden" name="selectedIamgeValues" value="">
			<input id="selectedValues" type="hidden" name="selectedValues" value="">
		</form>
		<button type="button" class="btn btn-default" id="cancel">取消</button>
		<!-- <button type="button" class="btn btn-default" id="beforeButton">上一步</button> -->
		<button type="button" class="btn btn-primary" id="save">保存</button>
	</div>
</div>

<%@ include file="../footer.jsp"%>

<script type="text/javascript">
var storageId = ${storageId};
var tableData = {};
//动态生成的属性td
var tableTdArray = [];
var imageTableTdArray = [];
//动态生成属性Id，已":"隔开
var valueIdArray = [];
var imageValueIdArray = [];
//用于存储编辑时属性值
var initProperty = {};
var initImageProperty = {};

$(document).ready(function(){
	$.post('/bgGoods/third/getBgGoodsProperty',{"bgGoodsId":$('#bgGoodsId').val()},function(ret){
		if(ret.code == 0){
			var propertyBgSkuMap = ret.data.propertyBgSkuMap;
			var selectedProperty = ret.data.selectedProperty;
			var propertyImageMap = ret.data.propertyImageMap;
			//初始规格数据
			for(var key in propertyBgSkuMap){
				var item = propertyBgSkuMap[key];
				var valueObject = {"bgSkuId":item.bgSkuId,"price":item.originPrice/100,"code":item.wmsGoodsGbms,"stock":item.stock};
				initProperty[key] = valueObject;
			}
			//初始化图片
			for(var key in propertyImageMap){
				initImageProperty[key] = propertyImageMap[key];
			}
			//赋值规格
			for(var i=0,len=selectedProperty.length;i<len;i++){
				$('#pv'+selectedProperty[i].propertyValueId+'').prop('checked','checked');
				addItem2TableData($('#pv'+selectedProperty[i].propertyValueId+''));
			}
			processImageTable();
			processTable();
		}else{
			alert(ret.msg);
		}
	},'json');
	
});

$('#save').click(function(){
	$.post('/bgGoods/third/edit',$('#editForm').serialize(),function(json){
		if(json.code == 0){
			$('#bgGoodsId').val(json.data);
			alert(json.msg);
			window.location.href = "/bgGoods/third/list";
		}else{
			alert(json.msg);
		}
	});
});

$('#beforeButton').click(function(){
	window.location.href = "/bgGoods/third/propertySelect?storageId="+storageId+"&bgGoodsId="+$('#bgGoodsId').val();
});

$('#cancel').click(function(){
	window.location.href = "/bgGoods/third/list";
});

//初始化数据表
function addItem2TableData(o){
	var index = o.attr('index');
	var needPic = o.attr('needPic');
	var propertyNameId = o.attr('propertyNameId');
	var propertyName = o.attr('propertyName');
	var propertyValueId = o.attr('propertyValueId');
	var propertyValue = o.attr('propertyValue');
	var valueObject = {"id":propertyValueId,"value":propertyValue};
	if(tableData[index] == null){
		var nameObject = {"id":propertyNameId,"name":propertyName,"needPic":needPic,"values":{}};
		nameObject.values[propertyValueId] = valueObject;
		tableData[index] = nameObject;
	}else{
		//判断值是否存在
		if(tableData[index].values[propertyValueId] == null){
			tableData[index].values[propertyValueId] = valueObject;
		}
	}
};

//构建属性表数据
$('.propertyValueSelect').click(function(){
	var index = $(this).attr('index');
	var needPic = $(this).attr('needPic');
	var propertyNameId = $(this).attr('propertyNameId');
	var propertyName = $(this).attr('propertyName');
	var propertyValueId = $(this).attr('propertyValueId');
	var propertyValue = $(this).attr('propertyValue');
	if(this.checked){
		var valueObject = {"id":propertyValueId,"value":propertyValue};
		if(tableData[index] == null){
			var nameObject = {"id":propertyNameId,"name":propertyName,"needPic":needPic,"values":{}};
			nameObject.values[propertyValueId] = valueObject;
			tableData[index] = nameObject;
		}else{
			//判断值是否存在
			if(tableData[index].values[propertyValueId] == null){
				tableData[index].values[propertyValueId] = valueObject;
			}
		}
	}else{
		if(tableData[index] != null){
			if(tableData[index].values[propertyValueId] != null){
				//移除
				delete tableData[index].values[propertyValueId];
			}
			//判断values是否为空
			if(isEmptyObject(tableData[index].values)){
				//空,删除规格
				delete tableData[index];
			}
		}
	}
	processImageTable();
	processTable();
});

//利用数据绘制规格属性表
function processTable(){
	var nameValueTable = ['<table id="" class="propertyNameValue"><tr>'];
	tableTdArray = [];
	valueIdArray = [];
	if(isEmptyObject(tableData)){
		$('#selectedValues').val('');
		$('#propertyNameValueContainer').empty();
		return;
	}
	for(var index in tableData){
		//组合数据
		var tableTdItems = [];
		var valueIdItems = [];
		for(var valueId in tableData[index].values){
			tableTdItems.push('<td>'+tableData[index].values[valueId].value+'</td>');
			valueIdItems.push(tableData[index].values[valueId].id);
		}
		tableTdArray.push(tableTdItems);
		valueIdArray.push(valueIdItems);
		//生成表头
		nameValueTable.push('<th>'+tableData[index].name+'</th>');
	}
	nameValueTable.push('<th>价格</th><th>数量</th><th>商品条码</th></tr>');
	//每一行
	var tableTdArrayTemp = combine(tableTdArray,"");
	var valueIdArraytemp = combine(valueIdArray,":");
	for(var i=0,len=tableTdArrayTemp.length; i<len; i++){
		var initValue = initProperty[valueIdArraytemp[i]];
		if(initValue != null){
			tableTdArrayTemp[i] = '<tr>'+ tableTdArrayTemp[i] 
			+ '<td><input type="text" name="price'+valueIdArraytemp[i]+'" value="'+initValue.price+'"></td>'
			+ '<td><input type="text" name="stock'+valueIdArraytemp[i]+'" value="'+initValue.stock+'"></td>' 
			+ '<td><input type="text" name="code'+valueIdArraytemp[i]+'" value="'+initValue.code+'">'
			+ '<input type="hidden" name="bgSkuId'+valueIdArraytemp[i]+'" value="'+initValue.bgSkuId+'"></td></tr>';
		}else{
			tableTdArrayTemp[i] = '<tr>'+ tableTdArrayTemp[i] 
			+ '<td><input type="text" name="price'+valueIdArraytemp[i]+'" value=""></td>'
			+ '<td><input type="text" name="stock'+valueIdArraytemp[i]+'" value=""></td>' 
			+ '<td><input type="text" name="code'+valueIdArraytemp[i]+'" value="">'
			+ '<input type="hidden" name="bgSkuId'+valueIdArraytemp[i]+'" value="-1"></td></tr>';
		}
	}
	nameValueTable = nameValueTable.concat(tableTdArrayTemp);
	nameValueTable.push('</table>');
	$('#selectedValues').val(valueIdArraytemp.join(','));
	$('#propertyNameValueContainer').empty();
	$('#propertyNameValueContainer').append(nameValueTable.join(' '));
}

//利用数据绘制图像表
function processImageTable(){
	var imageTable = ['<table id="propertyImage" class="propertyImage"><tr>'];
	imageTableTdArray = [];
	imageValueIdArray = [];
	var hasData = false;
	for(var index in tableData){
		if(tableData[index].needPic == 1){
			hasData = true;
		}
	}
	if(!hasData){
		$('#selectedImageValues').val('');
		$('#propertyImageContainer').empty();
		return;
	}
	for(var index in tableData){
		if(tableData[index].needPic == 1){
			//组合数据
			var tableTdItems = [];
			var valueIdItems = [];
			for(var valueId in tableData[index].values){
				tableTdItems.push('<td>'+tableData[index].values[valueId].value+'</td>');
				valueIdItems.push(tableData[index].values[valueId].id);
			}
			imageTableTdArray.push(tableTdItems);
			imageValueIdArray.push(valueIdItems);
			//生成表头
			imageTable.push('<th>'+tableData[index].name+'</th>');
		}
	}
	imageTable.push('<th>图片</th></tr>');
	//每一行
	var imageTableTdArrayTemp = combine(imageTableTdArray,"");
	var imageValueIdArraytemp = combine(imageValueIdArray,":");
	for(var i=0,len=imageTableTdArrayTemp.length; i<len; i++){
		var num = i + 2;
		var initValue = initImageProperty[imageValueIdArraytemp[i]];
		if(initValue != null){
			imageTableTdArrayTemp[i] = '<tr>'+ imageTableTdArrayTemp[i] 
			+ '<td><img id="uploadPreview'+num+'" src="'+initValue+'" style="width:150px;height:100px" alt="图片"/>'
			+ '<input type="hidden" id="imageUrl'+num+'" class="request" placeholder="属性图片" name="image'+imageValueIdArraytemp[i]+'" value="'+initValue+'"><input id="uploadImage'+num+'" type="file" name="p1" onchange="PreviewImage('+num+');"></td></tr>';
		}else{
			imageTableTdArrayTemp[i] = '<tr>'+ imageTableTdArrayTemp[i] 
			+ '<td><img id="uploadPreview'+num+'" src="" style="width:150px;height:100px" alt="图片"/>'
			+ '<input type="hidden" id="imageUrl'+num+'" class="request" placeholder="属性图片" name="image'+imageValueIdArraytemp[i]+'" value=""><input id="uploadImage'+num+'" type="file" name="p1" onchange="PreviewImage('+num+');"></td></tr>';
		}
	}
	imageTable = imageTable.concat(imageTableTdArrayTemp);
	imageTable.push('</table>');
	$('#selectedIamgeValues').val(imageValueIdArraytemp.join(','));
	$('#propertyImageContainer').empty();
	$('#propertyImageContainer').append(imageTable.join(' '));
}

//多个数据集合，每个集合取一个值组合,已sep分隔
function combine(arr,sep){
    if(arr.length > 1){
        var len1=arr[0].length, len2=arr[1].length, newArr=arr.slice(0), temp=[];
        for(var i=0;i<len1;i++){
            for(var j=0;j<len2;j++){
                temp.push(arr[0][i]+sep+arr[1][j])
            }
        }
        newArr.splice(0,2,temp);
        return combine(newArr,sep);
    }
    return arr[0];
}


function checkParam(){
	for(var i=0;i<$(".request").length;i++){
		if($.trim($(".request").eq(i).val()).length==0){
			alert($(".request").eq(i).attr('placeholder')+"是必填项");
			return false;
		}
	}
	return true;
}

function isEmptyObject(obj) { 
	for ( var name in obj ) { 
		return false; 
	} 
	return true; 
} 
</script>