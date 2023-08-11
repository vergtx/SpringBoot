package edu.pnu.DataInitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminUsername = "admin";
        String adminPassword = "admin"; // 초기 비밀번호, 나중에 변경해야 함
        String adminRole = "ROLE_ADMIN"; // ADMIN 역할

        if (!memberRepository.existsByUsername(adminUsername)) {
            Member adminMember = Member.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword)) // PasswordEncoder 사용
                    .role(adminRole)
                    .enabled(true)
                    .build();

            memberRepository.save(adminMember);
        }
    }
}
