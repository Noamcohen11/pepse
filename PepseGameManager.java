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

/**
 * The Pepse game manager.
 * It manages every part of the game and it's objects.
 *
 * @author Noam Cohen
 */
public class PepseGameManager extends GameManager {

    private static final int CYCLE_LENGTH = 30;
    private static final int ENERGY_UI_SIZE = 100;

    // Create the terrain
    private void createTerrain(WindowController windowController) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
        for (Block block : terrain.createInRange(0, (int) windowController.getWindowDimensions().x())) {
            this.gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    // Create the day-night cycle - the sun and the night.
    private void createDayNightCycle(WindowController windowController) {
        GameObject night = Night.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        this.gameObjects().addGameObject(night, Layer.FOREGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
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
        Vector2 initialAvatarPos = new Vector2(
                windowController.getWindowDimensions().x()/2-Constants.AVATAR_SIZE,
                windowController.getWindowDimensions().y()*Constants.DIRT_SKY_RATIO
                        -Constants.AVATAR_SIZE);
        Avatar avatar = new Avatar(initialAvatarPos, inputListener, imageReader);
        this.gameObjects().addGameObject(avatar, Layer.DEFAULT);
        this.gameObjects().addGameObject(new EnergyUi(new Vector2(0,0),
                new Vector2(ENERGY_UI_SIZE,ENERGY_UI_SIZE),
                () -> (int) avatar.getEnergy()), Layer.UI);
    }


    /**
     * The main function.
     *
     *
     *
     * @param args The command line arguments. - numOfBrickRows, bricksPerRow.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
