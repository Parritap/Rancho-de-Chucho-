package co.edu.uniquindio.pos_resturant_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan("co.edu.uniquindio.pos_resturant_app.entities")
public class PosResturantAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosResturantAppApplication.class, args);
    }

}
