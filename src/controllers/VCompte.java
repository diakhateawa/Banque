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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author user
 */
public class VCompte implements Initializable {

    @FXML
    private TableView<?> tbvCcompte;
    @FXML
    private TableColumn<?, ?> tbcNcompte;
    @FXML
    private TableColumn<?, ?> tbcNCI;
    @FXML
    private TableColumn<?, ?> tbcNom;
    @FXML
    private TableColumn<?, ?> tbcPrenom;
    @FXML
    private TableColumn<?, ?> tbcSolde;
    @FXML
    private TableColumn<?, ?> tbcEtat;
    @FXML
    private TableColumn<?, ?> tbcTypeCompte;
    @FXML
    private TableColumn<?, ?> tbcEmail;
    @FXML
    private TableColumn<?, ?> tbcTelephone;
    @FXML
    private TableColumn<?, ?> tbcAgence;
    @FXML
    private TableColumn<?, ?> tbcDateFin;
    @FXML
    private TableColumn<?, ?> tbcDateCreat;
    @FXML
    private JFXTextField txtNom;
    @FXML
    private JFXPasswordField txtCPassword;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXTextField txtPrenom;
    @FXML
    private JFXTextField txtNci;
    @FXML
    private JFXTextField txtAdresse;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtTelephone;
    @FXML
    private JFXComboBox<?> cbxTypeCompte;
    @FXML
    private JFXTextField txtSolde;
    @FXML
    private DatePicker dateFin;
    @FXML
    private JFXComboBox<?> cbxNCompteBlocage;
    @FXML
    private JFXButton btnEtat;
    @FXML
    private Label lblError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleEnregistrer(ActionEvent event) {
    }

    @FXML
    private void handleVerifier(ActionEvent event) {
    }

    @FXML
    private void handleEtat(ActionEvent event) {
    }
    
}
