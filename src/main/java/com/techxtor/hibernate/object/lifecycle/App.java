package com.techxtor.hibernate.object.lifecycle;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {
    public static void main(String[] args) {

        Configuration config = new Configuration().configure().addAnnotatedClass(Laptop.class);

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        session.beginTransaction();

        // commented to avoid re-entry when executing
/*
        Laptop l1 = new Laptop();
        l1.setId(103);
        l1.setBrand("Sony");
        l1.setPrice(40000);
        // l1 is in transient state -> i.e. not persisted

        session.save(l1); // persistence state -> save to db as 40000
        l1.setPrice(41000); // still in persistence state -> updates price to 41000
*/

// comment before executing or delete row with id = 105
        Laptop l1 = new Laptop();
        l1.setId(105);
        l1.setBrand("Apple");
        l1.setPrice(65000);
        // l1 is in transient state -> i.e. not persisted

        session.save(l1); // persistence state -> save to db as 15000
        l1.setPrice(75000); // still in persistence state -> updates price to 25000


/*
        session.remove(l1); //deletes from db
        session.delete(l1); //deletes from db
*/

        session.getTransaction().commit();

        session.detach(l1); // comes to detach state -> changes won't affect db
        l1.setPrice(60000);
    }

}
