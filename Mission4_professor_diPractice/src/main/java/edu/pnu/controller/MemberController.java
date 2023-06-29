package edu.pnu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberVO;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor //4번

public class MemberController {
	
	//@RequiredArgsConstructor 을 사용 하려면 final 붙여야 함
	private final MemberService memberService;
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	
//	@Autowired 1번 @Autowired 만 추가하고 //memberService = new MemberService();  서비스 호출문 삭제 왜냐면 오토 와이어로 서비스 di에서 연결 함
	
//	public MemberController() {
//	
//		log.info("MemberController() 생성자가 호출됨.");
//		//memberService = new MemberService();  @Autowired쓰러면 내가 만든 객체 사용 하면 안된다. 그래서 각주 처리 해서 실행안되게 처리 해줌
//	}
	
	
	//@RequiredArgsConstructor는 생성자 기능을 해줌 사용하려면 생성자 삭제 @Autowired 삭제
	
	
	
//	@Autowired 3번 lombok 이 없는 경우 이걸 사용
//	public MemberController(MemberService memberService) {
//		this.memberService =  memberService;
//		log.info("MemberController() 생성자가 호출됨.");
//		//memberService = new MemberService();  @Autowired쓰러면 내가 만든 객체 사용 하면 안된다. 그래서 각주 처리 해서 실행안되게 처리 해줌
//	}
	
	@GetMapping("/member")
	public List<MemberVO> getMembers() {
		log.info("MemberController - getMembers()가 호출됨.");
		return memberService.getMembers();
	}

	@GetMapping("/member/{id}")
	public MemberVO getMember(@PathVariable Integer id) {
		log.info(String.format("MemberController - getMember(%d)가 호출됨.", id));
		return memberService.getMember(id);
	}

	@GetMapping("/member/body") // JSON으로 데이터를 요청하는 경우
	public MemberVO getMemberbyJSON(@RequestBody MemberVO member) {
		log.info(String.format("MemberController - getMemberbyJSON(%s)이 호출됨.", member));
		return memberService.getMember(member.getId());
	}
	
	@PostMapping("/member")
	public MemberVO addMember(MemberVO member) {
		log.info(String.format("MemberController - addMember(%s)가 호출됨.", member));
		return memberService.addMember(member);
	}

	@PutMapping("/member")
	public MemberVO updateMember(MemberVO member) {
		log.info(String.format("MemberController - updateMember(%s)가 호출됨.", member));
		return memberService.updateMember(member);
	}
	
	@DeleteMapping("/member/{id}")
	public MemberVO deleteMember(@PathVariable Integer id) {
		log.info(String.format("MemberController - deleteMember(%ㅇ)가 호출됨.", id));
		return memberService.deleteMember(id);
	}
}
