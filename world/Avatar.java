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
    private static final String[] IDLE_IMAGES =
            IntStream.range(0, 4).mapToObj(
                    i -> Constants.ASSETS_FOLDER + "/idle_" + i + ".png").toArray(String[]::new);

    private final UserInputListener inputListener;

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
    }

    /**
     * Update the avatar.
     *
     * @param deltaTime The time since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            xVel -= VELOCITY_X;
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            xVel += VELOCITY_X;
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
            transform().setVelocityY(VELOCITY_Y);
    }
}
