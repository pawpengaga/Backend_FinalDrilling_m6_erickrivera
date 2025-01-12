package com.pawpengaga.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pawpengaga.backend.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

  Optional<Alumno> findByRut(String rut);

  // Las triples comillas permiten hacer hacer bloques de texto como en Ruby
  @Query(value = """
    SELECT 
        a.id AS alumno_id,
        a.rut AS alumno_rut,
        a.nombre AS alumno_nombre,
        a.direccion AS alumno_direccion,
        ARRAY_AGG(mat.nombre) AS materias
    FROM 
        alumnos a
    JOIN 
        alumno_materia am ON a.id = am.alumno_id
    JOIN 
        materias mat ON am.materia_id = mat.id
    GROUP BY
        a.id, a.nombre
    ORDER BY 
        a.id
    """,
  nativeQuery = true)
  List<Object[]> findAlumnosWithMaterias();

}
