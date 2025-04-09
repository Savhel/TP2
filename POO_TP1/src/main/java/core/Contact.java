package core;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public abstract class Contact {
    private String nom;
    private String code;
    private String address;
    private String email;
    private String telNumber;
    private LocalDate dateNaissance;

    public Contact() {}

    public Contact(String nom, String code, String address, String email, String telNumber, LocalDate dateNaissance) {
        this.nom = nom;
        this.code = code;
        this.address = address;
        this.email = email;
        this.telNumber = telNumber;
        this.dateNaissance = dateNaissance;
    }
    public abstract void insererNouveauContact(Connection connection) throws Exception;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
