package com.cczora.armybuilder.config;

import com.cczora.armybuilder.config.jwt.JwtRequestFilter;
import com.cczora.armybuilder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userDetails;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(@Lazy UserService userDetails, JwtRequestFilter jwtRequestFilter) {
        this.userDetails = userDetails;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                requiresChannel()
                //this is necessary in order for http requests to be forwarded correctly to Heroku
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
                .and()
                .csrf().disable()
                .authorizeRequests()
                /*allows anyone to get into the authentication controller to get their JWT,
                and allows swagger to get in too!*/
                .antMatchers("/authenticate", "/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                //...but any other requests have to be authenticated first
                .antMatchers("/army", "/detachment", "/unit").authenticated()
//                //don't manage sessions for me
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/home")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?login_error=1").permitAll()
                .and()
                .logout().logoutRequestMatcher(
                new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobalInDB(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
