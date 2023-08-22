package day0822;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.sist.dao.DbConn;

public class EmpDAO {
	//이것은 생성자
	private static EmpDAO eDAO;
	
	private EmpDAO() {
		
	}
	
	public static EmpDAO getInstance() {
		if(eDAO == null) {
			eDAO = new EmpDAO();
		}
		return eDAO;
	}
	
	public ArrayList<String> selecEmpno() throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> empno = new ArrayList<String>();
		
		DbConn dc = DbConn.getInstance();
		
		try {
			con = dc.getConnection("localhost", "scott", "tiger");
			
			String selectEmpno = "select empno from emp";
			
			pstmt = con.prepareStatement(selectEmpno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				empno.add(rs.getString("empno"));
			}
			return empno;
		}finally {
			dc.dbClose(rs, con, pstmt);
		}
	}
	
	public EmpVO selectOneEmpInfo(String empno) throws SQLException{
		EmpVO eVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConn dc = DbConn.getInstance();
		
		try {
			con = dc.getConnection("localhost", "scott", "tiger");
			
			StringBuilder selectEmpno = new StringBuilder();
			selectEmpno
			.append("	select empno,ename,hiredate,job,sal	")
			.append("	from emp													")
			.append("	where empno = ?												");
			
			pstmt = con.prepareStatement(selectEmpno.toString());
			
			pstmt.setString(1, empno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eVO = new EmpVO();
				
				eVO.setEmpno(rs.getString("empno"));
				eVO.setEname(rs.getString("ename"));
				eVO.setHiredate(rs.getString("hiredate"));
				eVO.setJob(rs.getString("job"));
				eVO.setSal(rs.getString("sal"));
			}
		}finally {
			dc.dbClose(rs, con, pstmt);
		}
		
		return eVO;
	}
	
}
