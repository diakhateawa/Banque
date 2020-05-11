/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.Affectation;
import models.Agence;
import models.Guichet;
import models.Utilisateur;
import services.SecuriteService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CGuichet implements Initializable {

    @FXML
    private TableView<Affectation> tbvGuichet;
    @FXML
    private TableColumn<Affectation, String> tbcId;
    @FXML
    private TableColumn<Affectation, Guichet> tbcGuichet;
    @FXML
    private TableColumn<Affectation, Agence> tbcAgence;
    @FXML
    private TableColumn<Affectation, Utilisateur> tbcCaissier;
    @FXML
    private TableColumn<Affectation, String> tbcDebut;
    @FXML
    private TableColumn<Affectation, String> tbcFin;
    @FXML
    private JFXComboBox<Guichet> cbxGuichet;
    @FXML
    private JFXComboBox<Utilisateur> cbxCaissier;
    @FXML
    private JFXComboBox<Agence> cbxAgenceRecherche;
    @FXML
    private DatePicker dateRecherche;
    @FXML
    private JFXComboBox<Utilisateur> cbxCaissierRecherche;
    @FXML
    private Pane panelAdministrateur;
    @FXML
    private JFXComboBox<Agence> cbxAgence;
    @FXML
    private JFXTextField txtLibelle;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    SecuriteService service;
    ObservableList<Agence> obAgences;
    ObservableList<Utilisateur> obCaissiers;
    ObservableList<Affectation> obAffectations;
    int ok=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcGuichet.setCellValueFactory(new PropertyValueFactory<>("guichet"));
        tbcAgence.setCellValueFactory(new PropertyValueFactory<>("agence"));
        tbcDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        tbcFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        tbcCaissier.setCellValueFactory(new PropertyValueFactory<>("caissier"));
        initData();
        
    }
    private void initData(){
        service=new SecuriteService();
        
        obAgences=FXCollections.observableArrayList(service.getAllAgences());
        cbxAgence.getItems().addAll(obAgences);
        
        if(CLogin.getInstance().getUtilisateur().getProfil().getLibelle().compareToIgnoreCase("Responsable")==0)
        {
            Agence a=service.getAgenceByResponsable(CLogin.getInstance().getUtilisateur());
            cbxAgenceRecherche.getItems().add(a);
            cbxAgenceRecherche.getSelectionModel().selectFirst();
            cbxCaissierRecherche.getItems().
                addAll(service.getCaissierByAgence(a));
            cbxCaissier.getItems().
                addAll(service.getCaissierByAgence(a));
            cbxGuichet.getItems().addAll(service.getAllGuichetsByAgence(a));
            
            panelAdministrateur.setDisable(true);
            cbxAgenceRecherche.setDisable(true);
            
            
            obAffectations=FXCollections.observableArrayList(service.getAffectationsByAgenece(a));
            tbvGuichet.setItems(obAffectations);
            
        }
        else if(CLogin.getInstance().getUtilisateur().getProfil().getLibelle().compareToIgnoreCase("Administrateur")==0)
        {
            
            cbxAgenceRecherche.getItems().addAll(obAgences);
            cbxAgenceRecherche.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    if(ok>0)
                    {
                        cbxCaissierRecherche.getItems().clear();
                        cbxGuichet.getItems().clear();
                        cbxCaissier.getItems().clear();
                    }
                    
                    cbxCaissierRecherche.getItems().addAll(service.getCaissierByAgence(newValue));
                    cbxGuichet.getItems().addAll(service.getAllGuichetsByAgence(newValue));
                    cbxCaissier.getItems().addAll(service.getCaissierByAgence(newValue));
                    ok++;
                    
                    obAffectations=FXCollections.observableArrayList(service.getAffectationsByAgenece(newValue));
                    tbvGuichet.setItems(obAffectations);
                    
                }
            );
        }
    }
    private void initLabel()
    {
        txtLibelle.setText("");
        cbxAgence.getSelectionModel().clearSelection();
    }
       

    @FXML
    private void handleAffecter(ActionEvent event) {
         
        Utilisateur caissier=cbxCaissier.getSelectionModel().getSelectedItem();
        Guichet guichet=cbxGuichet.getSelectionModel().getSelectedItem();
        guichet.setEtat("Indisponible");
        int degrees=service.AnalyseAffectation(guichet, caissier, dateDebut.getValue(), dateFin.getValue());
        if(degrees==0)
        {
            Affectation  a=new Affectation(dateDebut.getValue(), dateFin.getValue(), caissier, guichet);
            
            int id=service.addAffectation(a);
        
            if(id!=0){
               Alert alert=new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("Caissier: "+caissier.toString()+" Affecté au Guichet: "+guichet.getNumero());
               alert.showAndWait();
               a.setId(id);
               obAffectations.add(a);
            }
        }
        else
        {
            if(degrees==1)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuiller Vérifier les dates(Début & fin) de cette affectation SVP!!!");
                alert.showAndWait();
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Caissier/Guichet Indisponible pendant cette Période.\n"
                                        + "Veuiller modifier les dates de cette affectation SVP!!!");
                alert.showAndWait();
            }
            
        }
    }

    @FXML
    private void handleRechercher(ActionEvent event) {
          if(cbxAgenceRecherche.getSelectionModel().isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Champ de recherche +'Agence'+ non séléctionné.\n"
                                        + "Veuiller en Séléctionner un SVP!!!");
                alert.showAndWait();
        }
        else
        {
            obAffectations.clear();
            obAffectations.addAll(service.getAffectationsByAgenece(cbxAgenceRecherche.getSelectionModel().getSelectedItem()));
            if(!cbxCaissierRecherche.getSelectionModel().isEmpty())
            {
                obAffectations.clear();
                obAffectations.addAll(service.getAffectationByCaissier(cbxCaissierRecherche.getSelectionModel().getSelectedItem()));
            }
            if(dateRecherche.getValue()!=null)
            {
                List<Affectation>l=new ArrayList<>();
                l.addAll(obAffectations);
                obAffectations.clear();
                obAffectations.addAll(service.getAllAffectationsByOneDate(l, dateRecherche.getValue()));        
            }
        }
    
    }

    @FXML
    private void handleAjouter(ActionEvent event) {
        Agence agence=cbxAgence.getSelectionModel().getSelectedItem();
        
        Guichet guichet=new Guichet(txtLibelle.getText(), "Disponible", agence);
        
        int id=service.addGuichet(guichet);
        if(id!=0){
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("Guichet Enregisté avec succees");
           alert.showAndWait();
           guichet.setId(id);
           initLabel();
        }
    }
    
}
