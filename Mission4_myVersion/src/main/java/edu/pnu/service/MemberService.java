package edu.pnu.service;

import java.util.List;

import edu.pnu.dao.LogDao;
import edu.pnu.dao.MemberDaoH2Implement;
import edu.pnu.dao.MemberDaoListImplement;
import edu.pnu.dao.MemberInterface;
import edu.pnu.domain.MemberVO;

public class MemberService {
	
	private LogDao logDao; //  멤버 변수 사용 >> 아래 클래스들 내 에서 사용 가능 
	   
	
	MemberInterface memberInterface;

	public MemberService() {
		//memberInterface =  new MemberDaoListImplement();
		memberInterface= new MemberDaoH2Implement();
		
		logDao = new LogDao(); 			//LogDao 클래스의 새로운 인스턴스를 생성하여 logDao 변수에 할당하는 것
										// 이렇게 되면 MemberService 클래스에서 LogDao 클래스의 기능을 사용할 수 있게 됨
										// ()는 생성자 호출 시 인자를 전달하는데 사용 예를 들어 
										//, "new LogDao(arg1, arg2)"와 같이 괄호 안에 인자를 전달하여 생성자를 호출할 수 있음 
	}
	
	public List<MemberVO> getmembers() {
		logDao.addLog("selectAll", "SELECT * FROM member", true);
		
		// logDao.log("getMembers", "SELECT * FROM member", true);는 getMembers 메서드가 호출될 때 해당 메서드와 관련된 로그를 기록하는 역할 을 목표로
		// LogDao class의 log 메서드를 호출
		return memberInterface.getmembers();
		
	}
	
	public MemberVO getMember(Integer id) {
		logDao.addLog("selectID", "select * from member where id=?", true);
		return memberInterface.getMember(id);
	}
	

	public MemberVO addMember(MemberVO member) {
		logDao.addLog("add","insert into member (id,name,pass,regidate) values (?,?,?,?)", true);
		return memberInterface.addMember(member);
	}

	public MemberVO updateMember(MemberVO member) {
		logDao.addLog("update","update member set name=?,pass=? where id=?", true);
		return memberInterface.updateMember(member);
	}

	public MemberVO deleteMember(Integer id) {
		logDao.addLog("delete","delete from member where id=?", true);
		return memberInterface.deleteMember(id);
	}
	

}