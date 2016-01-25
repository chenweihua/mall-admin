<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ include file="../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 仓库列表
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
		             	 <span class="input-group-addon">名称或编码</span> <input type="text"
		                	id="searchStr" class="form-control"  placeholder="请输入名称或编码">
		           	 	</div>
		          	</div>
		          	
		            <div class="col-lg-2">
		            	<div class="input-group">
		             		 <span class="input-group-addon">仓库类型</span>
		             		 <div class="btn-group">
		               		 <button type="button" class="btn btn-default dropdown-toggle"
		                 		 data-toggle="dropdown">
		                 		 <span id="storagetypeText"> 全 部 </span>&nbsp;<span
		                   		 class="caret"></span>
		                		</button>
		                		<ul class="dropdown-menu" role="menu">
		                  			<li><a href="javascript:void(0);" class="StorageType"
		                       			data-val="-1">全部</a></li>	                   
		                          	<li><a href="javascript:void(0);" class="StorageType"
		                        		data-val="0">RDC</a></li>
		                        	<li><a href="javascript:void(0);" class="StorageType"
		                        		data-val="1">LDC</a></li>
		                        	<li><a href="javascript:void(0);" class="StorageType"
		                        		data-val="2">虚拟仓</a></li>
		                		</ul>
		             	 </div>
		            	</div>
		          </div>
		          
		         <div class="col-lg-2">
		            <div class="input-group">
		              <span class="input-group-addon">所属城市</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="cityText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="ulcityid">
		                  	<li><a href="javascript:void(0);" class="CityId"
		                        data-val="-1">全部</a></li>
		                        <c:forEach var="city" items="${cityList}">
									 <li><a href="javascript:void(0);" class="CityId"
										data-val="${city.cityId}">${city.cityName}</a></li>
								</c:forEach>			
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		          <div class="col-lg-2" >
		            	<div class="input-group">
		             		 <span class="input-group-addon">开关</span>
		             		 <div class="btn-group">
		               		 <button type="button" class="btn btn-default dropdown-toggle"
		                 		 data-toggle="dropdown">
		                 		 <span id="iscloseText"> 全 部 </span>&nbsp;<span
		                   		 class="caret"></span>
		                		</button>
		                		<ul class="dropdown-menu" role="menu">
		                  			<li><a href="javascript:void(0);" class="IsClose"
		                       			data-val="-1">全部</a></li>	                   
		                          	<li><a href="javascript:void(0);" class="IsClose"
		                        		data-val="0">开启</a></li>
		                        	<li><a href="javascript:void(0);" class="IsClose"
		                        		data-val="1">关闭</a></li>
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
		    </div>
		    		    		
			<div class="box-content">
				<table id="storageTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>仓库编号</th>
							<th>仓库名称</th>
							<th>仓库类型</th>
							<th>所属城市</th>
							<th>创建 时间</th>
							<th>更新时间</th>
							<th>开启/关闭</th>
							<th>虚拟门店id</th>
							<th>虚拟学校id</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
	<button type="button" class="btn btn-primary btn-sm" id="addButton" >添加</button>
</div>
<!--/row-->

<%@ include file="editstorage.jsp"%>
<%@ include file="region_tree.jsp" %>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">

/*$("#searchStr").focus(function(){//#input换成你的input的ID 
	$("#searchStr").blur(function(){		
		table.ajax.reload();
	});
});*/

var storagetype;
var cityid;
var isclose;
var isclosestatusMap = {
		0: "开启",
		1: "关闭",
	};
var typestatusMap = {
		0:"RDC",
		1:"LDC",
		2:"虚拟仓",
};

$('.StorageType').click(function(){
	storagetype = $(this).data("val");
    $('#storagetypeText').html($(this).text());
 //   table.ajax.reload();
});


$('.IsClose').click(function(){
	isclose = $(this).data("val");
    $('#iscloseText').html($(this).text());
//    table.ajax.reload();
});


var cityList = new Array();  
var table = null;
$('.CityId').click(function(){
	cityid = $(this).data("val");
    $('#cityText').html($(this).text());
 //   table.ajax.reload();
});

$('#btnSubmit').click(function () {
	table.ajax.reload();
});    

var itemList;
$(document).ready(function() {
	//datatable
	table = $("#storageTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
	       	url:"queryStorage",
	       	data: function(d) {
        		d.searchStr=$("#searchStr").val();
        		d.storagetype = storagetype;
        		d.cityid = cityid;
        		d.isclose = isclose;
            },
 			dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			var operatorstr = '<a href="javascript:void(0)" onclick="editstorage('+item.storageId+');">【修改】</a>';
        			if(item.storageType == 2){
        				operatorstr = '<a href="javascript:void(0)" onclick="editstorage('+item.storageId+');">【修改】</a><a href="javascript:void(0)" onclick="getRegion(this);">【范围】</a>';
        			}
					var createTime=new Date(item.createTime);
                    var updateTime = new Date(item.updateTime);
                    if(updateTime < createTime){
                    	updateTime = createTime;
                    }
                    var type = typestatusMap[item.storageType];
                    if(item.storageType == 1 && item.ldcType == 1){//ldc商超
                    	type = type + "-SC";
                    }
                    newData[newData.length] = [
            			               item.storageId,
            			               item.storageName,
            			               type,
            			               item.cityName+'<input type="hidden" value="'+item.cityId+'">',
            			               createTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               updateTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               isclosestatusMap[item.isClose]+'<input type="hidden" value="'+item.isClose+'">',
            			               item.vmStoreId,
            			               item.vmCollegeId,
            			               operatorstr
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


var temp;
function editstorage(storageId){
	$.post('getStorageById',{"storageId":storageId},function(ret){
		if(ret.code != 0){
			alert('请重试！');
		}else{
			var item = ret.data;
			$("#method").val("0");
			$('#storageiddiv').show();
			$('#storageid').val(item.storageId);
			$('#storageid').prop("readonly",true);
			$('#storagename').val(item.storageName);
			storageType = item.storageType;
			var ldcType = item.ldcType;
			var ldcOwnerType = item.ldcOwnerType;
			if(storageType == 0){
				$('#storagetype option[value="0"]').attr("selected",true);
				$("#citydiv").hide();
				$("#vmstoreiddiv").hide();
				$("#ldcTypeDiv").hide();
				$("#ldcOwnerTypeDiv").hide();
				$("#vFreight").hide();
				$("#ldcTypeId").prop("checked",false);
				$("#ldcTypeId").attr('value',"0");
			}else if(storageType == 1){
				$('#storagetype option[value="1"]').attr("selected",true);
				$("#ldcTypeDiv").show();
				$("#ldcOwnerTypeDiv").show();
				$("#citydiv").show();
				$("#vmstoreiddiv").show();
				$("#vFreight").hide();
				if(ldcType == '1') {//如果是LDC商超
					$("#ldcTypeDiv").show();
					$("#ldcTypeId").prop("checked",true);
					$("#ldcTypeId").val(ldcType);
				}
				else {
					$("#ldcTypeId").prop("checked",false);
					$("#ldcTypeId").attr('value',"0");
				}
				$("#ldcOwnerType").val(ldcOwnerType);
			}else if(storageType == 2){
				$('#storagetype option[value="2"]').attr("selected",true);
				$("#ldcTypeDiv").hide();
				$("#ldcOwnerTypeDiv").hide();
				$("#citydiv").hide();
				$("#vmstoreiddiv").hide();
				$("#vFreight").show();
				if(item.freightType == 1 && item.freight == 0){
					freightTypeVal = 2;
				}else{
					freightTypeVal = 1;
				}
				$("input[type=radio][name=freightType]").removeAttr("checked");
				$("input[type=radio][name=freightType][value="+freightTypeVal+"]").prop("checked","checked");
				freightTypeChange(freightTypeVal);
				freightSubTypeVal = item.freightType;
				$("input[type=radio][name=freightSubType]").removeAttr("checked");
				$("input[type=radio][name=freightSubType][value="+freightSubTypeVal+"]").prop("checked","checked");
				if(freightSubTypeVal == 1){
					 $('#freightSubContent1').show();
					 $('#freightSubContent2').hide();
					 $('#freight1').val(item.freight/100);
				}else{
					 $('#freightSubContent1').hide();
					 $('#freightSubContent2').show();
					 $('#freight2').val(item.freight/100);
					 $('#freightSub').val(item.freightSub/100);
				}
				$("#ldcTypeId").prop("checked",false);
				$("#ldcTypeId").attr('value',"0");
			}
			
			$('#storagetype').attr("disabled",true);
			$("#vmcollegeid").val("");
			$("#vmcollegeiddiv").hide();
			$('#cityid').val(item.cityId);
			$('#cityid').attr("disabled",true);
			$("#isclose").val(item.isClose);
			$("#onoff").show();
			$("#vmstoreid").val(item.vmStoreId);
			$("#modalEdit").modal();
		}
	},'json');
}

function getRegion(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var storageId = $(child[0]).text();
	//为范围设值
	$.post("getRegion",{"storageId":storageId}, function(json){
		if (json.code != 0) {
			alert(json.msg);
		} else {
			var zNodes = json.data;
			$.fn.zTree.init($("#selectTree"), setting, zNodes);
			$('#storageId4Region').val(storageId);
			$('#treeEdit').modal('show');
		}
	});
}


$('#treeSubmit').click(function() {
	var storageId = $('#storageId4Region').val();
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	var info = JSON.stringify(nodes);
	$.post("setRegion",{"storageId":storageId,"treeInfo": info}, function(json){
		if (json.code != 0) {
			alert(json.msg);
		} else {
			alert('范围设置成功');
			$('#treeEdit').modal('hide');
		}
	});
});

function deletestorage(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var id = $(child[0]).text();
	$.post("deleteStorage",{"storageid":id}, function(response){
		if (response.code == 0) {
			alert(response.msg);
        	table.ajax.reload(null, false);
        	$('#modalEdit').modal('hide');
		} else{
			alert(response.msg);
		}
	});
}


$("#addButton").click(function(){
	$("#method").val("1");
	$('#storageid').prop("readonly",false);
	$('#storageid').val("");
	$('#storageiddiv').hide();
	$('#storagename').val("");
	$('#storagetype').val("");
	$('#cityid').val("");
	$('#storagetype').attr("disabled",false);
	$('#cityid').attr("disabled",false);
	$("#onoff").hide();
	$("#citydiv").hide();
	$("#ldcTypeDiv").hide();
	$("#ldcOwnerTypeDiv").hide();
	$("#vmcollegeiddiv").hide();
	$("#vmcollegeid").val("");
	$("#vmstoreid").val("");
	$("#vmcollegeiddiv").val("");
	$("#vmcollegeiddiv").hide();
	$("#vmstoreiddiv").hide();
	$("#vFreight").hide();
	$("#modalEdit").modal();
});

$('#start-time').datetimepicker({
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

$('#end-time').datetimepicker({
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
  

//验证数据格式
function checkParam(){
	var positveNumReg = /^[0-9]*[1-9][0-9]*$/ ;
	//六位小数
	var numReg =  /^-?\d+\.?\d{0,6}$/;
	if($('#storagename').val().trim().length== 0){
		return "请输入仓库名称";
	}
	if($('#storagetype').val() == null){
		return "请选择仓库类型";
	}
	if($('#storagetype').val() == 1 && $('#cityid').val() == null){
		return "请选择城市";
	}
	if($('#storagetype').val() == 1){
		if($('#vmstoreid').val().trim() == 0){
			return "ldc仓的虚拟门店id不能为空";
		}
		if(!positveNumReg.test(($('#vmstoreid').val()))){
			return "门店id格式错误";
		}
	}
	return null;
}
  
    
</script>