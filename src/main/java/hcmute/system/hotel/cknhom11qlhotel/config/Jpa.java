package hcmute.system.hotel.cknhom11qlhotel.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class Jpa {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("hotel11");

    private Jpa() {}

    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    public static void shutdown() {
        EMF.close();
    }
}
