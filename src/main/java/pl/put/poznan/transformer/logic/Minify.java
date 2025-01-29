package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Dekorator do minimalizacji JSON.
 */
public class Minify extends BaseJsonProcessor {
    private final JsonProcessor processor;

    public Minify(JsonProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        return objectMapper.writeValueAsString(jsonNode);
    }
}
