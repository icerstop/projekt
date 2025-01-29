package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Podstawowa implementacja procesora JSON.
 */
public class BaseJsonProcessor implements JsonProcessor {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String process(String json) throws JsonProcessingException {
        return json;
    }
}
