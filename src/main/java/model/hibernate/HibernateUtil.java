package model.hibernate;

import lombok.Getter;

import model.serviceDAO.entity.*;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static HibernateUtil INSTANCE;
    private Flyway flyway;
    private PropertiesReader propertiesReader;
    @Getter
    private SessionFactory sessionFactory;

    private HibernateUtil() {
        propertiesReader = new PropertiesReader();
        flyway = Flyway
                .configure()
                .dataSource(propertiesReader.getDbUrl(), propertiesReader.getDbUser(), propertiesReader.getDbPass()).load();

        flyway.migrate();

        sessionFactory = new Configuration()
                .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Skills.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

    }

    public static HibernateUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HibernateUtil();
        }
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }

}
