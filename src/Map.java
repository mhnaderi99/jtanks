import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    private MapCell[][] map;
    private int width;
    private int height;

    public Map(String filePath) {
        readDimensions(filePath);
        map = new MapCell[width][height];
        loadMap(filePath);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MapCell[][] getMap() {
        return map;
    }

    public boolean loadMap(String filePath) {
        File file = new File(filePath);
        try {
            FileInputStream stream = new FileInputStream(file);
            char current;
            int x = 0, y = 0;
            while (stream.available() > 0) {
                current = (char) stream.read();
                int code = Character.getNumericValue(current);
                if (code != -1) {
                    placeCell(GameConstants.getCellByCode(code), x, y/2);
                    //System.out.print(current + " ");
                    x++;
                }
                else {
                    //System.out.println();
                    y++;
                    x=0;
                }
            }
            stream.close();
            return true;
        }
        catch (FileNotFoundException e) {return false;}
        catch (IOException e) {return false;}
    }

    public boolean placeCell(MapCell cell, int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            map[x][y] = cell;
            return true;
        }
        return false;
    }

    public void readDimensions(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1, filePath.length());
        String dims = fileName.substring(fileName.lastIndexOf("(") + 1, fileName.lastIndexOf(")"));
        int commaIndex = dims.lastIndexOf(',');
        width = Integer.parseInt(dims.substring(0, commaIndex));
        height = Integer.parseInt(dims.substring(commaIndex + 1, dims.length()));
    }

    public void update() {

    }

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

    private void goRight() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().x + GameConstants.getStep() <= GameConstants.getCellWidth() * GameLoop.getState().getMap().width - GameConstants.getScreenWidth()) {
                GameLoop.getState().getTopLeftPoint().x += GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }

    private void goLeft() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().x >= GameConstants.getStep()) {
                GameLoop.getState().getTopLeftPoint().x -= GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }

    private void goUp() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().y >= GameConstants.getStep()) {
                GameLoop.getState().getTopLeftPoint().y -= GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }

    private void goDown() {
        for (int i = 0; i < GameConstants.getAmount() / GameConstants.getStep(); i++) {
            if (GameLoop.getState().getTopLeftPoint().y + GameConstants.getStep() <= GameLoop.getState().getMap().height * GameConstants.getCellHeight() - GameConstants.getScreenHeight()) {
                GameLoop.getState().getTopLeftPoint().y += GameConstants.getStep();
                GameLoop.getCanvas().render(GameLoop.getState(), true);
            }
        }
    }


}
