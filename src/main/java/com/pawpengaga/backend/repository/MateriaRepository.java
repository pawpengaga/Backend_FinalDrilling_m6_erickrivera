package com.pawpengaga.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pawpengaga.backend.model.Materia;
import java.util.List;


public interface MateriaRepository extends JpaRepository<Materia, Long> {

  Materia findByNombre(String nombre);

}
