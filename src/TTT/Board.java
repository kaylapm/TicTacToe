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

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private int rows;
    private int cols;
    private int winCondition;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;
    public static final int Y_OFFSET = 1;

    private Image backgroundImage;
    Cell[][] cells;

    public Board(int size) {
        this.rows = size;
        this.cols = size;
        this.winCondition = (size == 3) ? 3 : 4;
        initGame();
        backgroundImage = new ImageIcon("src/images/background.png").getImage();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void initGame() {
        cells = new Cell[rows][cols];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public void newGame() {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col].newGame();
            }
        }
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        cells[selectedRow][selectedCol].content = player;

        if (checkWin(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    if (cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING;
                    }
                }
            }
            return State.DRAW;
        }
    }

    private boolean checkWin(Seed player, int row, int col) {
        return (checkLine(player, row, 0, 0, 1) ||
                checkLine(player, 0, col, 1, 0) ||
                checkLine(player, row - Math.min(row, col), col - Math.min(row, col), 1, 1) ||
                checkLine(player, row - Math.min(row, cols - 1 - col), col + Math.min(row, cols - 1 - col), 1, -1));
    }

    private boolean checkLine(Seed player, int startRow, int startCol, int deltaRow, int deltaCol) {
        int count = 0;
        for (int i = 0; i < Math.max(rows, cols); i++) {
            int r = startRow + i * deltaRow;
            int c = startCol + i * deltaCol;
            if (r >= 0 && r < rows && c >= 0 && c < cols && cells[r][c].content == player) {
                count++;
                if (count == winCondition) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        int canvasWidth = Cell.SIZE * cols;
        int canvasHeight = Cell.SIZE * rows;

        g.setColor(COLOR_GRID);
        for (int row = 1; row < rows; ++row) {
            g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
                    canvasWidth - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < cols; ++col) {
            g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET,
                    GRID_WIDTH, canvasHeight - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}