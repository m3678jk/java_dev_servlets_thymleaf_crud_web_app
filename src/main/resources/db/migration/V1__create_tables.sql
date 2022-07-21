CREATE TABLE developer(
id_developer bigint NOT NULL AUTO_INCREMENT,
first_name varchar(100) NOT NULL,
second_name varchar(100),
age INT,
sex varchar(10) NOT NULL,
salary INT,
primary key (id_developer)
);


CREATE TABLE project(
id_project bigint NOT NULL AUTO_INCREMENT,
name_of_project varchar(50) NOT NULL,
description varchar(1000),
start_date date,
primary key (id_project)
);

CREATE TABLE company(
id_company bigint NOT NULL AUTO_INCREMENT,
name_of_company varchar(50) NOT NULL,
address varchar(100),
primary key (id_company)
);

CREATE TABLE customer(
id_customer bigint NOT NULL AUTO_INCREMENT,
name_of_customer varchar(50) NOT NULL,
address varchar(100),
primary key (id_customer)
);

