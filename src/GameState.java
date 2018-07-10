import java.awt.*;

public class GameState {

    private Tank tank;
    private Map map;
    private Point topLeftPoint;

    public GameState() {
        map = new Map("res/maps/map1(27,27).txt");
        tank = new Tank();
        topLeftPoint = new Point(0,0);
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

    public void update() {
        tank.update();
        map.update();
    }
}
