create table if not exists discounts (
    id varchar not null primary key,
    name varchar not null,
    percent integer not null,
    sku varchar,
    constraint fk_discounts_on_products foreign key (sku) references products(sku)
);
