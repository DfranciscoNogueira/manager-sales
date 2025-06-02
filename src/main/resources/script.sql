
-- criação da tabelas

CREATE TABLE CLIENT (
    id bigint NOT NULL,
    day_closing_invoice INTEGER NOT NULL,
    limit_sales NUMERIC(38,2) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PRODUCT (
    id bigint NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(38,2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE SALE_ITEM (
    id bigint NOT NULL,
    amount INTEGER NOT NULL,
    total_value NUMERIC(38,2) NOT NULL,
    product_id bigint NOT NULL,
    sales_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE SALES (
    id bigint NOT NULL,
    date_sale date NOT NULL,
    client_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE sequence client_seq start WITH 1 increment BY 1;
CREATE sequence product_seq start WITH 1 increment BY 1;
CREATE sequence sales_seq start WITH 1 increment BY 1;

ALTER TABLE if EXISTS SALE_ITEM ADD CONSTRAINT c_fk_product_id FOREIGN KEY (product_id) REFERENCES PRODUCT;

ALTER TABLE if EXISTS SALE_ITEM ADD CONSTRAINT c_fk_sales_id FOREIGN KEY (sales_id) REFERENCES SALES;

ALTER TABLE if EXISTS SALES ADD CONSTRAINT c_fk_client_id FOREIGN KEY (client_id) REFERENCES CLIENT;

INSERT INTO public.client (id, day_closing_invoice, limit_sales, "name") VALUES(nextval('public.client_seq'), 1, 35, 'Diego Francisco');