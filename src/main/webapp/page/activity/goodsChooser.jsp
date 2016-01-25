<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style>
<!--
.skuInfoTable,.skuInfoTable tr,.skuInfoTable tr td{
	
	border-color: grey;
    border-width: 1px;
    border-style: solid;
	
}
-->
</style>
	
<div class="modal fade" id="goodsChooserModal" tabindex="1" role="dialog" aria-labelledby="myModalLabel4" aria-hidden="true">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">选择商品</h4>
			</div>
			<div class="modal-body" style="height: 500px;overflow-y: auto;">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<table id="goodsTable"
					class="table table-striped table-bordered bootstrap-datatable datatable">
					<thead>
						<tr>
							<th width="10%">商品名称 </th>
							<th width="20%">备注</th>
							<th width="5%">类型</th>
							<th width="5%">原价</th>
							<th width="5%">活动价</th>
							<th width="5%">商品类型</th>
							<th width="20%">图片</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
				</table>
						</div>
					</div>

				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="chooseGoodsSubmit">提交</button>
			</div>
		</div>
	</div>
</div>

<script id="saleInCollegeTableTemplate" type="text/template">
		<p>商品信息：</p>
		<table border='1' id="skuinfo" class="skuInfoTable"><tr class="title"><td>商品名称</td><td>单位</td><td>描述</td><td>原价</td><td>活动价</td></tr>
       		 {{#bgSkuInfo}}
            	<tr class="skuinfo">
					<td><input type="hidden" id="skuid" value='{{bgSkuId}}'/> {{bg_goods_name}}</td>
					<td> {{unit}}</td>
					<td> {{description}}</td>
					<td><input type="text" id="originprice" value="{{origPriceDouble}}"/></td>
					<td><input type="text" id="activityprice"/></td>
				</tr>
       		{{/bgSkuInfo}}
		</table>
</script>
<script type="text/javascript" src="/js/mustache.js"></script>
<script type="text/javascript">

var table = null;
var itemList;

$(document).ready(function() {
	//datatable
	table = $("#goodsTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: true,
        ajax: {
        	url: "/activitygoods/querybggoods?activityType=8",
        	dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			
        			var goodsType = "未知";
        			var bgGoodsNameDesc = item.bgGoodsName;
					if (item.bgGoodsType == 1) {
						goodsType = "单品";
					} else if (item.bgGoodsType == 2) {
						goodsType = "组合品";
					} else if (item.bgGoodsType == 3) {
						goodsType = "聚合品";
						bgGoodsNameDesc = '<a class="blueletter" href="javascript:void(0)" onclick="showSkuInfo(this,'
							+ item.bgGoodsId+','+item.bgGoodsType
							+ ')">'
							+ item.bgGoodsName
							+ '</a>';
					}
					
					var bgGoodsNameDesc = 
        			
           			newData[newData.length] = [
           			                           bgGoodsNameDesc,
           			                           item.bgGoodsRemark,
           			                           goodsType,
           			                           '<input type="text" name="originPriceEdit" />',
           			                           '<input type="text" name="nowPriceEdit" />',
           			                           '<select name="storageTypeEdit">'
           			                           		+ '<option value=""></option>'
           			                           		+ '<option value="0">RDC商品</option>'
           			                           		+ '<option value="1" >LDC商品</option>'
					                				+ '<option value="2" >第三方商品</option>'
					                			+'</select>',
           			                           '<img src="' + item.imageUrl + '" style="height:10%;" class="imgClass" />',
           			                           "<input type='radio' value='"+item.bgGoodsId+"' name='goodsRadio'>"];
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

var tableTemplateData = {};


var tableTemplate = document.getElementById('saleInCollegeTableTemplate').innerHTML;

function fillTable(bgSkuInfo) {
	tableTemplateData.bgSkuInfo = bgSkuInfo;
	return Mustache.render(tableTemplate, tableTemplateData);
}

function fillDiv(bggoodsId, bgSkuInfo) {
	var skuInfoTable = null;
	if (bgSkuInfo == null || bgSkuInfo.length == 0) {
		skuInfoTable = "没有商品";
	} else {
		skuInfoTable = fillTable(bgSkuInfo);
	}
	var divStr = '<tr class="yellowbox padding10"><td colspan="14" align="center">'
			+ '<table>'
			+ '<tr><td></td></tr>'
			+ '<tr><td id=block' + bggoodsId + '>'
			+ skuInfoTable
			+ '</td></tr></table>';
	return divStr;
}

//弹出sku详细信息
function showSkuInfo(obj, bggoodsId,goodsType) {
	$(obj).parents("tr").find("input[name=originPriceEdit],input[name=nowPriceEdit]").attr("disabled",true);
	//$("#originPrice"+bggoodsId).attr("disabled",true);
	//$("#activityprice"+bggoodsId).attr("disabled",true);
	//$("#stock"+bggoodsId).attr("disabled",true);
	$(obj).attr("onclick", "hideSkuInfo(this," + bggoodsId +","+goodsType+ ")");
	$.post("/activitygoods/getskuinfo", {
		"bggoodsId" : bggoodsId,
		"goodsType":goodsType,
	}, function(result) {
		var saleInCollegeDiv = fillDiv(bggoodsId, result.data.bgSkuInfo);

		var nextTr = $(obj).parent().parent().next();

		var trClass = nextTr.attr("class");
		if (trClass == undefined || trClass.indexOf("yellowbox") < 0) {
			$(obj).parent().parent().after(saleInCollegeDiv);
		}
	});
}

//去掉sku详细信息
function hideSkuInfo(obj, bggoodsId,goodsType) {
	$(obj).parents("tr").find("input[name=originPriceEdit],input[name=nowPriceEdit]").attr("disabled",false);
	//$("#originPrice"+bggoodsId).attr("disabled",false);
	//$("#activityprice"+bggoodsId).attr("disabled",false);
	//$("#stock"+bggoodsId).attr("disabled",false);
	$(obj).attr("onclick", "showSkuInfo(this,'" + bggoodsId +"','"+goodsType+ "')");
	var nextTr = $(obj).parent().parent().next();
	if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
		nextTr.remove();
	}
}

//确定
$("#chooseGoodsSubmit").click(function(){
	var bgGoodsId = $('input[name="goodsRadio"]:checked').val();
	if(bgGoodsId == undefined){
		alert("请先选择商品");
		return;
	}
	//alert(bgGoodsId);
	var $selectedRow = $('input[name="goodsRadio"]:checked').parents("tr");
	var originPriceEdit = $selectedRow.find("input[name=originPriceEdit]").val();
	var nowPriceEdit = $selectedRow.find("input[name=nowPriceEdit]").val();
	var storageTypeEdit = $selectedRow.find("select[name=storageTypeEdit]").val();
	var choosedGoodsName = $selectedRow.find("td:first").text();
	var imageUrl = $selectedRow.find(".imgClass").attr("src");
	var goodsType = $selectedRow.find("td").eq(2).text();
	//alert(originPrice);
	//alert(nowPrice);
	//alert(choosedGoodsName);
	
	var hasSkus = false;
	var skuInfoList = $('#block'+bgGoodsId).find('.skuinfo');
	if(skuInfoList.length > 0){
		hasSkus = true;
	}
	
	if(!hasSkus) {
		if(originPriceEdit == '') {
			alert("原价不能为空！");
			return ;
		}
		if(!checkDouble(originPriceEdit)) {
			alert("原价必需为数字且小数位数不能多于两位");
			return;
		}
		if(nowPriceEdit == '') {
			alert("活动价不能为空！");
			return ;
		}
		if(!checkDouble(nowPriceEdit)) {
			alert("活动价必需为数字且小数位数不能多于两位");
			return;
		}
		if(originPriceEdit > 999999 || nowPriceEdit > 999999) {
			alert("价格不能大于999999");
			return;
		}
	}
	if(storageTypeEdit == '') {
		alert("商品类型不能为空！");
		return ;
	}
	
	
	//遍历商品下sku信息
	var bgGoodsInfo;
	if(!hasSkus){
		bgGoodsInfo = {'bgGoodsId':bgGoodsId,'activityPrice':nowPriceEdit,'originPrice':originPriceEdit};
	} else {
		var skuinfo = [];
		var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		for(var i=0;i<skuInfoList.length;i++){
	         var child =skuInfoList.eq(i).find('td');
	         var skuid = child.eq(0).find('input').eq(0).val();
	         var originprice=  child.eq(3).find('input').eq(0).val();
	         var bgGoodsName = child.eq(0).text();
	         var unit = child.eq(1).text();
	         var desc = child.eq(2).text();
	         if(originprice==''||!exp.test(originprice)||originprice<=0 || originprice > 999999){
					alert("原价错误~");
					return;
			}
			 var activityprice = child.eq(4).find('input').eq(0).val();
	         if(activityprice==''||!exp.test(activityprice)||activityprice<=0 || activityprice > 999999){
					alert("活动价错误~");
					return;
			}
	        skuinfo.push({'skuId':skuid,'activityPrice':activityprice,'originPrice':originprice,'bgGoodsName':bgGoodsName,'unit' : unit,'desc' : desc});
		}
		
		bgGoodsInfo = {'bgGoodsId':bgGoodsId,'activityPrice':0,'originPrice':0,'skuListBean':skuinfo};
	}
		
	
	//var bgGoodsInfo = JSON.stringify(info);
	
	//alert(bgGoodsInfo);
	
	setParentPageField(bgGoodsInfo,storageTypeEdit,choosedGoodsName,imageUrl,goodsType);
	
	clearGoodsChooser();
	
	$("#goodsChooserModal").modal("hide");
	
});




//清空该页面的输入信息
function clearGoodsChooser() {
	$('input[name="goodsRadio"]:checked').prop("checked",false);
	$("input[name=originPriceEdit]").val("");
	$("input[name=nowPriceEdit]").val("");
	$("select[name=storageTypeEdit]").each(function(){
		
		$(this).find("option:first").attr("selected","selected");
		
	});
	
	$("#originprice").val("");
	$("#activityprice").val("");
	
}

//判断数字和小数点
function checkDouble(input) {
     var re = /^\d+(\.\d{1,2})?$/;

     if (!re.test(input)) {
        return false;
     }
     
     return true;
}

</script>