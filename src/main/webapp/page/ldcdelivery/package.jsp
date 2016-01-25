<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/print.js"></script>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<div class="row"> 	
	<div class="col-lg-3">
		<p id="packageSuccess"></p>
	</div>
		          
	<div class="col-lg-6">
		<p id="packageError" style="color:red"></p>
	</div>
	          
	<div class="col-lg-3">
		<div class="input-group">
			<button type="button" id="btnSubmit1" class="btn btn-primary" style="width:150px">批量出库</button>
		</div>
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
		              <input type="text" id="orderCode" class="form-control" value="" placeholder="请扫描面单号">
		          </div>
		          
		          <div class="col-lg-1">
		            <div class="input-group">
		              <button type="button" id="btnSubmit" class="btn btn-default">添加包裹</button>
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
	             			<th>麦客姓名</th>
	             			<th>麦客电话</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					
				</table>
			</div>
		</div>
	</div>
	<!--/span-->

</div>
<!--/row-->


<!-- Modal 驳回原因-->
<div class="modal fade" id="modalRemark" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">请填写备注信息：</h4>
      </div>
      <div class="modal-body">
        <textarea id="remark" style="margin: 0px; width: 565px; height: 65px;"></textarea>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="btnSubmitRemark">提交</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../footer.jsp"%>

<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>


<script>
var table = null;
var orderCodes = [];
//提交搜索条件
function add() {
	//$('#packageSuccess').empty();
	$('#packageError').empty();
	var orderCode = $('#orderCode').val();
	var rowCount = $("#orderList > tbody").children().length;
	if (rowCount >= 50) {
		$('#orderCode').val('');
		('批量出库最多支持50条');
		return;
	}

	//alert(orderCode);
	$.ajax({
		type: 'POST',
		url: 'getOrderByOrderCode',
		data: {'orderCode': orderCode},
		success: function(e) {
			console.log(e);
			if (e.status == 'success') {
				var line = '<tr>';
				var order = e.order;
				if (orderCodes.indexOf(order.orderCode)>=0) {
					//alert(orderCode + "已经在列表中，不能重复添加");
					$('#packageError').text(orderCode + "已经在列表中，不能重复添加");
					return;
				} else {
					//$('#packageSuccess').text("已加入"+orderCodes.legth);
					orderCodes.push(orderCode);
					$('#packageSuccess').text("已加入"+orderCodes.length+"包");
				}
				var detailInfo = '';
				
				for (var i = 0; i < order.details.length; i++) {
					detailInfo +=  order.details[i].skuName + "*" + order.details[i].skuNum + "</br>";
				}
			
				line += '<td>' + order.orderCode + '</td>';
				line += '<td>' + order.receiverName + '   ' + order.receiverPhone + '</td>';
				line += '<td>' + detailInfo + '</td>';
				line += '<td>' + new Date(order.createTime).Format("yyyy-MM-dd hh:mm:ss") + '</td>';
				//line += '<td>' + order.courierName + '<br>' + order.courierPhone + '</td>';
				line += '<td>' + order.courierName + '</td>';
				line += '<td>' + order.courierPhone + '</td>';
				line += '</tr>';
			
				$('#orderList > tbody').prepend(line);
			} else if (e.status == 'error') {
				$('#packageError').text(e.msg);
			}
		},
		error: function(e) {
			//alert("内部错误!");
			$('#packageError').text("内部错误!");
		}
	});
	$('#orderCode').val('');
	var rowCount = $("#orderList > tbody").children().length;
	if (rowCount >= 50) {
		//alert('批量出库一次最多50条，请先出库');
		$('#packageError').text("批量出库一次最多50条，请先出库");

	}
};

$('#btnSubmit').click(function(){	
	var orderCode = $('#orderCode').val();
	var rowCount = $("#orderList > tbody").children().length;
	if (rowCount >= 50) {
		$('#orderCode').val('');
		//alert('批量出库最多支持50条，请出库');
		$('#packageError').text("批量出库最多支持50条，请先出库");
		return;
	}

	$.ajax({
		type: 'POST',
		url: 'getOrderByOrderCode',
		data: {'orderCode': orderCode},
		success: function(e) {
			console.log(e);
			if (e.status == 'success') {
				var line = '<tr>';
				var order = e.order;
				if (orderCodes.indexOf(order.orderCode)>=0) {
					//alert(orderCode + "已经在列表中，不能重复添加");
					$('#packageError').text(orderCode + "已经在列表中，不能重复添加");
					return;
				} else {
					//$('#packageSuccess').text("已加入"+orderCodes.legth);
					orderCodes.push(orderCode);
					$('#packageSuccess').text("已加入"+orderCodes.length+"包");
				}
				var detailInfo = '';
				
				for (var i = 0; i < order.details.length; i++) {
					detailInfo +=  order.details[i].skuName + "*" + order.details[i].skuNum + "</br>";
				}
			
				line += '<td>' + order.orderCode + '</td>';
				line += '<td>' + order.receiverName + '   ' + order.receiverPhone + '</td>';
				line += '<td>' + detailInfo + '</td>';
				line += '<td>' + new Date(order.createTime).Format("yyyy-MM-dd hh:mm:ss") + '</td>';
				//line += '<td>' + order.courierName + '<br>' + order.courierPhone + '</td>';
				line += '<td>' + order.courierName + '</td>';
				line += '<td>' + order.courierPhone + '</td>';
				line += '</tr>';
			
				$('#orderList > tbody').prepend(line);
			} else if (e.status == 'error') {
				//alert("没有此包裹");
				$('#packageError').text(e.msg);
			}
		},
		error: function(e) {
			//alert("内部错误!");
			$('#packageError').text("内部错误");
		}
	});
	$('#orderCode').val('');
	var rowCount = $("#orderList > tbody").children().length;
	if (rowCount >= 50) {
		//alert('批量出库最多支持50条，请出库');
		$('#packageError').text("批量出库最多支持50条，请出库");
	}
});

$('#btnSubmit1').click(function(){
	var orderList = [];
	$('#orderList > tbody > tr').each(function(i, val){
        var orderCode = $('td:first', this).text();
        var courierName = $('td:nth-child(5)', this).text();
        var courierPhone = $('td:last',this).text();
        var node = {'orderCode': orderCode, 'courierName': courierName, 'courierPhone': courierPhone, 'fdcStatus':3};
        orderList.push(node);
    });
	
	console.log(orderList);
	console.log(JSON.stringify({'orderList': orderList}));
	
	$('#btnSubmit1').attr('disabled',true);
	$.ajax({
		type: 'POST',
		url: 'doPackage',
		data:  {"orderListJson":JSON.stringify({'orderList': orderList})},
		success: function(e) {
			console.log(e);
			if (e.status == 'success') {
				if(e.msg != ''){
					alert(e.msg);
				}
				$('#packageSuccess').text(e.successNum+ " 个包裹出库成功,"+e.errorNum+"个包裹出库失败,加油哟,亲~");
				$('#packageError').text();
				$('#orderCode').focus();
			} else if (e.status == 'error') {
				$('#packageError').text("出库失败，包裹已出库或没有此包裹");
			}
			$('#btnSubmit1').removeAttr("disabled");
		},
		error: function(e) {
			$('#btnSubmit1').removeAttr("disabled");
			$('#packageError').text("出库失败，包裹已出库或没有此包裹");
		}
	});
	$('#orderList > tbody').html("");
	orderCodes=[];
});

$(document).keypress(function(e) {
	//enter 事件
	if(e.which == 13) {
		add();
	}
});
$(document).ready(function() {
	$('#orderCode').focus();
});
</script>