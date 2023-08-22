package day0821;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LoginFrmEvt extends WindowAdapter implements ActionListener {

	
	private LoginForm lf;
	
	public LoginFrmEvt(LoginForm lf) {
		this.lf=lf;
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
	   chkEmpty();
		}

	
	@Override
	public void windowClosing(WindowEvent e) {
		lf.dispose();
	}
	
	private void chkEmpty() {
		String id = lf.getJtfId().getText().trim();
		//아이디가 비어있다면 경고창으로 아이디 입력을 보여준다.
		if("".equals(id)) {
			JOptionPane.showMessageDialog(lf, "아이디는 필수 입력");
			lf.getJtfId().requestFocus();
			return;
		}
		//비밀번호가 비어있다면 경고창으로 비밀번호입력을 보여준다.
		String pass= new String(lf.getJpfPass().getPassword());
		if("".equals(pass)) {
			JOptionPane.showMessageDialog(lf, "비밀번호는 필수입력");
			lf.getJpfPass().requestFocus();
			return;
		}
		
		//그렇지 않으면 loginCheck()를 호출한다.
//		id=injectionBlock(id);//id와 비밀번호에 SQLInjection해당 하는 값이 존재하면 삭제시킨다 
//		pass=injectionBlock(pass);
		LoginVO lVO = new LoginVO(id, pass);
		loginCheck(lVO);
	}
	
	public String injectionBlock(String sql) {
		String resultSql=sql;
		//공백을 허용하지 않음, SQL주석 막기 , 쿼리문에 해당하는 문자열 막기
		resultSql=sql.replaceAll(" ","").replaceAll("--","").replaceAll("select","")
		.replaceAll("insert","").replaceAll("delete","");
		
		return resultSql;
		
	}
	
	private void loginCheck(LoginVO lVO) {
		InjectionTestDAO ItDAO = InjectionTestDAO.getInstance();
		try {
		//	LoginResultVO lrVO = ItDAO.useStatementLogin(lVO);
			LoginResultVO lrVO = ItDAO.usePreparedStatementLogin(lVO);
		if(lrVO==null) {
			JOptionPane.showMessageDialog(lf, "아이디나 비밀번호를 확인해주세요");
			lf.getJlblOutput().setText("");
			return;
		}
		lf.getJlblOutput().setText(lrVO.getName()+"님 로그인하셨습니다.");
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(lf, "아이디나 비밀번호를 입력해주세요");
			e.printStackTrace();
		}
	}
		

}
