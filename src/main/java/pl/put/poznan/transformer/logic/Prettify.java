package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Dekorator do upiększania JSON.
 * <p>
 * Klasa ta implementuje dekorator wzorca projektowego, który umożliwia upiększenie (formatowanie) JSON-a,
 * czyniąc go bardziej czytelnym. Używa innego procesora JSON (przekazanego w konstruktorze) do przetworzenia JSON-a,
 * a następnie formatuje wynikowy JSON, dodając odpowiednie wcięcia, nowe linie oraz spacje, aby poprawić jego czytelność.
 * </p>
 */
public class Prettify extends BaseJsonProcessor {
    private final JsonProcessor processor;
    /**
     * Konstruktor klasy Prettify.
     *
     * @param processor Procesor JSON, który ma zostać zastosowany do wstępnego przetworzenia JSON-a przed upiększaniem.
     */
    public Prettify(JsonProcessor processor) {
        this.processor = processor;
    }
    /**
     * Przetwarza dany JSON, upiększając jego format.
     * <p>Metoda najpierw przetwarza JSON za pomocą innego procesora przekazanego w konstruktorze,
     * a następnie formatuje wynikowy JSON, stosując odpowiednie wcięcia, nowe linie oraz spacje,
     * aby zwiększyć jego czytelność (tzw. "prettifying").</p>
     *
     * @param json JSON w formie tekstowej (String), który ma zostać przetworzony.
     * @return Upiększony (sformatowany) JSON w formie tekstowej (String).
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-a.
     */
    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }
}

