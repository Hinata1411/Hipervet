package com.HipervetCRUDSQL.Hipervet.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeGUI extends JFrame {

        private JPanel sidebar;
        private JPanel contentPanel;
        private JTextField searchField;
        private JButton searchButton;
        private JButton toggleThemeButton;
        private JButton logoutButton;
        private boolean isDarkMode = false;
        private CardLayout cardLayout;

        public HomeGUI() {
            setTitle("Home - HiperVet");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana
            setLocationRelativeTo(null); // Centrar la ventana
            setResizable(true); // Permitir redimensionar la ventana
            setLayout(new BorderLayout());

            // Escuchar cambios en el estado de la ventana
            addWindowStateListener(new WindowStateListener() {
                @Override
                public void windowStateChanged(WindowEvent e) {
                    if (e.getNewState() == Frame.ICONIFIED) {
                        setExtendedState(JFrame.NORMAL); // Evitar minimizar por completo
                        setSize(800, 600); // Tamaño promedio
                    }
                }
            });

            // Crear el header (parte superior) para el buscador y los botones
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(0, 153, 204)); // Azul más pastel

            // Campo de búsqueda
            searchField = new JTextField(20);
            searchField.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente sofisticada
            searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Botón de búsqueda (lupa)
            searchButton = new JButton("\uD83D\uDD0D"); // Emoji de lupa
            searchButton.setFocusable(false);
            searchButton.setBackground(new Color(0, 204, 204)); // Color pastel
            searchButton.setBorder(BorderFactory.createEmptyBorder());
            searchButton.setPreferredSize(new Dimension(40, 30));
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchText = searchField.getText();
                    System.out.println("Buscando: " + searchText);
                }
            });

            // Botón para cambiar entre modo claro y oscuro
            toggleThemeButton = new JButton("Modo Oscuro");
            toggleThemeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleTheme();
                }
            });

            // Botón para cerrar sesión
            logoutButton = new JButton("Cerrar Sesión");
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logout();
                }
            });

            // Añadir el campo de búsqueda y los botones al header
            JPanel searchPanel = new JPanel(new BorderLayout());
            searchPanel.setOpaque(false); // Hacer que el panel tenga el mismo fondo que el header
            searchPanel.add(searchField, BorderLayout.CENTER);
            searchPanel.add(searchButton, BorderLayout.EAST);
            headerPanel.add(searchPanel, BorderLayout.WEST);

            // Panel para los botones de "Modo Oscuro" y "Cerrar Sesión"
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.add(toggleThemeButton);
            buttonPanel.add(logoutButton);

            headerPanel.add(buttonPanel, BorderLayout.EAST);
            headerPanel.setPreferredSize(new Dimension(getWidth(), 50));

            // Crear el panel lateral (sidebar)
            sidebar = new JPanel();
            sidebar.setBackground(new Color(173, 216, 230)); // Azul pastel
            sidebar.setPreferredSize(new Dimension(200, getHeight()));
            sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

            // Cambiar fuente de los botones
            Font buttonFont = new Font("Arial", Font.PLAIN, 16);

            // Crear y añadir botones al sidebar con iconos
            String[] options = {"Home", "Empleado", "Mascotas", "Citas", "Diagnósticos", "Persona"};
            String[] iconPaths = {
                    "src/icons/home.png",
                    "src/icons/person.png",
                    "src/icons/dog.png",
                    "src/icons/client-card.png",
                    "src/icons/stethoscope.png",
                    "src/icons/person.png"
            };

            // Crear el panel de contenido con CardLayout
            cardLayout = new CardLayout();
            contentPanel = new JPanel(cardLayout);

            // Panel Home (con imagen)
            JPanel homePanel = new JPanel(new BorderLayout()) {
                private Image image;

                {
                    try {
                        // Cargar la imagen de la carpeta de recursos
                        BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\ASUS\\Desktop\\Hipervet\\Hipervet\\src\\main\\java\\com\\HipervetCRUDSQL\\Hipervet\\Resurces\\Hipervetimage.jpg"));
                        image = bufferedImage;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (image != null) {
                        // Escalar la imagen al tamaño del panel
                        int width = getWidth();
                        int height = getHeight();
                        g.drawImage(image, 0, 0, width, height, this);
                    }
                }
            };

            // Agregar el panel de inicio al contentPanel
            contentPanel.add(homePanel, "Home");

            // Agregar el panel de empleados
            contentPanel.add(new EmpleadoGUI(), "Empleado");

            // Agregar el panel de Persona
            contentPanel.add(new PersonaGUI(), "Persona");

            // Agregar el panel de Mascotas
            contentPanel.add(new MascotaGUI(), "Mascotas");

            // Agregar el panel de citas
            contentPanel.add(new CitasGUI(), "Citas");

            // Agregar el panel de Diagnósticos
            contentPanel.add(new DiagnosticGUI(), "Diagnósticos");

            // Agregar ActionListener a los botones del sidebar
            for (int i = 0; i < options.length; i++) {
                JButton button = new JButton(options[i]);
                button.setFont(buttonFont);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                button.setIcon(new ImageIcon(iconPaths[i]));

                // Agregar ActionListener
                final String option = options[i];
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(contentPanel, option); // Cambia el panel mostrado
                    }
                });

                sidebar.add(button);
                sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            // Añadir el header, sidebar, y panel de contenido al frame
            add(headerPanel, BorderLayout.NORTH);
            add(sidebar, BorderLayout.WEST);
            add(contentPanel, BorderLayout.CENTER);

            setVisible(true);
        }

        // Método para cambiar entre modo claro y oscuro
        private void toggleTheme() {
            if (isDarkMode) {
                sidebar.setBackground(new Color(173, 216, 230)); // Azul pastel
                contentPanel.setBackground(Color.WHITE);
                toggleThemeButton.setText("Modo Oscuro");
                searchField.setBackground(Color.WHITE);
            } else {
                sidebar.setBackground(new Color(51, 51, 51)); // Gris oscuro
                contentPanel.setBackground(new Color(34, 34, 34)); // Gris más oscuro
                toggleThemeButton.setText("Modo Claro");
                searchField.setBackground(new Color(70, 70, 70)); // Fondo de búsqueda oscuro
            }
            isDarkMode = !isDarkMode;
        }

        // Método para cerrar sesión y volver al formulario de login
        private void logout() {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Cerrar la ventana de home
                new LoginGUI().setVisible(true); // Abrir el formulario de login
            }
        }

        public static void main(String[] args) {
            new HomeGUI();
        }
}

