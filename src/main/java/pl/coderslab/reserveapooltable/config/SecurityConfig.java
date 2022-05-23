package pl.coderslab.reserveapooltable.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation
        .web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.reserveapooltable.service.MyRegisteredUserDetailsService;
import pl.coderslab.reserveapooltable.service.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyRegisteredUserDetailsService registeredUserDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}admin123").roles("ADMIN");
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/holiday-workdays/**").hasRole("ADMIN")
                .antMatchers("/price/**").hasRole("ADMIN")
                .antMatchers("/home").permitAll()
                .antMatchers("/reservation/**").permitAll()
                .antMatchers( "/user/**").permitAll()
                .antMatchers("/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/reservation/date", true)
                .failureUrl("/user/log-failed")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "basketId")
                //.logoutSuccessHandler(logoutSuccessHandler())
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(registeredUserDetailsService);
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("adminPass1!")).roles("ADMIN");
    }
}
