<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- left menu starts -->
<div class="col-sm-2 col-lg-2">
	<div class="sidebar-nav">
		<div class="nav-canvas">
			<div class="nav-sm nav nav-stacked"></div>
			<ul class="nav nav-pills nav-stacked main-menu">
				<li class="nav-header">后台管理</li>
				<c:forEach var="item" items="${user.menuList}">
					<c:if test="${item.menu_type==1}">
					<li class="accordion"><a href="#"><i
							class="glyphicon glyphicon-plus"></i><span> ${item.menu_name}</span></a>
						<ul class="nav nav-pills nav-stacked">
							<c:forEach var="child" items="${item.childMenu}">
								<li><a href="${child.menu_url}">${child.menu_name}</a></li>
							</c:forEach>
						</ul></li>
					</c:if>
					<c:if test="${item.menu_type==2}">
							<li><a class="ajax-link" href="${item.menu_url}"><i
									class="glyphicon glyphicon-align-justify"></i><span>${item.menu_name }</span></a></li>
							<li class="nav-header hidden-md"></li>
					</c:if>
					
				</c:forEach>
				
				<!-- 
				<c:if test="${loginInfo.isSuperAdminUser}">
				<li class="accordion"><a href="#"><i
						class="glyphicon glyphicon-plus"></i><span> 用户管理</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${loginInfo.urlPrefix }adminUser/add">添加用户</a></li>
						<li><a href="${loginInfo.urlPrefix }adminUser/list">用户列表</a></li>
					</ul></li>
				</c:if>
									
				<li class="accordion"><a href="#"><i
						class="glyphicon glyphicon-plus"></i><span> 商品上单</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${loginInfo.urlPrefix }item/generalSku">普通商品上单</a></li>
						<li><a href="${loginInfo.urlPrefix }item/secKillSku">秒杀商品上单</a></li>
					</ul>
				</li>
				
				<c:if test="${loginInfo.isSuperAdminUser}">
				<li class="accordion"><a href="#"><i
						class="glyphicon glyphicon-plus"></i><span> 订单管理</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${loginInfo.urlPrefix }orderManagement/list">订单查询</a></li>
					</ul>
				</li>
				</c:if>
				
				<c:if test="${loginInfo.isSuperAdminUser}">
				<li><a class="ajax-link" href="${loginInfo.urlPrefix }couponRule/list"><i
						class="glyphicon glyphicon-align-justify"></i><span>优惠活动</span></a></li>
				<li class="nav-header hidden-md"></li>
			    </c:if>
				
				<c:if test="${loginInfo.isSuperAdminUser}">
                <li><a class="ajax-link" href="${loginInfo.urlPrefix }category/list"><i
                        class="glyphicon glyphicon-align-justify"></i><span>类目管理</span></a></li>
                <li class="nav-header hidden-md"></li>
			    </c:if>

				<c:if test="${loginInfo.isSuperAdminUser}">
				<li class="accordion">
				  <a href="#"><i class="glyphicon glyphicon-plus"></i><span>商城统计</span></a>
				  <ul class="nav nav-pills nav-stacked">
				    <li><a href="/selleradmin/statistic/overview">概览</a></li>
				    <li><a href="/selleradmin/statistic/mallUsers">商城用户</a></li>
				  </ul>
				  </li>
				</c:if>
			    
			    <c:if test="${loginInfo.isSuperAdminUser}">
				<li class="accordion"><a href="#"><i
						class="glyphicon glyphicon-plus"></i><span> 广告管理</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${loginInfo.urlPrefix }bannerManagement/list">广告列表</a></li>
						<li><a href="${loginInfo.urlPrefix }bannerManagement/add">添加广告</a></li>
					</ul></li>
				</c:if>
				
				<c:if test="${loginInfo.isSuperAdminUser}">
				<li class="accordion"><a href="#"><i
						class="glyphicon glyphicon-plus"></i><span> 校园管理</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${loginInfo.urlPrefix }college/view">校园查询</a></li>
					</ul></li>
				</c:if>
				
				<c:if test="${loginInfo.isSuperAdminUser}">
					<li class="accordion"><a href="#"><i
						class="glyphicon glyphicon-plus"></i><span> 库存管理</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${loginInfo.urlPrefix }goods/view">商品管理</a></li>
					</ul></li>
				</c:if>-->
				</ul>
		</div>
	</div>
</div>
<!--/span-->
<!-- left menu ends -->

