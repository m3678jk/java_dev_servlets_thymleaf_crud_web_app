package model.serviceDAO.dao;

import model.hibernate.HibernateUtil;
import model.serviceDAO.entity.Developer;
import model.serviceDAO.entity.Skills;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class SkillsDAO implements DAO{
    HibernateUtil util = HibernateUtil.getInstance();

    public Skills getById(long id) {
        Session session = util.getSessionFactory().openSession();
        Skills skills = session.get(Skills.class, id);
        session.close();
        return skills;
    }

    public List<Skills> getList() {
        Session session = util.getSessionFactory().openSession();
        List<Skills> skills = session.createQuery("from Skills as s inner join fetch s.developer", Skills.class).list();
        session.close();
        return skills;
    }

    public void delete(long skillsId) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Skills skillsToDelete = session.find(Skills.class, skillsId);
        if (skillsToDelete!=null) {
            session.remove(skillsToDelete);
        } else {
            System.out.println("ID not found");
        }
        transaction.commit();
        session.close();
    }

    public void insert(long developerId, Skills skills) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        DeveloperDAO developerDAO = new DeveloperDAO();
        Developer developer = developerDAO.getById(developerId);
        List<Skills> collect = developer.getSkills().stream().filter(it -> it.equals(skills)).collect(Collectors.toList());
        if (developer != null) {
            developer.addSkills(skills);
            session.merge(developer);
        } else {
            System.out.println("ID incorrect");
        }
        transaction.commit();
        session.close();
    }

    public void update(long skillsId, Skills skills) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Skills.Technology technology = skills.getTechnology();
        String levelOfPosition = skills.getLevelOfPosition();

        Skills existingSkills = getById(skillsId);

        if (existingSkills != null) {
            existingSkills.setTechnology(technology);
            existingSkills.setLevelOfPosition(levelOfPosition);
            session.merge(existingSkills);

        } else {
            System.out.println("ID incorrect");
        }
        transaction.commit();
        session.close();
    }

}