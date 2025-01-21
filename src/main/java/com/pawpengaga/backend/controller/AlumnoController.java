package com.pawpengaga.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.repository.AlumnoRepository;
import com.pawpengaga.backend.repository.MateriaRepository;
import com.pawpengaga.backend.service.AlumnoService;
import com.pawpengaga.backend.service.MateriaService;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

  private static final Logger myLogger = LoggerFactory.getLogger(AlumnoController.class);

  @Autowired
  AlumnoService alumnoService;

  @Autowired
  MateriaService materiaService;

  /* ************************************************** */
  
  @GetMapping
  public ResponseEntity<List<Alumno>> listar() {
    List<Alumno> alumnos = alumnoService.listarAlumnos();
    if (alumnos.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alumnos);
    }
    return ResponseEntity.ok(alumnos);
  }

  @PostMapping("/grabar")
  public ResponseEntity<String> grabarAlumnos(@RequestBody Alumno alumno){
    try {

      Alumno alumnoGuardar = alumnoService.guardarAlumno(alumno);

      if (alumnoGuardar == null) {
        myLogger.error("Ocurrió un error a nivel de controlador REST al guardar al alumno");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("El alumno no ha podido guardarse...");
      }

      myLogger.info("Alumno guardado!: {}", alumno);
      return ResponseEntity.ok("Alumno guardado!:\n" + alumnoGuardar);
      
    } catch (Exception e) {
      myLogger.error("Ocurrió un error a nivel de controlador REST al guardar al alumno", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    // return ResponseEntity.ok("Alumno guardado. Revise los logs para más información " + alumnoService.guardarAlumno(alumno));
  }

}
