package kot.flamaster.animation;

import javax.swing.*;

public class AnimatedController implements AnimationController {
    private static final int ANIMATION_DURATION = 500;
    private int animationFrame;
    private boolean isAnimating;

    private Runnable callback;

    public AnimatedController(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void startAnimation() {
        isAnimating = true;
        animationFrame = 0;
        Timer animationTimer = new Timer(ANIMATION_DURATION / 10, e -> {
            animationFrame++;

            if (animationFrame >= 10) {
                ((Timer) e.getSource()).stop();
            }
            else
            this.callback.run();

        });
        animationTimer.start();
    }

    @Override
    public boolean isAnimating() {
        return isAnimating;
    }

    @Override
    public int getAnimationFrame() {
        return animationFrame;
    }
}

