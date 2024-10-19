package com.HipervetCRUDSQL.Hipervet.GUI;

import com.HipervetCRUDSQL.Hipervet.Conexion.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame implements ActionListener {

        private JLabel labelTitulo, labelUsuario, labelPassword, labelLogo;
        private JTextField textUsuario;
        private JPasswordField textPassword;
        private JButton botonLogin;

        public LoginGUI() {
            // Configuración básica de la ventana
            setTitle("Login HiperVet");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Panel izquierdo verde sólido
            JPanel panelIzquierdoVerde = new JPanel();
            panelIzquierdoVerde.setBackground(Color.decode("#0b750b")); // Color verde sólido
            panelIzquierdoVerde.setPreferredSize(new Dimension(350, 600));
            panelIzquierdoVerde.setLayout(new GridBagLayout()); // Para centrar los elementos

            // Colocar logo
            JPanel panelImagenLogo = new JPanel();
            panelImagenLogo.setOpaque(false); // Hacerlo transparente
            panelImagenLogo.setLayout(new BorderLayout());
            panelImagenLogo.setPreferredSize(new Dimension(200, 200));

            // Cargar imagen del logo
            try {
                ImageIcon iconLogo = new ImageIcon("C:\\Users\\ASUS\\Desktop\\Hipervet\\Hipervet\\src\\main\\java\\com\\HipervetCRUDSQL\\Hipervet\\Resurces\\Logoblanco.png");
                if (iconLogo.getIconWidth() != -1) {
                    Image img = iconLogo.getImage();
                    Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImg);
                    JLabel labelLogoImg = new JLabel(scaledIcon, SwingConstants.CENTER);
                    panelImagenLogo.add(labelLogoImg, BorderLayout.CENTER);
                }
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
            }

            // Panel para campos de texto y botones
            JPanel panelCampos = new JPanel(new GridBagLayout());
            panelCampos.setOpaque(false); // Hacerlo transparente
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 0, 10, 0);

            labelTitulo = new JLabel("Bienvenido");
            labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
            labelTitulo.setForeground(Color.WHITE);
            gbc.gridy = 0;
            panelCampos.add(labelTitulo, gbc);

            labelUsuario = new JLabel("Usuario");
            labelUsuario.setForeground(Color.WHITE);
            gbc.gridy = 1;
            panelCampos.add(labelUsuario, gbc);

            textUsuario = new JTextField(15);
            gbc.gridy = 2;
            panelCampos.add(textUsuario, gbc);

            labelPassword = new JLabel("Contraseña");
            labelPassword.setForeground(Color.WHITE);
            gbc.gridy = 3;
            panelCampos.add(labelPassword, gbc);

            textPassword = new JPasswordField(15);
            gbc.gridy = 4;
            panelCampos.add(textPassword, gbc);

            botonLogin = new JButton("Iniciar Sesión");
            gbc.gridy = 5;
            panelCampos.add(botonLogin, gbc);
            botonLogin.addActionListener(this);

            // Añadir componentes al panel izquierdo verde
            gbc.gridy = 0;
            panelIzquierdoVerde.add(panelImagenLogo, gbc);
            gbc.gridy = 2;
            panelIzquierdoVerde.add(panelCampos, gbc);

            // Añadir el panel izquierdo al contenedor principal
            add(panelIzquierdoVerde, BorderLayout.CENTER);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botonLogin) {
                String nombreUsuario = textUsuario.getText();
                String password = new String(textPassword.getPassword());

                // Verificar credenciales
                try {
                    if (Conexion.verificarCredenciales(nombreUsuario, password)) {
                        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        // Abrir la ventana de Home
                        HomeGUI homeGUI = new HomeGUI();
                        homeGUI.setVisible(true);

                        this.dispose(); // Cerrar la ventana de login
                    } else {
                        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

