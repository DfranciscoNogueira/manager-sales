package br.com.manager.util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private JPAUtil() {
        // Construtor privado para evitar instanciamento
    }

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("postgres-unit");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close() {
        factory.close();
    }

}