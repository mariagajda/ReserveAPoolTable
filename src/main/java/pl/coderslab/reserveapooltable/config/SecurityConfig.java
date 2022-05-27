package pl.coderslab.reserveapooltable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation
        .web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.reserveapooltable.service.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/static/**", "/resources/**", "/resources/static/**").permitAll();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/holiday-workdays/**").hasRole("ADMIN")
                .antMatchers("/price/**").hasRole("ADMIN")
//
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/reservation/date", false)
                .failureUrl("/user/log-failed")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "basketId")
//                .logoutSuccessHandler(logoutSuccessHandler())
//                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/errors/403")

        ;

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

}
