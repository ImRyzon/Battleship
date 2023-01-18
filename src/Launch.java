/*
 * Name: Daniel Guo
 * Date: December 13, 2022
 *
 * Description: Create a multi frame application that will showcase the swing and awt concepts with the BreakAPlate game.
 * The game contains 4 pages: the actual game, profile page, menu, and log in page.
 * When program first runs, the login page will be launched. After the user logs in, the menu page will be launched, from the menu page,
 * the user can choose to go to the profile page or the game page.
 * The game rules will be shown in the terminal when you click the rule button in menu.
 */

public class Launch {

    public static void main(String[] args) throws Exception {

        //Instantiate loginPage object for the LoginPage class
        new LoginPage();
    }
}