package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// 이페이지는 시큐리티 기본 말고 커스터마이징 하겠다는 이야기
	
	@Autowired
	private BoardUserDetailsService boardUserDetailsService;
	
	@Bean
	public BCryptPasswordEncoder encorder() { // 암호화 라이브러리
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		
		security.authorizeHttpRequests(auth -> {
	
			auth.requestMatchers("/").permitAll()
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/memager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMINN")
				.anyRequest().permitAll();
		});
		
		security.csrf(csrf->csrf.disable());
		security.formLogin(form->{
			form.loginPage("/login")
				.defaultSuccessUrl("/loginSuccess", true);
		}); 
		
		security.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied"));
		security.logout(logt->{
			logt.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/login");
		});
		
		security.userDetailsService(boardUserDetailsService);
		
		return security.build();
						
	}
	
	@Autowired
	public void authenticate(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("member")
			.password("{noop}abcd")
			.roles("MEMBER");
		auth.inMemoryAuthentication()
			.withUser("manager")
			.password("{noop}abcd")
			.roles("MANAGER");
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("{noop}abcd")
			.roles("ADMIN");
	}
}
