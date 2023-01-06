import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AccessTest extends AbstractTest {

    @Test
    @DisplayName("Search without API Key")
    void searchWithoutKey() {
        given()
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(getResponseSpecification401());
    }

    @Test
    @DisplayName("Search with invalid API Key")
    void defaultSearch() {
        given()
                .queryParam("apiKey", "0e0e0e0e0e0e0e0e0e0e0e0e0e0e0e0e")
                .when()
                .get(AbstractTest.getBaseUrl() + getComplexSearch())
                .then()
                .spec(getResponseSpecification401());
    }
}
