package com.pawpengaga.backend.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.model.Materia;
import com.pawpengaga.backend.repository.AlumnoRepository;
import com.pawpengaga.backend.repository.MateriaRepository;

@Component
public class DataLoader implements CommandLineRunner {

  
  @Autowired
  AlumnoRepository alumnoRepo;

  @Autowired
  MateriaRepository materiaRepo;

  // Hacer una seed aqui
  @Override
  public void run(String... args) throws Exception {

    /*
    */
    
    System.out.println("???????????????????????????????????????????????????????????????????????????");
    
    System.out.println("DEBUGGER PLAYGROUND AND SEED");
    
    /* ********** ACCION 1: Listar alumnos si existieran ********** */

    if (!alumnoRepo.findAll().isEmpty()) {
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
    
      for (Object alumno : alumnos) {
        System.out.println(alumno);
      }
    }

    /* ********** ACCION 2: Cargar una lista de materias si no existieran ********** */

    if(materiaRepo.findAll().isEmpty()){
      
      String[] materiasLista = {
        "Matematicas",
        "Historia",
        "Ciencias",
        "Educacion Civica",
        "Educacion Fisica",
        "Quimica",
        "Ingles"
      };

      for (String materia : materiasLista) {
        System.out.println("Guardando materia: " + materia);
        materiaRepo.save(new Materia(null, materia, null));
      }

    }


    
    System.out.println("???????????????????????????????????????????????????????????????????????????");
    
  }
  
}
