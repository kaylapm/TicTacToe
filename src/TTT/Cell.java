package TTT;

import java.awt.*;

public class Cell {
    public static int SIZE = 100; // Reduced size for better fit
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

        // Calculate the center position for the symbols
        int centerX = col * SIZE + SIZE / 2;
        int centerY = row * SIZE + SIZE / 2;

        // Set thicker stroke for drawing
        g2d.setStroke(new BasicStroke(4)); // Adjust the thickness as needed

        if (content == Seed.CROSS) {
            g2d.setColor(TTT.COLOR_CROSS);
            int offset = SEED_SIZE / 2;
            g2d.drawLine(centerX - offset, centerY - offset, centerX + offset, centerY + offset);
            g2d.drawLine(centerX + offset, centerY - offset, centerX - offset, centerY + offset);
        } else if (content == Seed.NOUGHT) {
            g2d.setColor(TTT.COLOR_NOUGHT);
            int radius = SEED_SIZE / 2;
            g2d.drawOval(centerX - radius, centerY - radius, SEED_SIZE, SEED_SIZE);
        }
    }
}