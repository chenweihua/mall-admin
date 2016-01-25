<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- <%@ include file="header.jsp" %> --%>
        
    <div class="row">
        <div class="col-md-12 center login-header">
            您没有${sessionScope.nopermissionmessage}的权限，返回<a href="<%=request.getContextPath()%>/selleradmin/home/index">【首页】</a></h2>
        </div>
        <!--/span-->
    </div><!--/row-->

<%-- <%@ include file="footer.jsp" %> --%>
