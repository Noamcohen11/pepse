package pepse.ui;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.Color;
import java.util.function.Supplier;

/**
 * A UI element that displays the energy of the avatar.
 * The energy is displayed as a number in the top left corner of the window.
 *
 * @author Noam Cohen and Gilad Omesi
 */
public class EnergyUi extends GameObject {
    private final Supplier<Integer> energySupplier;

    /**
     * Create the energy UI.
     *
     * @param topLeftCorner The top left corner of the UI.
     * @param dimensions The dimensions of the UI.
     * @param energySupplier The energy supplier.
     *                       It supplies the energy of the avatar.
     */
    public EnergyUi(Vector2 topLeftCorner, Vector2 dimensions, Supplier<Integer> energySupplier) {
        super(topLeftCorner, dimensions,
                new TextRenderable(Integer.toString(energySupplier.get())));

        this.energySupplier = energySupplier;
    }

    /**
     * Update the energy UI.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        TextRenderable energyUi = new TextRenderable(Integer.toString(energySupplier.get()));
        energyUi.setColor(Color.BLACK);
        renderer().setRenderable(energyUi);
    }
}

