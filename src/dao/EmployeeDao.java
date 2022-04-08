package dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.Book;
import model.Employee;
import model.Transaction;
import views.ErrorDialog;

public class EmployeeDao {

	public Employee getEmployeeById(Integer Id) {
		SessionFactory factory = new InitialiseSFHibernate().getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		try {
			Employee employee = (Employee) session.get(Employee.class, Id);
			return employee;
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}

		finally {
			session.close();
		}
	}

	public Employee verifyIdentity(String username, String password) {
		SessionFactory factory = new InitialiseSFHibernate().getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		try {
			Query query2 = session.createQuery("from Employee where username = :userName AND password = :passWord");
			query2.setParameter("userName", username);
			query2.setParameter("passWord", password);
			List<Employee> resultList = query2.list();
			if (resultList == null || resultList.isEmpty())
				return null;
			return resultList.get(0);
		} catch (Exception e) {
			if (session.getTransaction() == null)
				session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

}
