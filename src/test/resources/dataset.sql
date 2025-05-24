BEGIN;

INSERT INTO usuario (esta_activo, cedula, img_url, nombre, password, username)  VALUES
(true, 1002656555, NULL, 'Juan Esteban Parra Parra', 'parra', 'parra'),
(true, 0000, NULL, 'Finn el Humano', 'finn', 'finn'),
(true, 1111, NULL, 'Miguel Florez', 'admin', 'admin'),
(true, 2222, NULL, 'Jake el Cocinero', 'cocinero', 'cocinero');

INSERT INTO cocinero (cedula) VALUES
(2222);

INSERT INTO mesero (en_turno, cedula) VALUES
(true, 0000),
(true, 1002656555);


INSERT INTO admin (cedula) VALUES
(1111);

-- Unidad de Medida (Measurement Units)
INSERT INTO unidad_medida (notacion, nombre) VALUES
('g', 'Gramos'),
('kg', 'Kilogramos'),
('ml', 'Mililitros'),
('l', 'Litros'),
('oz', 'Onzas'),
('lb', 'Libras'),
('und', 'Unidades'),
('cdta', 'Cucharadita'),
('cda', 'Cucharada');

-- Tipo Plato (Dish Types) with hierarchical structure
INSERT INTO tipo_plato (nombre, descripcion) VALUES
('Entradas', 'Platos para iniciar la comida'),
('Platos Fuertes', 'Platos principales de la carta'),
('Postres', 'Opciones dulces para finalizar'),
('Bebidas', 'Refrescos y bebidas variadas'),
('Sopas', 'Caldos y cremas');

-- Add some sub-types
INSERT INTO tipo_plato (nombre, descripcion, tipo_padre) VALUES
('Carnes', 'Platos a base de carnes rojas', 2),
('Mariscos', 'Especialidades del mar', 2),
('Pastas', 'Platos a base de pasta', 2),
('Ensaladas', 'Opciones frescas y saludables', 1);

-- Ingredientes (Ingredients)
INSERT INTO ingrediente (nombre, cantidad_disponible, precio_compra, unidad_medida, marca) VALUES
('Carne de res', 20000, 25000.00, 'kg', 'Carnes Premium'),
('Pollo', 30000, 12000.00, 'kg', 'Avícola del Valle'),
('Camarones', 5000, 40000.00, 'kg', 'Del Mar'),
('Langostinos', 3000, 60000.00, 'kg', 'Del Mar'),
('Arroz', 50000, 5000.00, 'kg', 'Diana'),
('Pasta', 15000, 4500.00, 'kg', 'Doria'),
('Queso parmesano', 3000, 30000.00, 'kg', 'Alpina'),
('Crema de leche', 10000, 8000.00, 'l', 'Colanta'),
('Tomate', 8000, 3500.00, 'kg', 'Huerta Fresca'),
('Cebolla', 6000, 2800.00, 'kg', 'Huerta Fresca'),
('Ajo', 2000, 15000.00, 'kg', 'Huerta Fresca'),
('Pimienta negra', 1000, 25000.00, 'kg', 'Condimentos La Especial'),
('Sal marina', 3000, 4000.00, 'kg', 'Refisal'),
('Aceite de oliva', 5000, 35000.00, 'l', 'Olivetto'),
('Mantequilla', 5000, 18000.00, 'kg', 'Colanta'),
('Chocolate negro', 4000, 28000.00, 'kg', 'Nacional'),
('Azúcar', 15000, 3500.00, 'kg', 'Manuelita'),
('Harina de trigo', 20000, 3800.00, 'kg', 'Haz de Oros'),
('Huevos', 5000, 15000.00, 'und', 'La Granja'),
('Leche entera', 15000, 3200.00, 'l', 'Alpina'),
('Limón', 3000, 4800.00, 'kg', 'Huerta Fresca');

-- Platos (Dishes)
INSERT INTO plato (nombre, descripcion, precio, id_tipo_plato, activo) VALUES
('Carpaccio de res', 'Finas láminas de res con aliño especial', 28000, 1, true),
('Bruschetta', 'Pan artesanal con tomate, albahaca y aceite de oliva', 18000, 1, true),
('Lomo a la pimienta', 'Corte fino de lomo bañado en salsa de pimienta', 45000, 6, true),
('Filete de res en salsa de vino', 'Filete de res preparado con reducción de vino tinto', 48000, 6, true),
('Pollo a la parmesana', 'Pechuga de pollo empanizada con queso parmesano', 35000, 2, true),
('Camarones al ajillo', 'Camarones salteados en mantequilla de ajo', 42000, 7, true),
('Paella marina', 'Arroz con mariscos y azafrán', 55000, 7, true),
('Pasta Alfredo', 'Fettuccine en salsa cremosa', 32000, 8, true),
('Pasta Napolitana', 'Pasta con salsa de tomate y albahaca', 30000, 8, true),
('Ensalada César', 'Lechuga romana, pollo, queso y aderezo César', 25000, 9, true),
('Tiramisú', 'Postre italiano de café y queso mascarpone', 18000, 3, true),
('Mousse de chocolate', 'Postre aireado de chocolate', 15000, 3, true),
('Limonada natural', 'Zumo de limón con agua y azúcar', 8000, 4, true),
('Sopa de tomate', 'Crema de tomate con crutones', 16000, 5, true),
('Crema de champiñones', 'Delicada crema con champiñones', 18000, 5, true);

-- Ingredientes por Plato
INSERT INTO ingrediente_plato (id_plato, id_ingrediente, cantidad, id_unidad_medida) VALUES
-- Carpaccio de res
(1, 1, 150, 'g'),
(1, 14, 15, 'ml'),
(1, 12, 2, 'g'),
(1, 13, 1, 'g'),

-- Bruschetta
(2, 9, 100, 'g'),
(2, 14, 20, 'ml'),
(2, 13, 1, 'g'),

-- Lomo a la pimienta
(3, 1, 250, 'g'),
(3, 12, 10, 'g'),
(3, 15, 20, 'g'),
(3, 8, 50, 'ml'),

-- Filete de res en salsa de vino
(4, 1, 280, 'g'),
(4, 15, 30, 'g'),
(4, 11, 10, 'g'),

-- Pollo a la parmesana
(5, 2, 200, 'g'),
(5, 7, 50, 'g'),
(5, 18, 30, 'g'),
(5, 19, 1, 'und'),

-- Camarones al ajillo
(6, 3, 180, 'g'),
(6, 11, 15, 'g'),
(6, 15, 30, 'g'),
(6, 14, 15, 'ml'),

-- Paella marina
(7, 3, 100, 'g'),
(7, 4, 100, 'g'),
(7, 5, 200, 'g'),
(7, 9, 50, 'g'),
(7, 10, 50, 'g'),

-- Pasta Alfredo
(8, 6, 180, 'g'),
(8, 8, 100, 'ml'),
(8, 7, 40, 'g'),
(8, 15, 30, 'g'),

-- Pasta Napolitana
(9, 6, 180, 'g'),
(9, 9, 150, 'g'),
(9, 11, 10, 'g'),
(9, 14, 20, 'ml'),

-- Ensalada César
(10, 2, 100, 'g'),
(10, 7, 30, 'g'),
(10, 14, 20, 'ml'),

-- Tiramisú
(11, 20, 200, 'ml'),
(11, 17, 50, 'g'),
(11, 19, 3, 'und'),

-- Mousse de chocolate
(12, 16, 100, 'g'),
(12, 19, 2, 'und'),
(12, 20, 150, 'ml'),
(12, 17, 40, 'g'),

-- Limonada natural
(13, 21, 150, 'g'),
(13, 17, 30, 'g'),

-- Sopa de tomate
(14, 9, 300, 'g'),
(14, 10, 50, 'g'),
(14, 11, 5, 'g'),
(14, 8, 50, 'ml'),

-- Crema de champiñones
(15, 8, 150, 'ml'),
(15, 15, 30, 'g'),
(15, 13, 2, 'g');

-- Usuarios (skipping the two existing users with cedulas 1002656555 and 1111)
INSERT INTO usuario (cedula, nombre, username, password, esta_activo, img_url) VALUES
('1001234567', 'Carlos Mendoza', 'carlos_m', '$2a$10$hPvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/men/22.jpg'),
('1002345678', 'Ana María López', 'ana_lopez', '$2a$10$hNvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/women/45.jpg'),
('1003456789', 'Juan Pablo Restrepo', 'juan_chef', '$2a$10$jPvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/men/32.jpg'),
('1004567890', 'Luisa Fernanda Giraldo', 'luisa_chef', '$2a$10$lPvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/women/28.jpg'),
('1005678901', 'Diego Alejandro Ospina', 'diego_mesero', '$2a$10$dPvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/men/56.jpg'),
('1006789012', 'Valentina Herrera', 'vale_mesera', '$2a$10$vPvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/women/62.jpg'),
('1007890123', 'Santiago Ramírez', 'santi_mesero', '$2a$10$sPvnWnT5tB1X9RJHBHnIZu8zHV6.QD13plm4kJA1.CSjnSZPgEcXa', true, 'https://randomuser.me/api/portraits/men/71.jpg');

-- Asignación de roles a usuarios
INSERT INTO admin (cedula) VALUES ('1001234567'), ('1002345678');
INSERT INTO cocinero (cedula) VALUES ('1003456789'), ('1004567890');
INSERT INTO mesero (cedula, en_turno) VALUES
('1005678901', true),
('1006789012', true),
('1007890123', false);

-- Mesas
INSERT INTO mesa (disponible) VALUES
(true),
(true),
(true),
(true),
(true),
(true),
(false),
(false),
(true),
(true);

-- Órdenes
INSERT INTO orden (fecha_inicio, fecha_cierre, subtotal, impuestos, total, id_mesa, cedula_mesero, estado) VALUES
-- Órdenes activas
('2024-07-20 12:30:00', NULL, 85000.00, 16150.00, 101150.00, 7, '1005678901', 'PROCESO'),
('2024-07-20 13:15:00', NULL, 73000.00, 13870.00, 86870.00, 8, '1006789012', 'ESPERA'),

-- Órdenes finalizadas
('2024-07-19 19:30:00', '2024-07-19 21:15:00', 110000.00, 20900.00, 130900.00, 3, '1005678901', 'FINALIZADA'),
('2024-07-19 20:45:00', '2024-07-19 22:20:00', 78000.00, 14820.00, 92820.00, 5, '1006789012', 'FINALIZADA'),
('2024-07-18 13:30:00', '2024-07-18 14:45:00', 48000.00, 9120.00, 57120.00, 1, '1007890123', 'FINALIZADA'),

-- Orden cancelada
('2024-07-20 11:45:00', '2024-07-20 12:10:00', 28000.00, 5320.00, 33320.00, 4, '1007890123', 'CANCELADA');

-- Detalle de órdenes (orden_plato)
INSERT INTO orden_plato (id_orden, id_plato, cantidad) VALUES
-- Orden 1 (en proceso)
(1, 3, 1),  -- Lomo a la pimienta
(1, 6, 1),  -- Camarones al ajillo

-- Orden 2 (en espera)
(2, 10, 1), -- Ensalada César
(2, 4, 1),  -- Filete en salsa de vino

-- Orden 3 (finalizada)
(3, 7, 2),  -- Paella marina

-- Orden 4 (finalizada)
(4, 5, 1),  -- Pollo a la parmesana
(4, 8, 1),  -- Pasta Alfredo
(4, 13, 2), -- Limonadas

-- Orden 5 (finalizada)
(5, 14, 1), -- Sopa de tomate
(5, 10, 1), -- Ensalada César

-- Orden 6 (cancelada)
(6, 1, 1);  -- Carpaccio de res

COMMIT;
