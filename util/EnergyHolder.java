package pepse.util;

/**
 * An object that can hold energy.
 */
public interface EnergyHolder {
    /**
     * Add energy to the holder.
     *
     * @param energy The energy to add.
     */
    public void addEnergy(float energy);

    /**
     * Lose energy from the holder.
     *
     * @param energy The energy to lose.
     */
    public void loseEnergy(float energy);

    /**
     * Get the energy of the holder.
     *
     * @return The energy of the holder.
     */
    public float getEnergy();

    /**
     * Get the maximum energy of the holder.
     *
     * @return The maximum energy of the holder.
     */
    public float getMaxEnergy();
}
