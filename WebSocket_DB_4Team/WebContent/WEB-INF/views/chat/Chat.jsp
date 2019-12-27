<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<c:import url="/common/HeadTag.jsp" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
<link href="https://fonts.googleapis.com/css?family=Gothic+A1|Hi+Melody|Jua|Nanum+Pen+Script&display=swap" rel="stylesheet">
<link href="css/style_message.css" rel="stylesheet">
<script type="text/javascript">
	//웹소켓 변수 선언
	var wsocket;

	$(function() {
		connect();
		$('#message').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
				send($('#message').val());
			}
			event.stopPropagation();
		});

		$('#sendBtn').click(function() {
			send($('#message').val());
		});

	       $(window).on("beforeunload", function(){
				disconnect();
		    });
	})
	
	function connect() { 
		wsocket = new WebSocket("ws://192.168.6.27:8090/WebSocket_DB_4Team/Chat-ws.do?cmd=join&room=${room}");

	
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}
	
	function disconnect() {
		wsocket.close();
	}

	function onMessage(evt) {
		var data = JSON.parse(evt.data);
		appendMessage(data);
	}

	function onClose(evt) {
		$("#chatMessageArea").empty();
		$("#nickname").attr("disabled", false);
		$("#memberArea").empty();
		$("#memberArea").hide();
	}

	function send(message) {
		let data = { message : message
						, cmd : "message"
						, room : "${room}"
						 };
		
		wsocket.send(JSON.stringify(data));
		$("#message").val("");
	}

	function appendMessage(data) {
		if (data.type == "my") {
			let messageBox = "<div class='msgRbox'>"
										+ "<span>"+data.sender+"</span>"
										+ "<div>"+data.message+"</div>"
									 + "</div>";
			$("#chatMessageArea").append(messageBox);
		} else if(data.type == "memberInfo"){
			$("#chatMessageArea").append( "<div class='msgCbox'>" + data.message + "</div>");
			setChattingMember(data.owner, data.users);
		} else {
			let messageBox = "<div class='msgLbox'>"
										+ "<span>"+data.sender+"</span>"
										+ "<div>"+data.message+"</div>"
									 + "</div>";
			$("#chatMessageArea").append(messageBox);
		}

		let chatAreaHeight = $("#chatArea").height();
		let maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		$("#chatArea").scrollTop(maxScroll);
	}
	
	function setChattingMember(owner, members){
		$("#memberArea").empty();
		$.each(members, function(index, element){
			let sp = $("<span></span>");
			if(element == "${sessionScope.userid}")
				sp.css("background-color","yellow");
			if(element == owner)
				sp.append("👑");
			
			sp.append(element);
			sp.append("<br>");
			$("#memberArea").append(sp);
		})
	}
	
</script>
</head>
<style>
#chatArea, #memberArea {
	height: 300px;
	overflow-y: auto;
	border: 1px solid black;
}

#memberArea {
	height: 300px;
	border: 1px solid black;
}

#pop-up {
   display: none;
   position: absolute;
   width: 280px;
   padding: 10px;
   background: #eeeeee;
   color: #000000;
   border: 1px solid #1a1a1a;
   font-size: 90%;
 }

</style>
<body id="page-top">
	<!-- !! Content !! -->
			<div class="container-fluid">
				<div class="card mb-3">
					<div class="card-header">
						<i class="fas fa-comments"></i> ${room}
					</div>
					<div class="card-body">
						<div class="row">
								<div class="table-responsive">
								<div class="row">
									<div class="col-md-12">
										<h5>채팅방</h5>
										 
										<div id="chatArea">
											<div id="chatMessageArea"></div>
										</div>
										<div id="inputBox">
											<input type="text" id="message" style="width: 400px;">
						                    <input type="button" id="sendBtn" value="전송">
						                    <br>
										</div>
										
										<h5>참여 멤버</h5>   

				                      <div id="memberArea" style="border:2px solid black; width: 100%; height: 320px;">
				                      
				                      
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