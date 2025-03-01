-- Create 5 registers for the unidad_medida table given that table DDL is
--
--CREATE TABLE IF NOT EXISTS public.unidad_medida
--(
--   nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
--   notacion character varying(255) COLLATE pg_catalog."default" NOT NULL,
--   CONSTRAINT unidad_medida_pkey PRIMARY KEY (notacion)
--);

-- Unidad medida
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('Kilogramo', 'kg');
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('Libra', 'lb');
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('Litro', 'l');
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('Metro', 'm');
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('Mililitro', 'ml');
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('gramo', 'g');
-- La siguiente notacion existe para aquellos productos que no puedan por alguna razón ser medidos.
INSERT INTO public.unidad_medida (nombre, notacion) VALUES ('Generico', 'gen');




--
-- Ingredientes
INSERT INTO public.ingrediente (cantidad_disponible, precio_compra, marca, nombre, unidad_medida) VALUES (100, 1.50, 'Ledesma', 'Azúcar', 'Kg');
INSERT INTO public.ingrediente (cantidad_disponible, precio_compra, marca, nombre, unidad_medida) VALUES (50, 2.20, 'Cocinero', 'Aceite de Girasol', 'L');
INSERT INTO public.ingrediente (cantidad_disponible, precio_compra, marca, nombre, unidad_medida) VALUES (200, 0.80, 'Celusal', 'Sal', 'Kg');
INSERT INTO public.ingrediente (cantidad_disponible, precio_compra, marca, nombre, unidad_medida) VALUES (150, 3.00, 'Molino', 'Harina', 'Kg');
INSERT INTO public.ingrediente (cantidad_disponible, precio_compra, marca, nombre, unidad_medida) VALUES (75, 1.00, 'La Serenisima', 'Leche', 'L');


