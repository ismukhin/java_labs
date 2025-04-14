package com.example;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Database {
    SessionFactory sf = HibernateSF.getSessionFactory();

    public Database() {}
    
    public ArrayList<UserInDB> getAllUsers() {
        ArrayList<UserInDB> res = new ArrayList<>();
        
        List<UserInDB> list = (List<UserInDB>)sf.openSession().createQuery("From UserInDB").list();
        res.addAll(list);
        return res;
    }

    public void addUser(UserInDB user) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(user);
        tx.commit();
        session.close();
    }

    public void incrWins(String nameUser) {
        Query query = sf.openSession().createQuery("from UserInDB where name = :param");
        query.setParameter("param", nameUser);
        List list = query.list();
        UserInDB user = (UserInDB)list.get(0);
        user.wins++;

        Session session = sf.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }
}
