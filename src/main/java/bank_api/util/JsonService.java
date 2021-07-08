package bank_api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonService {

    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return myObjectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    public static <A> A parse(InputStream is, Class<A> clazz) throws IOException {
        return myObjectMapper.readValue(is, clazz);
    }

    public static <A> A parse(String content, Class<A> clazz) throws IOException {
        return myObjectMapper.readValue(content, clazz);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static <T> String stringify(List<T> list) throws JsonProcessingException {
        return generateJson(list, false);
    }

    public static <K, V> String stringify(Map<K, V> map) throws JsonProcessingException {
        return generateJson(map, false);
    }

    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    public static <T> String stringifyPretty(List<T> list) throws JsonProcessingException {
        return generateJson(list, true);
    }

    public static <K, V> String stringifyPretty(Map<K, V> map) throws JsonProcessingException {
        return generateJson(map, true);
    }

    public static String stringifyPretty(Object object) throws JsonProcessingException {
        return generateJson(object, true);
    }

    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(o);
    }

}
