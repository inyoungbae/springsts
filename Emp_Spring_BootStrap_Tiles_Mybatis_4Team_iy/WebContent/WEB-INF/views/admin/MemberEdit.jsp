<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<head>
    <meta charset="UTF-8">
    <jsp:include page="/common/HeadTag.jsp"></jsp:include>
    <script type="text/javascript">

    $(function(){
    	 $("#photo").change(function(){
    			var reader = new FileReader();

    		    reader.onload = function (e) {
    		        // get loaded data and render thumbnail.
    		        document.getElementById("viewPhoto").src = e.target.result;
    		    };

    		    // read the image file as a data URL.
    		    reader.readAsDataURL(this.files[0]);
    		});
    });
    
   
</script>
</head>

<body id="page-top">
    <!-- Top -->
    <jsp:include page="/common/Top.jsp"></jsp:include>
    <div id="wrapper">
        <!-- Left Menu -->
        <jsp:include page="/common/Left.jsp"></jsp:include>
        <div id="content-wrapper">
            <!-- !! Content !! -->
			<c:set var="emp" value="${requestScope.emp }"/>
            <div class="container-fluid">
                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fas fa-user-edit"></i>
                 
                        회원 정보 수정 [<b>${emp.ename}님</b>]
               
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <form action="" method="post" enctype="multipart/form-data">
                                        	<div class="form-group">
                                                		<div class="form-row">
                                                   			<div class="col-md-6">
                                                        		<div class="form-label-group">
                                                            		<img id="viewPhoto" name="viewPhoto" src="upload/${emp.imagefilename}" alt="프로필사진"  style="width:10em; height:100%;">
                                                        		</div>
                                                    		</div>
                                                    		<div class="col-md-6">
                                                        		<div class="form-label-group">
                                                            		<input type="file" id="photo" name="file" class="form-control" accept="image/*">
                                                            		<label for="photo">photo</label>
                                                        		</div>
                                                    		</div>
                                                		</div>
                                            		</div>
                                            <div class="form-group">
                                                <div class="form-row">
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                            <input type="number" id="empno" name="empno" class="form-control" placeholder="No" required="required" autofocus="autofocus"
                                                            			readonly value="${emp.empno}">
                                                            <label for="empno">No</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                            <input type="text" id="ename" name="ename" class="form-control" placeholder="Name" required="required"
                                                            			value="${emp.ename}">
                                                            <label for="ename">Name</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="form-row">
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                          <%--   <input type="date" id="hiredate" class="form-control" placeholder="Hire Date" required="required"
                                                            			value="${emp.hiredate}">
	                                                        <label for="hiredate">Hire Date</label> --%>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                            <input type="text" id="job" name="job" class="form-control" placeholder="Job" required="required"
                                                            		value="${emp.job}">
                                                            <label for="job">Job</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="form-row">
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                            <input type="number" id="deptno" name="deptno" class="form-control" placeholder="Dept No" required="required"
                                                            			value="${emp.deptno}">
                                                            <label for="deptno">Dept No</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                        	<input type="number" id="mgr" name="mgr" class="form-control" placeholder="Manager" required="required"
                                                        				value="${emp.mgr}">
                                                        	<label for="mgr">Manager</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="form-row">
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                            <input type="number" id="sal" name="sal" class="form-control" placeholder="Sal" required="required"
                                                            			value="${emp.sal}">
                                                            <label for="sal">Salary</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-label-group">
                                                            <input type="number" id="comm" name="comm" class="form-control" placeholder="Commission" required="required"
                                                            			value="${emp.comm}">
                                                            <label for="comm">Commission</label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-row">
                                           		 <div class="col-md-6">
                                                    <input type="submit" class="btn btn-primary btn-block" value="Edit">
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="button" class="btn btn-danger btn-block" value="Cancel"
                                                        		onClick="MemberList.do">
                                                </div>
                                               
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>