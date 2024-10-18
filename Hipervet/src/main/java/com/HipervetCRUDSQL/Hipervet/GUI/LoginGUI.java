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

            // Crear un panel principal con gradiente
            GradientPanel panelPrincipal = new GradientPanel(Color.decode("#750b19"), Color.decode("#FFFFFF"));
            panelPrincipal.setLayout(new BorderLayout());
            panelPrincipal.setPreferredSize(new Dimension(800, 600)); // Aseguramos que tenga un tamaño preferido

            // Panel para los campos de login
            JPanel panelCentral = new JPanel();
            panelCentral.setLayout(new GridBagLayout());
            panelCentral.setOpaque(false); // Hacerlo transparente

            // Colocar logo
            JPanel panelImagenLogo = new JPanel();
            panelImagenLogo.setOpaque(false); // Hacerlo transparente
            panelImagenLogo.setLayout(new BorderLayout());
            panelImagenLogo.setPreferredSize(new Dimension(200, 200));

            try {
                ImageIcon iconLogo = new ImageIcon("C:\\Users\\ASUS\\Downloads\\CRUD-MVC-Java-MySQL-main\\CRUD-MVC-Java-MySQL-main\\src\\Recursos\\Solologo.png");
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

            // Agregar el logo al panel central
            labelLogo = new JLabel("HiperVet", SwingConstants.CENTER);
            labelLogo.setFont(new Font("Arial", Font.BOLD, 30));
            labelLogo.setForeground(Color.WHITE);

            // Panel para los campos de texto y botones
            JPanel panelCampos = new JPanel(new GridBagLayout());
            panelCampos.setOpaque(false); // Aseguramos que sea transparente
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

            // Añadir componentes al panel central
            gbc.gridy = 0;
            panelCentral.add(panelImagenLogo, gbc);
            gbc.gridy = 1;
            panelCentral.add(labelLogo, gbc);
            gbc.gridy = 2;
            panelCentral.add(panelCampos, gbc);

            // Panel derecho (Imagen)
            JPanel panelImagen = new JPanel();
            panelImagen.setOpaque(false); // Hacerlo transparente
            panelImagen.setLayout(new BorderLayout());
            panelImagen.setPreferredSize(new Dimension(400, 600));

            try {
                ImageIcon icon = new ImageIcon("C:\\Users\\ASUS\\Downloads\\CRUD-MVC-Java-MySQL-main\\CRUD-MVC-Java-MySQL-main\\src\\Recursos\\HipervetLogo.png");
                if (icon.getIconWidth() != -1) {
                    Image img = icon.getImage();
                    Image scaledImg = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImg);
                    JLabel labelImagen = new JLabel(scaledIcon, SwingConstants.CENTER);
                    panelImagen.add(labelImagen, BorderLayout.CENTER);
                }
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
            }

            // Añadir los dos paneles al split pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCentral, panelImagen);
            splitPane.setDividerLocation(350);
            splitPane.setEnabled(false);

            // Añadir el split pane al panel principal
            panelPrincipal.add(splitPane, BorderLayout.CENTER);

            // Añadir el panel principal al frame
            add(panelPrincipal);

            setVisible(true);
        }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLogin) {
            String nombreUsuario = textUsuario.getText();
            String password = new String(textPassword.getPassword());

            // Verificar credenciales
            if (Conexion.verificarCredenciales(nombreUsuario, password)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Abrir la ventana de Home
                HomeGUI homeGUI = new HomeGUI(); // Crear instancia de HomeGUI
                homeGUI.setVisible(true);

                this.dispose(); // Cerrar la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

            //Clase para hacer el gradiente
        class GradientPanel extends JPanel {
            private Color color1;
            private Color color2;

            public GradientPanel(Color color1, Color color2) {
                this.color1 = color1;
                this.color2 = color2;
                setLayout(new BorderLayout());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); //llamar a este método primero
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        }
}
