
insert into developers (firstName, secondName, age, sex,salary) values 
("Lee", "Lui", 26, "unknown", 2000),
("Jan", "Kowalski", 30, "male",5000),
("Joanna", "Kielbasa", 30, "female", 4700),
("Kamil", "Nowak", 28, "male",4500),
("Anna", "Ziemba", 39, "female", 4600),
("Mateusz", "Lesinski", 48, "male",5200)
;

insert into skills(id_developer, technology, levelOfPosition) values
(1, "Java", "Junior"),
(3, "C_PLUS_PLUS", "Senior"),
(2, "C_SHARP", "Middle"),
(4, "JS", "Middle"),
(5, "C_PLUS_PLUS", "Middle"),
(6, "Java", "Senior"),
(1, "JS", "Junior"),
(3, "Java", "Senior"),
(2, "C_PLUS_PLUS", "Middle"),
(3, "JS", "Senior"),
(4, "Java", "Middle"),
(6, "C_PLUS_PLUS", "Senior")
;

insert into projects (name_of_project, description, start_date) values
("warehouse", "organizing stock in warehouse", '2021-07-03'),
("accountant application", "application to count of workhours",'2020-05-06'),
("demand forecast" , "anylize of market", '2022-01-09'),
("HR app", "app for HR department",'2021-10-02')
;

insert into project_developer (id_project, id_developer) values
(1,3),
(2,3),
(3,1),
(4,2),
(2,1),
(2,2),
(3,3),
(2,5),
(1,6),
(4,5)
;


insert into companies (name_of_company, address) values
("Capgemini", "France, Paris"),
("BNY Mellon" , "New York"),
("Opera", "Wroclaw")
;

insert into customers(name_of_customer, address) values
("Kovolis", "Zleby"),
("ZF", "Lagenhagen"),
("Clean Logistoc", "Stockach"),
("RDW", "Regensbur")
;
insert into customer_project (id_customer, id_project) values
(3,1),
(2,2),
(2,3),
(3,4)
;

insert into company_project (id_company, id_project) values
(2,1),
(1,2),
(3,3),
(2,4)
;
