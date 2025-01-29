package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Podstawowa implementacja procesora JSON.
 * <p>
 * Klasa ta implementuje interfejs {@link JsonProcessor} i zapewnia podstawową funkcjonalność przetwarzania JSON-ów.
 * W tej wersji metoda {@link #process(String)} zwraca po prostu przekazany JSON w formie tekstowej, bez żadnych modyfikacji.
 * Jest to baza, z której mogą być dziedziczone bardziej zaawansowane klasy procesorów JSON, np. {@link Compare}.
 * </p>
 */
public class BaseJsonProcessor implements JsonProcessor {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Procesuje dany JSON w formie tekstowej.
     *
     * <p>W tej podstawowej implementacji metoda zwraca po prostu przekazany JSON bez żadnych zmian. Może być
     * nadpisana w klasach dziedziczących, by zaimplementować bardziej zaawansowaną logikę przetwarzania.</p>
     *
     * @param json JSON w formie tekstowej (String), który ma zostać przetworzony.
     * @return Zwrocony JSON w formie tekstowej (String).
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-a.
     */
    @Override
    public String process(String json) throws JsonProcessingException {
        return json;
    }
}
