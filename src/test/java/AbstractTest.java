import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {

    static ObjectMapper objectMapper = new ObjectMapper();

    static Properties properties = new Properties();
    private static InputStream confFile;
    private static String apiKey;
    private static String baseUrl;
    private static String complexSearch;
    private static String cuisine;
    private static String mealPlanRecipe;
    private static ConnectUserRequest connectUserRequest = new ConnectUserRequest("Vadim", "Vadim", "Ivanchenko", "gb-test-vi@mail.ru");
    private static String userData;

    protected static ResponseSpecification responseSpecificationOK;
    protected static ResponseSpecification responseSpecification401;
    protected static RequestSpecification requestSpecification;
    protected static RequestSpecification requestSpecificationMP;
    protected static RequestSpecification requestSpecificationMPDelete;

    public static RequestSpecification getRequestSpecificationMP() {
        return requestSpecificationMP;
    }

    public static RequestSpecification getRequestSpecificationMPDelete() {
        return requestSpecificationMPDelete;
    }

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

    public static String getUserData() {
        return userData;
    }

    public static ConnectUserRequest getConnectUserRequest() {
        return connectUserRequest;
    }

    public static String  getMealPlanRecipe() {
        return mealPlanRecipe;
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
        userData = objectMapper.writeValueAsString(getConnectUserRequest());
        mealPlanRecipe = properties.getProperty("mealPlanRecipe");


        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .build();

        requestSpecificationMP = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .setBody(getMealPlanRecipe())
                .build();

        requestSpecificationMPDelete = new RequestSpecBuilder()
                .addQueryParam("hash", "c6b5a96c728245372e5ebd76bac8e1bc3bee20de")
                .addQueryParam("apiKey", getApiKey())
                .setBody(getMealPlanRecipe())
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
