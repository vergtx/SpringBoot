package edu.pnu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.dao.log.LogDao;
import edu.pnu.dao.log.LogDaoFileImpl;
import edu.pnu.dao.log.LogDaoH2Impl;
import edu.pnu.dao.member.MemberDaoH2Impl;
import edu.pnu.dao.member.MemberInterface;
import edu.pnu.domain.MemberVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberInterface memberDao;
	@Autowired
	private LogDao logDao;
	
//	public MemberService() {
		//@Autowired 사용 하려고 주석 처리
		//MemberService 클래스의 생성자에서 memberDao와 logDao를 직접적으로 인스턴스화하고 있습니다. 
		//이 경우 @Autowired를 사용하여 DI를 적용하는 의미가 없으므로, 
		//해당 부분을 삭제하거나 주석 처리하는 것이 좋습니다. 
		//아래 주석 처리
		//-------------------------------------
//		memberDao = new MemberDaoH2Impl();
//		// memberDao = new MemberDaoListImpl();
//
//		 logDao = new LogDaoH2Impl();
//		//logDao = new LogDaoFileImpl();
//		}

	@SuppressWarnings("unchecked")
	public List<MemberVO> getMembers() {
		Map<String, Object> map = memberDao.getMembers();
		List<MemberVO> list = (List<MemberVO>) map.get("data");
		if (list != null)
			logDao.addLog("get", (String) map.get("sql"), true);
		else
			logDao.addLog("get", (String) map.get("sql"), false);
		return list;
	}

	public MemberVO getMember(Integer id) {
		Map<String, Object> map = memberDao.getMember(id);
		MemberVO member = (MemberVO) map.get("data");
		if (member != null)
			logDao.addLog("get", (String) map.get("sql"), true);
		else
			logDao.addLog("get", (String) map.get("sql"), false);
		return member;
	}

	public MemberVO addMember(MemberVO member) {
		Map<String, Object> map = memberDao.addMember(member);
		MemberVO m = (MemberVO) map.get("data");
		if (m != null)
			logDao.addLog("post", (String) map.get("sql"), true);
		else
			logDao.addLog("post", (String) map.get("sql"), false);
		return m;
	}

	public MemberVO updateMember(MemberVO member) {
		Map<String, Object> map = memberDao.updateMember(member);
		MemberVO m = (MemberVO) map.get("data");
		if (m != null)
			logDao.addLog("put", (String) map.get("sql"), true);
		else
			logDao.addLog("put", (String) map.get("sql"), false);
		return m;
	}

	public MemberVO deleteMember(Integer id) {
		Map<String, Object> map = memberDao.deleteMember(id);
		MemberVO m = (MemberVO) map.get("data");
		if (m != null)
			logDao.addLog("delete", (String) map.get("sql"), true);
		else
			logDao.addLog("delete", (String) map.get("sql"), false);
		return m;
	}
}
