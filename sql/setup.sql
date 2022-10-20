-- this script should run on phob
-- pre requisite: once.sql
--
-- psql -U phob -d postgres
-- phob=> \i setup.sql

-- cleanup
drop table if exists contact;

create table contact (
    contact_id serial primary key,
    name varchar(20) not null,
    phone varchar(20) not null
);

begin;
    insert into contact (name, phone) values ('Tom', '+44 981 459 1244');
    insert into contact (name, phone) values ('Bob', '+44 981 459 1244');
    insert into contact (name, phone) values ('Kim', '123 44 3443');
    insert into contact (name, phone) values ('Zoe', '994 34 3434');
commit;
