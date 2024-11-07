create table if not exists products (
    sku varchar not null primary key,
    description varchar not null,
    original_price numeric(19, 2),
    category_id varchar not null,
    constraint fk_products_on_category foreign key (category_id) references categories(id)
);
