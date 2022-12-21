import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {

    static Properties properties = new Properties();
    private static InputStream confFile;
    private static String apiKey;
    private static String baseUrl;
    private static String complexSearch;
    private static String cuisine;

    public static String getComplexSearch() {
        return complexSearch;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getCuisine() {
        return cuisine;
    }

    @BeforeAll
    static void init() throws IOException {
        confFile = new FileInputStream("src/main/resources/my.properties");
        properties.load(confFile);

        apiKey = properties.getProperty("apiKey");
        baseUrl = properties.getProperty("baseUrl");
        complexSearch = properties.getProperty("complexSearch");
        cuisine = properties.getProperty("cuisine");



    }
}
