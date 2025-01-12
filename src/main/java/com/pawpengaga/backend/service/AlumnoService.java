package com.pawpengaga.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.model.Materia;
import com.pawpengaga.backend.repository.AlumnoRepository;
import com.pawpengaga.backend.repository.MateriaRepository;

@Service
public class AlumnoService {

  private static final Logger myLogger = LoggerFactory.getLogger(AlumnoService.class);

  @Autowired
  AlumnoRepository alumnoRepo;

  @Autowired
  MateriaRepository materiaRepo;

  /* ************************************************* */
  // Anotación: Uso experimental de los logs
  /*
   * Anotación: Uso experimental de lógica transaccional
   * Problema: Los datos no se renderizan para el método GET y presenta error
   * Solución: Usar @Transactional
   * Explicación: La manera en que funcionan las transacciones de SpringBoot
   * permite mantener abierta la conexión a base de datos para obtener las materias
   * en operaciones de lectura e inserción
   */

  // @Transactional
  public Alumno guardarAlumno(Alumno alumno) {
      try {
        if (alumno.getMateriaList() != null) {

          alumno.getMateriaList().forEach(materia -> {
              Materia temp = materiaRepo.findById(materia.getId()).orElse(null);
              Alumno alumnoTemp = new Alumno();
              if (temp != null) {

                materia.setNombre(temp.getNombre());
                alumnoTemp.setId(alumno.getId());
                materia.getAlumno().add(alumnoTemp);

              }
              System.out.println("LA MATERIA AFECTADA ES: " + materia.toString());
          });
        }

          System.out.println("EL ALUMNO INGRESADO ES: " + alumno);
          
          return alumnoRepo.save(alumno);
          // return new Alumno();

      } catch (Exception e) {
          myLogger.error("Hubo un error al guardar el alumno... {}", e);
          return null;
      }
  }

  public List<Alumno> listarAlumnos() {
    try {

      List<Alumno> alumnos = new ArrayList<>();

      List<Object[]> alumnosRecuperados = alumnoRepo.findAlumnosWithMaterias();
      alumnos = alumnosRecuperados.stream()
        .map(row -> new Alumno(
          ((Number) row[0]).longValue(),
          (String) row[1],
          (String) row[2],
          (String) row[3],
          Arrays.stream((String[]) row[4])
            .map(nombreMateria -> new Materia(materiaRepo.findByNombre(nombreMateria).getId(), nombreMateria, new HashSet<>()))
            .collect(Collectors.toSet())
        ))
        .toList();

      return alumnos;
    } catch (Exception e) {
      System.err.println("---------------------------------------------");
      System.err.println("HA OCURRIDO UN ERROR A NIVEL DE SERVICIO" + e.getMessage());
      System.err.println("---------------------------------------------");
      return new ArrayList<>();
    }
  }



}
