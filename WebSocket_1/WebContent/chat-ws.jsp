<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>채팅</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
	var wsocket;
	
	function connect() {
		wsocket = new WebSocket("ws://192.168.6.27:8090/WebSocket_1/chat-ws");  //웹소켓 연결
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}
	
	function disconnect() {  //웹소켓 연결 해제
		wsocket.close();
	}
	
	function onOpen(evt) {
		appendMessage("연결되었습니다.");
	}
	
	function onMessage(evt) {  // 서버로부터 메세지 받으면 실행되는 함수 
		var data = evt.data;
		if (data.substring(0, 4) == "msg:") {// 서버로부터 msg:로 시작되는 메세지를 받으면 msg:(0~4) 제외한 부분을 append해라. 
			appendMessage(data.substring(4));
		}
	}
	
	function onClose(evt) {
		appendMessage("연결을 끊었습니다."); //appendMessage - 함수 
	}
	
	function send() {   
		var nickname = $("#nickname").val();
		var msg = $("#message").val();
		wsocket.send("msg:"+nickname+" :" + msg);     //서버로 메세지 전송
		$("#message").val("");
	}

	function appendMessage(msg) {
		$("#chatMessageArea").append(msg+"<br>");
		var chatAreaHeight = $("#chatArea").height();
		var maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		$("#chatArea").scrollTop(maxScroll);
	}

	$(document).ready(function() {
		$('#message').keypress(function(event){
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(keycode == '13'){  //(keycode == '13')  = enter 
				send();	
			}
			event.stopPropagation();
		});
		
		$('#sendBtn').click(function() { send(); });
		$('#enterBtn').click(function() { connect(); }); //입장button
		$('#exitBtn').click(function() { disconnect(); }); //나가기 button 
	});
</script>
<style>
#chatArea {
	width: 200px; height: 100px; overflow-y: auto; border: 1px solid black;
}
</style>
</head>
<body>
	이름:<input type="text" id="nickname">
	<input type="button" id="enterBtn" value="입장">
	<input type="button" id="exitBtn" value="나가기">
    
    <h1>대화 영역</h1>
    <div id="chatArea"><div id="chatMessageArea"></div></div>
    <br/>
    <input type="text" id="message">
    <input type="button" id="sendBtn" value="전송">
</body>
</html>