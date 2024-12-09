# **Proyecto Spring Boot 3.4.0 con Java 21 y Maven**

## **Descripción**
Este proyecto es un API construida con **Spring Boot 3.4.0**, **Java 21**, y **Maven**. 

El objetivo de este proyecto es crear una API de carrito de compras utilizando **Java Spring Boot**. La API permitirá gestionar el proceso de compra mediante operaciones como:

- **Agregar productos** al carrito.
- **Actualizar cantidades** de productos seleccionados.
- **Eliminar productos** del carrito.
- **Obtener productos asociados** al carrito.

El proyecto incluye el diseño de la base de datos y el desarrollo del backend para esta funcionalidad principal.

---

## **Requisitos Previos**
Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 21** ([Descargar Java](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html))
- **Maven 3.9+** ([Descargar Maven](https://maven.apache.org/download.cgi))
- **Git** (opcional)

---

## **Instalación y Configuración**

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/VasquezNodier/BackendCart.git
   cd BackendCart
    
2. **Configura el archivo `application.properties` o `application.yml`:**
    
    *   Ubicado en `src/main/resources/`
        
    *   Configura la conexión a la base de datos, el puerto del servidor y otras propiedades según sea necesario.
        
4.  `mvn clean install`
    
5.  `mvn spring-boot:run`

6.  Access to API doc from `http://localhost:8080/swagger-ui/index.html`

**Estructura del Proyecto**
---------------------------

```  
/src
   /main
      /java/com/shopper/cart
         /configuration
         /controller
         /dto
         /model
         /repository
         /service
            /impl
      /resources
         application.properties 
         BackendShoppingCart.sql    --> Script SQL
         RelationalModelSQL.png     --> Database Diagram         
   ```

    

**Tecnologías Utilizadas**
--------------------------

*   **Spring Boot 3.4.0**
    
*   **Java 21**
    
*   **Maven**
    
*   **Spring Data JPA**
    
*   **Swagger/OpenAPI** 
    
*   **Spring Security** (opcional)
    
*   **PostgreSQL** (configurable)
    


**Contacto**
------------


*   **Autor:** Nodier Vasquez
    
*   **Correo Electrónico:** vasqueznap@gmail.com
    
*   **Repositorio:** [GitHub - BackendCart](https://github.com/VasquezNodier/BackendCart)
    
