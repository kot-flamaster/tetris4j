package kot.flamaster.logic;

import java.awt.*;

public enum Tetromino {
    I(new int[][] {
            {1, 1, 1, 1}
    },
            Color.CYAN),
    O(new int[][] {
            {1, 1},
            {1, 1}
    },
            Color.YELLOW),
    T(new int[][] {
            {1, 1, 1},
            {0, 1, 0}
    },
            Color.MAGENTA),
    S(new int[][] {
            {0, 1, 1},
            {1, 1, 0}
    },
            Color.GREEN),
    Z(new int[][] {
            {1, 1, 0},
            {0, 1, 1}
    },
            Color.RED),
    J(new int[][] {
            {1, 0, 0},
            {1, 1, 1}
    },
            Color.BLUE),
    L(new int[][] {
            {0, 0, 1},
            {1, 1, 1}
    },
            Color.ORANGE);

    private final int[][] shape;
    private final Color color;

    Tetromino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int[][] rotate() {
        int[][] rotatedShape = new int[shape.length][shape.length];

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape.length; y++) {
                rotatedShape[y][shape.length - x - 1] = shape[x][y];
            }
        }

        return rotatedShape;
    }
}
