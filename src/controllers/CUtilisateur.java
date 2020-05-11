/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.AffectationCaissier;
import models.Agence;
import models.Profil;
import models.Utilisateur;
import services.SecuriteService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CUtilisateur implements Initializable {

    @FXML
    private JFXTextField txtNom;
    @FXML
    private JFXTextField txtPrenom;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXPasswordField txtCPassword;
    @FXML
    private JFXComboBox<Profil> cbxProfil;
    @FXML
    private JFXComboBox<Agence> cbxAgence;
    @FXML
    private TableView<Utilisateur> tbvUtilistaeur;
    @FXML
    private TableColumn<Utilisateur, String> tbcId;
    @FXML
    private TableColumn<Utilisateur, String> tbcNom;
    @FXML
    private TableColumn<Utilisateur, String> tbcPrenom;
    @FXML
    private TableColumn<Utilisateur, String> tbcLogin;
    @FXML
    private TableColumn<Utilisateur, String> tbcEtat;
    @FXML
    private TableColumn<Utilisateur, String> tbcAgence;
    @FXML
    private TableColumn<Utilisateur, String> tbcProfil;
    @FXML
    private JFXComboBox<Utilisateur> cbxUtilisateur;
    @FXML
    private JFXButton btnEtat;
    @FXML
    private JFXComboBox<Agence> cbxAgenceAff;
    @FXML
    private JFXComboBox<Utilisateur> cbxUtilisateurAff;
    @FXML
    private Label lblError;
    @FXML
    private JFXButton btnAjouter;
    @FXML
    private JFXButton btnAfecter;
    SecuriteService service;
    ObservableList<Agence> obAgences;
    ObservableList<Utilisateur> obUtilisateurs;
    ObservableList<Profil> obProfils;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tbcPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tbcLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tbcEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tbcProfil.setCellValueFactory(new PropertyValueFactory<>("profil"));
        tbcAgence.setCellValueFactory(new PropertyValueFactory<>("agence"));
        initData();
    } 
    public void initData()
    {    
        
        service=new SecuriteService();
        
        obAgences=FXCollections.observableArrayList(service.getAllAgences());
        cbxAgence.getItems().addAll(obAgences);
        cbxAgenceAff.getItems().addAll(obAgences);
        
        obProfils=FXCollections.observableArrayList(service.getAllProfilsWoClient());
        cbxProfil.getItems().addAll(obProfils);
        
        List<Utilisateur> utilisateurs=new ArrayList<>();
        utilisateurs.addAll(service.getAllUtilisateurByProfil("Caissier"));
        utilisateurs.addAll(service.getAllUtilisateurByProfil("Responsable"));
        utilisateurs.sort(Comparator.comparing(Utilisateur::getId));
        
        obUtilisateurs=FXCollections.observableArrayList(utilisateurs);
        cbxUtilisateur.getItems().addAll(obUtilisateurs);
        cbxUtilisateurAff.getItems().addAll(obUtilisateurs);
        
        tbvUtilistaeur.setItems(obUtilisateurs);
        
        btnEtat.setDisable(true);
        cbxUtilisateur.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {        
                    if(!cbxUtilisateur.getSelectionModel().isEmpty())
                    {
                        btnEtat.setDisable(false);
                        if(newValue.getEtat().compareToIgnoreCase("Actif")==0)
                        btnEtat.setText("Bloquer");
                        else
                            if(newValue.getEtat().compareToIgnoreCase("Inactif")==0)
                                btnEtat.setText("Débloquer");
                    }   
                    
                    
                }
            );
    }
     private void initTraitementAjout()
    {
        txtNom.setText("");
        txtPrenom.setText("");
        txtLogin.setText("");
        txtPassword.setText("");
        txtCPassword.setText("");
        cbxAgence.getSelectionModel().clearSelection();
        cbxProfil.getSelectionModel().clearSelection();
        
    }
    
    @FXML
    private void handleAjouter(ActionEvent event) {
           if(txtPassword.getText().compareTo(txtCPassword.getText())==0)
        {
            Utilisateur u=new Utilisateur(txtNom.getText(), txtPrenom.getText(), txtLogin.getText(), txtPassword.getText(), cbxProfil.getSelectionModel().getSelectedItem());
            String message="";
            int idU=service.addUtilisateur(u);
            u.setId(idU);
            if(idU!=0)
                message+=" -Utilisateur Enregisté avec succes\n";
            if(!cbxAgence.getSelectionModel().isEmpty())
            {
                int id=0;
                Agence a=cbxAgence.getSelectionModel().getSelectedItem();
                if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                {
                    id=service.affecterCaissierToAgence(new AffectationCaissier(u, a));
                    if(id!=0)
                        message+=" -Affectation 'Caissier' Effectuée avec succes\n";
                }
                else
                {
                    
                    a.setResponsable(u);
                    id=service.updateAgence(a);
                    if(id!=0)
                        message+=" -Affectation 'Responsable' Effectuée avec succes\n";
                }
                u.setEtat(service.getUtilisateurByID(idU).getEtat());
                u.setAvoiragence("Oui");
                idU=service.updateUtilisateur(u);
                
            }
            if(idU!=0)
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
                initTraitementAjout();
                obUtilisateurs.add(u);
                cbxUtilisateur.getItems().add(u);
                cbxUtilisateurAff.getItems().add(u);
            }
        }
        else
        {
            lblError.setVisible(true);
            lblError.setText("Mots de Pass Non Conforme !!!");
        }
    }
       
    @FXML
    private void handleAffecter(ActionEvent event) {
         String message="";
        if(!cbxAgenceAff.getSelectionModel().isEmpty() && !cbxUtilisateurAff.getSelectionModel().isEmpty())
        {
            Utilisateur u=cbxUtilisateurAff.getSelectionModel().getSelectedItem();
            Agence a=cbxAgenceAff.getSelectionModel().getSelectedItem();
            int id=0;
            if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                    {
                        id=service.affecterCaissierToAgence(new AffectationCaissier(u, a));
                        if(id!=0)
                            message+=" -Affectation 'Caissier' Effectuée avec succes\n";
                    }
                    else
                    {

                        a.setResponsable(u);
                        id=service.updateAgence(a);
                        if(id!=0)
                            message+=" -Affectation 'Responsable' Effectuée avec succes\n";
                    }

                    u.setAvoiragence("Oui");
                    int idU=service.updateUtilisateur(u);
                    
                        
        }
        else
        {
            if(cbxAgenceAff.getSelectionModel().isEmpty())
                message+="-Champ +'Agence'+ non séléctionné.\n";
            if(cbxUtilisateur.getSelectionModel().isEmpty())
                message+="-Champ +'Utilisateur'+ non séléctionné.\n";
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message+ "  Veuiller les Séléctionner SVP!!!");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleEtat(ActionEvent event) {
          if(!cbxUtilisateur.getSelectionModel().isEmpty())
        {
            Utilisateur u=cbxUtilisateur.getSelectionModel().getSelectedItem();
            if(u.getEtat().compareToIgnoreCase("Actif")==0)
            {
                u.setEtat("Inactif");
            }
            else
            if(u.getEtat().compareToIgnoreCase("Inactif")==0)
            {
                u.setEtat("Actif");
            }

            int id=service.updateUtilisateur(u);

            
        }

    }
    
    
}
