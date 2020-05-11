/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import models.Utilisateur;
import services.SecuriteService;

/**
 *
 * @author user
 */
public class Test {
    public static void main(String[] args) {
        SecuriteService ss=new SecuriteService();
        
        System.out.println(ss.getAgenceByResponsable(ss.seConnecter(new Utilisateur("Am", "passer"))).getLibelle());
    }
    
}
