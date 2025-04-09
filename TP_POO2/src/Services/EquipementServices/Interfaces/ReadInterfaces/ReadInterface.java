package Services.EquipementServices.Interfaces.ReadInterfaces;

import java.sql.ResultSet;

public interface ReadInterface {
   ResultSet read(Integer id) throws Exception;
}
