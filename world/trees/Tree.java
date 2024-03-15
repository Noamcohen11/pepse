package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * A pair of trunk and leaves is a tree.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Tree implements PropertyChangeListener {
    private final Block trunk;
    private final ArrayList<Leaf> leaves;
    private final ArrayList<Fruit> fruits;


    /**
     * Construct a new tree.
     *
     * @param trunkTopLeftCorner The top left corner of the trunk.
     * @param leafs              The leaves of the tree.
     * @param fruits             The fruits of the tree.
     */
    public Tree(Vector2 trunkTopLeftCorner, ArrayList<Leaf> leafs, ArrayList<Fruit> fruits) {
        this.trunk = new Block(trunkTopLeftCorner,
                new RectangleRenderable(ColorSupplier.approximateColor(getRandomTrunkColor())));
        this.leaves = leafs;
        this.fruits = fruits;
    }

    /**
     * Get the trunk of the tree.
     *
     * @return The trunk of the tree.
     */
    public Block trunk() {
        return trunk;
    }

    /**
     * Get the leaves of the tree.
     *
     * @return The leaves of the tree.
     */
    public ArrayList<Leaf> leaves() {
        return leaves;
    }

    /**
     * Get the fruits of the tree.
     *
     * @return The fruits of the tree.
     */
    public ArrayList<Fruit> fruits() {
        return fruits;
    }

    // Get a random color for the trunk
    private Color getRandomTrunkColor() {
        Random random = new Random();
        int red = 150 + random.nextInt(51);
        int green = 50 + random.nextInt(51);
        int blue = random.nextInt(51);
        return new Color(red, green, blue);
    }

    /**
     * Called when an event occurs.
     * This is called when the avatar jumps.
     * It changes the color of the trunk.
     *
     * @param evt The event that occurred.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Constants.AVATAR_JUMP_EVENT.equals(evt.getPropertyName())) {
            this.trunk().renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(getRandomTrunkColor())));
        }
    }
}