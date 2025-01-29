package pl.put.poznan.transformer.testing.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasa testowa dla procesorów JSON w aplikacji JsonTransformer.
 * Testuje różne funkcjonalności, takie jak porównywanie, filtrowanie, minimalizacja i upiększanie JSON.
 */
public class JsonTransformerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Testuje funkcję porównywania dwóch obiektów JSON.
     * Sprawdza, czy różnice pomiędzy nimi są prawidłowo wykrywane i raportowane.
     *
     * @throws Exception w przypadku problemów z przetwarzaniem JSON.
     */
    @Test
    public void testCompare() throws Exception {
        String json1 = """
                {
                    "name": "Alice",
                    "age": 25,
                    "location": "New York"
                }
                """;

        String json2 = """
                {
                    "name": "Alice",
                    "age": 30,
                    "location": "New York"
                }
                """;

        Compare processor = new Compare(objectMapper.readTree(json2));
        String result = processor.process(json1);

        String expectedDifference = """
                Różnica w: /age
                Oczekiwane: 25
                Właściwe: 30
                
                """;

        assertEquals(expectedDifference, result);
    }
    /**
     * Testuje funkcję filtrowania JSON, aby usunąć określone właściwości.
     * Sprawdza, czy wynikowy JSON nie zawiera wskazanych kluczy.
     *
     * @throws Exception w przypadku problemów z przetwarzaniem JSON.
     */
    @Test
    public void testFilterExclude() throws Exception {
        String inputJson = """
                {
                    "name": "Alice",
                    "age": 25,
                    "location": "New York"
                }
                """;

        String[] propertiesToExclude = {"age"};
        JsonProcessor processor = new FilterExclude(new BaseJsonProcessor(), propertiesToExclude);
        String result = processor.process(inputJson);

        String expectedOutput = """
                {
                    "name": "Alice",
                    "location": "New York"
                }
                """;

        assertEquals(objectMapper.readTree(expectedOutput), objectMapper.readTree(result));
    }
    /**
     * Testuje funkcję filtrowania JSON, aby zachować tylko określone właściwości.
     * Sprawdza, czy wynikowy JSON zawiera jedynie wskazane klucze.
     *
     * @throws Exception w przypadku problemów z przetwarzaniem JSON.
     */
    @Test
    public void testFilterInclude() throws Exception {
        String inputJson = """
                {
                    "name": "Alice",
                    "age": 25,
                    "location": "New York"
                }
                """;

        String[] propertiesToInclude = {"name", "location"};
        JsonProcessor processor = new FilterInclude(new BaseJsonProcessor(), propertiesToInclude);
        String result = processor.process(inputJson);

        String expectedOutput = """
                {
                    "name": "Alice",
                    "location": "New York"
                }
                """;

        assertEquals(objectMapper.readTree(expectedOutput), objectMapper.readTree(result));
    }
    /**
     * Testuje funkcję minimalizacji JSON.
     * Sprawdza, czy wynikowy JSON jest prawidłowo zminimalizowany (bez zbędnych spacji i nowych linii).
     *
     * @throws Exception w przypadku problemów z przetwarzaniem JSON.
     */
    @Test
    public void testMinify() throws Exception {
        String inputJson = """
                {
                    "name": "Alice",
                    "age": 25,
                    "location": "New York"
                }
                """;

        JsonProcessor processor = new Minify(new BaseJsonProcessor());
        String result = processor.process(inputJson);

        String expectedOutput = "{\"name\":\"Alice\",\"age\":25,\"location\":\"New York\"}";

        assertEquals(expectedOutput, result);
    }
    /**
     * Testuje funkcję upiększania JSON.
     * Sprawdza, czy wynikowy JSON jest prawidłowo sformatowany z odpowiednimi wcięciami i nowymi liniami.
     *
     * @throws Exception w przypadku problemów z przetwarzaniem JSON.
     */
    @Test
    public void testPrettify() throws Exception {
        String inputJson = "{\"name\":\"Alice\",\"age\":25,\"location\":\"New York\"}";

        JsonProcessor processor = new Prettify(new BaseJsonProcessor());
        String result = processor.process(inputJson);

        String expectedOutput = """
                {
                  "name" : "Alice",
                  "age" : 25,
                  "location" : "New York"
                }
                """;

        assertEquals(objectMapper.readTree(expectedOutput), objectMapper.readTree(result));
    }
}