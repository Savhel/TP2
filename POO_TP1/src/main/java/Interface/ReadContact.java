package Interface;

import java.sql.ResultSet;

public interface ReadContact {
    ResultSet readContact(String search) throws Exception;
    ResultSet findContact(String search) throws Exception;
}
