package model.serviceDAO.dao;

import model.hibernate.HibernateUtil;
import model.serviceDAO.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompaniesDAO implements DAO {
    HibernateUtil util = HibernateUtil.getInstance();

    public Company getById(long id) {
        Session session = util.getSessionFactory().openSession();
        Company company = session.get(Company.class, id);
        session.close();
        return company;
    }

    public List<Company> getList() {
        Session session = util.getSessionFactory().openSession();
        List<Company> companies = session.createQuery("from Company", Company.class).list(); // important to have a Capital letter
        session.close();
        return companies;
    }

    public void delete(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Company companyToDelete = getById(id);
        if (companyToDelete!=null) {
            session.remove(companyToDelete);
        } else {
            System.out.println("ID not found");
        }
        transaction.commit();
        session.close();
    }

    public void insert(Company company) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(company);
        transaction.commit();
        session.close();
    }

    public void update(long id, Company company) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String nameOfCompany = company.getNameOfCompany();
        String address = company.getAddress();
        Company existing = session.get(Company.class, id);
        existing.setNameOfCompany(nameOfCompany);
        existing.setAddress(address);
        session.merge(existing);
        transaction.commit();
        session.close();
    }
}
