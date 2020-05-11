/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.Utilisateur;
import services.SecuriteService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CDashboard implements Initializable {
 
    @FXML
    private Text lblPrenom;
    @FXML
    private Text lblNom;
    @FXML
    private Label lblProfil;
   
    @FXML
    private Label lblAgence;
    @FXML
    private Pane panelAdministrateur;
    @FXML
    private Pane panelResponsable;
    @FXML
    private Pane panelCaissier;
    @FXML
    private Pane panelClient;
    private static CDashboard instance;
    @FXML
    private Label lblWelcome;
    @FXML
    private JFXButton btnDeconnexion;
    private static Utilisateur utilisateurConnect;
    
    SecuriteService securite;
    private String viewName;
    
    private final Map<String,Pane> mapPanel=new HashMap<>();
    @FXML
    private AnchorPane anchorPane;
    
    
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        securite=new SecuriteService();
        utilisateurConnect = CLogin.getInstance().getUtilisateur();
        initData();
        verfProfil(utilisateurConnect.getProfil().getLibelle());
        instance=this;
        
    } 
      private void initData()
    {
        addMapPanels();
        if(utilisateurConnect.getProfil().getLibelle().compareTo("Administrateur")==0||utilisateurConnect.getProfil().getLibelle().compareTo("Client")==0)
        {
            lblAgence.setText("");
        }else{
            if(utilisateurConnect.getProfil().getLibelle().compareTo("Responsable")==0)
                lblAgence.setText(securite.getAgenceByResponsable(utilisateurConnect).getLibelle());
            else
                lblAgence.setText(securite.getAgenceByCaissier(utilisateurConnect).getLibelle());
        }
        lblProfil.setText(utilisateurConnect.getProfil().toString());
        lblNom.setText(utilisateurConnect.getNom());
        lblPrenom.setText(utilisateurConnect.getPrenom());
    }
       private void addMapPanels(){
        mapPanel.put("Client",panelClient);
        mapPanel.put("Caissier",panelCaissier);
        mapPanel.put("Responsable",panelResponsable);
        mapPanel.put("Administrateur",panelAdministrateur);
    }
    
    private void verfProfil(String profil){
        boolean bol = utilisateurConnect.getProfil().getLibelle().compareToIgnoreCase(profil)==0;
        panelClient.setDisable(true);
        panelCaissier.setDisable(true);
        panelResponsable.setDisable(true);
        panelAdministrateur.setDisable(true);
        if(bol){
            mapPanel.get(profil).setDisable(false);
            lblWelcome.setText("Bienvenu Dans Votre Agence Bancaire !!!");
            if(profil.compareToIgnoreCase("Caissier")==0 && securite.ifCaissierWorks(utilisateurConnect)==false)
            {
                mapPanel.get(profil).setDisable(true);
                lblWelcome.setText("Vous n'avez pas été planifié pour Aujourd'hui !!!");                
            }
        }else {
        panelClient.setDisable(false);
        panelCaissier.setDisable(false);
        panelResponsable.setDisable(false);
        panelAdministrateur.setDisable(false);
        }
    }

    @FXML
    private void handleProfil(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorPane, "VProfil");
    }

    @FXML
    private void handleDeconnexion(ActionEvent event) throws IOException {
        CLogin.getUtils().changeView(lblProfil, "VLogin");
    }

    @FXML
    private void handleHistorique(MouseEvent event) {
    }

    @FXML
    private void handleGuichet(MouseEvent event) throws IOException {
         CLogin.getUtils().showView(anchorPane, "VGuichet");
    }

    @FXML
    private void handleAgence(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorPane, "VAgence");
      
    }

    @FXML
    private void handleUtilisateur(MouseEvent event) throws IOException {
         CLogin.getUtils().showView(anchorPane, "VUtilisateur");
    }


    @FXML
    private void handleCompte(MouseEvent event) throws IOException {
         CLogin.getUtils().showView(anchorPane, "VCompte");
    }

    @FXML
    private void handleTransaction(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorPane, "Transaction");
    }

    @FXML
    private void handleDepot(MouseEvent event) throws IOException {
        viewName="Dépot";
        CLogin.getUtils().showView(anchorPane,"responsable/VTransaction");
    }

    @FXML
    private void handleRetrait(MouseEvent event) throws IOException {
        viewName="Dépot";
        CLogin.getUtils().showView(anchorPane,"responsable/VTransaction");
    }

    @FXML
    private void handleVirement(MouseEvent event) throws IOException {
        viewName="Dépot";
        CLogin.getUtils().showView(anchorPane,"responsable/VTransaction");
    }

}

   
  
    

