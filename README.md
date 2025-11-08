Link parte visual 
https://github.com/RLiteM/pages


# Proyecto de Sistema de Inventario

Este es un proyecto de API REST para un sistema de inventario simple, desarrollado con Spring Boot.

## Cómo Levantar la API

Sigue estos pasos para ejecutar la aplicación en tu entorno local.

### Prerrequisitos

*   **Java 21** o una versión superior.
*   **Maven** para la gestión de dependencias y construcción del proyecto.
*   Una base de datos **MySQL** en ejecución.

### Configuración

1.  **Base de Datos:** Asegúrate de que tu base de datos MySQL esté activa. Ejecuta el script que se encuentra más abajo en la sección "Script de la Base de Datos (MySQL)" para crear la estructura necesaria.

2.  **Credenciales:** Abre el archivo `src/main/resources/application.properties` y ajusta las siguientes líneas con tus credenciales de MySQL si son diferentes a las de por defecto:
    ```properties
    spring.datasource.username=root
    spring.datasource.password=Guayaba426
    ```

### Ejecución

1.  **Construir el Proyecto (Opcional pero recomendado):**
    Abre una terminal en la raíz del proyecto y ejecuta el siguiente comando para compilar y asegurarte de que todo está correcto:
    ```shell
    ./mvnw clean install
    ```

2.  **Levantar la API:**
    Usa el siguiente comando para iniciar la aplicación:
    ```shell
    ./mvnw spring-boot:run
    ```

    Una vez ejecutado, la API estará disponible en `http://localhost:8080`.

## Script de la Base de Datos (MySQL)

Ejecuta el siguiente script en tu cliente de MySQL para crear la base de datos y las tablas necesarias.

```sql
-- crear BD
CREATE DATABASE IF NOT EXISTS inventariofinal
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE inventariofinal;

-- proveedor
CREATE TABLE proveedor(
    proveedor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(120) NOT NULL,
    pais_origen   VARCHAR(80)
);

-- producto
CREATE TABLE producto(
    producto_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(160) NOT NULL,
    sku           VARCHAR(60) UNIQUE NOT NULL,
    proveedor_id  BIGINT NOT NULL,
    stock_actual  DECIMAL(14,2) DEFAULT 0,
    CONSTRAINT fk_producto_proveedor FOREIGN KEY (proveedor_id)
        REFERENCES proveedor(proveedor_id)
);

-- movimiento inventario
CREATE TABLE movimiento_inventario(
    movimiento_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    tipo CHAR(1) NOT NULL, -- 'E' entrada, 'S' salida
    cantidad DECIMAL(14,2) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    referencia VARCHAR(80),
    CONSTRAINT fk_mov_prod FOREIGN KEY (producto_id)
        REFERENCES producto(producto_id)
);
```

## Endpoints Principales de la API

*   **Proveedores:** `GET, POST, PUT, DELETE /api/proveedores`
*   **Productos:** `GET, POST, PUT, DELETE /api/productos`
*   **Movimientos de Inventario:**
    *   `POST /api/inventario/movimientos` (Para registrar entradas/salidas)
    *   `GET /api/productos/{id}/movimientos` (Para ver el historial de un producto)

---
### Documentación de Referencia de Spring

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/maven-plugin)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.7/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.7/reference/data/sql.html#data.sql.jpa-and-spring-data)
