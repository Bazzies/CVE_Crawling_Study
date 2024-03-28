//package com.example.cveservertest.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    // 관리자 권한이 있는 사용자만 "/cve/find"에 접근할 수 있도록 설정
//    // 모든 요청에 대해 인증된 사용자만 접근할 수 있도록 설정
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/cve/find").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic(); //평문으로 TEST진행하였음
//    }
//
//    @Bean
//    //메모리에 사용자를 저장하고 관리하는 킄래스
//    public UserDetailsService userDetailsService() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("admin").password(encoder.encode("Intin8080")).roles("ADMIN").build());
//        return manager;
//    }
//}
