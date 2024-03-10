package pepse.world.trees;

import java.util.ArrayList;

/**
 * A pair of trunk and leaves is a tree.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Tree {
    private final Trunk trunk;
    private final ArrayList<Leaf> leaves;


    /**
     * Construct a new tree.
     *
     * @param trunk The trunk of the tree.
     * @param leafs The leaves of the tree.
     */
    public Tree(Trunk trunk, ArrayList<Leaf> leafs) {
        this.trunk = trunk;
        this.leaves = leafs;
    }

    /**
     * Get the trunk of the tree.
     *
     * @return The trunk of the tree.
     */
    public Trunk trunk() {
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
}