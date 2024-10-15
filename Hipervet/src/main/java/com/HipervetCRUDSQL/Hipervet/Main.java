package com.HipervetCRUDSQL.Hipervet;


import com.HipervetCRUDSQL.Hipervet.CRUDS.FichaMascotaCRUD;
import com.HipervetCRUDSQL.Hipervet.CRUDS.ServicioCRUD;
import com.HipervetCRUDSQL.Hipervet.GUI.LoginUI;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
	public static void main(String[] args) {



		/*imprimirDatosVPersona imprimirDatos = new imprimirDatosVPersona();
        imprimirDatos.imprimirPersonas();*/
                /*PersonaCRUD personaCRUD = new PersonaCRUD();
                personaCRUD.realizarCRUD();*/
//		ServicioCRUD servicioCRUD = new ServicioCRUD();
//		servicioCRUD.realizarCRUD();
        FichaMascotaCRUD fichaMascotaCRUD = new FichaMascotaCRUD();
        fichaMascotaCRUD.realizarCRUD();


	}
}
