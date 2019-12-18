<%@ page contentType="text/html; charset=utf-8" %>
<html>
<body>
<ul>

<li>커맨드 객체, @ModelAttribute:
	<ul>
		<li><a href="member/regist">/member/regist</a>: 커맨드 객체, @ModeAttribute로 커맨드 객체 이름 지정, RegistrationController</li>
		<li><a href="acl/list">/acl/list</a>: 커맨드 객체 리스트 처리, AclController
			<ul>
				<li>로그인 기능 수행 후, 실행</li>
				<li>또는, sample.xml이나 SampleConfig.java에서 핸들러 인터셉서 설정을 주석 처리 후 실행</li>
			</ul>
		</li>
		<li><a href="member/modify?mid=m1">/member/modify?mid=m1</a>: GET/POST에서 동일 타입 커맨드 객체 사용하기, MemberModificationController</li>
		<li><a href="event/list">/event/list</a>: @ModelAttribute를 이용한 공통 모델, EventController.recommend()</li>
	</ul>
</li>

</ul>

</body>
</html>
