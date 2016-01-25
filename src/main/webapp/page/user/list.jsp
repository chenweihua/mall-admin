<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-user"></i> 后台用户列表
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
				<table id="userList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>账号</th>
							<th>用户名</th>
							<th>角色</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	<!--/span-->

	<div class="pl15">
		<button type="button" class="btn btn-primary btn-sm" id="addButton">添加用户</button>
	</div>

</div>
<!--/row-->
<%@ include file="../region_tree.jsp"%>
<%@ include file="modifypassword.jsp"%>
<%@ include file="../footer.jsp"%>


<script>
	$("#addButton").click(function() {
		window.location.href = "add";
	});

	var table = null;

	function delUser(userId) {
		if (confirm("确定删除此用户")) {

			$.getJSON("del?user_id=" + userId, function(ret) {
				if (ret.code != 0) {
					alert(ret.msg);
				} else {
					table.ajax.reload();
				}
			});
		}
	}

	function openUser(userId) {
		if (confirm("确定恢复此用户")) {

			$.getJSON("open?user_id=" + userId, function(ret) {

				if (ret.code != 0) {
					alert(ret.msg);
				} else {
					table.ajax.reload();
				}

			});
		}
	}

	function editUser(userId) {
		window.location.href = 'edit?user_id=' + userId;
	}
	
	function modifypassword(userId) {
		$("#modifyuserId").val(userId);
		$("#password1").val("");
		$("#repassword1").val("");
		$("#modalEdit").modal();
	}

	$(document)
			.ready(
					function() {
						//datatable
						table = $('#userList')
								.DataTable(
										{
											ordering : false,
											processing : true,
											serverSide : true,
											ajax : {
												url : "query",
												dataSrc : function(json) {
													var newData = [];
													for ( var i = 0, len = json.data.length; i < len; i++) {
														var user = json.data[i];
														var status = user.is_del == 0 ? "正常"
																: "删除";
														var operator = '<a href="javascript:delUser('
																+ user.user_id
																+ ');">删除</a>&nbsp;&nbsp;<a href="javascript:editUser('
																+ user.user_id
																+ ');">编辑</a>&nbsp;&nbsp;<a href="javascript:modifypassword('
																+ user.user_id
																+ ');">修改密码</a>'
														if (user.is_del != 0) {
															operator = '<a href="javascript:openUser('
																	+ user.user_id
																	+ ');">恢复</a>';
														}else{
															if(user.role.admin_flag!=1){
																operator=operator+'&nbsp;&nbsp;<a href="javascript:getRegion('
																	+ user.user_id
																	+ ');">设置仓库</a>'
															}
														}
														newData[newData.length] = [
																user.account,
																user.user_name,
																user.role.role_name,
																status,
																operator ];
													}
													json.data = newData;
													return newData;
												}
											},
											sDom : "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
											sPaginationType : "bootstrap",
											oLanguage : {
												sLengthMenu : "_MENU_ 记录每页",
												sSearch : "按用户名搜索 _INPUT_",
												sZeroRecords : "暂无数据",
												sProcessing : "正在处理...",
												sEmptyTable : "暂无数据",
												sInfo : "_START_ - _END_ (共_TOTAL_条)",
												sInfoFiltered : "",
												oPaginate : {
													sFirst : "第一页",
													sLast : "最后一页",
													sNext : "下一页",
													sPrevious : "前一页",
												},

											}
										});

					});
	
	
	//设置用户负责的仓库
	var user_id_temp;
	function getRegion(user_id){
		$('#treeEdit').modal('show');
		user_id_temp=user_id;
		/* $.fn.zTree.init($("#selectTree"), setting, zNodes); */
	    $.post("getregion", {"user_id":user_id},function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
	        	 var zNodes = ret.data;
	     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
	     	 }
		},"json"); 
	}


	var setting = {
			check: {
				enable: true,
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			data: {
				simpleData: {
					enable: true
				}
			}
			
		};
	 /*初始化树数据格式*/
	var zNodes =[
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
	$("#selectBtn").click(function(){
		$.fn.zTree.init($("#selectTree"), setting, zNodes);
	});


	//确定
	$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	var info = JSON.stringify(nodes);
	 $.post("setregion",{"user_id":user_id_temp,"region":info}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~")
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
	});
	
</script>