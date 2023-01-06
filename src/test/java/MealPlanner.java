import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MealPlanner extends AbstractTest {

    @Test
    @DisplayName("End to end Meal Planner test")
    void verifyMealPlanner() {
        Response response;
        response = given()
                .spec(getRequestSpecification())
                .body("{\n" +
                        "    \"username\": \"Vadim\",\n" +
                        "    \"firstName\": \"Vadim\",\n" +
                        "    \"lastName\": \"Ivanchenko\",\n" +
                        "    \"email\": \"gb-test-vi@mail.ru\"\n" +
                        "}")
                .when()
                .post(getBaseUrl() + "users/connect")
                .then().extract().response();

        String username = response.jsonPath().get("username");
        String hash = response.jsonPath().get("hash");

        String id = given()
                .queryParam("hash",hash)
                .queryParam("apiKey", getApiKey())
                .body("{\n" +
                        "    \"date\": 1589500800,\n" +
                        "    \"slot\": 1,\n" +
                        "    \"position\": 0,\n" +
                        "    \"type\": \"INGREDIENTS\",\n" +
                        "    \"value\": {\n" +
                        "        \"ingredients\": [\n" +
                        "            {\n" +
                        "                \"name\": \"1 banana\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}")
                .when()
                .post(getBaseUrl() + "mealplanner/" + username + "/items")
                .prettyPeek()
                .then()
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        System.out.println(id);

        given()
                .queryParam("hash", "c6b5a96c728245372e5ebd76bac8e1bc3bee20de")
                .queryParam("apiKey", AbstractTest.getApiKey())
                .when()
                .delete(getBaseUrl() + "mealplanner/vadim1/items/" + id)
                .prettyPeek()
                .then()
                .spec(getResponseSpecificationOK())
                .body("status", equalTo("success"));

    }
}
