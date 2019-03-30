CREATE TABLE categories (
	id BIGINT AUTO_INCREMENT,
	name VARCHAR(255),
	sequence INT,
	CONSTRAINT pk_id PRIMARY KEY (id)
) engine = innodb;


ALTER TABLE products
ADD COLUMN category_id BIGINT DEFAULT 1;


INSERT INTO categories (name,sequence) VALUES ('No category', 1);

INSERT INTO categories (name,sequence) VALUES ('Bamboo products', 2);
INSERT INTO categories (name,sequence) VALUES ('Straws', 3);
INSERT INTO categories (name,sequence) VALUES ('Coconut bowls', 4);
INSERT INTO categories (name,sequence) VALUES ('Eco bags', 5);

ALTER TABLE products
ADD CONSTRAINT fk_products_categories
FOREIGN KEY(category_id)REFERENCES categories(id);

update products join categories  on  products.category_id=categories.id
set products.category_id=(select id from categories where categories.name = 'Coconut bowls' )
  where products.name='Natural Coconut Bowl' ;

update products join categories  on  products.category_id=categories.id
 set products.category_id=(select id from categories where categories.name = 'Coconut bowls' )
 where products.name='Original Coconut Bowl';

 update products join categories  on  products.category_id=categories.id
  set products.category_id=(select id from categories where categories.name = 'Coconut bowls' )
  where products.name='Natural Coconut Bowl Set';

  update products join categories  on  products.category_id=categories.id
    set products.category_id=(select id from categories where categories.name = 'Coconut bowls' )
    where products.name='Original Coconut Bowl Set';

update products join categories  on  products.category_id=categories.id
    set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
    where products.name='Bamboo Toothbrush';

update products join categories  on  products.category_id=categories.id
    set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
    where products.name='Bamboo Water Bottle';

    update products join categories  on  products.category_id=categories.id
        set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
        where products.name='Bamboo Phone Case';

update products join categories  on  products.category_id=categories.id
        set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
        where products.name='Bamboo Twist Pen';

 update products join categories  on  products.category_id=categories.id
      set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
       where products.name='Bamboo Notebook and Pen';

  update products join categories  on  products.category_id=categories.id
       set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
        where products.name='Bamboo Cutlery Set';

update products join categories  on  products.category_id=categories.id
       set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
        where products.name='Bamboo Cotton Buds';

        update products join categories  on  products.category_id=categories.id
               set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
                where products.name='Glass Keep Cup';

update products join categories  on  products.category_id=categories.id
               set products.category_id=(select id from categories where categories.name = 'Bamboo products' )
                where products.name='Bamboo Cup';

 update products join categories  on  products.category_id=categories.id
                set products.category_id=(select id from categories where categories.name = 'Straws' )
                 where products.name='Stainless Steel Metal Straws';

 update products join categories  on  products.category_id=categories.id
                 set products.category_id=(select id from categories where categories.name = 'Straws' )
                  where products.name='Biodegradable Paper Straws';

 update products join categories  on  products.category_id=categories.id
                  set products.category_id=(select id from categories where categories.name = 'Straws' )
                   where products.name='Reusable Silicone Straws';

 update products join categories  on  products.category_id=categories.id
                   set products.category_id=(select id from categories where categories.name = 'Straws' )
                    where products.name='Reusable Glass Straws';

  update products join categories  on  products.category_id=categories.id
                     set products.category_id=(select id from categories where categories.name = 'Eco bags' )
                      where products.name='Organic Mesh Drawstring Bag';

   update products join categories  on  products.category_id=categories.id
                      set products.category_id=(select id from categories where categories.name = 'Eco bags' )
                       where products.name='Recycled Cotton Canvas Lunch Bag';

 update products join categories  on  products.category_id=categories.id
                       set products.category_id=(select id from categories where categories.name = 'Eco bags' )
                        where products.name='Organic Canvas Tote Bag';
 update products join categories  on  products.category_id=categories.id
                       set products.category_id=(select id from categories where categories.name = 'Eco bags' )
                        where products.name='String Bag Organic Cotton Tote';



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