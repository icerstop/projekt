package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;

/**
 * Klasa JsonTransformer zawiera metody do manipulacji JSON, w tym:
 * minimalizowanie, upiększanie, filtrowanie oraz porównywanie struktur JSON.
 */
public class JsonTransformer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Minimalizuje JSON, usuwając niepotrzebne białe znaki.
     *
     * @param json JSON w formacie tekstowym.
     * @return Zminimalizowany JSON w formacie tekstowym.
     * @throws JsonProcessingException gdy JSON jest niepoprawny.
     */
    public static String minify(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        return objectMapper.writeValueAsString(jsonNode);
    }

    /**
     * Upiększa JSON, dodając wcięcia i formatowanie dla lepszej czytelności.
     *
     * @param json JSON w formacie tekstowym.
     * @return Upiększony JSON w formacie tekstowym.
     * @throws JsonProcessingException gdy JSON jest niepoprawny.
     */
    public static String prettify(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }

    /**
     * Filtrowanie JSON w celu zachowania tylko określonych właściwości.
     *
     * @param json JSON w formacie tekstowym.
     * @param properties Tablica nazw właściwości do zachowania.
     * @return Przefiltrowany JSON zawierający tylko wskazane właściwości.
     * @throws JsonProcessingException gdy JSON jest niepoprawny.
     */
    public static String filterInclude(String json, String[] properties) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        ObjectNode filteredNode = objectMapper.createObjectNode();
        for (String property : properties) {
            if (jsonNode.has(property)) {
                filteredNode.set(property, jsonNode.get(property));
            }
        }
        return objectMapper.writeValueAsString(filteredNode);
    }

    /**
     * Filtrowanie JSON w celu usunięcia określonych właściwości.
     *
     * @param json JSON w formacie tekstowym.
     * @param properties Tablica nazw właściwości do usunięcia.
     * @return Przefiltrowany JSON bez wskazanych właściwości.
     * @throws JsonProcessingException gdy JSON jest niepoprawny.
     */
    public static String filterExclude(String json, String[] properties) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        ObjectNode filteredNode = (ObjectNode) jsonNode.deepCopy();
        for (String property : properties) {
            filteredNode.remove(property);
        }
        return objectMapper.writeValueAsString(filteredNode);
    }

    /**
     * Porównuje dwa JSON-y i zwraca różnice między nimi.
     *
     * @param json1 Pierwszy JSON w formacie tekstowym.
     * @param json2 Drugi JSON w formacie tekstowym.
     * @return Opis różnic między dwoma JSON-ami lub informacja o ich braku.
     * @throws JsonProcessingException gdy którykolwiek JSON jest niepoprawny.
     */
    public static String compare(String json1, String json2) throws JsonProcessingException {
        JsonNode tree1 = objectMapper.readTree(json1);
        JsonNode tree2 = objectMapper.readTree(json2);

        StringBuilder differences = new StringBuilder();
        compareNodes(tree1, tree2, differences, "");
        return differences.length() > 0 ? differences.toString() : "Brak różnic.";
    }

    /**
     * Rekurencyjnie porównuje dwa węzły JSON i zapisuje różnice w buforze. Metoda wykorzystywana w metodzie 'compare'
     *
     * @param node1 Pierwszy węzeł JSON.
     * @param node2 Drugi węzeł JSON.
     * @param differences Bufor do zapisu różnic.
     * @param path Ścieżka do aktualnie porównywanego węzła.
     */
    private static void compareNodes(JsonNode node1, JsonNode node2, StringBuilder differences, String path) {
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
