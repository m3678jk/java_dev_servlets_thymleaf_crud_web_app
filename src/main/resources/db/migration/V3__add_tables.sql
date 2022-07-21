CREATE TABLE project_company(
    projects_id_project bigint NOT NULL,
    companies_id_company bigint NOT NULL,
    PRIMARY KEY (projects_id_project,companies_id_company),
    FOREIGN KEY (projects_id_project) REFERENCES project(id_project),
    FOREIGN KEY(companies_id_company) REFERENCES company(id_company)
);

CREATE TABLE project_customer(
    projects_id_project bigint NOT NULL,
    customers_id_customer bigint NOT NULL,
    PRIMARY KEY (projects_id_project,customers_id_customer),
    FOREIGN KEY (projects_id_project) REFERENCES project(id_project),
    FOREIGN KEY(customers_id_customer) REFERENCES customer(id_customer)
);



CREATE TABLE project_developer(
    projects_id_project bigint NOT NULL,
    developers_id_developer bigint NOT NULL,
    PRIMARY KEY (projects_id_project,developers_id_developer),
    FOREIGN KEY (projects_id_project) REFERENCES project(id_project),
    FOREIGN KEY(developers_id_developer) REFERENCES developer(id_developer)
);

CREATE TABLE skills(
    id_skills bigint NOT NULL AUTO_INCREMENT,
    developer_id_developer bigint NOT NULL ,
    technology varchar(30) NOT NULL,
    level_of_position varchar(30) NOT NULL,
    primary key (id_skills),
    FOREIGN KEY (developer_id_developer) REFERENCES developer(id_developer)
);
