<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../../header.jsp"%>

<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-banner"></i> 红包开关
				</h2>

				 <br>
        		<br>
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i
						class="glyphicon glyphicon-cog"></i></a> <a href="#"
						class="btn btn-minimize btn-round btn-default"><i
						class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
						class="btn btn-close btn-round btn-default"><i
						class="glyphicon glyphicon-remove"></i></a>
				</div>
				<!-- 红包领取方式:
				<label class="radio">
				 	<input name ="checkItem" style="position:relative;margin-left:0px;" type="radio" value="0" checked>先领取再分享
				 	<input name ="checkItem" style="position:relative;margin-left:0px;" type="radio" value="1" >先分享再领取
				</label> -->
				
				
				<table id="switchList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>开关名称</th>
							<th>描述</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
				
		        <br>
        		<br>
			</div>
		</div>
	</div>
	</div>


<%@ include file="../../footer.jsp"%>
<%@ include file="../../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
	
var table = null;
var switchCouponList ;
var urlPrefix = "";

var fissionType = {
		0: "先领取后分享",
		1: "先分享后领取"
	};
	
var switchType = {
		"fissionSwitch": "裂变红包分享流程"
	};

$(document).ready(function() {
	
	table = $('#switchList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: urlPrefix+"ajaxList",
        	data: function(d) {
            },
        	dataSrc: function ( json ) {
        		var newData = [];
        		switchCouponList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var mallIni = json.data[i];
        			var timeMsg = "";
        			 var openOpera ="";
        			if(mallIni.iniValue ==0){
        				
        			}
                    openOpera = '[<a href="javascript:changeSwitch(\''+mallIni.iniName+'\',' + mallIni.iniValue+');">分享流程切换</a>]';
        			newData[newData.length] = [switchType[mallIni.iniName],
        			                           mallIni.iniDesc,
        			                           fissionType[mallIni.iniValue],
        			                           openOpera
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
	
	
	function changeSwitch(switchName, value) {
		
		var msg = fissionType[value];
		
	    if (confirm("确定"+msg+"切换")) {

	        $.getJSON(urlPrefix+"change?switchName="+switchName + "&value=" + value, function (ret) {
	            if (ret.code ==1) {
	            	alert(msg+"切换成功");
	                table.ajax.reload();
	            }else {
	            	alert(msg+"切换失败");
	            }
	        });
	    }
	}
</script>