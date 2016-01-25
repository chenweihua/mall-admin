<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../../header.jsp"%>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-couponRule"></i>优惠活动列表
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
			              <span class="input-group-addon">开始时间</span> <input type="text"
			                id="search_start_time" class="form-control" value="${startDate}" readonly>
			            </div>
			          </div>
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">结束时间</span> <input type="text"
			                id="search_end_time" class="form-control" value="${endDate}" readonly>
			            </div>
			          </div>
			          
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">活动名称</span> <input type="text"
			                id="activityName" class="form-control"  >
			            </div>
			          </div>
		          </div>
		          
		          <div class="row">
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动类型</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnActivityTypeText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnType"
		                        data-val="-1">全部</a></li>  
		                   <c:forEach items="${ActivityType}" var="obj">
 								<li><a href="javascript:void(0);" class="btnType"
		                       		 data-val="${obj.code}">${obj.msg}</a></li>
				      	    </c:forEach>
		                </ul>
		              </div>
		            </div>
		          </div>
		
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动平台</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnActivityPlatformText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnPlatformType"
		                        data-val="-1">全部</a></li>
		                     <c:forEach items="${ActivityPlatform}" var="obj">
 								<li><a href="javascript:void(0);" class="btnPlatformType"
		                       		 data-val="${obj.code}">${obj.msg}</a></li>
				      	     </c:forEach>
		                </ul>
		              </div>
		            </div>
		          </div>
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动状态</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnStatusText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="-1">全部</a></li>
		                        <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="1">开启</a></li>
		                    <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="0">关闭</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		          </div>
		          
		          <div class="row">
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动城市</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnCityText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="ccityList">
		                  	<li><a href="javascript:void(0);" class="btnCity"
		                        data-val="-1" onclick="changeCity(this)">全部</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动学校</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnCollegeText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="CollegeList">
		                	<li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="-1" onclick="changeCollege(this)">全部</a></li>
		                  	<!-- <li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="0">北大</a></li>
		                    <li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="1">清华</a></li>
		                    <li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="2">北邮</a></li> -->
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		           <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动方案</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnActivityProgramText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnProgramType"
		                        data-val="-1">全部</a></li>
		                     <c:forEach items="${ActivityPlan}" var="obj">
 								<li><a href="javascript:void(0);" class="btnProgramType"
		                       		 data-val="${obj.code}">${obj.msg}</a></li>
				      	     </c:forEach>
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
				<br>
		<table id="couponRuleList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>活动名称</th>
							<th>活动类型</th>
							<th>活动平台</th>
							<th>前端显示</th>
							<th>图片</th>
							<th>活动时间</th>
							<th>是否显示</th>
							<th>权重</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
		        </div>
				
				
			</div>
		</div>
	</div>
	<!--/span-->
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>
</div>
<!--/row-->


<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="form1">
							<input type="hidden" name="activityId" id="activityId" />
							<input type="hidden" name="addType" id="addType" value="0" />
							<input type="hidden" name="addClickType" id="addClickType" value="0" />
							<input type="hidden" name="addPlatformType" id="addPlatformType" value="0" />
							<input type="hidden" name="addProgramType" id="addProgramType" value="0" />
							<input type="hidden" name="addOpenType" id="addOpenType" value="1" />
							<input type="hidden" name="addShowType" id="addShowType" value="0" />
							
				            <div class="form-group">
				              <label for="exampleInputEmail1">活动类型</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown" id="btnTypeSelect">
				                  <span id="btnActivityTypeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                   <c:forEach items="${ActivityType}" var="obj">
		 								<li><a href="javascript:void(0);" class="btnType-add"
				                       		 data-val="${obj.code}" onclick="selectActivityDivShow(${obj.code})">${obj.msg}</a></li>
						      	    </c:forEach>
				                </ul>
				              </div>
				            </div>
				            <div class="form-group">
				              <label for="exampleInputEmail1">点击类型</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnClickTypeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="btnClickType-add"
				                      data-val="0">新页面</a></li>
<!-- 				                  <li><a href="javascript:void(0);" class="btnClickType-add"
				                      data-val="1">hash change</a></li> -->
				                  <li><a href="javascript:void(0);" class="btnClickType-add"
				                      data-val="2">浮层</a></li>
				                </ul>
				              </div>
				            </div>
							
							<div class="form-group">
				              <label for="exampleInputEmail1">活动平台</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnActivityPlatformText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                    <c:forEach items="${ActivityPlatform}" var="obj">
		 								<li><a href="javascript:void(0);" class="btnPlatformType-add"
				                       		 data-val="${obj.code}">${obj.msg}</a></li>
						      	    </c:forEach>
				                </ul>
				              </div>
				            </div>
				            
				            <div class="form-group">
				              <label for="exampleInputEmail1">活动方案</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnActivityProgramTypeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                    <c:forEach items="${ActivityPlan}" var="obj">
		 								<li><a href="javascript:void(0);" class="btnProgramType-add"
				                       		 data-val="${obj.code}">${obj.msg}</a></li>
						      	    </c:forEach>
				                </ul>
				              </div>
				            </div>
							
							<div class="form-group">
				              <label for="exampleInputEmail1">是否显示</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnShowTypeText-add">是</span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="btnShowType-add"
				                      data-val="1">是</a></li>
				                  <li><a href="javascript:void(0);" class="btnShowType-add"
				                      data-val="0">否</a></li>
				                </ul>
				              </div>
				            </div>
							
							<div class="form-group">
								<div id="selectActivityDiv">
									<label for="exampleInputPassword1">优惠批次号</label> 
									<input class="form-control" name="batchIds" id="batchIds" placeholder="多个批次号用英文逗号分割">	
								</div>
								<div id="selectActivityReductionDiv">
									<label for="exampleInputPassword1">首减优惠金额</label> 
									<input class="form-control" name="value" id="value" placeholder="首单用户减去优惠金额，单位元">	
								</div>
							</div>
							
							<div class="form-group">
								<label for="exampleInputPassword1">活动名称</label> <input
									class="form-control" name="bgName" id="bgName"
									placeholder="活动名称">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">前端显示活动名称</label> <input
									class="form-control" name="name" id="name"
									placeholder="前端显示活动名称">
							</div>
							<!-- <div class="form-group">
								<label for="exampleInputEmail1">活动图片</label> <input type="text"
									class="form-control" name="img" id="img"
									placeholder="活动图片">
									<a href="" target="_blank" id="editImgUrl" style="display: none;">查看</a>
									 <img id="uploadPreview2" src="" style="width:300px" alt="未激活图片"/><br />
									<input id="uploadImage2" type="file" name="p1" onchange="PreviewImage(2);" />
									<input type="hidden" id="imageUrl2" name="imageUrl2" value="123123">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">权重</label> <input
									class="form-control" name="weight" id="weight"
									placeholder="权重">
							</div> -->
							<div class="form-group">
				              <label for="exampleInputEmail1">活动时间</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnActivityTimeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <!-- <li><a href="javascript:void(0);" class="btnOpenType-add"
				                      data-val="0" onclick="selectTimeDivShow(0)">手动控制</a></li> -->
				                  <li><a href="javascript:void(0);" class="btnOpenType-add"
				                      data-val="1" onclick="selectTimeDivShow(1)">自动控制</a></li>
				                </ul>
				              </div>
				            </div>
							<div class="form-group">
								<div id="selectTimeDiv">
									<div class="input-group">
						              <span class="input-group-addon">开始时间</span> <input type="text" style="width: 180px;"
						                id="startDate" name="startDate" class="form-control" placeholder="开始时间">
						            </div>
						            
						            <div class="input-group">
						              <span class="input-group-addon">结束时间</span> <input type="text" style="width: 180px;"
						                id="endDate" name="endDate" class="form-control" placeholder="结束时间">
						            </div>
								</div>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">活动图片</label> <!--<input type="text"
									class="form-control" name="img" id="img"
									placeholder="活动图片"> -->
									<a href="" target="_blank" id="editImgUrl" style="display: none;">查看</a>
									 <img id="uploadPreview2" src="" style="width:300px" alt="未激活图片"/><br />
									<input id="uploadImage2" type="file" name="p1" onchange="PreviewImage(2);" />
									<input type="hidden" id="imageUrl2" name="imageUrl2" value="123123">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">权重</label> <input
									class="form-control" name="weight" id="weight"
									placeholder="权重">
							</div>
						</form>
		
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="btnSubmitEdit">提交</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../../footer.jsp"%>
<%@ include file="../../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>

$('#search_start_time').datetimepicker({
	language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 0,
    minView: 'month',
    format: "yyyy-mm-dd",
    formatDate:'yyyy-mm-dd',
    autoclose:true
});

$('#search_end_time').datetimepicker({
	language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 0,
    minView: 'month',
    format: "yyyy-mm-dd",
    formatDate:'yyyy-mm-dd',
    autoclose:true
  });

$('#startDate').datetimepicker({
	language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 0,
    minView: 'month',
    format: "yyyy-mm-dd",
    formatDate:'yyyy-mm-dd',
    autoclose:true
});

$('#endDate').datetimepicker({
	language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 0,
    minView: 'month',
    format: "yyyy-mm-dd",
    formatDate:'yyyy-mm-dd',
    autoclose:true
  });

var urlPrefix = "";
  
var typeMap = {
		0:"",
		1:"",
		2:""
}

var activityTypeMap = {
		'-1':"全部",
		<c:forEach items="${ActivityType}" var="obj">
			${obj.code}:"${obj.msg}",
  	    </c:forEach>
	};
	
var activityProgramTypeMap = {
		'-1':"全部",   
		<c:forEach items="${ActivityPlan}" var="obj">
			${obj.code}:"${obj.msg}",
  	    </c:forEach>
	};

var activityPlatformTypeMap = {
		'-1':"全部",   
		<c:forEach items="${ActivityPlatform}" var="obj">
			${obj.code}:"${obj.msg}",
  	    </c:forEach>
	};


var activityTimeMap = {
		'-1':"全部",
		/* 0: "手动控制", */
		1: "自动控制"
	};
	
var activityStatusMap = {
		'-1':"全部",
		0: "关闭",
		1: "开启"
	};
	
var activityClickTypeMap = {
		0: "新页面",
/* 		1: "hash change", */
		2: "浮层"
}

/* function selectTimeDivShow(type) {
	if(type == 0) {
		$("#selectTimeDiv").hide();	
	} else {
		$("#selectTimeDiv").show();
	}
	
} */

function selectActivityDivShow(type) {
	if(type == 0 || type == 1) {
		$("#selectActivityDiv").hide();	
		$("#selectActivityReductionDiv").hide();	
	} else {
		if(type == 4){
			$("#selectActivityReductionDiv").show();	
			$("#selectActivityDiv").hide();		
		}else{
			$("#selectActivityReductionDiv").hide();	
			$("#selectActivityDiv").show();
		}
	}
}





var cityList ;
var collegeMap ;
var addType = 0;
var addBatchIds = '-1';
//for search select change
var type = -1;
var platformType = -1;
var status = -1;
var city = -1;
var college = -1;
var programType  = -1;


$('.btnType').click(function(){
    type = $(this).data("val");
    $('#btnActivityTypeText').html(activityTypeMap[type]);
});

$('.btnPlatformType').click(function(){
	platformType = $(this).data("val");
    $('#btnActivityPlatformText').html(activityPlatformTypeMap[platformType]);
});

$('.btnProgramType').click(function(){
	programType = $(this).data("val");
    $('#btnActivityProgramText').html(activityProgramTypeMap[programType]);
});

$('.btnStatus').click(function(){
	status = $(this).data("val");
    $('#btnStatusText').html(activityStatusMap[status]);
});

function changeCity(obj){
	city = $(obj).data("val");
    $('#btnCityText').html($(obj).html());
    
    college = -1;//$(obj).data("val");
    $('#btnCollegeText').html("全部");
    
    if(collegeMap[city]) {
    	for (var i=0, len = collegeMap[city].length; i<len; i++) {
			$("#CollegeList").append('<li><a href="javascript:void(0);" class="btnCollege" data-val="'+collegeMap[city][i].collegeId+'" onclick="changeCollege(this)">'+collegeMap[city][i].collegeName+'</a></li>');
		}
    }
};

function changeCollege(obj){
	college = $(obj).data("val");
    $('#btnCollegeText').html($(obj).html());
};

// for add select change
$('.btnType-add').click(function(){
	$("#addType").val($(this).data("val"));
    $('#btnActivityTypeText-add').html(activityTypeMap[$(this).data("val")]);
});

$('.btnPlatformType-add').click(function(){
	$("#addPlatformType").val($(this).data("val"));
    $('#btnActivityPlatformText-add').html(activityPlatformTypeMap[$(this).data("val")]);
});

$('.btnProgramType-add').click(function(){
	$("#addProgramType").val($(this).data("val"));
    $('#btnActivityProgramTypeText-add').html(activityProgramTypeMap[$(this).data("val")]);
});


$('.btnOpenType-add').click(function(){
	$("#addOpenType").val($(this).data("val"));
    $('#btnActivityTimeText-add').html(activityTimeMap[$(this).data("val")]);
});
$(".btnClickType-add").click(function() {
	$("#addClickType").val($(this).data("val"));
    $('#btnClickTypeText-add').html(activityClickTypeMap[$(this).data("val")]);
});
$(".btnShowType-add").click(function() {
	$("#addShowType").val($(this).data("val"));
    $('#btnShowTypeText-add').html($(this).html());
});


var table = null;

var typeEdit;
//edit
var operation = "add";
var activityList ;
$('#addButton').click(function () {
	operation = "add";
    
	$("#btnTypeSelect").attr("data-toggle", "dropdown");
	
	$("#editImgUrl").hide();
	
    /* $("#btnActivityTypeText-add").html(activityTypeMap[0]);
    $("#btnActivityPlatformText-add").html(activityPlatformTypeMap[0]);
    
    $("#btnActivityTimeText-add").html(activityTimeMap[0]);
    
    $("#btnClickTypeText-add").html(activityClickTypeMap[0]); */
    
    $("#activityId").val(-1);
	
	$("#addType").val(3);
    $('#btnActivityTypeText-add').html(activityTypeMap[3]);

	$("#addPlatformType").val(0);
    $('#btnActivityPlatformText-add').html(activityPlatformTypeMap[0]);
    
    $("#addProgramType").val(0);
    $('#btnActivityProgramTypeText-add').html(activityProgramTypeMap[0]);
    
	$("#addOpenType").val(1);
    $('#btnActivityTimeText-add').html(activityTimeMap[1]);
    
	$("#addClickType").val(0);
    $('#btnClickTypeText-add').html(activityClickTypeMap[0]);
    
	$("#addShowType").val(1);
	
	$("#selectActivityReductionDiv").hide();
	
	/* selectTimeDivShow(0);
	selectActivityDivShow(0); */
	
    $('#btnShowTypeText-add').html("是");
    
	$("#bgName").val("");
	$("#value").val("");
	$("#batchIds").val("");
	$("#name").val("");

	$("#weight").val("");
    
    $("#startDate").val("");
	$("#endDate").val("");	
    
    
    //document.getElementById('money').value = '';
    //document.getElementById('subMoney').value = '';
	$('#modalEdit').modal();
});

function checkParam(){
	
	var numReg = /^\d+(?=\.{0,1}\d+$|$)/;
	
	
	var addType = $("#addType").val();
	var addClickType = $("#addClickType").val();
	var addPlatformType = $("#addPlatformType").val();
	var addProgramType = $("#addProgramType").val();
	
	var addOpenType = $("#addOpenType").val();
	var bgName = $("#bgName").val();
	//var value = $("#value").val("");
	//var batchIds = $("#batchIds").val("");
	var name = $("#name").val();
	var img = $("#img").val();
	var weight = $("#weight").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	/* var startTime = $('#startDate').val();
	var endTime = $('#endDate').val(); */
	
	//alert(startTime + ":" + $.trim(startTime).length);
	
	if($.trim(bgName).length < 1 || $.trim(bgName).length > 100) {
		return "请先填写活动名称";
	}
	
	if($.trim(name).length < 1 || $.trim(name).length > 100) {
		return "请先填写前端显示活动名称";
	}
	
	if($.trim(img).length < 1 || $.trim(img).length > 100) {
		//return "请先上传图片";
	}
	
	if(addType<0 || addType>9){
		return "活动类型错误";
	} 
	if(addProgramType == ''){
		return "请选择正确的活动方案，秒杀和普通活动方案请选择实物！";
	}
	
	if(addProgramType<0 || addProgramType>9){
		return "请选择正确的活动方案，秒杀和普通活动方案请选择实物！";
	}
	if(addClickType<0 || addClickType>9){
		return "点击类型错误";
	}
	
	if(addPlatformType<0 || addPlatformType>9){
		return "活动平台类型错误";
	}  
	if(addOpenType<0 || addOpenType>9){
		return "活动时间类型错误";
	}
	if(addOpenType==1) {
		if($.trim(startDate).length <1 || $.trim(endDate).length <1) {
			return "自动控制活动时间时-请添加活动开始-结束时间";
		}
		if(startDate >= endDate) {
			return "活动规则开始时间不得早于结束时间";
		}
	}
	
	
	var disCountMoney = $("#value").val();
	if($("#addType").val() == 4 && (disCountMoney<=0||!checkInteger(disCountMoney))) {
		return "首减活动首减优惠金额字段不能为空,且不能为小数";
		
	}
	return null;
}


$('#btnSubmitEdit').click(function(){
	
	var msg = checkParam();
	if(msg != null){
		alert (msg)
		return ;
	}
	$.post(urlPrefix+operation+"?type="+typeEdit, $("#form1").serialize(), function(response){
		if (response.status == "success") {
			alert("操作成功");
			$("#form1")[0].reset();
	        table.ajax.reload(null, false);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.status);
		}
	});
	
	return false;
});

function editCouponRule (index) {
	operation = "edit";
	
	$("#editImgUrl").show();
	
	$("#btnTypeSelect").attr("data-toggle", "");
	
	var activityEdit = activityList[index];
	
	/* $("#addType").val(activityEdit.activityType);
	$("#addClickType").val(activityEdit.actionType);
	$("#addPlatformType").val(activityEdit.platformType);
	$("#addOpenType").val(activityEdit.openType);
	$("#addShowType").val(activityEdit.isShow); */
	console.log(activityEdit);
	
		$("#editImgUrl").attr("href", activityEdit.imageUrl);
	
		$("#activityId").val(activityEdit.activityId);
	
		$("#addType").val(activityEdit.activityType);
	    $('#btnActivityTypeText-add').html(activityTypeMap[activityEdit.activityType]);

		$("#addPlatformType").val(activityEdit.platformType);
	    $('#btnActivityPlatformText-add').html(activityPlatformTypeMap[activityEdit.platformType]);
	    
	    $("#addProgramType").val(activityEdit.programType);
        $('#btnActivityProgramTypeText-add').html(activityProgramTypeMap[activityEdit.programType]);
	    
	    
		$("#addOpenType").val(activityEdit.openType);
	    $('#btnActivityTimeText-add').html(activityTimeMap[activityEdit.openType]);
	    
		$("#addClickType").val(activityEdit.actionType);
	    $('#btnClickTypeText-add').html(activityClickTypeMap[activityEdit.actionType]);
	    
		$("#addShowType").val(activityEdit.isShow);
		
		$("#img").val(activityEdit.imageUrl);
		
		/* selectTimeDivShow(activityEdit.openType);
		selectActivityDivShow(activityEdit.activityType); */
		
		var showMsg = "";
		if(activityEdit.isShow == 0) {
			showMsg = "否";
		} else {
			showMsg = "是";
		}
	    $('#btnShowTypeText-add').html(showMsg);
	    
	$("#bgName").val(activityEdit.activityName);
	if(activityEdit.value == -1){
		$("#value").val();
	}else{
		$("#value").val(activityEdit.value);
	}
	$("#batchIds").val(activityEdit.batchIds);	
	$("#name").val(activityEdit.activityShowName);
	
	$("#weight").val(activityEdit.weight);
	
	if(activityEdit.openType == 1) {
		var startDate = new Date();
		var endDate = new Date();
		startDate.setTime(activityEdit.startDate);
		endDate.setTime(activityEdit.endDate);
		$("#startDate").val(startDate.Format("yyyy-MM-dd"));
		$("#endDate").val(endDate.Format("yyyy-MM-dd"));		
	}
	
	/* typeEdit = couponRuleEdit.couponType;
	var start = new Date();
	start.setTime(couponRuleEdit.beginTime);
	var end = new Date();
	end.setTime(couponRuleEdit.endTime);
    $('#btnTypeEditText').html(typeMap[typeEdit]);
    $("#title").val(couponRuleEdit.couponName);
    $("#desc").val(couponRuleEdit.description);
    document.getElementById('money').value = couponRuleEdit.totalMoney/100.0;
    document.getElementById('subMoney').value = couponRuleEdit.subMoney/100.0;
    document.getElementById('couponRuleId').value = couponRuleEdit.couponId;
    
    $("#startDate").val(start.Format("yyyy-MM-dd hh:mm:ss"));
    $("#endDate").val(end.Format("yyyy-MM-dd hh:mm:ss")); */
    
	$('#modalEdit').modal();
}


function delCouponRule (couponRuleId) {	
    if (confirm("确定删除此优惠活动")) {

        $.getJSON(urlPrefix+"del?activityId="+couponRuleId, function (ret) {
            if (ret.code != null && ret.code != "success") {
                alert(ret.msg);
            }else {
            	alert("删除成功");
                table.ajax.reload();
            }
        });
    }
}


function openActivity(activityId, status) {
	
	var msg = "";
	if(status == 0) {
		msg = "开启";
	} else {
		msg = "关闭";
	}
	
    if (confirm("确定"+msg+"此活动")) {

        $.getJSON(urlPrefix+"open?activityId="+activityId + "&status=" + status, function (ret) {
            if (ret.code != null && ret.code != "success") {
                alert(ret.msg);
            }else {
            	alert(msg+"成功");
                table.ajax.reload();
            }
        });
    }
}


//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload();
});

$(document).ready(function() {
	//datatable
    //$('#btnTypeText').html(typeMap[type]);
	table = $('#couponRuleList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: urlPrefix+"ajaxListData",
        	data: function(d) {
        		d.activityId = $("#activityId").val();
        		d.startDate = $('#search_start_time').val();
                d.endDate = $('#search_end_time').val();
                d.name = $('#activityName').val();
                d.type = type;
                d.programType = programType;
                d.platformType = platformType;
                d.status = status;
                d.city = city;
                d.college = college;
            },
        	dataSrc: function ( json ) {
        		var newData = [];
        		activityList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var activity = json.data[i];
        			
        			var timeMsg = "";
        			if(activity.openType == 1) {
        				var startDate = new Date();
            			var endDate = new Date();
        				startDate.setTime(activity.startDate);
            			endDate.setTime(activity.endDate);
            			if(activity.startDate && activity.endDate) {
            				timeMsg =startDate.Format("yyyy-MM-dd") + "~" + endDate.Format("yyyy-MM-dd");	
            			} else {
            				timeMsg = "自动";
            			}
            			
        			} /* else {
        				timeMsg = "手动";
        			} */
        			
                    var openOpera = "";
                    /* if(activity.openType == 1) {
                    	openOpera = "开启";
                    } else { */
                    	// 关闭
                    	if(activity.isOpen == 0) {
                    		openOpera = '关闭[<a href="javascript:openActivity('+activity.activityId+','+activity.isOpen+');">开启</a>]';
                    	} else {
                    		openOpera = '开启[<a href="javascript:openActivity('+activity.activityId+','+activity.isOpen+');">关闭</a>]';
                    	}
                    /* } */
                    var isShowMsg = "";
                    if(activity.isShow==0) {
                    	isShowMsg = "否";
                    } else {
                    	isShowMsg = "是";
                    }
                    var imageInfo = "暂无";
                    if(activity.imageUrl) {
                    	imageInfo = '<a href="'+activity.imageUrl+'" target="_blank">查看</a>';
                    }
        			newData[newData.length] = [activity.activityName,
        			                           activityTypeMap[activity.activityType],
        			                           activityPlatformTypeMap[activity.platformType],
        			                           activity.activityShowName,
        			                           imageInfo,
        			                           timeMsg,
        			                           isShowMsg,
        			                           activity.weight,
        			                           openOpera,
        			                           /* <a href="javascript:delCouponRule('+activity.activityId+');">删除</a>&nbsp;&nbsp; */
        			                           '<a href="javascript:delCouponRule('+activity.activityId+');">删除</a>&nbsp;&nbsp;<a href="javascript:editCouponRule('+i+');">编辑</a>&nbsp;&nbsp;<a href="javascript:getRegion('+ activity.activityId +');">范围设置</a>'];
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "",
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

	$.getJSON(urlPrefix+"ajaxMateData", function (ret) {
		cityList = ret.citiList;
		collegeMap = ret.collegeMap;
		for (var i=0, len = cityList.length; i<len; i++) {
			$("#ccityList").append('<li><a href="javascript:void(0);" class="btnCity" data-val="'+cityList[i].cityId+'" onclick="changeCity(this)">'+cityList[i].cityName+'</a></li>');
		}
		console.log(ret);
    });
    
} );
var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode, clickFlag){
				
			}
		}
	};
var couponRuleIdTemp = 0;
function getRegion(couponRuleId){
	couponRuleIdTemp = couponRuleId
	/* $.fn.zTree.init($("#selectTree"), setting, zNodes); */
    $.post("getregion", {"activityId":couponRuleId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
        	 $('#treeEdit').modal('show');
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     		initTreeExpand();
     	 }
	},"json"); 
}

function initTreeExpand() {
	var treeObj = $.fn.zTree.getZTreeObj("selectTree");
	treeObj.expandAll(false);
	var rootNode = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
	treeObj.expandNode(rootNode,true,false,true,false);
	
	//var checkedNodes = treeObj.getNodesByFilter(function (node) { return node.checked == true }, false);
	var checkedNodes = treeObj.getNodesByParam('checked',true);
	for(var i = 0;i < checkedNodes.length; i++) {
		treeObj.expandNode(checkedNodes[i].getParentNode(),true);
	}
	
	
	
}

//确定
$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	
	var nodes=treeObj.getCheckedNodes();
	var checkNodeIdArr = new Array();
	for(var i =0; i < nodes.length; i++) {
		var checkNodeId = nodes[i].id;
		if(checkNodeId.indexOf('college_') == 0) {
			checkNodeId = checkNodeId.substring(checkNodeId.lastIndexOf('_') + 1,checkNodeId.length);
			checkNodeIdArr[checkNodeIdArr.length] = checkNodeId;
		}
	}
	
	if(checkNodeIdArr.length == 0) {
		alert("请至少选择一个学校节点");
		return;
	}
	
	//var info = JSON.stringify(nodes);
	
	 $.post("setregion",{"couponRuleId":couponRuleIdTemp,"region":checkNodeIdArr.join(",")}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~")
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
	 
});

//判断正整数
function checkInteger(input) {
     var re = /^[1-9]+[0-9]*]*$/;

     if (!re.test(input)) {
        return false;
     }
     
     return true;
}
</script>