package com.pawpengaga.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.service.AlumnoService;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

  private static final Logger myLogger = LoggerFactory.getLogger(AlumnoController.class);

  @Autowired
  AlumnoService alumnoService;

  /* ************************************************** */

  @GetMapping("/")
  public ResponseEntity<List<Alumno>> listar(){
    myLogger.info("Listando los alumnos a través de ResponseEntity...");

    List<Alumno> alumnosRecibidos = alumnoService.listarAlumnos();

    if (alumnosRecibidos.size() < 1) {
      myLogger.warn("No existen alumnos registrados el momento...");
    }

    return ResponseEntity.ok(alumnosRecibidos);
  }

  @PostMapping("/grabar")
  public ResponseEntity<String> grabarAlumnos(@RequestBody Alumno alumno){
    try {
      alumnoService.guardarAlumno(alumno);
      myLogger.info("Alumno guardado!: {}", alumno);
      return ResponseEntity.ok("Alumno guardado. Revise los logs para más información");
    } catch (Exception e) {
      myLogger.error("Ocurrió un error a nivel de controlador REST al guardar al alumno", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }


}
