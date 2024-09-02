import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SomeStatusCodes {

    @Test
    public void getStatusCode200() {
        /*
        This API call says what type of request it is
        https://playground.learnqa.ru/api/check_type
        */
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");

        Response response = RestAssured
                .given()
                .body(params)
                .when()
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.print();
    }

    @Test
    public void getStatusCode500() {
        /*
        This API call response with 500 code (server error)
        https://playground.learnqa.ru/api/get_500
        */
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_500")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.print();
    }

    @Test
    public void getStatusCode404() {
        /*
        This API call response with 404 code (client error not found)
        */
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/random_endpoint")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.print();
    }

    @Test
    public void getStatusCode303RedirectFalse() {
        /*
        This API call response with 303 code (server tells client that content is not presented on this url and redirects to another)
        Tells correct url in header.
        */
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.print();
    }

    @Test
    public void getStatusCode303RedirectTrue() {
        /*
        This API call response with 303 code (server tells client that content is not presented on this url and redirects to another)
        Tells correct url in header.
        */
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.print();
    }
}
