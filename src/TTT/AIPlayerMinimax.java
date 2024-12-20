/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #5
 * 1 - 5026231158 - Kayla Putri Maharani
 * 2 - 5026231170 - Tahiyyah Mufhimah
 * 3 - 5026231206 - Rafael Dimas K
 */

package TTT;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMinimax extends AIPlayer {

    public AIPlayerMinimax(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        int depth = 4;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int[] result = minimax(depth, getMySeed(), alpha, beta);
        return new int[] {result[1], result[2]};
    }

    private int[] minimax(int depth, Seed player, int alpha, int beta) {
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
                    currentScore = minimax(depth - 1, getOppSeed(), alpha, beta)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                    alpha = Math.max(alpha, bestScore);
                } else {
                    currentScore = minimax(depth - 1, getMySeed(), alpha, beta)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                    beta = Math.min(beta, bestScore);
                }
                getBoard().cells[move[0]][move[1]].content = Seed.NO_SEED;
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

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

    private int evaluate() {
        int score = 0;
        int winCondition = getBoard().getWinCondition();

        for (int row = 0; row < getBoard().getRows(); ++row) {
            for (int col = 0; col <= getBoard().getCols() - winCondition; ++col) {
                score += evaluateLine(row, col, 0, 1, winCondition);
            }
        }
        for (int col = 0; col < getBoard().getCols(); ++col) {
            for (int row = 0; row <= getBoard().getRows() - winCondition; ++row) {
                score += evaluateLine(row, col, 1, 0, winCondition);
            }
        }
        for (int row = 0; row <= getBoard().getRows() - winCondition; ++row) {
            for (int col = 0; col <= getBoard().getCols() - winCondition; ++col) {
                score += evaluateLine(row, col, 1, 1, winCondition);
            }
            for (int col = winCondition - 1; col < getBoard().getCols(); ++col) {
                score += evaluateLine(row, col, 1, -1, winCondition);
            }
        }

        return score;
    }

    private int evaluateLine(int startRow, int startCol, int deltaRow, int deltaCol, int winCondition) {
        int score = 0;
        int countMySeed = 0;
        int countOppSeed = 0;

        for (int i = 0; i < winCondition; i++) {
            int row = startRow + i * deltaRow;
            int col = startCol + i * deltaCol;
            if (row >= 0 && row < getBoard().getRows() && col >= 0 && col < getBoard().getCols()) {
                if (getBoard().cells[row][col].content == getMySeed()) {
                    countMySeed++;
                } else if (getBoard().cells[row][col].content == getOppSeed()) {
                    countOppSeed++;
                }
            }
        }

        if (countMySeed == winCondition) {
            score += 1000;
        } else if (countOppSeed == winCondition) {
            score -= 1000;
        } else if (countMySeed == winCondition - 1 && countOppSeed == 0) {
            score += 100;
        } else if (countOppSeed == winCondition - 1 && countMySeed == 0) {
            score -= 100;
        }

        return score;
    }

    private boolean hasWon(Seed thePlayer) {
        return getBoard().stepGame(thePlayer, 0, 0) == State.CROSS_WON ||
                getBoard().stepGame(thePlayer, 0, 0) == State.NOUGHT_WON;
    }
}
