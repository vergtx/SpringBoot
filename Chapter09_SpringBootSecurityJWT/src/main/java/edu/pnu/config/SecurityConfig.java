package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.pnu.config.auth.JWTAuthorizationFilter;
import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.persistence.MemberRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Autowired //AuthenticationManager를 얻기 위해 DI 받는 객체
	private AuthenticationConfiguration authConfig;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable()); // CSRF 보호 비활성화 (JS에서 호출 가능)
		http.cors(cors->cors.disable()); // CORS 보호 비활성화 (React에서 호출 가능):RestAPI로 호출할 때

		http.authorizeHttpRequests(security->{
			security.requestMatchers("/member/**").authenticated() 
					.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll();
		});
		// 아래 2가지는 기존에서 추가됨
		
		http.formLogin(frmLogin -> frmLogin.disable()); // Form을 이용한 로그인을 사용하지 않겠다는 설정
		// 시큐리티 세션을 만들지 않겠다고 설정
		http.sessionManagement(ssmg->ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager()));
		
		http.addFilter(new JWTAuthorizationFilter(authConfig.getAuthenticationManager(),
				memberRepository));

		//JWTAuthenticationFilter의 인증 메소드attemptAuthentication에서 인증을 실행하기 위해 AuthenticationManager를 넘긴다.
		

		return http.build();

	}
}
