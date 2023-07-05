package edu.pnu.config.auth;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private MemberRepository memRepo;
	// 생성자는 부모 클래스가 AuthenticationManager 객체를 요구한다.
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memRepo) {
		
		super(authenticationManager);
		
		this.memRepo = memRepo;
		// TODO Auto-generated constructor stub
	}
	// 필터링 메서드를 오더라이드한다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String srcToken = request.getHeader("Authorization");
		if(srcToken == null || !srcToken.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
		String jwtToken = srcToken.replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwtkey")).build().verify(jwtToken).getClaim("username").asString();
		// 토큰에서 얻은 username으로 DB를 검색해서 사용자를 찾고
		Optional<Member> opt = memRepo.findById(username);
		if(!opt.isPresent()) {
			chain.doFilter(request, response);
			return;
		}
		
		Member findmember = opt.get();
		// DB에서 읽은 사용자 정보를 이용해서 UserDetails 타입의 객체를 만들어서
		User user = new User(findmember.getUsername(), findmember.getPassword(), findmember.getAuthorities());
		// Authentication 객체를 생성 : 사용자명과 권한 관리를 위한 정보를 입력(암호는 필요없음)
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		// 시큐리티 세션에 등록한다.
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		chain.doFilter(request, response);
		
	}
	

}
