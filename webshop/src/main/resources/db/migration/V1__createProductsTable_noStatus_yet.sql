CREATE TABLE products (
id BIGINT AUTO_INCREMENT,
code VARCHAR(255) NOT NULL,
name VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
manufacturer VARCHAR(255),
price INT NOT NULL,
CONSTRAINT pk_products PRIMARY KEY (id),
CONSTRAINT uq_code UNIQUE (code),
CONSTRAINT uq_name UNIQUE (name),
CONSTRAINT uq_address UNIQUE (address)
) engine = innodb;
