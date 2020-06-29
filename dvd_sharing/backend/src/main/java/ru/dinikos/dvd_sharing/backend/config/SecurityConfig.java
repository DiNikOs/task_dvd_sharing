/**
 * @author Ostrovskiy Dmitriy
 * @created 21.06.2020
 * SecurityConfig
 * @version v1.0
 */

package ru.dinikos.dvd_sharing.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.dinikos.dvd_sharing.backend.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class JwtTokenWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private JwtFilter jwtFilter;

        @Autowired
        public void setJwtFilter(JwtFilter jwtFilter) {
            this.jwtFilter = jwtFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/v0/disks/free").authenticated()
                    .antMatchers("/api/v0/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/v0/users/**").hasAnyRole("ADMIN","USER")
                    .antMatchers("/api/v0/disks/**").hasRole("USER")
                    .anyRequest().permitAll()
                    .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(2)
    public static class BasicWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private DaoAuthenticationProvider provider;

        @Autowired
        public void setProvider(DaoAuthenticationProvider provider) {
            this.provider = provider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/disks/**").hasRole("USER")
                    .antMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/admin/users/**").hasRole("ADMIN")
                    .antMatchers("/profile/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/auth")
                    .permitAll()
                    .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll().and().exceptionHandling().accessDeniedPage("/403");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(provider);
        }
    }
}


