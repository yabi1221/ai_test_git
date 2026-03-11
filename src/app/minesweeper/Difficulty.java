package app.minesweeper;

class Difficulty {
    static final Difficulty[] DEFAULTS = {
        new Difficulty("Easy", 9, 9, 10),
        new Difficulty("Normal", 16, 16, 40),
        new Difficulty("Hard", 16, 30, 99)
    };

    final String name;
    final int rows;
    final int cols;
    final int mines;

    Difficulty(String name, int rows, int cols, int mines) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    @Override
    public String toString() {
        return name;
    }
}
