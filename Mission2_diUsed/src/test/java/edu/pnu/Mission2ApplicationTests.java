package edu.pnu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class Mission2ApplicationTests {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	DataSource dataSource;
	
	@Test
	
	public void dataSourceTest() throws SQLException {
		Statement st = dataSource.getConnection().createStatement();
		ResultSet rs =  st.executeQuery("select * from Member");
		while(rs.next()) {
			
		}
	}
	
	@Test
	@Order(1)
	public void contextLoads() {
		System.out.println("=".repeat(40));
		List<Member> list = memberService.getMembers();
		for (Member m : list) {
			System.out.println(m);
			
		}
		System.out.println("=".repeat(40));
	
		
	}

}
