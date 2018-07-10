import java.awt.*;

public class GameState {

    private Tank tank;
    private Map map;
    private Point topLeftPoint;

    public GameState() {
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

    public void update() {
        tank.update();
        map.update();
    }
}
