<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<link
	href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<script type="text/javascript" src="/js/print.js"></script>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<div class="row" style=""> 
	<div class="col-lg-3">
		<span  style="color:red;">新订单  </span><span id="newOrderCount" style="color:red;font-size:20px">${newOrderCount}</span></br>
		<span  style="color:red">最近10天未出库订单数  </span><span id="orderTotalCount" style="color:red;font-size:20px"> ${latestFdcCount}</span>
	</div>
	<div class="col-lg-6" style="width:450px">
		<p id="orderPrintCount" style="color:red"></p>
	</div>
	<div class="col-lg-3">
		<span class="input-group" style="width:300px">
			<button type="button" id="refurbish-btnSubmit" class="btn btn-primary" style="margin-left:0px" >刷 新</button>

		    <button type="button" id="btnSubmit1" class="btn btn-primary" style="margin-left:90px;width:150px">批量打印</button>
		</span>
	</div>
</div>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 订单查询
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
		              <span class="input-group-addon">开始时间</span> <!--<input type="text"
		                id="start-date" class="form-control" value="${startDate}">-->
		                <input class="datepicker form-control" id="start-date" value="${startDate}">
		                
		            </div>
		          </div>
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">结束时间</span> <!--<input type="text"
		                id="end-date" class="form-control" value="${endDate}">-->
		            	<input class="datepicker form-control" id="end-date" value="${endDate}">     	
		            </div>
		          </div>  
				</div>
		   		</br>
		       <div class="row"> 
		       <div class="col-lg-3">
	       			<div class="input-group">
					    	 <span class="input-group-addon">仓库</span>
				        	<select class="form-control" id="storageId" name="storageId" style="width:150px">
				        		<c:forEach var="storage" items="${storageList}">
				                	<option value="${storage.storageId}" >${storage.storageName}</option>
				                </c:forEach>
				            </select>
					</div>
		       </div>
		       <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">状态</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnStatusText">未打印</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="0">全部</a></li>
		                    <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="1">未打印</a></li>
		                    <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="2">待出库</a></li>
		                    <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="3">已出库</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		       	 <div class="col-lg-3">
		              <input type="text" id="query-word" class="form-control" value="" placeholder="请输入面单号, 姓名，手机号">
		          </div>
		          
		          <div class="col-lg-1">
		            <div class="input-group">
		              <button type="button" id="btnSubmit" class="btn btn-primary">搜索</button>
		            </div>
		          </div>
		       </div>
		
		        <br>
        		<br>
				<table id="orderList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>面单号</th>
							<th>用户信息</th>
							<th>商品明细</th>
	             			<th>下单时间</th>
	             			<th>备注</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					
				</table>
			</div>
		</div>
	</div>
	<!--/span-->

</div>
<!--/row-->

<%@ include file="../footer.jsp"%>

<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>


<script>

$('#start-date').datetimepicker({
	language : 'zh-CN',
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	forceParse : 0,
	showMeridian : 1,
	format : 'yyyy-mm-dd'
});

$('#end-date').datetimepicker({
	language : 'zh-CN',
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	forceParse : 0,
	showMeridian : 1,
	format : 'yyyy-mm-dd'
});

var table = null;
var fdcStatus =1;

var ajaxData = null;
  
var fdcStatusMap = {
	0:"全部",
	1:"未打印",
	2:"待出库",
	3:"已出库"
};

var deliveryTypeMap = {
	0:"自提",
	1:"限时达"
};

function genDeliveryTime(deliveryType, createTime, p2pDeliveryStartTime, p2pDeliveryEndTime) {
	if (deliveryType == 1 || deliveryType == 3) {
		var startTime = new Date(p2pDeliveryStartTime).Format('yyyy-MM-dd hh:mm');
		var pieces = startTime.split(" ");
		var date = pieces[0];
		var t1 = pieces[1];
		var endTime = new Date(p2pDeliveryEndTime).Format('yyyy-MM-dd hh:mm');
		var t2 = endTime.split(" ")[1];
		return date + ' ' +  t1 + '-' + t2;
		
	} else {
		//自提时间改为隔天
		return new Date(createTime).Format('yyyy-MM-dd');
	}	
};

function prefixName(name, len) {
	if (name.length > len) {
		return name.substr(0, len) + '...';
	}
	return name;
};

//获取页数
function getPageNum(totalNum, NumPerPage) {
	if (totalNum % NumPerPage == 0) {
		return totalNum / NumPerPage;
	} 
	return parseInt(totalNum / NumPerPage) + 1;
};


function genPrintData(ajaxData, orderCode) {
	console.log(ajaxData);
	var orderCodes = [];
	var printData = Object();
	printData['action'] = "printer";
	printData['pid'] = 1;
	
	var data = Object();
	printData['data'] = data;
	
	data['uid'] = 1;
	data['tid'] = 2;
	data['type'] = 3;
	
	orderDetail = [];
	skuInfo = [];
	data['orderCount'] = 0;
	data['skuInfo'] = skuInfo;
	data['orderDetail'] = orderDetail;
	
	var totalCount = 0;
	var skuInfoMap = {};
	
	for (i=0; i<ajaxData.data.length; i++) {
		order = ajaxData.data[i];
		if (orderCode != order.orderCode) {
			continue;
		}
		
		orderCodes.push(order.orderCode);
		
		details = order.details;
		//var pageNum = parseInt(details.length/10) + 1;
		var pageNum = getPageNum(details.length, 8);
		var k = 0;
		skuTotalCount = 0;
		var item;
		
		for(k=0; k<details.length; k++) {
			skuTotalCount = skuTotalCount + details[k].skuNum;
		}
		
		k=0;
		while(k<details.length) {
			if (k % 8  == 0 && (!(item===null))) {
				if (k > 0) {
					orderDetail.push(item);
				}
				item = Object();
				item['orderCode'] = order.orderCode;
				item['receiverName'] = prefixName(order.receiverName, 4);
				item['receiverPhone'] = order.receiverPhone;
				item['receiverAddress'] = order.receiverAddress;
				item['college'] = order.collegeName;
				item['deliveryType'] = deliveryTypeMap[order.deliveryType];
				item['deliveryTime'] = genDeliveryTime(order.deliveryType, order.createTime, 
						order.p2pDeliveryStartTime, order.p2pDeliveryEndTime);
				var currentPageNum = parseInt(k/8)+1;
				item['currentTip'] = currentPageNum + "/" + pageNum;
				item['skuTotalCount'] = skuTotalCount;
				item['description'] = '4008635200';
			}

			var skuName = details[k].skuName;
			var skuNum = details[k].skuNum;
			var skuId = "[" + details[k].limitedGbm + "]";
			var skuSubName = details[k].skuSubName;
			var skuNameKey = 'skuName' + (k%8+1);
			var skuNumKey = 'skuCount' + (k%8+1);
			
			item[skuNameKey] = skuId + "  " + prefixName(skuName, 15);
			item[skuNumKey] = skuNum;
			k = k + 1;
		}
		orderDetail.push(item);
		item = null;
		break;
	}
	
	return JSON.stringify({'printData': printData, 'orderCodes': orderCodes});	
};

function print(orderCode) {
	var printData = genPrintData(ajaxData, orderCode);
	//console.log(printData);
	//console.log(orderCode);
	$.ajax({
		type: 'POST',
		url: 'print',	
		data: {"storageId":storageId,"printData":printData},
		success: function(e) {
			//console.log(e);
			if (e.code == '0') {
					//alert("打印成功!");
		     	//location.reload();
				$('#orderPrintCount').text("打印成功");
				table.ajax.reload();
			} else if (e.code == '1') {
				//alert("内部错误!");
				$('#orderPrintCount').text("内部错误");
			} else{
			//alert("打印服务错误, 请联系fdc产品童鞋！");
			$('#orderPrintCount').text(e.msg);
		}
		},
		error: function(e) {
			alert("内部错误");
		}
	});
};

//生成批量打包数据
function genBatchPrintData(ajaxData) {
	console.log(ajaxData);
	var orderCodes = [];
	var printData = Object();
	printData['action'] = "printer";
	printData['pid'] = 1;
	
	var data = Object();
	printData['data'] = data;
	
	data['uid'] = 1;
	data['tid'] = 2;
	data['type'] = 3;
	data['orderCount'] = ajaxData.data.length;
	
	orderDetail = [];
	skuInfo = [];
	data['skuInfo'] = skuInfo;
	data['orderDetail'] = orderDetail;
	
	var totalCount = 0;
	var skuInfoMap = {};
	
	for (i=0; i<ajaxData.data.length; i++) {
		order = ajaxData.data[i];
		var key =  'orderCode'+(i+1);
		data[key] = order.orderCode;
		orderCodes.push(order.orderCode);
		
		details = order.details;
		//var pageNum = parseInt(details.length/10) + 1;
		//详情页每页8个sku一页
		var pageNum = getPageNum(details.length, 8);
		var k = 0;
		var item;
		var skuTotalCount = 0;
		
		for(k=0; k<details.length; k++) {
			skuTotalCount = skuTotalCount + details[k].skuNum;
		}
		
		k = 0;
		while(k<details.length) {
			if (k % 8 == 0) {
				if (k > 0 && (!(item===null))) {
					orderDetail.push(item);
				}
				item = Object();
				item['orderCode'] = order.orderCode;
				item['receiverName'] = prefixName(order.receiverName, 4);
				item['receiverPhone'] = order.receiverPhone;
				item['receiverAddress'] = order.receiverAddress;
				item['college'] = order.collegeName;
				item['deliveryType'] = deliveryTypeMap[order.deliveryType];
				item['deliveryTime'] = genDeliveryTime(order.deliveryType, order.createTime, 
						order.p2pDeliveryStartTime, order.p2pDeliveryEndTime);	
				var currentPageNum = parseInt(k/8)+1;
				item['currentTip'] = currentPageNum + "/" + pageNum;
				item['skuTotalCount'] = skuTotalCount;
				item['description'] = '4008635200';
			}

			var skuName = details[k].skuName;
			var skuNum = details[k].skuNum;
			var skuNameKey = 'skuName' + (k%8+1);
			var skuNumKey = 'skuCount' + (k%8+1);
			
			var skuId = "[" + details[k].skuId + "]";
			//var skuSubName = details[k].skuSubName;
			
			item[skuNameKey] = skuId + "  " + prefixName(skuName, 15);
			//item[skuNameKey] = prefixName(skuName, 15);
			item[skuNumKey] = skuNum;
			
			totalCount = totalCount + skuNum;
			var newSkuName = "[" + details[k].skuId + "]" + "  " + skuName;
			if (newSkuName in skuInfoMap) {
				skuInfoMap[newSkuName] = skuInfoMap[newSkuName] + skuNum;
			} else {
				skuInfoMap[newSkuName] = skuNum;
			}
			k = k + 1;
		}
		orderDetail.push(item);
		item = null;
	}

	//生成skuInfo
	var j = 0;
	var sku;
	var totalSku = 0;
	for (key in skuInfoMap) {
		totalSku = totalSku + 1;
	}
	
	//var pageNum = parseInt(10 - totalSku%10 + totalSku)/10;
	var pageNum = getPageNum(totalSku, 10);
	for (key in skuInfoMap) {
		if (j%10 == 0) {
			if (j  > 0) {
				skuInfo.push(sku);
			}
			sku = Object();
			sku['skuCount'] = totalCount;
			var currentPageNum = parseInt(j/10) + 1;
			sku['currentTip'] = currentPageNum + "/" + pageNum;
		}
		var skuNameKey = 'skuName' + (j%10+1);
		var skuNumKey = 'skuCount' + (j%10+1);
		sku[skuNameKey] = prefixName(key, 15);
		sku[skuNumKey] = skuInfoMap[key];	
		j=j+1;
	}
	skuInfo.push(sku);
	//end 生成skuInfo
	return JSON.stringify({'printData': printData, 'orderCodes': orderCodes});
};

$('.btnStatus').click(function(){
    fdcStatus = $(this).data("val");
    $('#btnStatusText').html(fdcStatusMap[fdcStatus]);
});

var storageId=$("#storageId").val();

function query() {
	/* var startDate = $('#start-date').val();
	var endDate = $('#end-date').val();
	var queryWord = $('#query-word').val();
 	window.location.href = urlPrefix+"order/list?startDate="+startDate+"&endDate="+endDate+"&queryWord="+queryWord+"&fdcStatus="+fdcStatus;	 */
 	storageId = $("#storageId").val();
	table.ajax.reload();
};

//提交搜索条件
$('#btnSubmit').click(function(){
/* 	this.prop('disabled',true);
	var _this=this;
	setTimeout(function(){
		_this.prop('disabled',false);
	},30*1000) */
  	query();
});
//刷新：和btnSubmit按钮功能相同
$('#refurbish-btnSubmit').click(function(){
  	query();
});

//批量打印
$('#btnSubmit1').click(function(){
	console.log(ajaxData);
	var printData = genBatchPrintData(ajaxData);
	//console.log(printData);
	$.ajax({
		type: 'POST',
		url: 'print',	
		data: {"storageId":storageId,"printData":printData},
		success: function(e) {
			console.log(e);
			if (e.code == 0) {
				$('#orderPrintCount').text('打印成功');
				table.ajax.reload();
			} else if (e.code == 1) {
				$('#orderPrintCount').text("内部错误");
			} else if (e.code == 2) {
				$('#orderPrintCount').text("打印服务错误");
			}
		},
		error: function(e) {
			$('#orderPrintCount').text("内部错误");	
		}
	});
});



$(document).ready(function() {
  	$('#btnStatusText').html(fdcStatusMap[fdcStatus]);
	table = $('#orderList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        bLengthChange: false,
        ajax: {
        	url: "query",
            data: function(d) {
              //时间
              d.beginDate = $('#start-date').val();
              d.endDate = $('#end-date').val();
              d.queryWord = $('#query-word').val();
              d.fdcStatus = fdcStatus;
              d.storageId=$("#storageId").val();
              d.searchStr = $("#query-word").val();
          },
        	dataSrc: function ( json ) {
        		storageId_temp = $("#storageId").val();
        		ajaxData = json;
        		var newData = [];
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var order = json.data[i];
        			var details = order.details;
        			var detailInfo = "";
        			for (var j = 0; j < details.length; j++) {
        				detailInfo +=  details[j].skuName + "*" + details[j].skuNum + "</br>";
        			}
        			
        			var dateTime = new Date(order.createTime).Format("yyyy-MM-dd hh:mm:ss");
        			
        			var action='';
        			//da
        			if (order.fdcStatus <= 1) {
                  		action = '<a href="javascript:void(0);" onclick="print(\''+ order.orderCode + '\')">打印</a>';
        			} else {
                  		action = '<a href="javascript:void(0);" onclick="print(\''+ order.orderCode + '\')">重新打印</a>';
        			}

        			var row = [
        			          	order.orderCode,
        			            order.receiverName + "   " + order.receiverPhone, 
        			            detailInfo,
        			            dateTime,
        			            order.remark,
        			            fdcStatusMap[order.fdcStatus],
        			            action
        			           ];
        			
        			newData[newData.length] = row;
        		}
				//$('#orderTotalCount').text('总订单数 '+ json.recordsFiltered);
        		return newData;
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sInfoEmpty: "共0条",
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "按手机号搜索 _INPUT_",
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
            },
        }
    });
});

/* $("ul li").click(function(){
	query();
}); */
/**获得待查询的仓库id，每次向后台查询订单是，对该变量赋值*/
var storageId_temp =0;
$(document).keypress(function(e) {
	//enter 事件
	if(e.which == 13) {
		query();
	}
});
$(document).ready(function() {
	$('#query-word').focus();
});

function getLdcCount()
{
	$.ajax({
		type: 'POST',
		url: 'getOrdersCount',	
		data: {"storageId":storageId},
		success: function(e) {
			console.log(e);
			if (e.status == 'success') {
				//$('#newOrderCount').text('新订单 ' + e.newOrdersCount);
				//$('#orderTotalCount').text('最近10天未出库订单数 ' + e.ordersCount);
				//$('#newOrderCount').text('最近10天未打包订单数 ' + e.newOrdersCount);
				$('#newOrderCount').text(e.newOrdersCount);
				$('#orderTotalCount').text(e.ordersCount);
			} else if (e.status == 'error') {
				//alert("内部错误!");
				$('#orderPrintCount').text("内部错误");
			}
		},
		error: function(e) {
			//alert("内部错误!");			
			$('#orderPrintCount').text("内部错误");	
		}
	});
}

 setInterval("getLdcCount()",2*60*1000); //10秒一次执行

</script>