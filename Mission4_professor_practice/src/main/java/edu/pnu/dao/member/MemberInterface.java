package edu.pnu.dao.member;

import java.util.Map;

import edu.pnu.domain.MemberVO;

public interface MemberInterface { //인터페이스는 메서드의 명세를 제공하지만 구체적인 구현은 각 클래스에서 수행

	Map<String, Object> getMembers();

	Map<String, Object> getMember(Integer id);

	Map<String, Object> addMember(MemberVO member);

	Map<String, Object> updateMember(MemberVO member);

	Map<String, Object> deleteMember(Integer id);

}