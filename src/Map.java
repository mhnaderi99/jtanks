import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Mohammadhossein Naderi 9631815
 * @author Mahsa Bazzaz 9631405
 *
 */

public class Map {

    private MapCell[][] map;
    private int[][] enemiesAndArsenals;
    private static Point startPoint;
    private static Point endPoint;
    private static Point topLeftPoint;
    private static int width;
    private static int height;

    public Map(String filePath) {
        readDimensions(filePath);
        map = new MapCell[width][height];
        enemiesAndArsenals = new int[width][height];
        loadMap(filePath);
    }

    /**
     * to get the width of the map
     * @return the width
     */
    public static int getWidth() {
        return width;
    }

    /**
     * to get the height of the map
     * @return the height
     */
    public static int getHeight() {
        return height;
    }

    /**
     * to get the start point of the map
     * @return the start point
     */
    public static Point getStartPoint() {
        return startPoint;
    }

    /**
     * to get the end point of the map
     * @return the end point of the map
     */
    public static Point getEndPoint() {
        return endPoint;
    }

    /**
     * to get the map
     * @return the map
     */
    public MapCell[][] getMap() {
        return map;
    }

    /**
     * to load the map
     * @param filePath the address of the map
     * @return true if loaded successfully false if not
     */
    public boolean loadMap(String filePath) {
        File file = new File(filePath);
        try {
            FileInputStream stream = new FileInputStream(file);
            char current;
            int x = 0, y = 0;
            boolean flag = false;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                if (line.startsWith("s")) {
                    flag = true;
                    String start = line.substring(0, line.lastIndexOf("."));
                    String end = line.substring(line.lastIndexOf(".") + 1, line.length());
                    startPoint = point("s", start);
                    endPoint = point("e", end);
                    y = 0;
                }
                else if (flag) {
                    x = 0;
                    for (int i = 0; i < line.length(); i++) {
                        x = i;
                        current = line.charAt(i);
                        int code = Character.getNumericValue(current);
                        if (code != 0) {
                            placeEnemy(GameConstants.getEnemyByCode(code), x, y);
                        }
                    }
                    y++;
                }

                if(! flag) {
                    for (int i = 0; i < line.length(); i++) {
                        x = i;
                        current = line.charAt(i);
                        int code = Character.getNumericValue(current);
                        if (code != -1) {
                            placeCell(GameConstants.getCellByCode(code), x, y);
                        }
                    }
                    y++;
                }
            }
            stream.close();
            return true;
        }
        catch (FileNotFoundException e) {return false;}
        catch (IOException e) {return false;}
    }

    /**
     * to get the points read from the map file
     * @param ch if it's start point or end point
     * @param line the line read from the file
     * @return the point
     */
    private Point point(String ch, String line) {
        line = line.replaceAll(ch, "");
        line = line.replaceAll("\\(", "");
        line = line.replaceAll("\\)", "");
        int x = Integer.parseInt(line.substring(0, line.lastIndexOf(",")));
        int y = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1, line.length()));
        return new Point(x,y);
    }

    /**
     * to place each cell of the map
     * @param cell the map cell
     * @param x the x position
     * @param y the y position
     * @return true if successfully placed false if not
     */
    public boolean placeCell(MapCell cell, int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            map[x][y] = cell;
            return true;
        }
        return false;
    }

    /**
     * to place enemies
     * @param enemy the enemy
     * @param x the x position
     * @param y the y position
     * @return true if successfully placed false if not
     */
    public boolean placeEnemy(CombatVehicle enemy, int x, int y) {
        if (enemy == null) {
            return false;
        }
        if (x >= 0 && x <width &&  y>= 0 && y < height) {
            if (! map[x][y].isBarrier()) {
                enemy.setXPosition(x*GameConstants.getCellWidth() + (GameConstants.getCellWidth() - enemy.getBody().getWidth()) / 2);
                enemy.setYPosition(y*GameConstants.getCellHeight() + (GameConstants.getCellHeight() - enemy.getBody().getHeight()) / 2);
                GameState.getEnemies().add(enemy);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * to read the dimension of the map from the map file
     * @param filePath the address of the map file
     */
    public void readDimensions(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1, filePath.length());
        String dims = fileName.substring(fileName.lastIndexOf("(") + 1, fileName.lastIndexOf(")"));
        int commaIndex = dims.lastIndexOf(',');
        width = Integer.parseInt(dims.substring(0, commaIndex));
        height = Integer.parseInt(dims.substring(commaIndex + 1, dims.length()));
    }

    public void update() {

    }

    /**
     * //todo
     * @param dx
     * @param dy
     */
    public void changeView(int dx, int dy) {
        if (dx == -1) {
            goLeft();
        }
        if (dx == 1) {
            goRight();
        }
        if (dy == -1) {
            goUp();
        }
        if (dy == 1) {
            goDown();
        }
    }

    /**
     * to go right
     */
    private void goRight() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().x + GameConstants.getStep() <= GameConstants.getCellWidth() * GameLoop.getState().getMap().width - GameConstants.getScreenWidth()) {
                GameLoop.getState().getTopLeftPoint().x += GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }

    /**
     * to go left
     */
    private void goLeft() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().x >= GameConstants.getStep()) {
                GameLoop.getState().getTopLeftPoint().x -= GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }

    /**
     * to go up
     */
    private void goUp() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().y >= GameConstants.getStep()) {
                GameLoop.getState().getTopLeftPoint().y -= GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }

    /**
     * to go down
     */
    private void goDown() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().y + GameConstants.getStep() <= GameLoop.getState().getMap().height * GameConstants.getCellHeight() - GameConstants.getScreenHeight()) {
                GameLoop.getState().getTopLeftPoint().y += GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }


}
