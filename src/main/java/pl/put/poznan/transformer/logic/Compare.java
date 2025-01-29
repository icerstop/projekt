package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

/**
 * Dekorator do porównywania dwóch JSON-ów.
 * <p>
 * Klasa ta umożliwia porównanie dwóch obiektów JSON i wypisanie różnic pomiędzy nimi.
 * Jej działanie opiera się na rekursywnym porównaniu struktury obiektów JSON, zarówno na poziomie
 * pól, jak i wartości.
 * </p>
 */
public class Compare extends BaseJsonProcessor {
    private final JsonNode json2;
    /**
     * Konstruktor klasy Compare.
     *
     * @param json2 Drugi JSON (w formie JsonNode), który będzie porównywany z JSON-em przekazywanym do metody {@link #process}.
     */
    public Compare(JsonNode json2) {
        this.json2 = json2;
    }
    /**
     * Przetwarza dwa JSON-y, porównując je i zwracając różnice.
     *
     * <p>Metoda przyjmuje JSON w formacie tekstowym (String) i porównuje go z wcześniej przekazanym JSON-em
     * (json2). Zwraca różnice między tymi dwoma JSON-ami w formie tekstowej, a jeśli nie ma różnic,
     * zwraca komunikat o braku różnic.</p>
     *
     * @param json1 Pierwszy JSON do porównania (w formie String).
     * @return Różnice między JSON-ami w formacie tekstowym, lub komunikat "Brak różnic." w przypadku braku różnic.
     * @throws JsonProcessingException Jeśli wystąpi błąd podczas przetwarzania JSON-ów.
     */
    @Override
    public String process(String json1) throws JsonProcessingException {
        String json2String = objectMapper.writeValueAsString(json2);

        JsonNode tree1 = objectMapper.readTree(json1);
        JsonNode tree2 = objectMapper.readTree(json2String);

        StringBuilder differences = new StringBuilder();
        compareNodes(tree1, tree2, differences, "");
        return !differences.isEmpty() ? differences.toString() : "Brak różnic.";
    }
    /**
     * Rekursywnie porównuje dwa obiekty JSON i zapisuje różnice.
     *
     * <p>Ta metoda sprawdza, czy dwa węzły JSON są różne. Jeśli są to obiekty, metoda iteruje po ich polach
     * i porównuje je rekurencyjnie. W przypadku znalezienia różnicy, zapisuje szczegóły w obiekcie
     * StringBuilder. W przeciwnym razie przechodzi do porównania kolejnych poziomów struktury JSON.</p>
     *
     * @param node1 Pierwszy węzeł JSON do porównania.
     * @param node2 Drugi węzeł JSON do porównania.
     * @param differences StringBuilder, który zbiera różnice między JSON-ami.
     * @param path Ścieżka, która wskazuje, które pole w strukturze JSON jest aktualnie porównywane.
     */
    private void compareNodes(JsonNode node1, JsonNode node2, StringBuilder differences, String path) {
        if (!node1.equals(node2)) {
            if (node1.isObject() && node2.isObject()) {
                Iterator<String> fieldNames = node1.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    compareNodes(node1.get(fieldName), node2.get(fieldName), differences, path + "/" + fieldName);
                }
            } else {
                differences.append("Różnica w: ").append(path)
                        .append("\nOczekiwane: ").append(node1)
                        .append("\nWłaściwe: ").append(node2).append("\n\n");
            }
        }
    }
}
