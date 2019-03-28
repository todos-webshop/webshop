DELETE FROM basket_items;
DELETE FROM baskets;
DELETE FROM ordered_items;
DELETE FROM orders;
DELETE FROM users;
DELETE FROM products;
DELETE FROM categories;


INSERT INTO categories VALUES (1, 'No category', 1);
INSERT INTO categories VALUES (2, 'First category', 2);

INSERT INTO products (code, name, address, manufacturer, price) VALUES
('CB1', 'Natural Coconut Bowl', 'natural_coconut_bowl', 'Coconut Bowls', 3190, 1),
('CB2', 'Original Coconut Bowl', 'original_coconut_bowl', 'Coconut Bowls', 3190, 1),
('CB3', 'Natural Coconut Bowl Set', 'natural_coconut_bowl_set', 'Coconut Bowls', 9990, 2),
('CB4', 'Original Coconut Bowl Set', 'original_coconut_bowl_set', 'Coconut Bowls', 9990, 1),
('BP1', 'Bamboo Toothbrush', 'bamboo_toothbrush', 'The Bam&Boo', 1590, 2);


INSERT  INTO users (first_name, last_name, username, password, role, enabled) VALUES ('Pál', 'Admin', 'admin', '$2a$04$aIC3NGHhEw23ZXa7lShzMu3ywhCYliO1Vk3UamnifvFetuNGZZxGy', 'ROLE_ADMIN', 1);
INSERT  INTO users (first_name, last_name, username, password, role, enabled) VALUES ('Peti', 'User', 'user', '$2a$04$6hZ39bO3tVmMXR1r9oQicunolXuk99tPCTavnPk4hpvbMNxTM0IF6', 'ROLE_USER', 1);

INSERT INTO baskets (id, user_id) VALUES (1,2);

INSERT into basket_items (basket_id, product_id) VALUES (1,1,2);
INSERT into basket_items (basket_id, product_id) VALUES (1,2,10);
INSERT into basket_items (basket_id, product_id) VALUES (1,3,3);

INSERT INTO orders (id, user_id, order_time, status) VALUES (1,2,"2019-03-28 10:31:08", "ACTIVE", "1111 BP. Fő út 1");
INSERT INTO orders (id, user_id, order_time, status) VALUES (2,2,"2019-03-29 10:31:08", "DELETED", "1111 BP. Fő út 2");
INSERT INTO orders (id, user_id, order_time, status) VALUES (3,2,"2019-03-27 10:31:08", "DELIVERED", "1111 BP. Fő út 3");

INSERT INTO ordered_items (order_id, product_id, order_price) VALUES (1,1,2900,1);
INSERT INTO ordered_items (order_id, product_id, order_price) VALUES (1,2,3900,3);
INSERT INTO ordered_items (order_id, product_id, order_price) VALUES (1,3,4900,10);
