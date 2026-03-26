package hcmute.system.hotel.cknhom11qlhotel.init;


import hcmute.system.hotel.cknhom11qlhotel.service.SeedDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {

    private final SeedDataService seedDataService;

    public AppInitializer(SeedDataService seedDataService) {
        this.seedDataService = seedDataService;
    }

    @Override
    public void run(String... args) {
        seedDataService.seedDefaultUsers();
    }
}
