package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Dekorator do upiększania JSON.
 */
public class Prettify extends BaseJsonProcessor {
    private final JsonProcessor processor;

    public Prettify(JsonProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(processor.process(json));
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }
}

