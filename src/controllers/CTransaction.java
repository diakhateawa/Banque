/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import models.Compte;
import models.Guichet;
import models.Transaction;
import models.Utilisateur;
import services.SystemeService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CTransaction implements Initializable {
    private static Utilisateur utilisateurConnect;

    private Label lblTitle;
    @FXML
    private JFXTextField txtMontant;
    private Separator vM2;
    @FXML
    private Label lblClient;
    @FXML
    private Label lblTransaction;
    @FXML
    private JFXButton btnClient;
    @FXML
    private JFXButton btnBeneficiaire;
    @FXML
    private JFXTextField txtNCompteBeneficiaire;
    @FXML
    private Label lblBeneficiaire;
    @FXML
    private JFXButton btnAfficher;
    @FXML
    private JFXButton btnEnregistrerTransaction;
    @FXML
    private JFXButton btnAnnulerTransaction;
    @FXML
    private JFXTextField txtNCompteClient;
    SystemeService service;
    String title;
    int x,y,z;
    Compte CClient,CBeneficiaire;
    double val;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        utilisateurConnect = CLogin.getInstance().getUtilisateur();
        
        service=new SystemeService();
        String tmp=lblTitle.getText();
        //title=CDashboard.getInstance().getViewName();
        lblTitle.setText(tmp+" "+title);
        x=0;y=0;z=0;
        initData();
    }    
    private void initData()
    {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            
        }
        else
        {
            btnBeneficiaire.setVisible(false);
            txtNCompteBeneficiaire.setVisible(false);
            lblBeneficiaire.setVisible(false);
            vM2.setVisible(false);
        }
    }
    private void initForm()
    {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            txtNCompteBeneficiaire.setText("");
            lblBeneficiaire.setText("");
            y=0;
        }
        txtNCompteClient.setText("");
        lblClient.setText("");
        
        txtMontant.setText("");
        lblTransaction.setText("");
        x=0;
        z=0;
        
    }
    public void activation(boolean bl)
    {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            txtNCompteBeneficiaire.setDisable(bl);
            btnBeneficiaire.setDisable(bl);
            lblBeneficiaire.setDisable(bl);
        }
        txtNCompteClient.setDisable(bl);
        btnClient.setDisable(bl);
        lblClient.setDisable(bl);
        
        txtMontant.setDisable(bl);
        btnAfficher.setDisable(bl);
    }
    public String chargerInfo(Compte c)
    {
        return "\t"+c.getNumero().toUpperCase()+"\n"
                + "\t"+c.getNom().toUpperCase()+"\n"
                + "\t"+c.getPrenom().toUpperCase()+"\n"
                + "\t"+c.getTypeCompte().toUpperCase()+"\n";
    }
    private int showInfo(Compte c,Label l,int test)
     {
        if(c!=null)
        {
            l.setText(chargerInfo(c));
            test=1;
        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ce N°compte n'existe pas.");
            alert.showAndWait();
            test=0;
        }
        return test;
    }



    @FXML
    private void handleAfficherTransaction(ActionEvent event) {
          if(title.compareToIgnoreCase("Virement")==0)
        {
            if(x==1 && y==1)
            {
                if(txtMontant.getText().isEmpty())
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Vueiller Saisir le Montant de ce virement SVP!!!");
                    alert.showAndWait();
                }else
                {
                    CBeneficiaire=service.getCompteByNumero(txtNCompteBeneficiaire.getText());
                    CClient=service.getCompteByNumero(txtNCompteClient.getText());
                    val=Double.parseDouble(txtMontant.getText());
                    if(CClient.getId()!=CBeneficiaire.getId())
                    {
                    lblTransaction.setText("***********************************************\n"+
                    title+":\nDe:"+lblClient.getText()+
                    "***********************************************\n"
                    +title+":\nVers:"+CBeneficiaire.getNumero()+
                    "\n***********************************************\n"
                            +"Montant"+":\t"+val+" FCFA\n"
                                    + "***********************************************");                
                    z=1;
                    activation(true);
                    }
                    else
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Virement ne peut être effectué sur le même Compte!");
                        alert.showAndWait();
                    }
                }
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vueiller Charger tous les numéros de comptes concernés par ce virement!");
                alert.showAndWait();
            }
        }
        else
        {
            if(x==1)
            {
                if(txtMontant.getText().isEmpty())
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Vueiller Saisir le Montant de ce "+title+" SVP!!!");
                    alert.showAndWait();
                }else
                {
                    CClient=service.getCompteByNumero(txtNCompteClient.getText());
                    val=Double.parseDouble(txtMontant.getText());
                    lblTransaction.setText("***********************************************\n"+
                    title+":\n"+lblClient.getText()+
                    "***********************************************\n"
                            +"Montant"+":\t"+val+" FCFA\n"
                                    + "***********************************************");
                    z=1;
                    activation(true);
                }
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vueiller Charger le numéro de compte client concernés par ce "+title+"!");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void handleEnregistrerTransaction(ActionEvent event) {
          if(z==1)
        {
            String message="Opération Impossible !!!\n";
            switch(title)
            {
                case "Virement":
                    faireTransactionVirement(CClient,CBeneficiaire,message);
                    break;
                default:
                    if(title.compareToIgnoreCase("Retrait")==0)
                    {
                        faireTransactionRetrait(CClient,message);
                    }
                    else
                    {
                        faireTransactionDepot(CClient,message);
                    }
                    break;
            }
            initForm();
            activation(false);
        }
        else  
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vueiller 'Afficher' les informations avant d'Enregistrer'");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAnnulerTransaction(ActionEvent event) {
         initForm();
        activation(false);
    }
    public void faireTransactionVirement(Compte CClient,Compte cBnf,String message)
    {
        if(CClient!=null && CClient.getTypeCompte().compareToIgnoreCase("Courant")==0)
        {
            double x=CClient.getSolde()-val;
            if(x>=0)
            {
                CClient.setSolde(x);
                service.updateCompte(CClient);
                if(CBeneficiaire!=null)
                {
                    cBnf.setSolde(cBnf.getSolde()+val);
                    service.updateCompte(cBnf);
                }
                Utilisateur u=utilisateurConnect;
                Guichet g=null;
                if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                    g=service.getGuichetByCaissierByOneDate(u,LocalDate.now());                               

                Transaction trans=new Transaction(title, val, CClient, CBeneficiaire, g);
                service.addTransaction(trans);
                message="- '"+title+"' Effectué avec Success";
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
            }
            else
            {
                
            }
        }
        else
        {
            message+=" -Impossible de Débiter le compte est un Compte Epargne !!!";
            
        }        
        
        
    }
    public void faireTransactionRetrait(Compte CClient,String message)
    {
        if(CClient!=null && CClient.getTypeCompte().compareToIgnoreCase("Courant")==0)
        {
            double w=CClient.getSolde()-val;
            if(w>=0)
            {
                CClient.setSolde(w);
                service.updateCompte(CClient);
                
                Utilisateur u=utilisateurConnect;
                Guichet g=null;
                if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                    g=service.getGuichetByCaissierByOneDate(u,LocalDate.now());                               

                Transaction trans=new Transaction(title, val, CClient, null, g);
                service.addTransaction(trans);
                message="- '"+title+"' Effectué avec Success";
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
            }
            else
            {
                message+=" -Solde Demandeur Insuffisant !!!";
            }
        }
        else
        {
            message+=" -Impossible de Débiter le compte est un Compte Epargne !!!";
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.showAndWait();
        }        
        
        
    }
    public void faireTransactionDepot(Compte CClient,String message)
    {
        if(CClient!=null && CClient.getTypeCompte().compareToIgnoreCase("Courant")==0)
        {
            double w=CClient.getSolde()+val;
            
            CClient.setSolde(w);
            service.updateCompte(CClient);

            Utilisateur u=utilisateurConnect;
            Guichet g=null;
            if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                g=service.getGuichetByCaissierByOneDate(u,LocalDate.now());                               

            Transaction trans=new Transaction(title, val, CClient, null, g);
            service.addTransaction(trans);
            message="- '"+title+"' Effectué avec Success";
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(message);
            alert.showAndWait();
            
        }
        else
        {
            message+=" -Impossible de Débiter le compte est un Compte Epargne !!!";
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.showAndWait();
        }        
        
        
    }

    @FXML
    private void handleChargerBeneficiaire(ActionEvent event) {
         y=showInfo(service.getCompteByNumero(txtNCompteBeneficiaire.getText()),lblBeneficiaire,y);
        txtNCompteBeneficiaire.setDisable(true);
    }

    @FXML
    private void handleChargerClient(ActionEvent event) {
        x=showInfo(service.getCompteByNumero(txtNCompteClient.getText()),lblClient,x);
        txtNCompteClient.setDisable(true);
    }
    
}
