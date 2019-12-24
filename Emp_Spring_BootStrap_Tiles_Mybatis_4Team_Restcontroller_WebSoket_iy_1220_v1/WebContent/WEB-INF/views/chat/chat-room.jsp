<%@ page contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="userid" value="${sessionScope.userid}" />

<!-- 로그인 안하면 이방에 못들어오게 막기 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>채팅</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<jsp:include page="/common/HeadTag.jsp" />

<script type="text/javascript">
console.log('${param.roomName}');
	var wsocket;

	//웹소켓 연결
	function connect() {
 		if(wsocket != null) {
			$('#chatMessageArea').append('이미 연결되었습니다.<br>');
			scroll();
			return;
			} 
		wsocket = new WebSocket("ws://192.168.6.27:8090/Emp_Spring_BootStrap_Tiles_Mybatis_4Team_Restcontroller_WebSoket_iy_1220_v1/Chat-ws1.do");
		console.log('${sessionScope.userid}');
		
		$('#enterBtn').hide();
		appendMessage("${sessionScope.userid}님이 입장하셨습니다.", "center", '<i class="fab fa-gratipay"></i>', '#FFFFFF');
		//wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}

	function disconnect() { //웹소켓 연결 해제
		wsocket.close();
	}

	function onOpen(evt) {
		$('#enterBtn').hide();
		appendMessage("${sessionScope.userid}님이 입장하셨습니다.'<br><br>'", "center", '<i class="fab fa-gratipay"></i>', '#FFFFFF');
	}

	function onMessage(evt) { // 서버로부터 메세지 받으면 실행되는 함수 
		var data = evt.data;
		console.log("data뭐니" + data);

		var jsondata = JSON.parse(data);
		console.log(jsondata.id);
		console.log(jsondata.msg);

		if('${sessionScope.userid}' == jsondata.id) {
			appendMessage(jsondata.id + ":" + jsondata.msg, "right", '<i class="far fa-heart"></i>', 	'#FFD133');
		}else {
			appendMessage(jsondata.id + ":" + jsondata.msg, "left",'<i class="far fa-heart"></i>', '#F7F6F1');
			}
	}

	function onClose(evt) {
		appendMessage("${sessionScope.userid}님이 퇴장하셨습니다.", "center", '<i class="fab fa-gratipay"></i>',	'#FFFFFF'); //appendMessage - 함수 
		
	}

	function send() {
		var nickname = '${sessionScope.userid}';
		var msg = $("#message").val();
		if(msg == "") {
			return;
			}else {
				var json = { 
						"cmd" : "enter",
						"id" : nickname,
					     "msg" : msg
					   }
				}
		   wsocket.send(JSON.stringify(json));   //서버로 넘길때 String으로 밖에 못받아서 json > String 변환
		$("#message").val("");
		$("#message").focus();
	};
	
	function appendMessage(msg, pst, icon, color) {

		let message1 = msg.split(':')[0];
		let message2 = msg.split(':')[1];

		var ptag= $("<div class='bubble' style='margin-top:1%'>"+icon + message1 + "&nbsp&nbsp<span class='bubbleinner' style='border:1px solid lightgray; border-radius: 0.3em; background-color:"+ color +"'>"+ message2 +" </span></div>");
		ptag.css("text-align", pst);
		$("#chatMessageArea").append(ptag);
		scroll();
	}

	function scroll() {
		var chatAreaHeight = $("#chatArea").height();
		var maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		$("#chatArea").scrollTop(maxScroll);
		};

		
	$(document).ready(function() {
		connect();
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

		$('#exitBtn').click(function() {
			if (confirm("정말로 나가시겠습니까?") == true) {
				disconnect();
				window.close();
			
			} else {
				return;
			}
		}); //나가기 button 
	});
</script>
<style>
#chatArea {
   width: 80%;
   height: 400px;
   overflow-y: auto;

}

#chatArea, #sendArea {
 margin-left: 8%;
  margin-right: 2%;
}

#content-wrapper {
	padding-left: 5%;
	padding-right: 5%
}

#sendArea {
	width: 80%;
	margin-left: auto;
	margin-right: auto;
	position: fixed;
	top: 90%;
}

h5 {
	text-align: center;
}

.bubble {
	margin-bottom: 7%;
}

.bubbleinner {
	padding : 1%;
	
}

#wrapper {
background-color: #B3DBF1; 
}

</style>
</head>
<body scroll="no">
	<%-- <jsp:include page="/common/Top.jsp"></jsp:include> --%>
	<div id="wrapper">
		<%-- <jsp:include page="/common/Left.jsp"></jsp:include> --%>
		<div id="content-wrapper">
		<br>
			<input type="button" id="exitBtn" value="나가기"> <br>
			

			<h5><i class="fas fa-comments"></i>&nbsp;&nbsp;&nbsp;${param.roomName}</h5>

			<hr>
			<br>
			<div id="chatArea">
				<div id="chatMessageArea"></div>
			</div>
			<br>
			<div id="sendArea">
				<input type="text" id="message" style="width: 80%"> 
				<input type="button" id="sendBtn" value="전송">
			</div>
			<jsp:include page="/common/Bottom.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>