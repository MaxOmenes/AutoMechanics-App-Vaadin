create database automechanics;

create table if not exists clients(
    id bigint primary key,
    name varchar(255),
    surname varchar(255),
    patronymic varchar(255),
    phone varchar(255)
);

create table if not exists cars(
    vin varchar(255) primary key,
    brand varchar(255),
    model varchar(255),
    year int,
    number varchar(255),
    client_id uuid references clients(id)
);

create table if not exists orders(
    id uuid primary key,
    date date,
    status varchar(255),
    description varchar,
    price float,
    car_vin varchar(255) references cars(vin)
);

create table if not exists shops(
    id uuid primary key,
    name varchar(255),
    address varchar(255),
    phone varchar(255)
);

create table if not exists parts(
    id uuid primary key,
    name varchar(255),
    price float,
    date date,
    shop varchar(255),
    order_id uuid references orders(id)
);

alter table parts rename column shop to shop_id;
alter table parts alter column shop_id type uuid using shop_id::uuid;
alter table parts add constraint fk_shop_id foreign key (shop_id) references shops(id);
alter table parts drop column shop_id;
alter table parts drop column date;

create table if not exists delivery_notes(
    id uuid primary key,
    date date,
    shop_id uuid references shops(id)
);

alter table parts add column delivery_note_id uuid;

create table if not exists services(
    id uuid primary key,
    name varchar(255),
    price float
);

create table if not exists order_services(
    order_id uuid references orders(id),
    service_id uuid references services(id)
);

create table if not exists workers(
    id uuid primary key,
    name varchar(255),
    surname varchar(255),
    patronymic varchar(255),
    phone varchar(255),
    birthday date,
    job varchar(255),
    salary float,
    experience int,
    experience_bonus float,
    operating_mode varchar(255)
);

create table if not exists order_workers(
    order_id uuid references orders(id),
    worker_id uuid references workers(id)
);

create table if not exists worker_services(
    worker_id uuid references workers(id),
    service_id uuid references services(id)
);










