<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

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
				<!-- <div class="alert alert-info">
					For help with such table please check <a
						href="http://datatables.net/" target="_blank">http://datatables.net/</a>
				</div> -->
				<div class="col-lg-2">
		            <div class="input-group">
		              <span class="input-group-addon">类型</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle"
			                  data-toggle="dropdown">
			                  <span id="btnTypeText">全部</span>&nbsp;<span
			                    class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="menu">
			                  <li><a href="javascript:void(0);" class="btnType"
			                      data-val="0">全部</a></li>
			                  <li><a href="javascript:void(0);" class="btnType"
			                      data-val="1">首减</a></li>
			                  <li><a href="javascript:void(0);" class="btnType"
			                      data-val="2">满减</a></li>
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
							<th>类型</th>
							<th>名称</th>
							<th>总额</th>
							<th>减免金额</th>
							<th>创建时间</th>
							<th>创建时间</th>
						</tr>
					</thead>

				</table>
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
							<input type="hidden" name="couponRuleId" id="couponRuleId" />
					            <div class="form-group">
					              <label for="exampleInputEmail1">类型</label>
					              <div class="btn-group">
					                <button type="button" class="btn btn-default dropdown-toggle"
					                  data-toggle="dropdown">
					                  <span id="btnTypeEditText"></span>&nbsp;<span
					                    class="caret"></span>
					                </button>
					                <ul class="dropdown-menu" role="menu">
					                  <li><a href="javascript:void(0);" class="btnTypeEdit"
					                      data-val="1">首减</a></li>
					                  <li><a href="javascript:void(0);" class="btnTypeEdit"
					                      data-val="2">满减</a></li>
					                </ul>
					              </div>
					            </div>
							<br>
							<div class="form-group">
								<label for="exampleInputPassword1">标题</label> <input
									class="form-control" id="title" name="title"
									placeholder="标题">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">描述</label> <input
									class="form-control" id="desc" name="desc"
									placeholder="描述">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">总额</label> <input type="text"
									class="form-control" id="money" name="money"
									placeholder="总额">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">减免金额</label> <input
									class="form-control" id="subMoney" name="subMoney"
									placeholder="减免金额">
							</div>
							<!-- <div class="form-group">
								<label for="exampleInputPassword1">开始时间</label> <input
									class="form-control" id="startDate" name="startDate"
									placeholder="开始时间">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">结束时间</label> <input
									class="form-control" id="endDate" name="endDate"
									placeholder="结束时间">
							</div> -->
							
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

<%@ include file="../footer.jsp"%>
<%@ include file="../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>

/* $('#startDate').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:ss'
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
      format: 'yyyy-mm-dd hh:ii:ss'
  }); */

var urlPrefix = "";

var typeMap = {
		0: "全部",
		1: "首减",
		2: "满减"
	};
var type =<c:out value="${type}" />;
$('.btnType').click(function(){
    type = $(this).data("val");
    $('#btnTypeText').html(typeMap[type]);
});

var table = null;

var typeEdit;
//edit
var operation = "add";
var couponRuleList ;
$('#addButton').click(function () {
	operation = "add";
	typeEdit = 2;
    $('#btnTypeEditText').html(typeMap[typeEdit]);
    document.getElementById('money').value = '0.0';
    document.getElementById('subMoney').value = '0.0';
	$('#modalEdit').modal();
});

$('.btnTypeEdit').click(function(){
	typeEdit = $(this).data("val");
    $('#btnTypeEditText').html(typeMap[typeEdit]);
});

function checkParam(){
	
	var numReg = /^\d+(?=\.{0,1}\d+$|$)/;
	
	var title = $("#title").val();
	var desc = $("#desc").val();
	/* var startTime = $('#startDate').val();
	var endTime = $('#endDate').val(); */
	
	//alert(startTime + ":" + $.trim(startTime).length);
	
	if($.trim(title).length < 2 || $.trim(title).length > 200) {
		return "标题不得为空，且长度2~100";
	}
	
	if($.trim(desc).length < 2 || $.trim(desc).length > 200) {
		return "描述不得为空，且长度2~100";
	}
	
	if($('#money').val().trim().length== 0){
		return "请输入总额";
	}  
	if(!numReg.test($('#money').val()) || $('#money').val() == "0"){
		return "总额必须为正数";
	}
	
	if($('#subMoney').val().trim().length== 0){
		return "请输入减免金额";
	}  
	if(!numReg.test($('#subMoney').val()) || $('#subMoney').val() == "0"){
		return "减免金额必须为正整数";
	}
	
	var num1 = Number($('#money').val());
	var num2 = Number($('#subMoney').val());
	if(num1 <= num2){
		return "总额必须大于减免金额"
	}
	
	/* if($.trim(startTime).length != 19 || $.trim(endTime).length != 19) {
		return "请添加活动规则开始-结束时间";
	}
	
	if(startTime >= endTime) {
		return "活动规则开始时间不得早于结束时间";
	} */
	
	return null;
}


$('#btnSubmitEdit').click(function(){
	
	/* $.post(urlPrefix+"judgestorage/defaultstorage",{"dstorageId":$("#dstorageId").val()},function(response){
		
		if (response.code == -1) {
			alert(response.msg);  
			window.location.href=urlPrefix+"home/index";
		}	
	},"json");  */
	
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
	couponRuleEdit = couponRuleList[index];
	typeEdit = couponRuleEdit.couponType;
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
    
    /* $("#startDate").val(start.Format("yyyy-MM-dd hh:mm:ss"));
    $("#endDate").val(end.Format("yyyy-MM-dd hh:mm:ss")); */
    
	$('#modalEdit').modal();
}


function delCouponRule (couponRuleId) {
	/* $.post(urlPrefix+"judgestorage/defaultstorage",{"dstorageId":$("#dstorageId").val()},function(response){
		
		if (response.code == -1) {
			alert(response.msg);  
			window.location.href=urlPrefix+"home/index";
		}	
	},"json");  */
	
    if (confirm("确定删除此优惠活动")) {

        $.getJSON(urlPrefix+"del?couponRuleId="+couponRuleId, function (ret) {
            if (ret.code != null && ret.code != "success") {
                alert(ret.msg);
            }else {
            	alert("删除成功");
                table.ajax.reload();
            }
        });
    }
}


//提交搜索条件
$('#btnSubmit').click(function(){
	window.location.href = urlPrefix+"list?type="+type;
});

$(document).ready(function() {
	//datatable
    $('#btnTypeText').html(typeMap[type]);
	table = $('#couponRuleList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: urlPrefix+"ajaxListData",
        	data: function(d) {
                d.type = type;
            },
        	dataSrc: function ( json ) {
        		var newData = [];
        		couponRuleList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var couponRule = json.data[i];
        			var createTime = new Date();
                    createTime.setTime(couponRule.createTime);
        			newData[newData.length] = [typeMap[couponRule.couponType],
        			                           couponRule.couponName,
        			                           couponRule.totalMoney/100.0,
        			                           couponRule.subMoney/100.0,
        			                           createTime.Format("yyyy-MM-dd hh:mm:ss"),
        			                           '<a href="javascript:delCouponRule('+couponRule.couponId+');">删除</a>&nbsp;&nbsp;<a href="javascript:editCouponRule('+i+');">编辑</a>&nbsp;&nbsp;<a href="javascript:getRegion('+ couponRule.couponId +');">范围设置</a>'];
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
/*初始化树数据格式*/
/* var zNodes =[
		{ id:1, pId:0, name:"RDC", open:true,icon:"/img/ztree/rdc.png"},
		{ id:11, pId:1, name:"RDC所有仓库",open:true,icon:"/img/ztree/storage.png"},
		{ id:111, pId:11, name:"北京仓",icon:"/img/ztree/storage.png"},
		{ id:112, pId:11, name:"武汉仓",icon:"/img/ztree/storage.png"},
		{ id:1111, pId:111, name:"北京大学",icon:"/img/ztree/college.png"},
		{ id:1112, pId:111, name:"清华大学",icon:"/img/ztree/college.png"},
		{ id:2, pId:0, name:"LDC", checked:true, open:true,icon:"/img/ztree/ldc.png"},
		{ id:21, pId:2, name:"所有城市",open:true,icon:"/img/ztree/storage.png"},
		{ id:221, pId:21, name:"北京市", checked:true,icon:"/img/ztree/storage.png"},
		{ id:222, pId:21, name:"上海市",icon:"/img/ztree/storage.png"},
		{ id:2221, pId:222, name:"小麦店1",icon:"/img/ztree/storage.png"},
		{ id:2222, pId:222, name:"小麦店2",icon:"/img/ztree/storage.png"},
		{ id:23, pId:2222, name:"北京大学",icon:"/img/ztree/college.png"}
	];
$('#treeEdit').modal('hide');
$('#treeEdit').modal('show');
$.fn.zTree.init($("#selectTree"), setting, zNodes); */
var couponRuleIdTemp = 0;
function getRegion(couponRuleId){
	couponRuleIdTemp = couponRuleId
	/* $.fn.zTree.init($("#selectTree"), setting, zNodes); */
    $.post("getregion", {"couponRuleId":couponRuleId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
        	 $('#treeEdit').modal('show');
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     	 }
	},"json"); 
}

//确定
$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	var info = JSON.stringify(nodes);
	 $.post("setregion",{"couponRuleId":couponRuleIdTemp,"region":info}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~")
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
});
</script>