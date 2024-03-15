package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Block;

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
    private static final Vector2 TRUNK_DIMENSIONS = new Vector2(Block.SIZE, 300);
    private static final int LEAVES_IN_ROW = 10;
    private static final float ODDS_OF_TREE = 1f / 10;
    private static final float ODDS_OF_LEAF = 1f / 2;
    private static final float ODDS_OF_FRUIT = 1f / 4;

    // Create a tree in a random position.
    private Tree createTree(Vector2 bottomLeftCorner) {
        Vector2 trunkTopLeftCorner = new Vector2(
                bottomLeftCorner.x(),
                bottomLeftCorner.y() - (TRUNK_DIMENSIONS.y() + Block.SIZE) / 2);
        Tree tree =
                new Tree(trunkTopLeftCorner,
                        new ArrayList<>(), new ArrayList<>());
        tree.trunk().setDimensions(TRUNK_DIMENSIONS);
        createTreeLeafsAndFruits(bottomLeftCorner, trunkTopLeftCorner, tree);
        return tree;
    }

    // Create a tree's leaves and fruits on a grid
    private static void createTreeLeafsAndFruits(Vector2 bottomLeftCorner, Vector2 trunkTopLeftCorner, Tree tree) {
        Vector2 leafsTopLeftCorner = new Vector2(
                trunkTopLeftCorner.x() - (Block.SIZE * LEAVES_IN_ROW - TRUNK_DIMENSIONS.x()) / 2,
                bottomLeftCorner.y() - TRUNK_DIMENSIONS.y() - (float) (Block.SIZE * LEAVES_IN_ROW) / 2);
        for (int i = 0; i < LEAVES_IN_ROW; i++) {
            for (int j = 0; j < LEAVES_IN_ROW; j++) {
                if (Math.random() > ODDS_OF_LEAF) {
                    Vector2 leafTopLeftCorner = new Vector2(
                            leafsTopLeftCorner.x() + i * Block.SIZE,
                            leafsTopLeftCorner.y() + j * Block.SIZE);
                    tree.leaves().add(new Leaf(leafTopLeftCorner));
                    continue;
                }
                if (Math.random() < ODDS_OF_FRUIT) {
                    Vector2 fruitTopLeftCorner = new Vector2(
                            leafsTopLeftCorner.x() + i * Block.SIZE,
                            leafsTopLeftCorner.y() + j * Block.SIZE);
                    tree.fruits().add(new Fruit(fruitTopLeftCorner));
                }
            }
        }
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
        int statingX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        ArrayList<Tree> trees = new ArrayList<>();
        for (int x = statingX; x < maxX; x += Block.SIZE) {
            // if random is 1 in 10, create a tree.
            if (Math.random() < ODDS_OF_TREE) {
                trees.add(createTree(
                        new Vector2(x, GetTreePosition.apply((float) (x)))));
                x += Block.SIZE * LEAVES_IN_ROW / 2;
            }
        }
        return trees;
    }
}
