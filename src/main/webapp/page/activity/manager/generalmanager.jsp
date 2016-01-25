<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../../header.jsp"%>

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

<script type="text/javascript" src="/js/mustache.js"></script>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 日常专题商品管理
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

					<div class="col-sm-2">
						<div class="input-group">
							<span class="input-group-addon">活动</span> <select
								class="form-control" id="activity" name="activity">
								<option value="0">全部</option>
								<c:forEach var="activity" items="${activitylist}">
									<option value="${activity.activityId}">${activity.activityName}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="col-sm-2">
						<div class="input-group">
							<span class="input-group-addon">状态</span> <select
								class="form-control" id="status" name="status">
								<option value="0" selected="selected">全部</option>
								<option value="1">待售</option>
								<option value="2">在售</option>
								<option value="3">售罄</option>
							</select>
						</div>
					</div>

					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">名称</span> <input type="text"
								id="searchStr" class="form-control" placeholder="请输入商品名称">
						</div>
					</div>

					<div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
						</div>
					</div>

				</div>
				<br>
				<div class="pl15">
					<button type="button" class="btn btn-primary btn-sm" id="setButton">批量保存</button>
					&nbsp;&nbsp;
					<button type="button" class="btn btn-primary btn-sm" id="queryQrCodeBtn">查看二维码</button>
				</div>
				<br>
				<table id="seckillgoods"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th style='width:8%'><input type="checkBox" id="checkAll">序号</th>
							<th style='width:10%'>商品名称</th>
							<th style='width:10%'>活动名称</th>
							<th style='width:10%'>标签</th>
							<th style='width:10%'>描述</th>
							<th style='width:8%'>单位</th>
							<th style='width:8%'>状态</th>
							<th style='width:8%'>权重</th>
							<th style='width:8%'>限购</th>
							<th style='width:10%'>商品类型</th>
							<th style='width:10%'>操作</th>
						</tr>
					</thead>

				</table>
			</div>

			<div class="pl15">
				<button type="button" class="btn btn-primary btn-sm" id="setButton1">批量保存</button>
			</div>
		</div>
	</div>
	<!--/span-->



</div>
<!--/row-->
<%@ include file="../../footer.jsp"%>
<%@ include file="../../region_tree.jsp"%>
<%@ include file="../../qrCode.jsp"%>


<script id="seckillskuTableTemplate" type="text/template">
		<p>商品信息：</p>
		<table border='1' id="skuinfo"><tr class="title"><td>商品名称</td><td>单位</td><td>标记</td><td>描述</td><td>原价</td><td>活动价</td><td>限售数量</td></tr>
       		 {{#bgSkuInfo}}
            	<tr class="skuinfo">	
					<td><input type="hidden" id="skuid" value='{{activityBgSkuId}}'/> {{goodsName}}</td>
					<td> {{unit}}</td>
					<td> {{remark}}</td>
					<td> {{describe}}</td>
					<td><input type="text" id="originprice" value="{{origPriceDouble}}"/></td>
					<td><input type="text" id="activityprice" value="{{activityPriceDouble}}"/></td>
					<td><input type="text" id="stock" value="{{stock}}"/></td>
				</tr>
       		{{/bgSkuInfo}}
		</table>
</script>
<script>

	$("#queryQrCodeBtn").click(function(){
		
		if($("#activity").val() == "0") {
			alert("请先选择活动!");
			return;
		}
		
		var selectedGoodsSize = $("input[name=bggoodsId]:checked").size();
		if(selectedGoodsSize == 0) {
			alert("请先选择一个商品");
			return;
		}
		if(selectedGoodsSize > 1) {
			alert("只能选择一个商品");
			return;
		}
		
		var activityId = $("#activity").val();
		var activityBgGoodsId = $("input[name=bggoodsId]:checked").val();
		
		/* 需求一开始只支持单品，后需求改为都支持
		for ( var i = 0, len = jsonData.length; i < len; i++) {
			var activityGoods = jsonData[i];
			var id = activityGoods.activityBgGoodsId;
			if(id == activityBgGoodsId) {
				if(activityGoods.goodsType != 1) {
					alert("查看二维码操作只支持单品");
					return;
				}
			}
		}
		*/
		
		
	    $.post("queryActivityGoodsCollege", {"activityId":activityId,"activityBgGoodsId":activityBgGoodsId},function(ret){
			 if (ret.code != 0) {
	            alert(ret.msg);
	         }else {
	        	$('#treeEdit').modal('show');
	        	var zNodes = ret.data;
	     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
	     	 }
		},"json"); 
		
		
		
	});
	
	
	$("#treeSubmit").click(function(){
		var treeObj=$.fn.zTree.getZTreeObj("selectTree");
		
		var nodes=treeObj.getCheckedNodes();
		var checkNodeIdArr = new Array();
		var collegeName;
		for(var i =0; i < nodes.length; i++) {
			var checkNodeId = nodes[i].id;
			var checkNodeName = nodes[i].name;
			if(checkNodeId.indexOf('college_') == 0) {
				checkNodeId = checkNodeId.substring(checkNodeId.lastIndexOf('_') + 1,checkNodeId.length);
				checkNodeIdArr[checkNodeIdArr.length] = checkNodeId;
				collegeName = checkNodeName;
			}
		}
		if(checkNodeIdArr.length == 0) {
			alert("请选择一个学校节点");
			return;
		}
		if(checkNodeIdArr.length > 1) {
			alert("只能选择一个学校节点");
			return;
		}
		
		var collegeId = checkNodeIdArr[0];
		var activityId = $("#activity").val();
		var activityBgGoodsId = $("input[name=bggoodsId]:checked").val();
		
		
		$.post("queryActivityGoodsId", {"activityId" : activityId,"activityBgGoodsId" : activityBgGoodsId,"collegeId" : collegeId},function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {
	     		setQrUrl(ret.data,0,2,collegeId, collegeName);
	     		$("#modalQr").modal("show");
	     	 }
		},"json");
		
		
		
	});
	
	setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType : "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			}
			
	};


	$('#start-time').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		format : 'yyyy-mm-dd hh:ii:00'
	});

	$('#end-time').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		format : 'yyyy-mm-dd hh:ii:00'
	});
	var table;
	var jsonData;
	$(document)
			.ready(
					function() {
						//datatable
						table = $('#seckillgoods')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											searching : false,
											ajax : {
												url : "querygeneralgoods",
												data : function(d) {
													d.activityId = $("#activity").val();
													d.status = $("#status").val();
													d.activityType = 0;
													d.searchStr = $("#searchStr").val();
												},
												dataSrc : function(json) {
													var newData = [];
													var seq = json.start + 1;
													jsonData = json.data;
													for ( var i = 0, len = json.data.length; i < len; i++) {
														var activityGoods = json.data[i];
														var id = activityGoods.activityBgGoodsId;
														var bgGoodsNameLink = '<a class="blueletter" href="javascript:void(0)" onclick="showSkuInfo(this,'
															+ id+ ')">'
															+ activityGoods.goodsName
															+ '</a>';
														var type = "未知";
														if(activityGoods.storageType==0){
															type="RDC商品";
														}else if(activityGoods.storageType==1){
															type="LDC商品"
														}else if(activityGoods.storageType==2){
															type="第三方商品"
														}
														
														newData[newData.length] = [
																'<input type="checkBox" name="bggoodsId" value="'+id+'">'
																		+ (seq + i),
																bgGoodsNameLink,
																activityGoods.activityName,
																activityGoods.remark,
																activityGoods.describe,
																activityGoods.uint,
																'<input type="hidden" id="statusVal'+id+'" name="statusVal'+id+'" value="'+activityGoods.status+'"></input>'
																+ '<input class="radiobutton" type="radio" name="statusRadio'+ id+ '" value="1" onclick="radioclick(this,'+id+')"/><label style="font-weight:lighter" onclick="labelclick(this)">待售</label><br>'
																+ '<input class="radiobutton" type="radio" name="statusRadio'+ id+ '" value="2" onclick="radioclick(this,'+id+')"/><label style="font-weight:lighter" onclick="labelclick(this)">在售</label><br>'
																+ '<input class="radiobutton" type="radio" name="statusRadio'+ id+ '" value="3" onclick="radioclick(this,'+id+')"/><label style="font-weight:lighter" onclick="labelclick(this)">售罄</label>',

																'<input type="text" style="width:40px;" id="weight'+id+'" value="'+activityGoods.weight+'">',
																'<input type="text" style="width:60px;" id="maxnum'+id+'" value="'+activityGoods.maxNum+'">', 
																type, 
																'<a class="blueletter" href="javascript:void(0)" onclick="save('+ id+ ')">【保存】</a><br>'
																+ '<a class="blueletter" href="javascript:void(0)" onclick="deleteGoods(\''+ id+'\',\''+activityGoods.goodsName+ '\')">【删除】</a>' ];
													}
													json.data = newData;
													return newData;
												}
											},
											sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
											sPaginationType : "bootstrap",
											oLanguage : {
												sLengthMenu : "_MENU_ 记录每页",
												sSearch : "按名称搜索 _INPUT_",
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
												},

											},
											fnDrawCallback : function() {
												defaultSelect();
											},
									       	fnInitComplete: function(){								       		
									       		var child = $('#seckillgoods').find('tr').eq(1).find('td');
									       		var id = child.eq(0).find("input").eq(0).val();
												var flag = child.eq(6).find("input").eq(0).val();
												$('input:radio[name="statusRadio' + id + '"][value="' + flag + '"]').prop("checked", true);
									       	},
									        iDisplayLength:100
										});

					});

	function defaultSelect() {
		var trs = $('#seckillgoods').find('tr');
		for ( var i = 1; i < trs.length; i++) { //第一行为标题                         
			var child = trs.eq(i).find('td');
			var id = child.eq(0).find("input").eq(0).val();
			//			var flag =  child[7].getElementsByTagName("input")[0].value;
			var flag = child.eq(6).find("input").eq(0).val();
			$('input:radio[name="statusRadio' + id + '"][value="' + flag + '"]')
					.prop("checked", true);
		}
	}
	
	function radioclick(obj,id){	
		$("#statusVal"+id).val(obj.value);
	}
	
	//全选
	$("#checkAll").click(function() {
		if ($("#checkAll").prop("checked") == true) {
			$("input[name='bggoodsId']").each(function() {
				$(this).prop("checked", true);
			});
			//alert();
		} else {
			$("input[name='bggoodsId']").each(function() {
				$(this).prop("checked", false);
			});
		}
	});

	//提交搜索条件
	$('#btnSubmit').click(function() {
		table.ajax.reload();
	});
	
/************************************************修改价格（单条和批量） begin***********************************************************/
	/**
		单条商品修改价格
	*/
	function save(id){
		if (id == "") {
			alert("请选择要修改的商品~");
			return;
		}
		dosave(id)
	}
	/**
	批量修改价格
	*/
	$("#setButton").click(function(){
		//获取勾选的资产的ID集合
		var bggoodsIds = "";
		$("input[name=bggoodsId]").each(function() {
			if ($(this).prop("checked")) {
				bggoodsIds += $(this).val() + ",";
			}
		});
		if (bggoodsIds == "") {
			alert("请选择要批量修改商品~");
			return;
		}
		dosave(bggoodsIds)
	});
	
	/**
	批量修改价格
	*/
	$("#setButton1").click(function(){
		//获取勾选的资产的ID集合
		var bggoodsIds = "";
		$("input[name=bggoodsId]").each(function() {
			if ($(this).prop("checked")) {
				bggoodsIds += $(this).val() + ",";
			}
		});
		if (bggoodsIds == "") {
			alert("请选择要批量修改商品~");
			return;
		}
		dosave(bggoodsIds)
	});
	
	function dosave(idstr){
		var idstr_temp = idstr+"";
		var info = [];
		var idarr = idstr_temp.split(",");
		for(var i=0;i<idarr.length;i++){
			var id=idarr[i];
			if(id==""){
				continue;
			}
			var status = $("#statusVal"+id).val();
			var weight = $("#weight"+id).val();
			if(weight==''||!/^[0-9]*$/.test(weight)||weight<1){
				alert("权重错误~");
				$("#weight"+id).focus();
				return;
			}
			var maxnum = $("#maxnum"+id).val();
			if(maxnum==''||!/^[0-9]*$/.test(maxnum)||maxnum<1){
				alert("限购数量错误~");
				$("#maxnum"+id).focus();
				return;
			} 
			var skuInfo= getSkuInfo(id);
			if(skuInfo=="false"){
				return;
			}
			if(skuInfo.length==0){
				info.push({'activityBgGoodsId':id,'weight':weight,'status':status,'maxNum':maxnum});
			}else{
				info.push({'activityBgGoodsId':id,'weight':weight,'status':status,'maxNum':maxnum,'modifySkuList':skuInfo});
			}
		}
		
		var modifyStr = JSON.stringify(info);
		//alert($("#activity").val());
		//alert($("#storageType").val());
		$.post("modify",
			{"modifyStr":modifyStr,"modifytype":0},
			function(response) {
				if (response.code == 0) {
					$("#checkAll").prop("checked", false);
					alert(response.msg);
					table.ajax.reload(null,false);
				} else {
					alert(response.msg);
				}
		},"json");
		
	}
	
	/***
						<td><input type="text" id="originprice" value="{{origPriceDouble}}"/></td>
					<td><input type="text" id="activityprice" value="{{activityPriceDouble}}"/></td>
					<td><input type="text" id="stock" value="{{stock}}"/></td>
	*/
	/**获得商品下sku的修改信息*/
	function getSkuInfo(bggoodsId){
		var skuinfo = [];
		var skuInfoList = $('#block'+bggoodsId).find('.skuinfo');
		if(!skuInfoList && typeof(skuInfoList)!="undefined"){
			return skuinfo;
		}
		 var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		for(var i=0;i<skuInfoList.length;i++){
	         var child =skuInfoList.eq(i).find('td');
	         var skuid = child.eq(0).find('input').eq(0).val();
	         var originprice=  child.eq(4).find('input').eq(0).val();
	         if(originprice==''||!exp.test(originprice)||originprice<=0){
					alert("原价错误~");
					$('#modalEdit').modal('hide');
					child.eq(3).find('input').eq(0).focus();
					return "false";
			}
			 var activityprice = child.eq(5).find('input').eq(0).val();
	         if(activityprice==''||!exp.test(activityprice)||activityprice<=0){
					alert("活动价错误~");
					$('#modalEdit').modal('hide');
					child.eq(4).find('input').eq(0).focus();
					return "false";
			}
	         var stock =  child.eq(6).find('input').eq(0).val();
	         if(stock==''||!/^[0-9]*$/.test(stock)||stock<=0){
					alert("限售数量错误~");
					$('#modalEdit').modal('hide');
					child.eq(5).find('input').eq(0).focus();
					return "false";
			}
	         skuinfo.push({'activityBgSkuId':skuid,'stock':stock,'activityPrice':activityprice,'originPrice':originprice})
		}
		return skuinfo
	}
	/************************************************修改价格（单条和批量）end ***********************************************************/
	
	/************************************************修改时间 begin***********************************************************/
	function edittime(id,begintime,endtime,showtime){
		$('#begintime').val(begintime);
		$('#endtime').val(endtime);
		$('#showtime').val(showtime);
		$('#bgGoodsId').val(id);
		$('#modalEdit').modal();
	}
	
	$("#setTimeButton").click(function(){
		//获取勾选的资产的ID集合
		var bggoodsIds = "";
		$("input[name=bggoodsId]").each(function() {
			if ($(this).prop("checked")) {
				bggoodsIds += $(this).val() + ",";
			}
		});
		if (bggoodsIds == "") {
			alert("请选择要批量修改商品~");
			return;
		}
		$('#begintime').val('');
		$('#endtime').val('');
		$('#showtime').val('');
		$('#bgGoodsId').val(bggoodsIds);
		$('#modalEdit').modal();
	});
	
	$("#setTimeButton1").click(function(){
		//获取勾选的资产的ID集合
		var bggoodsIds = "";
		$("input[name=bggoodsId]").each(function() {
			if ($(this).prop("checked")) {
				bggoodsIds += $(this).val() + ",";
			}
		});
		if (bggoodsIds == "") {
			alert("请选择要批量修改商品~");
			return;
		}
		$('#begintime').val('');
		$('#endtime').val('');
		$('#showtime').val('');
		$('#bgGoodsId').val(bggoodsIds);
		$('#modalEdit').modal();
	});
	/************************************************修改时间 end*************************************************************/
	/************************************************删除活动商品 begin*************************************************************/
	function deleteGoods(goodsId,goodsName){
		var gnl=confirm("确定要删除商品"+goodsName+"?"); 
		if (gnl==false){ 
			return; 
		} 
		$.post("delete", {
			"activityBgGoodsId" : goodsId,
		}, function(response) {
			if (response.code == 0) {
				alert(response.msg);
				table.ajax.reload();
			} else {
				alert(response.msg);
			}
		});
	}
	/************************************************删除活动商品 end*************************************************************/
	/***************************************获得对应的sku信息 begin****************************************************/
var tableTemplateData = {};
	
tableTemplateData.origPriceDouble = function() {
	return this.originPrice/100.0;
};
tableTemplateData.activityPriceDouble = function() {
	return this.activityPrice/100.0;
};


var tableTemplate = document.getElementById('seckillskuTableTemplate').innerHTML;

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

function showSkuInfo(obj, bggoodsId) {
	
	$(obj).attr("onclick", "hideSkuInfo(this," + bggoodsId +")");
	$.post("queryactivitybgsku", {
		"activityBgGoodsId" : bggoodsId,
	}, function(result) {
		var saleInCollegeDiv = fillDiv(bggoodsId, result.data.bgSkuInfo);
		var nextTr = $(obj).parent().parent().next();
		var trClass = nextTr.attr("class");
		if (trClass == undefined || trClass.indexOf("yellowbox") < 0) {
			$(obj).parent().parent().after(saleInCollegeDiv);
		}
	});
}

function hideSkuInfo(obj, bggoodsId,goodsType) {

	$(obj).attr("onclick", "showSkuInfo(this,'" + bggoodsId +"')");
	var nextTr = $(obj).parent().parent().next();
	console.log(nextTr);
	if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
		nextTr.remove();
	}
}
/***************************************获得对应的sku信息 end****************************************************/
</script>