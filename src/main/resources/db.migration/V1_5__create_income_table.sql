CREATE SEQUENCE income_id_seq START 1 INCREMENT 1;

CREATE TABLE income (
    id bigint PRIMARY KEY DEFAULT nextval('income_id_seq'),
    income_name varchar(255) NOT NULL,
    income_amount numeric(15,2) NOT NULL,
    recurring_frequency varchar(100),
    recurringYn boolean NOT NULL Default false
)