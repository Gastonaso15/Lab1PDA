package culturarte.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"culturarte.api", "culturarte.logica"})
public class CulturarteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CulturarteApiApplication.class, args);
    }
}
