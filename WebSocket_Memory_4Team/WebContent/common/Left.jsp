<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<body>
    <!-- Sidebar -->
   <c:choose>
   	<c:when test="${sessionScope.userid == null }">
   		<c:set var="isLogin" value="false"/>
   	</c:when>
   	<c:otherwise>
   		<c:set var="isLogin" value="true"/>
   	</c:otherwise>
   </c:choose>
    <ul class="sidebar navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="index.jsp">
                <i class="fas fa-home"></i>
                <span>Main</span>
            </a>
        </li>
       <c:choose>
        	<c:when test="${isLogin}">
        		<li class="nav-item">
		            <a class="nav-link" data-toggle="modal" data-target="#logoutModal">
		                <i class="fas fa-sign-out-alt"></i>
		                <span>Logout</span>
		             </a>
		        </li>
		        <li class="nav-item">
		            <a class="nav-link" href="ChatHome.do">
		                <i class="fas fa-comments"></i>
		                <span>실시간 채팅</span>
		             </a>
		        </li>
        	</c:when>
        	<c:otherwise>
        		 <li class="nav-item">
		            <a class="nav-link" href="Login.do">
		                <i class="fas fa-sign-in-alt"></i>
		                <span>Login</span></a>
		        </li>
        	</c:otherwise>
        </c:choose>
    </ul>
    
    <!-- Logout Modal-->
     <jsp:include page="/WEB-INF/views/modal/LogoutModal.jsp"/> 
</body>
</html>