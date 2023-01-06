import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Data;
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

    protected static ResponseSpecification responseSpecificationOK;
    protected static ResponseSpecification responseSpecification401;
    protected static RequestSpecification requestSpecification;

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


    public static ResponseSpecification getResponseSpecificationOK() {
        return responseSpecificationOK;
    }

    public static ResponseSpecification getResponseSpecification401() {
        return responseSpecification401;
    }

    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    @BeforeAll
    static void init() throws IOException {
        confFile = new FileInputStream("src/main/resources/my.properties");
        properties.load(confFile);

        apiKey = properties.getProperty("apiKey");
        baseUrl = properties.getProperty("baseUrl");
        complexSearch = properties.getProperty("complexSearch");
        cuisine = properties.getProperty("cuisine");

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .build();

        responseSpecificationOK = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();

        responseSpecification401 = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectContentType("application/json")
                .build();
    }
}
