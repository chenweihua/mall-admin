<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>

<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-couponRule"></i>类目管理
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
							<span class="input-group-addon">品类名称</span> <input type="text"
								id="name" class="form-control" value="${name}">
						</div>
					</div>

					<div class="col-lg-2">
						<div class="input-group">
							<span class="input-group-addon">展示首页</span>
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span id="showIndexText">全部</span>&nbsp; <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="javascript:void(0);" class="btnType"
										data-val="2">全部</a></li>
									<li><a href="javascript:void(0);" class="btnType"
										data-val="1">是</a></li>
									<li><a href="javascript:void(0);" class="btnType"
										data-val="0">否</a></li>
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
				<br /> <br />
				<table id="categoryList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>类目名称</th>
							<!-- <th>类型</th> -->
							<th>类目描述</th>
							<th>类目权重</th>
							<th>创建时间</th>
							<th>更新时间</th>
							<th>激活图片</th>
							<th>未激活图片</th>
							<th>APP图片</th>
							<th>是否首页展示</th>
							<th>首页权重</th>
							<!-- <th>激活图片</th>
                        <th>未激活图片</th> -->
							<th>操作</th>
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


<%@ include file="editOradd.jsp"%>

<%@ include file="../footer.jsp"%>


<script type="text/javascript">
    var showIndexMap = {1:"是",0:"否",2:"全部"};
    var isIndex;
    var isIndex_temp;
     $('.btnType').click(function () {    
        isIndex = $(this).data("val");
        $('#showIndexText').html(showIndexMap[isIndex]);        
    }); 
     
    var table = null;
    var typeEdit;
    //edit
    var operation = "add";
    var categoryList;
    $('#addButton').click(function () {
        operation = "add";
        typeEdit = 0;
        isIndex_temp = 0;
        $('#btnShowIndexText').html(showIndexMap[isIndex_temp]);
        $('#modalBtnType').removeAttr("disabled");
        $('#categoryName').val("");
        $('#description').val("");
        $('#weight').val("");
        $('#indexWeight').val("");
        document.getElementById('imageUrl1').value = "";
    	document.getElementById('uploadPreview1').src = "";
        document.getElementById('imageUrl2').value = "";
    	document.getElementById('uploadPreview2').src = "";
    	document.getElementById('imageUrl3').value = "";
    	document.getElementById('uploadPreview3').src = "";
        $('#modalEdit').modal();
    });
</script>

<script type="text/javascript">
    $('.btnShowIndex').click(function () {
        isIndex_temp = $(this).data("val");
        $('#btnShowIndexText').html(showIndexMap[isIndex_temp]);
    });

    $('#btnSubmitEdit').click(function () {
        var name = $('#categoryName').val().trim();
        if(name.length < 1){
            alert("请输入类目名称");
            return false;
        }
        if(name.length >10){
            alert("类目名称过长");
            return false;
        }
        var description = $('#description').val().trim();
        if(description.length > 512){
            alert("类目描述最长512个字");
            return false;
        }
        var weight = $('#weight').val().trim();
        if(weight.length == 0){
            alert("请输入类目权重");
            return false;
        }else if(!isInt(weight)){
            alert("类目权重必须是正整数");
            return false;
        }
        var indexWeight = $('#indexWeight').val().trim();
        if(isIndex_temp == 0 && indexWeight.length == 0){
            alert("请输入首页权重");
            return false;
        }else if(isIndex_temp == 0 && !isInt(indexWeight)){
            alert("首页权重必须是正整数");
            return false;
        }
        
        
        
        $.post(operation +"?isIndex="+ isIndex_temp+"&showIndex="+isIndex_temp , $("#form1").serialize(), function (response) {
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
        categoryEdit = categoryList[index];
        isIndex_temp = categoryEdit.showIndex; 
        $('#indexWeight').val(categoryEdit.indexWeight);
        $("#indexWeight").removeAttr("disabled"); 
        $("#modalBtnType1").removeAttr("disabled");
    
        $('#btnShowIndexText').html(showIndexMap[isIndex_temp]);
        $('#modalBtnType').attr("disabled","disabled");
        $('#categoryName').val(categoryEdit.categoryName);
        $('#description').val(categoryEdit.description);
        $('#weight').val(categoryEdit.weight);
        $('#categoryId').val(categoryEdit.categoryId);
        $('#labelTypeId' + categoryEdit.labelType).prop("checked",true);
       
        var iconOn = categoryEdit.iconOn;
        var iconOff = categoryEdit.iconOff;
        var appIcon = categoryEdit.appIcon;
        if(iconOn == null){
        	iconOn = "";
        }        
        if(iconOff == null)
        	iconOff = "";
        
        if(appIcon == null){
        	appIcon = "";
        } 
        document.getElementById('imageUrl1').value = iconOn;
    	document.getElementById('uploadPreview1').src = iconOn;
        document.getElementById('imageUrl2').value = iconOff;
    	document.getElementById('uploadPreview2').src = iconOff;
    	document.getElementById('imageUrl3').value = appIcon;
    	document.getElementById('uploadPreview3').src = appIcon;
        $('#modalEdit').modal();
    }
</script>

<script type="text/javascript">
    function del(categoryId) {
    	
        if (confirm("确定删除此类目")) {

            $.getJSON("del?categoryId=" + categoryId, function (ret) {
                if (ret.code != 0) {
                    alert(ret.msg);
                } else {
                	alert(ret.msg);
                    table.ajax.reload();
                }
            });
        }
    }

    function isInt(value){
        return value > 0 && value == parseInt(value);
    }

    //提交搜索条件
    $('#btnSubmit').click(function () {
//        window.location.href = "list?name=" + $("#name").val() + "&isIndex=" + ( (isIndex==null) ? "" : isIndex);
    		table.ajax.reload();
    });
 </script>

<script type="text/javascript">
    var table = null;
    
    $(document).ready(function () {
        //datatable
        
        table = $('#categoryList').DataTable({
            ordering: false,
            processing: true,
            serverSide: true,
            searching: true,
            ajax: {
                url: "json",
    	       	data: function(d) {
            		d.name=$("#name").val().trim();
            		d.isIndex=isIndex;
                },
                dataSrc: function ( json ) {
                    var newData = [];
                    categoryList = json.data;
                    for (var i = 0, len = json.data.length; i < len; i++) {
                        var category = json.data[i];
                        var categoryId = category.categoryId;
                        var labelType = category.labelType;
                        var categoryName = category.categoryName;
                        var description = category.description;
                        var iconOn = category.iconOn;
                        var iconOff = category.iconOff;
                        var appIcon = category.appIcon;
                        var iconOnStr = "没有图片";
                        var iconOffStr = "没有图片";
                        var appIconStr = "没有图片";
                        var weight = category.weight;
                        var showIndex = category.showIndex;
                        var indexWeight = category.indexWeight;
                        var isDel = category.isDel;
                        var updateTime = category.updateTime;
                        var createTime = category.createTime;
                        var operator = category.operator;
                        if(iconOn!=null&&iconOn!=""){
                        	iconOnStr='<a href="'+iconOn+'" target="_BLANK">查看</a>';
                        }
                        if(iconOff!=null&&iconOff!=""){
                        	iconOffStr='<a href="'+iconOff+'" target="_BLANK">查看</a>';
                        }
                        if(appIcon!=null&&appIcon!=""){
                        	appIconStr='<a href="'+appIcon+'" target="_BLANK">查看</a>';
                        }
/*                         var operatorStr= '<a href="javascript:edit(' + i + ');">【编辑】</a>'; */
                        var operatorStr= '<a href="javascript:edit(' + i + ');">【编辑】</a><a href="javascript:void(0)" onclick="del('+ categoryId +');">【关闭】</a>'; 
                        
                        newData[newData.length] = [
                                                    categoryName, /* typeMap[category.isSecKill], */ 
                                                    description, 
                                                    weight,
                            					    new Date(createTime).Format("yyyy-MM-dd hh:mm:ss"),
                                          			new Date(updateTime).Format("yyyy-MM-dd hh:mm:ss"),
                            						iconOnStr,
                            						iconOffStr,
                            						appIconStr,
                            						showIndexMap[showIndex],
                            						indexWeight,
                            						operatorStr,
                            						labelType];
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