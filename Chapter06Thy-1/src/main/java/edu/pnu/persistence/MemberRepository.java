package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	
// 데이터베이스에서 데이터 읽어올수 있는 인터페이스
}
