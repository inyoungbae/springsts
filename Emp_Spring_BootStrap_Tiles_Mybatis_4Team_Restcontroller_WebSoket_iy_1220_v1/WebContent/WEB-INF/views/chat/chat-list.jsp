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
		//console.log(roomnm);
		let control = $("<tr></tr>");
		control.append("<td>"+n+"</td>");   
		control.append("<td>" + roomnm + "</td>")
		control.append("<td>"+"${userid}"+"</td>");  
		//console.log("<button onclick='windowOpen("+'"'+roomnm+'"'+");'>Enter</button>");
		let entBtn = "<button onclick='windowOpen("+'"'+roomnm+'"'+");'>Enter</button>";
		control.append("<td>"+ entBtn +"</td>");  
		//console.log(control);
		
	   $('#chatbody').append(control);

	   windowOpen(roomnm);
   }    
}


function windowOpen(roomnm) {
	//console.log(roomnm);
    window.open("Chat-ws.do?roomName="+ roomnm, roomnm, 
    "width=500, height=600, toolbar=no, scrollbars=no, resizable=no, channelmode=yes, top=20, left=50");
    //console.log( $("#roomName").val());
}



	var wsocket;

	//웹소켓 연결
	function connect() {

		wsocket = new WebSocket("ws://192.168.6.27:8090/Emp_Spring_BootStrap_Tiles_Mybatis_4Team_Restcontroller_WebSoket_iy_1220_v1/Chat-ws1.do");
		
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	};

	function disconnect() { //웹소켓 연결 해제
		wsocket.close();
	};

	function onOpen(evt) {
	};

	function onMessage(evt) { // 서버로부터 메세지 받으면 실행되는 함수 
		let data = evt.data;
		console.log(data);
	};

	function onClose(evt) {
		
	};

	function send() {
		
 		var json = { "cmd" : "create",
 		 		"roomname" : $('#roomName').val(),
 		 		"captain" : '${userid}'
				};

 		wsocket.send(JSON.stringify(json)); 
	};
	
	function appendMessage(msg, pst, icon, color) {

	};

	function scroll() {
		var chatAreaHeight = $("#chatArea").height();
		var maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		$("#chatArea").scrollTop(maxScroll);
		};



$(document).ready(function() {
	connect ();
	
	$('#roomName').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == '13') { //(keycode == '13')  = enter 
			send();
			openWin();
		}
		event.stopPropagation();
		});

	
	$('#makeRoomBtn').click(function() {
		send();
		//openWin();
	});
	
});





 
</script>
</head>
<body>
	<jsp:include page="/common/Top.jsp"></jsp:include>
	<div id="wrapper">
		 <jsp:include page="/common/Left.jsp"></jsp:include> 
	
		<div id="content-wrapper">
			<div class="row" style="margin-left: 2%">
			<form action="">
			<input type="text" id="roomName" name="roomName" placeholder="방 제목을 입력하세요" style="margin-right: 1%"> 
			<input type="button" class="btn btn-primary btn-block" style="width: 100px" id="makeRoomBtn" value="방만들기" >
			</form>
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
                                            <th>Entrance</th>
                                        </tr>
                            </thead>
								<tbody id="chatbody">
										
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