package com.example.poo_tp1;

import core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import services.Utils.DateUtils.StringToDateModern;
import services.Utils.PasswordUtils.CheckConnexion;
import services.Utils.WordUtils.RandomStringSimple;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MainFrmController implements Initializable {

    @FXML
    private TextField AddAdress;

    @FXML
    private Button AddAgent;

    @FXML
    private Button AddButtonAgentEnd;

    @FXML
    private Button AddButtonEnseignantEnd;

    @FXML
    private Button AddButtonEtudiantEnd;

    @FXML
    private Label AddCode;

    @FXML
    private Pane AddContactPage1;

    @FXML
    private Pane AddContactPage2;

    @FXML
    private Pane AddContactPageAgentEnd;

    @FXML
    private Pane AddContactPageEnseignantEnd;

    @FXML
    private Pane AddContactPageEtudiantEnd;

    @FXML
    private TextField AddDateNaiss;

    @FXML
    private Button AddEnseignant;

    @FXML
    private Button AddEtudiant;

    @FXML
    private TextField AddMail;

    @FXML
    private TextField AddNom;

    @FXML
    private TextField AddNumero;

    @FXML
    private AnchorPane Addcontact1;

    @FXML
    private BorderPane AjouterContactPage;

    @FXML
    private Button AnnulerButton;

    @FXML
    private Button AnnulerButtonEtudiantEnd;

    @FXML
    private Button AnnulerButtonEtudiantEnd1;

    @FXML
    private Button AnnulerButtonEtudiantEnd11;

    @FXML
    private Button AnnulerButtonPageAddContact2;

    @FXML
    private TextField CategorieAgent;

    @FXML
    private ComboBox<String> ChoixCycle;

    @FXML
    private ComboBox<String > ChoixNiveau;

    @FXML
    private ComboBox<String > ChoixStatut;

    @FXML
    private ComboBox<String> ChoixStatutAgent;

    @FXML
    private TableView<Contact> listeContact;

    @FXML
    private TableColumn<Contact, String> ColCode;

    @FXML
    private TableColumn<Contact, String> ColNom;

    @FXML
    private BorderPane ChoosePage;

    @FXML
    private Label DisplayAdress;

    @FXML
    private Label DisplayAdress1;

    @FXML
    private Label DisplayAdress11;

    @FXML
    private Label DisplaySalaire;

    @FXML
    private AnchorPane DisplayAgentPane;

    @FXML
    private Label DisplayCategorie;

    @FXML
    private Label DisplayCode;

    @FXML
    private Label DisplayCode1;

    @FXML
    private Label DisplayCode11;

    @FXML
    private Label DisplayCycle;

    @FXML
    private Label DisplayDateNaiss;

    @FXML
    private Label DisplayDateNaiss1;

    @FXML
    private Label DisplayDateNaiss11;

    @FXML
    private Label DisplayEmail;

    @FXML
    private Label DisplayEmail1;

    @FXML
    private Label DisplayEmail11;

    @FXML
    private AnchorPane DisplayEnseignantPane;

    @FXML
    private AnchorPane paneUpdateAgent;

    @FXML
    private AnchorPane paneUpdateEns;

    @FXML
    private AnchorPane paneUpdateEns1;


    @FXML
    private AnchorPane paneUpdateEtudiant;


    @FXML
    private AnchorPane DisplayEtudiantPane;

    @FXML
    private Label DisplayIndiceSalaire;

    @FXML
    private Label DisplayNiveau;

    @FXML
    private Label DisplayNom;

    @FXML
    private Label DisplayNom1;

    @FXML
    private Label DisplayNom11;

    @FXML
    private Label DisplayOccupation;

    @FXML
    private Label DisplaySalaire11;

    @FXML
    private Label DisplayStatut;

    @FXML
    private Label DisplayStatutAgent;

    @FXML
    private Label DisplayTel;

    @FXML
    private Label DisplayTel1;

    @FXML
    private Label DisplayTel11;

    @FXML
    private BorderPane ListePage;

    @FXML
    private TextField OccupationAgent;

    @FXML
    private TextField SalaireAgent;

    @FXML
    private TextField IndiceSalaireAgent;

    @FXML
    private TextField SalaireEnseignant;

    @FXML
    private Label StatutAgent;

    @FXML
    private Button ValiderButton;

    @FXML
    private Button ajouterContact;

    @FXML
    private Label displayNomAdd;

    @FXML
    private Label displayNomAdd1;

    @FXML
    private Label displayNomAdd2;

    @FXML
    private BorderPane mainFrmpage;

    @FXML
    private TextField searchContact;

    @FXML
    private TextField userName;

    @FXML
    private TextField userPassword;

    @FXML
    private Button voirListeContact;

    @FXML
    private Button voirListeContact1;


    @FXML
    private TextField UpdateAdresse;

    @FXML
    private TextField UpdateAdresse1;

    @FXML
    private TextField UpdateAdresse11;

    @FXML
    private AnchorPane UpdateAgent;

    @FXML
    private TextField UpdateCategorie;

    @FXML
    private ComboBox<String> UpdateChoix;

    @FXML
    private TextField UpdateCode;

    @FXML
    private TextField UpdateCode1;

    @FXML
    private TextField UpdateCode11;

    @FXML
    private ComboBox<String> UpdateCycle;

    @FXML
    private TextField UpdateEmail;

    @FXML
    private TextField UpdateEmail1;

    @FXML
    private TextField UpdateEmail11;

    @FXML
    private TextField UpdateIndiceSalaire;

    @FXML
    private TextField UpdateNaiss;

    @FXML
    private TextField UpdateNaiss1;

    @FXML
    private TextField UpdateNaiss11;

    @FXML
    private ComboBox<String> UpdateNiveau;

    @FXML
    private TextField UpdateNom;

    @FXML
    private TextField UpdateNom1;

    @FXML
    private TextField UpdateNom11;

    @FXML
    private TextField UpdateNumero;

    @FXML
    private TextField UpdateNumero1;

    @FXML
    private TextField UpdateNumero11;

    @FXML
    private TextField UpdateOccupation;

    @FXML
    private TextField UpdateSalaire;

    @FXML
    private ComboBox<String> UpdateStatutEns;

    @FXML
    private Button ValiderUpdate;

    @FXML
    private Button ValiderUpdate11;

    @FXML
    private Button ValiderUpdateEns;

    private static Contact contact;
    private static String selectedContactCode;
    private static String nom;
    private static String adresse;
    private static String code;
    private static LocalDate dateNaissance;
    private static String email;
    private static String telNumber;
    private static String cycle = null;
    private static Integer niveau = 0;
    private static String statutEns = null;

    private static String statutAg = null;
    private static String categorie;
    private static String salaire;
    private static String indiceSalaire;

    private static String occupation;

    private static Repertoire repertoire = new Repertoire();
    private static ObservableList<Contact> contacts;


    public void Connexion(){
        try{
            if (CheckConnexion.checkConnexion(userName.getText(), userPassword.getText())){
                System.out.println("connexion reussie");
                mainFrmpage.setVisible(false);
                ChoosePage.setVisible(true);

            };
        } catch (Exception e) {
            throw new RuntimeException("Une erreur est survenue lors de la connexion");
        }
    }

    public void SeeListContact(){
        ChoosePage.setVisible(false);
        ListePage.setVisible(true);
    }

    public void SeeAddContact(){
        ListePage.setVisible(false);
        ChoosePage.setVisible(false);
        AjouterContactPage.setVisible(true);
        AddContactPage1.setVisible(true);
        AddContactPage2.setVisible(false);
        code = RandomStringSimple.generateRandomString();
        AddCode.setText(code);
    }

    public void AjouterContact(){
        if (!AddNom.getText().isEmpty() && !AddNumero.getText().isEmpty() && !AddDateNaiss.getText().isEmpty() && !AddMail.getText().isEmpty() && !AddAdress.getText().isEmpty()){
            nom = AddNom.getText();
            telNumber = AddNumero.getText();
            dateNaissance = StringToDateModern.getDate(AddDateNaiss.getText());
            email = AddMail.getText();
            adresse = AddAdress.getText();

            AddContactPage1.setVisible(false);
            AddContactPage2.setVisible(true);
            AddContactPageEnseignantEnd.setVisible(false);
            AddContactPageEtudiantEnd.setVisible(false);
            AddContactPageAgentEnd.setVisible(false);
        }
    }

    public void SelectTypeContactEtudiant(){
        ChoixCycle.getItems().addAll("Ingénieur","LSI");
        ChoixNiveau.getItems().addAll("1","2","3","4","5");
        AddContactPageEtudiantEnd.setVisible(true);
        AddContactPage2.setVisible(false);
    }

    public void AjouterEtudiant(){
        if (niveau != 0 && cycle != null){
            Etudiant etudiant = new Etudiant(nom, code, adresse, email, telNumber, dateNaissance, cycle, niveau);
            try {
                repertoire.AjouterContact(etudiant);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        AjouterContactPage.setVisible(false);
        ChoosePage.setVisible(false);
        ListePage.setVisible(true);
        mainFrmpage.setVisible(false);
        updateData("");
    }

    public void AjouterEnseignant(){
        if (statutEns != null){
            Enseignant enseignant = new Enseignant(nom, code, adresse, email, telNumber, dateNaissance, statutEns);
            try {
                repertoire.AjouterContact(enseignant);
            } catch (Exception e) {
                throw new RuntimeException("Une erreur est survenue lors de la creation d'une nouveau enseignant");
            }
        }
        AjouterContactPage.setVisible(false);
        ChoosePage.setVisible(false);
        ListePage.setVisible(true);
        mainFrmpage.setVisible(false);
        updateData("");
    }

    public void AjouterAgent(){
        if (!SalaireAgent.getText().isEmpty() && statutAg != null && !IndiceSalaireAgent.getText().isEmpty() && !CategorieAgent.getText().isEmpty() && !OccupationAgent.getText().isEmpty()){
            categorie = CategorieAgent.getText();
            salaire = SalaireAgent.getText();
            indiceSalaire = IndiceSalaireAgent.getText();
            occupation = OccupationAgent.getText();
            Agent agent = new Agent(nom, code, adresse, email, telNumber, statutAg, categorie, indiceSalaire, Integer.valueOf(salaire), occupation, dateNaissance);
            try {
                repertoire.AjouterContact(agent);
            } catch (Exception e) {
                throw new RuntimeException("Une erreur est survenue lors de la creation d'une nouveau agent");
            }
        }
        AjouterContactPage.setVisible(false);
        ChoosePage.setVisible(false);
        ListePage.setVisible(true);
        mainFrmpage.setVisible(false);

        updateData("");
    }
    public void SelectTypeContactEnseignant(){
        ChoixStatut.getItems().addAll("Vacataire", "Permanent");
        AddContactPageEnseignantEnd.setVisible(true);
        AddContactPage2.setVisible(false);
    }
    public void SelectTypeContactAgent(){
        ChoixStatutAgent.getItems().addAll("Temporaire","Stagiaire","Permanent");
        AddContactPageAgentEnd.setVisible(true);
        AddContactPage2.setVisible(false);
    }

    public void OnclickAnnulerButton(){
        ChoosePage.setVisible(true);
        ListePage.setVisible(false);
        mainFrmpage.setVisible(false);
        AjouterContactPage.setVisible(false);

    }

    public ObservableList<Contact> chargerContact(String code) {
        contacts = FXCollections.observableArrayList();
        try {
            List<Contact> resultats = repertoire.RechercherContact(code);
            if (resultats != null){
                contacts.addAll(resultats);
                contacts.forEach(ctc -> System.out.println("contact de changer contact : " +ctc.getNom()));
                return contacts;
            }else {
                return null;
            }
        }
        catch (Exception e) {
            // Gestion d'erreur améliorée
            System.err.println("Erreur lors du chargement des contacts: " + e.getMessage());
            e.printStackTrace();
            return null; // Retourne une liste vide observable
        }
    }

    public void updateData(String code){
        ColCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        ColNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        listeContact.setItems(FXCollections.emptyObservableList());
        listeContact.refresh();
        try {
            contacts = FXCollections.emptyObservableList();
            listeContact.setItems(contacts);
            contacts = chargerContact(code);
            contacts.forEach(ctc -> System.out.println("contact: " +ctc.getNom()));
            listeContact.setItems(contacts);
        } catch (Exception e) {
            listeContact.setItems(FXCollections.emptyObservableList());
        }
    }

    public void ModifierAgent(){
        UpdateAgent.setVisible(true);
        paneUpdateAgent.setVisible(true);
        paneUpdateEns.setVisible(false);
        paneUpdateEtudiant.setVisible(false);
        ChoixStatutAgent.getItems().addAll("Temporaire","Stagiaire","Permanent");

        Agent agent = ((Agent) Repertoire.FindPerson(selectedContactCode));

        assert agent != null;
        UpdateCode.setText(agent.getCode());
        UpdateNumero.setText(agent.getTelNumber());
        UpdateNom.setText(agent.getNom());
        UpdateNaiss.setText(agent.getDateNaissance().toString());
        UpdateCategorie.setText(agent.getCategorie());
        UpdateEmail.setText(agent.getEmail());
        UpdateAdresse.setText(agent.getAddress());
        UpdateIndiceSalaire.setText(agent.getIndiceSalaire());
        UpdateOccupation.setText(agent.getOccupation());
        UpdateSalaire.setText(agent.getSalaire().toString());
        statutAg = agent.getStatut();
        UpdateChoix.getItems().addAll("Temporaire","Stagiaire","Permanent");
    }

    public void annulerModifierAgent(){
        UpdateAgent.setVisible(false);
        paneUpdateAgent.setVisible(false);
        paneUpdateEns.setVisible(false);
        paneUpdateEtudiant.setVisible(false);
    }

    public void validerModifierAgent(){
        Agent agent = new Agent(UpdateNom.getText(),UpdateCode.getText(),UpdateAdresse.getText(),UpdateEmail.getText(),UpdateNumero.getText(),statutAg,UpdateCategorie.getText(),UpdateIndiceSalaire.getText(),Integer.parseInt(UpdateSalaire.getText()),UpdateOccupation.getText(), StringToDateModern.getDate(UpdateNaiss.getText()));
        try{
            repertoire.ModifierContact(agent,selectedContactCode);
            searchContact.setText(UpdateCode.getText());
            ActualiserDisplayData(agent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        annulerModifierAgent();
    }

    public void ModifierEtudiant(){
        UpdateAgent.setVisible(true);
        paneUpdateAgent.setVisible(false);
        paneUpdateEns.setVisible(false);
        paneUpdateEtudiant.setVisible(true);
        UpdateCycle.getItems().clear();
        UpdateCycle.getItems().addAll("Ingénieur","LSI");

        Etudiant agent = ((Etudiant) Repertoire.FindPerson(selectedContactCode));

        assert agent != null;
        UpdateCode11.setText(agent.getCode());
        UpdateNumero11.setText(agent.getTelNumber());
        UpdateNom11.setText(agent.getNom());
        UpdateNaiss11.setText(agent.getDateNaissance().toString());
        UpdateEmail11.setText(agent.getEmail());
        UpdateAdresse11.setText(agent.getAddress());
        niveau = agent.getNiveau();
        cycle = agent.getCycle();
        UpdateNiveau.getItems().addAll("1","2","3","4","5");
    }

    public void checkSupprimer(){
        UpdateAgent.setVisible(true);
        paneUpdateEns1.setVisible(true);

    }

    public void annulerSupprimer(){
        UpdateAgent.setVisible(false);
        paneUpdateEns1.setVisible(false);

    }
    public void Supprimer(){
        Contact contact = Repertoire.FindPerson(selectedContactCode);
        try{
            repertoire.SupprimerContact(contact);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        annulerSupprimer();

    }

    public void annulerModifierEtudiant(){
        UpdateAgent.setVisible(false);
        paneUpdateAgent.setVisible(false);
        paneUpdateEns.setVisible(false);
        paneUpdateEtudiant.setVisible(false);

    }

    public void validerModifierEtudiant(){
        Etudiant agent = new Etudiant(UpdateNom11.getText(),UpdateCode11.getText(),UpdateAdresse11.getText(),UpdateEmail11.getText(),UpdateNumero11.getText(),StringToDateModern.getDate(UpdateNaiss11.getText()),cycle,niveau);
        try{
            repertoire.ModifierContact(agent,selectedContactCode);
            searchContact.setText(UpdateCode.getText());
            ActualiserDisplayData(agent);
        } catch (Exception e) {
            throw new RuntimeException("Une erreur est survenue lors de la modification de l'etudiant",e);
        }
        annulerModifierAgent();
    }

    public void Actualiser(){
        updateData("");
    }

    public void ModifierEnseignant(){
        UpdateAgent.setVisible(true);
        paneUpdateAgent.setVisible(false);
        paneUpdateEns.setVisible(true);
        paneUpdateEtudiant.setVisible(false);
        UpdateStatutEns.getItems().addAll("Vacataire","Permanent");

        Enseignant agent = ((Enseignant) Repertoire.FindPerson(selectedContactCode));

        assert agent != null;
        UpdateCode1.setText(agent.getCode());
        UpdateNumero1.setText(agent.getTelNumber());
        UpdateNom1.setText(agent.getNom());
        UpdateNaiss1.setText(agent.getDateNaissance().toString());
        UpdateEmail1.setText(agent.getEmail());
        UpdateAdresse1.setText(agent.getAddress());
        statutEns = agent.getStatut();
    }

    public void annulerModifierEnseignant(){
        UpdateAgent.setVisible(false);
        paneUpdateAgent.setVisible(false);
        paneUpdateEns.setVisible(false);
        paneUpdateEtudiant.setVisible(false);
    }

    public void validerModifierEnseignant(){
        Enseignant agent = new Enseignant(UpdateNom1.getText(),UpdateCode1.getText(),UpdateAdresse1.getText(),UpdateEmail1.getText(),UpdateNumero1.getText(),StringToDateModern.getDate(UpdateNaiss1.getText()),statutEns);
        try{
            repertoire.ModifierContact(agent,selectedContactCode);
            searchContact.setText(UpdateCode.getText());
            ActualiserDisplayData(agent);
        } catch (Exception e) {
            throw new RuntimeException("Une erreur est survenue lors de la modification de l'etudiant",e);
        }
        annulerModifierAgent();
    }

    public void ActualiserDisplayData(Contact contact){
        if (contact instanceof Agent){
            DisplayAgentPane.setVisible(true);
            DisplayEnseignantPane.setVisible(false);
            DisplayEtudiantPane.setVisible(false);
            DisplayCode11.setText(" Code: "+contact.getCode());
            DisplayNom11.setText(" Nom: "+contact.getNom());
            DisplayTel11.setText(" Numéro: "+contact.getTelNumber());
            DisplayDateNaiss11.setText(" DateNaiss: "+contact.getDateNaissance().toString());
            DisplayAdress11.setText(" Adresse: "+contact.getAddress());
            DisplayEmail11.setText(" Email: "+contact.getEmail());
            DisplayStatutAgent.setText(" Statut: "+((Agent) contact).getStatut());
            DisplayCategorie.setText(" Catégorie "+((Agent) contact).getCategorie());
            DisplayIndiceSalaire.setText(" Indice Salaire "+((Agent) contact).getIndiceSalaire());
            DisplayOccupation.setText(" Occupation "+((Agent) contact).getOccupation());
            DisplaySalaire11.setText(" Salaire "+((Agent) contact).getSalaire().toString());
        }
        else if (contact instanceof Enseignant) {
            DisplayAgentPane.setVisible(false);
            DisplayEnseignantPane.setVisible(true);
            DisplayEtudiantPane.setVisible(false);
            DisplayCode1.setText(" Code: "+contact.getCode());
            DisplayNom1.setText(" Nom: "+contact.getNom());
            DisplayTel1.setText(" Numéro: "+contact.getTelNumber());
            DisplayDateNaiss1.setText(" DateNaiss: "+contact.getDateNaissance().toString());
            DisplayAdress1.setText(" Adresse: "+contact.getAddress());
            DisplayEmail1.setText(" Email: "+contact.getEmail());
            DisplayStatut.setText(" Statut: "+((Enseignant) contact).getStatut());
        }
        else if (contact instanceof Etudiant){
            DisplayAgentPane.setVisible(false);
            DisplayEnseignantPane.setVisible(false);
            DisplayEtudiantPane.setVisible(true);
            DisplayCode.setText(" Code: "+contact.getCode());
            DisplayNom.setText(" Nom: "+contact.getNom());
            DisplayTel.setText(" Numéro: "+contact.getTelNumber());
            DisplayDateNaiss.setText(" DateNaiss: "+contact.getDateNaissance().toString());
            DisplayAdress.setText(" Adresse: "+contact.getAddress());
            DisplayEmail.setText(" Email: "+contact.getEmail());
            DisplayCycle.setText(" Cycle: "+((Etudiant) contact).getCycle());
            DisplayNiveau.setText(" Niveau: "+((Etudiant) contact).getNiveau().toString());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DisplayAgentPane.setVisible(false);
        DisplayEnseignantPane.setVisible(false);
        DisplayEtudiantPane.setVisible(false);
        updateData("");

        listeContact.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedContactCode = newValue.getCode();
                        System.out.println("contact sélectionné : " + newValue.getCode());
                        contact = Repertoire.FindPerson(selectedContactCode);

                        ActualiserDisplayData(contact);
                    }

                }
        );

        searchContact.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null && !newValue.isEmpty()) {
                        listeContact.setItems(FXCollections.observableArrayList());
                        listeContact.refresh();
                        updateData(newValue);
                    }else {
                        updateData("");
                    }
                }
        );

        ChoixCycle.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        cycle = newValue;
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );

        ChoixNiveau.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        niveau = Integer.valueOf(newValue);
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );

        UpdateCycle.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        cycle = newValue;
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );

        UpdateNiveau.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        niveau = Integer.valueOf(newValue);
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );

        ChoixStatut.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        statutEns = newValue;
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );
        UpdateStatutEns.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        statutEns = newValue;
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );

        ChoixStatutAgent.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        statutAg = newValue;
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );
        UpdateChoix.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        statutAg = newValue;
//                        System.out.println("Sélection : " + newValue);
                    }
                }
        );
    }
}
