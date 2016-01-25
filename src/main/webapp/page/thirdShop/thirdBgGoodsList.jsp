<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<%@ include file="../header.jsp"%>
<link href='/css/multilevelnavbar.css' rel="stylesheet">


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 库存管理
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
							<span class="input-group-addon">货品名称/条码</span> <input type="text"
								id="bgGoodsName" class="form-control" placeholder="请输入名称">
						</div>
					</div>
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">商品类别</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="categoryText">全部</span>&nbsp;<span
										class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="ulcategory">
									<c:forEach var="category" items="${categoryList}">
										<li><a href="javascript:void(0);" class="category"
											data-val="${category.propertyCategoryId}">${category.propertyCategoryName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">店铺</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="storageText">${defaultShopProfile.shopName}</span>&nbsp;<span
										class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="ulstorage">
									<c:forEach var="shopProfile" items="${shopProfileList}">
										<li><a href="javascript:void(0);" class="storage"
											data-val="${shopProfile.storageId}">${shopProfile.shopName}</a></li>
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
									<span id="bgGoodsStatusText">全部</span>&nbsp;<span
										class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="ulbgGoodsStatus">
										<li><a href="javascript:void(0);" class="bgGoodsStatus"
											data-val="-1">全部</a></li>
										<li><a href="javascript:void(0);" class="bgGoodsStatus"
											data-val="1">关闭</a></li>
										<li><a href="javascript:void(0);" class="bgGoodsStatus"
											data-val="2">开启</a></li>
								</ul>
							</div>
						</div>
					</div>

					<div class="col-lg-3">
						<div class="input-group" style="float: right; padding-right: 50px">
							<button type="button" id="btnSubmit" class="btn btn-default">查询</button>
						</div>
					</div>
				</div>
			</div>
			<div class="box-content">
				<button id="addBgGoods">添加商品</button>
				<!-- <div class="row col-lg-2"><a href="javascript:void(0)" onclick="addSubjectGoods()">添加商品</a></div> -->
				<table id="bgGoodsTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>ID</th>
							<th>名称</th>
							<!-- <th>编码</th> -->
							<th>类别</th>
							<!-- <th>类型</th> -->
							<th>库存</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">
	var goodsTypeMap = {
	    1:"单品",
	    2:"组合品",
	    3:"聚合品"
	};
	var goodsStatusMap = {
	    1:"关闭",
	    2:"开启"
	};
	var table = null;
	var categoryId = -1;
	var bgGoodsStatus = -1;
	var storageId = ${defaultShopProfile.storageId};

	$('.category').click(function() {
		categoryId = $(this).data("val");
		$('#categoryText').text($(this).text());
	});
	
	$('.storage').click(function() {
		storageId = $(this).data("val");
		$('#storageText').text($(this).text());
	});
	$('.bgGoodsStatus').click(function() {
		isOpen = $(this).data("val");
		$('#bgGoodsStatusTest').text($(this).text());
	});
	
	$('#btnSubmit').click(function() {
		table.ajax.reload();
	});
	
	//注册查询事件
	$('#btnSubmit').click(function() {
		table.ajax.reload();
	});

	$(document).ready(function() {
		table = $("#bgGoodsTable").DataTable({
			ordering : false,
			processing : true,
			serverSide : true,
			searching : false,
			bFilter : false,
			ajax : {
				url : "/bgGoods/third/query",
				async : false,
				data : function(d) {
					d.bgGoodsName = $("#bgGoodsName").val();
					d.storageId = storageId;
					d.categoryId = categoryId;
					d.bgGoodsStatus = bgGoodsStatus;
				},
				dataSrc : function(json) {
					var newData = [];
					for (var i = 0, len = json.data.length; i < len; i++) {
						var goodsInfo = json.data[i];
						var goodsStatusStr = '';
						if(goodsInfo.bgGoodsStatus == 2){
							goodsStatusStr = '开启 <a class="blueletter" href="javascript:void(0)" onclick="modifyGoodsStatus('+goodsInfo.bgGoodsId+',1)">[关闭]</a>';
						}else if(goodsInfo.bgGoodsStatus == 1){
							goodsStatusStr = '关闭 <a class="blueletter" href="javascript:void(0)" onclick="modifyGoodsStatus('+goodsInfo.bgGoodsId+',2)">[开启]</a>';
						}
						newData[newData.length] = [
                            goodsInfo.bgGoodsId,
							goodsInfo.bgGoodsName,                
							/* goodsInfo.wmsGoodsGbm == null ? "" : goodsInfo.wmsGoodsGbm, */                
							goodsInfo.categoryName == null ? "" : goodsInfo.categoryName,
							/* goodsTypeMap[goodsInfo.goodsType], */
							goodsInfo.stock,
							goodsStatusStr,
							//'<a href="javascript:void(0)" onclick="editBgGoods('+goodsInfo.bgGoodsId+')">编辑</a> | <a href="javascript:void(0)" onclick="deleteBgGoods('+goodsInfo.bgGoodsId+')">删除</a>' 
							'<a href="javascript:void(0)" onclick="editBgGoods('+goodsInfo.bgGoodsId+')">编辑</a>' 
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
				sSearch : "",
				sZeroRecords : "暂无数据",
				sProcessing : "正在处理...",
				sEmptyTable : "暂无数据",
				sInfo : "_START_ - _END_ (共_TOTAL_条)",
				sInfoFiltered : "",
				oPaginate : {
					sFirst : "第一页",
					sLast : "最后一页",
					sNext : "下一页",
					sPrevious : "前一页"
				}
			}
		});
	});
	
	$('#addBgGoods').click(function(){
		if(storageId == -1){
			alert("请先选中店铺，再添加");
		}else{
			window.location.href = "/bgGoods/third/propertySelect?storageId="+storageId;
		}
	});
	
	function editBgGoods(bgGoodsId){
		window.location.href = "/bgGoods/third/editPage?bgGoodsId="+bgGoodsId;
	};
	
	function modifyGoodsStatus(id,status){
		$.post("/bgGoods/third/editBgGoodsStatus", {"bgGoodsId":id,"goodsStatus":status}, function(ret) {
			if (ret.code == 0) {
				alert(ret.msg);
				table.ajax.reload();
			} else {
				alert(ret.msg);
			}
		});
	}
</script>