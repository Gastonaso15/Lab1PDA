package culturarte.persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT;

    static {
        if (System.getenv("DOCKER") != null) {
            PERSISTENCE_UNIT = "culturartePU-docker";
        } else {
            PERSISTENCE_UNIT = "culturartePU";
        }
    }
    public static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
