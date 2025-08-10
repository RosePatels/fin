CREATE TABLE pots (
    id bigint NOT NULL,
    pot_name varchar(255) NOT NULL,
    total_saved numeric(15,2) NOT NULL DEFAULT 0,
    pot_target numeric(15,2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE pots_id_seq START 1 INCREMENT 1;
