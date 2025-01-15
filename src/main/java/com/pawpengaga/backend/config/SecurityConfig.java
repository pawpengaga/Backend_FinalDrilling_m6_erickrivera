package com.pawpengaga.backend.config;

import javax.swing.text.html.HTML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.pawpengaga.backend.model.RoleEnum;
import com.pawpengaga.backend.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    // @Autowired
    // JWUtils jwtUtils;

    /* ************************************************************************************ */

    httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
      .authorizeHttpRequests(http -> {

        
        http.requestMatchers(HttpMethod.GET, "/api/v1/auth/**").permitAll();
        http.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll();
        
        // http.requestMatchers(HttpMethod.GET, "/api/v1/alumnos/**", "/api/v1/materias/**").permitAll();
        // http.requestMatchers(HttpMethod.POST, "/api/v1/alumnos/**", "/api/v1/materias/**").permitAll();
        
        http.requestMatchers(HttpMethod.GET, "/api/v1/alumnos/**", "/api/v1/materias/**").hasAuthority("READ");
        http.requestMatchers(HttpMethod.POST, "/api/v1/alumnos/**", "/auth/**", "/api/v1/materias/**").hasAuthority("CREATE");
        
        http.anyRequest().authenticated();
      
      })
      .formLogin(formlogin -> formlogin
        .usernameParameter("correo")
        .passwordParameter("clave")
        .loginProcessingUrl("/api/v1/auth/login")
        // .loginPage("/auth/login")
      )

      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults());
      // .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class);

    /* ************************************************************************************ */

    return httpSecurity.build();

  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /* HABILITADO POR FIN */  
  @Bean
  AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailService){
    
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailService);
    return authenticationProvider;
    
  }
  /* HABILITADO POR FIN */  
  
  
  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  // No usaremos usuario en memoria ni siquiera con fines de prueba

}
