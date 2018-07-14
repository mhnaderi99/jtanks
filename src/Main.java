import javax.swing.*;
import java.awt.*;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 * game starts here
 */

public class Main {

    public static void main(String[] args) {
        MenuFrame menu = new MenuFrame("JTanks");
        menu.start();
        menu.initBufferstrategy();
        menu.draw();
    }
}
