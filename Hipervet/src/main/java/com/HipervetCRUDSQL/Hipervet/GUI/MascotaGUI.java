package com.HipervetCRUDSQL.Hipervet.GUI;

import javax.swing.*;
import java.awt.*;

public class MascotaGUI extends JPanel {
    public MascotaGUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Bienvenido a la sección de Mascotas", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
