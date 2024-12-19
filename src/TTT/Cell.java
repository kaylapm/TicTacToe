package TTT;

import java.awt.*;

public class Cell {
    public static final int SIZE = 100; // Assuming a size for the cells
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