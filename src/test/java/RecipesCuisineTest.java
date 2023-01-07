import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RecipesCuisineTest extends AbstractTest {

    @Test
    @DisplayName("Search with valid title")
    void searchWithTitle() {
        given()
                .spec(getRequestSpecification())
                .formParam("title", "Cauliflower, Brown Rice, and Vegetable Fried Rice")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .spec(getResponseSpecificationOK())
                .body("cuisines[0]", equalTo("Chinese"))
                .body("cuisines[1]", equalTo("Asian"));
    }

    @Test
    @DisplayName("Search with invalid language param")
    void searchWithLangParam() {
        given()
                .log().params()
                .spec(getRequestSpecification())
                .queryParam("language", "fr")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Search with ingredient list")
    void searchWithIngList() {
        given()
                .spec(getRequestSpecification())
                .formParam("ingredientList", "3 oz pork shoulder")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .assertThat()
                .spec(getResponseSpecificationOK())
                .body("cuisines[0]", equalTo("Mediterranean"))
                .body("cuisines[1]", equalTo("European"))
                .body("cuisines[2]", equalTo("Italian"));

    }

    @Test
    @DisplayName("Response with invalid request content type")
    void verifyInvContTypeReq() {
        given()
                .spec(getRequestSpecification())
                .header("Content-Type", "form-data")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Request with invalid method")
    void verifyRequestWithInvMethod() {
        given()
                .spec(getRequestSpecification())
                .formParam("title", "Cauliflower, Brown Rice, and Vegetable Fried Rice")
                .when()
                .get(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .statusCode(405);
    }
}

