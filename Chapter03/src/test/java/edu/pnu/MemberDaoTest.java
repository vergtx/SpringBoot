package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import edu.pnu.dao.MemberDao;
import edu.pnu.domain.Member;

@SpringBootTest
//@TestMethodOrder(OrderAnnotation.class)
public class MemberDaoTest {

	@Test
	public void insertMemberTest() {
		MemberDao memberDao = new MemberDao();
		for (int i = 1; i <= 10; i++) {
			memberDao.insertMember(
					Member.builder()
					.name("name" + i)
					.age(20 + i)
					.nickname("nickname" + i)
					.build()

			);
		}
	}
	
	@Test
	@Order(2)
	public void selectAllMemberTest() {
		MemberDao memberDao = new MemberDao ();
		
		List<Member> list = memberDao.getMembers();
		for (Member m : list) {
			System.out.println(m);
		}
	}
}
