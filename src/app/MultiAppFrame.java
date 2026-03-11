package app;

import app.board.BoardPanel;
import app.minesweeper.MinesweeperPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MultiAppFrame extends JFrame {
    public MultiAppFrame() {
        super("AI_TEST - Minesweeper / Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        tabbedPane.addTab("Minesweeper", new MinesweeperPanel());
        tabbedPane.addTab("Board", new BoardPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setSize(1200, 760);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);
    }
}
