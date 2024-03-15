package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
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
    private static final Color BASIC_TRUNK_COLOR = new Color(100, 50, 20);

    private static final Vector2 TRUNK_DIMENSIONS = new Vector2(Constants.BLOCK_SIZE, 300);
    private static final float ODDS_OF_LEAF = 1f / 2;
    private static final float ODDS_OF_FRUIT = 1f / 4;
    private static final int COLOR_DELTA = 50;


    /**
     * Construct a new tree.
     *
     * @param bottomLeftCorner The top left corner of the trunk.
     * @param leafs            The leaves of the tree.
     * @param fruits           The fruits of the tree.
     */
    public Tree(Vector2 bottomLeftCorner, ArrayList<Leaf> leafs, ArrayList<Fruit> fruits) {
        Vector2 trunkTopLeftCorner = new Vector2(
                bottomLeftCorner.x(),
                bottomLeftCorner.y() - (TRUNK_DIMENSIONS.y() + Constants.BLOCK_SIZE) / 2);
        this.trunk = new Block(trunkTopLeftCorner,
                new RectangleRenderable(ColorSupplier.approximateColor(BASIC_TRUNK_COLOR, COLOR_DELTA)));
        this.trunk.setDimensions(TRUNK_DIMENSIONS);
        this.leaves = leafs;
        this.fruits = fruits;
        createTreeLeafsAndFruits(bottomLeftCorner, bottomLeftCorner, this);
    }

    private static void createTreeLeafsAndFruits(Vector2 bottomLeftCorner,
                                                 Vector2 trunkTopLeftCorner, Tree tree) {
        Vector2 leafsTopLeftCorner = new Vector2(
                trunkTopLeftCorner.x() - (Constants.BLOCK_SIZE *
                        Constants.LEAVES_IN_ROW - TRUNK_DIMENSIONS.x()) / 2,
                bottomLeftCorner.y() - TRUNK_DIMENSIONS.y() - (float)
                        (Constants.BLOCK_SIZE * Constants.LEAVES_IN_ROW) / 2);
        for (int i = 0; i < Constants.LEAVES_IN_ROW; i++) {
            for (int j = 0; j < Constants.LEAVES_IN_ROW; j++) {
                if (Math.random() > ODDS_OF_LEAF) {
                    Vector2 leafTopLeftCorner = new Vector2(
                            leafsTopLeftCorner.x() + i * Constants.BLOCK_SIZE,
                            leafsTopLeftCorner.y() + j * Constants.BLOCK_SIZE);
                    tree.leaves().add(new Leaf(leafTopLeftCorner));
                    continue;
                }
                if (Math.random() < ODDS_OF_FRUIT) {
                    Vector2 fruitTopLeftCorner = new Vector2(
                            leafsTopLeftCorner.x() + i * Constants.BLOCK_SIZE,
                            leafsTopLeftCorner.y() + j * Constants.BLOCK_SIZE);
                    tree.fruits().add(new Fruit(fruitTopLeftCorner));
                }
            }
        }
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
            this.trunk().renderer().setRenderable(new RectangleRenderable(
                    ColorSupplier.approximateColor(BASIC_TRUNK_COLOR, COLOR_DELTA)));
        }
    }
}