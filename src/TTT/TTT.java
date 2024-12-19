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
    private JButton quitButton;
    private JButton singlePlayerButton;
    private JButton multiPlayerButton;
    private boolean isSinglePlayer;
    private int gridSize;

    public TTT() {
        setLayout(new BorderLayout());
        initWelcomePanel();
        add(welcomePanel, BorderLayout.CENTER);
    }

    private void initWelcomePanel() {
        welcomePanel = new JPanel(new BorderLayout());

        // Load the background image
        ImageIcon backgroundIcon = new ImageIcon("src/images/1panel.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());

        // Add welcome label
        JLabel welcomeLabel = new JLabel(" ", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Configure GridBagConstraints for the welcome label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 40); // Add some padding
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(welcomeLabel, gbc);

        // Add start button
        startButton = new JButton(" ");
        styleButton(startButton);
        startButton.setPreferredSize(new Dimension(180, 100)); // Set preferred size for the button
        startButton.setOpaque(false); // Make the button transparent
        startButton.setContentAreaFilled(false); // Remove the button's content area fill
        startButton.setBorderPainted(false); // Do not paint the button border
        startButton.setFocusPainted(false); // Do not paint the focus rectangle
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

        // Adjust constraints for the button to move it down
        gbc.gridy = 1; // Position the button below the welcome label
        gbc.anchor = GridBagConstraints.CENTER; // Center the button horizontally
        gbc.insets = new Insets(550, 70, 10, 50);
        backgroundLabel.add(startButton, gbc);

        // Add the background label to the welcome panel
        welcomePanel.add(backgroundLabel, BorderLayout.CENTER);
    }

    private void initPlayerSelectionPanel() {
        playerSelectionPanel = new JPanel(new BorderLayout());

        // Load the background image
        ImageIcon backgroundIcon = new ImageIcon("src/images/panel2new.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());

        // Add player selection label
        JLabel playerLabel = new JLabel(" ", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Configure GridBagConstraints for the player label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundLabel.add(playerLabel, gbc);

        // Add single player button
        singlePlayerButton = new JButton(" ");
        styleButton(singlePlayerButton);
        singlePlayerButton.setPreferredSize(new Dimension(400, 200)); // Set larger preferred size
        singlePlayerButton.setOpaque(false); // Make the button transparent
        singlePlayerButton.setContentAreaFilled(false); // Remove the button's content area fill
        singlePlayerButton.setBorderPainted(false); // Do not paint the button border
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

        // Adjust constraints for the single player button
        gbc.gridy = 1; // Position below the player label
        gbc.insets = new Insets(100, -750, 0, 0); // Increase top padding to move the button down
        backgroundLabel.add(singlePlayerButton, gbc);

        // Add multiplayer button
        multiPlayerButton = new JButton(" ");
        styleButton(multiPlayerButton);
        multiPlayerButton.setPreferredSize(new Dimension(400, 200)); // Set larger preferred size
        multiPlayerButton.setOpaque(false); // Make the button transparent
        multiPlayerButton.setContentAreaFilled(false); // Remove the button's content area fill
        multiPlayerButton.setBorderPainted(false); // Do not paint the button border
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

        // Adjust constraints for the multiplayer button
        gbc.gridy = 2; // Position below the single player button
        gbc.insets = new Insets(-200, 800, 50, 0); // Standard padding
        backgroundLabel.add(multiPlayerButton, gbc);

        // Add the background label to the player selection panel
        playerSelectionPanel.add(backgroundLabel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
    }

    private void initGridSelectionPanel() {
        gridSelectionPanel = new JPanel(new GridLayout(4, 1));
        JLabel gridLabel = new JLabel("Select Grid Size", SwingConstants.CENTER);
        gridLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gridSelectionPanel.add(gridLabel);

        JButton grid3x3Button = new JButton("3x3");
        styleButton(grid3x3Button);
        grid3x3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 3;
                startGame();
            }
        });
        gridSelectionPanel.add(grid3x3Button);

        JButton grid5x5Button = new JButton("5x5");
        styleButton(grid5x5Button);
        grid5x5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 5;
                startGame();
            }
        });
        gridSelectionPanel.add(grid5x5Button);

        JButton grid7x7Button = new JButton("7x7");
        styleButton(grid7x7Button);
        grid7x7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridSize = 7;
                startGame();
            }
        });
        gridSelectionPanel.add(grid7x7Button);
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
        initGamePanel();
        add(statusBar, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void initGamePanel() {
        statusBar = new JLabel(" ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setPreferredSize(new Dimension(400, 30));

        quitButton = new JButton("Quit");
        styleButton(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiPlayerButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);

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
        frame.setSize(Cell.SIZE * game.gridSize, Cell.SIZE * game.gridSize + 100); // Adjusted height
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}