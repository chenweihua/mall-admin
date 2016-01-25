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
					<i class="glyphicon glyphicon-item"></i> 库存管理
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
		             	 <span class="input-group-addon">名称或条码</span> <input type="text"
		                	id="searchStr" class="form-control"  placeholder="请输入名称或条码">
		           	 	</div>
		          	</div>
		          	
		            <div class="col-lg-2">
		            	<div class="input-group">
		             		 <span class="input-group-addon">仓库类型</span>
		             		 <div class="btn-group">
		               			 <button type="button" class="btn btn-default dropdown-toggle"
		                 		 data-toggle="dropdown">
		                 		 	<span id="storagetypeText">${defaultTypeBean.typeName}</span>&nbsp;<span class="caret"></span>
		                		</button>
		                		<ul class="dropdown-menu" role="menu">                
		                          	<c:forEach items="${storageTypeList}" var="storageType">
										<li><a href="javascript:void(0);" class="storageType"
										data-val="${storageType.typeId }">${storageType.typeName}</a></li>
									</c:forEach>
		                		</ul>
		             	 </div>
		            	</div>
		         	 </div>
		          
		        <!-- 城市 -->
		          <div class="col-lg-2" id="citydiv">
		            <div class="input-group">
		              <span class="input-group-addon">城市</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="cityText">
		                  	${districtList.get(0).city.cityName}
		                  </span> <span
		                    class="caret"></span>
		                </button>
						<ul class="dropdown-menu" role="menu" id="ulcity">
							<c:forEach var="district" items="${districtList}">
										<li><a href="javascript:void(0);" class="city"
											data-val="${district.city.cityId}">${district.city.cityName}</a>
										</li>
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
						<ul class="dropdown-menu" role="menu" id="ulstorage">
							<c:forEach var="storage" items="${storageList}">
										<li><a href="javascript:void(0);" class="Storage"
										data-val="${storage.storageId}">${storage.storageName}</a></li>
							</c:forEach>		
						</ul>
		              </div>
		            </div>
		          </div>
		          
		        <div class="col-lg-3">
					<div class="input-group" style="float: right;padding-right:50px">
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
							<th>名称</th>
							<th>条码</th>
							<th>规格</th>
							<th>单位</th>
							<th>保质期</th>
							<th>库存</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<!--/span-->
    <div class="pl15">
		<span  class="input-file btn btn-primary btn-sm">批量<input type="file" id="importfile" name ="myfiles" onchange="importfile('importfile')"/></span>
	</div>
</div>
<!--/row-->

<%@ include file="editgoodsstock.jsp"%>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">

/*$("#searchStr").focus(function(){//#input换成你的input的ID 
	$("#searchStr").blur(function(){		
		table.ajax.reload();
	});
});*/

$('#btnSubmit').click(function () {
	table.ajax.reload();
}); 

var storagetype = ${defaultTypeBean.typeId};
var storageid = ${defaultStorage.storageId};
var cityid =  ${districtList.get(0).city.cityId};

$('.storageType').click(function(){
	storagetype = $(this).data("val");
    $('#storagetypeText').text($(this).text());
    var i = ajaxStorage(storagetype,cityid);

    if(storagetype == 1){
    	$('#citydiv').show();
    }else{
    	$('#citydiv').hide();
    }
});

$('.city').click(function(){
	cityid = $(this).data("val");
    $('#cityText').html($(this).text());
    var i = ajaxStorage(storagetype,cityid);

});

function importfile(importfileId){
		  $.ajaxFileUpload({  
			fileElementId: importfileId,
		    url: "importFile?storageId="+storageid, 
		    dataType: 'content',
		    success: function (data, textStatus) {  
		    	var reg = /<pre.+?>(.+)<\/pre>/g;  
		    	var result = data.match(reg);  
		    	data = RegExp.$1;
		    	var obj = eval('(' + data + ')'); 
		    	if(obj.code==0){
		    		alert("导入成功~");
		    		table.ajax.reload(null, false);
		    	}else{
		    		alert(obj.msg);
		    	}
		    },  
		    error: function (XMLHttpRequest, textStatus, errorThrown) {  
		    	alert(errorThrown);
		    }
		  });  
}

var cityList = new Array();  
var table = null;
var watch;

function ajaxStorage(storagetype,city){

/*	$.post("ajaxStorage",{"storagetype": storagetype},function(data){		
		watch = data;
		if(data.code == 0){
			alert(data.msg);
		}
		$('#ulstorage').html("");
	    var flag = 0;
		$.each(data,function(key,values){   
			if( flag == 0 ){
				$('#storageText').html(values.storageName);
				$('#ulstorage').append('<li><a href="javascript:void(0);" class="Storage" data-val="'+key+'">'+values.storageName+'</a></li>');
				storageid = key;
				flag += 1;
			}
			else{
				$('#ulstorage').append('<li><a href="javascript:void(0);" class="Storage" data-val="'+key+'">'+values.storageName+'</a></li>');
			}
		});

		$('.Storage').click(function(){
			storageid = $(this).data("val");
		    $('#storageText').html($(this).text());
		    table.ajax.reload();
		});
	});
	
	*/
	$.ajax({  
         type : "post",  
         url : "ajaxStorage",  
         data : "storagetype="+storagetype+"&cityid="+cityid, 
         async : true,  
         success : function(data){  
     		watch = data;
    		if(data.code == 1){
    			alert(data.msg);
    			return 1;
    		}else{
    		$('#ulstorage').html("");
    		$('#storageText').html("");
    	    var flag = 0;
    		for(var i=0;i<data.storageList.length;i++){
    			var item = data.storageList[i];
    			if( flag == 0 ){
    				$('#storageText').html(item.storageName);
    				$('#ulstorage').append('<li><a href="javascript:void(0);" class="Storage" data-val="'+item.storageId+'">'+item.storageName+'</a></li>');
    				storageid = item.storageId;
    				flag += 1;
    			}
    			else{				
    				$('#ulstorage').append('<li><a href="javascript:void(0);" class="Storage" data-val="'+item.storageId+'">'+item.storageName+'</a></li>');
    			}
    		}   		

    		$('.Storage').click(function(){
    			storageid = $(this).data("val");
    		    $('#storageText').html($(this).text());
    		});
    		return 0;
    		}
         }  
    }); 
}

$(document).ready(function() {
	
	if(storagetype == 1){
		/* storagetype = 1;
	    $('#storagetypeText').html(LDC仓); */
	    var i = ajaxStorage(storagetype,cityid);
	    $('#citydiv').show();
	}else{
		$('#citydiv').hide();
	}
	
	ajaxStorage(storagetype);

	table = $("#storageTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
	       url:"queryStorageGoodsStock",
	       async: false,
	       data: function(d) {
        		d.searchStr=$("#searchStr").val();
        		d.storagetype = storagetype;
        		d.storageid = storageid;
            },
 			dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
        			var operatorstr = '<a href="javascript:void(0)" onclick="purchasestorage(this);">【采购入库】 </a>'+'<a href="javascript:void(0)" onclick="ajuststorage(this);"> 【库存调整】</a>';
        			if(item.storage_type == 2){
        				//虚拟仓
        				operatorstr = '<a href="javascript:void(0)" onclick="purchasestorage(this);">【采购入库】 </a>';
        			}
                    newData[newData.length] = [
            			               item.wms_goods_name+'<input type="hidden" value="'+item.storage_goods_id+'">',
            			               item.wms_goods_gbm,
            			               item.package_spec,
            			               item.unit,
            			               item.shelf_life,
            			               item.stock,
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
function purchasestorage(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var goodsname = $(child[0]).text();
	var goodsid = $(child[0]).find("input").val();
	var storage =  $('#storageText').text();
	$("#goodsname").val(goodsname);
	$("#goodsid").val(goodsid);
	$("#storage").val(storage);
	$("#storageid").val(storageid);
	$("#type_0").show();
	$("#type_1").hide();
	$("#recordtype").val(1);
	$("#goodsname").attr("readonly","readonly");
	$("#storage").attr("readonly","readonly");
	$("#type").attr("readonly","readonly");
	$("#method").val("0");
	$("#price").val("");
	$("#num").val("");
	$("#recordcode").val("");
	$("#remark").val("");
	$("#seller").val("");
	$("#modalEdit").modal();
}

function ajuststorage(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var goodsname = $(child[0]).text();
	var goodsid = $(child[0]).find("input").val();
	var storage =  $('#storageText').text();	
	$("#goodsname").val(goodsname);
	$("#goodsid").val(goodsid);
	$("#storage").val(storage);
	$("#storageid").val(storageid);
	$("#type_0").hide();
	$("#type_1").show();
	$("#recordtype").val(1);
	$("#goodsname").attr("readonly","readonly");
	$("#storage").attr("readonly","readonly");
	$("#type").attr("readonly","readonly");
	$("#method").val("1");	
	$("#price").val("");
	$("#num").val("");
	$("#recordcode").val("");
	$("#remark").val("");
	$("#seller").val("");
	$("#selectedtype").val("");
	$("#modalEdit").modal();
}



  

//验证数据格式
function checkParam(){
	
	var positveNumReg = /^[0-9]*[1-9][0-9]*$/ ;
	
	//六位小数
	var num6Reg =  /^-?\d+\.?\d{0,6}$/;
	//两位 小数 
	var num2Reg =  /^-?\d+\.?\d{0,2}$/;
	
	if($('#price').val().trim().length== 0){
		return "请输入价钱";
	}
	
	if(!num2Reg.test($('#price').val())){
		return "价钱格式错误";
	}
	
	if($('#num').val().trim().length== 0){
		return "请输入数量";
	}
	
	if(!positveNumReg.test($('#num').val())){
		return "数量格式错误";
	}
	
	if($('#recordcode').val().trim() == 0){
		return "单号不能为空";
	}
	
	return null;
}
  
    
</script>