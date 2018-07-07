public class Map {

    private MapCell[][] map;
    private int width;
    private int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new MapCell[width][height];
    }

    public void placeCell(MapCell cell, int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            map[x][y] = cell;
        }
    }


}
