CREATE SEQUENCE budgets_id_seq START 1 INCREMENT 1;

CREATE TABLE budgets (
    id bigint PRIMARY KEY DEFAULT nextval('budgets_id_seq'),
    budget_name varchar(255) NOT NULL,
    budget_amount numeric(15,2) NOT NULL DEFAULT 0,
    amount_spent numeric(15,2) NOT NULL DEFAULT 0
);