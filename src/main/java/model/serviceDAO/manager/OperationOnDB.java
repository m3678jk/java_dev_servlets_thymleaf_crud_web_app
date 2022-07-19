package model.serviceDAO.manager;

import model.hibernate.HibernateUtil;
import model.serviceDAO.entity.Developer;
import model.serviceDAO.entity.Project;
import model.serviceDAO.entity.Skills;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OperationOnDB {
    private HibernateUtil util = HibernateUtil.getInstance();

    public int getSumOfSalary(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query<Project> query =
                // from table Project as alias p join "field" developers (set) where project's id is ?
                session.createQuery("from Project as p inner join fetch p.developers as d where p.id = : id",
                                Project.class)
                        .setParameter("id", id);
        List<Project> list = query.list();
        int sum = list.stream()
                .flatMap(o -> o.getDevelopers().stream())
                .mapToInt(o -> o.getSalary())
                .sum();
        transaction.commit();
        session.close();

        return sum;
    }

    public List<Developer> getListOfDevelopers(int id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query<Project> query =
                session.createQuery("from Project as p inner join fetch p.developers as d where p.id = : id",
                                Project.class)
                        .setParameter("id", id);
        List<Project> list = query.list();
        List<Developer> result = list.stream()
                .flatMap(o -> o.getDevelopers().stream())
                .collect(Collectors.toList());
        transaction.commit();
        session.close();

        return result;
    }

    public List<Developer> getListOfJavaDev() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query<Skills> query = session.createQuery("from Skills as s inner join fetch s.developer",
                Skills.class);
        List<Skills> list = query.list();
        List<Developer> result = list.stream()
                .filter(it -> it.getTechnology() == Skills.Technology.JAVA)
                .map(o -> o.getDeveloper())
                .collect(Collectors.toList());
        transaction.commit();
        session.close();

        return result;
    }

    public List<Developer> getListMidDev() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query<Skills> query = session.createQuery("from Skills as s inner join fetch s.developer",
                Skills.class);
        List<Skills> list = query.list();
        List<Developer> result = list.stream()
                .filter(it -> it.getLevelOfPosition().equals("Middle"))
                .map(o -> o.getDeveloper())
                .collect(Collectors.toList());
        transaction.commit();
        session.close();

        return result;

    }

    public List<List<String>> getListOfProject2() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<List<String>> result = new ArrayList<>();
        Query<Project> query = session.createQuery("select p from Project as p inner join fetch p.developers as d",
                Project.class);
        List<Project> list1 = query.list();
        for (Project l : list1) {
           result.add(List.of(String.valueOf(l.getId()), String.valueOf(l.getDate()),l.getNameOfProject(),
                   l.getDescription(), String.valueOf(l.getDevelopers().stream().count())));
        }

        transaction.commit();
        session.close();
        return result;

    }

}
