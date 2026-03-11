package app.minesweeper;

import javax.swing.JButton;

class CellButton extends JButton {
    final int row;
    final int col;

    CellButton(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
