package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.JsonTransformer;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class JsonTransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonTransformerApplication.class, args);
    }
}
