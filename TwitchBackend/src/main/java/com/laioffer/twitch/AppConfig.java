package com.laioffer.twitch;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.sql.DataSource;


@Configuration
public class AppConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // disable了 crosside recourse fordary
                // 不然他会拒绝服务
                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/**").permitAll()
//                        // 看/之后 然后允许所有路径 所有请求
                        auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/*.json", "/*.png", "/static/**").permitAll()
                                //用现有的东西上面两行 PathRequest 和HttpMethod写好了
                                //.requestMatchers("/hello/**").permitAll()
                                //没什么关系的东西
                                .requestMatchers(HttpMethod.POST, "/login", "/register", "/logout").permitAll()
                                //这三个 必须permit all , 放作为free to access
                                .requestMatchers(HttpMethod.GET, "/recommendation", "/game").permitAll()
                                //没有登陆 也可以访问get recommendation 和 get game
                                .anyRequest().authenticated()
                                //有顺序的 这个必须放最后 不然前面的都不执行了 这是any request

                )
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // 没注册 上面两行 然后处理这种情况
                .and()
                .formLogin()
                .successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
                //login 成功给你个no_content
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
                //logout 成功给你个no_content no content可以改
        return http.build();
    }

    @Bean
    //spring靠他们俩 在yml里创建datasource
    //创建账号
    UserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    //b crypt 来加密密码
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}



