-- criação do DB

CREATE DATABASE manager_sales;

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
    total  NUMERIC(38,2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE sequence client_seq start WITH 1 increment BY 1;
CREATE sequence product_seq start WITH 1 increment BY 1;
CREATE sequence sales_seq start WITH 1 increment BY 1;
CREATE sequence sales_item_seq start WITH 1 increment BY 1;

ALTER TABLE if EXISTS SALE_ITEM ADD CONSTRAINT c_fk_product_id FOREIGN KEY (product_id) REFERENCES PRODUCT;

ALTER TABLE if EXISTS SALE_ITEM ADD CONSTRAINT c_fk_sales_id FOREIGN KEY (sales_id) REFERENCES SALES;

ALTER TABLE if EXISTS SALES ADD CONSTRAINT c_fk_client_id FOREIGN KEY (client_id) REFERENCES CLIENT;

-- criação de dados para teste

INSERT INTO public.client (id, day_closing_invoice, limit_sales, "name") values
(nextval('public.client_seq'), 1, 35, 'Diego Francisco'),
(nextval('public.client_seq'), 5, 5000.00, 'Carlos Silva'),
(nextval('public.client_seq'), 10, 3000.00, 'Ana Souza'),
(nextval('public.client_seq'), 15, 8000.00, 'José Ferreira'),
(nextval('public.client_seq'), 20, 1500.00, 'Mariana Lima'),
(nextval('public.client_seq'), 25, 6000.00, 'Ricardo Almeida'),
(nextval('public.client_seq'), 30, 2000.00, 'Fernanda Costa'),
(nextval('public.client_seq'), 8, 7500.00, 'Mateus Oliveira'),
(nextval('public.client_seq'), 12, 4000.00, 'Juliana Mendes'),
(nextval('public.client_seq'), 18, 500.00, 'Bruno Santos'),
(nextval('public.client_seq'), 22, 9500.00, 'Tatiane Rocha'),
(nextval('public.client_seq'), 4, 12000.00, 'Eduardo Nunes'),
(nextval('public.client_seq'), 17, 3800.00, 'Paula Ramos'),
(nextval('public.client_seq'), 9, 2500.00, 'Gabriel Monteiro'),
(nextval('public.client_seq'), 14, 6700.00, 'Renata Vasconcelos'),
(nextval('public.client_seq'), 19, 4300.00, 'Lucas Matheus'),
(nextval('public.client_seq'), 28, 3100.00, 'Camila Andrade'),
(nextval('public.client_seq'), 6, 10500.00, 'Felipe Augusto'),
(nextval('public.client_seq'), 11, 2100.00, 'Patrícia Silva'),
(nextval('public.client_seq'), 27, 9000.00, 'Rafael Cunha'),
(nextval('public.client_seq'), 3, 7000.00, 'Michele Duarte');

INSERT INTO public.product (id, description, price) VALUES
(nextval('public.product_seq'), 'Notebook Dell Inspiron', 4500.00),
(nextval('public.product_seq'), 'Smartphone Samsung Galaxy S21', 3200.00),
(nextval('public.product_seq'), 'Monitor LED 27" LG', 1200.00),
(nextval('public.product_seq'), 'Teclado Mecânico RGB', 350.00),
(nextval('public.product_seq'), 'Mouse Gamer Logitech', 250.00),
(nextval('public.product_seq'), 'Fone de Ouvido Bluetooth JBL', 550.00),
(nextval('public.product_seq'), 'Cadeira Ergonômica Gamer', 890.00),
(nextval('public.product_seq'), 'Impressora Multifuncional HP', 970.00),
(nextval('public.product_seq'), 'HD Externo 1TB Seagate', 430.00),
(nextval('public.product_seq'), 'Smartwatch Apple Watch Series 7', 2400.00),
(nextval('public.product_seq'), 'Tablet Samsung Galaxy Tab', 1800.00),
(nextval('public.product_seq'), 'Monitor 24" Asus Full HD', 1100.00),
(nextval('public.product_seq'), 'Carregador Portátil 10000mAh', 250.00),
(nextval('public.product_seq'), 'SSD NVMe 500GB Kingston', 410.00),
(nextval('public.product_seq'), 'Processador Intel Core i7', 2800.00),
(nextval('public.product_seq'), 'Placa de Vídeo RTX 3060', 3200.00),
(nextval('public.product_seq'), 'Mousepad Gamer Extra Grande', 150.00),
(nextval('public.product_seq'), 'Webcam Full HD Logitech', 390.00),
(nextval('public.product_seq'), 'Caixa de Som Bluetooth Sony', 720.00),
(nextval('public.product_seq'), 'Cabo HDMI 4K Ultra HD', 120.00);

INSERT INTO public.sales (id, date_sale, client_id, total) VALUES
(nextval('public.sales_seq'), '2024-01-15', 1, 3200.00),
(nextval('public.sales_seq'), '2024-02-10', 3, 5700.00),
(nextval('public.sales_seq'), '2024-03-05', 5, 1500.00),
(nextval('public.sales_seq'), '2024-04-22', 8, 2400.00),
(nextval('public.sales_seq'), '2024-05-18', 2, 4000.00),
(nextval('public.sales_seq'), '2024-06-30', 9, 6700.00),
(nextval('public.sales_seq'), '2024-07-05', 6, 1300.00),
(nextval('public.sales_seq'), '2024-08-25', 10, 8200.00),
(nextval('public.sales_seq'), '2024-09-12', 4, 1100.00),
(nextval('public.sales_seq'), '2024-10-17', 7, 3900.00),
(nextval('public.sales_seq'), '2024-11-08', 11, 2200.00),
(nextval('public.sales_seq'), '2024-12-14', 13, 5800.00),
(nextval('public.sales_seq'), '2025-01-03', 15, 9200.00),
(nextval('public.sales_seq'), '2025-02-27', 17, 3200.00),
(nextval('public.sales_seq'), '2025-03-08', 19, 5000.00),
(nextval('public.sales_seq'), '2025-04-18', 12, 7100.00),
(nextval('public.sales_seq'), '2025-05-07', 16, 1400.00),
(nextval('public.sales_seq'), '2025-06-23', 14, 3300.00),
(nextval('public.sales_seq'), '2025-07-14', 20, 8900.00),
(nextval('public.sales_seq'), '2025-08-30', 18, 4600.00);

INSERT INTO public.sale_item (id, amount, total_value, product_id, sales_id) VALUES
(nextval('public.sales_item_seq'), 1, 3200.00, 2, 1),
(nextval('public.sales_item_seq'), 2, 2400.00, 10, 2),
(nextval('public.sales_item_seq'), 1, 1500.00, 5, 3),
(nextval('public.sales_item_seq'), 3, 2400.00, 12, 4),
(nextval('public.sales_item_seq'), 2, 4000.00, 1, 5),
(nextval('public.sales_item_seq'), 4, 6700.00, 6, 6),
(nextval('public.sales_item_seq'), 1, 1300.00, 3, 7),
(nextval('public.sales_item_seq'), 5, 8200.00, 18, 8),
(nextval('public.sales_item_seq'), 2, 1100.00, 7, 9),
(nextval('public.sales_item_seq'), 3, 3900.00, 15, 10),
(nextval('public.sales_item_seq'), 1, 2200.00, 4, 11),
(nextval('public.sales_item_seq'), 2, 5800.00, 9, 12),
(nextval('public.sales_item_seq'), 3, 9200.00, 11, 13),
(nextval('public.sales_item_seq'), 4, 3200.00, 20, 14),
(nextval('public.sales_item_seq'), 2, 5000.00, 8, 15);


