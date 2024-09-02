import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SomethingAboutCookies {

    @Test
    public void getCookies() {
        /*
        This API call set auth cookie by login=secret_login and password=secret_pass
        Params:
            @ login : string
            @ password : string
        https://playground.learnqa.ru/api/get_auth_cookie
         */
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        //Text in response from server (empty)
        System.out.println("\nPretty text:");
        response.prettyPrint();

        //All headers from server
        System.out.println("\nHeaders:");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        //All cookies from headers
        System.out.println("\nCookies:");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        //Print special cookie
        System.out.println("\nSpecial cookie:");
        String special_cookie = response.getCookie("auth_cookie");
        System.out.println(special_cookie);
    }

    @Test
    public void canNotGetCookiesWrongCredentials() {
        /*
        This API call set auth cookie by login=secret_login and password=secret_pass
        Params:
            @ login : string
            @ password : string
        https://playground.learnqa.ru/api/get_auth_cookie
         */
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login1");
        data.put("password", "secret_pass1");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        //Text in response from server (wrong data)
        System.out.println("\nPretty text:");
        response.prettyPrint();

        //All headers from server
        System.out.println("\nHeaders:");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        //All cookies from headers
        System.out.println("\nCookies:");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        //Print special cookie
        System.out.println("\nSpecial cookie:");
        String special_cookie = response.getCookie("auth_cookie");
        System.out.println(special_cookie);
    }

    @Test
    public void checkAuthCookies() {
        /*
        This API call returns text depends on has you auth cookie or not
        https://playground.learnqa.ru/api/check_auth_cookie

        This API call set auth cookie by login=secret_login and password=secret_pass
        Params:
            @ login : string
            @ password : string
        https://playground.learnqa.ru/api/get_auth_cookie
         */
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response responseForGettingCookie = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        String responseCookie = responseForGettingCookie.getCookie("auth_cookie");
        Map<String, String> cookies = new HashMap<>();

        if (responseCookie != null) {
            cookies.put("auth_cookie", responseCookie);
        }

        cookies.put("auth_cookie", responseCookie);

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();

        responseForCheck.print();
    }
}
