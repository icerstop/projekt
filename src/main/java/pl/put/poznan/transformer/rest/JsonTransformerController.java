package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.*;
import com.fasterxml.jackson.databind.JsonNode;

import static pl.put.poznan.transformer.logic.BaseJsonProcessor.objectMapper;

/**
 * Kontroler REST API umożliwiający operacje na strukturach JSON.
 */
@RestController
@RequestMapping("/json")
public class JsonTransformerController {

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
        try {
            JsonProcessor processor = new Minify(new BaseJsonProcessor());
            return processor.process(json);
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
        try {
            JsonProcessor processor = new Prettify(new BaseJsonProcessor());
            return processor.process(json);
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
        try {
            JsonProcessor processor = new FilterInclude(new BaseJsonProcessor(), request.getProperties());
            return processor.process(request.getJson());
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
        try {
            JsonProcessor processor = new FilterExclude(new BaseJsonProcessor(), request.getProperties());
            return processor.process(request.getJson());
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
        try {
            JsonProcessor processor = new Compare(request.getJson2());
            String json1String = objectMapper.writeValueAsString(request.getJson1());
            return processor.process(json1String);
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

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String[] getProperties() {
            return properties;
        }

        public void setProperties(String[] properties) {
            this.properties = properties;
        }
    }

    /**
     * DTO dla żądań porównania JSON.
     */
    public static class CompareRequest {
        private JsonNode json1;
        private JsonNode json2;

        public JsonNode getJson1() {
            return json1;
        }

        public void setJson1(JsonNode json1) {
            this.json1 = json1;
        }

        public JsonNode getJson2() {
            return json2;
        }

        public void setJson2(JsonNode json2) {
            this.json2 = json2;
        }
    }
}