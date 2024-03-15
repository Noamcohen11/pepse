package pepse;

/**
 * The constants of the game.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Constants {

    /**
     * What precentage of the window's width the sky takes vs the dirt.
     */
    public static final float DIRT_SKY_RATIO = 2f / 3f;
    /**
     * The size of the avatar.
     */
    public static final int AVATAR_SIZE = 50;
    /**
     * The size of the block.
     */
    public static final int BLOCK_SIZE = 30;
    /**
     * The folder of the assets.
     */
    public static final String ASSETS_FOLDER = "assets";
    /**
     * The tag of the avatar.
     */
    public static final String AVATAR_TAG = "avatar";
    /**
     * The tag of the block.
     */
    public static final String BLOCK_TAG = "block";
    /**
     * The tag of the fruit.
     */
    public static final String FRUIT_TAG = "fruit";
    /**
     * The tag of the leaf.
     */
    public static final String LEAF_TAG = "leaf";
    /**
     * The length of the day-night cycle.
     */
    public static final int CYCLE_LENGTH = 30;

    /**
     * Event that is fired when the avatar eats a fruit.
     */
    public static final String AVATAR_EAT_FRUIT_EVENT = "avatarEatFruit";
    /**
     * Event that is fired when the avatar jumps.
     */
    public static final String AVATAR_JUMP_EVENT = "avatarJump";
    /**
     * The number of leaves to create in a row in a tree.
     */
    public static final int LEAVES_IN_ROW = 10;
}
