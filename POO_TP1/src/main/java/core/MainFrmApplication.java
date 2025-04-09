package core;

import java.sql.Connection;

public class MainFrmApplication {
    private Connection connection;

    public Connection poolConnection(){
        return DatabaseConnection.getInstance().getConnection();
    }
}
