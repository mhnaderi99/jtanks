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

    public static ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public Point getTopLeftPoint() {
        return topLeftPoint;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank2(Tank tank2) {
        this.tank2 = tank2;
    }

    public Tank getTank2() {
        return tank2;
    }

    public static ArrayList<CombatVehicle> getEnemies() {
        return enemies;
    }

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
                    GameLoop.getCanvas().render(this, true);

                } else {
                    vehicle.update();
                }
            }
            catch (ConcurrentModificationException e ) { break;}
        }
        map.update();
    }
}
