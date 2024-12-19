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
    public int[] move() {
        int depth = (getBoard().getRows() == 3) ? 2 : 4; // Use getter method
        int[] result = minimax(depth, getMySeed());
        return new int[] {result[1], result[2]};
    }

    /** Recursive minimax at level of depth for either maximizing or minimizing player. */
    private int[] minimax(int depth, Seed player) {
        List<int[]> nextMoves = generateMoves();

        int bestScore = (player == getMySeed()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                getBoard().cells[move[0]][move[1]].content = player;
                if (player == getMySeed()) {
                    currentScore = minimax(depth - 1, getOppSeed())[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, getMySeed())[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                getBoard().cells[move[0]][move[1]].content = Seed.NO_SEED;
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    /** Find all valid next moves. */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();
        if (hasWon(getMySeed()) || hasWon(getOppSeed())) {
            return nextMoves;
        }
        for (int row = 0; row < getBoard().getRows(); ++row) {
            for (int col = 0; col < getBoard().getCols(); ++col) {
                if (getBoard().cells[row][col].content == Seed.NO_SEED) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    /** The heuristic evaluation function for the current board */
    private int evaluate() {
        int score = 0;
        // Add your evaluation logic here
        return score;
    }

    /** Returns true if thePlayer wins */
    private boolean hasWon(Seed thePlayer) {
        // Add your winning logic here
        return false;
    }
}