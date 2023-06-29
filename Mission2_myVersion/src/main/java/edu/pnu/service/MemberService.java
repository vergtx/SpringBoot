package edu.pnu.service;



import java.util.List;

import edu.pnu.dao.MemberDao;
import edu.pnu.domain.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		// TODO Auto-generated constructor stub
		memberDao = new MemberDao();
	}

	public Member getMember(Long id) {
		return memberDao.getMember(id);
		// TODO Auto-generated constructor stub
	}

	
	public int insertMember(Member member) {
		// TODO Auto-generated method stub
		 return memberDao.insertMember(member);
	}

	public List<Member> getMembers() {
	    return memberDao.getMembers();
	}

	public int deleteMember(Long id) {
		// TODO Auto-generated method stub
		return memberDao.deleteMember(id);
	}

	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		return memberDao.updateMember(member);
	}

}
