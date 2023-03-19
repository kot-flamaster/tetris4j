package kot.flamaster.animation;

public class NoAnimationController implements AnimationController {
    @Override
    public void startAnimation() {
        // Do nothing
    }

    @Override
    public boolean isAnimating() {
        return false;
    }

    @Override
    public int getAnimationFrame() {
        return 0;
    }
}