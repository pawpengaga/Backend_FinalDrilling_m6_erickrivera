package com.pawpengaga.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawpengaga.backend.model.AuthResponse;
import com.pawpengaga.backend.model.LoginRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public AuthResponse loginProcess(@Valid @RequestBody LoginRequest request){

    Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    List<String> roles = userDetails
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .toList();
    
    AuthResponse authResponse = new AuthResponse(userDetails.getUsername(), roles);

    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    System.out.println(request.toString());
    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

    System.out.println("EL ESTADO DE LA AUTHRESPONSE ES: " + authResponse);

    return authResponse;
  }

}
