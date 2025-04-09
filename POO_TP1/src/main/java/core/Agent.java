package core;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class Agent extends Contact {
    private String statut;
    private String categorie;
    private String indiceSalaire;

    private Integer salaire;
    private String occupation;


    public Agent() {
        super();
    }

    public Agent(String nom, String code, String address, String email, String telNumber, String statut, String categorie, String indiceSalaire, Integer salaire, String occupation, LocalDate dateNaissance) {
        super(nom, code, address, email, telNumber, dateNaissance);
        this.statut = statut;
        this.categorie = categorie;
        this.indiceSalaire = indiceSalaire;
        this.occupation = occupation;
        this.salaire = salaire;
    }



    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getIndiceSalaire() {
        return indiceSalaire;
    }

    public Integer getSalaire() {
        return salaire;
    }

    public void setIndiceSalaire(String indiceSalaire) {
        this.indiceSalaire = indiceSalaire;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setSalaire(Integer salaire) {
        this.salaire = salaire;
    }

    @Override
    public void insererNouveauContact(Connection connection) {

    }
}
