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
					<i class="glyphicon glyphicon-item"></i> 商家列表
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
		                	id="searchStr" class="form-control"  placeholder="请输入供应商名称或编码">
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
				<table id="sellerTable"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>编号</th>
							<th>卖家名称</th>
							<th>创建时间</th>
							<th>更新时间</th>
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

<%@ include file="editseller.jsp"%>
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

var table = null;

$(document).ready(function() {
	//datatable
	table = $("#sellerTable").DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
	       	url:"querySeller",
	       	data: function(d) {
        		d.searchStr=$("#searchStr").val();;
            },
 			dataSrc: function ( json ) {
        		var newData = [];
        		itemList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var item = json.data[i];
					var operatorstr = '<a href="javascript:void(0)" onclick="editseller(this);">【修改】</a><a href="javascript:void(0)" onclick="deleteseller(this);">【删除】</a>';
					var createTime=new Date(item.createTime);
                    var updateTime = new Date(item.updateTime);
                    if(updateTime < createTime){
                    	updateTime = createTime;
                    }
                    newData[newData.length] = [
            			               item.sellerId,
            			               item.sellerName,
            			               createTime.Format("yyyy-MM-dd hh:mm:ss"),
            			               updateTime.Format("yyyy-MM-dd hh:mm:ss"),
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

function editseller(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var id=$(child[0]).text();
	var name = $(child[1]).text();
	$("#method").val("0");
	$('#sellerid').val(id);
	$('#sellerdiv').show();
	$('#sellerid').prop("readonly",true);
	$('#sellername').val(name);
	$("#modalEdit").modal();
}

function deleteseller(obj){
	var tr=$(obj).parent().parent();
	var child = tr.find("td");
	var id=$(child[0]).text();
    if (confirm("确定删除该供应商？")) {
        $.getJSON("deleteseller?sellerId="+id, function (ret) {
            if (ret.code == 0) {
                alert(ret.msg);
                table.ajax.reload();
            }else {
            	alert("删除失败");
            }
        });
    }
}



$("#addButton").click(function(){
	$('#sellerdiv').hide();
	$("#method").val("1");
	$('#sellerid').prop("readonly",false);
	$('#sellerid').val("");
	$('#sellername').val("");
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
    
</script>