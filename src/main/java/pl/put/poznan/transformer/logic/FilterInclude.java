package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Dekorator do filtrowania JSON (zachowanie określonych właściwości).
 * <p>
 * Klasa ta implementuje dekorator wzorca projektowego, który umożliwia zachowanie tylko określonych właściwości w obiekcie JSON.
 * Używa innego procesora JSON (przekazanego w konstruktorze) do przetworzenia JSON-a, a następnie filtruje właściwości
 * na podstawie tablicy nazw właściwości przekazanej w konstruktorze, zachowując tylko te, które znajdują się w tej tablicy.
 * </p>
 */
public class FilterInclude extends BaseJsonProcessor {
    private final JsonProcessor processor;
    private final String[] properties;
    /**
     * Konstruktor klasy FilterInclude.
     *
     * @param processor Procesor JSON, który ma zostać zastosowany do wstępnego przetworzenia JSON-a.
     * @param properties Tablica nazw właściwości (pol), które mają zostać zachowane w przetwarzanym JSON-ie.
     */
    public FilterInclude(JsonProcessor processor, String[] properties) {
        this.processor = processor;
        this.properties = properties;
    }
    /**
     * Przetwarza dany JSON, zachowując tylko określone właściwości.
     * <p>Metoda najpierw przetwarza JSON za pomocą innego procesora przekazanego w konstruktorze,
     * a następnie filtruje obiekt JSON, zachowując tylko właściwości, które znajdują się w tablicy {@code properties}.
     * Na końcu zwraca zaktualizowany JSON zawierający tylko te właściwości.</p>
     *
     * @param json JSON w formie tekstowej (String), który ma zostać przetworzony.
     * @return Zaktualizowany JSON w formie tekstowej (String), zawierający tylko zachowane właściwości.
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-a.
     */
    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        ObjectNode filteredNode = objectMapper.createObjectNode();
        for (String property : properties) {
            if (jsonNode.has(property)) {
                filteredNode.set(property, jsonNode.get(property));
            }
        }
        return objectMapper.writeValueAsString(filteredNode);
    }
}
