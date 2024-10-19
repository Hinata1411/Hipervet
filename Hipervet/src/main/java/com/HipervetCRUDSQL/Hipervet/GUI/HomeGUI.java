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
import java.io.InputStream;

import static java.awt.Font.TRUETYPE_FONT;

public class HomeGUI extends JFrame {

        private JPanel sidebar;
        private JPanel contentPanel;
        private JTextField searchField;
        private JButton searchButton;
        private JButton toggleThemeButton;
        private JButton logoutButton;
        private boolean isDarkMode = false;
        private CardLayout cardLayout;
        private JPanel headerPanel; // Agregado para controlar el color del header

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
            headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(25, 117, 11));

            // Campo de búsqueda
            searchField = new JTextField(20);
            searchField.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente sofisticada
            searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Botón de búsqueda (lupa)
            searchButton = new JButton("\uD83D\uDD0D"); // Emoji de lupa
            searchButton.setFocusable(false);
            searchButton.setBackground(new Color(25, 117, 11));
            searchButton.setPreferredSize(new Dimension(40, 30));
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchText = searchField.getText();
                    System.out.println("Buscando: " + searchText);
                }
            });

            // Botón para cambiar entre modo claro y oscuro
//            toggleThemeButton = new JButton("Modo Oscuro");
//            toggleThemeButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    toggleTheme();
//                }
//            });

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
//            buttonPanel.add(toggleThemeButton);
              buttonPanel.add(logoutButton);

            headerPanel.add(buttonPanel, BorderLayout.EAST);
            headerPanel.setPreferredSize(new Dimension(getWidth(), 50));

            // Crear el panel lateral (sidebar)
            sidebar = new JPanel();
            sidebar.setBackground(new Color(25, 117, 11));
            sidebar.setPreferredSize(new Dimension(200, getHeight()));
            sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

            // Cambiar fuente de los botones
            Font buttonFont = new Font("Arial", Font.PLAIN, 16);

            // Crear y añadir botones al sidebar con iconos
            String[] options = {"Home", "Persona", "Empleado", "Clientes", "Mascotas", "Citas", "Diagnosticos"};
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
                        BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\ASUS\\Desktop\\Hipervet\\Hipervet\\src\\main\\java\\com\\HipervetCRUDSQL\\Hipervet\\Resurces\\Hiverpetimage.png"));
                        image = bufferedImage;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (image != null) {
                        // Escalar la imagen a un tamaño más pequeño (por ejemplo, 300x300)
                        int imgWidth = 700;
                        int imgHeight = 700;
                        int x = (getWidth() - imgWidth) / 2; // Centrar horizontalmente
                        int y = (getHeight() - imgHeight) / 2 + 50; // Centrar verticalmente con un pequeño desplazamiento
                        g.drawImage(image, x, y, imgWidth, imgHeight, this);
                    }
                }
            };

            // Agregar el texto "Hipervet"
            JLabel welcomeLabel = new JLabel("Hipervet", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 100)); // Aumentar el tamaño del texto
            welcomeLabel.setForeground(new Color(25, 117, 11));
            welcomeLabel.setPreferredSize(new Dimension(getWidth(), 100)); // Hacer el JLabel más grande
            homePanel.add(welcomeLabel, BorderLayout.NORTH); // Añadir el texto al panel Home

            // Agregar el panel de inicio al contentPanel
            contentPanel.add(homePanel, "Home");

            // Agregar el panel de empleados
            contentPanel.add(new EmpleadoGUI(), "Empleado");

            // Agregar el panel de Persona
            contentPanel.add(new PersonaGUI(), "Persona");

            // Agregar el panel de Mascotas
            contentPanel.add(new MascotaGUI(), "Mascotas");

            // Agregar el panel de Clientes
            contentPanel.add(new ClienteGUI(), "Clientes");

            // Agregar el panel de citas
            contentPanel.add(new CitasGUI(), "Citas");

            // Agregar el panel de Diagnósticos
            contentPanel.add(new DiagnosticGUI(), "Diagnosticos");

            // Agregar ActionListener a los botones del sidebar
            for (int i = 0; i < options.length; i++) {
                JButton button = new JButton(options[i]);
                button.setFont(buttonFont);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
//                button.setIcon(new ImageIcon(iconPaths[i]));

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

//        // Método para cambiar entre modo claro y oscuro
//        private void toggleTheme() {
//            Color sidebarBackground, contentBackground, buttonBackground, textColor, searchFieldBackground, headerBackground;
//
//            if (isDarkMode) {
//                // Colores para modo claro
//                sidebarBackground = new Color(153, 204, 153); // Verde pastel claro
//                contentBackground = Color.WHITE;
//                buttonBackground = new Color(102, 153, 102); // Verde medio
//                headerBackground = new Color(25, 117, 11); // Verde principal
//                textColor = Color.BLACK;
//                searchFieldBackground = Color.WHITE;
//                toggleThemeButton.setText("Modo Oscuro");
//            } else {
//                // Colores para modo oscuro
//                sidebarBackground = new Color(25, 77, 11); // Verde oscuro
//                contentBackground = new Color(8, 41, 7); // Verde más oscuro
//                buttonBackground = new Color(25, 117, 11); // Verde principal para los botones
//                headerBackground = new Color(25, 117, 11); // Mismo verde para el header
//                textColor = Color.WHITE;
//                searchFieldBackground = new Color(25, 77, 11); // Verde oscuro para el campo de búsqueda
//                toggleThemeButton.setText("Modo Claro");
//            }
//
//            // Cambiar color del sidebar
//            sidebar.setBackground(sidebarBackground);
//
//            // Cambiar color del contenido
//            contentPanel.setBackground(contentBackground);
//
//            // Cambiar color del header
//            headerPanel.setBackground(headerBackground);
//
//            // Cambiar color de los botones del sidebar
//            for (Component component : sidebar.getComponents()) {
//                if (component instanceof JButton) {
//                    component.setBackground(buttonBackground);
//                    component.setForeground(textColor); // Color del texto
//                }
//            }
//
//            // Cambiar color de los botones del header
//            searchButton.setBackground(buttonBackground);
//            searchButton.setForeground(textColor); // Color de la lupa
//
//            toggleThemeButton.setBackground(buttonBackground);
//            toggleThemeButton.setForeground(textColor);
//
//            logoutButton.setBackground(buttonBackground);
//            logoutButton.setForeground(textColor);
//
//            // Cambiar el color del campo de búsqueda
//            searchField.setBackground(searchFieldBackground);
//            searchField.setForeground(textColor); // Color del texto del campo de búsqueda
//
//            // Cambiar colores del texto del contentPanel (por ejemplo, en el label "Hipervet")
//            for (Component component : contentPanel.getComponents()) {
//                if (component instanceof JPanel) {
//                    for (Component subComponent : ((JPanel) component).getComponents()) {
//                        if (subComponent instanceof JLabel) {
//                            subComponent.setForeground(textColor); // Cambiar el color del texto del JLabel
//                        }
//                    }
//                }
//            }
//
//            // Alternar el estado del modo oscuro
//            isDarkMode = !isDarkMode;
//        }

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



