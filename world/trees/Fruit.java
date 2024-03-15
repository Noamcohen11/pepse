package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A fruit that can be eaten by the avatar.
 */
public class Fruit extends GameObject implements PropertyChangeListener {
    private static final int SIZE = 20;
    private boolean isActive = true;
    private final PropertyChangeSupport support;
    private final Color BASIC_FRUIT_COLOR = new Color(166, 234, 63);

    /**
     * Create a Fruit.
     *
     * @param topLeftCorner The top left corner of the block.
     */
    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE),
                new OvalRenderable(ColorSupplier.approximateColor(Color.GREEN)));

        this.setTag(Constants.FRUIT_TAG);
        support = new PropertyChangeSupport(this);
    }

    /**
     * Add a property change listener.
     *
     * @param pcl The property change listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
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
            support.firePropertyChange(Constants.AVATAR_EAT_FRUIT_EVENT, null, null);
            this.hideFruit();
        }
    }

    // Show the fruit
    private void showFruit() {
        isActive = true;
        this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(BASIC_FRUIT_COLOR)));
    }

    // Hide the fruit for a certain time.
    private void hideFruit() {
        isActive = false;
        this.renderer().setRenderable(null);
        new ScheduledTask(this, Constants.CYCLE_LENGTH, true, this::showFruit);
    }

    /**
     * Called when an event occurs.
     * This is called when the avatar jumps.
     * It changes the color of the fruit.
     *
     * @param evt The event that occurred.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Constants.AVATAR_JUMP_EVENT.equals(evt.getPropertyName()) && isActive) {
            this.renderer().setRenderable(new OvalRenderable(
                    ColorSupplier.approximateColor(BASIC_FRUIT_COLOR, 30)));
        }
    }

}
