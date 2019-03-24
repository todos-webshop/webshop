CREATE TABLE orders (
id BIGINT AUTO_INCREMENT,
user_id BIGINT,
order_time DATETIME,
total_order BIGINT,
status VARCHAR(255) NOT NULL,
CONSTRAINT pk_orders PRIMARY KEY (id),
CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id)
) engine = innodb;


CREATE TABLE ordered_items (
order_id BIGINT,
product_id BIGINT,
order_price BIGINT NOT NULL,
CONSTRAINT fk_ordered_items_baskets FOREIGN KEY (order_id) REFERENCES orders(id),
CONSTRAINT fk_oredered_items_products FOREIGN KEY (product_id) REFERENCES products(id)
) engine = innodb;