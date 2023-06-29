package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
	
	private MemberService memberService;
	
	public MemberController() {
	System.out.println("member contoller 가 실행되었습니다.");	
	log.info("member contoller 가 실행되었습니다.");
	
	memberService = new MemberService();
	}
	
	//localhost:8080/member/1
	@GetMapping("/member/{id}")
	public Member getMember(@PathVariable Long id) {
		return memberService.getMember(id);
		
	}
	
	@GetMapping("/member") // 모든 데이터 리턴
	public List<Member> getMembers() {
	    System.out.println("getMembers:  ");
	    return memberService.getMembers();
	}
	
	@PostMapping("/member")
	public int insertMember (Member member) {
		System.out.println("insertMember: " + member);
		return memberService.insertMember(member);
	}
	
	@PutMapping("/member")
	public int updateMember(Member member) {
	    System.out.println("updateMember: " + member);
	    return memberService.updateMember(member);
	}
	
	@DeleteMapping("/member/{id}")
	public int deleteMember (@PathVariable Long id) {
		System.out.println("deleteMember: " + id);
		return memberService.deleteMember(id);
	}
	
	
}
