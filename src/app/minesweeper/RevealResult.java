package app.minesweeper;

import java.util.Collections;
import java.util.List;

class RevealResult {
    private final boolean startedGame;
    private final boolean mineHit;
    private final boolean cleared;
    private final List<CellPosition> affectedCells;

    private RevealResult(boolean startedGame, boolean mineHit, boolean cleared, List<CellPosition> affectedCells) {
        this.startedGame = startedGame;
        this.mineHit = mineHit;
        this.cleared = cleared;
        this.affectedCells = affectedCells;
    }

    static RevealResult noChange() {
        return new RevealResult(false, false, false, Collections.emptyList());
    }

    static RevealResult mineHit(boolean startedGame, List<CellPosition> minePositions) {
        return new RevealResult(startedGame, true, false, minePositions);
    }

    static RevealResult safeReveal(boolean startedGame, boolean cleared, List<CellPosition> revealedCells) {
        return new RevealResult(startedGame, false, cleared, revealedCells);
    }

    boolean startedGame() {
        return startedGame;
    }

    boolean mineHit() {
        return mineHit;
    }

    boolean cleared() {
        return cleared;
    }

    List<CellPosition> affectedCells() {
        return affectedCells;
    }
}
