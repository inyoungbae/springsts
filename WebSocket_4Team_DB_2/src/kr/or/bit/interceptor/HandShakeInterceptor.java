package kr.or.bit.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor{
	  
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    	//attributes 에 값을 저장하면 웹소켓 핸들러 클래스의 WebSocketSession에 전달

    	System.out.println("Before Handshake");
    	ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
    	HttpServletRequest req= ssreq.getServletRequest();

    	String cmd  = req.getParameter("cmd");
    	String user  = (String) req.getSession().getAttribute("userid");
    	System.out.println("");
    	System.out.println("cdm :" + cmd+ "/user :"+user);
    	
        // 파라미터로 입력된 attributes에 put을 하면 WebSocketSession에서 접근가능
    	attributes.put("cmd", cmd);
    	attributes.put("user", user);
    	if(cmd.equals("join")) { //join에 추가하는 과정 
    		attributes.put("room", req.getParameter("room"));
    	}
    	
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
  
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
