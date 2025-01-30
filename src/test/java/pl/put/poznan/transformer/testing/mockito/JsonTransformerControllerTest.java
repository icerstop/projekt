package pl.put.poznan.transformer.testing.mockito;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.put.poznan.transformer.logic.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Klasa testowa dla JsonTransformerController, wykorzystująca Mockito do testowania różnych operacji transformacji JSON.
 */
public class JsonTransformerControllerTest {

    // Rzeczywisty ObjectMapper używany do porównywania wyników
    private final ObjectMapper realMapper = new ObjectMapper();

    @Mock
    private ObjectMapper mockMapper; // Mockowany ObjectMapper

    @Mock
    private JsonProcessor mockProcessor; // Mockowany JsonProcessor

    /**
     * Inicjalizacja obiektów mockujących przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test sprawdzający działanie filtra usuwającego określone pola z JSON.
     */
    @Test
    public void testFilterExclude() throws Exception {
        String inputJson = "{ \"name\": \"Alice\", \"age\": 25, \"location\": \"New York\" }";
        String expectedJson = "{ \"name\": \"Alice\", \"location\": \"New York\" }";

        JsonNode inputNode = realMapper.readTree(inputJson);
        JsonNode expectedNode = realMapper.readTree(expectedJson);

        when(mockProcessor.process(inputJson)).thenReturn(inputJson);
        when(mockMapper.readTree(inputJson)).thenReturn(inputNode);
        when(mockMapper.writeValueAsString(any(JsonNode.class))).thenAnswer(inv ->
                realMapper.writeValueAsString(inv.getArgument(0))
        );

        FilterExclude filterExclude = new FilterExclude(mockProcessor, new String[]{"age"});
        String result = filterExclude.process(inputJson);

        JsonNode resultNode = realMapper.readTree(result);
        assertEquals(expectedNode, resultNode);
        verify(mockProcessor).process(inputJson);
    }

    /**
     * Test sprawdzający działanie filtra, który zachowuje tylko określone pola JSON.
     */
    @Test
    public void testFilterInclude() throws Exception {
        String inputJson = "{ \"name\": \"Alice\", \"age\": 25, \"location\": \"New York\" }";
        String expectedJson = "{ \"name\": \"Alice\", \"location\": \"New York\" }";

        JsonNode inputNode = realMapper.readTree(inputJson);
        JsonNode expectedNode = realMapper.readTree(expectedJson);

        when(mockProcessor.process(inputJson)).thenReturn(inputJson);
        when(mockMapper.readTree(inputJson)).thenReturn(inputNode);
        when(mockMapper.writeValueAsString(any(JsonNode.class))).thenAnswer(inv ->
                realMapper.writeValueAsString(inv.getArgument(0))
        );

        FilterInclude filterInclude = new FilterInclude(mockProcessor, new String[]{"name", "location"});
        String result = filterInclude.process(inputJson);

        JsonNode resultNode = realMapper.readTree(result);
        assertEquals(expectedNode, resultNode);
        verify(mockProcessor).process(inputJson);
    }

    /**
     * Test sprawdzający działanie minifikacji JSON, tj. usuwania zbędnych spacji i formatowania.
     */
    @Test
    public void testMinify() throws Exception {
        String inputJson = "{ \"name\": \"Alice\", \"age\": 25, \"location\": \"New York\" }";
        String expectedJson = "{\"name\":\"Alice\",\"age\":25,\"location\":\"New York\"}";

        JsonNode inputNode = realMapper.readTree(inputJson);

        when(mockProcessor.process(inputJson)).thenReturn(inputJson);
        when(mockMapper.readTree(inputJson)).thenReturn(inputNode);
        when(mockMapper.writeValueAsString(any(JsonNode.class))).thenReturn(expectedJson);

        Minify minifyProcessor = new Minify(mockProcessor);
        String result = minifyProcessor.process(inputJson);

        assertEquals(expectedJson, result);
        verify(mockProcessor).process(inputJson);
    }

    /**
     * Test sprawdzający działanie formatowania JSON w czytelny sposób (prettify).
     */
    @Test
    public void testPrettify() throws Exception {
        String inputJson = "{\"name\":\"Alice\",\"age\":25,\"location\":\"New York\"}";
        String expectedJson = """
                {
                  "name" : "Alice",
                  "age" : 25,
                  "location" : "New York"
                }""";

        JsonNode inputNode = realMapper.readTree(inputJson);

        when(mockProcessor.process(inputJson)).thenReturn(inputJson);
        when(mockMapper.readTree(inputJson)).thenReturn(inputNode);
        when(mockMapper.writerWithDefaultPrettyPrinter()).thenReturn(realMapper.writerWithDefaultPrettyPrinter());

        Prettify prettifyProcessor = new Prettify(mockProcessor);
        String result = prettifyProcessor.process(inputJson);

        assertEquals(expectedJson, result);
        verify(mockProcessor).process(inputJson);
    }
}
