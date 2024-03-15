package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import danogl.components.ScheduledTask;
import danogl.components.Transition;

import java.awt.*;
import java.util.Random;

/**
 * A leaf is a block that is part of a tree.
 *
 * @author Noam Cohen and Gilad Omesi.
 */
public class Leaf extends Block {
    private static final Random random = new Random();
    private static final String TAG = "leaf";
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final float LEAF_ANGLE_STARTING_VALUE = -2 * (float) Math.PI;
    private static final float LEAF_ANGLE_ENDING_VALUE = 2 * (float) Math.PI;
    private static final float LEAF_SIZE_STARTING_VALUE = 1.0f;
    private static final float LEAF_SIZE_ENDING_VALUE = 0.95f;
    private static final float LEAF_CYCLE_LENGTH = 0.5f;
    private static final int LEAF_TIME_SLOTS = 10;

    /**
     * Create a block.
     *
     * @param topLeftCorner The top left corner of the block.
     */
    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, new RectangleRenderable
                (ColorSupplier.approximateColor(LEAF_COLOR)));

        this.setTag(TAG);

        // Starting the leaf animation transition at a random time so leaves are out of sync
        float randomTime = ((float) random.nextInt(LEAF_TIME_SLOTS) / LEAF_TIME_SLOTS);
        new ScheduledTask(this, randomTime, false, this::leafWindTransitions);
    }

    /**
     * Starts the leaf wind transition while on the tree.
     * These transitions are changing the angle and size of the leaf
     */
    private void leafWindTransitions() {
        //the angle transition
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                LEAF_ANGLE_STARTING_VALUE,
                LEAF_ANGLE_ENDING_VALUE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                LEAF_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        //the size transition
        new Transition<>(
                this,
                (factor) -> this.setDimensions(new Vector2(SIZE, SIZE).mult(factor)),
                LEAF_SIZE_STARTING_VALUE,
                LEAF_SIZE_ENDING_VALUE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                LEAF_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}
