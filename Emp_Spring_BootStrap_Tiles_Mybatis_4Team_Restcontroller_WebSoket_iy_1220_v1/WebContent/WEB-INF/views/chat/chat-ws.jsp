<%@ page contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>채팅</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<jsp:include page="/common/HeadTag.jsp" />
<style>
	#chatArea {
	 	width: 50%;
	 	height: 80%;
	}
</style>
<script type="text/javascript">
	var wsocket;

	//웹소켓 연결
	function connect() {
		wsocket = new WebSocket(
				"ws://192.168.6.27:8090/Emp_Spring_BootStrap_Tiles_Mybatis_4Team_Restcontroller_WebSoket_iy_1220_v1/Chat-ws1.do");
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}

	function disconnect() { //웹소켓 연결 해제
		wsocket.close();
		location.href = "index.jsp";
	}

	function onOpen(evt) {
		appendMessage($("#nickname").val() + "님이 입장하셨습니다.", "center", '<i class="fab fa-gratipay"></i>', '#FFFFFF');
	}

	function onMessage(evt) { // 서버로부터 메세지 받으면 실행되는 함수 
		var data = evt.data;
		console.log("data뭐니" + data);

		var jsondata = JSON.parse(data);
		console.log(jsondata.id);
		console.log(jsondata.msg);

		if($('#nickname').val() == jsondata.id) {
			appendMessage(jsondata.id + " : " + jsondata.msg, "right", '<i class="far fa-heart"></i>', 	'#FFDAB9');
		}else {
			appendMessage(jsondata.id + " : " + jsondata.msg, "left",'<i class="far fa-heart"></i>', '#B0C4DE');
			}
	}

	function onClose(evt) {
		appendMessage($("#nickname").val() + "님이 퇴장하셨습니다.", "center", '<i class="fab fa-gratipay"></i>', 	'#FFFFFF'); //appendMessage - 함수 
	}

	function send() {
		var nickname = $("#nickname").val();
		var msg = $("#message").val();
		if(msg == "") {
			return;
			}else {
				var json = { "id" : nickname,
					     "msg" : msg
					   }
				}
		


		   wsocket.send(JSON.stringify(json));   //서버로 넘길때 String으로 밖에 못받아서 json > String 변환
		$("#message").val("");
		$("#message").focus();
	}

	function appendMessage(msg, pst, icon, color) {

		console.log(msg);
		console.log(typeof(msg));


		var ptag= $("<div style='margin-top:1%'>"+icon+"<span style='border:1px solid black; border-radius: 0.5em; background-color:"+ color +"'>"+ msg +" </span></div>");
		ptag.css("text-align", pst);
		$("#chatMessageArea").append(ptag);
		
		var chatAreaHeight = $("#chatArea").height();
		var maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		$("#chatArea").scrollTop(maxScroll);
	}


	$(document).ready(function() {
		$('#message').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') { //(keycode == '13')  = enter 
				send();
			}
			event.stopPropagation();
		});

		$('#sendBtn').click(function() {
			send();
		});
		$('#enterBtn').click(function() {
			connect();
		}); //입장button
		$('#exitBtn').click(function() {
			if (confirm("정말로 나가시겠습니까?") == true) {
				disconnect();
			} else {
				return;
			}

		}); //나가기 button 
	});
</script>
<style>
#chatArea {
	width: 200px;
	height: 100px;
	overflow-y: auto;
	border: 1px solid black;
}
</style>
</head>
<body>
	<jsp:include page="/common/Top.jsp"></jsp:include>
	<div id="wrapper">
		<jsp:include page="/common/Left.jsp"></jsp:include>
		<div id="content-wrapper" style="boarder-left: 10%; boarder-right: 10%">
			<input type="button" id="exitBtn" value="나가기"> <br>
			<br> 이름:<input type="text" id="nickname"> <input
				type="button" id="enterBtn" value="입장">


			<h1>대화 영역</h1>
			<div id="chatArea">
				<div id="chatMessageArea" ></div>
			</div>
			<br />
			<input type="text" id="message"> 
			<input type="button" id="sendBtn" value="전송">

			<jsp:include page="/common/Bottom.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>