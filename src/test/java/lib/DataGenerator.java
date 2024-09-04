package lib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHss").format(new Date());
        return "learnQa" + timeStamp + "@example.com";
    }

    public static Map<String, String> getRegistrationData() {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", getRandomEmail());
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        return userData;
    }

    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = getRegistrationData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};

        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }

        return userData;
    }
}
