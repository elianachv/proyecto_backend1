# Proyecto Integrador Backend I
**Estudiante:** Eliana Chávez

## 1. Descripción
Sistema de agendamiento de citas de odontología, con las siguientes funcionalidades:

* **Odontologos**
  * Listar odontologos registrados
  * Buscar un odontologo especifico por su matricula
  * Buscar un odontologo especifico por su id
  * Crear un odontologo en el sistema
  * Modificar un odontologo del sistema
  * Eliminar un odontologo del sistema
  
* **Pacientes**
  * Listar pacientes registrados
  * Buscar un paciente especifico por su dni
  * Buscar un paciente especifico por su id
  * Buscar un paciente especifico por su nombre y apellido
  * Crear un paciente en el sistema
  * Modificar un paciente del sistema
  * Eliminar un paciente del sistema

* **Turnos**
    * Listar todos los turnos agendados
    * Listar todos los turnos de un paciente
    * Listar todos los turnos de un odontologo
    * Listar todos los turnos de una fecha
    * Buscar un turno especifico por su id
    * Crear un turno en el sistema
    * Modificar un turno del sistema
    * Eliminar un turno del sistema

## 2. Alcance entrega n° 2
Se deben entregar las funcionalidades correspondientes al dominio de odontologo, paciente y turnos.

El proyecto debe incluir los logs y las pruebas unitarias.

## 3. Tecnologías
Se implementaron las siguientes tecnologías:
* Maven
* Java
* H2
* Log4j2
* JUnit

## 4. Observaciones
* Para la correcta configuración del proyecto maven se utilizó como guía el material del playground e información util de la siguiente página https://www.baeldung.com/maven-cant-find-junit-tests 
* Se logra configuración de logs para ambiente "productivo" y para ambiente de pruebas creando dos archivos log4j2.properties 
* Se adiciona apidocs (documentación del api) en la carpeta resources
* Link de github: https://github.com/elianachv/proyecto_backend1