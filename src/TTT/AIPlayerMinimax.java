package TTT;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMinimax extends AIPlayer {

    /** Constructor with the given game board */
    public AIPlayerMinimax(Board board) {
        super(board);
    }

    /** Get next best move for computer. Return int[2] of {row, col} */
    @Override
    int[] move() {
        int depth = (ROWS == 3) ? 2 : 4; // Increase depth for larger grids
        int[] result = minimax(depth, mySeed);
        return new int[] {result[1], result[2]};
    }

    /** Recursive minimax at level of depth for either maximizing or minimizing player. */
    private int[] minimax(int depth, Seed player) {
        List<int[]> nextMoves = generateMoves();

        int bestScore = (player == mySeed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                cells[move[0]][move[1]].content = player;
                if (player == mySeed) {
                    currentScore = minimax(depth - 1, oppSeed)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, mySeed)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                cells[move[0]][move[1]].content = Seed.NO_SEED;
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    /** Find all valid next moves. */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();
        if (hasWon(mySeed) || hasWon(oppSeed)) {
            return nextMoves;
        }
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    /** The heuristic evaluation function for the current board */
    private int evaluate() {
        int score = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                score += evaluateLine(row, col, row, col + 1, row, col + 2, row, col + 3);
                score += evaluateLine(row, col, row + 1, col, row + 2, col, row + 3, col);
                score += evaluateLine(row, col, row + 1, col + 1, row + 2, col + 2, row + 3, col + 3);
                score += evaluateLine(row, col, row + 1, col - 1, row + 2, col - 2, row + 3, col - 3);
            }
        }
        return score;
    }

    /** The heuristic evaluation function for the given line of 4 cells */
    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3, int row4, int col4) {
        int score = 0;
        if (isValid(row1, col1) && isValid(row2, col2) && isValid(row3, col3) && isValid(row4, col4)) {
            int myCount = 0;
            int oppCount = 0;

            if (cells[row1][col1].content == mySeed) myCount++;
            else if (cells[row1][col1].content == oppSeed) oppCount++;

            if (cells[row2][col2].content == mySeed) myCount++;
            else if (cells[row2][col2].content == oppSeed) oppCount++;

            if (cells[row3][col3].content == mySeed) myCount++;
            else if (cells[row3][col3].content == oppSeed) oppCount++;

            if (cells[row4][col4].content == mySeed) myCount++;
            else if (cells[row4][col4].content == oppSeed) oppCount++;

            if (myCount == 4) score += 100;
            else if (myCount == 3 && oppCount == 0) score += 10;
            else if (myCount == 2 && oppCount == 0) score += 1;
            else if (oppCount == 4) score -= 100;
            else if (oppCount == 3 && myCount == 0) score -= 10;
            else if (oppCount == 2 && myCount == 0) score -= 1;
        }
        return score;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    /** Returns true if thePlayer wins */
    private boolean hasWon(Seed thePlayer) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (checkLine(thePlayer, row, col, 0, 1) || // Check row
                        checkLine(thePlayer, row, col, 1, 0) || // Check column
                        checkLine(thePlayer, row, col, 1, 1) || // Check diagonal
                        checkLine(thePlayer, row, col, 1, -1)) { // Check opposite diagonal
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkLine(Seed player, int startRow, int startCol, int deltaRow, int deltaCol) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = startRow + i * deltaRow;
            int c = startCol + i * deltaCol;
            if (isValid(r, c) && cells[r][c].content == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
}