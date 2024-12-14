package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonTransformer;

/**
 * Kontroler REST API umożliwiający operacje na strukturach JSON.
 * Oferuje funkcjonalności takie jak: minimalizacja, upiększanie,
 * filtrowanie oraz porównywanie danych JSON.
 */
@RestController
@RequestMapping("/json")
public class JsonTransformerController {

    @GetMapping("/test")
    public String test() {
        return "Application is running!";
    }

    private static final Logger logger = LoggerFactory.getLogger(JsonTransformerController.class);

    /**
     * Minimalizuje JSON poprzez usunięcie zbędnych białych znaków.
     *
     * @param json JSON w formacie tekstowym przesłany w treści żądania.
     * @return Zminimalizowany JSON lub komunikat o błędzie w przypadku niepowodzenia.
     */
    @PostMapping(value = "/minify", produces = "application/json")
    public String minify(@RequestBody String json) {
        logger.info("Otrzymano żądanie dot. zminimalizowania JSON");
        logger.debug("Wejściowy JSON: {}", json);
        try {
            String result = JsonTransformer.minify(json);
            logger.info("JSON zminimalizowany pomyślnie!");
            return result;
        } catch (Exception e) {
            logger.error("Błąd przy minimalizowaniu: ", e);
            return "{\"error\": \"Nieprawidłowy format JSON\"}";
        }
    }

    /**
     * Upiększa JSON, dodając wcięcia i lepsze formatowanie.
     *
     * @param json JSON w formacie tekstowym przesłany w treści żądania.
     * @return Upiększony JSON lub komunikat o błędzie w przypadku niepowodzenia.
     */
    @PostMapping(value = "/prettify", produces = "application/json")
    public String prettify(@RequestBody String json) {
        logger.info("Otrzymano żądanie dot. upiększenia JSON");
        logger.debug("Wejściowy JSON: {}", json);
        try {
            String result = JsonTransformer.prettify(json);
            logger.info("JSON upiększony pomyślnie!");
            return result;
        } catch (Exception e) {
            logger.error("Błąd przy upiększaniu: ", e);
            return "{\"error\": \"Nieprawidłowy format JSON\"}";
        }
    }

    /**
     * Filtruje JSON, zachowując tylko wskazane właściwości.
     *
     * @param request Obiekt zawierający JSON oraz tablicę nazw właściwości do zachowania.
     * @return Przefiltrowany JSON lub komunikat o błędzie w przypadku niepowodzenia.
     */
    @PostMapping(value = "/filter/include", produces = "application/json")
    public String filterInclude(@RequestBody FilterRequest request) {
        logger.info("Otrzymano żądanie dot. filtrowania JSON (zachowaj właściwości)");
        logger.debug("Wejściowy JSON: {}, Właściwości: {}", request.getJson(), request.getProperties());
        try {
            String result = JsonTransformer.filterInclude(request.getJson(), request.getProperties());
            logger.info("JSON przefiltrowany pomyślnie (zachowano właściwości)!");
            return result;
        } catch (Exception e) {
            logger.error("Błąd przy filtrowaniu (zachowaj właściwości): ", e);
            return "{\"error\": \"Nieprawidłowy format JSON lub właściwości\"}";
        }
    }

    /**
     * Filtruje JSON, usuwając wskazane właściwości.
     *
     * @param request Obiekt zawierający JSON oraz tablicę nazw właściwości do usunięcia.
     * @return Przefiltrowany JSON lub komunikat o błędzie w przypadku niepowodzenia.
     */
    @PostMapping(value = "/filter/exclude", produces = "application/json")
    public String filterExclude(@RequestBody FilterRequest request) {
        logger.info("Otrzymano żądanie dot. filtrowania JSON (usuń właściwości)");
        logger.debug("Wejściowy JSON: {}, Właściwości: {}", request.getJson(), request.getProperties());
        try {
            String result = JsonTransformer.filterExclude(request.getJson(), request.getProperties());
            logger.info("JSON przefiltrowany pomyślnie (usunięto właściwości)!");
            return result;
        } catch (Exception e) {
            logger.error("Błąd przy filtrowaniu (usuń właściwości): ", e);
            return "{\"error\": \"Nieprawidłowy format JSON lub właściwości\"}";
        }
    }

    /**
     * Porównuje dwa JSON-y i zwraca różnice między nimi.
     *
     * @param request Obiekt zawierający dwa JSON-y do porównania.
     * @return Różnice między JSON-ami, komunikat o ich braku lub błąd w przypadku nieprawidłowego formatu danych wejściowych.
     */
    @PostMapping(value = "/compare", produces = "application/json")
    public String compare(@RequestBody CompareRequest request) {
        logger.info("Otrzymano żądanie dot. porównania JSON-ów");
        logger.debug("JSON 1: {}, JSON 2: {}", request.getJson1(), request.getJson2());
        try {
            String result = JsonTransformer.compare(request.getJson1(), request.getJson2());
            logger.info("Porównanie JSON-ów zakończone pomyślnie!");
            return result;
        } catch (Exception e) {
            logger.error("Błąd przy porównywaniu JSON-ów: ", e);
            return "{\"error\": \"Nieprawidłowy format JSON\"}";
        }
    }

    /**
     * DTO dla żądań filtracji JSON.
     */
    public static class FilterRequest {
        private String json;
        private String[] properties;

        /**
         * Zwraca JSON przesłany w żądaniu.
         *
         * @return JSON w formacie tekstowym.
         */
        public String getJson() {
            return json;
        }

        /**
         * Ustawia JSON przesłany w żądaniu.
         *
         * @param json JSON w formacie tekstowym.
         */
        public void setJson(String json) {
            this.json = json;
        }

        /**
         * Zwraca tablicę nazw właściwości do filtrowania.
         *
         * @return Tablica nazw właściwości.
         */
        public String[] getProperties() {
            return properties;
        }

        /**
         * Ustawia tablicę nazw właściwości do filtrowania.
         *
         * @param properties Tablica nazw właściwości.
         */
        public void setProperties(String[] properties) {
            this.properties = properties;
        }
    }

    /**
     * DTO dla żądań porównania JSON.
     */
    public static class CompareRequest {
        private String json1;
        private String json2;

        /**
         * Zwraca pierwszy JSON przesłany w żądaniu.
         *
         * @return Pierwszy JSON w formacie tekstowym.
         */
        public String getJson1() {
            return json1;
        }

        /**
         * Ustawia pierwszy JSON przesłany w żądaniu.
         *
         * @param json1 Pierwszy JSON w formacie tekstowym.
         */
        public void setJson1(String json1) {
            this.json1 = json1;
        }

        /**
         * Zwraca drugi JSON przesłany w żądaniu.
         *
         * @return Drugi JSON w formacie tekstowym.
         */
        public String getJson2() {
            return json2;
        }

        /**
         * Ustawia drugi JSON przesłany w żądaniu.
         *
         * @param json2 Drugi JSON w formacie tekstowym.
         */
        public void setJson2(String json2) {
            this.json2 = json2;
        }
    }
}
