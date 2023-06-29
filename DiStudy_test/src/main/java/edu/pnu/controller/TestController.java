package edu.pnu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.Service.TestService;
import edu.pnu.Service.TestService2;

@RestController
public class TestController {
	
//	@Autowired
//	private TestService testService;
//	
//	@Autowired
//	private TestService2 testService2;
//	
//	public TestController () {
//		System.out.println("Testcontroller");
//	}

	private  TestService testService;
	private TestService2 testService2;
	
//	@Autowired
//	public TestController(TestService testService, TestService2 testService2) {
//		this.testService = testService ;
//		this.testService2 = testService2;
//		System.out.println("Testcontroller");
//	}
//	
	
	
	@GetMapping ({"/", "/home"})
	public String home() {
		return "home";
	}
	

}
