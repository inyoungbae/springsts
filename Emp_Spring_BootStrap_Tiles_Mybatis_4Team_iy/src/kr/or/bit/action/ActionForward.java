package kr.or.bit.action;

//로직 제어 , 화면전달 , 경로 .. 설정
public class ActionForward {
	private boolean isRedirect = false; // 화면단 제어
	private String path = null; // 이동경로

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
