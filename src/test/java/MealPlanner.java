import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MealPlanner extends AbstractTest {


    @Test
    @DisplayName("End to end Meal Planner test")
    void verifyMealPlanner() {
        MealPlannerResponse response;
        response = given()
                .spec(getRequestSpecification())
                .body(getUserData())
                .when()
                .post(getBaseUrl() + "users/connect")
                .then()
                .extract()
                .response()
                .body()
                .as(MealPlannerResponse.class);

        String username = response.getUsername();
        String hash = response.getHash();

        String id = given()
                .queryParam("hash",hash)
                .spec(getRequestSpecificationMP())
                .when()
                .post(getBaseUrl() + "mealplanner/" + username + "/items")
                .prettyPeek()
                .then()
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .spec(getRequestSpecificationMPDelete())
                .when()
                .delete(getBaseUrl() + "mealplanner/vadim1/items/" + id)
                .prettyPeek()
                .then()
                .spec(getResponseSpecificationOK())
                .body("status", equalTo("success"));

    }
}
