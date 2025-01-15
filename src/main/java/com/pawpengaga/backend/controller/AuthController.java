package com.pawpengaga.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawpengaga.backend.dto.AuthLoginRequest;
import com.pawpengaga.backend.dto.AuthResponse;
import com.pawpengaga.backend.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;
  
  @Autowired
  UserDetailsServiceImpl userDetailsServiceImpl;
  
  @PostMapping("/log-in")
  public ResponseEntity<AuthResponse> restLogin(@RequestBody @Valid AuthLoginRequest userRequest){

    return new ResponseEntity<>(userDetailsServiceImpl.loginUser(userRequest), HttpStatus.OK);
    
  }

}
