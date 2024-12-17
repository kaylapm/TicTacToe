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
    public static final Color COLOR_BG = new Color(240, 240, 240);
    public static final Color COLOR_BG_STATUS = new Color(200, 200, 200);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
    public static final Font FONT_STATUS = new Font("Arial", Font.PLAIN, 16);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private AIPlayer aiPlayer;
    private JPanel welcomePanel;
    private JPanel playerSelectionPanel;
    private JPanel gridSelectionPanel;
    private JButton startButton;
    private boolean isSinglePlayer;
    private int gridSize;

    public TTT() {
        setLayout(new BorderLayout());
        initWelcomePanel();
        add(welcomePanel, BorderLayout.CENTER);
    }

    private void initWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setBackground(COLOR_BG);
        welcomePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);

        startButton = new JButton("Start Game");
        styleButton(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(welcomePanel);
                initPlayerSelectionPanel();
                add(playerSelectionPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        gbc.gridy = 1;
        welcomePanel.add(startButton, gbc);
    }

    private void initPlayerSelectionPanel() {
        playerSelectionPanel = new JPanel();
        playerSelectionPanel.setBackground(COLOR_BG);
        playerSelectionPanel.setLayout(new GridLayout(1, 2));

        JButton onePlayerButton = new JButton("1 Player");
        styleButton(onePlayerButton);
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSinglePlayer = true;
                remove(playerSelectionPanel);
                initGridSelectionPanel();
                add(gridSelectionPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        JButton twoPlayerButton = new JButton("2 Players");
        styleButton(twoPlayerButton);
        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSinglePlayer = false;
                remove(playerSelectionPanel);
                initGridSelectionPanel();
                add(gridSelectionPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        playerSelectionPanel.add(onePlayerButton);
        playerSelectionPanel.add(twoPlayerButton);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 144, 255), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    private void initGridSelectionPanel() {
        gridSelectionPanel = new JPanel();
        gridSelectionPanel.setBackground(COLOR_BG);
        gridSelectionPanel.setLayout(new GridLayout(1, 3));

        JButton threeByThreeButton = new JButton("3x3");
        styleButton(threeByThreeButton);
        threeByThreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 3;
                Cell.SIZE = 120;
                startGame();
            }
        });

        JButton fiveByFiveButton = new JButton("5x5");
        styleButton(fiveByFiveButton);
        fiveByFiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 5;
                Cell.SIZE = 100;
                startGame();
            }
        });

        JButton sevenBySevenButton = new JButton("7x7");
        styleButton(sevenBySevenButton);
        sevenBySevenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 7;
                Cell.SIZE = 80;
                startGame();
            }
        });

        gridSelectionPanel.add(threeByThreeButton);
        gridSelectionPanel.add(fiveByFiveButton);
        gridSelectionPanel.add(sevenBySevenButton);
    }

    private void startGame() {
        remove(gridSelectionPanel);
        board = new Board(gridSize);
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;
        if (isSinglePlayer) {
            aiPlayer = new AIPlayerMinimax(board);
            aiPlayer.setSeed(Seed.NOUGHT);
        }
        initGamePanel();
        add(statusBar, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void initGamePanel() {
        statusBar = new JLabel(" ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setPreferredSize(new Dimension(400, 30));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols()
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                        if (isSinglePlayer && currentPlayer == Seed.NOUGHT && currentState == State.PLAYING) {
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
    }

    public void newGame() {
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;
        board.newGame();
        statusBar.setText(" ");
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);
        if (currentState == State.PLAYING) {
            statusBar.setText((currentPlayer == Seed.CROSS ? "X" : "O") + "'s Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setText("It's a Draw! Click to play again.");
        } else {
            statusBar.setText((currentState == State.CROSS_WON ? "X" : "O") + " Won! Click to play again.");
        }
    }

    public static void play() {
        JFrame frame = new JFrame(TITLE);
        TTT game = new TTT();
        frame.setContentPane(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(Cell.SIZE * game.gridSize, Cell.SIZE * game.gridSize + 50);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}