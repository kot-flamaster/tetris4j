package kot.flamaster;

import kot.flamaster.logic.Tetromino;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Board extends JPanel {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 30;

    private Color[][] board;

    public Board() {
        board = new Color[WIDTH][HEIGHT];
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                g.setColor(board[x][y] != null ? board[x][y] : Color.BLACK);
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    private boolean isValidMove(Tetromino tetromino, int posX, int posY) {
        int[][] shape = tetromino.getShape();

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y] != 0) {
                    int boardX = posX + x;
                    int boardY = posY + y;

                    if (boardX < 0 || boardX >= WIDTH || boardY < 0 || boardY >= HEIGHT || board[boardX][boardY] != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}



