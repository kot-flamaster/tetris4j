package kot.flamaster.logic;

import java.awt.*;

public class FigureInstance implements IGameFigure {
    private final Tetromino tetromino;
    private int rotation;

    public FigureInstance(Tetromino tetromino) {
        this.tetromino = tetromino;
        this.rotation = 0;
    }

    public Tetromino getTetromino() {
        return tetromino;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void rotate() {
        setRotation((rotation + 1) % tetromino.getShapes().length);
    }

    @Override
    public Color getColor() {
        return tetromino.getColor();
    }

    @Override
    public int[][] getShape() {
        return tetromino.getShape(rotation);
    }
}
