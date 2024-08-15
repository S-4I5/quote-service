package xyz.s4i5.quoteservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class TestUtil {
    private static final String JSON_BASE_PATH = "src/test/resources/json/";
    private static final String JSON_EXTENSION = ".json";

    public static String readJsonAsString(String filename) throws IOException {
        var path = Paths.get(JSON_BASE_PATH, filename + JSON_EXTENSION);
        return Files.readString(path);
    }

//    public static void assertAsJson(List<Map<String, Object>> actual, String expected) throws Exception {
//        JSONAssert.assertEquals(expected, new ObjectMapper().writeValueAsString(actual), JSONCompareMode.LENIENT);
//    }
}
