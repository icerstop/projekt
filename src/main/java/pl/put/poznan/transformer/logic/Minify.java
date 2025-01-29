package pl.put.poznan.transformer.logic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Dekorator do minimalizacji JSON.
 * <p>
 * Klasa ta implementuje dekorator wzorca projektowego, który umożliwia minimalizację (kompresję) JSON-a.
 * Używa innego procesora JSON (przekazanego w konstruktorze) do przetworzenia JSON-a, a następnie zwraca wersję
 * JSON-a, która została zminimalizowana poprzez usunięcie zbędnych białych znaków (spacji, nowych linii itp.).
 * </p>
 */
public class Minify extends BaseJsonProcessor {
    private final JsonProcessor processor;
    /**
     * Konstruktor klasy Minify.
     *
     * @param processor Procesor JSON, który ma zostać zastosowany do wstępnego przetworzenia JSON-a przed minimalizacją.
     */
    public Minify(JsonProcessor processor) {
        this.processor = processor;
    }
    /**
     * Przetwarza dany JSON, minimalizując jego rozmiar.
     * <p>Metoda najpierw przetwarza JSON za pomocą innego procesora przekazanego w konstruktorze,
     * a następnie minimalizuje wynikowy JSON, usuwając zbędne białe znaki, takie jak spacje, tabulatory i nowe linie.</p>
     *
     * @param json JSON w formie tekstowej (String), który ma zostać przetworzony.
     * @return Zminimalizowany JSON w formie tekstowej (String).
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-a.
     */
    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        return objectMapper.writeValueAsString(jsonNode);
    }
}
