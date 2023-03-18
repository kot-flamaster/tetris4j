package kot.flamaster;

import javax.swing.*;

public class Tetris extends JFrame {

    public Tetris() {
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        Board board = new Board();
        add(board);
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.setVisible(true);
    }
}