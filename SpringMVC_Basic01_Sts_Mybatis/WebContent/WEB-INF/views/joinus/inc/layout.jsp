<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
    
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title><!-- TITLE 영역 --></title>
			<tiles:getAsString name="title" />
			
		
		<!-- CSS 설정 가져오기  -->
		<!-- <link href="join.css" type="text/css" rel="stylesheet" /> -->
		<link href='<tiles:getAsString name="css"/>' type="text/css" rel="stylesheet" />
	</head>
	<body>
		<!-- Header 영역 -->
		<tiles:insertAttribute name="header" />
		<!-- Visual영역 -->
		<tiles:insertAttribute name="visual" />
		<div id="main">
			<div class="top-wrapper clear">
				<!-- Content영역 -->
				<tiles:insertAttribute name="content" />
				
				<!-- Aside(navi)영역 -->
				<tiles:insertAttribute name="aside" />
				
			</div>
		</div>
		<!-- Footer 영역 -->
		<tiles:insertAttribute name="footer" />
		
	</body>
</html>
