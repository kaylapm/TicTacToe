package TTT;

public abstract class AIPlayer {
    protected int ROWS; // number of rows
    protected int COLS; // number of columns
    protected Cell[][] cells; // the board's ROWS-by-COLS array of Cells
    protected Seed mySeed; // computer's seed
    protected Seed oppSeed; // opponent's seed

    /** Constructor with reference to game board */
    public AIPlayer(Board board) {
        this.ROWS = board.getRows();
        this.COLS = board.getCols();
        cells = board.cells;
    }

    /** Set/change the seed used by computer and opponent */
    public void setSeed(Seed seed) {
        this.mySeed = seed;
        oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    /** Abstract method to get next move. Return int[2] of {row, col} */
    abstract int[] move(); // to be implemented by subclasses
}