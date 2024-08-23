import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

public class Estudiante {
    public JPanel estudiante;
    public JTextArea imprimirdatos;
    public JButton visualizarCalificacionesButton1;
    public JButton visualizarMateriasButton;
    public JLabel titulo;
    public JFrame estudianteFrame;

    public Estudiante(JFrame frame) {
        this.estudianteFrame = frame;

        // Visualizar calificaciones
        visualizarCalificacionesButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = JOptionPane.showInputDialog("Ingrese su cédula:");

                if (cedula == null || cedula.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía.");
                    return;
                }

                // Conectar a la base de datos
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

                    imprimirdatos.setText(result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al visualizar las calificaciones.");
                }
            }
        });

        // Visualizar materias (Aquí asumo que tienes una tabla de materias, si no la tienes, esta funcionalidad no aplicará)
        visualizarMateriasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Conectar a la base de datos
                String url = "jdbc:mysql://localhost:3306/calificaciones";
                String user = "root";
                String password = "123456";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "SELECT DISTINCT materia FROM calificaciones WHERE cedula_estudiante = ?";
                    String cedula = JOptionPane.showInputDialog("Ingrese su cédula:");

                    if (cedula == null || cedula.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía.");
                        return;
                    }

                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, cedula);

                    ResultSet resultSet = pstmt.executeQuery();
                    StringBuilder result = new StringBuilder("Materias:\n");
                    while (resultSet.next()) {
                        int materia = resultSet.getInt("materia");
                        result.append("Materia: ").append(materia).append("\n");
                    }

                    imprimirdatos.setText(result.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al visualizar las materias.");
                }
            }
        });
    }
}
