package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Dekorator do filtrowania JSON (usuwanie określonych właściwości).
 */
public class FilterExclude extends BaseJsonProcessor {
    private final JsonProcessor processor;
    private final String[] properties;

    public FilterExclude(JsonProcessor processor, String[] properties) {
        this.processor = processor;
        this.properties = properties;
    }

    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        ObjectNode filteredNode = (ObjectNode) jsonNode.deepCopy();
        for (String property : properties) {
            filteredNode.remove(property);
        }
        return objectMapper.writeValueAsString(filteredNode);
    }
}
