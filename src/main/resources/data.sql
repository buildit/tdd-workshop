DROP TABLE product IF EXISTS;

CREATE TABLE product (
  product_id INT NOT NULL IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  manufacturer  VARCHAR(30),
  price DOUBLE,
  category VARCHAR(30),
  is_returnable BOOLEAN,
  num_days_till_returnable INTEGER,
  stock_quantity INTEGER
);

CREATE TABLE customer (
	customer_id INTEGER PRIMARY KEY,
	name VARCHAR(30),
	phone VARCHAR(10),
	address VARCHAR(100),
	city VARCHAR(30),
	state VARCHAR(30),
	pincode INTEGER
);

CREATE TABLE cart (
	cart_id INTEGER PRIMARY KEY,
	customer_id INTEGER,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE cartitem (
	cart_item_id INTEGER PRIMARY KEY,
	product_id INTEGER,
	product_qty INTEGER,
	cart_id INTEGER,
	FOREIGN KEY (product_id) REFERENCES product(product_id),
	FOREIGN KEY (cart_id) REFERENCES cart(cart_id)
);

INSERT INTO product VALUES (1, 'iPhone 11 Pro', 'Apple', 160000.00, 'Mobile Phones', false, 0, 15);
