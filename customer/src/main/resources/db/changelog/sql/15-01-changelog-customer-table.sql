-- liquibase formatted sql
CREATE SEQUENCE IF NOT EXISTS customer_id_sequence START WITH 1 INCREMENT BY 50;

CREATE TABLE customer
(
    id         INTEGER NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255),
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE customer_rights
(
    customer_id INTEGER NOT NULL,
    rights      INTEGER
);

ALTER TABLE customer_rights
    ADD CONSTRAINT fk_customer_rights_on_customer FOREIGN KEY (customer_id) REFERENCES customer (id);
