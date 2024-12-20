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

import java.awt.*;

public class Cell {
    public static final int SIZE = 100;
    Seed content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        newGame();
    }

    public void newGame() {
        content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        if (content != Seed.NO_SEED) {
            Image img = content.getImage();
            if (img != null) {
                g.drawImage(img, col * SIZE, row * SIZE, SIZE, SIZE, null);
            }
        }
    }
}