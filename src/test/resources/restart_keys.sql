-- Reset sequence for public.ingrediente.id
-- Finds the sequence name, gets the max(id), adds 1, and sets the next sequence value.
-- COALESCE handles the case where the table might be empty (MAX returns NULL).
SELECT setval(
               pg_get_serial_sequence('public.ingrediente', 'id'),
               COALESCE(MAX(id), 0) + 1,
               false -- 'false' means the next call to nextval() will return this value
       )
FROM public.ingrediente;

-- Reset sequence for public.plato.id_plato
SELECT setval(
               pg_get_serial_sequence('public.plato', 'id_plato'),
               COALESCE(MAX(id_plato), 0) + 1,
               false
       )
FROM public.plato;

-- Reset sequence for public.tipo_plato.id_tipo_plato
SELECT setval(
               pg_get_serial_sequence('public.tipo_plato', 'id_tipo_plato'),
               COALESCE(MAX(id_tipo_plato), 0) + 1,
               false
       )
FROM public.tipo_plato;

-- Reset sequence for public.orden.id_orden
SELECT setval(
               pg_get_serial_sequence('public.orden', 'id_orden'),
               COALESCE(MAX(id_orden), 0) + 1,
               false
       )
FROM public.orden;

-- Reset sequence for public.mesa.id_mesa
SELECT setval(
               pg_get_serial_sequence('public.mesa', 'id_mesa'),
               COALESCE(MAX(id_mesa), 0) + 1,
               false
       )
FROM public.mesa;
