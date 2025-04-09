package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class Etudiant extends Contact{

    private String cycle;
    private  Integer niveau;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Etudiant() {
        super();
    }

    public Etudiant(String nom, String code, String address, String email, String telNumber, LocalDate dateNaissance, String cycle, Integer niveau) {
        super(nom, code, address, email, telNumber, dateNaissance);
        this.cycle = cycle;
        this.niveau = niveau;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    @Override
    public void insererNouveauContact(Connection connection) {
        try{
            preparedStatement = connection.prepareStatement("Insert into Etudiant ()");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
