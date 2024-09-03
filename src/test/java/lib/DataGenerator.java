package lib;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataGenerator {
    public static String getRandomEmail() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHss").format(new Date());
        return "learnQa" + timeStamp + "@example.com";
    }
}
