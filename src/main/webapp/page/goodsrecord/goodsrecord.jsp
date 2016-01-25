<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ include file="../header.jsp"%>
<link href='/css/multilevelnavbar.css' rel="stylesheet" >

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/print.js"></script>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i> 调整记录
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
		          	<!-- 文本搜索 -->
		          	<div class="col-lg-3">
		            	<div class="input-group">
		             	 <span class="input-group-addon">名称或条码</span> <input type="text"
		                	id="searchStr" class="form-control"  placeholder="请输入名称或条码">
		           	 	</div>
		          	</div>
		          	<!-- 仓库类型 -->
		            <div class="col-lg-2">
		            	<div class="input-group">
		             		 <span class="input-group-addon">仓库类型</span>
		             		 <div class="btn-group">
		               		 <button type="button" class="btn btn-default dropdown-toggle"
		                 		 data-toggle="dropdown">
		                 		    <span id="storageTypeText">${defaultTypeBean.typeName}</span>&nbsp;<span class="caret"></span>
		                		</button>
		                		<ul class="dropdown-menu menu-top" role="menu">                
		                          	<c:forEach items="${storageTypeList}" var="storageType">
										<li><a href="javascript:void(0);" class="StorageType"
										data-val="${storageType.typeId }">${storageType.typeName }</a></li>
									</c:forEach>	                        		
		                		</ul>
		             	 </div>
		            	</div>
		          </div>
		          <!-- 城市 -->
		          <div class="col-lg-2">
		            <div class="input-group">
		              <span class="input-group-addon">城市</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="cityText">
		                  	${defaultDistrict.city.cityName}
		                  </span> <span
		                    class="caret"></span>
		                </button>
						<ul class="dropdown-menu" role="menu" id="ulcity">
							<c:forEach var="district" items="${districtList}">
								<li><a href="javascript:void(0);" class="City"
												data-val="${district.city.cityId}">${district.city.cityName}</a>
	<!--  							 <ul class="dropdown-menu">
									 	<c:forEach var="city" items="${district.areas}">
									 		<li><a href="javascript:void(0);" class="City"
												data-val="${city.cityId}">${city.cityName}</a></li>
									 	</c:forEach>
									 </ul>-->	
								</li>
							</c:forEach>			
						</ul>
		              </div>
		            </div>
		          </div>
		          
		          <!-- 仓库 -->
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
		          
		           <!-- 供应商-->
		         <div class="col-lg-2">
		            <div class="input-group">
		              <span class="input-group-addon">供应商</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="sellerText">全部</span> <span
		                    class="caret"></span>
		                </button>
						<ul class="dropdown-menu" role="menu" id="ulseller">
							<li><a href="javascript:void(0);" class="Seller"
									data-val="-1">全部</a></li>
							<c:forEach var="seller" items="${sellerList}">
								<c:choose> 
									<c:when test="${seller.isDel eq 0}">
								<li><a href="javascript:void(0);" class="Seller"
									data-val="${seller.sellerId}">${seller.sellerName}</a></li>
								</c:when>
								</c:choose>
							</c:forEach>								
						</ul>
		              </div>
		            </div>
		          </div>
		          
		          		   			    
		    <!-- 更改类型 -->		
		    <div class="col-lg-2">
		         <div class="input-group">
		              <span class="input-group-addon">记录类型</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="recordTypeText"> 全部</span> <span
		                    class="caret"></span>
		                </button>
						<ul class="dropdown-menu" role="menu" id="ulstorage">
							<li><a href="javascript:void(0);" class="RecordType"
									data-val="-1">全部</a></li>
							<c:forEach var="recordType" items="${recordTypeList}">

										<li><a href="javascript:void(0);" class="RecordType"
											data-val="${recordType.id}">${recordType.name}</a></li>
							</c:forEach>								
						</ul>
		             </div>
		         </div>
		      </div> 
		      
		      <!-- 支付状态 -->
		      <div class="col-lg-2">
		            	<div class="input-group">
		             		 <span class="input-group-addon">支付状态</span>
		             		 <div class="btn-group">
		               		 <button type="button" class="btn btn-default dropdown-toggle"
		                 		 data-toggle="dropdown">
		                 		   <span id="payStatusText">
		                  			全部
		                  		</span> <span
		                    class="caret"></span>
		                		</button>
		                		<ul class="dropdown-menu menu-top" role="menu"> 
		                			<li><a href="javascript:void(0);" class="PayStatus"
		                        		data-val="-1">全部</a>
		                        	</li>               
		                          	<li><a href="javascript:void(0);" class="PayStatus"
		                        		data-val="0">未支付</a>
		                        	</li>
		                        	<li><a href="javascript:void(0);" class="PayStatus"
		                        		data-val="1">待支付</a>
		                        	</li>
		                        	<li><a href="javascript:void(0);" class="PayStatus"
		                        		data-val="2">已支付</a>
		                        	</li>		                       	                        		
		                		</ul>
		             	 </div>
		            	</div>
		          </div>
		      
		      
		           <!-- 时间 -->	  
		     	 <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">开始时间</span> <input type="text"
		                id="start-time" class="form-control" value="${startDate}">
		            </div>
		          </div>
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">结束时间</span> <input type="text"
		                id="end-time" class="form-control" value="${endDate}">
		            </div>
		          </div>
		          
		          <div class="col-lg-1">
					<div class="input-group">
						<button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
					</div>
				  </div> 	
		          
		                   		         		    		          
		   		 </div>   	    	
			<div class="box-content">
				<input type='button' value="加入结算池" id="apply" class="btn btn-default"  style="margin-bottom: 10px;"> 
				<table id="recordTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th><input type="checkBox"  id="checkAll">序号</th>
							<th>商品名称</th>
							<th>条码</th>
	             			<th>供应商</th>
							<th>数量</th>
							<th>价格</th>
							<th>调整类型</th>
							<th>入库单号</th>
							<th>入库时间</th>
							<th>状态</th>
							<th>结算单号</th>
							<th>会计数量</th>
							<th>会计单价</th>
							<th>支付金额</th>
							<th>应付金额</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
				
											
				<input type='button' value="加入结算池" id="apply1" class="btn btn-default"> 
				<input type='button' value="导出" id="btnExport" class="btn btn-default"> 
				
				<div id="paypool" style="display:none">
					<div class="box-header well" data-original-title="">
						<h2>
							<i class="glyphicon glyphicon-banner"></i> 结算池
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
					
					<table id="lockRecordList"
						class="table table-striped table-bordered bootstrap-datatable datatable responsive">
						<thead>
							<tr>
								<th>序号</th>
								<th>商品名称</th>
								<th>条码</th>
		             			<th>供应商</th>
								<th>数量</th>
								<th>单价</th>
								<th>总价</th>
								<th>入库时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<input type='button' value="提交" id="applycheck" class="btn btn-default"> 				
				</div>
				
			</div>			
		</div>
	</div>
</div>
<!--/row-->

<%@ include file="editgoodsrecord.jsp"%>
<%@ include file="applypay.jsp"%>		
<%@ include file="../footer.jsp"%>

<script type="text/javascript">


$('#btnSubmit').click(function () {
	table.ajax.reload();
	locktable.ajax.reload();
}); 

var storagetype = ${defaultTypeBean.typeId};
var storageid = ${defaultStorage.storageId};
var cityid = ${defaultDistrict.city.cityId};
var sellerid = -1;
var watchSeller = ${sellerList.get(0).isDel};
var recordtype = -1;
var paystatus = -1;
var sellerMap={}
var recordTypeMap = {};
var table = null;
var locktable = null;
$('.StorageType').click(function(){
	storagetype = $(this).data("val");
    $('#storageTypeText').html($(this).text());  
    getStorageOption(storagetype,cityid);
});

$('.Seller').click(function(){
	sellerid = $(this).data("val");
	$('#sellerText').html($(this).text());

});

$('.City').click(function(){
	cityid = $(this).data("val");
	$('#cityText').html($(this).text());
	getStorageOption(storagetype,cityid)
});

$('.Storage').click(function(){
	storageid = $(this).data("val");
	$('#storageText').html($(this).text());
});
$('.RecordType').click(function(){
	recordtype = $(this).data("val");
	$('#recordTypeText').html($(this).text());
});
$('.PayStatus').click(function(){
	paystatus = $(this).data("val");
	$('#payStatusText').html($(this).text());
});

function getStorageOption(storagetype,cityid){
    $.post("getStorageOption",{"storagetype": storagetype,"cityid":cityid},function(data){		
		if(data.code == 0){
			alert(data.msg);
		}
		$('#ulstorage').html("");
		$('#storageText').html("");
   		var flag = 0;
		for(var i=0;i<data.storageList.length;i++){  
			var item = data.storageList[i];
				if(i == 0){
					$('#storageText').html(item.storageName);
					storageid = item.storageId;
				}
				$('#ulstorage').append('<li><a href="javascript:void(0);" class="Storage" data-val="'+item.storageId+'">'+item.storageName+'</a></li>');
		}
		
//  		table.ajax.reload();
//   		locktable.ajax.reload();

		$('.Storage').click(function(){
			storageid = $(this).data("val");
	   		$('#storageText').html($(this).text());
		});
	});
}

$(document).ready(function() {
	
	$.ajax({  
        type : "post",  
        url : "getParamMap",  
        async : false,  
        success : function(data){  
   			if(data.code == 0){
   				alert(data.msg);
   			}
   			sellerMap=data.sellerMap;
   			recordTypeMap = data.recordTypeMap;
        }  
   });
	
	
		
	table = $("#recordTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
	    	url:"queryStorageGoodsRecord",
	   		async: false,
		    data: function(d) {
	        	d.searchStr=$("#searchStr").val().trim();
	        	d.sellerid = sellerid;
	        	d.storageid = storageid;
	        	d.recordtype = recordtype;
	        	d.paystatus = paystatus;
	            d.startDate = $('#start-time').val();
	            d.endDate = $('#end-time').val();
	        },
 			dataSrc: function ( json ) {
 				watch = json;
 				 $("#checkAll").prop("checked",false);

        		var newData = [];
        		var start = json.start*1+1;
        		for (var i=0, len = json.data.length; i<len; i++)  {
        			var item = json.data[i];
        			var recordTime = new Date();
        			recordTime.setTime(item.record_time);
        			
        			var index = (i*1+start*1)+'<input type="hidden" value='+item.id+'>';
        		
					var operatorstr = '<a href="javascript:void(0)" onclick="editRecord(this);">【编辑】 </a>';        
					var status =  item.pay_status;
					var lockstatus = item.lock_status;
					
					var recordtype = item.recordtype;
					var record = "未知";
					if(recordTypeMap[recordtype] != undefined && recordTypeMap[recordtype] != null){
						record = recordTypeMap[recordtype].name;
					}

					var seller = "未知";
					if(sellerMap[item.sellerId] != undefined && sellerMap[item.sellerId] != null){
						seller = sellerMap[item.sellerId].sellerName;
					}
					 
					var statusStr="未知";
	                    if(status==0&&lockstatus==0&&recordtype==1){
	                    	statusStr="未支付";
	                    	index='<input type="checkBox" name="goodsId" value="'+item.id+'">'+(i*1+start*1);
	                    } if(status==0&&lockstatus==0&&(recordtype!=1)){
	                    	statusStr="未支付";
	                    }else if(status==0&&lockstatus==1){
	                    	statusStr="未支付("+item.lock_userName+"已占用)";
	                    	operatorstr="禁止编辑";
	                    }else if(status==1){
	                    	statusStr="待支付";
	                    	operatorstr="禁止编辑";
	                    }else if(status==2){
	                    	statusStr="已支付";
	                    	operatorstr="禁止编辑";
	                 }
                    

	                    
					newData[newData.length] = [
                                       index,
            			               item.wmsgoodsname,
            			               item.wmsgoodsgbm,
            			               seller+'<input type="hidden" value="'+item.sellerId+'">',
            			               item.num,
            			               item.price/100.0,
            			               record,
            			               item.recordcode,
            			               recordTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               statusStr,
            			               item.apply_pay_code,      							  
        							   item.account_num,
        							   item.account_price/100.0,
        							   item.account_money/100.0,
        							   item.num*item.price/100.0,
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
	
		
	locktable = $('#lockRecordList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: "lockRecordList",
		    data: function(d) {
	        	d.searchStr=$("#searchStr").val().trim();
	        	d.sellerid = sellerid;
	        	d.storageid = storageid;
	        	d.recordtype = recordtype;
	        	d.paystatus = paystatus;
	            d.startDate = $('#start-time').val();
	            d.endDate = $('#end-time').val();
	        },
        	dataSrc: function ( json ) {
        		var start = json.start*1+1;
        		
        		var newData = [];
        		var rownum=0;
        		var summoney = 0;
        		if(json.data.length>0){
        			for (var i=0, len = json.data.length; i<len; i++) {      				 
            			var item = json.data[i];
            			var recordTime = new Date();
                        recordTime.setTime(item.record_time);
                        summoney+=item.num*item.price;
                        var index = (i*1+start*1)+'<input type="hidden" value='+item.id+'>';
//             			var row = [(i+start)+'<input type="hidden" value='+goodsRecord.id+'>',
            			var row = [index,
            			           item.wmsgoodsname,
        			               item.wmsgoodsgbm,
        			               sellerMap[item.sellerId].sellerName+'<input type="hidden" value='+item.sellerId+'>',
            			           item.num,
            			           item.price/100.0,
            			           item.num*item.price/100.0,
            			           recordTime.Format("yyyy-MM-dd hh:mm:ss"),
            			           '<a href="javascript:removerecord(\''+item.id+'\');">删除</a>'
            			           ];
            			
            			newData[newData.length] = row;
            			rownum=rownum+1;
            		}
            		
            		var row = [" ",
        			           " ",
        			           " ",
        			           " ",
        			           " ",
        			           "合计:",
        			           summoney/100,
        			           " ",
        			           " "
        			           ];
            		
            		newData[rownum]=row;
            		json.data = newData;
            		$("#paypool").css('display','block'); 
        		}else{
        			$("#paypool").css('display','none'); 
        		}
        		
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "按手机号搜索 _INPUT_",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...",
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页",
            },

        }
    } );
	
/**   	$.each(sellerList,function(i,values){   
   		$('#sellerId').append('<option value="'values.sellerId+'">'+values.sellerName+'</option>');
	});*/
    
} );


var temp;
function editRecord(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var id = $(child[0]).find("input").val();
	var num = $(child[4]).text();
	var price = $(child[5]).text();
	var sellerId =  $(child[3]).find("input").val();

	$('#recordId').val(id);
	$('#count').val(num);
	$('#price').val(price);
	$('#sellerId').val(sellerId);
	$('#remark').val("");
	
	$("#modalEdit").modal();
}

//添加到转移池按钮点击
$("#apply1").click(function(){
	modifystatus();
});
$("#apply").click(function(){
	modifystatus();
});

$('#btnExport').click(function () {
	window.location.href = "exportrecord?startDate="+$('#start-time').val()+"&endDate="+$('#end-time').val()+"&searchstr="+$("#searchStr").val().trim()+"&sellerId="+sellerid+"&paystatus="+paystatus
			+"&recordtype="+recordtype+"&storageid="+storageid+"&recordtype="+recordtype;	
});

function modifystatus(){
	//获取勾选的资产的ID集合
	 var goodsrecordIds="";  
       $("input[name=goodsId]").each(function() {  
           if ($(this).prop("checked")) {  
           	goodsrecordIds += $(this).val()+",";  
           }  
       }); 
//	        alert("assestsIds:"+assestsIds);
       
       $.post("addtopool",{'goodsrecordids':goodsrecordIds,'storageid':storageid},function(ret){
				alert(ret.msg);
				if(ret.code==0){
					table.ajax.reload(null, false);
					locktable.ajax.reload();
				}
       },"json");
//清空选择
       $("input[name='checkAll']").prop("checked",false);
}

//全选
$("#checkAll").click(function(){  
	if($("#checkAll").prop("checked")==true){
		 $("input[name='goodsId']").each(function(){
			   $(this).prop("checked",true);
		}); 
		//alert();
	}else{
		$("input[name='goodsId']").each(function(){
			   $(this).prop("checked",false);
		});
	}
    
 });
 
function removerecord(recordId){
	$.post("removefrompool",{'recordId':recordId},function(ret){
		alert(ret.msg);
		if(ret.code==0){
			table.ajax.reload();
			locktable.ajax.reload();
		}
	},"json");
}

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

$('#start-time').on('changeDate', function(ev){
	table.ajax.reload();
	locktable.ajax.reload();
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
  
$('#end-time').on('changeDate', function(ev){
	table.ajax.reload();
	locktable.ajax.reload();
});


//验证数据格式
function checkParam(){
	
	var positveNumReg = /^[0-9]*[0-9][0-9]*$/ ;
	
	//六位小数
	var numReg =  /^-?\d+\.?\d{0,6}$/;
	
	var num2Reg =  /^-?\d+\.?\d{0,2}$/;
	
	if($('#count').val().trim().length== 0){
		return "请输入数量";
	}
	
	if(!positveNumReg.test($('#count').val())){
		return "数量格式错误";
	}
	
	if($('#price').val().trim().length== 0){
		return "请输入价钱";
	}
	
	if(!num2Reg.test($('#price').val())){
		return "价钱格式错误";
	}
	
	if($('#remark').val().trim().length == 0){
		return "备注不能为空";
	}
	
	return null;
}
  

</script>