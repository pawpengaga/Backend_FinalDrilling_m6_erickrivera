package com.pawpengaga.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "alumnos")
public class Alumno {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, nullable = false, unique = true)
  private String rut;
  
  @Column(length = 100, nullable = false)
  private String nombre;
  
  @Column(length = 100, nullable = false)
  private String direccion;
  
  // @JoinTable(name = "alumno_materia", joinColumns = @JoinColumn(name="alumno_id"), inverseJoinColumns = @JoinColumn(name = "materia_id"))
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "alumno_materia",
    joinColumns = {
      @JoinColumn(name = "alumno_id", referencedColumnName = "id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "materia_id", referencedColumnName = "id")
    }
  )
  @Column(nullable = false)
  private Set<Materia> materiaList = new HashSet<>();

  @Override
  public String toString() {
      return "{\n" +
             "  \"id\": \"" + getId() + "\",\n" +
             "  \"rut\": \"" + getRut() + "\",\n" +
             "  \"nombre\": \"" + getNombre() + "\",\n" +
             "  \"direccion\": \"" + getDireccion() + "\",\n" +
             "  \"materias\": " + getMateriaList() + "\n" +
             "}";
  }

}
