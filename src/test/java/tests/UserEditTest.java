package tests;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserEditTest extends BaseTestCase {

    @Test
    public void editJustCreatedUser() {
        //Generate user
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseReg = RestAssured
                .given()
                .body(userData)
                .when()
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseReg.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseAuth = RestAssured
                .given()
                .body(authData)
                .when()
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        /* Edit - PUT
        Update user (must be logged in as this user)
        Params:
            @ username : string
            @ firstName : string
            @ lastName : string
            @ email : string
            @ password : string
        https://playground.learnqa.ru/api/user/{id}
         */
        String editedName = "Edited Name";
        Map<String, String> editedData = new HashMap<>();
        editedData.put("firstName", editedName);

        RestAssured
                .given()
                .header("x-csrf-token", getHeader(responseAuth, "x-csrf-token"))
                .cookie("auth_sid", getCookie(responseAuth, "auth_sid"))
                .body(editedData)
                .when()
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //Get
        Response responseGet = RestAssured
                .given()
                .header("x-csrf-token", getHeader(responseAuth, "x-csrf-token"))
                .cookie("auth_sid", getCookie(responseAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        Assertions.assertStringValFromJsonByName(responseGet, "firstName", editedName);
    }
}
