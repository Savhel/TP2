package services.Utils.PasswordUtils;


import org.mindrot.jbcrypt.BCrypt;

public class VerifyPassword{
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
