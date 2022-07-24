CREATE TABLE project_company(
    projects_id bigint NOT NULL,
    companies_id bigint NOT NULL,
    PRIMARY KEY (projects_id, companies_id),
    FOREIGN KEY (projects_id) REFERENCES project(id),
    FOREIGN KEY(companies_id) REFERENCES company(id)
);

CREATE TABLE project_customer(
    projects_id bigint NOT NULL,
    customers_id bigint NOT NULL,
    PRIMARY KEY (projects_id,customers_id),
    FOREIGN KEY (projects_id) REFERENCES project(id),
    FOREIGN KEY(customers_id) REFERENCES customer(id)
);



CREATE TABLE project_developer(
    projects_id bigint NOT NULL,
    developers_id bigint NOT NULL,
    PRIMARY KEY (projects_id,developers_id),
    FOREIGN KEY (projects_id) REFERENCES project(id),
    FOREIGN KEY(developers_id) REFERENCES developer(id)
);

CREATE TABLE skills(
    id bigint NOT NULL AUTO_INCREMENT,
    developer_id bigint NOT NULL ,
    technology varchar(30) NOT NULL,
    level_of_position varchar(30) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (developer_id) REFERENCES developer(id)
);
