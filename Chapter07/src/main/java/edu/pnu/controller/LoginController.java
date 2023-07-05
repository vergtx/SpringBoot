package edu.pnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.pnu.domain.Member;

@Controller
public class LoginController {

	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {}
	

}
