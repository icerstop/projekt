package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji JSON Transformer.
 * <p>
 * Odpowiada za uruchamianie aplikacji i konfigurację kontekstu Spring Boot.
 * </p>
 * <p>
 * Aplikacja umożliwia operacje na danych JSON, takie jak:
 * minimalizacja, upiększanie, filtrowanie i porównywanie.
 * </p>
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class JsonTransformerApplication {
    

    /**
     * Główna metoda aplikacji.
     * <p>
     * Inicjalizuje kontekst Spring Boot i uruchamia aplikację.
     * </p>
     *
     * @param args Argumenty wiersza poleceń przekazywane do aplikacji.
     */
    public static void main(String[] args) {
        SpringApplication.run(JsonTransformerApplication.class, args);
    }
}
