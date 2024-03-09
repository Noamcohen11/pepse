package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Terrain {
    private static final int TERRAIN_DEPTH = 20;
    private static final float DIRT_SKY_RATIO = 2f/3f;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private final float groundHeightAtX0;
    private static final String BLOCK_TAG = "ground";

    /**
     * Create a terrain.
     * @param windowDimensions The dimensions of the window.
     * @param seed The seed for the random number generator.
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * DIRT_SKY_RATIO;
    }

    /**
     * Returns the height of the ground at the given x coordinate.
     * @param x The x coordinate.
     * @return The height of the ground at the given x coordinate.
     */
    public float groundHeightAt(float x) { return groundHeightAtX0; }

    /**
     * Create the terrain in the given range.
     * The terrain is created with blocks that are Block.size wide.
     * so the range is divided into blocks of size Block.size, and if it can be divided evenly,
     * we will create the blocks before the minX and after the maxX.
     *
     * @param minX The minimum x coordinate that terrain must be created in.
     * @param maxX The maximum x coordinate that terrain must be created in.
     * @return The blocks that were created in data structure of list.
     */
    public List<Block> createInRange(int minX, int maxX) {

        int statingY = (int) (Math.floor(groundHeightAt(minX) / Block.SIZE) * Block.SIZE);
        int statingX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        List<Block> blocks = new ArrayList<>();
        for (float y = statingY;
             y < statingY + TERRAIN_DEPTH*Block.SIZE; y += Block.SIZE) {
            for (int x = statingX; x < maxX; x += Block.SIZE) {
                Block dirt = new Block(new Vector2(x, y),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                dirt.setTag(BLOCK_TAG);
                blocks.add(dirt);
            }
        }
        return blocks;
    }
}
