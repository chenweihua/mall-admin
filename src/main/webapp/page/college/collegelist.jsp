<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ include file="../header.jsp"%>
<link href='/css/multilevelnavbar.css' rel="stylesheet" >


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 学校列表
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
		                	id="searchStr" class="form-control"  placeholder="请输入学校名称或编码">
		           	 	</div>
		          	</div>	          		          
		       		
		       		<div class="col-lg-2">
		            	<div class="input-group">
		              		<span class="input-group-addon">城市</span>
		              		<div class="btn-group">
		                		<button type="button" class="btn btn-default dropdown-toggle"
		                  		data-toggle="dropdown">
		                  			<span id="cityText">
		                  				全部
		                  			</span> <span
		                    		class="caret"></span>
		                		</button>
								<ul class="dropdown-menu" role="menu" id="ulcity">
									<li><a href="javascript:void(0);" class="City"
											data-val="0">全部</a></li>
									<c:forEach var="district" items="${districtList}">
									 	<li><a href="javascript:void(0);" class="City"
											data-val="${district.city.cityId}">${district.city.cityName}</a></li>			
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
		    </div>
			<div class="box-content">
				<table id="collegeTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>编号</th>
							<th>学校名称</th>
							<th>地区</th>
							<th>rdc</th>
							<th>ldc</th>
							<th>开启</th>
							<th>展示</th>
							<th>经纬度</th>
							<th>rdc配送方式</th>
							<th>rdc自提地址</th>
							<th>ldc配送方式</th>
							<th>ldc自提地址</th>
							<th>门店id</th>
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

<%@ include file="editcollege.jsp"%>
<%@ include file="copysku.jsp"%>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">


$('#btnSubmit').click(function () {
	table.ajax.reload();
});

/*$("#searchStr").focus(function(){//#input换成你的input的ID 
	$("#searchStr").blur(function(){		
		table.ajax.reload();
	});
});*/


var cityid = 0;
	
var isopenstatusMap = {
		0: "休息",
		1: "正常营业"
		
	};
var isshowstatusMap = {
		0:"不可见",
		1:"可见",
};
var deliverytypeMap = {
		3:"",
		0:"自提",
		1:"配送",
		2:"自提+配送"
}


var initldchtml = $('#ldc').html();
var initcityhtml = $('#editCityText').html();
 

$('.City').click(function(){
	cityid = $(this).data("val");
    $('#cityText').html($(this).text());
});




var table = null;
var cityList = new Array();
var watch;
$(document).ready(function() {
	
	$('#areaid').val(editareaid);
	
	//datatable
	table = $("#collegeTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url:"queryCollege",
	       	data: function(d) {
        		d.searchStr=$("#searchStr").val();
        		d.cityid = cityid;
            },
        	dataSrc: function ( json ) {
        		    var newData = [];
          			itemList = json.data;
      	       		for (var i=0, len = json.data.length; i<len; i++) {
      	       			var item = json.data[i];
						var operatorstr = '<a href="javascript:void(0)" onclick="editcollege(this);">【修改】</a><a href="javascript:void(0)" onclick="deletecollege(this);">【删除】</a>';
            			newData[newData.length] = [
            			                         //item.id+'<input type="hidden" id="itemCityName" value="'+cityName+'"><input type="hidden" id="itemCityId" value="'+cityId+'"><input type="hidden" id="itemweight" value="'+weight+'">',
            			                           item.collegeId+'<input type="hidden" value="'+item.collegeAddr+'">',
            			                           item.collegeName,
            			                           item.cityName+'<input type="hidden" value="'+item.cityId+'">'+'<input type="hidden" value="'+item.areaId+'">',
            			                           item.rdcStorageName+'<input type="hidden" value="'+item.rdcStorageId+'">',
            			                           item.ldcStorageName+'<input type="hidden" value="'+item.ldcStorageId+'">',          			                           
            			                           isopenstatusMap[item.isOpen]+'<input type="hidden" value="'+item.isOpen+'">',
            			                           isshowstatusMap[item.isShow]+'<input type="hidden" value="'+item.isShow+'">',
            			                           item.longitude+"  , "+item.latitude+'<input type="hidden" value="'+item.longitude+'">'+'<input type="hidden" value="'+item.latitude+'">',			                           
            			                           deliverytypeMap[item.rdcDeliveryType]+'<input type="hidden" value="'+item.rdcDeliveryType+'">',
            			                           item.rdcDeliveryAddress,
            			                           deliverytypeMap[item.ldcDeliveryType]+'<input type="hidden" value="'+item.ldcDeliveryType+'">',
            			                           item.ldcDeliveryAddress,
            			                           item.storeId,
            			                           operatorstr];
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

function deletecollege (obj) {
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var collegeid = $(child[0]).text();
    if (confirm("确定删除该学校？")) {
        $.getJSON("deletecollege?collegeId="+collegeid, function (ret) {
            if (ret.code =="0") {
                alert(ret.msg);
                table.ajax.reload();
            }else {
            	alert("删除失败");
            }
        });
    }
}


function editcollege(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var id = $(child[0]).text();
	var collegeAddr = $(child[0]).find("input").val();
	var name = $(child[1]).text();
	var cityid = $(child[2]).find("input").first().val();
	var areaid = $(child[2]).find("input").last().val();	
	var cityname = $(child[2]).text();
	var storeid = $(child[12]).text();	
	$("#collegeiddiv").hide();
	var rdcid = $(child[3]).find("input").val();
	var ldcid = $(child[4]).find("input").val();
	var rdcname = $(child[3]).text();
	var ldcname = $(child[4]).text();
	var lon = $(child[7]).find("input").first().val();
	var lat = $(child[7]).find("input").last().val();
	var isopen = $(child[5]).find("input").val();
	var isshow = $(child[6]).find("input").val();	
	var rdctype = $(child[8]).find("input").val();
	var ldctype = $(child[10]).find("input").val();
	var rdcaddress = $(child[9]).text();
	var ldcaddress = $(child[11]).text();
	
	$("#methodtype").val(1);
	
	$("#rdcdiv4edit").hide();
	$("#ldcdiv4edit").hide();
	$("#rdcdiv4add").hide();
	$("#ldcdiv4add").hide();
	
	if(rdcid != 0){
		$('#rdc').val(rdcid);
		$("#rdcdiv4edit").show();
	}else{
		$("#rdcdiv4add").show();
	}
	if(ldcid != 0){
		$('#ldcstorageid4edit').val(ldcid);
		$("#ldcdiv4edit").show();
	}else{
		$('#ldcstorageid4edit').val(ldcid);
		$.post("ajaxLdcByCityId", {"cityId":cityid}, function(response){
			if (response.code == 1) {
				alert(response.msg);
				$('#cityid').val(editcityid);
				$('#ldc').html("");
				$('#ldc').append('<option value="0" >无</option>');
			} else {
				var item = response.ldcStorages;
				$('#ldc').html("");
				$('#ldc').append('<option value="0" >无</option>');
				watch = item;
				for(var i=0;i<item.length;i++){
					$('#ldc').append('<option value="'+item[i].storageId+'" >'+item[i].storageName+'</option>');
				}
			}
		});
		$("#ldcdiv4add").show();
	}
	$("#citydiv4add").hide();		
	$("#citydiv4edit").show();
	$("#div4Open").show();
	$("#div4Show").show();
	
	$('#rdc').val(rdcid);
	$("#method").val("0");
	$('#ldc_edit').val(ldcname);
	$('#ldc_edit').attr("readonly",true);
	$('#rdc_edit').val(rdcname);
	$('#rdc_edit').attr("readonly",true);
	
	
	$('#storeid_edit').val(storeid);
	$('#storeid_edit').attr("readonly",true);
	
	$('#collegename_edit').val(name);
	
	$('#isShow').val(isshow);
	
	$('#isOpen').val(isopen);
	
	$('#longitude').val(lon);
	
	$('#latitude').val(lat);
	
	$('#editCityText').html("");
	$('#city_edit').val(cityname);
	$('#city_edit').attr("readonly",true);
	
	$('#rdcDeliveryType').val(rdctype);
	
	$('#rdcDeliveryAddress').val(rdcaddress);
	
	$('#ldcDeliveryType').val(ldctype);
	
	$('#ldcDeliveryAddress').val(ldcaddress);
	
	$('#collegeid_edit').val(id);
	editcityid = cityid;
	$('#collegeAddr').val(collegeAddr);
	
	$("#modalEdit").modal();
}

$("#region").change(function(){
	$("#ldc").empty(); 
	$.post("ajaxLdcByCityId", {"cityId":$("#region").val()},function(data){
		var list = data.ldcStorages;
		$('#ldc').append('<option value="0" >无</option>');
		for(var i=0;i<list.length;i++){
			var ldcid = list[i].storageId;
			var ldcname = list[i].storageName;
			$('#ldc').append("<option value='"+ldcid+"'>"+ldcname+"</option>");
		}
	});
});

/**$("#ldc").change(function(){
	$.post("getCiyByLdcId", {"ldcStorageId":$("#ldc").val()},function(data){
		$("#ldc").empty(); 
		$("#region").val(data.city.city_id);
	});
});*/

$("#addButton").click(function(){
	$("#methodtype").val(0);
	$("#citydiv4add").show();
	$("#rdcdiv4add").show();
	$("#ldcdiv4add").show();
	$("#citydiv4edit").hide();
	$("#rdcdiv4edit").hide()
	$("#ldcdiv4edit").hide();
	$('#storeid_edit').attr("readonly",false);
	$('#rdc').attr("disabled",false);
	$('#ldc').attr("disabled",false);
	$("#collegeiddiv").hide();
	$("#method").val("1");
	$('#storeid_edit').prop("readonly",false);
	$('#storeid_edit').val("");
	$('#collegename_edit').val("");
	$('#collegeid_edit').val("");
	$('#region').val("");
	$('#rdc').val("");
	$('#ldc').val("");
	$('#longitude').val("");
	$('#latitude').val("");
	$('#editCityText').html(initcityhtml);
	$('#ldc').html(initldchtml);
	$("#rdc option:nth-child(1)").attr("selected","selected");
	$("#div4Open").hide();
	$("#div4Show").hide();
	$('#rdcDeliveryType').val(3);
	$('#rdcDeliveryAddress').val("");
	$('#ldcDeliveryType').val(3);
	$('#ldcDeliveryAddress').val("");

	editcityid = ${districtList.get(0).city.cityId};
	
	$("#modalEdit").modal();
});
    
</script>