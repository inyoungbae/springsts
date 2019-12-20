import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//TCP 서버
//server(process) - client(process)

//서버 : 192.168.0.24
//포트 : port :9999

public class Ex02_TCP_Server {
	public static void main(String[] args) throws IOException {
		//서버쪽에서 통신프로토콜을 열어놓고 대기하다가 들어오면 서로연결해주는거까지 2줄. 
		ServerSocket serversocket = new ServerSocket(9999);  //port 번호 : 9999 정의해야함. 
		System.out.println("접속 대기중 ...");
		Socket socket = serversocket.accept(); //클라이언트가 접속하면 ....
		System.out.println("연결완료");
		/////////////////////////////////////////////////////////////////////////
		
		
		//연결이되면 
		//서버 : 클라이언트 (read , write)
		//socket 과 socket 
		//socket(input , output Stream) 
		OutputStream out = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(out); //8가지 기본 타입 + 알파
		dos.writeUTF("문자데이터 Byte 통신 가능8888");
		
		System.out.println("서버 종료");
		
		dos.close();
		out.close();
		socket.close();
		serversocket.close();
	}

}



