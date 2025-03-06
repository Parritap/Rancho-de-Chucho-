BEGIN;

-- Insert types of dishes
INSERT INTO public.tipo_plato (id_tipo_plato, tipo_padre, descripcion, nombre)
VALUES (1, NULL, 'Plato principal con proteínas', 'Plato fuerte'),
       (2, NULL, 'Acompañamiento ligero', 'Entrada'),
       (3, NULL, 'Postre dulce', 'Postre');

-- Insert dishes
INSERT INTO public.plato (id_plato, id_tipo_plato, precio, descripcion, nombre)
VALUES (1, 1, 25000, 'Delicioso filete de res con papas y ensalada', 'Filete de Res'),
       (2, 2, 15000, 'Sopa de cebolla con queso gratinado', 'Sopa de Cebolla'),
       (3, 3, 12000, 'Tarta de chocolate con crema', 'Tarta de Chocolate');

-- Insert units of measurement
INSERT INTO public.unidad_medida (notacion, nombre)
VALUES ('kg', 'Kilogramo'),
       ('g', 'Gramo'),
       ('ml', 'Mililitro');

-- Insert ingredients
INSERT INTO public.ingrediente (id, cantidad_disponible, precio_compra, marca, nombre, unidad_medida)
VALUES (1, 50, 10000, 'Marca A', 'Carne de res', 'kg'),
       (2, 30, 5000, 'Marca B', 'Papas', 'kg'),
       (3, 40, 3000, 'Marca C', 'Lechuga', 'g'),
       (4, 25, 8000, 'Marca D', 'Cebolla', 'g'),
       (5, 10, 12000, 'Marca E', 'Queso', 'g'),
       (6, 20, 7000, 'Marca F', 'Chocolate', 'g'),
       (7, 15, 4000, 'Marca G', 'Harina', 'g'),
       (8, 18, 6000, 'Marca H', 'Crema de leche', 'ml');

-- Insert ingredients in dishes
INSERT INTO public.ingrediente_plato (id_ingrediente, id_plato, cantidad)
VALUES (1, 1, 1),   -- 1 kg de Carne de res para Filete de Res
       (2, 1, 0.5), -- 0.5 kg de Papas para Filete de Res
       (3, 1, 100), -- 100 g de Lechuga para Filete de Res
       (4, 2, 200), -- 200 g de Cebolla para Sopa de Cebolla
       (5, 2, 50),  -- 50 g de Queso para Sopa de Cebolla
       (6, 3, 150), -- 150 g de Chocolate para Tarta de Chocolate
       (7, 3, 200), -- 200 g de Harina para Tarta de Chocolate
       (8, 3, 100); -- 100 ml de Crema de leche para Tarta de Chocolate

COMMIT;