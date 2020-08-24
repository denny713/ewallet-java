-- Table: public.user_balance

-- DROP TABLE public.user_balance;

CREATE TABLE public.user_balance
(
    id numeric NOT NULL DEFAULT nextval('"user-balance-seq"'::regclass),
    user_id numeric NOT NULL,
    balance numeric NOT NULL,
    balance_achieve numeric NOT NULL,
    CONSTRAINT user_balance_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_balance
    OWNER to postgres;