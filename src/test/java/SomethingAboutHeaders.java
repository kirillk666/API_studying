import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SomethingAboutHeaders {

    @Test
    public void showAllHeaders() {
        /*
        Show all headers from the requests
        https://playground.learnqa.ru/api/show_all_headers
        */
        Map<String, String> headers = new HashMap<>();
        headers.put("my_header_1", "my_value_1");
        headers.put("my_header_2", "my_value_2");

        Response response = RestAssured
                .given()
                .headers(headers) //add headers to request
                .when()
                .get("https://playground.learnqa.ru/api/show_all_headers")
                .andReturn();

        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
    }

    @Test
    public void showRedirectHeader() {
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

        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\n");

        String redirectHeader = response.getHeader("Location");
        System.out.println(redirectHeader);
    }
}
