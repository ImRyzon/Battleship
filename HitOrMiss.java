/**
 * This class will use a custom object that is structured to give information on a guessed
 * coordinate. It will inform if it is a hit or miss, what ship ID is hit (if any ship is hit), and
 * how many hits in total this ship has been hit including the current hit (if any ship is hit
 */

public class HitOrMiss {

    /**
     * hit --> whether any ship is hit or not
     * shipID --> iD of ship if hit
     * countHits --> number of hits on this ship if hit
     */
    private boolean hit;
    private int shipID;
    private int countHits;
    private boolean destroyedShip;

    /**
     * this constructor will instantiate a new HitOrMiss object with the necessary information
     * given
     * @param hit
     * @param shipID
     * @param countHits
     */
    public HitOrMiss(boolean hit, int shipID, int countHits, boolean destroyedShip) {
        this.hit = hit;
        this.shipID = shipID;
        this.countHits = countHits;
        this.destroyedShip = destroyedShip;
    }

    /**
     * setter for hit
     * @param hit
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    /**
     * getter for hit
     * @return
     */
    public boolean getHit() {
        return this.hit;
    }

    /**
     * setter for shipID
     * @param shipID
     */
    public void setID(int shipID) {
        this.shipID = shipID;
    }

    /**
     * getter for shipID
     * @return
     */
    public int getID() {
        return this.shipID;
    }

    /**
     * setter for countHits
     * @param countHits
     */
    public void setCountHit(int countHits) {
        this.countHits = countHits;
    }

    /**
     * getter for countHits
     * @return
     */
    public int getCountHit() {
        return this.countHits;
    }
}
