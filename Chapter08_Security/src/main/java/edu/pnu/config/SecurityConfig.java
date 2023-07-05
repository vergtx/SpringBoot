package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable());	//  CSRF 보호 비활성화 (JS에서 호출 가능)
		http.cors(cors->cors.disable());	//  CORS 보호 비활성화 (React에서 호출 가능) : RestAPI로 호출할 때

		// 웹페이지 접근 권한 설정
		http.authorizeHttpRequests(security->{
			security.requestMatchers("/member/**").authenticated()	
					.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll();
			//  /member와 하위 주소는 로그인한 사용자는 모두 접근 가능
			//  /manager와 하위 주소는 로그인한 ROLE_MANAGER, ROLE_ADMIN 권한을 가진 사용자만 접근 가능
			//  /admin과 하위 주소는 로그인한 ROLE_ADMIN 권한을 가진 사용자만 접근 가능
			//  위에서 설정한 이외 모든 주소는 접근 허용
		});

		http.formLogin(frmLogin-> {
			frmLogin.loginPage("/login")
					.defaultSuccessUrl("/loginSuccess", true);
		});

		
		http.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied"));
		http.logout(logt->{
			logt.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login");
		});


		return http.build();
	}
	
	@GetMapping("/member")
	public void forMember() {
		System.out.println("Member 요청입니다.");
	}
	
	@GetMapping("/manager")
	public void forManager() {
		System.out.println("Manager 요청입니다.");
	}
	
	@GetMapping("/admin")
	public void forAdmin() {
		System.out.println("Admin 요청입니다.");
	}
}
