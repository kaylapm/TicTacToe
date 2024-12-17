package TTT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TTT extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private AIPlayer aiPlayer; // AI player instance
    private JPanel welcomePanel; // Welcome panel
    private JButton startButton; // Start game button

    public TTT() {
        setLayout(new BorderLayout());
        initWelcomePanel();
        add(welcomePanel, BorderLayout.CENTER);
    }

    private void initWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridBagLayout());
        welcomePanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 144, 255), 2), // Dodger Blue
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        gbc.gridy = 1;
        welcomePanel.add(startButton, gbc);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(welcomePanel);
                initGamePanel();
                revalidate();
                repaint();
            }
        });
    }

    private void initGamePanel() {
        board = new Board();
        aiPlayer = new AIPlayerMinimax(board); // Initialize AIPlayerMinimax
        aiPlayer.setSeed(Seed.NOUGHT); // Set AI seed

        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                        // AI Move
                        if (currentPlayer == Seed.NOUGHT && currentState == State.PLAYING) {
                            int[] aiMove = aiPlayer.move();
                            currentState = board.stepGame(currentPlayer, aiMove[0], aiMove[1]);
                            currentPlayer = Seed.CROSS;
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        newGame();
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        if (board != null) {
            board.paint(g);
        }

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public static void play() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new TTT());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}