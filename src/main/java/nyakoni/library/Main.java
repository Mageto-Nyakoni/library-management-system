package nyakoni.library;

import nyakoni.library.ServiceLayer.UserService;
import nyakoni.library.ControllerLayer.Menu;

public class Main {
    public static void main(String[] args) {
        // Launch interactive menu so users can log in/register and browse.
        UserService userService = new UserService();
        Menu menu = new Menu(userService);
        menu.start();
    }
}