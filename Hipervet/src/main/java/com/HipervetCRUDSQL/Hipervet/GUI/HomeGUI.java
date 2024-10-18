package com.HipervetCRUDSQL.Hipervet.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana para que ocupe toda la pantalla
            setUndecorated(true); // Opcional: elimina la barra de título para un aspecto más limpio
            setLocationRelativeTo(null); // Centrar la ventana en la pantalla
            setLayout(new BorderLayout());

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
            String[] options = {"Home", "Empleados", "Mascotas", "Clientes", "Diagnósticos"};
            String[] iconPaths = {
                    "src/icons/home.png",
                    "src/icons/person.png",
                    "src/icons/dog.png",
                    "src/icons/client-card.png",
                    "src/icons/stethoscope.png"
            };

            // Crear el panel de contenido con CardLayout
            cardLayout = new CardLayout();
            contentPanel = new JPanel(cardLayout);

            // Panel Home (puedes personalizarlo más)
            JPanel homePanel = new JPanel();
            homePanel.setBackground(Color.WHITE);
            JLabel homeLabel = new JLabel("Bienvenido a la página principal", SwingConstants.CENTER);
            homeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            homePanel.add(homeLabel);

            contentPanel.add(homePanel, "Home");

            // Agregar el panel de empleados
            contentPanel.add(new EmpleadoGUI(), "Empleados");

            // Agregar el panel de Mascotas
            contentPanel.add(new MascotaGUI(), "Mascotas");

            // Agregar el panel de clientes
            contentPanel.add(new ClienteGUI(), "Clientes");

            // Agregar el panel de Diagnosticos
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
                // Cambiar a modo claro
                sidebar.setBackground(new Color(173, 216, 230)); // Azul pastel
                contentPanel.setBackground(Color.WHITE);
                toggleThemeButton.setText("Modo Oscuro");
                searchField.setBackground(Color.WHITE);
            } else {
                // Cambiar a modo oscuro
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
}
