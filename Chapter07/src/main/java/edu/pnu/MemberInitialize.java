package edu.pnu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
@Component
public class MemberInitialize implements ApplicationRunner {
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	BCryptPasswordEncoder encorder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		memRepo.save(Member.builder()
				.username("member")
				.password(encorder.encode("abcd"))
				.role("ROLE_MEMBER")
				.enabled(true)
				.build());
		memRepo.save(Member.builder()
				.username("manager")
				.password(encorder.encode("abcd"))
				.role("ROLE_MANAGER")
				.enabled(true)
				.build());
		memRepo.save(Member.builder()
				.username("admin")
				.password(encorder.encode("abcd"))
				.role("ROLE_ADMIN")
				.enabled(true)
				.build());

		// TODO Auto-generated method stub

	}

}
