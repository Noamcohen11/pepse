package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import danogl.gui.rendering.AnimationRenderable;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The avatar of the game.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;

    private static final float ENERGY_GAIN = 1;
    private static final float ENERGY_RUN_LOSE = 0.5f;
    private static final float ENERGY_JUMP_LOSE = 10;
    private static final float MAX_ENERGY = 100;
    private static final String[] IDLE_IMAGES =
            IntStream.range(0, 4).mapToObj(
                    i -> Constants.ASSETS_FOLDER + "/idle_" + i + ".png").toArray(String[]::new);

    private final UserInputListener inputListener;

    private float energy;

    // Update the x velocity of the avatar.
    private float updateVelocityX() {
        float xVel = 0;
        if (energy > ENERGY_RUN_LOSE) {
            if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
                xVel -= VELOCITY_X;
            if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
                xVel += VELOCITY_X;
            if (xVel != 0) {
                energy -= ENERGY_RUN_LOSE;
            }
        }
        return xVel;
    }
    // Update the y velocity of the avatar.
    private void updateVelocityY() {
        if (energy > ENERGY_JUMP_LOSE) {
            if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
                transform().setVelocityY(VELOCITY_Y);
                energy -= ENERGY_JUMP_LOSE;
            }
        }
    }

    /**
     * Create the avatar.
     *
     * @param pos The position of the avatar.
     * @param inputListener The input listener of the game.
     * @param imageReader The image reader of the game.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(Constants.AVATAR_SIZE),
                new AnimationRenderable(
                        Arrays.stream(IDLE_IMAGES).map(
                                idleImage -> imageReader.readImage(idleImage, true))
                                .toArray(Renderable[]::new),
                        1));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;

        energy = MAX_ENERGY;
    }

    /**
     * Update the avatar.
     *
     * @param deltaTime The time since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = updateVelocityX();
        transform().setVelocityX(xVel);
        updateVelocityY();
        if (getVelocity().x() == 0 && getVelocity().y() == 0) {
            energy += ENERGY_GAIN;
        }
        if (energy > MAX_ENERGY) {
            energy = MAX_ENERGY;
        }
    }

    /**
     *  Get the energy of the avatar.
     *
     * @return The energy of the avatar.
     */
    public float getEnergy() {
        return energy;
    }



}
