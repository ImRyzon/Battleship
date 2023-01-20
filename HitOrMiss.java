/**
 * @author Mark Wang
 * 2023-1-17
 *
 * This class will use a custom object that is structured to give information on a guessed
 * coordinate. It will inform if it is a hit or miss, what ship ID is hit (if any ship is hit), and
 * how many hits in total this ship has been hit including the current hit (if any ship is hit
 */

public class HitOrMiss {

    /**
     * hit --> whether any ship is hit or not
     * destroyedShip --> whether the hit ship is destroyed or not
     */
    private boolean hit;
    private boolean destroyedShip;

    /**
     * this constructor will instantiate a new HitOrMiss object with the necessary information
     * given
     * @param hit
     * @param destroyedShip
     */
    public HitOrMiss(boolean hit, boolean destroyedShip) {
        this.hit = hit;
        this.destroyedShip = destroyedShip;
    }

    /**
     * Getter for hit attribute
     * @return
     */
    public boolean getHit() {
        return this.hit;
    }

    /**
     * Getter for destroyedShip attribute
     * @return
     */
    public boolean getDestroyedShip() {
        return this.destroyedShip;
    }
}
