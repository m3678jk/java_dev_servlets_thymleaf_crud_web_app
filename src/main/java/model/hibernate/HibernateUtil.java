package model.hibernate;

import lombok.Getter;

import model.serviceDAO.entity.*;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateUtil {
    private static final HibernateUtil INSTANCE;
    private Flyway flyway;
    @Getter
    private SessionFactory sessionFactory;
    static {
        INSTANCE = new HibernateUtil();

    }

    private HibernateUtil() {
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/home_work_3";
        String dbUser = "root";
        String dbPass = "1234";

        flyway = Flyway
                .configure()
                .dataSource(dbUrl, dbUser, dbPass).load();

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
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }

}
