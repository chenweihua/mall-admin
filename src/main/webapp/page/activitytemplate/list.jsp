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
					<i class="glyphicon glyphicon-banner">活动模板</i>
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i class="glyphicon glyphicon-cog"></i></a>
					<a href="#"	class="btn btn-minimize btn-round btn-default"><i class="glyphicon glyphicon-chevron-up"></i></a>
					<a href="#"	class="btn btn-close btn-round btn-default"><i class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">
		        <div class="row">
		        
		        	<div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">模板名称</span> 
			              <input type="text" id="templateNameQuery" class="form-control" value="${templateNameQuery}" placeholder="">
			            </div>
			        </div>
			          
		        			          
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
			            </div>
			          </div>
					
					</div>
				
		        <br />
        		<br />
				<table id="activityTemplateList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
								<th style="width: 5%">模板名称</th>
								<th style="width: 5%">模板样式</th>
								<th style="width: 5%">背景颜色</th>
								<th style="width: 5%">头部图片</th>
						<!-- 		<th style="width: 25%">描述图片</th>   -->
								<th style="width: 5%">操作</th>
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
							<input type="hidden" name="activityTemplateId" id="activityTemplateId" />
							
							<div class="form-group">
								<label for="templateName" class="control-label">模板名称:</label>
								<input name="templateName" id="templateName" value="${activityTemplate.templateName}" placeholder="" style="width:50%;">
							</div>
							
							<div class="form-group">
				              <label for="lableCoupon" class="control-label">模板样式:</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				                  <span id="templateTypeEditText"></span>&nbsp;
				                  <span class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="templateTypeEdit" data-val="1">样式1</a></li>
				                  <li><a href="javascript:void(0);" class="templateTypeEdit" data-val="2">样式2</a></li>
				                </ul>
				              </div>
				            </div>
							
							<div class="form-group">
								<label for="colorValue" class="control-label">背景颜色:</label>
								<input name="colorValue" id="colorValue" value="${activityTemplate.colorValue}" placeholder="" style="width:50%;">
								<div id="colorPreView" style="display:inline-block;height:16px;width:100px;margin-left:20px;"></div>
							</div>
							
							<div class="form-group">
								<label for="headerImageUrl" class="control-label">头部图片:</label>
								<img id="uploadPreview1" src="" style="width:200px" alt="头部图片"/>
								<input id="uploadImage1" type="file" name="p1" onchange="PreviewImage(1);" style="display:inline;" />
  								<input type="hidden" id="imageUrl1" name="headerImageUrl" value="${activityTemplate.headerImageUrl}">
							</div>
							
							
							<div class="form-group" style="display:none;">
								<label for="imageUrl" class="control-label">描述图片:</label>
								<input id="uploadImage2" type="file" name="p2" onchange="PreviewMutiImage('uploadImage2','uploadDetailPreview','imageUrl',10, 'imageUrlBoxerShow');" style="display:inline;" />
								<div id="imageUrlBoxerShow" style="margin-left:88px;">
									<span id="uploadDetailPreview1">
										<img src="" style="width:200px" alt="描述图片"/>
										<input type="hidden" name="imageUrl" value="">
									</span><br />
								</div>
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

var templateTypeMap = {
	"":"不限",
	"1":"样式1",
	"2":"样式2"
};






var templateTypeEdit = "";
$('.templateTypeEdit').click(function(){
	templateTypeEdit = $(this).data("val") + "";
    $('#templateTypeEditText').html(templateTypeMap[templateTypeEdit]);
});


var urlPrefix = '';
var table = null;


var activityTemplateList ;


$('#addButton').click(function () {
	clearAll();
	$('#modalEdit').modal();
});




$('#btnSubmitEdit').click(function(){
	
	if(!checkParam()) {
		return;
	}
	
	
	$.post(urlPrefix+"save"+"?templateType="+templateTypeEdit, $("#form1").serialize(), function(response){
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
	
	if($.trim($("#templateName").val()) == "") {
		alert("模板名称不能为空！");
		return false;
	}
	if(templateTypeEdit == '') {
		alert("请选择模板样式！");
		return false;
	}
	if($.trim($("#colorValue").val()) == "") {
		alert("背景颜色不能为空！");
		return false;
	}
	
	if(!validateColorValue($("#colorValue").val())) {
		alert("背景颜色格式不正确,格式为#加6位16进制字符，如 #FF0000");
	    return false;
	}
	
	if($.trim($("#imageUrl1").val()) == "") {
		alert("头部图片不能为空！");
		return false;
	}
	/*
	var imageUrlNull = false;
	$("input[name=imageUrl]").each(function(){
		
		if($.trim($(this).val()) == "") {
			imageUrlNull = true;
		}
		
	});
	
	if(imageUrlNull || $("input[name=imageUrl]").size() == 0) {
		alert("描述图片不能为空！");
		return false;		
	}
	*/
	
	
	return true;
}


/**
 * 校验输入的颜色值是否为#加上6位16进制字符串格式
 */
function validateColorValue(value) {
	var re = /^#[0-9|a-f|A-F]{6}$/;
    return re.test($("#colorValue").val());
}


function clearAll() {

	$("#activityTemplateId").val("");
	$("#templateName").val("");
	$("#colorValue").val("");
	
	$("#colorPreView").hide();
	
	//清空图片
	$("#uploadPreview1").attr("src","");
	$("#imageUrl1").val("");
	
	$("#imageUrlBoxerShow").empty();
	$("#imageUrlBoxerShow").append('<span id="uploadDetailPreview1"><img src="" style="width:200px" alt="描述图片"/><input type="hidden" name="imageUrl" value=""></span><br />');
	
	
	templateTypeEdit = '';
	$("#templateTypeEditText").html(templateTypeMap[""]);
	
}


function edit (index) {
	
	
	var activityTemplate = activityTemplateList[index];
	$("#activityTemplateId").val(activityTemplate.activityTemplateId);
	
	$("#templateName").val(activityTemplate.templateName);
	$("#colorValue").val(activityTemplate.colorValue);
	
	if(validateColorValue($("#colorValue").val())) {
		$("#colorPreView").css("background-color",$("#colorValue").val());
		$("#colorPreView").show();
	}
	
	templateTypeEdit = activityTemplate.templateType;
	$("#templateTypeEditText").html(templateTypeMap[templateTypeEdit]);
	
	
	//初始化即将要编辑页图片
	$("#uploadPreview1").attr("src",activityTemplate.headerImageUrl);
	$("#imageUrl1").val(activityTemplate.headerImageUrl);
	
	$("#imageUrlBoxerShow").empty();
	var imageUrls = activityTemplate.imageUrl;
	var imageUrlArr = imageUrls.split(",");
	var descImage = '';
	for(var i = 0; i < imageUrlArr.length; i++) {
		descImage += ('<span id="uploadDetailPreview' + i + '">'
						+ '<img src="' + imageUrlArr[i] + '" style="width:200px" alt="描述图片"/>'
				 		+ '<a href="javascript:void(0);" onclick="removeElement(\'uploadDetailPreview' + i + '\');">删除</a><br />'
				 		+ '<input type="hidden" name="imageUrl" value="' + imageUrlArr[i] + '">'
				 	+ '</span>'); 
	}
	$("#imageUrlBoxerShow").append(descImage);
	
	$('#modalEdit').modal();
}






//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload(null, true);
	
});

$(document).ready(function() {
	
	table = $('#activityTemplateList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: urlPrefix+"query",
        	data: function(d) {
                d.templateName = $("#templateNameQuery").val();
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		activityTemplateList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var activityTemplate = json.data[i];
        			//描述图片
        			var imageUrls = activityTemplate.imageUrl;
        			var imageUrlArr = imageUrls.split(",");
        			var descImage = '';
        			for(var j = 0; j < imageUrlArr.length; j++) {
        				descImage += ('&nbsp;<img src="' + imageUrlArr[j] + '" style="height:10%;" />'); 
        			}
        			newData[newData.length] = [
        									   			activityTemplate.templateName,
        									   			templateTypeMap[activityTemplate.templateType],
        									   			activityTemplate.colorValue,
        									   			'<img src="' + activityTemplate.headerImageUrl + '" style="height:10%;" />',
        									   		//	descImage,
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

	$("#colorValue").keyup(function(){
		
		if(validateColorValue($(this).val())) {
			$("#colorPreView").css("background-color",$(this).val());
			$("#colorPreView").show();
		} else {
			$("#colorPreView").hide();
		}
		
	});
	
} );

</script>