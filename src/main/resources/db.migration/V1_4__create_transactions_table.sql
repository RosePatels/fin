CREATE SEQUENCE transactions_id_seq START 1 INCREMENT 1;

CREATE TABLE transactions (
    id bigint PRIMARY KEY DEFAULT nextval('transactions_id_seq'),
    transaction_name varchar(255) NOT NULL,
    transaction_amount numeric(15,2) NOT NULL,
    transaction_date date NOT NULL,
    category_type varchar(100) NOT NULL,
    category_id bigint NOT NULL
);