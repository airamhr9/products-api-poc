create table if not exists categories_discounts (
    category_id varchar not null,
    discount_id varchar not null,
    constraint pk_categories_discounts primary key (category_id, discount_id),
    constraint fk_categories_discounts_on_discounts foreign key (discount_id) references discounts(id),
    constraint fk_categories_discounts_on_categories foreign key (category_id) references categories(id)
);
