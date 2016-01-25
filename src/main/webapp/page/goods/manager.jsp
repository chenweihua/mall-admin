<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<style>
ul{
	list-style: none;
}

ul li{
	list-style: none;
}
</style>
<%@ include file="../header.jsp"%>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 商品管理
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
							<label id="isAdmin" style="display: none">${isAdmin }</label>
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
					class="table table-striped table-bordered bootstrap-datatable datatable">
					<thead>
						<tr>
							<th style="width:5%">商品ID</th>
							<th style="width:10%">商品名称</th>
							<th style="width:15%">描述</th>
							<th style="width:10%">类目</th>
							<th style="width:10%">售卖规格</th>
							<th style="width:20%">图片</th>
							<th style="width:10%">类型</th>
							<th style="width:10%">售卖区域</th>
							<th style="width:10%">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
</div>

<%@ include file="../footer.jsp"%>
<%@ include file="region_tree.jsp" %>
<%@ include file="regionPrice4Pop.jsp" %>
<%@ include file="add2Activity.jsp" %>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>

<script type="text/javascript">

var table = null;

/* 仓库类型变化 */	
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
		//table.ajax.reload();
	});

/* 仓库变化 */
	var storageId = ${defaultStorage.storageId};
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

/* 学校变化 */
 	var collegeId=0;
	$('#collegeMenu').delegate("li a","click",function() {
		collegeId= $(this).data("val");
		$('#collegeText').html($(this).text());
		table.ajax.reload();
	});
	
/* 类目变化 */
	var categoryId = ${defaultCategory.categoryId};
 	$('#categoryMenu').delegate("li a","click",function() {
 		categoryId= $(this).data("val");
		$('#categoryText').html($(this).text());
		table.ajax.reload();
	});
 	
	//提交搜索条件
	$('#btnSubmit').click(function() {
		table.ajax.reload();
	});
	
	$(document).ready(function() {
		var sid = $("#dstorageId").val();
		table = $('#saleinstorage')
				.DataTable(
						{
							ordering : false,
							processing : true,
							serverSide : true,
							searching : false,
							ajax : {
								url :"/goods/query",
								data : function(d) {
									d.stoageflag = storageTypeFlag;
									d.goodsName = $("#searchStr").val();
									d.categoryId = categoryId;
									d.storageId = storageId;
									d.collegeId=collegeId;
								},
								dataSrc : function(json) {
									var newData = [];
									var seq = json.start+1;
									var goodTypeStr = '';
									var operate = '';
									for ( var i = 0, len = json.data.length; i < len; i++) {
										goodsInfo = json.data[i];
										operate = '<a class="blueletter" href="javascript:void(0)" onclick="modifyBgGoods('+goodsInfo.bgGoodsId +','+goodsInfo.goodsType+')">修改</a>';
										if(goodsInfo.goodsType == 1){
											goodTypeStr = '单品';
											operate = '<a class="blueletter" href="javascript:void(0)" onclick="modifyBgGoods('+goodsInfo.bgGoodsId +','+goodsInfo.goodsType+')">修改</a> | <a class="blueletter" href="javascript:void(0)" onclick="editPrice('+goodsInfo.bgGoodsId +')">价格</a>';
										}else if(goodsInfo.goodsType == 2){
											goodTypeStr = '组合单品';
											operate = '<a class="blueletter" href="javascript:void(0)" onclick="modifyBgGoods('+goodsInfo.bgGoodsId +','+goodsInfo.goodsType+')">修改</a> | <a class="blueletter" href="javascript:void(0)" onclick="editPrice('+goodsInfo.bgGoodsId +')">价格</a>';
										}else if(goodsInfo.goodsType == 3){
											goodTypeStr = '聚合品';
											operate = '<a class="blueletter" href="javascript:void(0)" onclick="modifyBgGoods('+goodsInfo.bgGoodsId +','+goodsInfo.goodsType+')">修改</a> | <a class="blueletter" href="javascript:void(0)" onclick="add2Activity('+goodsInfo.bgGoodsId +')">添加至活动</a>';
										}
										newData[newData.length] = [
										        goodsInfo.bgGoodsId,
												goodsInfo.bgGoodsName,                
												goodsInfo.description,              
												goodsInfo.categoryName,
												goodsInfo.unit,
												'<img style="width:200px" src="'+goodsInfo.imageUrl+'">',
												goodTypeStr,
												'<a class="blueletter" href="javascript:void(0)" onclick="editRegion('+goodsInfo.bgGoodsId +')">查看</a>',
												operate
												];
									}
									json.data = newData;
									return newData;
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
	
	function editRegion(bgGoodsId){
		$('#bgGoodsId4tree').val(bgGoodsId);
		//为范围设值
		$.post("/goods/region/getRegion/byBgGoogdsId",{"bgGoodsId":bgGoodsId}, function(json){
			if (json.code != "0") {
				alert(json.msg);
			} else {
				//alert(json.msg);
				var zNodes = json.data;
				$.fn.zTree.init($("#selectTree"), setting, zNodes);
				$('#treeEdit').modal('show');
			}
		});
	}
	function modifyBgGoods(bgGoodsId,goodsType){
		//为范围设值
		if(goodsType == 3){
			window.location.href='/goods/multiProduct?bgGoodsId='+bgGoodsId;
		}else{
			window.location.href='/goods/singleProduct?bgGoodsId='+bgGoodsId;
		}
	}
	function editPrice(bgGoodsId){
		$('#bgGoodsId').val(bgGoodsId);
		//为价格设值
		rdcTablePop.ajax.url("/goods/price/getRdcStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
		ldcTablePop.ajax.url("/goods/price/getLdcCity4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
		vmTablePop.ajax.url("/goods/price/getVmStorage4Price/byBgGoodsId?bgGoodsId="+bgGoodsId).load();
		$('#priceEditPop').modal('show');
	}
	
	$('#treeSubmit').click(function() {
		var bgGoodsId = $('#bgGoodsId4tree').val();
		var treeObj=$.fn.zTree.getZTreeObj("selectTree");
		var nodes=treeObj.getNodes();
		var info = JSON.stringify(nodes);
		$.post("/goods/region/setRegion/byBgGoodsId",{"bgGoodsId":bgGoodsId,"treeInfo": info}, function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				alert('范围设置成功');
				$('#treeEdit').modal('hide');
			}
		});
	});
	
	function add2Activity(bgGoodsId){
		$('#bgGoodsId4Activity').val(bgGoodsId);
		//默认日常活动
		$.post('/activity4MultiGoods/getActivities/byActivityType',{"activityType":1},function(json){
			if(json.code == 0){
				var activities = json.data;
				var activity = null;
				var str = '';
				for(var i = 0;i< activities.length;i++){
					activity = activities[i];
					if(activity != null){
						str += '<option value="'+activity.activityId+'">'+activity.activityName+'</option>';
					}
				}
				$('#activityId option').remove();
				$('#activityId').append(str);
			}else{
				alert(json.msg);
			}
		},'json');
		$('#add2ActivityEdit').modal('show');
	}
</script>
