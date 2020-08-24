-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    id numeric NOT NULL DEFAULT nextval('"user-seq"'::regclass),
    username character varying(15) COLLATE pg_catalog."default",
    password character varying(64) COLLATE pg_catalog."default",
    email character varying(30) COLLATE pg_catalog."default",
    status character varying(5) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;
