-- Table: public.balance_bank

-- DROP TABLE public.balance_bank;

CREATE TABLE public.balance_bank
(
    id numeric NOT NULL DEFAULT nextval('"balance-bank-seq"'::regclass),
    balance numeric NOT NULL,
    balance_achieve numeric NOT NULL,
    code character varying(25) COLLATE pg_catalog."default",
    enable boolean NOT NULL,
    user_id numeric NOT NULL,
    CONSTRAINT balance_bank_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.balance_bank
    OWNER to postgres;