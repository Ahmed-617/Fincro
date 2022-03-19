package tn.microfinance.fincro.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class configSecurity extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .antMatcher("/**").authorizeRequests()
                .antMatchers(new String[]{"/", "/not-restricted","getAllUsers"}).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();


}}
