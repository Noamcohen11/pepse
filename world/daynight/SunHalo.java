package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.Color;

public class SunHalo {
    private static final String SUN_HALO_TAG = "sunHalo";
    private static final float SIZE = 100;
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);
    public static GameObject create(GameObject sun){
        GameObject halo = new GameObject(
                new Vector2(sun.getCenter()),
                new Vector2(SIZE,SIZE),
                new OvalRenderable(HALO_COLOR));
        halo.setCoordinateSpace(sun.getCoordinateSpace());
        halo.setTag(SUN_HALO_TAG);
        return halo;
    }
}
