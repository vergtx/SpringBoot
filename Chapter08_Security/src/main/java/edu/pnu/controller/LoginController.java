package edu.pnu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.pnu.config.MemberService;
import edu.pnu.domain.Member;

@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;


	@GetMapping("/login")
	public void login() {}

	@GetMapping("/loginSuccess")
	public void loginSuccess() {}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {}
	
	@GetMapping("/auth")
	public @ResponseBody String auth(@AuthenticationPrincipal User user) {
		return user.toString();
	}
	
	@GetMapping ("/join")
	public void join() {}

	@PostMapping ("/join")
	public String joinProc(Member member) {
		memberService.save(member);
		return "welcome";
	}



}
