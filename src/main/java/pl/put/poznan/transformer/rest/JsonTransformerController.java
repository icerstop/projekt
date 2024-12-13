package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonTransformer;

@RestController
@RequestMapping("/json")
public class JsonTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(JsonTransformerController.class);

    @PostMapping(value = "/minify", produces = "application/json")
    public String minify(@RequestBody String json) {
        try {
            return JsonTransformer.minify(json);
        } catch (Exception e) {
            logger.error("Error during minification: ", e);
            return "{\"error\": \"Invalid JSON format\"}";
        }
    }

    @PostMapping(value = "/prettify", produces = "application/json")
    public String prettify(@RequestBody String json) {
        try {
            return JsonTransformer.prettify(json);
        } catch (Exception e) {
            logger.error("Error during prettification: ", e);
            return "{\"error\": \"Invalid JSON format\"}";
        }
    }

    @PostMapping(value = "/filter/include", produces = "application/json")
    public String filterInclude(@RequestBody FilterRequest request) {
        try {
            return JsonTransformer.filterInclude(request.getJson(), request.getProperties());
        } catch (Exception e) {
            logger.error("Error during include filtering: ", e);
            return "{\"error\": \"Invalid JSON format or properties\"}";
        }
    }

    @PostMapping(value = "/filter/exclude", produces = "application/json")
    public String filterExclude(@RequestBody FilterRequest request) {
        try {
            return JsonTransformer.filterExclude(request.getJson(), request.getProperties());
        } catch (Exception e) {
            logger.error("Error during exclude filtering: ", e);
            return "{\"error\": \"Invalid JSON format or properties\"}";
        }
    }

    @PostMapping(value = "/compare", produces = "application/json")
    public String compare(@RequestBody CompareRequest request) {
        try {
            return JsonTransformer.compare(request.getJson1(), request.getJson2());
        } catch (Exception e) {
            logger.error("Error during comparison: ", e);
            return "{\"error\": \"Invalid JSON format\"}";
        }
    }

    // DTOs for request payloads
    public static class FilterRequest {
        private String json;
        private String[] properties;

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String[] getProperties() {
            return properties;
        }

        public void setProperties(String[] properties) {
            this.properties = properties;
        }
    }

    public static class CompareRequest {
        private String json1;
        private String json2;

        public String getJson1() {
            return json1;
        }

        public void setJson1(String json1) {
            this.json1 = json1;
        }

        public String getJson2() {
            return json2;
        }

        public void setJson2(String json2) {
            this.json2 = json2;
        }
    }
}
