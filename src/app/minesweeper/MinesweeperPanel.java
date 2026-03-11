package app.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MinesweeperPanel extends JPanel {
    private final JLabel statusLabel = new JLabel("", SwingConstants.LEFT);
    private final JLabel timerLabel = new JLabel("Time 0", SwingConstants.RIGHT);
    private final JButton resetButton = new JButton("New Game");
    private final JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.DEFAULTS);
    private final JPanel boardPanel = new JPanel();

    private CellButton[][] buttons;
    private Difficulty difficulty = Difficulty.DEFAULTS[0];
    private final MinesweeperGame game = new MinesweeperGame(difficulty);
    private final Timer timer;

    private int elapsedSeconds;

    public MinesweeperPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        timer = new Timer(1000, e -> {
            elapsedSeconds++;
            timerLabel.setText("Time " + elapsedSeconds);
        });

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        statusLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        timerLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));

        difficultyBox.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        difficultyBox.setFocusable(false);
        difficultyBox.setRenderer(new DifficultyRenderer());
        difficultyBox.addActionListener(e -> {
            difficulty = (Difficulty) difficultyBox.getSelectedItem();
            rebuildBoard();
            resetGame();
        });

        resetButton.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> resetGame());

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controlPanel.add(difficultyBox);
        controlPanel.add(resetButton);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(statusLabel, BorderLayout.WEST);
        infoPanel.add(timerLabel, BorderLayout.EAST);

        boardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 160, 160)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        boardPanel.setBackground(new Color(194, 194, 194));

        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        rebuildBoard();
        resetGame();
    }

    private void resetGame() {
        timer.stop();
        elapsedSeconds = 0;
        timerLabel.setText("Time 0");
        game.reset(difficulty);

        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                JButton button = buttons[row][col];
                button.setEnabled(true);
                button.setText("");
                button.setBackground(null);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }

        updateStatus();
    }

    private void rebuildBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(difficulty.rows, difficulty.cols, 2, 2));

        buttons = new CellButton[difficulty.rows][difficulty.cols];

        Font cellFont = new Font("Arial", Font.BOLD, difficulty.cols > 16 ? 13 : 18);
        int cellSize = difficulty.cols > 16 ? 28 : 42;
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                CellButton button = new CellButton(row, col);
                button.setFont(cellFont);
                button.setPreferredSize(new Dimension(cellSize, cellSize));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
                button.addActionListener(e -> revealCell(button.row, button.col));
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            toggleFlag(button.row, button.col);
                        }
                    }
                });
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void revealCell(int row, int col) {
        RevealResult result = game.revealCell(row, col);
        if (result.startedGame()) {
            timer.start();
        }

        if (result.mineHit()) {
            buttons[row][col].setText("*");
            buttons[row][col].setBackground(new Color(220, 80, 80));
            revealAllMines(result.affectedCells());
            timer.stop();
            statusLabel.setText("Game Over | You hit a mine");
            JOptionPane.showMessageDialog(this, "You hit a mine.");
            return;
        }

        applyRevealedCells(result.affectedCells());
        updateStatus();

        if (result.cleared()) {
            timer.stop();
            statusLabel.setText("Clear | All safe cells opened");
            disableAllCells();
            JOptionPane.showMessageDialog(this, "Board cleared.");
        }
    }

    private void applyRevealedCells(java.util.List<CellPosition> revealedCells) {
        for (CellPosition cell : revealedCells) {
            JButton button = buttons[cell.row][cell.col];
            button.setBackground(new Color(235, 235, 235));
            button.setBorder(BorderFactory.createLoweredBevelBorder());

            int count = game.getAdjacentCount(cell.row, cell.col);
            if (count > 0) {
                button.setText(String.valueOf(count));
                button.setForeground(colorForCount(count));
            } else {
                button.setText("");
            }
        }
    }

    private void toggleFlag(int row, int col) {
        if (!game.toggleFlag(row, col)) {
            return;
        }

        buttons[row][col].setText(game.isFlagged(row, col) ? "F" : "");
        buttons[row][col].setForeground(new Color(200, 120, 0));
        updateStatus();
    }

    private void revealAllMines(java.util.List<CellPosition> mineCells) {
        for (CellPosition mineCell : mineCells) {
            JButton button = buttons[mineCell.row][mineCell.col];
            button.setText("*");
            button.setEnabled(false);
            if (!game.isRevealed(mineCell.row, mineCell.col)) {
                button.setBackground(new Color(240, 170, 170));
            }
        }
        disableAllCells();
    }

    private void disableAllCells() {
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void updateStatus() {
        int flagCount = game.getFlagCount();
        int remaining = game.getRemainingMines();
        String state = game.isGameOver() ? statusLabel.getText() : "Safe " + game.getRevealedSafeCells();
        statusLabel.setText(state + " | Mines " + difficulty.mines + " | Flags " + flagCount + " | Left " + remaining);
    }

    private Color colorForCount(int count) {
        switch (count) {
            case 1:
                return new Color(30, 90, 200);
            case 2:
                return new Color(40, 140, 60);
            case 3:
                return new Color(210, 50, 50);
            case 4:
                return new Color(95, 40, 160);
            default:
                return new Color(120, 70, 25);
        }
    }

    private static final class DifficultyRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
        ) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Difficulty) {
                Difficulty item = (Difficulty) value;
                label.setText(item.name + " (" + item.rows + "x" + item.cols + ", " + item.mines + ")");
            }
            return label;
        }
    }
}
