package Services.EquipementServices.Interfaces.ReadInterfaces;

import java.sql.ResultSet;
import java.util.List;

public interface ReadInterface {
   List read(Integer id) throws Exception;
}
