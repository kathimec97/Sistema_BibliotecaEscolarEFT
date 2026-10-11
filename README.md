![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
# Caso: Sistema de Gestión de Biblioteca Escolar - Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto
* **Nombre Completo:** Katherine del Carmen Avila Mecia
* **Sección:** 003A
* **Carrera:** Analista Programador Computacional
* **Bimestre:** 3
* **Sede:** Campus Virtual

---
## 📘 Evaluación Final Transversal (Semana 9)

## Descripción del Proyecto
Sistema desarrollado en Java Swing para gestionar la circulación de materiales en una biblioteca escolar. Permite controlar el registro, préstamo y devolución de libros para estudiantes, así como la gestión de inventario y usuarios mediante una base de datos MySQL.

Este proyecto aplica el patrón de arquitectura Modelo-Vista-Controlador (MVC) junto con el patrón DAO, asegurando una separación limpia entre la interfaz gráfica, la lógica de negocio y la persistencia de datos. Además, integra principios de concurrencia para procesos críticos.

### Clases principales

* **Punto de Entrada:**
    * `Main`: Inicia la aplicación (debe instanciar y llamar a la `VistaLogin`).

* **Modelo (Entidades):**
    * `Persona`: Clase abstracta base.
    * `Usuario` y `Estudiante`: Heredan de Persona. Representan los actores del sistema.
    * `Libro` y `Categoria`: Representan el inventario físico y su clasificación.
    * `Prestamo`: Modelo transaccional que vincula un `Libro` con un `Estudiante`, controlando fechas y estados de devolución.
    * `Rol`: Enum para definir los niveles de acceso (ESTUDIANTE, BIBLIOTECARIO).

* **Controlador (Lógica de Negocio):**
    * `LoginControlador`: Valida credenciales y redirige según el rol.
    * `LibroControlador`, `EstudianteControlador`, `PrestamoControlador`, `ReportesControlador`: Validan reglas de negocio y actúan como intermediarios entre la Vista y la capa DAO.

* **Acceso a Datos (DAO):**
    * `DatabaseConnection`: Configura la conexión a la base de datos `biblioteca` utilizando el patrón **Singleton**.
    * `UsuarioDAOImpl`, `EstudianteDAOImpl`, `LibroDAOImpl`, `CategoriaDAOImpl`, `PrestamoDAOImpl`: Centralizan las sentencias SQL (CRUD) implementando sus respectivas interfaces.

* **Vista (Interfaz Gráfica):**
    * `VistaLogin`: Pantalla de autenticación.
    * `VistaPrincipal`: Panel contenedor principal para el rol Bibliotecario.
    * `VistaEstudiante`: Portal exclusivo para los alumnos (catálogo y mis préstamos).
    * `VistaLibros`, `VistaRegistroEstudiante`, `VistaPrestamos`, `VistaReportes`: Pantallas modulares (JPanels) con las funcionalidades CRUD y visualización de datos.

### Características destacadas
* **Autenticación por Roles**: El sistema distingue entre Bibliotecarios (gestión total) y Estudiantes (solo consulta y préstamos).
* **Concurrencia y Sincronización**: El registro de préstamos se ejecuta en un **hilo independiente** (`Thread`) para evitar el bloqueo de la interfaz (UI), y utiliza métodos `synchronized` para evitar condiciones de carrera al actualizar el stock de los libros.
* **Persistencia Segura**: Uso estricto de `PreparedStatement` en todas las transacciones SQL.
* **Reportes Dinámicos**: Generación de informes en tiempo real (Libros más prestados, libros activos, historial por alumno).

## Tecnologías utilizadas
* **Lenguaje:** Java
* **Interfaz Gráfica:** Java Swing (JFrame, JPanel, CardLayout, GridLayoutManager)
* **Base de Datos:** MySQL
* **Conexión:** JDBC (Java Database Connectivity)

---
## Instrucciones de Ejecución
1. Descarga o clona el repositorio y abre el proyecto en tu IDE (IntelliJ IDEA).
2. Abre MySQL Workbench y ejecuta los scripts SQL entregados (`Script_crea_tablas_biblioteca.sql` y `Script_poblado_tablas_biblioteca.sql`) para crear y poblar la base de datos `biblioteca`.
3. Abre el archivo `cl.duoc.util.DatabaseConnection.java` y verifica que las credenciales (USER, PASSWORD) coincidan con tu servidor local.
4. Asegúrate de que el conector de MySQL (MySQL Connector/J) esté agregado a las dependencias en tu archivo `pom.xml`.
5. Ejecuta la clase `Main.java` para iniciar la ventana de Login.

## 📁 Estructura del Proyecto

```plaintext
SistemaBiblioteca
├── src
│   ├── main
│   │   ├── java
│   │   │   └── cl
│   │   │       └── duoc
│   │   │           ├── controlador
│   │   │           │   ├── EstudianteControlador.java
│   │   │           │   ├── LibroControlador.java
│   │   │           │   ├── LoginControlador.java
│   │   │           │   ├── PrestamoControlador.java
│   │   │           │   └── ReportesControlador.java
│   │   │           ├── dao
│   │   │           │   ├── impl
│   │   │           │   │   ├── CategoriaDAOImpl.java
│   │   │           │   │   ├── EstudianteDAOImpl.java
│   │   │           │   │   ├── LibroDAOImpl.java
│   │   │           │   │   ├── PrestamoDAOImpl.java
│   │   │           │   │   └── UsuarioDAOImpl.java
│   │   │           │   ├── CategoriaDAO.java
│   │   │           │   ├── EstudianteDAO.java
│   │   │           │   ├── LibroDAO.java
│   │   │           │   ├── PrestamoDAO.java
│   │   │           │   └── UsuarioDAO.java
│   │   │           ├── modelo
│   │   │           │   ├── Categoria.java
│   │   │           │   ├── Estudiante.java
│   │   │           │   ├── Libro.java
│   │   │           │   ├── Persona.java
│   │   │           │   ├── Prestamo.java
│   │   │           │   ├── Rol.java
│   │   │           │   └── Usuario.java
│   │   │           ├── util
│   │   │           │   └── DatabaseConnection.java
│   │   │           └── vista
│   │   │               ├── VistaEstudiante.form / .java
│   │   │               ├── VistaLibros.form / .java
│   │   │               ├── VistaLogin.form / .java
│   │   │               ├── VistaPrestamos.form / .java
│   │   │               ├── VistaPrincipal.form / .java
│   │   │               ├── VistaRegistroEstudiante.form / .java
│   │   │               └── VistaReportes.form / .java
│   │   │           └── Main.java
├── pom.xml
└── README.md