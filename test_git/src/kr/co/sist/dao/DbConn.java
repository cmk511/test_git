package kr.co.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConn {
	
	private static DbConn dbConn;
	
	private DbConn() {
		
	}//DbConn
	
	public static DbConn getInstance() {
		if(dbConn==null) {
			dbConn = new DbConn();
		}//end if
		return dbConn;
	}//getInstance
	/**
	 * DB연결을 얻는일
	 * @param ip
	 * @param id
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String ip,String id, String pass) throws SQLException{
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url ="jdbc:oracle:thin:@"+ip+":1521:orcl";
		con=DriverManager.getConnection(url,id,pass);
		return con;
	}//getConnection
	/**
	 * 연결 끊기
	 * @param rs
	 * @param stmt
	 * @param con
	 * @throws SQLException
	 */
	public void dbClose(ResultSet rs,Statement stmt, Connection con)throws SQLException{
		if(rs!=null) {rs.close();}//end if
		if(stmt!=null) {stmt.close();}//end if
		if(con!=null) {con.close();}//end if
	}

}//class
