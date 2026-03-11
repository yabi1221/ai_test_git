package app;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MultiAppFrame frame = new MultiAppFrame();
            frame.setVisible(true);
        });
    }
}
