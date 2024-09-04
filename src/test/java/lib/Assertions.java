package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions {

    public static void assertIntValFromJsonByName(Response response, String name, int expectedValue) {
        response.then().assertThat().body("$", hasKey(name));

        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertStringValFromJsonByName(Response response, String name, String expectedValue) {
        response.then().assertThat().body("$", hasKey(name));

        String value = response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertResponseTextEquals(Response response, String expectedResponse) {
        assertEquals(expectedResponse, response.asString(), "response text is not as expected");
    }

    public static void assertResponseCodeEquals(Response response, int expectedCode) {
        assertEquals(expectedCode, response.getStatusCode(), "response status code is not as expected");
    }

    public static void assertJsonHasField(Response response, String expectedFieldName) {
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    public static void assertJsonHasFields(Response response, String...expectedFieldNames) {
        for(String expectedFieldName : expectedFieldNames) {
            assertJsonHasField(response, expectedFieldName);
        }
    }

    public static void assertJsonHasNotField(Response response, String unexpectedFiledName) {
        response.then().assertThat().body("$", not(hasKey(unexpectedFiledName)));
    }

    public static void assertFieldValueMoreThenValue(Response response, int fieldName, int val) {
        assertTrue(fieldName > val, "User id should be greater then '" + val + "'");
    }
}
