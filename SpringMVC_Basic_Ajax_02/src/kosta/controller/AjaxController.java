package kosta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kosta.vo.Employee;


@Controller
public class AjaxController {

	//org.springframework.web.servlet.view.json.MappingJackson2JsonView
	//@Autowired
	//private View jsonview;
	

	
	@RequestMapping(value="response.kosta",method=RequestMethod.POST)
	public @ResponseBody Employee add(HttpServletRequest request, HttpServletResponse response)  
	//받은걸 객체로 바꿔서 JSON으로 넘겨줌
	//@ResponseBody VIEW를 타지않고 바로 던져버림 
	{
		System.out.println("Response ");
		
		Employee employee = new Employee();
		employee.setFirstname(request.getParameter("firstName"));
		employee.setLastname(request.getParameter("lastName"));
		employee.setEmail(request.getParameter("email"));
		System.out.println(employee.getFirstname());
		return employee;  //{"firstname":"aa","lastname":"bb","email":"cc"}
	}
	
	@RequestMapping(value="response2.kosta",method=RequestMethod.POST)
	public @ResponseBody Employee add(@RequestBody Employee emp) //@RequestBody (비동기: 객체 형태로 받아요) 클라이언트가 보낸것도 객체로 받아요. ( @ResponseBody랑 친해요.)
	{   System.out.println("response");
		System.out.println(emp.toString());
		
		return emp;
	}
	
	@RequestMapping(value="response3.kosta",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> Detail() //@RequestBody (비동기: 객체 형태로 받아요)  맵으로 리턴하는거 많이써요!! ★★ 
	{ 	
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("result", "data");
		map.put("hello", "world");
		return map;
	}
	// {"result":"data","hello":"world"}
	
}

/*
public @ResponseBody Map<String,String> add(HttpServletRequest request, HttpServletResponse response)
{
	Map<String,String> map = new HashMap<>();
	map.put("result","success");
	return map;
}
*/


