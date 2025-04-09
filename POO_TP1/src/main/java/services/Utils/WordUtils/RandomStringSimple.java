package services.Utils.WordUtils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStringSimple {
    public static String generateRandomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return new SecureRandom().ints(6, 0, chars.length())
                .mapToObj(chars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

//    public static void main(String[] args) {
//        System.out.println(generateRandomString());
//    }
}