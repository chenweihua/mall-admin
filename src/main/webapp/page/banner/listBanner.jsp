<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<%@ include file="../header.jsp"%>

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
					<i class="glyphicon glyphicon-item"></i> 广告管理
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
							<span class="input-group-addon">平台</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="bannerTypeText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="bannerTypeMenu">
									<li><a href="javascript:void(0);" class="bannerType"
										data-val="0">全部</a></li>
									<li><a href="javascript:void(0);" class="bannerType"
										data-val="1">H5</a></li>
									<li><a href="javascript:void(0);" class="bannerType"
										data-val="2">APP</a></li>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">城市</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="cityText">全部</span>&nbsp;<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" id="cityMenu">
									<li><a href="javascript:void(0);" class="city"
										data-val="0">全部</a></li>
									<c:forEach var="city" items="${cities}">
										<li><a href="javascript:void(0);" class="city"
											data-val="${city.cityId}">${city.cityName}</a></li>
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
									<li><a href="javascript:void(0);" class="college"
										data-val="0">全部</a></li>
									<c:forEach var="college" items="${colleges}">
										<li><a href="javascript:void(0);" class="college"
											data-val="${college.collegeId}">${college.collegeName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">名称</span> <input type="text"
								id="searchStr" class="form-control" placeholder="请填写轮播图名称">
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
							<th style="width: 8%">平台</th>
							<th style="width: 8%">页面</th>
							<th style="width: 10%">广告名称</th>
							<th style="width: 15%">图片</th>
							<th style="width: 8%">权重</th>
							<th style="width: 10%">跳转</th>
							<th style="width: 10%">投放时间</th>
							<th style="width: 10%">结束时间</th>
							<th style="width: 10%">投放区域</th>
							<th style="width: 10%">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
</div>

<%@ include file="../footer.jsp"%>
<%@ include file="../region_tree.jsp" %>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>

<script type="text/javascript">

var table = null;

/* 平台变化 */
	var bannerType = 0;
	 
 	$('#bannerTypeMenu').delegate("li a","click",function() {
 		bannerType= $(this).data("val");
		$('#bannerTypeText').html($(this).text());
		table.ajax.reload();
	});

/* 城市变化 */
	var cityId = 0;
	
 	$('#cityMenu').delegate("li a","click",function() {
		cityId= $(this).data("val");
		$('#cityText').html($(this).text());
		$.post("/banner/city/byCityId", {"cityId": cityId},
			function(json){
			if(json.code == 0){
				collegeList = json.data;
				 if(collegeList.length > 0){
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
 	
	//提交搜索条件
	$('#btnSubmit').click(function() {
		table.ajax.reload();
	});

	
	$(document).ready(function() {
		table = $('#saleinstorage')
				.DataTable(
						{
							ordering : false,
							processing : true,
							serverSide : true,
							searching : false,
							ajax : {
								url :"/banner/query",
								data : function(d) {
									d.searchStr = $("#searchStr").val();
									d.bannerType = bannerType;
									d.cityId = cityId;
									d.collegeId=collegeId;
								},
								dataSrc : function(json) {
									var newData = [];
									var bannerList = json.data;
									var bannerTypeStr = '';
									var banner = null;
									for ( var i = 0, len = bannerList.length; i < len; i++) {
										banner = bannerList[i];
										if(banner != null){
											switch (banner.bannerType){
											case 0:
												bannerTypeStr = '全部';
												break;
											case 1:
												bannerTypeStr = 'H5';
												break;
											case 2:
												bannerTypeStr = 'APP';
												break;
											}
										
											operate = '<a class="blueletter" href="javascript:void(0)" onclick="modifyByBannerId('+banner.bannerId +')">编辑</a> | <a class="blueletter" href="javascript:void(0)" onclick="deleteByBannerId('+banner.bannerId +')">删除</a>';
											newData[newData.length] = [
													bannerTypeStr,                
													banner.connectStr,   
													banner.bannerName,
													'<img style="width:130px" src="'+banner.imageUrl+'"/>',
													banner.weight,
													banner.hrefUrlStr,
													banner.startTimeStr,
													banner.endTimeStr,
													'<a class="blueletter" href="javascript:void(0)" onclick="editRegion('+banner.bannerId +')">查看</a>',
													operate
													];
										}
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

	
	function editRegion(bannerId){
		$('#bgGoodsId4tree').val(bannerId);
		//为范围设值
		$.post("/banner/getRegion/byBannerId",{"bannerId":bannerId}, function(json){
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
	
	function modifyByBannerId(bannerId){
		//为范围设值
		window.location.href='/banner/edit/page?bannerId='+bannerId;
	}
	
	function deleteByBannerId(bannerId){
		//为范围设值
		if(confirm('确定要删除吗？')){
			$.post('/banner/delete',{"bannerId":bannerId},function(json){
				if(json.code != 0){
					alert(json.msg);
				}else{
					table.ajax.reload();
				}
			});
		}
	}
	
	$('#treeSubmit').click(function() {
		var bannerId = $('#bgGoodsId4tree').val();
		var treeObj=$.fn.zTree.getZTreeObj("selectTree");
		var nodes=treeObj.getNodes();
		var info = JSON.stringify(nodes);
		$.post("/banner/setRegion/byBannerId",{"bannerId":bannerId,"treeInfo": info}, function(json){
			if (json.code != 0) {
				alert(json.msg);
			} else {
				alert('范围设置成功');
				$('#treeEdit').modal('hide');
			}
		});
	});
	
	
	

	function formatDate(d) {  
		  var D=['00','01','02','03','04','05','06','07','08','09']  
		  with (d || new Date) return [  
		    [getFullYear(), D[getMonth()+1]||getMonth()+1, D[getDate()]||getDate()].join('-'),  
		    [D[getHours()]||getHours(), D[getMinutes()]||getMinutes(), D[getSeconds()]||getSeconds()].join(':')  
		  ].join(' ');  
		}  

</script>
