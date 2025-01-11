package com.pawpengaga.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pawpengaga.backend.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

  Optional<Alumno> findByRut(String rut);

}
