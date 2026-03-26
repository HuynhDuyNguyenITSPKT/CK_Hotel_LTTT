package hcmute.system.hotel.cknhom11qlhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        // Spring Boot 4 packages
        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration",
        "org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration",
        "org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration",
        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration",
        // Spring Boot 3 packages (kept for compatibility)
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration",
        "org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration",
        "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
public class CkNhom11QlhotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(CkNhom11QlhotelApplication.class, args);
    }

}
