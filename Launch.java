/**
 * This class will act as the launch page
 */

public class Launch {

    /**
     * This main method will launch the Break A Plate game. However, before
     * actually playing the game, the user must first go through the login page and
     * enter valid credentials in order to proceed into the actual game, hence why it calls
     * an object of the LoginPage class
     * @param args
     */
    public static void main(String[] args) {
        try {
                LoginPage window = new LoginPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
