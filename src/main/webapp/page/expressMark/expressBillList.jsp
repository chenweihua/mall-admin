<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<%@ include file="../header.jsp"%>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<style>
ul{
	list-style: none;
}
ul li{
	list-style: none;
}
table th{
	text-align: center;
}

table td{
	text-align: center;
}
</style>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i>未标注面单列表
				</h2>
				<div class="box-icon">
					 <a href="#" class="btn btn-minimize btn-round btn-default">
					 	<i class="glyphicon glyphicon-chevron-up"></i>
					 </a> 
				</div>
			</div>
			<div class="box-content">
				<div class="row">
				
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">日期（天）</span> <input type="text"
								id="date-day" class="form-control" value="${startDate}">
						</div>
					</div>
					
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">时间(小时)</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="hourText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="hourMenu">
									<li><a href="javascript:void(0);" class="hour" data-val="-1">全部</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="1">0:00-1:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="2">1:00-2:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="3">2:00-3:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="4">3:00-4:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="5">4:00-5:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="6">5:00-6:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="7">6:00-7:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="8">7:00-8:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="9">8:00-9:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="10">9:00-10:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="11">10:00-11:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="12">11:00-12:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="13">12:00-13:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="14">13:00-14:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="15">14:00-15:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="16">15:00-16:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="17">16:00-17:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="18">17:00-18:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="19">18:00-19:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="20">19:00-20:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="21">20:00-21:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="22">21:00-22:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="23">22:00-23:00</a></li>
									<li><a href="javascript:void(0);" class="hour" data-val="24">23:00-24:00</a></li>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
						</div>
					</div>
				</div>

				<br>
				<table id="expressBillTable" class="table table-striped table-bordered bootstrap-datatable datatable">
					<thead>
						<tr>
							<th>快递编号</th>
							<th>快递ID</th>
							<th>门店ID</th>
							<th>手机号</th>
							<th>时间</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<%@ include file="../footer.jsp"%>
<%@ include file="expressBillMark.jsp"%>


<script type="text/javascript">
$('#date-day').datetimepicker({
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

var hour = -1;
var table = null;

//选择时间
$('#hourMenu').delegate("li a","click",function() {
	hour= $(this).data("val");
	$('#hourText').html($(this).text());
	table.ajax.reload();
});

//提交搜索条件
$('#btnSubmit').click(function() {
	table.ajax.reload();
});

	
$(document).ready(function() {
	table = $('#expressBillTable').DataTable(
		{
			ordering : false,
			processing : true,
			serverSide : true,
			searching : false,
			ajax : {
				url :"/express/bill/query",
				data : function(d) {
					d.day = $('#date-day').val();
					d.hour = hour;
				},
				dataSrc : function(json) {
					if(json.code == 0){
						var newData = [];
						var billList = json.data;
						var bill = null;
						for ( var i = 0, len = billList.length; i < len; i++) {
							bill = billList[i];
							if(bill != null){
								operate = '<a class="blueletter" href="javascript:void(0)" onclick="markBill(\''+bill.rowkey+'\',\''+bill.mendianId+'\',\''+bill.kuaidiId+'\',\''+bill.kuaidiNo+'\',\''+bill.phoneNo+'\',\''+bill.picurl+'\',\''+bill.time+'\')">标注</a>';
								newData[newData.length] = [
										bill.kuaidiNo,  
										bill.kuaidiId, 
										bill.mendianId,                
										bill.phoneNo,   
										parseTimestamp(bill.time),   
										operate
										];
							}
						}
						json.data = newData;
						return newData;
					}else{
						alert(json.msg);
					}
				}
			},
			sDom : "<'row'<'col-md-4'f>r>t<'row'<'col-md-10'i><'col-md-2'l>> <'col-md-12 center-block'p>",
			sPaginationType : "bootstrap",
			oLanguage : {
				sLengthMenu : "_MENU_ 记录每页",
				sSearch : "按名称或条码搜索 _INPUT_",
				sZeroRecords : "暂无数据",
				sProcessing : "正在处理...",
				sEmptyTable : "暂无数据",
				sInfo : "_START_ - _END_ (共_TOTAL_条)",
				sInfoFiltered : "",
				oPaginate : {
					sFirst : "第一页",
					sLast : "最后一页",
					sNext : "下一页",
					sPrevious : "前一页",
				}
			}
		});
});

function markBill(rowkey,mendianId,kuaidiId,kuaidiNo,phoneNo,picurl,time){
	//标注
	$('#imageShow').attr("src",picurl);
	$('#rowkey').val(rowkey);
	$('#mendianId').val(mendianId);
	$('#kuaidiId').val(kuaidiId);
	$('#kuaidiNo').val(kuaidiNo);
	$('#phoneNo').val(phoneNo);
	$('#picurl').val(picurl);
	$('#time').val(parseTimestamp(time));
	$('#markModal').modal('show');
}

function parseTimestamp(timestamp){
	var d = new Date();
     d.setTime(timestamp);
     var payYear =d.getFullYear();
     var payMonth =d.getMonth()+1;
     var payDate = d.getDate();
     var payHour =checkTime(d.getHours());
     var payMin = checkTime(d.getMinutes());
     var paySec = checkTime(d.getSeconds());
     return payYear+"-"+payMonth+"-"+payDate+" "+payHour+":"+payMin+":"+paySec;
}
 function checkTime(i){
     if (i<10){
    	 i="0" + i
   	 }
     return i
 }

</script>
