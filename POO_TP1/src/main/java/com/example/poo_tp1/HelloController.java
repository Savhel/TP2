package com.example.poo_tp1;

import core.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.sql.Connection;

public class HelloController {

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
    private ComboBox<?> ChoixCycle;

    @FXML
    private ComboBox<?> ChoixNiveau;

    @FXML
    private ComboBox<?> ChoixStatut;

    @FXML
    private ComboBox<?> ChoixStatutAgent;

    @FXML
    private BorderPane ChoosePage;

    @FXML
    private Label DisplayAdress;

    @FXML
    private Label DisplayAdress1;

    @FXML
    private Label DisplayAdress11;

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
    private Label DisplaySalaire;

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
    private Button voirListeContact11;

    private Connection connection = DatabaseConnection.getInstance().getConnection();






}
