package services.Utils.PasswordUtils;

import org.mindrot.jbcrypt.BCrypt;

public class HashPassword {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

//    public static void main(String[] args) {
//        System.out.println(hashPassword("pwd"));
//    }
}