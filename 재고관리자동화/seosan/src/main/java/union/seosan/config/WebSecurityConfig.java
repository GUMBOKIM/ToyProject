package union.seosan.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import union.seosan.service.UserService;

@RequiredArgsConstructor
@EnableWebSecurity // Spring-Security 활성
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // SpringSecurity Configuration 역할을 위해 상속

    private final UserService userService;

    @Override // 인증을 무시할 경로 지정 - static 파일들(css, js)
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Http고나련 인증 설정
        http.authorizeRequests() // 6
                .antMatchers("**").permitAll(); // 누구나 접근 허용
//                .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
//                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
//                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
//                .and()
//                .formLogin() // 로그인 페이지 링크
//                .loginPage("/login") // 로그인 페이지 링크
//                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
//                .and()
//                .logout() // 8
//                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
//                .invalidateHttpSession(true); // 세션 날리기

    }
}
