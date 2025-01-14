package com.pawpengaga.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pawpengaga.backend.model.Usuario;
import com.pawpengaga.backend.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsuarioRepository userRepo;

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

}
