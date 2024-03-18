CREATE
    TABLE
        IF NOT EXISTS architectures(
            id uuid NOT NULL,
            name VARCHAR NOT NULL,
            notes VARCHAR,
            pros VARCHAR,
            cons VARCHAR,
            created TIMESTAMP,
            updated TIMESTAMP,
            PRIMARY KEY(id)
        );

CREATE
    TABLE
        IF NOT EXISTS architectures_ratings(
            id uuid NOT NULL,
            architecture_id uuid NOT NULL,
            user_id uuid NOT NULL,
            rating INT NOT NULL,
            comment VARCHAR,
            PRIMARY KEY(id)
        );

CREATE
    INDEX IF NOT EXISTS architectures_ratings_architecture_id ON
    architectures_ratings(architecture_id);
