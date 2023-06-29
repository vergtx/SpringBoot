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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

@RequiredArgsConstructor // 4번
public class MemberController {
	
	// 코드를 추가 하려면 2번 이나 3번 방법 사용해야 한다.
	
	//1번 핃드에 설정하는 반법
//	@Autowired //1번 오토 와이어 사용법
	private final MemberService memberService; /// 4번 때 private다음에 finial 입력
//	
//	public MemberController() {
//	System.out.println("member contoller 가 실행되었습니다.");	
//	log.info("member contoller 가 실행되었습니다.");
//	
////	memberService = new MemberService(); 기존작성 내용 삭제
//	
//	}
	
	
	//2번 생성자에 설정하는 방법
//	@Autowired
//	public MemberController(MemberService memberService) {
//		this.memberService = memberService;
//	System.out.println("member contoller 가 실행되었습니다.");	
//	log.info("member contoller 가 실행되었습니다.");
//	
////	memberService = new MemberService(); 기존작성 내용 삭제
//	
//	}
//	
//	//3번 Setter 사용하는 방법
//	@Autowired
//	private void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}
	
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
