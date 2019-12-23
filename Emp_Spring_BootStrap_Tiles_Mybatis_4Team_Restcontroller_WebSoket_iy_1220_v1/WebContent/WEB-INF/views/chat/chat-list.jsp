<%@ page contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userid" value="${sessionScope.userid}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>채팅</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<jsp:include page="/common/HeadTag.jsp" />
<script type="text/javascript">

var newName, n=0;

 function newWindow(value){ //여러창을 동시에 띄우기 위해 사용하는 함수 
   n = n + 1;
   newName = value + n;     
} 

function openWin(){  
	
   newWindow("MyWindow");
   if($("#roomName").val() == ""){
      alert("제목을 입력하세요"); 
   } else {
		let roomnm = $('#roomName').val();
		let control = $("<tr></tr>");
		control.append("<td>"+n+"</td>");   
		//let tag = "<a href='Chat-ws.do?roomName="+'$("#roomName").val()'+"'>";
		let tag = "<a href='javascript:windowOpen('"+ roomnm+ "')'>";
		control.append("<td>" + tag + $("#roomName").val() + "</td>");

		control.append("<td>"+"${userid}"+"</td>");    
		console.log(control);
		
	   $('#chatbody').append(control);

	   windowOpen(roomnm);
	
   }    
}

function windowOpen(roomnm) {
    window.open("Chat-ws.do?roomName="+ roomnm, roomnm, 
    "width=500, height=600, toolbar=no, scrollbars=no, resizable=no, channelmode=yes, top=20, left=50");
    console.log( $("#roomName").val());
}

/*
 toolbar = 상단 도구창 출력 여부 
 menubar = 상단 메뉴 출력 여부
 location = 메뉴아이콘 출력 여부
 directories = 제목 표시줄 출력 여부
 status = 하단의 상태바 출력 여부
 scrollbars = 스크롤바 사용 여부
 resizable = 팝업창의 사이즈 변경 가능 여부
 fullscreen = 전체화면으로 할지 선택 여부
 channelmode = F11키 기능이랑 같음
 */
</script>
</head>
<body>
	<jsp:include page="/common/Top.jsp"></jsp:include>
	<div id="wrapper">
		 <jsp:include page="/common/Left.jsp"></jsp:include> 
	
		<div id="content-wrapper">
			<div class="row" style="margin-left: 1%">
			<input type="text" id="roomName" name="roomName" placeholder="방 제목을 입력하세요" style="margin-right: 1%"> 
			<input type="button" class="btn btn-primary btn-block" style="width: 10%" id="makeRoomBtn" value="방만들기" onclick="openWin()" >
			</div>
			<br>
			
            <!-- !! Content !! -->
            <div class="container-fluid">
                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fas fa-user-cog"></i>
                       		채팅방 리스트
                    </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Roomname</th>
                                            <th>Captain</th>
                                            
                                        </tr>
                            </thead>
								<tbody id="chatbody">
										<tr>
										</tr>
								</tbody>
							</table>
                            </div>
                        </div>
                    </div>
                </div>
           
			
			
			
			
			
			
			
			<jsp:include page="/common/Bottom.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>