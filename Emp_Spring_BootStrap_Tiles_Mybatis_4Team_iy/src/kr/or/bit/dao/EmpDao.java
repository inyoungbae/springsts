package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import kr.or.bit.dto.Emp;
import kr.or.bit.dto.chart.AvgMaxMinSalaryByDept;
import kr.or.bit.dto.chart.DataByYear;
import kr.or.bit.dto.chart.LocDept;
import kr.or.bit.dto.chart.StatisticsByMgr;
import kr.or.bit.dto.chart.TotalSaleryChart;
import kr.or.bit.utils.DBHelper;

public interface EmpDao {
	
	//사원추가
	public int insertEmp(Emp emp);

	//사원 이름으로 찾기 
	public Emp getEmpByEmpno(int no);
	
	//
	public List<String> getJobRegister();
	
	//관리자 로그인체크
	public String checkAdminLogin(String userid, String pwd);
	
	//
	public List<Emp> getEmps();

	//사번으로 사원삭제
	public int deleteEmpByEmpno(int empno);

	//사원수정
	public int updateEmp(Emp emp);

	//월급차트데이터
	public List<TotalSaleryChart> ChartDataByTotalSalery(int count);

	//고용일
	public List<DataByYear> dataByYear();
	
	//deptno번호가져오는거
	public List<Integer> getDethNos();

	//
	public List<AvgMaxMinSalaryByDept> ChartSalByDept();

	//지역별로 돈 차트
	public List<LocDept> LocChart();
	
	// 매니저랑 사수, 부사수 잘케어하는지...부사수들 돈차트
	public List<StatisticsByMgr> statisticsByMgr();
}
