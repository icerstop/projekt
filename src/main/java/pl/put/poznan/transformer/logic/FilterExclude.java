package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Dekorator do filtrowania JSON (usuwanie określonych właściwości).
 * <p>
 * Klasa ta implementuje dekorator wzorca projektowego, który umożliwia filtrowanie właściwości w obiekcie JSON.
 * Używa innego procesora JSON (przekazanego w konstruktorze) do przetworzenia JSON-a, a następnie usuwa określone właściwości
 * z tego obiektu na podstawie tablicy właściwości przekazanej w konstruktorze.
 * </p>
 */
public class FilterExclude extends BaseJsonProcessor {
    private final JsonProcessor processor;
    private final String[] properties;
    /**
     * Konstruktor klasy FilterExclude.
     *
     * @param processor Procesor JSON, który ma zostać zastosowany do wstępnego przetworzenia JSON-a.
     * @param properties Tablica nazw właściwości (pol), które mają zostać usunięte z przetwarzanego JSON-a.
     */
    public FilterExclude(JsonProcessor processor, String[] properties) {
        this.processor = processor;
        this.properties = properties;
    }
    /**
     * Przetwarza dany JSON, usuwając określone właściwości.
     * <p>Metoda najpierw przetwarza JSON za pomocą innego procesora przekazanego w konstruktorze,
     * a następnie usuwa wszystkie wskazane właściwości z obiektu JSON. Na końcu zwraca zaktualizowany JSON
     * bez usuniętych właściwości.</p>
     *
     * @param json JSON w formie tekstowej (String), który ma zostać przetworzony.
     * @return Zaktualizowany JSON w formie tekstowej (String) z usuniętymi właściwościami.
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-a.
     */
    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        ObjectNode filteredNode = jsonNode.deepCopy();
        for (String property : properties) {
            filteredNode.remove(property);
        }
        return objectMapper.writeValueAsString(filteredNode);
    }
}
