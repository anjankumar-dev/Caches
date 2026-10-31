package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-mapping");
        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

// Get cache statistics
        Statistics stats = sessionFactory.getStatistics();

        stats.setStatisticsEnabled(true);
        stats.clear();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager1.getTransaction();
        System.out.println("---------- Starting...! ----------");


        System.out.println("---------- First Operation with entitiyManager1 ----------");
        User user = entityManager1.find(User.class, 1L);
        System.out.println(user);
//        System.out.println("---------- Second Operation with entitiyManager1 ----------");
//        User user1 = entityManager1.find(User.class, 1L);
//        System.out.println(user1);
//        System.out.println(entityManager1.contains(user1));

        entityManager1.close();

        System.out.println("---------- Same Operation with entitiyManager2 ----------");

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Before second lookup:");
        System.out.println("L2 Hits: " + stats.getSecondLevelCacheHitCount());

        User user2 = entityManager2.find(User.class, 1L);
        System.out.println(user2);

        System.out.println("After second lookup:");
        System.out.println("L2 Hits: " + stats.getSecondLevelCacheHitCount());

        System.out.println("Managed by EM2: " + entityManager2.contains(user2));

        System.out.println("---------- Ending...! ----------");


    }
}
