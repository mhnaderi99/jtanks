import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameState {

    private Tank tank;
    private static ArrayList<CombatVehicle> enemies;
    private Map map;
    private Point topLeftPoint;

    public GameState() {
        enemies = new ArrayList<CombatVehicle>();
        map = new Map("res/maps/map1(27,27).txt");

        int width = (int)Math.ceil((double)GameConstants.getScreenWidth() / (double) GameConstants.getCellWidth());
        int height = (int)Math.ceil((double)GameConstants.getScreenHeight() / (double) GameConstants.getCellHeight());
        int x = Math.max(0, map.getStartPoint().x - width/2) * GameConstants.getCellWidth();
        int y = Math.max(0, map.getStartPoint().y - height/2) * GameConstants.getCellHeight();
        topLeftPoint = new Point(x, Math.min(y, GameConstants.getCellHeight()*Map.getHeight() - GameConstants.getScreenHeight()));
        tank = new Tank();
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

    public static ArrayList<CombatVehicle> getEnemies() {
        return enemies;
    }

    public void update() {
        Iterator<CombatVehicle> iterator = enemies.iterator();
        if (! tank.isAlive()) {
            GameLoop.setGameOver(true);
        }
        tank.update();
        while (iterator.hasNext()) {
            CombatVehicle vehicle = iterator.next();
            if (! vehicle.isAlive()) {
                iterator.remove();
                GameLoop.getCanvas().render(this, true);
            }
            else {
                vehicle.update();
            }
        }
        map.update();
    }
}
