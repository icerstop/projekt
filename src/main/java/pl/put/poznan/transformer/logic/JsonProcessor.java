package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Interfejs definiujący operacje na JSON.
 * <p>
 * Interfejs ten definiuje operację przetwarzania JSON-a w formie tekstowej.
 * Wszystkie klasy, które implementują ten interfejs, muszą zaimplementować metodę {@link #process(String)}.
 * </p>
 */
public interface JsonProcessor {
    /**
     * Procesuje dany JSON w formie tekstowej.
     * <p>Metoda ta umożliwia przetwarzanie JSON-a w różnych sposób, w zależności od implementacji.
     * Może to obejmować walidację, modyfikację lub inne operacje na danych zawartych w formacie JSON.</p>
     *
     * @param json JSON w formie tekstowej (String), który ma zostać przetworzony.
     * @return Zaktualizowany JSON w formie tekstowej (String).
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-a.
     */
    String process(String json) throws JsonProcessingException;
}









