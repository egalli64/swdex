-- this script should run on postgres
--
-- psql -U postgres \i setup.sql

drop schema if exists phob cascade;
drop user if exists phob;

create user phob with password 'password';
create schema authorization phob;
