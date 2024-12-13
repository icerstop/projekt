package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;

public class JsonTransformer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Metoda do zminimalizowania JSON
    public static String minify(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        return objectMapper.writeValueAsString(jsonNode);
    }

    // Metoda do upiększenia JSON
    public static String prettify(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }

    // Metoda do filtrowania JSON aby zwracała tylko chciane właściwości
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

    // Metoda do filtrowania JSON aby zwracała bez niechcianych właściwości
    public static String filterExclude(String json, String[] properties) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        ObjectNode filteredNode = (ObjectNode) jsonNode.deepCopy();
        for (String property : properties) {
            filteredNode.remove(property);
        }
        return objectMapper.writeValueAsString(filteredNode);
    }

    // Metoda do porównania JSON i zwrócenia różnic
    public static String compare(String json1, String json2) throws JsonProcessingException {
        JsonNode tree1 = objectMapper.readTree(json1);
        JsonNode tree2 = objectMapper.readTree(json2);

        StringBuilder differences = new StringBuilder();
        compareNodes(tree1, tree2, differences, "");
        return differences.length() > 0 ? differences.toString() : "Brak różnic.";
    }

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
