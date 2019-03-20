CREATE TABLE users (
id BIGINT AUTO_INCREMENT,
first_name VARCHAR(255),
last_name VARCHAR(255),
username VARCHAR(255),
password VARCHAR(255),
role VARCHAR(50),
enabled INT,
CONSTRAINT uq_username UNIQUE (username),
CONSTRAINT pk_users PRIMARY KEY (id)
) engine = innodb;


CREATE TABLE baskets (
id BIGINT AUTO_INCREMENT,
user_id BIGINT,
CONSTRAINT pk_baskets PRIMARY KEY (id),
CONSTRAINT fk_baskets_users FOREIGN KEY (user_id) REFERENCES users(id)
) engine = innodb;


CREATE TABLE basket_items (
basket_id BIGINT,
product_id BIGINT,
CONSTRAINT fk_basket_items_baskets FOREIGN KEY (basket_id) REFERENCES baskets(id),
CONSTRAINT fk_basket_items_products FOREIGN KEY (product_id) REFERENCES products(id)
) engine = innodb;


INSERT  INTO users (first_name, last_name, username, password, role, enabled) values ('Pál', 'Admin', 'admin', '$2a$04$aIC3NGHhEw23ZXa7lShzMu3ywhCYliO1Vk3UamnifvFetuNGZZxGy', 'ROLE_ADMIN', 1);
INSERT  INTO users (first_name, last_name, username, password, role, enabled) values ('Peti', 'Kiss', 'robi', '$2a$04$slhfZh4tNqnPOuX58WXmc.fdprzJdPd3V274smetu9ryQHZ7u2V8.', 'ROLE_USER', 1);
INSERT  INTO users (first_name, last_name, username, password, role, enabled) values ('Margit', 'Gyémánt', 'margit', '$2a$04$dCt6DPHNA6/6bETqa.c9Z.Sv/nH0eKzK74ZP0/2aIj7e7EchJ5hQi', 'ROLE_USER', 1);
INSERT  INTO users (first_name, last_name, username, password, role, enabled) values ('Szilvia', 'Kilincs', 'kálmán', '$2a$04$9lGvpQU9akFneWQvQdW8sOWdtAk2UpcY73wzk9/R3cFViVJMnOAna', 'ROLE_USER', 1);
INSERT  INTO users (first_name, last_name, username, password, role, enabled) values ('Roland', 'Kabay', 'r$t56!z', '$2a$04$09bfDizDro1hSF4vBfhLQuMpLTn8qdCHtG85EBdyvO2ekX8ujSm.a', 'ROLE_USER', 1);
