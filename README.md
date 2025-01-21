# 🌸 Drilling final módulo 6 - Backend 🌸

Una API REST protegida por Spring Security y JWT

## Puertos

### Backend 🌸

```
localhost:3000
```

### Frontend 🌼

```
localhost:3001
```

## Instrucciones para ejecución

Crear base de datos
```sql
CREATE DATABASE db_final_drilling_m6;
```

Ejecutar el proyecto de manera normal


## Etapas del proyecto 🀄

- Crear los endopoints de la API `<- LISTO`
- Protegerlos con security `<- LISTO`
- Aplicar JWT `<- LISTO`

## Documentación de la api 🧭

### Listar alumnos `GET`

Requiere token en los headers para funcionar

```
localhost:3000/api/v1/alumnos
```

### Crear alumnos `POST`

Requiere token en los headers para funcionar

```
localhost:3000/api/v1/alumnos/grabar
```

Ejemplo de solicitud

```json
{
  "rut": "19.492.730-4",
  "nombre": "Erick Rivera",
  "direccion": "Arica, Chile",
  "materiaList": [
    {
      "id": 1
    },
    {
      "id": 2
    },
    {
      "id": 3
    }
  ]
}
```

### Obtener materias `GET`

Requiere token en los headers para funcionar


```
localhost:3000/api/v1/materias
```
### Crear materias `POST`

Requiere token en los headers para funcionar


```
localhost:3000/api/v1/materias/grabar
```

Ejemplo de solicitud

```json
{
  "nombre": "Materia de ana banana"
}
```

### Iniciar sesión `POST`

No requiere token


```
localhost:3000/api/v1/auth/log-in
```

Ejemplo de solicitud

```json
{
  "username": "anabanana@mail.com",
  "password": "12345678"
}
```


## Seed 🌱

Al iniciar el proyecto por primera vez, `DataLoader` cargará las siguientes materias *si no detecta ninguna*:

- Matemáticas
- Historia
- Ciencias
- Educacion Civica
- Educacion Fisica
- Quimica
- Ingles

> Adicionalmente listará todos los alumnos si llegaran a existir

### Usuarios pre cargados

**Un administrador** (READ, CREATE, UPDATE, DELETE) <br>
Puede acceder a las 


Correo: `anabanana@mail.com` <br>
Clave: `12345678`

**Un usuario regular** (READ, CREATE, UPDATE)

Correo: `mariasandia@mail.com` <br>
Clave: `12345678`

**Un usuario invitado** (READ)

Correo: `mariasandia@mail.com` <br>
Clave: `12345678`

🌼 Enlace al frontend 🌼