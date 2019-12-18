/*
 * package ncontroller;
 * 
 * import org.apache.ibatis.session.SqlSession; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod;
 * 
 * 
 * 
 * import service.MemberService;
 * 
 * 
 * @RequestMapping("/joinus/") public class RestController {
 * 
 * private SqlSession sqlSession;
 * 
 * @Autowired public void setSqlsession(SqlSession sqlSession) { this.sqlSession
 * = sqlSession; }
 * 
 * @Autowired private MemberService service;
 * 
 * //id체크하기
 * 
 * @RequestMapping(value="ajax_idcheck.htm", method=RequestMethod.POST) public
 * boolean idcheck(String userid) { System.out.println("유저아이디 넘어오니? ajax : " +
 * userid); int result = service.idCheck(userid); boolean data = false;
 * if(result > 0) { System.out.println("있는 아이디입니다."); data = false; }else { data
 * = true; } return data; }
 * 
 * }
 */
