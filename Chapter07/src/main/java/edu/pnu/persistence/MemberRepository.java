package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;


//member table 에 인터페이스
public interface MemberRepository extends JpaRepository<Member, String> {
	
	
	

}
