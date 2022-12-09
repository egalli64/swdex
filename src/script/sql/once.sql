-- this script should run on postgres
--
-- psql -U postgres \i setup.sql

drop schema if exists swdex cascade;
drop user if exists swdex;

create user swdex with password 'password';
create schema authorization swdex;
