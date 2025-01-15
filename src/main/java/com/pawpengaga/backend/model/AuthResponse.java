package com.pawpengaga.backend.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthResponse {

  @NotBlank
  private String correo;

  @NotBlank
  private List<String> roles;

}
