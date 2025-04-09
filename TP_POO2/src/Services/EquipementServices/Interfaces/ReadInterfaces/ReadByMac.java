package Services.EquipementServices.Interfaces.ReadInterfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ReadByMac {
    ResultSet getByMAC() throws SQLException;
}
