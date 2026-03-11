package app.minesweeper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MinesweeperGame {
    private final Random random = new Random();

    private Difficulty difficulty;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int[][] adjacentCounts;
    private boolean gameOver;
    private boolean firstClick;
    private int revealedSafeCells;

    MinesweeperGame(Difficulty difficulty) {
        reset(difficulty);
    }

    void reset(Difficulty difficulty) {
        this.difficulty = difficulty;
        mines = new boolean[difficulty.rows][difficulty.cols];
        revealed = new boolean[difficulty.rows][difficulty.cols];
        flagged = new boolean[difficulty.rows][difficulty.cols];
        adjacentCounts = new int[difficulty.rows][difficulty.cols];
        gameOver = false;
        firstClick = true;
        revealedSafeCells = 0;
    }

    RevealResult revealCell(int row, int col) {
        if (!isInBounds(row, col) || gameOver || flagged[row][col] || revealed[row][col]) {
            return RevealResult.noChange();
        }

        boolean started = false;
        if (firstClick) {
            placeMines(row, col);
            calculateAdjacentCounts();
            firstClick = false;
            started = true;
        }

        if (mines[row][col]) {
            gameOver = true;
            return RevealResult.mineHit(started, getMinePositions());
        }

        List<CellPosition> revealedCells = floodReveal(row, col);
        boolean cleared = revealedSafeCells == difficulty.rows * difficulty.cols - difficulty.mines;
        if (cleared) {
            gameOver = true;
        }

        return RevealResult.safeReveal(started, cleared, revealedCells);
    }

    boolean toggleFlag(int row, int col) {
        if (!isInBounds(row, col) || gameOver || revealed[row][col]) {
            return false;
        }
        flagged[row][col] = !flagged[row][col];
        return true;
    }

    boolean isRevealed(int row, int col) {
        return revealed[row][col];
    }

    boolean isFlagged(int row, int col) {
        return flagged[row][col];
    }

    boolean isMine(int row, int col) {
        return mines[row][col];
    }

    int getAdjacentCount(int row, int col) {
        return adjacentCounts[row][col];
    }

    boolean isGameOver() {
        return gameOver;
    }

    int getRevealedSafeCells() {
        return revealedSafeCells;
    }

    int getFlagCount() {
        int count = 0;
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (flagged[row][col]) {
                    count++;
                }
            }
        }
        return count;
    }

    int getRemainingMines() {
        return difficulty.mines - getFlagCount();
    }

    private List<CellPosition> floodReveal(int startRow, int startCol) {
        ArrayDeque<CellPosition> queue = new ArrayDeque<>();
        List<CellPosition> revealedCells = new ArrayList<>();
        queue.add(new CellPosition(startRow, startCol));

        while (!queue.isEmpty()) {
            CellPosition current = queue.removeFirst();
            int row = current.row;
            int col = current.col;

            if (!isInBounds(row, col) || revealed[row][col] || flagged[row][col] || mines[row][col]) {
                continue;
            }

            revealed[row][col] = true;
            revealedSafeCells++;
            revealedCells.add(current);

            if (adjacentCounts[row][col] > 0) {
                continue;
            }

            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (!(r == row && c == col)) {
                        queue.addLast(new CellPosition(r, c));
                    }
                }
            }
        }

        return revealedCells;
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

    private List<CellPosition> getMinePositions() {
        List<CellPosition> positions = new ArrayList<>();
        for (int row = 0; row < difficulty.rows; row++) {
            for (int col = 0; col < difficulty.cols; col++) {
                if (mines[row][col]) {
                    positions.add(new CellPosition(row, col));
                }
            }
        }
        return positions;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < difficulty.rows && col >= 0 && col < difficulty.cols;
    }

    private boolean isProtectedZone(int row, int col, int safeRow, int safeCol) {
        return Math.abs(row - safeRow) <= 1 && Math.abs(col - safeCol) <= 1;
    }
}
