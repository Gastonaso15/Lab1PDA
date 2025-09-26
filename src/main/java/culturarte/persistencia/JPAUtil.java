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

    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    public static EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    //---Comentarios Explicativos de como funciona la Persitencia--
    //public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    // Crea UNA SOLA fábrica de EntityManager para toda la app.
    // Lee META-INF/persistence.xml y carga el PU indicado.
    // Es caro de crear y es thread-safe (se puede usar desde varios hilos al mismo tiempo sin romperse),por eso es static final (singleton).

    //public static EntityManager getEntityManager(){return emf.createEntityManager();}
    // Devuelve un EntityManager NUEVO cada vez.
    // El EntityManager es una "sesión" liviana con la BD (NO es thread-safe).
    // Se usa por operación / por request y SIEMPRE hay que cerrarlo con close().
}
