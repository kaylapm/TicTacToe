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
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // Row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // Row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // Row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // Column 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // Column 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // Column 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // Diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // Opposite diagonal
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (board.cells[row1][col1].content == mySeed) {
            score = 1;
        } else if (board.cells[row1][col1].content == oppSeed) {
            score = -1;
        }

        // Second cell
        if (board.cells[row2][col2].content == mySeed) {
            if (score == 1) {   // cell1 is mySeed
                score = 10;
            } else if (score == -1) {  // cell1 is oppSeed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (board.cells[row2][col2].content == oppSeed) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (board.cells[row3][col3].content == mySeed) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (board.cells[row3][col3].content == oppSeed) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 0) {  // cell1 and/or cell2 is mySeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }

    private boolean hasWon(Seed seed) {
        // Add your winning logic here
        return false;
    }
}