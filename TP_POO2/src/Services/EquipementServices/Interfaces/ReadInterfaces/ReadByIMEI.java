package Services.EquipementServices.Interfaces.ReadInterfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ReadByIMEI {
    ResultSet getByIMEI() throws SQLException;
}
