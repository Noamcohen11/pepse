package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.awt.Color;

/**
 * The sun of the game.
 * The sun is a GameObject that starts in the middle of the window and is colored yellow.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Sun {
    private static final String SUN_TAG = "sun";
    private static final float SIZE = 80;
    private static final float INIT_POSITION = 0f;
    private static final float END_POSITION = 360f;

    /**
     * Create the sun.
     * The sun moves in a circle around the middle of the window.
     * We use a Transition to change the angle of the sun from 0 to 360 and back.
     *
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength      The length of the day-night cycle.
     * @return The sun as a GameObject.
     */

    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength) {

        // we rise at sunshine - the upper middle of the window.
        // Since the sun direction is 360 degrees,
        // we need to find a radius that will be visible in the window.

        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2 - SIZE / 2,
                windowDimensions.y() * (Constants.DIRT_SKY_RATIO) - SIZE / 2);

        float radius = Math.min(
                Math.min(cycleCenter.x(), cycleCenter.y()),
                windowDimensions.y() - cycleCenter.y());
        Vector2 initialSunCenter = new Vector2(cycleCenter.x(),
                cycleCenter.y() - radius + SIZE / 2);
        GameObject sun = new GameObject(
                new Vector2(initialSunCenter),
                new Vector2(SIZE, SIZE),
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);

        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter)),
                INIT_POSITION, END_POSITION,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );

        return sun;
    }
}
