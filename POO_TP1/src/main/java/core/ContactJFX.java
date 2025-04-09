package core;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class ContactJFX {
    private String nom;
    private String code;

    public ContactJFX(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }



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


}
