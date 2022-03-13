package com.techxtor.hibernate.hql.getvsload;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {

        Configuration config = new Configuration().configure().addAnnotatedClass(Laptop.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        session.beginTransaction();

        // .get() fires query and gives object
        Laptop lapGet = session.get(Laptop.class, 103);
        System.out.println(lapGet);

        // .load() files query only when asked for the object
        // -> try with commenting syso and see the difference
        // -> gives proxy object
        Laptop lapLoad = session.load(Laptop.class, 104);
//        System.out.println(lapLoad);

        session.getTransaction().commit();
    }

}

/**
 * .get() gives null
 * .load() gives ObjectNotFoundException
 */
