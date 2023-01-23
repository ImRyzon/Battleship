/**
 * @author Mark Wang
 * 2023-1-18
 *
 * This class will act as the launch page for the Battleship game, and will
 * redirect the user to the login page
 */

public class Launch {

    /**
     * This main method will launch the Battleship game. However, before
     * actually playing the game, the user must first go through the login page and
     * enter valid credentials in order to proceed into the actual game, hence why it calls
     * an object of the LoginPage class
     * @param args
     */
    public static void main(String[] args) {
        Battleship login = new Battleship();
    }
}
