create schema if not exists spring6jdbc3;

set schema spring6jdbc3;

create table if not exists speaker (id IDENTITY Not null primary key,
name varchar (100) not null);