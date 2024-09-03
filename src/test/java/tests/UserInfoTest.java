package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserInfoTest extends BaseTestCase {

    /* GET
     Get user info by id (you can get more info for user you are authorized as)
     https://playground.learnqa.ru/api/user/{id}
     */

    @Test
    public void getNotAuthorisedUserInfo() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasField(response, "username");
        Assertions.assertJsonHasNotField(response, "firstName");
        Assertions.assertJsonHasNotField(response, "lastName");
        Assertions.assertJsonHasNotField(response, "email");
    }

    @Test
    public void getAuthorisedUserDetailsAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseLogin = RestAssured
                .given()
                .body(authData)
                .when()
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseLogin, 200);

        String cookie = getCookie(responseLogin, "auth_sid");
        String header = getHeader(responseLogin, "x-csrf-token");

        Response responseUserDetails = RestAssured
                .given()
                .cookie("auth_sid", cookie)
                .header("x-csrf-token", header)
                .when()
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasFields(responseUserDetails, "username", "firstName", "lastName", "email");
    }
}
