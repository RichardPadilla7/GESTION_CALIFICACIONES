import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LOGIN {
    public JTextField correoTEXT;
    public JPasswordField contraTEXT;
    public JButton iniciarSesionButton;
    public JComboBox<String> rolesBOX;
    public JPanel login;
    public JLabel correo;
    public JLabel contrasenia;
    public JLabel rol;
    public JLabel titulo;
    public JFrame LoginFrame;

    // Parámetros de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/calificaciones";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public LOGIN(JFrame frame) {
        this.LoginFrame = frame;

        // Opciones del JComboBox para el rol
        rolesBOX.addItem("Administrador");
        rolesBOX.addItem("Estudiante");

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = correoTEXT.getText();
                String contrasenia = new String(contraTEXT.getPassword());
                String rolSeleccionado = (String) rolesBOX.getSelectedItem();

                if (correo.isEmpty() || contrasenia.isEmpty() || rolSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados.");
                    return;
                }

                // Conectar a la base de datos
                try {
                    // Cargar el driver JDBC para MySQL
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "SELECT tipo_rol FROM usuarios WHERE correo = ? AND contrasenia = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, correo);
                        pstmt.setString(2, contrasenia);

                        ResultSet resultSet = pstmt.executeQuery();

                        if (resultSet.next()) {
                            String rol = resultSet.getString("tipo_rol");

                            if (rol.equals(rolSeleccionado)) {
                                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso! Bienvenido " + correo);

                                // Abrir la ventana dependiendo del rol
                                if (rol.equals("Administrador")) {
                                    LoginFrame.dispose();
                                    JFrame adminFrame = new JFrame("Administrador");
                                    adminFrame.setContentPane(new Administrador(adminFrame).admin);
                                    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    adminFrame.setSize(800, 600); // Ajustado el tamaño de la ventana
                                    adminFrame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
                                    adminFrame.pack();
                                    adminFrame.setVisible(true);

                                } else if (rol.equals("Estudiante")) {
                                    LoginFrame.dispose();
                                    JFrame estudianteFrame = new JFrame("Estudiante");
                                    estudianteFrame.setContentPane(new Estudiante(estudianteFrame).estudiante);
                                    estudianteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    estudianteFrame.setSize(800, 600); // Ajustado el tamaño de la ventana
                                    estudianteFrame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
                                    estudianteFrame.pack();
                                    estudianteFrame.setVisible(true);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El rol seleccionado no coincide con el registrado.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario no encontrado o contraseña incorrecta");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error en la base de datos: " + ex.getMessage());
                    }
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Driver JDBC no encontrado: " + ex.getMessage());
                }
            }
        });
    }
}
