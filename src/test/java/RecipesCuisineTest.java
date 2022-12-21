import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RecipesCuisineTest extends AbstractTest {

    @Test
    @DisplayName("Search with valid title")
    void searchWithTitle() {
        given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "Cauliflower, Brown Rice, and Vegetable Fried Rice")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("cuisines[0]", equalTo("Chinese"))
                .body("cuisines[1]", equalTo("Asian"));
    }

    @Test
    @DisplayName("Search with invalid language param")
    void searchWithLangParam() {
        given()
                .log().params()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "fr")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Search with ingredient list")
    void searchWithIngList() {
        given()
                .queryParam("apiKey", getApiKey())
                .formParam("ingredientList", "3 oz pork shoulder")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("cuisines[0]", equalTo("Mediterranean"))
                .body("cuisines[1]", equalTo("European"))
                .body("cuisines[2]", equalTo("Italian"));
    }

    @Test
    @DisplayName("Response with invalid request contern type")
    void verifyInvContTypeReq() {
        given()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "form-data")
                .when()
                .post(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    @DisplayName("Request with invalid method")
    void verifyRequestWithInvMethod() {
        given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "Cauliflower, Brown Rice, and Vegetable Fried Rice")
                .when()
                .get(getBaseUrl() + getCuisine())
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(405);
    }
}

