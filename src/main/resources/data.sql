INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (0, false, 18);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (0, true, 23);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (1, false, 18);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (1, true, 30);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (2, false, 23);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (2, true, 30);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (3, false, 23);
INSERT INTO price (price_group, is_night_time, price_per_hour) VALUES (3, true, 23);

INSERT INTO table_to_reserve (table_number) VALUES (1);
INSERT INTO table_to_reserve (table_number) VALUES (2);
INSERT INTO table_to_reserve (table_number) VALUES (3);
INSERT INTO table_to_reserve (table_number) VALUES (4);
INSERT INTO table_to_reserve (table_number) VALUES (5);
INSERT INTO table_to_reserve (table_number) VALUES (6);
INSERT INTO table_to_reserve (table_number) VALUES (7);
INSERT INTO table_to_reserve (table_number) VALUES (8);
INSERT INTO table_to_reserve (table_number) VALUES (9);
INSERT INTO table_to_reserve (table_number) VALUES (10);

INSERT INTO role (id, name) VALUES (NULL, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (NULL, 'ROLE_ADMIN');
