import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Gestion de canchas de futbol");
        frame.setContentPane(new LOGIN(frame).login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null); //Esta parte sirve para centrar a la pantalla el JPanel
        frame.pack();
        frame.setVisible(true);

    }
}