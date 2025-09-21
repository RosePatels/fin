CREATE SEQUENCE finance_info_id_seq START 1 INCREMENT 1;

CREATE TABLE finance_info (
    id bigint PRIMARY KEY DEFAULT nextval('finance_info_id_seq'),
    current_balance numeric(15,2) NOT NULL DEFAULT 0,
    total_income numeric(15,2) NOT NULL DEFAULT 0,
    total_expenses numeric(15,2) NOT NULL DEFAULT 0
)