package edu.pnu.config.filter;



import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//로그인 인증을 위한 시큐리티 필터 작성
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {
		
 	private final AuthenticationManager authenticationManager; 
	 
	@Override
	// 로그인 인증 시도를 위한 메소드 오버라이딩
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
		throws AuthenticationException {
		// 여기에서 인증을 위한 토큰을 생성해서 인증을 요청하고 성공하면 세션에 인증정보를 등록한다.
		// req의 Body에 JSON으로 담겨오는 username/password를 읽어서 Member 객체로 받아온다.
		log.info("attemptAuthentication :");
		
		ObjectMapper om = new ObjectMapper();
		try {
			// req.getInputStream()에서 직접 읽어서 객체를 만들어도 된다. 자신감을 가지고 해보자.
			
			Member member = om.readValue(req.getInputStream(), Member.class);
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
			Authentication auth = authenticationManager.authenticate(authToken);
			//UserDetails – loadUserbyUsername 메소드에서	DB 검색 후 리턴되어 온 객체를 Session의 Authenticaton에 등록한다.

			// 읽어 들인 정보가 정확한지 콘솔에 출력해본다.
			log.info("Authenticated :[" +  member.getUsername() + "]");
			return auth;

		} catch (Exception e) {
			log.info("Not Authenticated : " + e.getMessage());

		}
		return null;
	}

	@Override
	// 성공적으로 로그인 인증이 완료된 후 처리를 위한 메소드 오버라이딩
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		// JWT 토큰(문자열)을 만들어서 resp의 Header에 등록한다.
		
		User user = (User)authResult.getPrincipal();
		log.info("SuccessfulAuthentication:" + user.toString());
		
		// JWT 생성
		String jwtToken = JWT.create()
				.withClaim("username", user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10))
				.sign(Algorithm.HMAC256("edu.pnu.jwtkey"));
		// 응답 Header에 "Authorization"이라는 키를 추가해서 JWT를 설정
		// Bearer : JWT임을 나타내는 용어; Basic : "Basic "+Base64(username:password)
		response.addHeader("Authorization", "Bearer " + jwtToken);
		chain.doFilter(request, response);

	}
	
	
}




