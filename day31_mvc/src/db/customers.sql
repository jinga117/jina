select * from customers;

create table customers (
	code number(4) primary key,
	name  varchar2(30) not null,
	email varchar2(30),
	phone varchar2(35)
);

insert into customers values();

select * from gift;