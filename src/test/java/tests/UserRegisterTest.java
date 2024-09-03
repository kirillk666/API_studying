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
        String email = "vinkotov@example.com";
        /* POST - Create user
        Params:
            @ username : string
            @ firstName : string
            @ lastName : string
            @ email : string
            @ password : string
            https://playground.learnqa.ru/api/user/
         */
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

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
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

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
