package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.dao.MemberDaoH2Implement;
import edu.pnu.dao.MemberDaoListImplement;
import edu.pnu.dao.MemberInterface;
import edu.pnu.domain.MemberVO;


//MemberService는 실제로 회원 데이터에 접근하는 DAO(Data Access Object) 클래스와 상호작용하여 요청된 작업을 수행
public class MemberService {

	MemberInterface memberInterface;

	public MemberService() {
		//memberInterface =  new MemberDaoListImplement();
		memberInterface= new MemberDaoH2Implement();
	}
	
	public List<MemberVO> getmembers() {
		return memberInterface.getmembers();
	}
	
	public MemberVO getMember(Integer id) {

		return memberInterface.getMember(id);
	}
	

	public MemberVO addMember(MemberVO member) {
		return memberInterface.addMember(member);
	}

	public MemberVO updateMember(MemberVO member) {
		
		return memberInterface.updateMember(member);
	}

	public MemberVO deleteMember(Integer id) {
		
		return memberInterface.deleteMember(id);
	}
	

}
