package edu.pnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecuriyController {
	
	@GetMapping({"/","/index"})
	public String index() {
		System.out.println("index 요청입니다.");
		return "index";
	}
	


}


