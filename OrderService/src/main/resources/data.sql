INSERT INTO products (product_name, category, price, stock_quantity)
SELECT *
FROM (
    VALUES
        ('Wireless Mouse', 'Electronics', 25.99, 100),
        ('Keyboard', 'Electronics', 45.50, 60),
        ('Notebook', 'Stationery', 3.25, 500),
        ('USB-C Cable', 'Electronics', 9.99, 300),
        ('Laptop Stand', 'Accessories', 29.99, 75),
        ('Office Chair', 'Furniture', 149.99, 20),
        ('Desk Lamp', 'Furniture', 22.50, 120)
) AS v(product_name, category, price, stock_quantity)
WHERE NOT EXISTS (SELECT 1 FROM products);
