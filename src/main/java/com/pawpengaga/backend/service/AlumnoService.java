package com.pawpengaga.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.repository.AlumnoRepository;

@Service
public class AlumnoService {

  private static final Logger myLogger = LoggerFactory.getLogger(AlumnoService.class);

  @Autowired
  AlumnoRepository alumnoRepo;

  /* ************************************************* */
  // Anotación: Uso experimental de los logs

  public Alumno guardarAlumno(Alumno alumno){
    try {
      Alumno alumnoGuardado = alumnoRepo.save(alumno);
      myLogger.info("Alumno guardado exitosamente!!: {}", alumnoGuardado);
      return alumnoGuardado;
    } catch (Exception e) {
      myLogger.error("Hubo un error al guardar el alumno... {}", e);
      return null;
    }
  }

  public List<Alumno> listarAlumnos(){
    try {
      List<Alumno> listaAlumnos = alumnoRepo.findAll();
      myLogger.info("Lista de alumnos devuelta!: {}", listaAlumnos);
      return listaAlumnos;
    } catch (Exception e){
      myLogger.error("Hubo un error al cargar la lista de alumnos...", e);
      return null;
    }
  }



}
