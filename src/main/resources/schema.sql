
CREATE TABLE price (
                         id   INTEGER      NOT NULL AUTO_INCREMENT,
                         is_night_time BIT(1),
                         price_group INTEGER,
                         price_per_hour DOUBLE,
                         PRIMARY KEY (id)
);
CREATE TABLE table_to_reserve (
                                  id   INTEGER      NOT NULL AUTO_INCREMENT,
                                  table_number INT,
                                  PRIMARY KEY (id)
);
CREATE TABLE user (
                      user_type VARCHAR(31),
                      id INTEGER      NOT NULL AUTO_INCREMENT,
                      email VARCHAR(255),
                      name VARCHAR(255),
                      phone_number VARCHAR(255),
                      usage_acceptance BIT(1),
                      discount DOUBLE,
                      password VARCHAR(255),
                      reservation_counter INT,
                      PRIMARY KEY (id)
);
CREATE TABLE reservation (
                         id   INTEGER      NOT NULL AUTO_INCREMENT,
                         date DATE,
                         end_date_time DATETIME(6),
                         is_available BIT(1),
                         price_group INTEGER,
                         price_per_reservation DOUBLE,
                         start_date_tim DATETIME(6),
                         table_id INTEGER,
                         user_id INTEGER,
                         PRIMARY KEY (id),
                         FOREIGN KEY (table_id) REFERENCES table_to_reserve(id),
                         FOREIGN KEY (user_id) REFERENCES  user(id)
);
CREATE TABLE user_reservations (
                                id INTEGER      NOT NULL AUTO_INCREMENT,
                                user_id   INTEGER,
                                reservations_id INTEGER,
                                PRIMARY KEY (id),
                                FOREIGN KEY (user_id) REFERENCES user(id),
                                FOREIGN KEY (reservations_id) REFERENCES reservation(id)
);
CREATE TABLE holiday_workdays (
                                  id   INTEGER      NOT NULL AUTO_INCREMENT,
                                  date DATE,
                                  PRIMARY KEY (id)
);