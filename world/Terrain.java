package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * The terrain of the game.
 */
public class Terrain {
    private static final int TERRAIN_DEPTH = 20;

    private static final int NOISE_FACOTR = 7;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private final float groundHeightAtX0;
    private static final String BLOCK_TAG = "ground";

    private final NoiseGenerator noiseGenerator;

    /**
     * Create a terrain.
     *
     * @param windowDimensions The dimensions of the window.
     * @param seed             The seed for the random number generator.
     */
    public Terrain(Vector2 windowDimensions, double seed) {
        groundHeightAtX0 = windowDimensions.y() * Constants.DIRT_SKY_RATIO;
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Returns the height of the ground at the given x coordinate.
     *
     * @param x The x coordinate.
     * @return The height of the ground at the given x coordinate.
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Constants.BLOCK_SIZE * NOISE_FACOTR);
        // print the noise
        return groundHeightAtX0 + noise;
    }

    /**
     * Create the terrain in the given range.
     * The terrain is created with blocks that are BLOCK_SIZE wide.
     * so the range is divided into blocks of size BLOCK_SIZE, and if it can be divided evenly,
     * we will create the blocks before the minX and after the maxX.
     *
     * @param minX The minimum x coordinate that terrain must be created in.
     * @param maxX The maximum x coordinate that terrain must be created in.
     * @return The blocks that were created in data structure of list.
     */
    public List<Block> createInRange(int minX, int maxX) {

        int statingX = (int) (Math.floor((float) minX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE);
        List<Block> blocks = new ArrayList<>();
        for (int x = statingX; x < maxX; x += Constants.BLOCK_SIZE) {
            float statingY = (float) (Math.floor(groundHeightAt(x) /
                    Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE);
            for (float y = statingY;
                 y < statingY + TERRAIN_DEPTH * Constants.BLOCK_SIZE; y += Constants.BLOCK_SIZE) {
                Block dirt = new Block(new Vector2(x, y),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                dirt.setTag(BLOCK_TAG);
                blocks.add(dirt);
            }
        }
        return blocks;
    }
}
