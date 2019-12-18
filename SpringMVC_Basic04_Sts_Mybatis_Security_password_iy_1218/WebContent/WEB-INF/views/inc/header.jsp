<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags"%>
<div id="header">
	<div class="top-wrapper">
		<h1 id="logo">
			<a href="/"><img src="" alt="로고" /></a>
		</h1>
		<h2 class="hidden">메인메뉴</h2>
		<ul id="mainmenu" class="block_hlist">
			<li><a href="">BIT가이드</a></li>
			<li><a href="">BIT과정</a></li>
			<li><a href="">BIT</a></li>
		</ul>
		<form id="searchform" action="" method="get">
			<fieldset>
				<legend class="hidden"> 과정검색폼 </legend>
				<label for="query">과정검색</label> <input type="text" name="query" />
				<input type="submit" class="button" value="검색" />
			</fieldset>
		</form>
		<h3 class="hidden">로그인메뉴</h3>
		<ul id="loginmenu" class="block_hlist">
			<li><a href="${pageContext.request.contextPath}/index.htm">HOME</a></li>
			<%-- 기존태그 설정
			<li><a href="${pageContext.request.contextPath}/joinus/login.htm">로그인</a></li> 
			--%>
			
			<!-- 
			principal객체 유무에따라 로그인/로그아웃 보여줌 
			userPrincipal.name을 뽑아내면 아이디값을 뽑아 낼 수 잇음  
			-->
<%-- 			<c:if test="${empty pageContext.request.userPrincipal}">
				<li>
					<a href="${pageContext.request.contextPath}/joinus/login.htm">로그인</a>
				</li>
			</c:if>
			<c:if test="${not empty pageContext.request.userPrincipal}">
				<li>
				<a href="${pageContext.request.contextPath}/logout">
						(${pageContext.request.userPrincipal.name})로그아웃</a></li>
			</c:if> --%>
			
			<!-- Spring security가 제공하는 script 언어를 사용해서 제어해볼게요. -->
			<se:authorize access="!hasRole('ROLE_USER')">
				<li>
					<a href="${pageContext.request.contextPath}/joinus/login.htm">로그인</a>
				</li>
			</se:authorize>
			<se:authentication property="name" var="loginuser"/> <!-- se:authentication 인증관련 -->
			<!-- name 값이 가지는 값을 var(loginuser) 변수에 넣는 것. 나중에 써먹기 위해서 like c:set  
					나중에 써먹을때 el태그로 호환되기때문에 el로 쓰면 됨
			       ${pageContext.request.userPrincipal.name} 얘를 대체함-->
			 <se:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')"><!-- hasAnyRole(=or) 현재 접속한 권한이 어드민 또는 유저라면 로그아웃 -->
			 	<li>
				<a href="${pageContext.request.contextPath}/logout">
						(${loginuser})로그아웃</a>
				</li>
			 </se:authorize>
			<li><a href="${pageContext.request.contextPath}/joinus/join.htm">회원가입</a></li>
		</ul>
		<h3 class="hidden">회원메뉴</h3>
		<ul id="membermenu" class="clear">
			<li><a href="${pageContext.request.contextPath}/joinus/memberConfirm.htm"><img
					src="${pageContext.request.contextPath}/images/menuMyPage.png"
					alt="마이페이지" /></a></li>
			<li><a
				href="${pageContext.request.contextPath}/customer/notice.htm"> <img
					src="${pageContext.request.contextPath}/images/menuCustomer.png"
					alt="고객센터" /></a></li>
		</ul>
	</div>
</div>