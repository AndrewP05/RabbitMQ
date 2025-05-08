package co.AndrewP05;

import javax.swing.SwingUtilities;

import co.AndrewP05.ui.OrderWindow;

public class Main {
    public static void main(String[] args) {
        // Arrancamos la UI en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            OrderWindow window = new OrderWindow();
            window.setVisible(true);
        });
    }
}
