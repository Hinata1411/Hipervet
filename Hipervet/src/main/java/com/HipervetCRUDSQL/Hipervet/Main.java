package com.HipervetCRUDSQL.Hipervet;


import com.HipervetCRUDSQL.Hipervet.CRUDS.FichaMascotaCRUD;
import com.HipervetCRUDSQL.Hipervet.GUI.HomeGUI;
import com.HipervetCRUDSQL.Hipervet.GUI.LoginGUI;
import lombok.extern.java.Log;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {
        // Iniciar la aplicación en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // Crear y mostrar la ventana de login
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }
}
