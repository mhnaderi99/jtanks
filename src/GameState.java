import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */
public class GameState {

    private Tank tank;
    private Tank tank2;
    private static ArrayList<CombatVehicle> enemies;
    private static ArrayList<Prize> prizes;
    private Map map;
    private Point topLeftPoint;

    public GameState(String mapName, int diff) {
        enemies = new ArrayList<CombatVehicle>();
        prizes = new ArrayList<Prize>();
        map = new Map("res/maps/" + mapName + diff + ".txt");

        int width = (int)Math.ceil((double)GameConstants.getScreenWidth() / (double) GameConstants.getCellWidth());
        int height = (int)Math.ceil((double)GameConstants.getScreenHeight() / (double) GameConstants.getCellHeight());
        int x = Math.max(0, map.getStartPoint().x - width/2) * GameConstants.getCellWidth();
        int y = Math.max(0, map.getStartPoint().y - height/2) * GameConstants.getCellHeight();
        topLeftPoint = new Point(x , Math.min(y + GameConstants.getScreenHeight() - GameLoop.getCanvas().getContentPane().getSize().height,
                GameConstants.getCellHeight()*Map.getHeight() - GameConstants.getScreenHeight()));
        tank = new Tank(true);
        tank2 = new Tank(false);
    }

    /**
     * to get the prizes
     * @return the prizes
     */
    public static ArrayList<Prize> getPrizes() {
        return prizes;
    }

    /**
     * to set the map
     * @param map the map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * to get the map
     * @return  the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * to get the top left point
     * @return the top left point
     */
    public Point getTopLeftPoint() {
        return topLeftPoint;
    }

    /**
     * to get tank 1
     * @return tank 1
     */
    public Tank getTank() {
        return tank;
    }

    /**
     * to get tank 2
     * @return tank 2
     */
    public Tank getTank2() {
        return tank2;
    }

    /**
     * to get enemies
     * @return enemies
     */
    public static ArrayList<CombatVehicle> getEnemies() {
        return enemies;
    }

    /**
     * updates the game
     */
    public void update() {
        Iterator<CombatVehicle> iterator = enemies.iterator();
        if (! tank.isAlive()) {
            GameLoop.setGameOver(true);
        }
        tank.update();
        if (GameLoop.isMultiplayer()) {
            tank2.update();
        }
        while (iterator.hasNext()) {
            try {
                CombatVehicle vehicle = iterator.next();
                if (!vehicle.isAlive()) {
                    Random random = GameConstants.getRandom();
                    if (random.nextInt(100) % GameConstants.getPrizeChance() == 0) {
                        Prize prize = GameConstants.randomPrize();
                        map.placePrize(prize, vehicle.getXPosition() / GameConstants.getCellWidth(), vehicle.getYPosition() / GameConstants.getCellHeight());
                    }
                    iterator.remove();
                    GameLoop.getCanvas().render(this, true, 0);

                } else {
                    vehicle.update();
                }
            }
            catch (ConcurrentModificationException e ) { break;}
        }
        map.update();
    }
}
