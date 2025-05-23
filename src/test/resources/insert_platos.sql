-- First, insert measurement units
INSERT INTO unidad_medida (notacion, nombre) VALUES
('g', 'Gramos'),
('kg', 'Kilogramos'),
('ml', 'Mililitros'),
('ud', 'Unidades'),
('cdta', 'Cucharadita');

-- Insert plate types
INSERT INTO tipo_plato (nombre, descripcion) VALUES
('Entrada', 'Platos para iniciar la comida'),
('Plato fuerte', 'Platos principales'),
('Postre', 'Platos dulces para finalizar la comida'),
('Bebida', 'Bebidas y refrescos');

-- Insert ingredients
INSERT INTO ingrediente (nombre, cantidad_disponible, precio_compra, unidad_medida, marca) VALUES
('Carne de res', 10000, 15000.00, 'g', 'Premium Carnes'),
('Pollo', 15000, 12000.00, 'g', 'Avícola del Valle'),
('Arroz', 25000, 3500.00, 'g', 'Diana'),
('Papa', 20000, 2500.00, 'g', 'Campo Limpio'),
('Cebolla', 5000, 2000.00, 'g', 'Hortalizas Frescas'),
('Tomate', 8000, 3000.00, 'g', 'Hortalizas Frescas'),
('Queso mozzarella', 3000, 18000.00, 'g', 'Alpina'),
('Camarones', 3000, 35000.00, 'g', 'Mariscos del Pacífico'),
('Pasta', 8000, 4500.00, 'g', 'Doria'),
('Chocolate', 5000, 15000.00, 'g', 'Nacional'),
('Leche', 10000, 3000.00, 'ml', 'Alpina'),
('Azúcar', 10000, 2500.00, 'g', 'Manuelita');

-- Insert plates
INSERT INTO plato (nombre, descripcion, precio, id_tipo_plato, activo) VALUES
('Lomo de res a la parrilla', 'Jugoso lomo de res asado a la parrilla', 32000.00, 2, true),
('Pollo en salsa de champiñones', 'Pollo en salsa cremosa con champiñones', 28000.00, 2, true),
('Pasta Alfredo', 'Pasta con salsa cremosa y queso parmesano', 25000.00, 2, true),
('Mousse de chocolate', 'Postre cremoso de chocolate con base de galleta', 15000.00, 3, true),
('Camarones al ajillo', 'Camarones salteados con ajo y limón', 22000.00, 1, true);

-- Create relationships between plates and ingredients
-- Lomo de res a la parrilla
INSERT INTO ingrediente_plato (id_plato, id_ingrediente, cantidad, id_unidad_medida) VALUES
(1, 1, 300, 'g'),    -- Carne de res
(1, 4, 200, 'g'),    -- Papa
(1, 5, 50, 'g');     -- Cebolla

-- Pollo en salsa de champiñones
INSERT INTO ingrediente_plato (id_plato, id_ingrediente, cantidad, id_unidad_medida) VALUES
(2, 2, 250, 'g'),    -- Pollo
(2, 3, 150, 'g'),    -- Arroz
(2, 11, 100, 'ml');  -- Leche

-- Pasta Alfredo
INSERT INTO ingrediente_plato (id_plato, id_ingrediente, cantidad, id_unidad_medida) VALUES
(3, 9, 200, 'g'),    -- Pasta
(3, 7, 100, 'g'),    -- Queso mozzarella
(3, 11, 150, 'ml');  -- Leche

-- Mousse de chocolate
INSERT INTO ingrediente_plato (id_plato, id_ingrediente, cantidad, id_unidad_medida) VALUES
(4, 10, 150, 'g'),   -- Chocolate
(4, 11, 200, 'ml'),  -- Leche
(4, 12, 50, 'g');    -- Azúcar

-- Camarones al ajillo
INSERT INTO ingrediente_plato (id_plato, id_ingrediente, cantidad, id_unidad_medida) VALUES
(5, 8, 200, 'g'),    -- Camarones
(5, 5, 50, 'g'),     -- Cebolla
(5, 6, 100, 'g');    -- Tomate
