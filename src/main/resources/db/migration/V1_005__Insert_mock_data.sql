-- Insert categories
insert into categories (id, name) values
('cat1', 'Electronics'),
('cat2', 'Furniture'),
('cat3', 'Books'),
('cat4', 'Toys'),
('cat5', 'Clothing')
on conflict do nothing;

-- Insert products
insert into products (sku, description, original_price, category_id) values
('prod1', 'Smartphone', 599.99, 'cat1'),
('prod2', 'Laptop', 899.99, 'cat1'),
('prod3', 'Bluetooth Speaker', 49.99, 'cat1'),
('prod4', 'Bookshelf', 120.00, 'cat2'),
('prod5', 'Office Chair', 199.99, 'cat2'),
('prod6', 'Novel - Fiction', 15.99, 'cat3'),
('prod7', 'Novel - Non-Fiction', 18.99, 'cat3'),
('prod8', 'Children’s Book', 8.99, 'cat3'),
('prod9', 'Action Figure', 25.99, 'cat4'),
('prod10', 'Puzzle Game', 14.99, 'cat4'),
('prod11', 'T-shirt', 12.99, 'cat5'),
('prod12', 'Jeans', 39.99, 'cat5'),
('prod13', 'Sneakers', 59.99, 'cat5'),
('prod14', 'Dress', 29.99, 'cat5'),
('prod15', 'Jacket', 79.99, 'cat5')
on conflict do nothing;

-- Insert discounts without specifying SKU for category-wide discounts
INSERT INTO discounts (id, name, percent, sku) VALUES
('disc1', 'New Year Sale', 10, NULL),       -- Category-wide discount for Electronics
('disc2', 'Clearance', 15, 'prod5'),        -- Specific product discount for Office Chair
('disc3', 'Holiday Special', 20, NULL),     -- Category-wide discount for Books
('disc4', 'Back to School', 5, NULL),       -- Category-wide discount for Toys
('disc5', 'Seasonal Discount', 25, 'prod12')
on conflict do nothing;  -- Specific product discount for Jeans

-- Insert categories_discounts (associating discounts with categories)
insert into categories_discounts (discount_id, category_id) values
('disc1', 'cat1'),  -- New Year Sale on Electronics (category-wide)
('disc3', 'cat3'),  -- Holiday Special on Books (category-wide)
('disc4', 'cat4')
on conflict do nothing;  -- Back to School on Toys (category-wide)
