import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

public class Administrador {
    // Componentes de la interfaz
    public JButton crearButton;
    public JTextArea textArea2;
    public JTextField textField1;
    public JButton visualizarNotaButton;
    public JTextField textField2;
    public JTextField textField3;
    public JButton actualizarButton;
    public JTextField textField4;
    public JButton borrarButton;
    public JPanel admin;
    public JFrame adminFrame;

    public Administrador(JFrame frame) {
        this.adminFrame = frame;

        // Configuración del botón para crear una calificación
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textField1.getText();
                String materiaStr = textField2.getText();
                String calificacionStr = textField3.getText();

                if (cedula.isEmpty() || materiaStr.isEmpty() || calificacionStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados.");
                    return;
                }

                // Conectar a la base de datos y ejecutar la inserción
                String url = "jdbc:mysql://localhost:3306/calificaciones";
                String user = "root";
                String password = "123456";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "INSERT INTO calificaciones (cedula_estudiante, materia, calificacion) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, cedula);
                    pstmt.setInt(2, Integer.parseInt(materiaStr));
                    pstmt.setBigDecimal(3, new BigDecimal(calificacionStr));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Calificación creada exitosamente.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al crear la calificación.");
                }
            }
        });

        // Configuración del botón para visualizar calificaciones
        visualizarNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textField1.getText();

                if (cedula.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La cédula debe ser llenada.");
                    return;
                }

                // Conectar a la base de datos y ejecutar la consulta
                String url = "jdbc:mysql://localhost:3306/calificaciones";
                String user = "root";
                String password = "123456";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "SELECT materia, calificacion FROM calificaciones WHERE cedula_estudiante = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, cedula);

                    ResultSet resultSet = pstmt.executeQuery();
                    StringBuilder result = new StringBuilder("Materias y calificaciones:\n");
                    while (resultSet.next()) {
                        int materia = resultSet.getInt("materia");
                        BigDecimal calificacion = resultSet.getBigDecimal("calificacion");
                        result.append("Materia: ").append(materia).append(", Calificación: ").append(calificacion).append("\n");
                    }

                    textArea2.setText(result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al visualizar las calificaciones.");
                }
            }
        });

        // Configuración del botón para actualizar una calificación
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textField1.getText();
                String materiaStr = textField2.getText();
                String calificacionStr = textField4.getText();

                if (cedula.isEmpty() || materiaStr.isEmpty() || calificacionStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados.");
                    return;
                }

                // Conectar a la base de datos y ejecutar la actualización
                String url = "jdbc:mysql://localhost:3306/calificaciones";
                String user = "root";
                String password = "123456";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "UPDATE calificaciones SET calificacion = ? WHERE cedula_estudiante = ? AND materia = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setBigDecimal(1, new BigDecimal(calificacionStr));
                    pstmt.setString(2, cedula);
                    pstmt.setInt(3, Integer.parseInt(materiaStr));
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Calificación actualizada exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró la calificación para actualizar.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar la calificación.");
                }
            }
        });

        // Configuración del botón para borrar una calificación
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textField1.getText();
                String materiaStr = textField2.getText();

                if (cedula.isEmpty() || materiaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados.");
                    return;
                }

                // Conectar a la base de datos y ejecutar la eliminación
                String url = "jdbc:mysql://localhost:3306/calificaciones";
                String user = "root";
                String password = "123456";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "DELETE FROM calificaciones WHERE cedula_estudiante = ? AND materia = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, cedula);
                    pstmt.setInt(2, Integer.parseInt(materiaStr));
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Calificación borrada exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró la calificación para borrar.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al borrar la calificación.");
                }
            }
        });
    }
}
