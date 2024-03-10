package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 * The trunk of a tree.
 * The trunk is a GameObject with a RectangleRenderable.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Trunk extends GameObject {
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Trunk(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions, new RectangleRenderable((TRUNK_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * Change the color of the trunk.
     * The new color is an approximation of the original color.
     */
    public void changeColor() {
        renderer().setRenderable(
                new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
    }
}
