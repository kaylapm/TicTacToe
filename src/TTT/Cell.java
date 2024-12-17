package TTT;

import java.awt.*;

public class Cell {
    public static int SIZE = 120; // Ukuran default, dapat disesuaikan
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
        int cellWidth = SIZE;
        int cellHeight = SIZE;

        // Calculate the top-left corner of the seed to center it
        int x1 = col * cellWidth + (cellWidth - SEED_SIZE) / 2;
        int y1 = row * cellHeight + (cellHeight - SEED_SIZE) / 2;

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