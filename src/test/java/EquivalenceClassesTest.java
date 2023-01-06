import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EquivalenceClassesTest extends AbstractTest {

    @Test
    @DisplayName("Search with default result number")
    void defaultSearch() {
        given()
                .spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(getResponseSpecificationOK())
                .body("number", equalTo(10));
    }

    @Test
    @DisplayName("Search for max number + 1 result")
    void searchForMaxPlus() {
        given()
                .spec(getRequestSpecification())
                .queryParam("number", "101")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(getResponseSpecificationOK())
                .body("number", equalTo(100));
    }

    @Test
    @DisplayName("Search for 0 result")
    void searchForZeroResults() {
        given()
                .spec(getRequestSpecification())
                .queryParam("number", "0")
                .when()
                .get(getBaseUrl() + getComplexSearch())
                .then()
                .spec(getResponseSpecificationOK())
                .body("number", equalTo(1));
    }
}
