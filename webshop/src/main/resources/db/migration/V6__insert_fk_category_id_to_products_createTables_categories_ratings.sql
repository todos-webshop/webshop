CREATE TABLE categories (
	id BIGINT AUTO_INCREMENT,
	name VARCHAR(255),
	sequence INT,
	CONSTRAINT pk_id PRIMARY KEY (id)
) engine = innodb;


ALTER TABLE products
ADD COLUMN category_id BIGINT DEFAULT 1;


INSERT INTO categories
VALUES (1, 'No category', 1);


ALTER TABLE products
ADD CONSTRAINT fk_products_categories
FOREIGN KEY(category_id)REFERENCES categories(id);


CREATE TABLE ratings(
    	id BIGINT AUTO_INCREMENT,
    	user_id BIGINT,
    	product_id BIGINT,
    	stars INT,
    	message TEXT,
    	rating_time DATETIME,
   	CONSTRAINT pk_id PRIMARY KEY (id),
CONSTRAINT fk_ratings_users FOREIGN KEY(user_id)REFERENCES users(id),
CONSTRAINT fk_ratings_products FOREIGN KEY(product_id)REFERENCES products(id)
);