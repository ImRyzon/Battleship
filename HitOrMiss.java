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
    public boolean hit;
    public int shipID;
    public int countHits;

    /**
     * this constructor will instantiate a new HitOrMiss object with the necessary information
     * given
     * @param hit
     * @param shipID
     * @param countHits
     */
    public HitOrMiss(boolean hit, int shipID, int countHits) {
        this.hit = hit;
        this.shipID = shipID;
        this.countHits = countHits;
    }
}
