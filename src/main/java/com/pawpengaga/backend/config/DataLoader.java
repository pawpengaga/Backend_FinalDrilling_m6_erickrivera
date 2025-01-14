package com.pawpengaga.backend.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pawpengaga.backend.model.Alumno;
import com.pawpengaga.backend.model.Materia;
import com.pawpengaga.backend.model.Permiso;
import com.pawpengaga.backend.model.Role;
import com.pawpengaga.backend.model.RoleEnum;
import com.pawpengaga.backend.model.Usuario;
import com.pawpengaga.backend.repository.AlumnoRepository;
import com.pawpengaga.backend.repository.MateriaRepository;
import com.pawpengaga.backend.repository.UsuarioRepository;

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
    
  }

  @Bean
  CommandLineRunner ini(UsuarioRepository userRepo){

    if (userRepo.findAll().isEmpty()){
      return args -> {
  
        System.out.println("###########################################################################");
        System.out.println("GENERADOR DE USUARIOS");
    
        String claveEncriptada = new BCryptPasswordEncoder().encode("12345678");    
    
        Permiso leer = Permiso.builder().name("READ").build();
        Permiso crear = Permiso.builder().name("CREATE").build();
        Permiso actualizar = Permiso.builder().name("UPDATE").build();
        Permiso eliminar = Permiso.builder().name("DELETE").build();
  
        Role admin = Role.builder()
                     .role(RoleEnum.ADMIN)
                     .permisos(Set.of(leer, crear, actualizar, eliminar))
                     .build();
  
        Role client = Role.builder()
                     .role(RoleEnum.CLIENT)
                     .permisos(Set.of(leer, crear))
                     .build();
  
        Usuario admistrador = Usuario.builder()
          .nombre("Ana Banana")
          .correo("anabanana@mail.com")
          .clave(claveEncriptada)
          .roles(Set.of(admin, client))
          .isEnabled(true)
          .accountNoExpired(true)
          .accountNoLocked(true)
          .credentialNoExpired(true)
          .build();
  
        Usuario cliente = Usuario.builder()
          .nombre("Maria Sandia")
          .correo("mariasandia@mail.com")
          .clave(claveEncriptada)
          .roles(Set.of(client))
          .isEnabled(true)
          .accountNoExpired(true)
          .accountNoLocked(true)
          .credentialNoExpired(true)
          .build();
  
        userRepo.saveAll(List.of(admistrador, cliente));
        
        System.out.println("###########################################################################");
  
      };
    } else {
      return args -> {
        System.out.println("###########################################################################");
        System.out.println("LOS USUARIOS NECESARIOS YA FUERON PRE CARGADOS");
        System.out.println("###########################################################################");
      };
    }
    
  }
  
}
