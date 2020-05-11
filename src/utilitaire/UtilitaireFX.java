/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

/**
 *
 * @author user
 */
public class UtilitaireFX {
    public void changeView(Label lbl,String view) throws IOException{
        lbl.getScene().getWindow().hide();
        String chaineV = "/views/"+view+".fxml";
        Parent root = FXMLLoader.load(getClass().getResource(chaineV));
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Gestion de Transfert Argent");
        stage.show();
    }
    
    public void showView(AnchorPane anchorContent,String view ) throws IOException{
        String chaineV = "/views/"+view+".fxml";
        AnchorPane root = FXMLLoader.load(getClass().getResource(chaineV));
        
        anchorContent.getChildren().clear();
        anchorContent.getChildren().add((Node)root);
    }

    public void closeButton(Button btn) {
        // get a handle to the stage
        Stage stage = (Stage) btn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
