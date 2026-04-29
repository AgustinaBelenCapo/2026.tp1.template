# BiblioTech - Trabajo Practico 1

Sistema base para gestion de biblioteca en Java, con arquitectura por capas 

## Estructura

src/main/java/com/bibliotech/
├── model: contiene las entidades del dominio
├── repository: acceso y gestión de datos en memoria
├── service: lógica de negocio
├── exception: maneja errores personalizados
└── Main.java: funciona como punto de entrada del sistema

Se dividió el proyecto en paquetes según su función para mantener una estructura clara y evitar acoplamiento entre componentes.

### Interfaces
Se utilizaron interfaces en servicios y repositorios para desacoplar implementaciones y permitir futuras extensiones sin modificar la lógica principal.

#### Manejo de Errores
Se implementaron excepciones personalizadas para controlar errores específicos del negocio, por ejemplo:

recurso no disponible
socio no encontrado
límite de préstamos excedido
operaciones inválidas

Esta decisión mejora el manejo de errores, hace el código más claro y separa los errores del negocio de los errores generales del sistema.


##### Modelado del dominio

Se definieron las principales entidades del sistema:

Recursos

    Recurso
    LibroFisico
    EBook
    CategoriaRecurso

Socios

    Socio
    SocioEstudiante
    SocioDocente
    TipoSocio

Operaciones

    Prestamo

Se utilizó abstracción e implementación por tipos para representar distintos comportamientos según el dominio.

###### Uso de Opcional 
Se utilizó Opcional en los métodos de búsqueda  para evitar el uso de null y reducir errores.
