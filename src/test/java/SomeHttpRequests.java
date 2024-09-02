import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SomeHttpRequests {
    //https://playground.learnqa.ru/api/map
    @Test
    public void answer() {
        /*This API call says hello by name you specify
        Params:
            @ name : string - Default "someone"
        https://playground.learnqa.ru/api/hello
        */
        Map<String, String> params = new HashMap<>();
        params.put("name", "Kirill");

        //Используется паттерн Builder.
        Response response = RestAssured //Переменная в котор. положим ответ
                .given()
                .queryParams(params)
                .when()
                .get("https://playground.learnqa.ru/api/hello") //setter, в который кладем URL
                .andReturn(); //executor

        /*
        prettyPrint() - возвращает отформатированный вид.
        Если возвращаем json, то красиво отформатирует, а если текст, то обернет в html теги.
         */
        response.prettyPrint();
    }

    @Test
    public void answerJson() {
        /*
        В р-те выполнения кода выше получаем ответ:
        {
            "answer": "Hello, Kirill"
        }
        Значит можно сразу проверить значение по ключу "answer" в Json, который возвращается в кач-ве ответа.
        */
        Map<String, String> params = new HashMap<>();
        params.put("name", "Kirill");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .when()
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = response.get("answer");
        System.out.println(answer);
    }

    @Test
    public void checkTypeGet() {
        /*
        This API call says what type of request it is
        https://playground.learnqa.ru/api/check_type
        */
        Response response = RestAssured
                .given()
                .queryParam("param1", "value1")
                .queryParam("param2", "value2")
                .when()
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        //Тут возвращаем текст, поэтому незачем использовать prettyPrint()
        response.print();
    }

    @Test
    public void checkTypePost() {
        /*
        This API call says what type of request it is
        https://playground.learnqa.ru/api/check_type
        */
        Response response = RestAssured
                .given()
                .body("param1=value&param2=value2")
                .when()
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void checkTypePostBetter() {
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

        response.print();
    }
}
