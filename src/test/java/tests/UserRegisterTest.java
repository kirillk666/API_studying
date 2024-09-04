package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {

    @Test
    public void createUserWithExistingEmail() {
        /* POST - Create user
        Params:
            @ username : string
            @ firstName : string
            @ lastName : string
            @ email : string
            @ password : string
            https://playground.learnqa.ru/api/user/
         */
        String email = "vinkotov@example.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);

        userData = DataGenerator.getRegistrationData(userData);

        Response response = RestAssured
                .given()
                .body(userData)
                .when()
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(response,"Users with email '" + email + "' already exists");
        Assertions.assertResponseCodeEquals(response, 400);
    }

    @Test
    public void createUserSuccessfullyWithRandomEmail() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response response = RestAssured
                .given()
                .body(userData)
                .when()
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertJsonHasField(response, "id");
        Assertions.assertResponseCodeEquals(response, 200);
    }
}
