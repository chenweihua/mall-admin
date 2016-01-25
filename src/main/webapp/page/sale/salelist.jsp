<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<%@ include file="../header.jsp"%>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 商品售卖
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

					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">仓库类型</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="storageTypeText">${defaultStorageType.typeName}</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="storageTypeMenu">
									<c:forEach items="${storageTypeList}" var="storageType">
										<li><a href="javascript:void(0);" class="storageType"
										data-val="${storageType.typeId }">${storageType.typeName }</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">仓库</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="storageText">${defaultStorage.storageName}</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="storageMenu">
									<c:forEach var="storage" items="${storageList}">
										<li><a href="javascript:void(0);" class="storage"
											data-val="${storage.storageId}">${storage.storageName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">学校</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="collegeText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="collegeMenu">
									<li><a href="javascript:void(0);" class="status"
										data-val="0">全部</a></li>
									<c:forEach var="college" items="${collegeList}">
										<li><a href="javascript:void(0);" class="college"
											data-val="${college.collegeId}">${college.collegeName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">类目</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="categoryText">${defaultCategory.categoryName }</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="categoryMenu">
									<c:forEach var="category" items="${categoryList}">
										<li><a href="javascript:void(0);" class="category"
											data-val="${category.categoryId}">${category.categoryName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">状态</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="statusText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="javascript:void(0);" class="status"
										data-val="0">全部</a></li>
									<li><a href="javascript:void(0);" class="status"
										data-val="1">待售</a></li>
									<li><a href="javascript:void(0);" class="status"
										data-val="2">在售</a></li>
									<li><a href="javascript:void(0);" class="status"
										data-val="3">售罄</a></li>
								</ul>
							</div>
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
				<table id="saleinstorage"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th><input type="checkBox"  id="checkAll">序号</th>
							<th>商品名称</th>
							<th>商品描述</th>
							<th>商品类型</th>
							<th>仓库</th>
							<th>类目</th>
							<th>权重</th>
							<th>限售</th>
							<th>售卖状态</th>
							<th>库存</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
	<button type="button" class="btn btn-primary btn-sm" id="addButton" onclick="modifystatus(2)">批量在售</button>
	<button type="button" class="btn btn-primary btn-sm" id="addButton" onclick="modifystatus(1)">批量待售</button>
	<button type="button" class="btn btn-primary btn-sm" id="addButton" onclick="modifystatus(3)">批量售罄</button>
</div>



<%@ include file="editgoods.jsp"%>
<%@ include file="../footer.jsp"%>
<%@ include file="../qrCode.jsp"%>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>


<script id="saleInCollegeTableTemplate" type="text/template">
		<p>商品信息：</p>
		<table border='1'><tr><td>商品名称</td><td>描述</td><td>单位</td></tr>
       		 {{#saleSkuInfo}}
            	<tr>	
					<td> {{bg_goods_name}}</td>
					<td> {{description}} </td>
					<td> {{unit}} </td>
				</tr>
       		{{/saleSkuInfo}}
		</table>
		<br>
		<p>学校信息：</p>
		<table border='1'><tr><td>名称</td><td>学校</td><td>权重</td><td>限售</td><td>状态</td><td>操作</td></tr>
        {{#saleInCollege}}
            <tr>	
				<td> {{goods_name}}</td>
				<td> {{college_name}} </td>
				<td> {{weight}} </td>
				<td> {{stockStr}} </td>
				<td> {{statusStr}} </td>
				<td>
					<a href="javascript:editSaleGoods(1,{{goods_id}},{{weight}},{{stock}},{{status}},{{storageId}});">修改</a>
					{{#allowQrCode}}&nbsp;&nbsp;<a  href="javascript:copyQrCode({{goods_id}},{{college_id}},'{{college_name}}')">二维码信息</a>{{/allowQrCode}}
				</td>
			</tr>
        {{/saleInCollege}}
	</table>
</script>

<script type="text/javascript">
	var isSecKill = $
	{
		isSecKill
	};
	var sellers = $
	{
		sellers
	};
	var categories = $
	{
		categories
	};
	var colleges = $
	{
		colleges
	};

	var status = 0;
	$('.status').click(function() {
		status = $(this).data("val");
		$('#statusText').html($(this).text());
		table.ajax.reload();
	});
	
	$.ajaxSetup({   
        async : false  
    }); 

	var storageTypeFlag = ${defaultStorageType.typeId};
	$('.storageType').click(function() {
		storageTypeFlag = $(this).data("val");
		$('#storageTypeText').html($(this).text());
		//获得RDC类型的仓或LDC类型的仓
		$.post("/user/getCategoryAndStorage", { "stoagetypeflag": storageTypeFlag },
				   function(result){
				     if(result.code==0){
				    	 storageList = result.data.storage;
				    	 categoryList = result.data.category;
				    	 collegeList = result.data.college;
				    	 if(storageList.length>0){
				    		 var storageInfo = "";
				    		 for ( var i = 0, len =storageList.length; i < len; i++) {
				    			 	
									storage = storageList[i];
									storageInfo+='<li><a href="javascript:void(0);" class="storage" data-val="'+storage.storageId+'">'+storage.storageName+'</a></li>';
									if(i==0){
				    					storageId= storage.storageId;
				    					$('#storageText').html(storage.storageName);
				    			 	}
				    		 }
				    		 $("#storageMenu").html("");
				    		 $("#storageMenu").html(storageInfo);
				    	 }else{
				    		 $("#storageMenu").html("");
				    		 $("#storageMenu").html('<li><a href="javascript:void(0);" class="storage" data-val="0">无</a></li>');
		    					storageId= 0;
		    					$('#storageText').html('无');
				    	 }
				    	 
				    	 if(categoryList.length>0){
				    		 var categoryInfo = "";
				    		 for ( var i = 0, len =categoryList.length; i < len; i++) {
									category = categoryList[i];
									categoryInfo+='<li><a href="javascript:void(0);" class="category" data-val="'+category.categoryId+'">'+category.categoryName+'</a></li>';
									if(i==0){
								 		categoryId= category.categoryId;
										$('#categoryText').html(category.categoryName);
									}
				    		 }
				    		 $("#categoryMenu").html("");
				    		 $("#categoryMenu").html(categoryInfo);
				    	 }else{
				    		 $("#categoryMenu").html("");
				    		 $("#categoryMenu").html('<li><a href="javascript:void(0);" class="category" data-val="0">无</a></li>');
						 	 categoryId= 0;
							 $('#categoryText').html('无');
				    	 }
				    	 
				    	 if(collegeList.length>0){
				    		 var collegeInfo = '<li><a href="javascript:void(0);" class="college" data-val="0">全部</a></li>';
				    		 for ( var i = 0, len =collegeList.length; i < len; i++) {
									college = collegeList[i];
									collegeInfo+='<li><a href="javascript:void(0);" class="college" data-val="'+college.collegeId+'">'+college.collegeName+'</a></li>';
									if(i==0){
								 		collegeId= 0;
										$('#collegeText').html('全部');
									}
				    		 }
				    		 $("#collegeMenu").html("");
				    		 $("#collegeMenu").html(collegeInfo);
				    	 }else{
				    		 $("#collegeMenu").html("");
				    		 $("#collegeMenu").html('<li><a href="javascript:void(0);" class="college" data-val="0">全部</a></li>');
				    		 collegeId= 0;
								$('#collegeText').html('全部');
				    	 }
				    	 
				     }else{
				    	 alert("没有找到对应的仓和类目");
				     }
				   }, "json");
		
		table.ajax.reload();
		
	});

	var categoryId = ${defaultCategory.categoryId};
/* 	 $('.category').click(function() {
		categoryId = $(this).data("val");
		$('#categoryText').html($(this).text());
	});  */
	 
 	$('#categoryMenu').delegate("li a","click",function() {
 		categoryId= $(this).data("val");
		$('#categoryText').html($(this).text());
		table.ajax.reload();
	});

	var storageId = ${defaultStorage.storageId};
	/* $('.storage').click(function() {
		storageId= $(this).data("val");
		$('#storageText').html($(this).text());
	});
 */
 	$('#storageMenu').delegate("li a","click",function() {
		storageId= $(this).data("val");
		$('#storageText').html($(this).text());
		$.post("/college/getCollegeByStorageId", { "stoagetypeflag": storageTypeFlag,"storageId":storageId },
			function(result){
			if(result.code==0){
				collegeList = result.data.college;
				 if(collegeList.length>0){
		    		 var collegeInfo = '<li><a href="javascript:void(0);" class="college" data-val="0">全部</a></li>';
		    		 for ( var i = 0, len =collegeList.length; i < len; i++) {
							college = collegeList[i];
							collegeInfo+='<li><a href="javascript:void(0);" class="college" data-val="'+college.collegeId+'">'+college.collegeName+'</a></li>';
							if(i==0){
						 		collegeId= 0;
								$('#collegeText').html('全部');
							}
		    		 }
		    		 $("#collegeMenu").html("");
		    		 $("#collegeMenu").html(collegeInfo);
		    	 }else{
		    		 $("#collegeMenu").html("");
		    		 $("#collegeMenu").html('<li><a href="javascript:void(0);" class="college" data-val="0">全部</a></li>');
		    		 collegeId= 0;
					$('#collegeText').html('全部');
		    	 }
			}else{
				 $("#collegeMenu").html("");
	    		 $("#collegeMenu").html('<li><a href="javascript:void(0);" class="college" data-val="0">全部</a></li>');
	    		 collegeId= 0;
				$('#collegeText').html('全部');
			}
		}, "json");
		table.ajax.reload();
	});
	
 	var collegeId=0;
	$('#collegeMenu').delegate("li a","click",function() {
		collegeId= $(this).data("val");
		$('#collegeText').html($(this).text());
		table.ajax.reload();
	});

	//提交搜索条件
	$('#btnSubmit').click(function() {
		table.ajax.reload();
	});

	
	$(document)
			.ready(
					function() {
						var isIndx = {
							0 : "其它",
							1 : "首页"
						};
						var sid = $("#dstorageId").val();
						//datatable

						table = $('#saleinstorage')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											searching : false,
											ajax : {
												url :"query",
												data : function(d) {
													d.stoageflag = storageTypeFlag;
													d.goodsName = $(
															"#searchStr").val();
													d.categoryId = categoryId;
													d.storageId = storageId;
													d.status = status;
													d.collegeId=collegeId;
												},
												dataSrc : function(json) {
													var newData = [];
													var seq = json.start+1;
													for ( var i = 0, len = json.data.length; i < len; i++) {
														saleinfo = json.data[i];
														var nameLink = '<a class="blueletter" href="javascript:void(0)" onclick="showCollegeGoods(this,'+saleinfo.bg_goods_id + ','+saleinfo.goods_type+','+saleinfo.storage_id+')">'+ saleinfo.bg_goods_name+'</a>';
														newData[newData.length] = [
																'<input type="checkBox" name="bggoodsId" value="'+saleinfo.bg_goods_id+'_'+saleinfo.storage_id+'">'+(seq+i),
																nameLink,
																saleinfo.bg_goods_describ,
																saleinfo.goods_type_str,
																saleinfo.storage_name,
																saleinfo.category_name,
																saleinfo.weight,
																saleinfo.stockStr,
																saleinfo.statusStr,
																saleinfo.storageStock,
																 '<a class="blueletter" href="javascript:void(0)" onclick="editSaleGoods(2,'+saleinfo.bg_goods_id +','+saleinfo.weight+','+saleinfo.stock+','+saleinfo.status+','+saleinfo.storage_id+')">编辑</a>'];
													}
													json.data = newData;
													return newData;
												}
											},
											//         sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
											sDom : "<'row'<'col-md-4'l><'col-md-4'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
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

	var tableTemplateData = {};
/* 	tableTemplateData.priceDouble = function() {
		return this.price / 100.0;
	};
	tableTemplateData.origPriceDouble = function() {
		return this.origPrice / 100.0;
	};

	tableTemplateData.stockNum = function() {
		if (this.stock >= 999999) {
			return "不限制";
		}
		return this.stock;
	};
	tableTemplateData.statusName = function() {
		return statusMap[this.status];
	};
	tableTemplateData.collegeName = function() {
		return matchCollege(this.collegeId);
	};
	tableTemplateData.startTime = function() {
		var startTime = new Date();
		startTime.setTime(this.secKillStartTime);
		return startTime.Format("yyyy-MM-dd hh:mm:ss");
	};
	tableTemplateData.endTime = function() {
		var endTime = new Date();
		endTime.setTime(this.secKillEndTime);
		return endTime.Format("yyyy-MM-dd hh:mm:ss");
	}; */
	var tableTemplate = document.getElementById('saleInCollegeTableTemplate').innerHTML;

	function fillTable(saleInColleges,saleSkuInfo,goodsType) {
		tableTemplateData.saleInCollege = saleInColleges;
		tableTemplateData.saleSkuInfo = saleSkuInfo;
		/*
		if(goodsType == 1) {  //mustatch不直接支持if条件判断，这里做转换，通过allowQrCode属性是否存在来处理
			tableTemplateData.allowQrCode = "allowQrCode";
		} else {
			delete tableTemplateData.allowQrCode;
		}
		*/
		//需求一开始只支持单品，后需求改为都支持
		tableTemplateData.allowQrCode = "allowQrCode";
		
		return Mustache.render(tableTemplate, tableTemplateData);
	}

	function fillDiv(bggoodsId, saleInColleges,saleSkuInfo, goodsType) {
		var saleInCollegeTable = null;
		if (saleInColleges == null || saleInColleges.length == 0) {
			saleInCollegeTable = "没有学校";
		} else {
			saleInCollegeTable = fillTable(saleInColleges,saleSkuInfo,goodsType);
		}
		var divStr = '<tr class="yellowbox padding10"><td colspan="14" align="center">'
				+ '<table>'
				+ '<tr><td></td></tr>'
				+ '<tr><td id=block' + bggoodsId + '>'
				+ saleInCollegeTable
				+ '</td></tr>';

		return divStr;
	}

	function showCollegeGoods(obj, bggoodsId,goodsType,storageId_temp) {
		
		$(obj).attr("onclick", "hideCollegeGoods(this," + bggoodsId +","+goodsType+','+storageId_temp+ ")");
		$.post("getcollegegoods", {
			"bggoodsId" : bggoodsId,
			"collegeId":collegeId,
			"storageflag":storageTypeFlag,
			"storageId":storageId_temp,
			"goodsType":goodsType,
		}, function(result) {
			var saleInCollegeDiv = fillDiv(bggoodsId, result.data.saleInCollege,result.data.saleSkuInfo,goodsType);

			var nextTr = $(obj).parent().parent().next();

			var trClass = nextTr.attr("class");
			if (trClass == undefined || trClass.indexOf("yellowbox") < 0) {
				$(obj).parent().parent().after(saleInCollegeDiv);
			}
			
			//$(window).resize();
		});
	}
	


	function hideCollegeGoods(obj, bggoodsId,goodsType,storageId_temp) {
		$(obj).attr("onclick", "showCollegeGoods(this,'" + bggoodsId +"','"+goodsType+"','"+storageId_temp+ "')");
		var nextTr = $(obj).parent().parent().next();
		console.log(nextTr);
		if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
			nextTr.remove();
		}
	}
	
	function editSaleGoods(modifytype,goodsid,weight,stock,status,storageId_temp){
		$("#goodsId").val(goodsid);
		$("#modifytype").val(modifytype);
		$("#storageId").val(storageId_temp);
		$("#storageType").val(storageTypeFlag);
		$("#collegeId").val(collegeId);

		$("#weight").val(weight);
		$("#status").val(status);
		if(stock>999999){
			$("#stock").val(9999999);
			$("#stock").prop("readonly",true);
	    	$("#limitstock").prop("checked",true);
		}else{
			$("#stock").val(stock);
			$("#stock").prop("readonly",false);
	    	$("#limitstock").prop("checked",false);
		}
		$('#modalEdit').modal();
	}
	
	//全选
	$("#checkAll").click(function(){  
		if($("#checkAll").prop("checked")==true){
			 $("input[name='bggoodsId']").each(function(){
				   $(this).prop("checked",true);
			}); 
			//alert();
		}else{
			$("input[name='bggoodsId']").each(function(){
				   $(this).prop("checked",false);
			});
		}
	    
	 });
	

	function modifystatus(status){
		//获取勾选的资产的ID集合
		 var bggoodsIds="";  
	       $("input[name=bggoodsId]").each(function() {  
	           if ($(this).prop("checked")) {  
	        	   bggoodsIds += $(this).val()+",";  
	           }  
	       }); 
	       if(bggoodsIds==""){
	    	   alert("请选择要批量修改商品~");
	    	   return;
	       }
//		        alert("assestsIds:"+assestsIds);
	       
	       $.post("batchmodify",
	    		   {'bggoodsIds':bggoodsIds,
	    	   		"status":status,
	    	   		"storageType":storageTypeFlag
	    	   		},
	    		   function(ret){
					alert(ret.msg);
					if(ret.code==0){
						table.ajax.reload(null,false);
					}
					$("input[id='checkAll']").prop("checked",false);
	       },"json");
	//清空选择
	      
	}
	

	function copyQrCode(goodsId, collegeId, collegeName) {
		setQrUrl(goodsId,0,1,collegeId,collegeName);
		$("#modalQr").modal("show");
		
		//setTimeout(registerCopy,1000);  //延迟执行1秒，立即执行方法时modal尚未显现
	}
	
	
</script>
