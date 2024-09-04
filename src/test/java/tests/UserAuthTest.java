package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

@Epic("Authorisation cases")
@Feature("Authorisation")
public class UserAuthTest extends BaseTestCase {

    String cookie;
    String token;
    int userId;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser() {
        /*
        Logs user into the system
        Params:
            @ email : string
            @ password : string
        https://playground.learnqa.ru/api/user/login
        */
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response response = apiCoreRequests
                .postRequest("https://playground.learnqa.ru/api/user/login", authData);

        Assertions.assertResponseCodeEquals(response, 200);

        cookie = getCookie(response, "auth_sid");
        token = getHeader(response, "x-csrf-token");

        userId = getIntFromJson(response, "user_id");
        Assertions.assertFieldValueMoreThenValue(response, userId, 0);
    }

    @Test
    @Description("This test successfully authorize user by email and password")
    @DisplayName("Test positive auth user")
    public void authUser() {
        /*
        Get user id you are authorizes as OR get 0 if not authorized
        https://playground.learnqa.ru/api/user/auth
         */
        Response response = apiCoreRequests
                .getRequest("https://playground.learnqa.ru/api/user/auth", token, cookie);

        Assertions.assertIntValFromJsonByName(response, "user_id", userId);
    }

    @Description("This test checks authorisation status w/o sending auth cookie or token")
    @DisplayName("Test negative auth user")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void negativeAuthUser(String condition) {
        Response response;

        if (condition.equals("cookie")) {
            response = apiCoreRequests.getRequestWithCookie("https://playground.learnqa.ru/api/user/auth", cookie);
        } else if (condition.equals("headers")) {
            response = apiCoreRequests.getRequestWithToken("https://playground.learnqa.ru/api/user/auth", token);
        } else {
            throw new IllegalArgumentException("Condition value '" + condition + "' is not known");
        }

        Assertions.assertIntValFromJsonByName(response, "user_id", 0);
    }
}

// C:\Users\kiril\IdeaProjects\API_studying>allure serve allure-results/