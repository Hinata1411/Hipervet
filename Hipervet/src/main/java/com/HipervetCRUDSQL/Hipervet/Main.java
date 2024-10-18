package com.HipervetCRUDSQL.Hipervet;


import com.HipervetCRUDSQL.Hipervet.CRUDS.FichaMascotaCRUD;
import com.HipervetCRUDSQL.Hipervet.GUI.HomeGUI;
import com.HipervetCRUDSQL.Hipervet.GUI.LoginGUI;
import lombok.extern.java.Log;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {

        // Crear y mostrar la ventana de HomeGUI
        SwingUtilities.invokeLater(() -> {
            HomeGUI home = new HomeGUI();
            home.setVisible(true);
        });
    }
}
