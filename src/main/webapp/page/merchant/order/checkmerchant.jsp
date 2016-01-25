<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mall.admin.enumdata.MerchantType"%>
<c:set var="MerchantType" value="<%=MerchantType.values()%>"/>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width:800px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
					<div class="box-content">
		        <div class="row">
		        
		        	<div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">商户名称</span> 
			              <input type="text" id="merchantNameSearch" class="form-control" value="" placeholder="">
			            </div>
			        </div>
			        
			        <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">联系人</span> 
			              <input type="text" id="shopOwnerSearch" class="form-control" value="${navigationNameQuery}" placeholder="">
			            </div>
			        </div>
			        
			       <div class="col-lg-2">
			            <div class="input-group">
			              <span class="input-group-addon">商户状态</span>
			              <div class="btn-group">
			                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                	<span id="statusQueryText">不限</span>&nbsp;
			                	<span class="caret"></span>
			                </button>
			                <ul class="dropdown-menu" role="merchantStatus">
			                  <li><a href="javascript:void(0);" class="statusQuery"  data-val="0">不限</a></li>
			                  <c:forEach items="${MerchantStatus}" var="merchantStatus">
			                  		<li><a href="javascript:void(0);" class="statusQuery"  data-val="${merchantStatus.type}">${merchantStatus.name}</a></li>
			                  </c:forEach>
			                </ul>
			              </div>
			            </div>
			         </div>
		        			          
			          <div class="col-lg-1">
			            <div class="input-group">
			              <button type="button" id="searchMerchantButton" class="btn btn-default">搜索</button>
			            </div>
			          </div>
					
					</div>
				
		        <br />
        		<br />
				<div class="tabbable" id="tabs-677904">
				
				<div class="tab-content">
					<div class="tab-pane active" contenteditable="false" id="merchatListDiv">
						<table id="merchantList" class="table table-striped table-bordered bootstrap-datatable datatable responsive">
							<thead>
								<tr>
										<th style="width: 5%"><input type="checkBox"  id="checkAll">全部</th>
										<th style="width: 5%">商户名称</th>
										<th style="width: 5%">商户类型</th>
										<th style="width: 5%">商户ID</th>
										<th style="width: 5%">联系人</th>
										<th style="width: 5%">联系电话</th>
										<th style="width: 5%">商户状态</th>
								</tr>
							</thead>
						</table>
					</div>
					<button type="button" class="btn btn-primary btn-sm" id="submit">确定</button>
					
				</div>
			</div>
			</div>
      </div>
  </div>
</div>
<script>

var merchantTable;
$("#searchMerchantButton").click(function(){
	merchantTable.ajax.reload(null,true);
});
var merchantStatus=0;
$('.statusQuery').click(function() {
	merchantStatus = $(this).data("val");
	$('#statusQueryText').html($(this).text());
});

$(document).ready(function() {
	merchantTable = $('#merchantList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	dataType: 'json', 
   			type: "POST", 
        	url: "../query",
        	data: function(d) {
                d.merchantname = $("#merchantNameSearch").val();
                d.shopowner = $("#shopOwnerSearch").val();
                d.status=merchantStatus;
             },
        	dataSrc: function ( json ) {
        		var newData = [];
        		merchantList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var merchant = json.data[i];
					var statusStr = "";
					if(merchant.status==1){
						statusStr="使用中";
					}else{
						statusStr="停用中";
					}
					var checkInfo = "";
				/* 	if(isAllCheck){
						checkInfo = '<input type="checkbox" disabled="true" checked="true" name="mechantCheck" onclick="initCheckBox()" value="'+merchant.merchantNo+','+merchant.merchantName+'">';
					}else{
						checkInfo = '<input type="checkbox" name="mechantCheck" onclick="initCheckBox(this)" value="'+merchant.merchantNo+','+merchant.merchantName+'">';
					} */
					checkInfo = '<input type="checkbox" name="mechantCheck" onclick="initCheckBox(this)" value="'+merchant.merchantNo+','+merchant.merchantName+'">';
        			newData[newData.length] = [
														checkInfo,
														merchant.merchantName,
        									   			merchant.merchantTypeStr,
        									   			merchant.merchantNo,
        									   			merchant.shopOwner,
        									   			merchant.ownerPhone,
        									   			statusStr
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

var isAllCheck=false;
//全选
$("#checkAll").click(function(){  
	if($("#checkAll").prop("checked")==true){
		isAllCheck= true;
		 $("input[name='mechantCheck']").each(function(){
			   $(this).prop("checked",false);
			  // $(this).prop("disabled",true);
		}); 
		//alert();
	}else{
		isAllCheck = false;
		$("input[name='mechantCheck']").each(function(){
//			   $(this).prop("checked",false);
			 //  $(this).removeAttr("disabled");
		});
	}
    
 });

$("#submit").click(function(){
	//获取勾选的资产的ID集合
	var merchantNos="";
	var merchantNames="";
	if(isAllCheck){
		var merchantNos="-1";
		var merchantNames="所有商户";
	}else{
		$("input[name='mechantCheck']").each(function(){
			if ($(this).prop("checked")==true) {  
				var infos = $(this).val();
				var info = infos.split(",");
				if(merchantNames==""){
					merchantNos=info[0];
					merchantNames=info[1];
				}else{
					merchantNos=merchantNos+","+info[0];
					merchantNames=merchantNames+","+info[1];
				}

	        }  
	    }); 
	}
	
    if(searchType==1){
    	  $("#merchantNames").val(merchantNames);
    	  $("#merchantNos").val(merchantNos);
    }
    if(searchType==2){
    	$("#merchantNames_all").val(merchantNames);
  	  $("#merchantNos_all").val(merchantNos);
    }
    $("#modalEdit").modal('hide');
});

function initCheckBox(obj){
	if($(obj).prop("checked")==true){
		$("#checkAll").prop("checked",false);
		 $("input[name='mechantCheck']").each(function(){
				if(this!=obj){
					$(this).prop("checked",false);
				}
		}); 
	}
}

</script>