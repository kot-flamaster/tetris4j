package kot.flamaster.logic;

import java.awt.*;
import java.util.Arrays;

import java.awt.Color;

public enum Tetromino implements IGameFigure {
    I(new Color(0, 255, 255), new int[][][]{
            {
                    {1},
                    {1},
                    {1},
                    {1}
            },
            {
                    {1, 1, 1, 1}
            }
    }),
    O(new Color(255, 255, 0), new int[][][]{
            {
                    {1, 1},
                    {1, 1}
            }
    }),
    T(new Color(128, 0, 128), new int[][][]{
            {
                    {1, 1, 1},
                    {0, 1, 0}
            },
            {
                    {0, 1},
                    {1, 1},
                    {0, 1}
            },
            {
                    {0, 1, 0},
                    {1, 1, 1}
            },
            {
                    {1, 0},
                    {1, 1},
                    {1, 0}
            }
    }),
    S(new Color(0, 255, 0), new int[][][]{
            {
                    {0, 1, 1},
                    {1, 1, 0}
            },
            {
                    {1, 0},
                    {1, 1},
                    {0, 1}
            }
    }),
    Z(new Color(255, 0, 0), new int[][][]{
            {
                    {1, 1, 0},
                    {0, 1, 1}
            },
            {
                    {0, 1},
                    {1, 1},
                    {1, 0}
            }
    }),
    J(new Color(0, 0, 255), new int[][][]{
            {
                    {1, 0, 0},
                    {1, 1, 1}
            },
            {
                    {1, 1},
                    {1, 0},
                    {1, 0}
            },
            {
                    {1, 1, 1},
                    {0, 0, 1}
            },
            {
                    {0, 1},
                    {0, 1},
                    {1, 1}
            }
    }),
    L(new Color(255, 165, 0), new int[][][]{
            {
                    {0, 0, 1},
                    {1, 1, 1}
            },
            {
                    {1, 0},
                    {1, 0},
                    {1, 1}
            },
            {
                    {1, 1, 1},
                    {1, 0, 0}
            },
            {
                    {1, 1},
                    {0, 1},
                    {0, 1}
            }
    });

    private final Color color;
    private final int[][][] shapes;

    Tetromino(Color color, int[][][] shapes) {
        this.color = color;
        this.shapes = shapes;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int[][] getShape() {
        return getShape(0);
    }

    public int[][] getShape(int rotation) {
        return shapes[rotation];
    }

    public int[][][] getShapes() {
        return shapes;
    }

    public static Tetromino randomTetromino() {
        Tetromino[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}
