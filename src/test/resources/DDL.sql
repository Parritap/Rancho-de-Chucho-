-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.unidad_medida
(
    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
    notacion character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT unidad_medida_pkey PRIMARY KEY (notacion)
);

CREATE TABLE IF NOT EXISTS public.ingrediente
(
    cantidad_disponible integer NOT NULL,
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    precio_compra numeric(38, 2) NOT NULL,
    marca character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
    unidad_medida character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT ingrediente_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.ingrediente_plato
(
    cantidad integer,
    id_ingrediente integer NOT NULL,
    id_plato integer NOT NULL,
    unidad character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT ingrediente_plato_pkey PRIMARY KEY (id_ingrediente, id_plato)
);

CREATE TABLE IF NOT EXISTS public.plato
(
    id_plato integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    id_tipo_plato integer NOT NULL,
    precio double precision NOT NULL,
    descripcion character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT plato_pkey PRIMARY KEY (id_plato)
);

CREATE TABLE IF NOT EXISTS public.tipo_plato
(
    id_tipo_plato integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    tipo_padre integer NOT NULL,
    descripcion character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipo_plato_pkey PRIMARY KEY (id_tipo_plato)
);

CREATE TABLE IF NOT EXISTS public.orden_plato
(
    cantidad integer NOT NULL,
    id_orden integer NOT NULL,
    id_plato integer NOT NULL,
    CONSTRAINT orden_plato_pkey PRIMARY KEY (id_orden, id_plato)
);

CREATE TABLE IF NOT EXISTS public.orden
(
    id_mesa integer NOT NULL,
    id_orden integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    impuestos integer NOT NULL,
    subtotal numeric(10, 2) NOT NULL,
    fecha_cierre timestamp(6) without time zone,
    fecha_inicio timestamp(6) without time zone NOT NULL,
    cedula_mesero character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT orden_pkey PRIMARY KEY (id_orden)
);

CREATE TABLE IF NOT EXISTS public.mesero
(
    en_turno boolean NOT NULL,
    cedula character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT mesero_pkey PRIMARY KEY (cedula)
);

CREATE TABLE IF NOT EXISTS public.usuario
(
    esta_activo boolean,
    cedula character varying(255) COLLATE pg_catalog."default" NOT NULL,
    img_url character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (cedula),
    CONSTRAINT usuario_username_key UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS public.admin
(
    cedula character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT admin_pkey PRIMARY KEY (cedula)
);

CREATE TABLE IF NOT EXISTS public.cocinero
(
    cedula character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cocinero_pkey PRIMARY KEY (cedula)
);

CREATE TABLE IF NOT EXISTS public.mesa
(
    disponible boolean NOT NULL,
    id_mesa integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    CONSTRAINT mesa_pkey PRIMARY KEY (id_mesa)
);

ALTER TABLE IF EXISTS public.ingrediente
    ADD CONSTRAINT fkevokaje9vgurb08i19ndgh434 FOREIGN KEY (unidad_medida)
        REFERENCES public.unidad_medida (notacion) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.ingrediente_plato
    ADD CONSTRAINT fk9c3mqwoavhii63nxlbgxfw02r FOREIGN KEY (unidad)
        REFERENCES public.unidad_medida (notacion) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.ingrediente_plato
    ADD CONSTRAINT fkkaici1d2ekiknhjvhf6m611wp FOREIGN KEY (id_plato)
        REFERENCES public.plato (id_plato) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.ingrediente_plato
    ADD CONSTRAINT fkoj8v1r29wbh8a8m2q61u9jpo9 FOREIGN KEY (id_ingrediente)
        REFERENCES public.ingrediente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.plato
    ADD CONSTRAINT fkeioa4f8g1vbs0bid1ifmv6niy FOREIGN KEY (id_tipo_plato)
        REFERENCES public.tipo_plato (id_tipo_plato) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.tipo_plato
    ADD CONSTRAINT fkgunv6nwwxpdrdqp2oquoo80lv FOREIGN KEY (tipo_padre)
        REFERENCES public.tipo_plato (id_tipo_plato) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.orden_plato
    ADD CONSTRAINT fk55jqeo5a0pbtyey3ffkwxpc8l FOREIGN KEY (id_orden)
        REFERENCES public.orden (id_orden) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.orden_plato
    ADD CONSTRAINT fkbetxni06nhfshfednc1lr7ctq FOREIGN KEY (id_plato)
        REFERENCES public.plato (id_plato) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.orden
    ADD CONSTRAINT fk557qcek3pfa9nrbws90abusbl FOREIGN KEY (cedula_mesero)
        REFERENCES public.mesero (cedula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.orden
    ADD CONSTRAINT fkp3csxwa38qulh9im9kp5yohnc FOREIGN KEY (id_mesa)
        REFERENCES public.mesa (id_mesa) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.mesero
    ADD CONSTRAINT fkrxu0l5hxle58os6atslemmgp0 FOREIGN KEY (cedula)
        REFERENCES public.usuario (cedula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS mesero_pkey
    ON public.mesero(cedula);


ALTER TABLE IF EXISTS public.admin
    ADD CONSTRAINT fk2cwkh97ymblu747hu3jib00lg FOREIGN KEY (cedula)
        REFERENCES public.usuario (cedula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS admin_pkey
    ON public.admin(cedula);


ALTER TABLE IF EXISTS public.cocinero
    ADD CONSTRAINT fk54mbif37tl995qq2ajd9h14pg FOREIGN KEY (cedula)
        REFERENCES public.usuario (cedula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS cocinero_pkey
    ON public.cocinero(cedula);

END;