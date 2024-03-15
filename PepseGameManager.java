package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;

import danogl.util.Vector2;
import pepse.ui.EnergyUi;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Fruit;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;

/**
 * The Pepse game manager.
 * It manages every part of the game and it's objects.
 *
 * @author Noam Cohen
 */
public class PepseGameManager extends GameManager {

    private static final int ENERGY_UI_SIZE = 50;
    private static final int TREE_ZONE_START = Constants.AVATAR_SIZE + 3 * Block.SIZE;
    private Terrain terrain;

    // Create the terrain
    private void createTerrain(WindowController windowController) {
        terrain = new Terrain(windowController.getWindowDimensions(), Math.random());
        for (Block block : terrain.createInRange(0, (int) windowController.getWindowDimensions().x())) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    // Create the day-night cycle - the sun and the night.
    private void createDayNightCycle(WindowController windowController) {
        GameObject night = Night.create(windowController.getWindowDimensions(), Constants.CYCLE_LENGTH);
        this.gameObjects().addGameObject(night, Layer.FOREGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), Constants.CYCLE_LENGTH);
        this.gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        this.gameObjects().addGameObject(sunHalo, Layer.BACKGROUND + 1);
    }

    // Create the flora
    private void createFlora(WindowController windowController, Avatar avatar) {
        // Create the flora
        Flora flora = new Flora(terrain::groundHeightAt);
        for (Tree tree : flora.createInRange(TREE_ZONE_START, (int) windowController.getWindowDimensions().x())) {
            this.gameObjects().addGameObject(tree.trunk(), Layer.STATIC_OBJECTS);
            for (Leaf leaf : tree.leaves()) {
                this.gameObjects().addGameObject(leaf, Layer.FOREGROUND);
            }
            for (Fruit fruit : tree.fruits()) {
                fruit.addPropertyChangeListener(avatar);
                this.gameObjects().addGameObject(fruit, Layer.DEFAULT);
            }
        }
    }

    /**
     * Initialize the game.
     * Builds the game's objects and sets the game's window.
     *
     * @param imageReader      The image reader.
     *                         It reads the images from the file system.
     *                         It's used to load the images of the game's objects.
     * @param soundReader      The sound reader.
     *                         It reads the sounds from the file system.
     * @param inputListener    The input listener.
     * @param windowController The window controller.
     *                         It's used to set the window's dimensions and title.
     */
    @Override
    public void initializeGame(
            ImageReader imageReader,
            SoundReader soundReader, UserInputListener inputListener,
            WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()),
                Layer.BACKGROUND);
        createTerrain(windowController);
        Vector2 initialAvatarPos = new Vector2(
                Constants.AVATAR_SIZE,
                terrain.groundHeightAt(Constants.AVATAR_SIZE) - Constants.AVATAR_SIZE);
        Avatar avatar = new Avatar(initialAvatarPos, inputListener, imageReader);
        this.gameObjects().addGameObject(avatar, Layer.DEFAULT);
        this.gameObjects().addGameObject(new EnergyUi(new Vector2(0, 0),
                new Vector2(ENERGY_UI_SIZE, ENERGY_UI_SIZE),
                () -> (int) avatar.getEnergy()), Layer.UI);
        createFlora(windowController, avatar);
        createDayNightCycle(windowController);
    }


    /**
     * The main function.
     *
     * @param args The command line arguments. - numOfBrickRows, bricksPerRow.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
