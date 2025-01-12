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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    /* ************************************************************************************ */
    // Ahora vamos a aplicar seguridad

    httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
      .authorizeHttpRequests(http -> {
        http.requestMatchers(HttpMethod.GET, "/**","/auth/**").permitAll();
        http.requestMatchers(HttpMethod.POST, "/**","/auth/**").permitAll();
      })
      .formLogin(formlogin -> formlogin
        .usernameParameter("correo")
        .passwordParameter("clave")
        .loginPage("/auth/login")
        .loginProcessingUrl("/auth/login")
      )

      .exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/401"))

      .httpBasic(Customizer.withDefaults());

    /* ************************************************************************************ */

    return httpSecurity.build();

  }

  // Este metodo corresponde al paso 3 de la infografía
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // Nuestro proveedor, aún vacío, el paso 4 de la infografía
  // Nuestro proveveedor de autenticacion de interno, va separado de lo que sería un oauth
  // @Bean
  // AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailService){

  //   DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
  //   authenticationProvider.setPasswordEncoder(passwordEncoder());
  //   authenticationProvider.setUserDetailsService(userDetailService);
  //   return authenticationProvider;

  // }

  // El paso 5A de la infografía
  // @SuppressWarnings("deprecation")
  @Bean
  PasswordEncoder passwordEncoder(){
    
    // return NoOpPasswordEncoder.getInstance(); // Un metodo obsoleto con fines de prueba
    return new BCryptPasswordEncoder();
  }
  
  // El paso 5B de la infografía
  // Trabamos al parecer con un usuario en memoria
  // @Bean
  // UserDetailsService userDetailsService(){
  //   UserDetails upUserDetails = User.withUsername("Admin")
  //                                   .password("12345678")
  //                                   .roles("ADMIN")
  //                                   .authorities("READ", "CREATE")
  //                                   .build();
  //   return new InMemoryUserDetailsManager(upUserDetails);
  // }

}
