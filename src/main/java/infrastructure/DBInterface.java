package infrastructure;

import ORM.HibernateUtil;
import entity.UserDBO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DBInterface {
    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;


    public DBInterface() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private void openSessionAndTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private void closeSessionAndTransaction() {
        transaction.commit();
        session.close();
    }

    public UserDBO findById(Long searchUserId) {
        openSessionAndTransaction();
        UserDBO result;
        Query<UserDBO> query = session.createQuery("select b from UserDBO b where b.id = :id",
                UserDBO.class);
        query.setParameter("id", searchUserId);
        result = query.uniqueResult();
        closeSessionAndTransaction();
        return result;
    }

    public void saveUserDB(UserDBO userDataBase) {
        openSessionAndTransaction();
        session.persist(userDataBase);
        closeSessionAndTransaction();
    }

    public void updateUserDB(UserDBO userDataBase) {
        openSessionAndTransaction();
        session.merge(userDataBase);
        closeSessionAndTransaction();
    }

    public List<UserDBO> getAllUserDBO() {
        openSessionAndTransaction();
        List<UserDBO> result;
        result = new ArrayList<>(session.createQuery("select user from users", UserDBO.class).getResultList());
        closeSessionAndTransaction();
        return result;
    }
}
