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
					<i class="glyphicon glyphicon-banner"></i> 文章列表管理
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
			              <span class="input-group-addon">状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="statusQueryText">全部</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="">全部</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="1">启用</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="0">禁用</a></li>
			                  <li><a href="javascript:void(0);" class="statusQuery"
			                      data-val="2">过期</a></li>
			                </ul>
			              </div>
			            </div>
			          </div>
			          
			          <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">页面</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="contentTypeQueryText">全部</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="contentTypeQuery"
			                      data-val="">全部</a></li>
			                  <li><a href="javascript:void(0);" class="contentTypeQuery"
			                      data-val="0">没事找事</a></li>
			                  <li><a href="javascript:void(0);" class="contentTypeQuery"
			                      data-val="1">来点干货</a></li>
			                </ul>
			              </div>
			            </div>
			          </div>
			          
			         <div class="col-lg-3" style="width:50%;">
			            <div class="input-group">
			              <span class="input-group-addon">文章标题或链接</span> 
			              <input type="text" id="contentOrLinkQuery" class="form-control" value="${contentOrLinkQuery}" placeholder="请输入文章标题或链接进行搜索...">
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
				<table id="subscribeDetailList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th style="width: 20%">标题</th>
							<th style="width: 20%">链接</th>
							<th style="width: 20%">图片</th>
							<th style="width: 10%">页面</th>
							<th style="width: 10%">权重</th>
	             			<th style="width: 10%">状态</th>
							<th style="width: 10%">操作</th>
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
							<input type="hidden" name="subscribeId" id="subscribeId" />
							<input type="hidden" name="subscribeType" value="isActivity" />
							<div class="form-group">
								<label for="title" class="control-label">文章标题:</label> 
								<input name="title" id="title" value="${subscribeDetail.title}" placeholder="">
							</div>
							
							<div class="form-group">
								<label for="subTitle" class="control-label">文章副标题:</label>
								<input name="subTitle" id="subTitle" value="${subscribeDetail.subTitle}" placeholder="">
							</div>
							
							<div class="form-group">
								<label for="htmlUrl" class="control-label">链接:</label>
								<input name="htmlUrl" id="htmlUrl" value="${subscribeDetail.htmlUrl}" placeholder="">
							</div>
							
				            <div class="form-group">
				              <label for="lableCoupon" class="control-label">状态:</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="statusEditText"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="statusEdit"
				                      data-val="1">启用</a></li>
		                          <li><a href="javascript:void(0);" class="statusEdit"
				                      data-val="0">禁用</a></li>
				                  <li><a href="javascript:void(0);" class="statusEdit"
				                      data-val="2">过期</a></li>
				                </ul>
				              </div>
				            </div>
				            
				            
				            <div class="form-group">
				              <label for="lableCoupon" class="control-label">页面</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="contentTypeEditText"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="contentTypeEdit"
				                      data-val="0">没事找事</a></li>
		                          <li><a href="javascript:void(0);" class="contentTypeEdit"
				                      data-val="1">来点干货</a></li>
				                </ul>
				              </div>
				            </div>
				            
							<br>
							
							<div class="form-group">
								<label for="weight" class="control-label">权重:</label> 
								<input name="weight" id="weight" placeholder="请输入权重数字" value="${subscribeDetail.weight }">
							</div>
							
							
							<div class="form-group">
								<label for="lableCoupon" class="control-label">图片:</label> 
								<input id="uploadImage2" type="file" name="p1" onchange="PreviewImage(2);" style="display:inline;" /><br />
								<img id="uploadPreview2" src="${subscribeDetail.imgUrl}" style="margin-left:12%;width:200px" alt="图片"/>
								<input type="hidden" id="imageUrl2" name="imgUrl" value="${subscribeDetail.imgUrl }">
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
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>

var statusMap = {
	"":"全部",
	"0":"禁用",
	"1":"启用",
	"2":"过期"
};

var contentTypeMap = {
	"":"全部",
	"0":"没事找事",
	"1":"来点干货"
};

var statusQuery;

$('.statusQuery').click(function(){
	statusQuery = $(this).data("val");
    $('#statusQueryText').html(statusMap[statusQuery]);
});

var contentTypeQuery;

$('.contentTypeQuery').click(function(){
	contentTypeQuery = $(this).data("val");
    $('#contentTypeQueryText').html(contentTypeMap[contentTypeQuery]);
});

var statusEdit;

$('.statusEdit').click(function(){
	statusEdit = $(this).data("val");
    $('#statusEditText').html(statusMap[statusEdit]);
});

var contentTypeEdit;

$('.contentTypeEdit').click(function(){
	contentTypeEdit = $(this).data("val");
    $('#contentTypeEditText').html(contentTypeMap[contentTypeEdit]);
});

var urlPrefix = '';
var table = null;

var typeEdit;
//edit
var operation = "add";
var subscribeDetailList ;
$('#addButton').click(function () {
	operation = "add";
	clearAll();
	$('#modalEdit').modal();
});



function checkParam(){
	
	
}

var curPath=window.document.location.href;  
var pathName=window.document.location.pathname;  
var pos=curPath.indexOf(pathName);  
$('#btnSubmitEdit').click(function(){
	var msg = checkParam();
	if(msg != null){
		alert (msg)
		return ;
	}
	$.post(curPath.substring(0,pos)+"/subscribe/advertise/save?statusEdit="+statusEdit+"&contentTypeEdit="+contentTypeEdit, $("#form1").serialize(), function(response){
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


function clearAll() {
	
	$("#title").val("");
	$("#subTitle").val("");
	$("#htmlUrl").val("");
	$("#imageUrl2").val("");
	$("#uploadPreview2").attr("src","");
	statusEdit = '';
	$("#statusEditText").html("&nbsp;");
	contentTypeEdit = '';
	$("#contentTypeEditText").html("&nbsp;");
	$("#weight").val("");
	$("#subscribeId").val("");
	
}


function edit (index) {
	
	var subscribeDetail = subscribeDetailList[index];
	$("#title").val(subscribeDetail.title);
	$("#subTitle").val(subscribeDetail.subTitle);
	$("#htmlUrl").val(subscribeDetail.htmlUrl);
	$("#imageUrl2").val(subscribeDetail.imgUrl);
	$("#uploadPreview2").attr("src",subscribeDetail.imgUrl);
	$("#weight").val(subscribeDetail.weight);
	$('#statusEditText').html(statusMap[subscribeDetail.status]);
	statusEdit = subscribeDetail.status;
	$('#contentTypeEditText').html(contentTypeMap[subscribeDetail.contentType]);
	contentTypeEdit = subscribeDetail.contentType;
	$("#subscribeId").val(subscribeDetail.subscribeId);
	
	$('#modalEdit').modal();
}




//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload(null, true);
});

$(document).ready(function() {
	
	//datatable
    //$('#btnTypeText').html(typeMap[type]);
	table = $('#subscribeDetailList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: urlPrefix+"query",
        	data: function(d) {
                d.status = statusQuery;
                d.contentType = contentTypeQuery;
                d.titleOrHtmlUrl = $("#contentOrLinkQuery").val();
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		subscribeDetailList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var subscribeDetail = json.data[i];
        			
        			newData[newData.length] = [subscribeDetail.title,
        			                           subscribeDetail.htmlUrl,
        			                           '<img src="' + subscribeDetail.imgUrl + '" style="width:200px" alt="图片"/>',
        			                           contentTypeMap[subscribeDetail.contentType],
        			                           subscribeDetail.weight,
        			                           statusMap[subscribeDetail.status],
        			                           '<a href="javascript:edit(' + i + ');">编辑</a>'
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




//判断正整数
function checkInteger(input) {
     var re = /^[1-9]+[0-9]*]*$/;

     if (!re.test(input)) {
        return false;
     }
     
     return true;
}


</script>