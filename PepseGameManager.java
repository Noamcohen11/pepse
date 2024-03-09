package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;

import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

/**
 * The Pepse game manager.
 * It manages every part of the game and it's objects.
 *
 * @author Noam Cohen
 */
public class PepseGameManager extends GameManager {

    private static final int cycleLength = 30;

    // Create the terrain
    private void createTerrain(WindowController windowController) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
        for (Block block : terrain.createInRange(0, (int) windowController.getWindowDimensions().x())) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    // Create the day-night cycle - the sun and the night.
    private void createDayNightCycle(WindowController windowController) {
        GameObject night = Night.create(windowController.getWindowDimensions(),cycleLength);
        this.gameObjects().addGameObject(night, Layer.FOREGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),cycleLength);
        this.gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        this.gameObjects().addGameObject(sunHalo, Layer.BACKGROUND+1);
    }

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
        createTerrain(windowController);
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
