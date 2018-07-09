import java.awt.*;

public class GameState {

    private Tank tank;
    private Map map;
    private Point topLeftPoint;

    public GameState() {
        tank = new Tank();
        map = new Map("res/maps/map1(27,27).txt");
        System.out.println(map.getWidth() + " " + map.getHeight());
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
