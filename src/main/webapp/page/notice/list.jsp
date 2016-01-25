<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<style>
<!--
.control-label {
	width : 10%;
	text-align : right;
	margin-right : 10px;
}
-->
</style>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i> 通告管理
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
			              <span class="input-group-addon">名称</span> 
			              <input type="text" id="nameQuery" class="form-control" value="${nameQuery}" placeholder="">
			            </div>
			          </div>
			          
			          
			           <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">平台</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="platformQueryText">不限</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                   <li><a href="javascript:void(0);" class="platformQuery"
			                      data-val="">不限</a></li>
			                  <li><a href="javascript:void(0);" class="platformQuery"
			                      data-val="0">全部</a></li>
			                  <li><a href="javascript:void(0);" class="platformQuery"
			                      data-val="1">APP</a></li>
			                  <li><a href="javascript:void(0);" class="platformQuery"
			                      data-val="2">H5</a></li>
			                </ul>
			              </div>
			            </div>
			          </div>
		        
			        <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">通告状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="statusQueryText">不限</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="">不限</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="1">开启</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="0">关闭</a></li>
			                </ul>
			              </div>
			            </div>
			          </div>
			          
			          <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">生失效状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="stateQueryText">生效</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="stateQuery"
			                      data-val="1">生效</a></li>
			                  <li><a href="javascript:void(0);" class="stateQuery"
			                      data-val="0">失效</a></li>
			                </ul>
			              </div>
			            </div>
			          </div>
			       
			       </div>
			       <div class="row"> 
			          <div class="col-lg-2"  style="width:25%;">
		            <div class="input-group">
		              <span class="input-group-addon">城市</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnCityText">全部</span> <span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="ccityList">
		                  	<li><a href="javascript:void(0);" class="btnCity"
		                        data-val="-1" onclick="changeCity(this)">全部</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		          <div class="col-lg-2" style="width:25%;">
		            <div class="input-group">
		              <span class="input-group-addon">学校</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnCollegeText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="CollegeList">
		                	<li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="-1" onclick="changeCollege(this)">全部</a></li>
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
				<table id="noticeList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th style="width: 4%">平台</th>
							<th style="width: 15%">通告名称</th>
							<th style="width: 20%">通告内容</th>
							<th style="width: 8%">控制方式</th>
							<th style="width: 16%">开始时间</th>
							<th style="width: 16%">结束时间</th>
							<th style="width: 8%">通告状态</th>
							<th style="width: 13%">操作</th>
						</tr>
					</thead>
					
				</table>
			</div>
		</div>
	</div>
	</div>
	<!--/span-->
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>
<!--/row-->

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width:800px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="form1">
							<input type="hidden" name="noticeId" id="noticeId" />
							<div class="form-group">
								<label for="title" class="control-label">展示平台:</label> 
								<input type="radio" name="platform" value="0" />全部
								<input type="radio" name="platform" value="1" />APP
								<input type="radio" name="platform" value="2" />H5
							</div>
							
							<div class="form-group">
								<label for="subTitle" class="control-label">通告名称:</label>
								<input name="noticeName" id="noticeName" value="${notice.noticeName}" placeholder="" style="width:50%;">
							</div>
							
							<div class="form-group">
								<label for="subTitle" class="control-label">通告内容:</label>
								<input name="content" id="noticeContent" value="${notice.content}" placeholder="" style="width:50%;">
							</div>
							
							<div class="form-group">
								<label for="subTitle" class="control-label">链接地址:</label>
								<input name="htmlUrl" id="htmlUrl" value="${notice.htmlUrl}" placeholder="" style="width:50%;">
							</div>
							
							<div class="form-group">
				              <label for="lableCoupon" class="control-label">展示页面:</label>
				              <input type="radio" name="position" value="1" />首页
				              <input type="radio" name="position" value="2" />购物车页
				            </div>
				            
				            
				            <div class="form-group">
								<label for="title" class="control-label">控制方式:</label> 
								<input type="radio" id="openTypeManual" name="openType" value="1" />手动
								<input type="radio" id="openTypeAuto" name="openType" value="2" />自动
							</div>
							
							<div class="form-group" id="manualArea">
								<label for="title" class="control-label">状态:</label> 
								<input type="radio"  name="status" value="1" />开启
								<input type="radio"  name="status" value="0" />关闭
							</div>
							
				            <div class="form-group" id="autoArea" style="display:none;">
				              <label for="lableCoupon" class="control-label">开始时间:</label>
				              <input style="width: 160px; display: inline-block;" class="form-control" id="startDate" name="startDate" readonly /> 
				              <label for="lableCoupon" class="control-label">结束时间:</label>
				              <input style="width: 160px; display: inline-block;" class="form-control" id="endDate" name="endDate" readonly />
				            </div>
				            
						</form>
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" id="btnSubmitEdit">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" id="btnCancle">取消</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../footer.jsp"%>
<%@ include file="../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var platformMap = {
	"":"不限",
	"0":"全部",
	"1":"APP",
	"2":"H5"
};

var statusMap = {
	"" : "不限",
	"1" : "开启",
	"0" : "关闭"	
};

var openTypeMap = {
	"1":"手动",	
	"2":"自动"
};

var stateMap = {
	"1" : "生效",
	"0" : "失效"	
};

var platformQuery;

$('.platformQuery').click(function(){
	platformQuery = $(this).data("val");
    $('#platformQueryText').html(platformMap[platformQuery]);
});


var statusQuery;

$('.statusQuery').click(function(){
	statusQuery = $(this).data("val");
    $('#statusQueryText').html(statusMap[statusQuery]);
});

var stateQuery;

$('.stateQuery').click(function(){
	stateQuery = $(this).data("val");
    $('#stateQueryText').html(stateMap[stateQuery]);
});

var urlPrefix = '';
var table = null;


var noticeList ;

var cityList ;
var collegeMap ;
var city;
var college;

function changeCity(obj){
	city = $(obj).data("val");
    $('#btnCityText').html($(obj).html());
    
    college = -1;//$(obj).data("val");
    $('#btnCollegeText').html("全部");
    
    $("#CollegeList").html("");
	$("#CollegeList").append('<li><a href="javascript:void(0);" class="btnCollege" data-val="-1" onclick="changeCollege(this)">全部</a></li>');
    
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

$('#addButton').click(function () {
	clearAll();
	$('#modalEdit').modal();
});


$('#startDate').datetimepicker({
	
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: "yyyy-mm-dd hh:ii:ss"
});

$('#endDate').datetimepicker({
	 language:  'zh-CN',
     weekStart: 1,
     todayBtn:  1,
     autoclose: 1,
     todayHighlight: 1,
     startView: 2,
     forceParse: 0,
     showMeridian: 1,
     format: "yyyy-mm-dd hh:ii:ss"
});

$('#btnSubmitEdit').click(function(){
	
	if(!checkParam()) {
		return;
	}
	
	$.post(urlPrefix+"save", $("#form1").serialize(), function(response){
		if (response.code == 0) {
			alert("操作成功");
			$("#form1")[0].reset();
			clearAll();
	        table.ajax.reload(null, true);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.msg);
		}
	});
	
	return false;
});

$('#btnCancle').click(function(){
	clearAll();
	$('#modalEdit').modal('hide');
});


function checkParam() {
	
	if($("input[name=platform]:checked").size() == 0) {
		alert("请选择平台!");
		return false;
	}
	
	if($.trim($("#noticeName").val()) == "") {
		alert("通告名称不能为空！");
		return false;
	}
	
	if($.trim($("#noticeContent").val()) == "") {
		alert("通告内容不能为空！");
		return false;
	}
	
	if($("input[name=position]:checked").size() == 0) {
		alert("请选择展示页面!");
		return false;
	}
	
	if($("input[name=openType]:checked").size() == 0) {
		alert("请选择控制方式!");
		return false;
	}
	
	var openType = $("input[name=openType]:checked").val();
	if(openType == "1") { //手动
		
		if($("input[name=position]:checked").size() == 0) {
			alert("请选择状态!");
			return false;
		}
		
	} else if(openType == "2") {  //自动
		if($("#startDate").val() == "" || $("#endDate").val() == "") {
			alert("请选择开始时间和结束时间");
			return false;
		}
		
		if($("#startDate").val() > $("#endDate").val()) {
			alert("开始时间不能大于结束时间");
			return false;
		}
	
	} else {
		alert("控制方式不合法：" + openType);
		return false;
	}
	
	return true;
}


function clearAll() {
	$("#noticeId").val("");
	$("input[name=platform]").prop("checked",false);
	$("#noticeName").val("");
	$("#noticeContent").val("");
	$("#htmlUrl").val("");
	$("input[name=position]").prop("checked",false);
	$("input[name=openType]").prop("checked",false);
	$("input[name=status]").prop("checked",false);
	$("#startDate").val("");
	$("#endDate").val("");
	$("#openTypeManual").click();
	$("#manualArea").show();
	$("#autoArea").hide();
	
}


function edit (index) {
	
	
	var notice = noticeList[index];
	$("#noticeId").val(notice.noticeId);
	$("input[name=platform][value=" + notice.platform + "]").prop("checked",true);
	$("#noticeName").val(notice.noticeName);
	$("#noticeContent").val(notice.content);
	$("#htmlUrl").val(notice.htmlUrl);
	$("input[name=position][value=" + notice.position + "]").prop("checked",true);
	$("input[name=openType][value=" + notice.openType + "]").prop("checked",true);
	$("input[name=status][value=" + notice.status + "]").prop("checked",true);
	if(notice.openType == 2) {
		$("#startDate").val(new Date(notice.startTime).Format("yyyy-MM-dd hh:mm:ss"));
		$("#endDate").val(new Date(notice.endTime).Format("yyyy-MM-dd hh:mm:ss"));
	}
	if(notice.openType == "1") {
		$("#manualArea").show();
		$("#autoArea").hide();
	} else if(notice.openType == "2") {
		$("#manualArea").hide();
		$("#autoArea").show();
	}
	
	
	$('#modalEdit').modal();
}

var noticeIdForRelation;

function getRegion(noticeId){
    $.post("getNoticeCollege", {"noticeId":noticeId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {
        	 noticeIdForRelation = noticeId;
        	 $('#treeEdit').modal('show');
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     	 }
	},"json"); 
}


$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	
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
	
	 $.post("setNoticeCollege",{"noticeId":noticeIdForRelation,"collegeIds":checkNodeIdArr.join(",")}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~")
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
});




//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload(null, true);
});

$(document).ready(function() {
	
	table = $('#noticeList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: urlPrefix+"query",
        	data: function(d) {
                //d.status = statusQuery;
                //d.contentType = contentTypeQuery;
                //d.titleOrHtmlUrl = $("#contentOrLinkQuery").val();
                d.noticeName = $("#nameQuery").val();
                d.platform = platformQuery;
                d.status = statusQuery;
                d.cityId = city;
                d.collegeId = college;
                d.state = stateQuery;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		noticeList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var notice = json.data[i];
        			newData[newData.length] = [platformMap[notice.platform],
        			                           notice.noticeName,
        			                           notice.content,
        			                           openTypeMap[notice.openType],
        			                           typeof(notice.startTime) == "undefined" ? '' : new Date(notice.startTime).Format("yyyy-MM-dd hh:mm:ss"),
        			                           typeof(notice.endTime) == "undefined" ? '' : new Date(notice.endTime).Format("yyyy-MM-dd hh:mm:ss"),
        			                           typeof(notice.status) == "undefined" ? '' : statusMap[notice.status],
        			                           '<a href="javascript:edit(' + i + ');">编辑</a>&nbsp;&nbsp;<a href="javascript:getRegion('+ notice.noticeId +');">学校设置</a>'
        			                          ]; 
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

	
	$("#openTypeManual,#openTypeAuto").click(function(){
		
		if($("input[name=openType]:checked").val() == "1") {
			$("#manualArea").show();
			$("#autoArea").hide();
		} else {
			$("#manualArea").hide();
			$("#autoArea").show();
		}
		
	});
	
	$.getJSON(urlPrefix+"getCityCollege", function (ret) {
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
	

</script>