CREATE TABLE city
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE area
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city_id INTEGER NOT NULL REFERENCES city (id)
);

CREATE TABLE ice_rink
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city_id INTEGER NOT NULL REFERENCES city (id),
    area_id INTEGER NOT NULL REFERENCES area (id),
    opening_date DATE NOT NULL,
    closing_date DATE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE day
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time TIME WITHOUT TIME ZONE NOT NULL,
    end_time TIME WITHOUT TIME ZONE NOT NULL,
    is_holiday BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE comment
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    ice_rink_id INTEGER NOT NULL REFERENCES ice_rink (id),
    user_name VARCHAR(255) NOT NULL,
    time TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE ice_rink_day
(
    id_ice_rink INTEGER REFERENCES ice_rink (id),
    id_day INTEGER REFERENCES day (id)
);
