package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Dekorator do filtrowania JSON (zachowanie określonych właściwości).
 */
public class FilterInclude extends BaseJsonProcessor {
    private final JsonProcessor processor;
    private final String[] properties;

    public FilterInclude(JsonProcessor processor, String[] properties) {
        this.processor = processor;
        this.properties = properties;
    }

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
