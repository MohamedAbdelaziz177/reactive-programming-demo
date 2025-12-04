CREATE TABLE student(
    id BIGINT GENERATED ALWAYS AS IDENTITY  NOT NULL PRIMARY KEY,
    firstname VARCHAR(20),
    lastname VARCHAR(20),
    age int
)