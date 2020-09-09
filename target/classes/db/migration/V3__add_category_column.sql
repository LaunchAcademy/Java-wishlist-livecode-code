ALTER TABLE products
ADD COLUMN category_id int REFERENCES categories(id);