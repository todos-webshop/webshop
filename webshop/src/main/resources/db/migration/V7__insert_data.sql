INSERT INTO orders (user_id, order_time, status, shipping_address) VALUES
        (3, '2019-04-03 09:02:33', 'ACTIVE', '1030 Budapest, Pók utca 3.'),
        (3, '2019-04-09 09:02:33', 'ACTIVE', '1030 Budapest, Pók utca 3.'),
        (3, '2019-04-06 09:02:33', 'ACTIVE', '1030 Budapest, Pók utca 3.'),
        (3, '2019-04-05 09:02:33', 'DELETED', '1030 Budapest, Pók utca 3.'),
        (3, '2019-03-23 09:02:33', 'DELIVERED', '1030 Budapest, Pók utca 3.'),
        (3, '2019-03-27 09:02:33', 'DELIVERED', '1030 Budapest, Pók utca 3.'),
        (3, '2019-03-27 09:02:33', 'DELIVERED', '1030 Budapest, Pók utca 3.'),
        (2, '2019-03-26 09:02:33', 'DELIVERED', '1030 Budapest, Teve utca 13.'),
        (2, '2019-03-24 09:02:33', 'DELIVERED', '1030 Budapest, Teve utca 13.'),
        (2, '2019-03-25 09:02:33', 'DELIVERED', '1030 Budapest, Teve utca 13.');


INSERT INTO ordered_items (order_id, product_id, order_price, quantity) VALUES
        (1, 1, 3190, 2),
        (1, 3, 9990, 1),
        (1, 5, 1590, 3),
        (1, 8, 1190, 4),
        (1, 2, 3190, 1),
        (1, 4, 9990, 1),
        (2, 15, 1990, 1),
        (2, 5, 1590, 1),
        (2, 3, 9990, 2),
        (3, 19, 1490, 1),
        (3, 22, 2990, 2),
        (3, 20, 1490, 4),
        (3, 16, 1490, 3),
        (3, 18, 2990, 1),
        (3, 14, 1690, 1),
        (4, 6, 4490, 1),
        (4, 8, 1190, 2),
        (4, 21, 1990, 2),
        (4, 12, 4990, 1),
        (4, 11, 990, 1),
        (4, 1, 3190, 2),
        (5, 2, 3190, 2),
        (5, 4, 9990, 2),
        (5, 17, 2490, 3),
        (5, 19, 1490, 5),
        (5, 10, 2990, 1),
        (5, 16, 1490, 2),
        (6, 5, 1590, 2),
        (6, 7, 6490, 1),
        (6, 9, 2190, 2),
        (6, 13, 5990, 1),
        (6, 14, 1690, 2),
        (6, 15, 1990, 1),
        (7, 10, 2990, 2),
        (7, 6, 4490, 3),
        (7, 8, 1190, 1),
        (7, 21, 1990, 1),
        (7, 1, 3190, 1),
        (7, 3, 9990, 2),
        (8, 2, 3190, 2),
        (8, 4, 9990, 2),
        (8, 17, 2490, 3),
        (8, 19, 1490, 5),
        (8, 10, 2990, 1),
        (8, 16, 1490, 2),
        (9, 5, 1590, 2),
        (9, 7, 6490, 1),
        (9, 9, 2190, 2),
        (9, 13, 5990, 1),
        (9, 14, 1690, 2),
        (9, 15, 1990, 1),
        (10, 10, 2990, 2),
        (10, 6, 4490, 3),
        (10, 8, 1190, 1),
        (10, 21, 1990, 1),
        (10, 1, 3190, 1),
        (10, 3, 9990, 2);

INSERT INTO ratings (user_id, product_id, stars, message, rating_time) VALUES
        (2, 2, 5, 'I love using my coconut bowls, makes my food taste much better:)', '2019-04-09 09:02:33'),
        (2, 4, 5, 'I love using my coconut bowls, makes my food taste much better:)', '2019-04-09 09:02:33'),
        (2, 17, 5, 'Very nice and stylish product! And a quick delivery! ;)', '2019-04-09 09:02:33'),
        (2, 19, 5, 'Awesome!! Love it!!', '2019-04-09 09:02:33'),
        (2, 10, 5, 'Amazing product!!', '2019-04-09 09:02:33'),
        (2, 16, 5, 'Love this product! Will recommend it to my friends!!', '2019-04-09 09:02:33'),
        (2, 5, 5, 'I am so happy with my product! There was a delay in shipping with Christmas and they sent me an extra product & I was so happy!', '2019-04-09 09:02:33'),
        (2, 7, 5, 'I love my new product !! Cute and eco-friendly', '2019-04-09 09:02:33'),
        (2, 9, 5, 'So happy that this company exists!!! Perfect gift for my husband!', '2019-04-09 09:02:33'),
        (2, 13, 5, 'Very nice and stylish product! And a quick delivery! ;)', '2019-04-09 09:02:33'),
        (2, 14, 5, 'Modern, reusable and non-toxic. Love it!', '2019-04-09 09:02:33'),
        (2, 15, 5, 'I love my new product !! Cute and eco-friendly', '2019-04-09 09:02:33'),
        (2, 10, 5, 'Very nice and stylish product! And a quick delivery! ;)', '2019-04-09 09:02:33'),
        (2, 6, 5, 'So happy that this company exists!!! Perfect gift for my husband!', '2019-04-09 09:02:33'),
        (2, 8, 5, 'So happy that this company exists!!! Perfect gift for my husband!', '2019-04-09 09:02:33'),
        (2, 21, 5, 'So happy that this company exists!!! Perfect gift for my husband!', '2019-04-09 09:02:33'),
        (2, 1, 5, 'I love using my coconut bowls, makes my food taste much better:)', '2019-04-09 09:02:33'),
        (2, 3, 5, 'I love using my coconut bowls, makes my food taste much better:)', '2019-04-09 09:02:33');





