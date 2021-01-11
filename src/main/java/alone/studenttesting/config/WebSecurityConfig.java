package alone.studenttesting.config;

import alone.studenttesting.entity.enums.Role;
import alone.studenttesting.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginServiceImpl loginService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeRequests(auth -> {
                    try {
                        auth.antMatchers("/home","/registrationPage","/register","/testPage",
                                "/test/**", "/loginPage","/authenticate" ).permitAll()
                                .antMatchers("/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
                                .antMatchers("/user/**").hasAnyAuthority(Role.ROLE_USER.name())
                                .anyRequest().authenticated()
                                .and()
                                .logout()
                                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                                .logoutSuccessUrl("/loginPage");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
