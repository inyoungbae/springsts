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
<link href="https://fonts.googleapis.com/css?family=Gothic+A1|Hi+Melody|Jua|Nanum+Pen+Script&display=swap"
	rel="stylesheet">
<link href="css/style_message.css" rel="stylesheet">
<script type="text/javascript">
	//웹소켓 변수 선언
	var wsocket;

	$(function() {
		connect();
		$('#message').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13')
				send($('#message').val());

			event.stopPropagation();
		});

		$('#sendBtn').click(function() {
			send($('#message').val());
		});
		
		$('#closeBtn').click(function() {
			self.close();
		});

       $(window).on("beforeunload", function() {
			disconnect();
	    });
	})
	
	function connect() { //입장 버튼 클릭시 작동 함수(웹소켓 생성)
		wsocket = new WebSocket("ws://192.168.6.27:8090/WebSocket_Memory_4Team/Chat-ws.do?cmd=join&room=${room}");

		//해당 함수 정의
		wsocket.onmessage = onMessage;
	}
	
	function disconnect() {
		wsocket.close();
	}

	function onMessage(evt) { 
		var data = JSON.parse(evt.data);
		appendMessage(data);
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
			 let messageBox = "<div class='direct-chat-msg right clearfix'>"
									+ "	<div class='direct-chat-info text-right'>"
									+ "	<span class='direct-chat-name'>"+ data.sender +"</span>"
									+ "	</div>"
									+ "	<div class='direct-chat-text'>" + data.message + "</div>"
									+ "</div>";
									
			$("#chatMessageArea").append(messageBox);
		} else if(data.type == "memberInfo"){
			$("#chatMessageArea").append( "<div class='center-message clearfix'>" + data.message + "</div>");
			setChattingMember(data.users);
		} else {
			let messageBox = "<div class='direct-chat-msg clearfix'>"
									+ "	<div class='direct-chat-info'>"
									+ "	<span class='direct-chat-name pull-left'>"+ data.sender +"</span>"
									+ "	</div>"
									+ "	<div class='direct-chat-text'>" + data.message + "</div>"
									+ "</div>";
									
			$("#chatMessageArea").append(messageBox);
		}

		let chatAreaHeight = $(".box-body").height();
		console.log(chatAreaHeight);
		console.log($("#chatMessageArea").height());
		let maxScroll = $("#chatMessageArea").height() - chatAreaHeight;
		console.log("scroll");
		console.log(maxScroll);
		$(".box-body").scrollTop(maxScroll); 
	}
	
	function setChattingMember(members){
		$("#memberArea").empty();
		$.each(members, function(index, element){
			let sp = $("<span></span>");
			if(element == "${sessionScope.userid}")
				sp.css("background-color","yellow");
			
			sp.append(element);
			$("#memberArea").append( $("<li style=' padding-left: 10px;'></li>").append(sp));
		})
	}
	
</script>
</head>
<style>


</style>
<body id="page-top">
<div class="col-md-3">
      <!-- DIRECT CHAT PRIMARY -->
      <div class="box box-primary direct-chat direct-chat-primary" >
        <div class="box-header with-border">
          <h3 class="box-title"> ${room}</h3>
          <div class="box-tools pull-right">

             <div class="btn-group gurdeepoushan">
				  <button  class="btn btn-box-tool"  data-toggle="dropdown" type="button" aria-expanded="false">
				   		<i class="fas fa-user-friends"></i>
				   </button>
				  <ul id="memberArea" role="menu" class="dropdown-menu pull-right">
					
				  </ul>
				</div>
            <button type="button" class="btn btn-box-tool" id="closeBtn">
            	<i class="fa fa-times"></i>
           	</button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body" style="height: 350px">
          <!-- Conversations are loaded here -->
          <div id="chatMessageArea" class="direct-chat-messages">
           
          </div>
    
          <!-- /.direct-chat-pane -->
        </div>

        <div class="box-footer">
            <div class="input-group">
              <input type="text" id="message" placeholder="Message" class="form-control">
                  <span class="input-group-btn">
                    <button id="sendBtn" class="btn btn-primary btn-flat">Send</button>
                  </span>
            </div>
        </div>
        <!-- /.box-footer-->
      </div>
      <!--/.direct-chat -->
    </div>
    
</body>

</html>