package kot.flamaster;

import kot.flamaster.logic.FigureInstance;
import kot.flamaster.logic.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Board extends JPanel {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 30;
    private static final int TILE_SIZE = 30;

    private Color[][] board;

    private Timer timer;
    private FigureInstance currentFigure;
    private int currentX;
    private int currentY;

    private int score;

    public Board() {
        board = new Color[WIDTH][HEIGHT];
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));

        spawnNewTetromino();

        initKeyListener();
        setFocusable(true);
        initTimer();

        score = 0;
    }

    public void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    public void initTimer() {
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
        drawTetromino(g);
        drawInfo(g);

    }

    public void drawBoard(Graphics g) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                g.setColor(board[x][y] != null ? board[x][y] : Color.BLACK);
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
        // Add more info if needed, e.g. level, lines cleared, etc.
    }


    private void drawTetromino(Graphics g) {
        int[][] shape = currentFigure.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y] != 0) {
                    g.setColor(currentFigure.getColor());
                    g.fillRect((currentX + x) * TILE_SIZE, (currentY + y) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect((currentX + x) * TILE_SIZE, (currentY + y) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    private boolean isValidMove(FigureInstance figure, int posX, int posY) {
        int[][] shape = figure.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y] != 0) {
                    int boardX = posX + x;
                    int boardY = posY + y;

                    if (boardX < 0 || boardX >= WIDTH || boardY >= HEIGHT || (boardY >= 0 && board[boardX][boardY] != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
               rotate();
                break;
            case KeyEvent.VK_DOWN:
                // Move tetromino down
                break;
            case KeyEvent.VK_LEFT:
                // Move tetromino left
                break;
            case KeyEvent.VK_RIGHT:
                // Move tetromino right
                break;
            case KeyEvent.VK_SPACE:
                // Drop tetromino
                break;
            case KeyEvent.VK_P:
                // Pause game
                break;
        }
    }
    private void drop() {
        if (isValidMove(currentFigure, currentX, currentY + 1)) {
            currentY++;
        } else {
            placeTetromino();
        }
    }
    private void updateGame() {
        // Move the current tetromino down
        if (currentFigure == null) {
            spawnNewTetromino();
        }

        drop();
        repaint();
    }

    private void placeTetromino() {
        int[][] shape = currentFigure.getShape();

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y] != 0) {
                    int boardX = currentX + x;
                    int boardY = currentY + y;
                    if (boardY >= 0) {
                        board[boardX][boardY] = currentFigure.getColor();
                    } else {
//                        isGameOver = true;
                        return;
                    }
                }
            }
        }

        removeCompletedLines();
        spawnNewTetromino();
    }

    private void removeCompletedLines() {
        // Implement line-clearing logic
    }

    private void spawnNewTetromino() {
        currentFigure = new FigureInstance(Tetromino.randomTetromino());
        currentX = WIDTH / 2 - 1;
        currentY = -currentFigure.getShape().length + 1;
    }

    private void rotate() {
        FigureInstance rotatedFigure = new FigureInstance(currentFigure.getTetromino());
        rotatedFigure.setRotation((currentFigure.getRotation() + 1) % currentFigure.getTetromino().getShapes().length);

        if (isValidMove(rotatedFigure, currentX, currentY)) {
            currentFigure.rotate();
        }
    }

    private void move(int dx) {
        if (isValidMove(currentFigure, currentX + dx, currentY)) {
            currentX += dx;
        }
    }
}



