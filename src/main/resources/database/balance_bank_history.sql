-- Table: public.balance_bank_history

-- DROP TABLE public.balance_bank_history;

CREATE TABLE public.balance_bank_history
(
    id numeric NOT NULL,
    balance_bank_id numeric NOT NULL,
    balance_before numeric NOT NULL,
    balance_after numeric NOT NULL,
    activity character varying(10) COLLATE pg_catalog."default" NOT NULL,
    type character varying(6) COLLATE pg_catalog."default" NOT NULL,
    ip character varying(20) COLLATE pg_catalog."default" NOT NULL,
    location character varying(25) COLLATE pg_catalog."default" NOT NULL,
    user_agent character varying(25) COLLATE pg_catalog."default" NOT NULL,
    author character varying(10) COLLATE pg_catalog."default" NOT NULL,
    id_history character varying(10) COLLATE pg_catalog."default" NOT NULL
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.balance_bank_history
    OWNER to postgres;