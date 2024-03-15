package pepse.world.trees;

import pepse.world.Block;

import java.util.ArrayList;

/**
 * A pair of trunk and leaves is a tree.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Tree {
    private final Block trunk;
    private final ArrayList<Leaf> leaves;
    private final ArrayList<Fruit> fruits;


    /**
     * Construct a new tree.
     *
     * @param trunk The trunk of the tree.
     * @param leafs The leaves of the tree.
     */
    public Tree(Block trunk, ArrayList<Leaf> leafs, ArrayList<Fruit> fruits) {
        this.trunk = trunk;
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
}