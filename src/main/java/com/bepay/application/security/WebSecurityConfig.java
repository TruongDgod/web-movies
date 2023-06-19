package com.bepay.application.security;

import com.bepay.application.constant.UrlConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private CookieRequestFilter cookieRequestFilter;

    public String[] AUTH_WHITELIST = {
            "/swagger-resources/",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout().deleteCookies(CookieRequestFilter.COOKIE_NAME)
                .and().authorizeRequests()
                .antMatchers(UrlConst.URL_AUTH_ACCEPT).permitAll()
                .antMatchers(UrlConst.URL_ALL_ACCEPT).permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(cookieRequestFilter,UsernamePasswordAuthenticationFilter.class );
    }
}
