package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.util.EnergyHolder;

import java.awt.*;

/**
 * A fruit that can be eaten by the avatar.
 */
public class Fruit extends GameObject {
    private static final String TAG = "fruit";
    private static final Color FRUIT_COLOR = new Color(227, 255, 100);
    private static final int SIZE = 20;
    private static final int ENERGY_GAIN = 10;
    private boolean isActive = true;
    private EnergyHolder AVATAR_OBSERVER;

    /**
     * Create a Fruit.
     *
     * @param topLeftCorner The top left corner of the block.
     */
    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR)));

        this.setTag(TAG);
        AVATAR_OBSERVER = null;
    }

    /**
     * Called when a collision occurs with this GameObject.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(Constants.AVATAR_TAG) && isActive) {
            if (AVATAR_OBSERVER != null) {
                AVATAR_OBSERVER.addEnergy(ENERGY_GAIN);
            }
            this.hideFruit();
        }
    }

    // Show the fruit after a certain time.
    private void showFruit() {
        isActive = true;
        this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR)));
    }

    // Hide the fruit after a certain time.
    private void hideFruit() {
        isActive = false;
        this.renderer().setRenderable(null);
        new ScheduledTask(this, Constants.CYCLE_LENGTH, true, this::showFruit);
    }

    /**
     * Set the avatar observer.
     *
     * @param observer The avatar observer.
     */
    public void setAvatarObserver(EnergyHolder observer) {
        AVATAR_OBSERVER = observer;
    }
}
