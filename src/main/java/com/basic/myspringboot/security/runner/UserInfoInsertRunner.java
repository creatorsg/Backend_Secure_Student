package com.basic.myspringboot.security.runner;

import com.basic.myspringboot.security.models.UserInfo;
import com.basic.myspringboot.security.models.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 실습용 계정을 만들어 두는 러너.
 *
 * 이미 계정이 있으면 건너뛴다. ( ddl-auto 가 update 인 환경에서 email UNIQUE 위반을 막는다 )
 */
@Order(1)
@Component
@RequiredArgsConstructor
@Slf4j
public class UserInfoInsertRunner implements ApplicationRunner {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userInfoRepository.count() > 0) {
            log.info("UserInfo 데이터가 이미 있어 초기화를 건너뜁니다");
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setName("adminboot");
        userInfo.setPassword(passwordEncoder.encode("pwd1"));
        userInfo.setEmail("admin@aa.com");
        userInfo.setRoles("ROLE_ADMIN,ROLE_USER");

        UserInfo userInfo2 = new UserInfo();
        userInfo2.setName("userboot");
        userInfo2.setPassword(passwordEncoder.encode("pwd2"));
        userInfo2.setEmail("user@aa.com");
        userInfo2.setRoles("ROLE_USER");

        userInfoRepository.saveAll(List.of(userInfo, userInfo2));
        log.info("실습용 계정 2개를 생성했습니다 ( admin@aa.com / user@aa.com )");
    }
}
