package com.pawpengaga.backend.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  private String nombre;
  
  @Column(unique = true)
  private String username;
  
  @Column(unique = true)
  private String correo;

  private String clave;



  // ATRIBUTOS REQUERIDOS POR SPRING SECURITY
  
  @Column(name = "is_expired")
  private boolean isEnabled;
  
  @Column(name = "account_no_expired")
  private boolean accountNoExpired;
  
  @Column(name = "account_no_locked")
  private boolean accountNoLocked;
  
  @Column(name = "credential_no_expired")
  private boolean credentialNoExpired;

  // FIN

  private List<Role> roles;
  

}
