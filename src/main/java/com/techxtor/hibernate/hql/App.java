package com.techxtor.hibernate.hql;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        Configuration cnfg = new Configuration().configure().addAnnotatedClass(Student.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(cnfg.getProperties()).build();

        SessionFactory sf = cnfg.buildSessionFactory(reg);
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        // create
/*
        Student s1 = new Student();
        s1.setRollno(1);
        s1.setName("Manish");
        s1.setMarks(100);
        session.save(s1);
*/

        // ========== read *
        Query q1 = session.createQuery("from Student");

        List<Student> studentList = q1.list();

        for (Student student : studentList) {
            System.out.println(student);
        }

        // ========== read unique with where clause
        Query q2 = session.createQuery("from Student where marks = 40");

        Student student = (Student) q2.uniqueResult();

        System.out.println(student);

        // ========== read unique with column name and where clause
        Query q3 = session.createQuery("select rollno, name, marks from Student where rollno = 6");

        Object[] studentObj = (Object[]) q3.uniqueResult();
//        System.out.println(studentObj);

        for (Object o : studentObj) {
            System.out.println(o);
        }

        // ========== read with column name
        Query q4 = session.createQuery("select rollno, name, marks from Student");

        List<Object[]> studentObjList = (List<Object[]>) q4.list();

        for (Object[] obj : studentObjList) {
            for (Object o : obj) {
                System.out.println(o);
            }
        }

        // ========== read with column name alias
        Query q5 = session.createQuery("select rollno, name, marks from Student s where s.marks>40");

        List<Object[]> studentObjList1 = (List<Object[]>) q5.list();

        for (Object[] obj : studentObjList1) {
            for (Object o : obj) {
                System.out.println(o);
            }
        }

        // ========== read unique with column name alias
        Query q6 = session.createQuery("select sum(marks) from Student s where s.marks > 40");

        long totalMarks = (long) q6.uniqueResult();

        System.out.println(totalMarks);

        // ========== read unique with column name alias and params
        int queryMarks = 40;

        Query q7 = session.createQuery("select sum(marks) from Student s where s.marks > :queryMarks");
        q7.setParameter("queryMarks", queryMarks);

        long totalMark = (long) q7.uniqueResult();

        System.out.println(totalMark);

        // ========== sql query - native query
        SQLQuery query = session.createSQLQuery("select * from student where marks>40");
        query.addEntity(Student.class);

        List<Student> studentListSQLQuery = query.list();

        for (Student students : studentListSQLQuery) {
            System.out.println(students);
        }

        // ========== sql query - native query
        SQLQuery query1 = session.createSQLQuery("select marks, name from student where marks>40");
        query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        List results = query1.list();

        for (Object o : results) {
            Map map = (Map) o;
            System.out.println(map.get("name") + " " + map.get("marks"));
        }

        tx.commit(); // needed for dml
    }
}
