package pepse.world.trees;

import danogl.util.Vector2;
import pepse.Constants;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * A collection of trees in random positions.
 * A tree is a collection of trunks and leaves.
 *
 * @author Noam Cohen and Gilad Omesi.
 */
public class Flora {
    private final Function<Float, Float> GetTreePosition;
    private static final float ODDS_OF_TREE = 1f / 10;

    // Create a tree in a random position.
    private Tree createTree(Vector2 bottomLeftCorner) {
        return new Tree(bottomLeftCorner,
                new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Construct a new flora.
     * This gets the function that supplies the position of a tree.
     *
     * @param GetTreePosition A function of a random position for a tree.
     */
    public Flora(Function<Float, Float> GetTreePosition) {
        this.GetTreePosition = GetTreePosition;
    }

    /**
     * Create trees in a random positions.
     * each position has a 1 in 10 chance of having a tree.
     * Note that we will separate the trees to prevent leaves overlap.
     *
     * @return A tree array in random positions.
     */
    public ArrayList<Tree> createInRange(int minX, int maxX) {
        int statingX = (int) (Math.floor((float) minX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE);
        ArrayList<Tree> trees = new ArrayList<>();
        for (int x = statingX; x < maxX; x += Constants.BLOCK_SIZE) {
            // if random is 1 in 10, create a tree.
            if (Math.random() < ODDS_OF_TREE) {
                trees.add(createTree(
                        new Vector2(x, GetTreePosition.apply((float) (x)))));
                x += Constants.BLOCK_SIZE * Constants.LEAVES_IN_ROW / 2;
            }
        }
        return trees;
    }
}
