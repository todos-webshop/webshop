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


INSERT INTO products (code, name, address, manufacturer, price) VALUES
	('CB1', 'Natural Coconut Bowl', 'natural_coconut_bowl', 'Coconut Bowls', 3190),
	('CB2', 'Original Coconut Bowl', 'original_coconut_bowl', 'Coconut Bowls', 3190),
	('CB3', 'Natural Coconut Bowl Set', 'natural_coconut_bowl_set', 'Coconut Bowls', 9990),
	('CB4', 'Original Coconut Bowl Set', 'original_coconut_bowl_set', 'Coconut Bowls', 9990),
	('BP1', 'Bamboo Toothbrush', 'bamboo_toothbrush', 'The Bam&Boo', 1590),
	('BP2', 'Bamboo Water Bottle', 'bamboo_water_bottle', 'Funk Trunk', 4490),
	('BP3', 'Bamboo Phone Case', 'bamboo_phone_case', 'Funk Trunk', 6490),
	('BP4', 'Bamboo Twist Pen', 'bamboo_twist_pen', 'Funk Trunk', 1190),
	('BP5', 'Bamboo Notebook and Pen', 'bamboo_notebook_and_pen', 'Funk Trunk', 2190),
	('BP6', 'Bamboo Cutlery Set', 'bamboo_cutlery_set', 'Funk Trunk', 2990),
	('BP7', 'Bamboo Cotton Buds', 'bamboo_cotton_buds', 'Go Bamboo', 990),
	('CUP1', 'Glass Keep Cup', 'glass_keep_cup', 'Keep Cup', 4990),
	('CUP2', 'Bamboo Cup', 'bamboo_cup', 'Funk Trunk', 5990),
	('STRW1', 'Bamboo Straws', 'bamboo_straws', 'Bambu', 1690),
	('STRW2', 'Stainless Steel Metal Straws', 'stainless_steel_metal_straws', 'Globi', 1990),
	('STRW3', 'Biodegradable Paper Straws', 'biodegradable_paper_straws', 'Balloon Red', 1490),
	('STRW4', 'Reusable Silicone Straws', 'reusable_silicone_straws', 'Softy Straws', 2490),
	('STRW5', 'Reusable Glass Straws', 'reusable_glass_straws', 'Hummingbird Straws', 2990),
	('EB1', 'Organic Mesh Drawstring Bag', 'organic_mesh_drawstring_bag', 'Ecobags', 1490),
	('EB2', 'Recycled Cotton Canvas Lunch Bag', 'recycled_cotton_canvas_lunch_bag', 'Ecobags', 1490),
	('EB3', 'Organic Canvas Tote Bag', 'organic_canvas_tote_bag', 'Ecobags', 1990),
	('EB4', 'String Bag Organic Cotton Tote', 'string_bag_organic_cotton_tote', 'Ecobags', 2990);