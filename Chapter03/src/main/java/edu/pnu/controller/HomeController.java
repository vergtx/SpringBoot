package edu.pnu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HomeController {
	
	public HomeController() {
		System.out.println("HomeController가 생성되었습니다.");
		log.error("HomeControlle errorr가 생성되었습니다.");
		log.warn("HomeController warn가 생성되었습니다.");
		log.trace("HomeController trace가 생성되었습니다.");
		log.debug("HomeController debug가 생성되었습니다.");
		log.info("HomeController info가 생성되었습니다.");
		
	}
	
	@GetMapping ("/hello")
	public String hello(String name) {
		return "Hello : " + name;
	}

}
