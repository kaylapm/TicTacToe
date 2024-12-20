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

import TTT.soundEffect;
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
    private Timer stopwatchTimer;
    private long stopwatchStartTime;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private AIPlayer aiPlayer;
    private JPanel welcomePanel;
    private JPanel playerSelectionPanel;
    private JPanel gridSelectionPanel;
    private JButton startButton;
    private JButton quitButton;
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private JButton backButton;
    private boolean isSinglePlayer;
    private int gridSize;
    private int playerScore = 0;
    private int computerScore = 0;
    private int tieScore = 0;
    private JLabel scoreLabel;
    private Image backgroundImage;
    private boolean isMusicEnabled = true;
    private JLabel stopwatchLabel;

    public TTT() {
        setLayout(new BorderLayout());
        initWelcomePanel();
        add(welcomePanel, BorderLayout.CENTER);
        backgroundImage = new ImageIcon("src/images/background.png").getImage();
        soundEffect.initGame();
    }

    private void initWelcomePanel() {
        welcomePanel = new JPanel(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("src/images/welcome.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 40);
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(welcomeLabel, gbc);

        startButton = new JButton("");
        styleButton(startButton);
        startButton.setPreferredSize(new Dimension(180, 100));
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            if (isMusicEnabled) {
                soundEffect.BACKSOUND.play();
            }
            remove(welcomePanel);
            initPlayerSelectionPanel();
            add(playerSelectionPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(500, 150, 10, 50);
        backgroundLabel.add(startButton, gbc);

        JButton musicButton = new JButton(isMusicEnabled ? " " : " ");
        musicButton.addActionListener(e -> {
            isMusicEnabled = !isMusicEnabled;
            musicButton.setText(isMusicEnabled ? " " : " ");
        });
        musicButton.setPreferredSize(new Dimension(100, 200));
        gbc.gridy = 2;
        gbc.insets = new Insets(-250, -350, 10, 10);
        musicButton.setOpaque(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setBorderPainted(false);
        musicButton.setFocusPainted(false);
        backgroundLabel.add(musicButton, gbc);

        welcomePanel.add(backgroundLabel, BorderLayout.CENTER);
    }


    private void initPlayerSelectionPanel() {
        playerSelectionPanel = new JPanel(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("src/images/panel2new.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());

        JLabel playerLabel = new JLabel(" ", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(playerLabel, gbc);

        singlePlayerButton = new JButton(" ");
        styleButton(singlePlayerButton);
        singlePlayerButton.setPreferredSize(new Dimension(400, 200));
        singlePlayerButton.setOpaque(false);
        singlePlayerButton.setContentAreaFilled(false);
        singlePlayerButton.setBorderPainted(false);
        singlePlayerButton.addActionListener(new ActionListener() {
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


        gbc.gridy = 1;
        gbc.insets = new Insets(100, -750, 0, 0);
        backgroundLabel.add(singlePlayerButton, gbc);

        multiPlayerButton = new JButton(" ");
        styleButton(multiPlayerButton);
        multiPlayerButton.setPreferredSize(new Dimension(400, 200));
        multiPlayerButton.setOpaque(false);
        multiPlayerButton.setContentAreaFilled(false);
        multiPlayerButton.setBorderPainted(false);
        multiPlayerButton.addActionListener(new ActionListener() {
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

        gbc.gridy = 2;
        gbc.insets = new Insets(-200, 800, 50, 0);
        backgroundLabel.add(multiPlayerButton, gbc);

        playerSelectionPanel.add(backgroundLabel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
    }

    private void initGridSelectionPanel() {
        gridSelectionPanel = new JPanel(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon("src/images/grid.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());

        JLabel gridLabel = new JLabel(" ", SwingConstants.CENTER);
        gridLabel.setFont(new Font("Arial", Font.BOLD, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(gridLabel, gbc);

        JButton grid3x3Button = new JButton(" ");
        styleButton(grid3x3Button);
        grid3x3Button.setPreferredSize(new Dimension(200, 150));
        grid3x3Button.setOpaque(false);
        grid3x3Button.setContentAreaFilled(false);
        grid3x3Button.setBorderPainted(false);
        grid3x3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 3;
                startGame();
            }
        });

        GridBagConstraints gbc3x3 = new GridBagConstraints();
        gbc3x3.gridx = 0;
        gbc3x3.gridy = 1;
        gbc3x3.insets = new Insets(300, -960, 10, 0);
        backgroundLabel.add(grid3x3Button, gbc3x3);

        JButton grid5x5Button = new JButton(" ");
        styleButton(grid5x5Button);
        grid5x5Button.setPreferredSize(new Dimension(200, 150));
        grid5x5Button.setOpaque(false);
        grid5x5Button.setContentAreaFilled(false);
        grid5x5Button.setBorderPainted(false);

        grid5x5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 5;
                startGame();
            }
        });

        GridBagConstraints gbc5x5 = new GridBagConstraints();
        gbc5x5.gridx = 0;
        gbc5x5.gridy = 2;
        gbc5x5.insets = new Insets(-200, 20, 10, 0);
        backgroundLabel.add(grid5x5Button, gbc5x5);

        JButton grid7x7Button = new JButton(" ");
        styleButton(grid7x7Button);
        grid7x7Button.setPreferredSize(new Dimension(200, 150));
        grid7x7Button.setOpaque(false);
        grid7x7Button.setContentAreaFilled(false);
        grid7x7Button.setBorderPainted(false);
        grid7x7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 7;
                startGame();
            }
        });

        GridBagConstraints gbc7x7 = new GridBagConstraints();
        gbc7x7.gridx = 0;
        gbc7x7.gridy = 3;
        gbc7x7.insets = new Insets(-200, 900, 10, 0);
        backgroundLabel.add(grid7x7Button, gbc7x7);

        gridSelectionPanel.add(backgroundLabel, BorderLayout.CENTER);
    }

    private void startGame() {
        remove(gridSelectionPanel);
        board = new Board(gridSize);
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;

        if (isSinglePlayer) {
            aiPlayer = new AIPlayer(board);
            aiPlayer.setSeed(Seed.NOUGHT);
        }

        if (isMusicEnabled) {
            soundEffect.BACKSOUND.play();
        } else {
            soundEffect.BACKSOUND.stop();
        }

        initGamePanel();
        add(statusBar, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);

        stopwatchStartTime = System.currentTimeMillis();
        stopwatchTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - stopwatchStartTime;
                long seconds = (elapsedTime / 1000) % 60;
                long minutes = (elapsedTime / (1000 * 60)) % 60;
                stopwatchLabel.setText(String.format("Stopwatch: %02d:%02d", minutes, seconds));
            }
        });
        stopwatchTimer.start();

        revalidate();
        repaint();
    }

    private void initGamePanel() {
        statusBar = new JLabel(" ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setPreferredSize(new Dimension(400, 30));

        stopwatchLabel = new JLabel("Stopwatch: 00:00");
        stopwatchLabel.setFont(FONT_STATUS);
        stopwatchLabel.setOpaque(true);
        stopwatchLabel.setBackground(COLOR_BG_STATUS);
        stopwatchLabel.setPreferredSize(new Dimension(400, 30));

        scoreLabel = new JLabel("Player: 0 | Tie: 0 | Computer: 0");
        scoreLabel.setFont(FONT_STATUS);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(COLOR_BG_STATUS);
        scoreLabel.setPreferredSize(new Dimension(400, 30));

        quitButton = new JButton("Quit");
        styleButton(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundEffect.BACKSOUND.stop();
                remove(board);
                remove(statusBar);
                add(gridSelectionPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        singlePlayerButton = new JButton("Single Player");
        styleButton(singlePlayerButton);
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSinglePlayer = true;
                newGame();
            }
        });

        multiPlayerButton = new JButton("Multiplayer");
        styleButton(multiPlayerButton);
        multiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSinglePlayer = false;
                newGame();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(scoreLabel);
        buttonPanel.add(stopwatchLabel);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiPlayerButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                soundEffect.CLICK.play();
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
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        board.paint(g);
        if (currentState == State.PLAYING) {
            statusBar.setText((currentPlayer == Seed.CROSS ? "X" : "O") + "'s Turn");
        } else if (currentState == State.DRAW) {
            soundEffect.DRAW.play();
            statusBar.setText("It's a Draw! Click to play again.");
            tieScore++;
        } else {
            soundEffect.WIN.play();
            if (currentState == State.CROSS_WON) {
                playerScore++;
            } else if (currentState == State.NOUGHT_WON) {
                computerScore++;
            }
            statusBar.setText((currentState == State.CROSS_WON ? "X" : "O") + " Won! Click to play again.");
        }
        scoreLabel.setText("Player: " + playerScore + " | Tie: " + tieScore + " | Computer: " + computerScore);
    }

    public static void play() {
        JFrame frame = new JFrame(TITLE);
        TTT game = new TTT();
        frame.setContentPane(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(Cell.SIZE * game.gridSize, Cell.SIZE * game.gridSize + 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}