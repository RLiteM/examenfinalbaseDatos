Scrypt
usar MYSQL

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
