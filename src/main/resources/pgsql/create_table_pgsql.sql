create table q_customer(
  id serial primary key,
  name varchar(32)
);

create table q_order(
  id int primary key,
  name varchar(32),
  price int,
  customer_id int
);
alter table q_order add constraint fk_q_order_q_customer foreign key(customer_id) references q_customer(id);