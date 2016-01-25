<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>


<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-edit"></i>
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
				<form role="form" id="form1">
					<input type="hidden" name="userId" value="${newuser.user_id }" />
					<div class="form-group">
						<label for="exampleInputEmail1">账号</label> <input type="text"
							class="form-control" id="account" name="account"
							placeholder="账号" value="${newuser.account}" readonly="readonly">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">用户名</label> <input type="text"
							class="form-control" id="userName" name="userName"
							placeholder="用户名" value="${newuser.user_name}">
					</div>
					
					<div class="control-group">
						<label class="control-label">用户类型</label>

						<div class="controls">
							<select id="userType" name="userType" class="form-control"	data-rel="chosen">
								<option value="0" <c:if test="${newuser.user_type==0}">selected</c:if>>商城用户</option>
								<option value="1" <c:if test="${newuser.user_type==1}">selected</c:if>>磐石用户</option>
								<option value="2" <c:if test="${newuser.user_type==2}">selected</c:if>>第三方卖家用户</option>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="RoleList">角色</label>

						<div class="controls">
							<select id="roleId" name="roleId" class="form-control"
								data-rel="chosen">
								<c:forEach var="item" items="${roleList }">
									<option value="${item.role_id }"
										<c:if test="${userroleid==item.role_id}">selected</c:if>>${item.role_name }</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="shopId">请选择此账号管理的类目</label>
						<div class="controls" >
							<select id="categoryIdList" name="categoryIdList" multiple
								class="form-control" data-rel="chosen">
								<option value="-1"
									<c:if test="${newuser.is_all_category == -1 }"> selected </c:if>>不负责类目</option>
								<option value="0"
									<c:if test="${newuser.is_all_category == 1 }"> selected </c:if>>所有类目</option>
								<c:forEach var="item" items="${categoryList }">
									<option value="${item.categoryId }"
										<c:if test="${categoryIdList.contains(item.categoryId)}">selected</c:if>>${item.categoryName }</option>
								</c:forEach>
							</select>
						</div>
					</div>


					<br>
					<button id="btnSubmit" class="btn btn-default">提交</button>
				</form>

			</div>
		</div>
	</div>
	<!--/span-->

</div>
<!--/row-->

<%@ include file="../footer.jsp"%>

<script>
	$('#btnSubmit').click(
			function() {
				//check param

				if ($('#account').val().trim().length == 0) {
					alert("输入用户名");
					return false;
				}

				if ($('#userName').val().trim().length == 0) {
					alert("输入用户名");
					return false;
				}

				if ($('#categoryIdList').val() == null) {
					alert("请选择类目");
					return false;
				}
				//submit
				$.post("edituser", $("#form1")
						.serialize(), function(response) {
					var ret = response;
					if (ret.code != 0) {
						alert(ret.msg);
					} else {
						window.location.href = "list";
					}
				},"json");
				return false;
			});
</script>