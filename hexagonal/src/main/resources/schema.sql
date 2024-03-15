CREATE
    TABLE
        IF NOT EXISTS architectures(
            id uuid NOT NULL,
            name VARCHAR NOT NULL,
            notes VARCHAR,
            pros VARCHAR,
            cons VARCHAR,
            PRIMARY KEY(id)
        );
