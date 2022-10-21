-- this script should run on swdex
-- pre requisite: once.sql
--
-- psql -U swdex -d postgres
-- swdex=> \i setup.sql

-- Phone Book
-- cleanup
drop table if exists contact;

create table contact (
    contact_id serial primary key,
    name varchar(20) not null,
    phone varchar(20) not null
);

begin;
    insert into contact (name, phone) values
   		('Tom', '+44 981 459 1244'),
   		('Bob', '+44 981 459 1244'),
   		('Kim', '123 44 3443'),
   		('Zoe', '994 34 3434');
commit;

-- Restaurant Ordering System
-- cleanup
drop table if exists menu;
drop table if exists category;

create table category (
    category_id serial primary key,
    name varchar(20) not null,
    description varchar(200)
);

begin;
    insert into category (name, description) values
   		('Antipasti', 'Per stuzzicare l''appetito'),
   		('Pizze', 'Leggere e digeribili'),
   		('Dolci', 'Nostra produzione del giorno'),
   		('Bevande', null);
commit;

create table menu (
    menu_id serial primary key,
    name varchar(20) not null,
    description varchar(200),
    price numeric(4,2) not null check(price > 0),
    category_id integer,
    
	constraint menu_category_fk foreign key (category_id) references category (category_id)
);

do $$ declare
    v_appetizer category.category_id%type;
    v_pizza category.category_id%type;
    v_dessert category.category_id%type;
    v_drink category.category_id%type;
begin
    select category_id into v_appetizer from category where name = 'Antipasti';
    select category_id into v_pizza from category where name = 'Pizze';
    select category_id into v_dessert from category where name = 'Dolci';
    select category_id into v_drink from category where name = 'Bevande';

    insert into menu (name, description, price, category_id) values
   		('Bruschetta', 'Pomodoro, stracciatella, origano, olio EVO', 2.0, v_appetizer),
   		('Pizzette fritte', 'Tre pezzi', 3.0,  v_appetizer),
   		('Burratina', '75 g.', 3.0, v_appetizer),
   		('Margherita', 'rossa, fiordilatte, basilico', 6.0, v_pizza),
   		('Bufala', 'bianca, bufala, basilico', 9.50, v_pizza),
   		('Carpaccio di mare', 'rossa, tonno, salmone, spada, rucola', 13.0, v_pizza),
   		('Tiramisù', 'savoiardi, mascarpone, cacao', 6.0, v_dessert),
   		('Cioccolato e pere', 'fetta di torta', 6.0, v_dessert),
   		('Acqua naturale', '75 cl. microfiltrata', 1.0, v_drink),
   		('Birra chiara', '50 cl. alla spina, nostra produzione', 5.0, v_drink);
    commit;
end $$;
