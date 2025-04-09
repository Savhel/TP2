package core;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class Enseignant extends Contact{
    private String statut;

    public Enseignant() {
        super();
    }

    public Enseignant(String nom, String code, String address, String email, String telNumber, LocalDate dateNaissance, String statut) {
        super(nom, code, address, email, telNumber, dateNaissance);
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public void insererNouveauContact(Connection connection) {

    }
}
