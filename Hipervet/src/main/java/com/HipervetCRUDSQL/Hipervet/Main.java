package com.HipervetCRUDSQL.Hipervet;


import com.HipervetCRUDSQL.Hipervet.CRUDS.FichaMascotaCRUD;
import com.HipervetCRUDSQL.Hipervet.GUI.LoginGUI;
import lombok.extern.java.Log;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
	public static void main(String[] args) {

        // Crear y mostrar la ventana de login
        SwingUtilities.invokeLater(() -> {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }



		/*imprimirDatosVPersona imprimirDatos = new imprimirDatosVPersona();
        imprimirDatos.imprimirPersonas();*/
                /*PersonaCRUD personaCRUD = new PersonaCRUD();
                personaCRUD.realizarCRUD();*/
//		ServicioCRUD servicioCRUD = new ServicioCRUD();
//		servicioCRUD.realizarCRUD();
//        FichaMascotaCRUD fichaMascotaCRUD = new FichaMascotaCRUD();
//        fichaMascotaCRUD.realizarCRUD();

}
