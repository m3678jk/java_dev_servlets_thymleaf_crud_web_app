package model.serviceDAO.dao;

;
import model.hibernate.HibernateUtil;
import model.serviceDAO.entity.Developer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class DeveloperDAO implements DAO{
   private HibernateUtil util = HibernateUtil.getInstance();

    public Developer getById(long id) {
        Session session = util.getSessionFactory().openSession();
        Developer developer = session.get(Developer.class, id);
        session.close();
        return developer;
    }

    public List<Developer> getList() {
        Session session = util.getSessionFactory().openSession();
        List<Developer> developers = session.createQuery("from Developer", Developer.class).list(); // important to have a Capital letter
        session.close();
        return developers;
    }

    public void delete(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Developer developerToDelete = getById(id);
        if (developerToDelete!=null) {
            session.remove(developerToDelete);
        } else {
            System.out.println("ID not found");
        }
        transaction.commit();
        session.close();
    }

    public void inset(Developer developer) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        session.close();
    }

    public void update(long id, Developer developer) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        int age = developer.getAge();
        String firstName = developer.getFirstName();
        String secondName = developer.getSecondName();
        int salary = developer.getSalary();
        Developer.Sex sex = developer.getSex();

        Developer existingDeveloper = session.get(Developer.class, id);
        if (existingDeveloper!=null) {
            existingDeveloper.setFirstName(firstName);
            existingDeveloper.setSecondName(secondName);
            existingDeveloper.setAge(age);
            existingDeveloper.setSex(sex);
            existingDeveloper.setSalary(salary);
            session.merge(existingDeveloper);
        } else {
            System.out.println("ID incorrect");
        }
        transaction.commit();
        session.close();
    }

}



