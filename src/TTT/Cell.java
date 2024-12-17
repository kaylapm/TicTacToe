package TTT;

import java.awt.*;

public class Cell {
    public static int SIZE = 120; // Default size, can be adjusted
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;

    Seed content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    public void newGame() {
        content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1 = col * SIZE + PADDING;
        int y1 = row * SIZE + PADDING;

        // Set thicker stroke for drawing
        g2d.setStroke(new BasicStroke(4)); // Adjust the thickness as needed

        if (content == Seed.CROSS) {
            g2d.setColor(TTT.COLOR_CROSS);
            g2d.drawLine(x1, y1, x1 + SEED_SIZE, y1 + SEED_SIZE);
            g2d.drawLine(x1 + SEED_SIZE, y1, x1, y1 + SEED_SIZE);
        } else if (content == Seed.NOUGHT) {
            g2d.setColor(TTT.COLOR_NOUGHT);
            g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
        }
    }
}