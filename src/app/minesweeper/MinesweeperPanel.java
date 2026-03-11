package app.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayDeque;
import java.util.Random;
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
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int[][] adjacentCounts;

    private Difficulty difficulty = Difficulty.DEFAULTS[0];
    private final Timer timer;
    private final Random random = new Random();

    private boolean gameOver;
    private boolean firstClick;
    private int revealedSafeCells;
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
        gameOver = false;
        firstClick = true;
        revealedSafeCells = 0;

        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                mines[row][col] = false;
                revealed[row][col] = false;
                flagged[row][col] = false;
                adjacentCounts[row][col] = 0;

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
        mines = new boolean[difficulty.rows][difficulty.cols];
        revealed = new boolean[difficulty.rows][difficulty.cols];
        flagged = new boolean[difficulty.rows][difficulty.cols];
        adjacentCounts = new int[difficulty.rows][difficulty.cols];

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

    private void placeMines(int safeRow, int safeCol) {
        int placed = 0;
        while (placed < difficulty.mines) {
            int row = random.nextInt(difficulty.rows);
            int col = random.nextInt(difficulty.cols);
            if (!mines[row][col] && !isProtectedZone(row, col, safeRow, safeCol)) {
                mines[row][col] = true;
                placed++;
            }
        }
    }

    private void calculateAdjacentCounts() {
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (mines[row][col]) {
                    continue;
                }

                int count = 0;
                for (int r = row - 1; r <= row + 1; r++) {
                    for (int c = col - 1; c <= col + 1; c++) {
                        if (isInBounds(r, c) && mines[r][c]) {
                            count++;
                        }
                    }
                }
                adjacentCounts[row][col] = count;
            }
        }
    }

    private void revealCell(int row, int col) {
        if (!isInBounds(row, col) || gameOver || flagged[row][col] || revealed[row][col]) {
            return;
        }

        if (firstClick) {
            placeMines(row, col);
            calculateAdjacentCounts();
            firstClick = false;
            timer.start();
        }

        if (mines[row][col]) {
            buttons[row][col].setText("*");
            buttons[row][col].setBackground(new Color(220, 80, 80));
            revealAllMines();
            gameOver = true;
            timer.stop();
            statusLabel.setText("Game Over | You hit a mine");
            JOptionPane.showMessageDialog(this, "You hit a mine.");
            return;
        }

        floodReveal(row, col);
        updateStatus();

        if (revealedSafeCells == difficulty.rows * difficulty.cols - difficulty.mines) {
            gameOver = true;
            timer.stop();
            statusLabel.setText("Clear | All safe cells opened");
            disableAllCells();
            JOptionPane.showMessageDialog(this, "Board cleared.");
        }
    }

    private void floodReveal(int startRow, int startCol) {
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int[] current = queue.removeFirst();
            int row = current[0];
            int col = current[1];

            if (!isInBounds(row, col) || revealed[row][col] || flagged[row][col] || mines[row][col]) {
                continue;
            }

            revealed[row][col] = true;
            revealedSafeCells++;

            JButton button = buttons[row][col];
            button.setBackground(new Color(235, 235, 235));
            button.setBorder(BorderFactory.createLoweredBevelBorder());

            int count = adjacentCounts[row][col];
            if (count > 0) {
                button.setText(String.valueOf(count));
                button.setForeground(colorForCount(count));
                continue;
            }

            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (!(r == row && c == col)) {
                        queue.addLast(new int[]{r, c});
                    }
                }
            }
        }
    }

    private void toggleFlag(int row, int col) {
        if (!isInBounds(row, col) || gameOver || revealed[row][col]) {
            return;
        }

        flagged[row][col] = !flagged[row][col];
        buttons[row][col].setText(flagged[row][col] ? "F" : "");
        buttons[row][col].setForeground(new Color(200, 120, 0));
        updateStatus();
    }

    private void revealAllMines() {
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (mines[row][col]) {
                    JButton button = buttons[row][col];
                    button.setText("*");
                    button.setEnabled(false);
                    if (!revealed[row][col]) {
                        button.setBackground(new Color(240, 170, 170));
                    }
                }
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
        int flagCount = 0;
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (flagged[row][col]) {
                    flagCount++;
                }
            }
        }

        int remaining = difficulty.mines - flagCount;
        String state = gameOver ? statusLabel.getText() : "Safe " + revealedSafeCells;
        statusLabel.setText(state + " | Mines " + difficulty.mines + " | Flags " + flagCount + " | Left " + remaining);
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < difficulty.rows && col >= 0 && col < difficulty.cols;
    }

    private boolean isProtectedZone(int row, int col, int safeRow, int safeCol) {
        return Math.abs(row - safeRow) <= 1 && Math.abs(col - safeCol) <= 1;
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
