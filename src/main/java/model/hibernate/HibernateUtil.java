package model.hibernate;

import lombok.Getter;

import model.serviceDAO.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateUtil {
    private static final HibernateUtil INSTANCE;
    @Getter
    private SessionFactory sessionFactory;
    static {
        INSTANCE = new HibernateUtil();
    }


    private HibernateUtil() {
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
