<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ref" value="${ref+1}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<c:import url="/common/HeadTag.jsp" />
<jsp:include page="/common/DataTableScript.jsp"></jsp:include>
<script type="text/javascript">
	let wsocket;
	
	$(function() {
		connect();

		$('#dataTable').DataTable({
		 	"searching": false,
		 	"ordering": false
 		});
 		
		$("#createChat").click( function() {
			backAndForth();
		});

		$(window).on("beforeunload", function(){
			disconnect();
	    });

	    $('#dataTable tbody').on( 'click', 'button', function () {
	    	openChat($(this).attr("id"));
	    });
	})
	
		
	const steps = ['방 제목', '최대 인원'];
	const swalQueueStep = Swal.mixin({
	  confirmButtonText: 'Next',
	  cancelButtonText: 'Back',
	  progressSteps: ['1','2'],
	  input: 'text',
	  inputAttributes: {
	    required: true
	  },
	  reverseButtons: true,
	  validationMessage: '필수 입력사항입니다.'
	})

	 async function backAndForth () {
	  const values = [];
	  let currentStep;

	  for (currentStep = 0; currentStep < steps.length;) {
	    const result = await swalQueueStep.fire({
	      title: steps[currentStep],
	      inputValue: values[currentStep],
	      showCancelButton: currentStep > 0,
	      currentProgressStep: currentStep
	    })
	
	    if (result.value) {
	      values[currentStep] = result.value
	      currentStep++
	    } else if (result.dismiss === 'cancel') {
	      currentStep--
	    } else {
	      break
	    }
	  }

	  if (currentStep === steps.length) {
	    let data = { cmd : "createChatRoom"
	    	    		  , name : values[0]
	    	    		  , max : values[1]
	    	    		};
	    sendSocket(data);
	    openChat(data.name);
	  }
	}

	 function connect() { // 웹소켓 연결 
		wsocket = new WebSocket("ws://192.168.6.27:8090/WebSocket_4Team_DB/Chat-ws.do?cmd=on");  // on : 실시간채팅 페이지로 들어옴 

		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	}

	function disconnect() { //웹소켓 연결 해제
		wsocket.close();
	}
	
	function onMessage(evt) { // 서버로부터 메세지 받으면 실행되는 함수 
		var data = JSON.parse(evt.data);  //parse 메소드는 string 객체를 json 객체로 변환
	}
	
	function onClose(evt) {
		
	}

		
    function sendSocket(jsonData) {
    	jsonData.sender = "${sessionScope.userid}";  //jsonData.sender하면 json데이터에 key : sender / value: ${sessionScope.userid} 만들어짐 
		jsonData.ref = "${ref}";
		console.log("send");
    	wsocket.send(JSON.stringify(jsonData));
    }

    function openChat(room){
        console.log("open Chat");
    	let url = "Chat.do?room="+room;
    	let name = room;
    	let option = "width = 500, height = 500, top = 100, left = 200, location = no, channelmode = yes";
        window.open(url, name, option);
    }
</script>
<style type="text/css">
.iconColumn {
	width: 100px;
	text-align: center;
}
</style>
</head>

<body id="page-top">
	<!-- Top -->
	<c:import url="/common/Top.jsp" />
	<div id="wrapper">
		<!-- Left Menu -->
		<c:import url="/common/Left.jsp" />

		<div id="content-wrapper">

			<!-- !! Content !! -->
			<div class="container-fluid">
				<div class="card mb-3">
					<div class="card-header">
						<i class="fas fa-comments"></i> 실시간 채팅
					</div>
					<div class="card-body">
						<button id="createChat" class="btn btn-primary mb-3" type="button">채팅방
							만들기</button>
						<div class="table-responsive">
							<table class="table table-bordered" id="dataTable">
								<thead>
									<tr>
										<th width="10%">NO</th>
										<th width="60%">NAME</th>
										<th width="10%">USER</th>
										<th width="10%">OWNER</th>
										<th width="10%">ENTER</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="list" items="${roomlist}">
									

										<tr>
											<td>${list.ref}</td>
											<td><c:out value="${list.name}" />
											<td><c:out value="${list.max}" />
											<td><c:out value="${list.owner}" />
											<td><button id="${list.name}">입장</button></td>
										</tr>

									</c:forEach>
					
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>


			<!-- Bottom -->
			<c:import url="/common/Bottom.jsp" />
		</div>
	</div>
</body>

</html>