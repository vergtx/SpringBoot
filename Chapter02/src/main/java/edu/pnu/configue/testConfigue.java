package edu.pnu.configue;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class testConfigue {
	
	public testConfigue() {
		System.out.println("=".repeat(50));
		System.out.println("testConfigue가 실행되었습니다.");
		System.out.println("=".repeat(50));
	
		// TODO Auto-generated constructor stub
	}
	@Bean // 리턴하는 객체 Testbean을 자동으로 메모리에 올린다.
	public Testbean testbean() {
		return new Testbean();
	}
}
	class Testbean {
		public Testbean() {
			// TODO Auto-generated constructor stub
			System.out.println("=".repeat(50));
			System.out.println("Testbean가 실행되었습니다.");
			System.out.println("=".repeat(50));
		}
	}

