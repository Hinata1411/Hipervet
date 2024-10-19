package com.HipervetCRUDSQL.Hipervet.GUI;

import javax.swing.*;
import java.awt.*;

public class CitasGUI extends JPanel {
    public CitasGUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Bienvenido a la sección de Citas", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
