package core;

import Interface.DeleteContact;
import Interface.InsertContact;
import Interface.ReadContact;
import Interface.UpdateContact;
import services.Delete.DeleteAgent;
import services.Delete.DeleteEnseignant;
import services.Delete.DeleteEtudiant;
import services.Insertion.InsertAgent;
import services.Insertion.InsertEnseignant;
import services.Insertion.InsertEtudiant;
import services.Read.ReadAgent;
import services.Read.ReadEnseignant;
import services.Read.ReadEtudiant;
import services.Update.UpdateAgent;
import services.Update.UpdateEnseignant;
import services.Update.UpdateEtudiant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Repertoire {

    private static List<Contact> listContact = new ArrayList<>();

    DeleteContact deleteContact;
    InsertContact insertContact;
    UpdateContact updateContact;
    ReadContact readContact;
    ReadContact readContact1;
    ReadContact readContact2;

    public Repertoire() {

    }

    public void AjouterContact(Contact contact) throws SQLException {
        if (contact instanceof Etudiant){
            insertContact = new InsertEtudiant((Etudiant) contact);
            insertContact.insertContact();
//            listEtudiant.add((Etudiant) contact);
        } else if (contact instanceof Enseignant) {
            insertContact = new InsertEnseignant((Enseignant) contact);
            insertContact.insertContact();
        } else if (contact instanceof Agent) {
            insertContact = new InsertAgent((Agent) contact);
            insertContact.insertContact();
        }
    }

    public void SupprimerContact(Contact contact) throws Exception {
        if (contact instanceof Etudiant){
            deleteContact = new DeleteEtudiant((Etudiant) contact);
            deleteContact.deleteContact();
//            listEtudiant.add((Etudiant) contact);
        } else if (contact instanceof Enseignant) {
            deleteContact = new DeleteEnseignant((Enseignant) contact);
            deleteContact.deleteContact();
        } else if (contact instanceof Agent) {
            deleteContact = new DeleteAgent((Agent) contact);
            deleteContact.deleteContact();
        }
    }

    public void ModifierContact(Contact contact, String code) throws Exception {
        if (contact instanceof Etudiant){
            updateContact = new UpdateEtudiant((Etudiant) contact);
            updateContact.updateContact(code);
//            listEtudiant.add((Etudiant) contact);
        } else if (contact instanceof Enseignant) {
            updateContact = new UpdateEnseignant((Enseignant) contact);
            updateContact.updateContact(code);
        } else if (contact instanceof Agent) {
            updateContact = new UpdateAgent((Agent) contact);
            updateContact.updateContact(code);
        }
    }

    public List<Contact> RechercherContact(String code) throws Exception {
        System.out.println("test");
        readContact = new ReadEtudiant();
        readContact1 = new ReadAgent();
        readContact2 = new ReadEnseignant();
        ResultSet resultSet = readContact.readContact(code);
        ResultSet resultSet1 = readContact1.readContact(code);
        ResultSet resultSet2 = readContact2.readContact(code);
        listContact.clear();

        while (resultSet.next()) {
            Etudiant contact = new Etudiant();
            contact.setNom(resultSet.getString("Nom"));
            contact.setCode(resultSet.getString("Code"));
            contact.setAddress(resultSet.getString("Adresse"));
            contact.setEmail(resultSet.getString("Email"));
            contact.setTelNumber(resultSet.getString("TelNumber"));
            contact.setDateNaissance(resultSet.getDate("DateNaissance").toLocalDate());
            contact.setCycle(resultSet.getString("Cycle"));
            contact.setNiveau(resultSet.getInt("Niveau"));
            listContact.add(contact);
        }
        while (resultSet1.next()) {
            Agent contact = new Agent();
            contact.setNom(resultSet1.getString("Nom"));
            contact.setCode(resultSet1.getString("Code"));
            contact.setAddress(resultSet1.getString("Adresse"));
            contact.setEmail(resultSet1.getString("Email"));
            contact.setTelNumber(resultSet1.getString("TelNumber"));
            contact.setDateNaissance(resultSet1.getDate("DateNaissance").toLocalDate());
            contact.setCategorie(resultSet1.getString("Categorie"));
            contact.setIndiceSalaire(resultSet1.getString("IndiceSalaire"));
            contact.setOccupation(resultSet1.getString("Occupation"));
            contact.setStatut(resultSet1.getString("Statut"));
            contact.setSalaire(resultSet1.getInt("Salaire"));
            listContact.add(contact);
        }
        while (resultSet2.next()) {
            Enseignant contact = new Enseignant();
            contact.setNom(resultSet2.getString("Nom"));
            contact.setCode(resultSet2.getString("Code"));
            contact.setAddress(resultSet2.getString("Adresse"));
            contact.setEmail(resultSet2.getString("Email"));
            contact.setTelNumber(resultSet2.getString("TelNumber"));
            contact.setDateNaissance(resultSet2.getDate("DateNaissance").toLocalDate());
            contact.setStatut(resultSet2.getString("Statut"));
            listContact.add(contact);
        }
        return listContact.stream().sorted(Comparator.comparing(Contact::getNom, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)).thenComparing(Contact::getEmail, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))).toList();
    }

    public static Contact FindPerson(String searchTerm) {

        if (searchTerm.startsWith("AGE")){
            try{
                ReadContact readContact = new ReadAgent();
                ResultSet resultSet1 = readContact.findContact(searchTerm);
//                while (resultSet1.next()){
//                    System.out.println("Nom : "+resultSet1.getString("Nom")  + resultSet1.getString("Code")+ resultSet1.getString("Adresse") + resultSet1.getString("Email") + resultSet1.getString("TelNumber") + resultSet1.getDate("DateNaissance").toLocalDate() + resultSet1.getString("Categorie") +
//                            resultSet1.getString("IndiceSalaire") + resultSet1.getString("Occupation") + resultSet1.getString("Statut") + resultSet1.getInt("Salaire"));
//                }
                Agent contact = new Agent();
                while (resultSet1.next()){
                    contact.setNom(resultSet1.getString("Nom"));
                    contact.setCode(resultSet1.getString("Code"));
                    contact.setAddress(resultSet1.getString("Adresse"));
                    contact.setEmail(resultSet1.getString("Email"));
                    contact.setTelNumber(resultSet1.getString("TelNumber"));
                    contact.setDateNaissance(resultSet1.getDate("DateNaissance").toLocalDate());
                    contact.setCategorie(resultSet1.getString("Categorie"));
                    contact.setIndiceSalaire(resultSet1.getString("IndiceSalaire"));
                    contact.setOccupation(resultSet1.getString("Occupation"));
                    contact.setStatut(resultSet1.getString("Statut"));
                    contact.setSalaire(resultSet1.getInt("Salaire"));

                    return contact;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if (searchTerm.startsWith("ENS")) {
                try{
                    ReadContact readContact = new ReadEnseignant();
                    ResultSet resultSet1 = readContact.findContact(searchTerm);
                    Enseignant contact = new Enseignant();
                    while (resultSet1.next()){
                        contact.setNom(resultSet1.getString("Nom"));
                        contact.setCode(resultSet1.getString("Code"));
                        contact.setAddress(resultSet1.getString("Adresse"));
                        contact.setEmail(resultSet1.getString("Email"));
                        contact.setTelNumber(resultSet1.getString("TelNumber"));
                        contact.setDateNaissance(resultSet1.getDate("DateNaissance").toLocalDate());
                        contact.setStatut(resultSet1.getString("Statut"));
                        return contact;
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }else if (searchTerm.startsWith("ETU")) {
            try{
                ReadContact readContact = new ReadEtudiant();
                ResultSet resultSet = readContact.findContact(searchTerm);
                Etudiant contact = new Etudiant();
                while (resultSet.next()){
                    contact.setNom(resultSet.getString("Nom"));
                    contact.setCode(resultSet.getString("Code"));
                    contact.setAddress(resultSet.getString("Adresse"));
                    contact.setEmail(resultSet.getString("Email"));
                    contact.setTelNumber(resultSet.getString("TelNumber"));
                    contact.setDateNaissance(resultSet.getDate("DateNaissance").toLocalDate());
                    contact.setCycle(resultSet.getString("Cycle"));
                    contact.setNiveau(resultSet.getInt("Niveau"));
                    return contact;
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
