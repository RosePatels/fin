CREATE SEQUENCE recurring_bills_id_seq START 1 INCREMENT 1;

CREATE TABLE recurring_bills (
    id bigint PRIMARY KEY DEFAULT nextval('recurring_bills_id_seq'),
    bill_name varchar(255) NOT NULL,
    bill_amount numeric(15,2) NOT NULL,
    due_date date NOT NULL,
    activeYn boolean NOT NULL DEFAULT false
)