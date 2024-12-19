package TTT;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {
    private Seed mySeed;    // AI's seed
    private Seed oppSeed;   // Opponent's seed
    private Board board;    // The game board

    public AIPlayer(Board board) {
        this.board = board;
    }

    public void setSeed(Seed seed) {
        this.mySeed = seed;
        this.oppSeed = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    public int[] move() {
        int depth = 4; // Adjust depth as needed for performance
        int[] result = minimax(depth, mySeed, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[] {result[1], result[2]}; // row, col
    }

    protected Board getBoard() {
        return board;
    }

    protected Seed getMySeed() {
        return mySeed;
    }

    protected Seed getOppSeed() {
        return oppSeed;
    }

    private int[] minimax(int depth, Seed player, int alpha, int beta) {
        List<int[]> nextMoves = generateMoves();

        int bestScore = (player == mySeed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                board.cells[move[0]][move[1]].content = player;
                if (player == mySeed) {
                    currentScore = minimax(depth - 1, oppSeed, alpha, beta)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                    alpha = Math.max(alpha, bestScore);
                } else {
                    currentScore = minimax(depth - 1, mySeed, alpha, beta)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                    beta = Math.min(beta, bestScore);
                }
                board.cells[move[0]][move[1]].content = Seed.NO_SEED;
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();
        if (hasWon(mySeed) || hasWon(oppSeed)) {
            return nextMoves;
        }
        for (int row = 0; row < board.getRows(); ++row) {
            for (int col = 0; col < board.getCols(); ++col) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    private int evaluate() {
        int score = 0;
        // Add your evaluation logic here
        return score;
    }

    private boolean hasWon(Seed seed) {
        // Add your winning logic here
        return false;
    }
}