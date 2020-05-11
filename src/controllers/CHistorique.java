/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CHistorique implements Initializable {

    @FXML
    private JFXComboBox<?> cbxCompte;
    @FXML
    private DatePicker dateRecherche;
    @FXML
    private JFXComboBox<?> cbxAgence;
    @FXML
    private TableView<?> tbvTransaction;
    @FXML
    private TableColumn<?, ?> tbcDate;
    @FXML
    private TableColumn<?, ?> tbcTypeCompte;
    @FXML
    private TableColumn<?, ?> tbcCSource;
    @FXML
    private TableColumn<?, ?> tbcCCible;
    @FXML
    private TableColumn<?, ?> tbcMontant;
    @FXML
    private TableColumn<?, ?> tbcAgence;
    @FXML
    private TableColumn<?, ?> tbcGuichet;
    @FXML
    private JFXComboBox<?> cbxGuichet;
    @FXML
    private JFXComboBox<?> cbxCaissier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleRechercher(ActionEvent event) {
    }
    
}
