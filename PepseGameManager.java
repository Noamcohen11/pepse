package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.*;

import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;

/**
 * The Pepse game manager.
 * It manages every part of the game and it's objects.
 */
public class PepseGameManager extends GameManager {

    private static final int cycleLength = 10;

    /**
     * Initialize the game.
     * Builds the game's objects and sets the game's window.
     *
     * @param imageReader The image reader.
     *                    It reads the images from the file system.
     *                    It's used to load the images of the game's objects.
     *
     * @param soundReader The sound reader.
     *                    It reads the sounds from the file system.
     *
     * @param inputListener The input listener.
     *
     * @param windowController The window controller.
     *                         It's used to set the window's dimensions and title.
     *
     */
    @Override
    public void initializeGame(
            ImageReader imageReader,
            SoundReader soundReader, UserInputListener inputListener,
            WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()),
                Layer.BACKGROUND);

        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
        // Add the terrain
        // print the amount of blocks:

        for (Block block : terrain.createInRange(0, 3000)) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        this.gameObjects().addGameObject(
                Night.create(windowController.getWindowDimensions(),cycleLength),
                Layer.FOREGROUND);

        this.gameObjects().addGameObject(
                Sun.create(windowController.getWindowDimensions(),cycleLength),
                Layer.BACKGROUND);
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
