package com.pawpengaga.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pawpengaga.backend.model.Materia;
import com.pawpengaga.backend.repository.AlumnoRepository;
import com.pawpengaga.backend.repository.MateriaRepository;

@Service
public class MateriaService {
  
  private static final Logger myLogger = LoggerFactory.getLogger(MateriaService.class);

  @Autowired
  AlumnoRepository alumnoRepo;

  @Autowired
  MateriaRepository materiaRepo;

  /* ************************************************* */
  // Anotación: Uso experimental de los logs

  public Materia guardarMateria(Materia materia){
    try {
      Materia materiaGuardada = materiaRepo.save(materia);
      myLogger.info("Materia guardada exitosamente!!: {}", materiaGuardada);
      return materiaGuardada;
    } catch (Exception e){
      myLogger.error("Hubo un error al guardar la materia... {}", e);
      return null;
    }
  }

}
