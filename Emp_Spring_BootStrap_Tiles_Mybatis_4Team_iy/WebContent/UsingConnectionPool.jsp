<%@page import="java.security.spec.DSAGenParameterSpec"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tomcat Connection Pool</title>
</head>
<body>
	<%
		Connection connection = null;

		Context context = new InitialContext();

		// java:comp/env/[name]
		DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/oracle");
		
		// Pool안에서 connection 가지고 오기
		connection=dataSource.getConnection();
		
		// 확인
		out.print("db 연결 여부 : "+connection.isClosed()+"<br/>");
		
		// Point
		// Pool에 객체 반환하기
		connection.close();
		// 확인
		out.print("connection.close() 후 연결 여부 : "+connection.isClosed()+"<br/>");
	%>
</body>
</html>