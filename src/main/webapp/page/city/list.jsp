<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/mustache.js"></script>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-couponRule"></i>城市管理
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"> <i
						class="glyphicon glyphicon-cog"></i>
					</a> <a href="#" class="btn btn-minimize btn-round btn-default"> <i
						class="glyphicon glyphicon-chevron-up"></i>
					</a> <a href="#" class="btn btn-close btn-round btn-default"> <i
						class="glyphicon glyphicon-remove"></i>
					</a>
				</div>
			</div>
			<div class="box-content">
				<div class="row">
					<div class="col-lg-3">
						<div class="input-group">
							<span class="input-group-addon">城市名称</span> <input type="text"
								id="name" class="form-control" value="${name}">
						</div>
					</div>

					<div class="col-lg-1">
						<div class="input-group">
							<button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
						</div>
					</div>
				</div>
				<br /> <br />
				<table id="myCityList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>城市名称</th>
							<th>权重</th>
							<th>创建时间</th>
							<th>更新时间</th>
							<th>是否前端展示</th>
							<!-- <th>是否休业</th> -->
							<th>UC ID</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
</div>
<!--/span-->
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加城市</button>
<!--/row-->


<%@ include file="editOraddcity.jsp"%>

<%@ include file="../footer.jsp"%>

<script id="secCityListInCityTableTemplate" type="text/template">
	<p>二级城市信息：</p>
	<table border='1'><tr><td>城市名称</td><td>权重</td><td>创建时间</td><td>更新时间</td><td>是否前端展示</td><td>UC ID</td><td>操作</td></tr>
        {{#secCityListInCity}}
            <tr>	
				<td> {{cityName}}</td>
				<td> {{weight}} </td>
				<td> {{createTime}} </td>
				<td> {{updateTime}} </td>
				<td> {{isShowMap}} </td>
				<td> {{ucId}} </td>
				<td> <a href="javascript:edit2({{index}});">【编辑】</a></td>
			</tr>
        {{/secCityListInCity}}
	</table>
</script>
				<!-- <td>是否休业</td>  <td> {{isStopMap}} </td> -->
<script type="text/javascript">
    var isShowMap = {0:"否",1:"是"};
    var isStopMap = {0:"否",1:"是"};
    var isShow;
    var isStop;
	var level;
	var cityName;
	var pid;
    var table = null;
    var typeEdit;
    //edit
    var operation = "add";
    var cityList;
    $('#addButton').click(function () {
        operation = "add";
        level = 1;
        isShow = 0;
        isStop = 0;
        cityName = "";
        $('#btnIsShow').html(isShowMap[isShow]);
        $('#btnIsStop').html(isStopMap[isStop]);
        $('#childCityName').attr("style", "display:none;");
        $('#pCityName').removeAttr("disabled");
        $('#modalBtnType').removeAttr("disabled");
        $('#pCityName').val("");
        $('#weight').val("");
        $('#ucId').val("");
        $('#btnIsShowText').html(isShowMap[0]);
        $('#btnIsStopText').html(isStopMap[0]);
        $('#modalEdit').modal();
    });
    $('.btnIsShow').click(function () {
        isShow = $(this).data("val");
        $('#btnIsShowText').html(isShowMap[isShow]);
    });
    
    $('.btnIsStop').click(function () {
        isStop = $(this).data("val");
        $('#btnIsStopText').html(isStopMap[isStop]);
    });
    
    $('#btnSubmitEdit').click(function () {
    	var cityName;
    	if(level == 1) {
    		cityName = $("#pCityName").val().trim();
    		pid = 0;
    	} else if(level == 2) {
    		cityName = $("#cCityName").val().trim();
    	}
    	var name = cityName;
        if(name.length < 1 || name.length > 128){
            alert("请输入城市名称");
            return false;
        }
        var weight = $('#weight').val().trim();
        if(weight.length == 0){
            alert("请输入权重");
            return false;
        }else if(!isInt(weight)){
            alert("权重必须是正整数");
            return false;
        }
        
        var ucId = $('#ucId').val().trim();
        if(ucId.length != 0){
        	if(!isInt(weight)){
            	alert("UC ID必须是正整数");
            	return false;
        	}
        }
        
        $('#level').val(level);
        $('#pid').val(pid);
        $('#isShow').val(isShow);
        $('#isStop').val(isStop);
        $('#cityName').val(cityName);
        console.log($("#form1").serialize());
        $.post(operation, $("#form1").serialize(), function (response) {
            if (response.code == 0) {
                alert("操作成功");
                table.ajax.reload(null, false);
                $('#modalEdit').modal('hide');
            } else {
                alert(response.msg);
            }
        });

        return false;
    });
</script>

<script type="text/javascript">
    function edit(index) {
        operation = "edit";
        cityEdit = cityList[index];
        isShow = cityEdit.isShow;
        isStop = cityEdit.isStop;
        level = cityEdit.level;
        cityName = cityEdit.cityName;
        pid = cityEdit.pid;
        
        $('#level').val(level);
    	$('#pid').val(pid);
    	$('#weight').val(cityEdit.weight);
        $('#cityId').val(cityEdit.cityId);
        $('#pCityName').val(cityName);
        $('#ucId').val(cityEdit.ucId);
        
    	$("#pCityName").removeAttr("disabled");
        $("#modalBtnType1").removeAttr("disabled");
        $("#childCityName").attr("style","display:none;");
        $('#btnIsShowText').html(isShowMap[isShow]);
        $('#btnIsStopText').html(isStopMap[isStop]);
        $('#modalEdit').modal();
    }
    
    function edit2(index) {
        operation = "edit";
        cityEdit = cityList2[index];
        console.log(cityEdit);
        isShow = cityEdit.isShow;
        isStop = cityEdit.isStop;
        level = cityEdit.level;
        cityName = cityEdit.cityName;
        pid = cityEdit.pid;
        var city = secCityList[index];
        
        $('#level').val(level);
    	$('#pid').val(pid);
    	$("#pCityName").val(city.pCityName);
    	$('#cCityName').val(cityName);
        $('#weight').val(cityEdit.weight);
        $('#cityId').val(cityEdit.cityId);
        if(cityEdit.ucId != undefined) {
        	$('#ucId').val(cityEdit.ucId);
    	} else {
    		$('#ucId').val("");
    	}
    	
        $("#modalBtnType1").removeAttr("disabled");
        
        $("#childCityName").removeAttr("style");
        $("#pCityName").attr("disabled","disabled");
        $('#btnIsShowText').html(isShowMap[isShow]);
        $('#btnIsStopText').html(isStopMap[isStop]);
        $('#modalBtnType').attr("disabled","disabled");
        $('#modalEdit').modal();
    }
</script>

<script type="text/javascript">
    function del(cityId) {
    	
        if (confirm("确定删除该城市？")) {

            $.getJSON("del?cityId=" + cityId, function (ret) {
                if (ret.code != 0) {
                    alert(ret.msg);
                } else {
                	alert(ret.msg);
                    table.ajax.reload();
                }
            });
        }
    }
    
	function add(index) {
		 operation = "add";
		 var city = cityList[index];
	     level = 2;
	     isShow = 0;
	     isStop = 0;
	     cityName = "";
	     $('#cityId').val(city.cityId);
	     $('#btnIsShow').html(isShowMap[isShow]);
	     $('#btnIsStop').html(isStopMap[isStop]);
	     $('#pCityName').attr("disabled","disabled");
	     $('#childCityName').removeAttr("style");
	     $('#modalBtnType').removeAttr("disabled");
	     $('#pCityName').val(city.cityName);
	     $('#cCityName').val("");
	     $('#weight').val("");
	     $('#ucId').val("");
	     $('#btnIsShowText').html(isShowMap[0]);
	     $('#btnIsStopText').html(isStopMap[0]);
	     $('#modalEdit').modal();
    }
	
    function isInt(value){
        return value > 0 && value == parseInt(value);
    }
    //提交搜索条件
    $('#btnSubmit').click(function () {
        window.location.href = "list?name=" + $("#name").val();
    });
    
 </script>
 
 <script type="text/javascript">
 
 	function fillDiv(secCityList) {
 		var secCityListInCityTable = null;
 		if (secCityList == null || secCityList.length == 0) {
 			secCityListInCityTable = "没有二级城市信息";
		} else {
			secCityListInCityTable = fillTable(secCityList);
		}
		var divStr = '<tr class="yellowbox padding10"><td colspan="14" align="center">'
				+ '<table>'
				+ '<tr><td></td></tr>'
				+ '<tr><td id=block>'
				+ secCityListInCityTable
				+ '</td></tr>';

		return divStr;
	}
	
 	var tableTemplate = document.getElementById('secCityListInCityTableTemplate').innerHTML;
 	var tableTemplateData = {};
 	
	function fillTable(secCityList) {
		tableTemplateData.secCityListInCity = secCityList;
		return Mustache.render(tableTemplate, tableTemplateData);
	}
	
	//隐藏二级城市列表
	function hideSecCityList(obj, cityId) {
		$(obj).attr("onclick", "showSecCityList(this," + cityId +")");
		var nextTr = $(obj).parent().parent().next();
		console.log(nextTr);
		if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
			nextTr.remove();
		}
	}
	
	var secCityList;
	//展示二级城市列表
	function showSecCityList(obj, cityId) {
		$(obj).attr("onclick", "hideSecCityList(this," + cityId +")");
		$.post("json/secCityList", {
			"cityId" : cityId
		}, function(result) {
			console.log(result.data);
            secCityList = result.data.secCityListInCity;
            
            var newData = [];
            cityList2 = result.data.secCityListInCity;
            for (var i = 0, len = cityList2.length; i < len; i++) {
                var city = cityList2[i];
                var cityId = city.cityId;
                var cityName = city.cityName;
                var weight = city.weight;
                var isShow = city.isShow;
                var isDel = city.isDel;
                var level = city.level;
                var isStop = city.isStop;
                var pid = city.pid;
                var updateTime = city.updateTime;
                var createTime = city.createTime;
                var operatorId = city.operatorId;
                secCityList[i].updateTime = new Date(updateTime).Format("yyyy-MM-dd hh:mm:ss");
                secCityList[i].createTime = new Date(createTime).Format("yyyy-MM-dd hh:mm:ss");
                secCityList[i].isShowMap = isShowMap[isShow];
                secCityList[i].isStopMap = isStopMap[isStop];
                secCityList[i].index = i;
                secCityList[i].ucId = city.ucId;
                secCityList[i].pCityName = $(obj).text();
            }
            
            
            var secCityListInCityDiv = fillDiv(secCityList);
            console.log(secCityList);
            var nextTr = $(obj).parent().parent().next();

    		var trClass = nextTr.attr("class");
    		if (trClass == undefined || trClass.indexOf("yellowbox") < 0) {
    			$(obj).parent().parent().after(secCityListInCityDiv);
    		}
		});
	}
</script>

<script type="text/javascript">
    var table = null;
    
    $(document).ready(function () {
        //datatable
        table = $('#myCityList').DataTable({
            ordering: false,
            processing: true,
            serverSide: true,
            searching: true,
            ajax: {
                url: "json?name=${name}",
                dataSrc: function ( json ) {
                    var newData = [];
                    cityList = json.data;
                    for (var i = 0, len = json.data.length; i < len; i++) {
                        var city = json.data[i];
                        var cityId = city.cityId;
                        var cityName = city.cityName;
                        var weight = city.weight;
                        var isShow = city.isShow;
                        var isDel = city.isDel;
                        var level = city.level;
                        /* var isStop = city.isStop; */
                        var pid = city.pid;
                        var updateTime = city.updateTime;
                        var createTime = city.createTime;
                        var operatorId = city.operatorId;
                        var operatorStr = "";
                        var cityNameLink = '<a href="javascript:void(0)" onclick="showSecCityList(this,'+ cityId + ');">'+ cityName +'</a>';
                        if(level == 2) {
                        	cityNameLink = cityName;
                        }
                        if(level == 2) {
                        	operatorStr= '<a href="javascript:edit(' + i + ');">【编辑】</a><a href="javascript:void(0)" onclick="del('+ cityId +');">【删除】</a>';
                        } else if(level == 1) {
                        	operatorStr= '<a href="javascript:edit(' + i + ');">【编辑】</a>'
                        	+'<a href="javascript:void(0)" onclick="add('+ i +');">【添加区】</a>';
                        }
                        
                        
                        newData[newData.length] = [
                                                    cityNameLink, /* typeMap[category.isSecKill], */ 
                                                    weight,
                            					    new Date(createTime).Format("yyyy-MM-dd hh:mm:ss"),
                                          			new Date(updateTime).Format("yyyy-MM-dd hh:mm:ss"),
                            						isShowMap[isShow],
/*                             						isStopMap[isStop], */
													city.ucId == undefined ? "" : city.ucId,
                            						operatorStr];
                    }
                    json.data = newData;
                    return newData;

                }
            },
            sDom: "<'row'<'col-md-6'l><>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
            sPaginationType: "bootstrap",
            oLanguage: {
                sLengthMenu: "_MENU_ 记录每页",
                sSearch: "",
                sZeroRecords: "暂无数据",
                sProcessing: "正在处理...",
                sEmptyTable: "暂无数据",
                sInfo: "_START_ - _END_ (共_TOTAL_条)",
                sInfoFiltered: "",
                oPaginate: {
                    sFirst: "第一页",
                    sLast: "最后一页",
                    sNext: "下一页",
                    sPrevious: "前一页"

                }

            }
        });

    });
</script>
