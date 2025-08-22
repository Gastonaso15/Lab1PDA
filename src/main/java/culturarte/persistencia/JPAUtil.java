package culturarte.persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;

public class JPAUtil {
    public static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("culturartePU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
