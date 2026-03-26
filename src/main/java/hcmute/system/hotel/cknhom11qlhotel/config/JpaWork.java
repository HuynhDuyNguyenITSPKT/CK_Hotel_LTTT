package hcmute.system.hotel.cknhom11qlhotel.config;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface JpaWork<T> {
    T execute(EntityManager em) throws Exception;
}