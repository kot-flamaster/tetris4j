package kot.flamaster;

import kot.flamaster.animation.AnimatedController;
import kot.flamaster.animation.AnimationController;
import kot.flamaster.animation.NoAnimationController;
import kot.flamaster.logic.FigureInstance;
import kot.flamaster.logic.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {

    private static final int TIMER_INTERVAL = 500;
    private AnimationController animationController;

    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 30;
    private static final int TILE_SIZE = 30;

    private Color[][] board;

    private Timer timer;
    private FigureInstance currentFigure;
    private int currentX;
    private int currentY;

    private boolean gameOver;
    private int score;
    private boolean isPaused = false;

    private boolean animationEnabled;
    private boolean isEnabledAnimation(){
        return animationEnabled;
    }
    private void changeAnimationMode(){
        this.animationEnabled = !animationEnabled;
    }

    public void initAnimationController(){
        if (!isEnabledAnimation()) {
            animationController = new NoAnimationController();
        } else {
            animationController = new AnimatedController(this::repaint);
        }
    }

    public void toggleAnimation() {
        changeAnimationMode();
        initAnimationController();
    }

    public Board() {
        initAnimationController();

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
        timer = new Timer(TIMER_INTERVAL, new ActionListener() {
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
                Color color = board[x][y] != null ? board[x][y] : Color.BLACK;
                drawTile(g, x * TILE_SIZE, y * TILE_SIZE, color);
            }
        }
    }

    private void drawInfo(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
        // Add more info if needed, e.g. level, lines cleared, etc.
        if (isPaused) {
            g.drawString("Paused", 10, 50);
        }
        if(animationController.isAnimating()){
            g.drawString("Animation ON", 10, 80);
        }
    }


    private void drawTetromino(Graphics g) {
        int[][] shape = currentFigure.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y] != 0) {
                    int px = (currentX + x) * TILE_SIZE;
                    int py = (currentY + y) * TILE_SIZE;

                    if (animationController.isAnimating()) {
                        py += (TILE_SIZE * animationController.getAnimationFrame()) / 10;
                    }

                    drawTile(g, px, py, currentFigure.getColor());
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
                drop();
                break;
            case KeyEvent.VK_LEFT:
                // Move tetromino left
                move(-1);
                break;
            case KeyEvent.VK_RIGHT:
                // Move tetromino right
                move(1);
                break;
            case KeyEvent.VK_SPACE:
                // Drop tetromino
                break;
            case KeyEvent.VK_P:
                pauseGame();
                break;
            case KeyEvent.VK_N:
                startNewGame();
                break;
            case KeyEvent.VK_T:
                toggleAnimation();
                break;
        }
    }
    private void drop() {
        if (isValidMove(currentFigure, currentX, currentY + 1)) {
//            timer.stop();
            animationController.startAnimation();
//            timer.start();
            currentY++;
        } else {
            placeTetromino();
        }
    }
    private void updateGame() {
        if (!gameOver) {
            // Move the current tetromino down
            if (currentFigure == null) {
                spawnNewTetromino();
            }

            drop();
            repaint();
        }
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
                        gameOver = true;
                        timer.stop();
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
        for (int y = 0; y < HEIGHT; y++) {
            boolean fullLine = true;
            for (int x = 0; x < WIDTH; x++) {
                if (board[x][y] == null) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                // Remove line and shift down all lines above
                for (int k = y; k > 0; k--) {
                    for (int x = 0; x < WIDTH; x++) {
                        board[x][k] = board[x][k - 1];
                    }
                }
                // Update score
                score += 100;
            }
        }
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

    private void pauseGame() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
        repaint();
    }

    private void startNewGame() {
        isPaused = false;
        gameOver = false;
        score = 0;
        clearBoard();
        spawnNewTetromino();
        timer.start();
    }

    private void clearBoard() {
        board = new Color[WIDTH][HEIGHT];
    }


    private void drawTile(Graphics g, int x, int y, Color color) {
        if (color != null) {
            g.setColor(color);
            g.fillRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2);

            g.setColor(color.brighter());
            g.drawLine(x, y + TILE_SIZE - 1, x, y);
            g.drawLine(x, y, x + TILE_SIZE - 1, y);

            g.setColor(color.darker());
            g.drawLine(x + 1, y + TILE_SIZE - 1, x + TILE_SIZE - 1, y + TILE_SIZE - 1);
            g.drawLine(x + TILE_SIZE - 1, y + TILE_SIZE - 1, x + TILE_SIZE - 1, y + 1);
        }
    }
}



