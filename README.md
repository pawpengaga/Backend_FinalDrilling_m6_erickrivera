# 🌸 Drilling final módulo 6 - Backend 🌸

Una API REST protegida por Spring Security y JWT

## Instrucciones para ejecución

Crear base de datos
```sql
CREATE DATABASE db_final_drilling_m6;
```

Ejecutar el proyecto de manera normal


## Etapas del proyecto

- Crear los endopoints de la API `<- LISTO`
- Protegerlos con security
- Aplicar JWT

## Documentación de la api 🧭

Listar alumnos `GET`

```
localhost:3000/api/v1/alumnos
```

Crear alumnos `POST`

```
localhost:3000/api/v1/alumnos/grabar
```

Crear materias `POST`

```
localhost:3000/api/v1/materias/grabar
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

Adicionalmente listará todos los alumnos si llegaran a existir

## Ideas para el proyecto

- Crear validación personalizada para el RUT

Enlace al frontend 🌼