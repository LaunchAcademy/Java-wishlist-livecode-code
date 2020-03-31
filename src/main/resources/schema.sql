CREATE TABLE products(
              id SERIAL PRIMARY KEY,
              name VARCHAR(255) NOT NULL,
              price FLOAT,
              url VARCHAR(255) NOT NULL
              );