package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.Color;

/**
 * The night of the game.
 * The night is a GameObject that is the size of the window and is colored black.
 * It's opacity changes according to the time of day.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class Night {
    private static final String NIGHT_TAG = "night";

    /**
     * Create the night.
     * The night is transparent at night and opaque at day.
     * We use a Transition to change the opacity of a black rectangle from 0 to 0.5 and back
     * to simulate the day-night cycle.
     *
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle.
     * @return The night as a GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);

        new Transition<>(
                night,
                night.renderer()::setOpaqueness,
                0f, 0.5f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

        return night;
    }
}
