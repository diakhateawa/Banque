/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.Utilisateur;
import services.SecuriteService;
import utilitaire.UtilitaireFX;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CLogin implements Initializable {

    @FXML
    private Label lblLogin;
    @FXML
    private Label lblPassword;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXButton btnAnnuler;
    @FXML
    private JFXPasswordField txtPassword;
    private Text txtError;
    SecuriteService securite;
    private static CLogin instance;
    private Utilisateur u;
    private static UtilitaireFX utils = new UtilitaireFX();
      public static UtilitaireFX getUtils() {
        return utils;
    }
      
    public static CLogin getInstance() {
        return instance;
    }
    @FXML
    private JFXButton btnConnexion;
     public Utilisateur getUtilisateur() {
        return u;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       securite=new SecuriteService();
        instance=this;
    } 
    @FXML
    private void handleConnexion(ActionEvent event) throws IOException {
        if(txtLogin.getText().isEmpty() || txtPassword.getText().isEmpty()){
            txtError.setText("Login et Mot de passe Obligatoire");
            txtError.setVisible(true);
        }else{
             u=securite.seConnecter(new Utilisateur(txtLogin.getText(), txtPassword.getText()));
         
                 if(u==null){
                 txtError.setText("Login ou Mot de passe Incorrect");
                  txtError.setVisible(true);
             }else{
                  if(u.getEtat().compareToIgnoreCase("Actif")==0){
                       
                         CLogin.getUtils().changeView(lblLogin, "VDashboard");
                         
                }else{
                       txtError.setText("Utilisateur bloqué");
                     txtError.setVisible(true);
                }
             }
        }
    }

    @FXML
    private void handleAnnuler(ActionEvent event) {
        utils.closeButton(btnAnnuler);
    }
    
    
}
