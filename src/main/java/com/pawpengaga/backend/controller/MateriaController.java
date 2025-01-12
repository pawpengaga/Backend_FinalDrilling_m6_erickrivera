package com.pawpengaga.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.model.Materia;
import com.pawpengaga.backend.service.MateriaService;

@RestController
@RequestMapping("/api/v1/materias")
public class MateriaController {

  private static final Logger myLogger = LoggerFactory.getLogger(AlumnoController.class);

  @Autowired
  MateriaService materiaService;

  /* ************************************************** */


  @PostMapping("/grabar")
  public ResponseEntity<String> grabarMaterias(@RequestBody Materia materia){
    try {
      Materia materiaMostrar = materiaService.guardarMateria(materia);
      myLogger.info("Materia guardada!: {}", materia);
      return ResponseEntity.ok("Materia guardada:\n" + materiaMostrar);
    } catch (Exception e) {
      myLogger.error("Ocurrió un error a nivel de controlador REST al guardar la materia", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
