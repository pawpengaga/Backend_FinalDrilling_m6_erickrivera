package com.pawpengaga.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pawpengaga.backend.dto.AuthLoginRequest;
import com.pawpengaga.backend.dto.AuthResponse;
import com.pawpengaga.backend.model.Usuario;
import com.pawpengaga.backend.repository.UsuarioRepository;
import com.pawpengaga.backend.utils.JWUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsuarioRepository userRepo;

  @Autowired
  JWUtils jwtUtils;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Usuario userEntity = userRepo.findByCorreo(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado..."));

    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    userEntity.getRoles().forEach(role -> {
      authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())));
    });

    userEntity.getRoles().stream().flatMap(role -> role.getPermisos().stream())
      .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getName())));

    System.out.println("------------------------------------------------------------------");
    
    System.out.println("AUTHORITIES PARA: " + userEntity.getCorreo());

    for (SimpleGrantedAuthority theAuth : authorityList) {
      System.out.println(theAuth);
    }

    System.out.println("------------------------------------------------------------------");


      return new User(
        userEntity.getCorreo(),
        userEntity.getClave(),
        userEntity.isEnabled(),
        userEntity.isAccountNoExpired(),
        userEntity.isAccountNoLocked(),
        userEntity.isCredentialNoExpired(),
        authorityList
      );

  }

  public AuthResponse loginUser(AuthLoginRequest userRequest){
    
    String username = userRequest.username();
    String password = userRequest.password();

    Authentication auth = authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(auth);

    String accessToken = jwtUtils.createToken(auth);
    
    AuthResponse authResponse = new AuthResponse(username, "Usuario logeado con exito", accessToken, true);

    return authResponse;
        
    }
    
    private Authentication authenticate(String username, String password) {

      UserDetails userDetails = this.loadUserByUsername(username);

      if (userDetails == null) {
        throw new BadCredentialsException("El nombre de usuario o contraseña invalidas. Intente nuevamente.");
      }

      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("Algo falla");
      }

      return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());


    }

}
