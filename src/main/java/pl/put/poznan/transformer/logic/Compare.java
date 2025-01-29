package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

/**
 * Dekorator do porównywania JSON.
 */
public class Compare extends BaseJsonProcessor {
    private final JsonNode json2;

    public Compare(JsonNode json2) {
        this.json2 = json2;
    }

    @Override
    public String process(String json1) throws JsonProcessingException {
        String json2String = objectMapper.writeValueAsString(json2);

        JsonNode tree1 = objectMapper.readTree(json1);
        JsonNode tree2 = objectMapper.readTree(json2String);

        StringBuilder differences = new StringBuilder();
        compareNodes(tree1, tree2, differences, "");
        return !differences.isEmpty() ? differences.toString() : "Brak różnic.";
    }

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
