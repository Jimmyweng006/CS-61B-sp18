package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        world[0][0] = Tileset.PLAYER;
        world[WIDTH-1][HEIGHT-1] = Tileset.WALL;

        // test layout Hexagons
        int size = 4;
        int startX = 0, startY = 15;
        int[] numberOfHexagon = {3, 4, 5, 4, 3};
        boolean flag = false;
        for (int n: numberOfHexagon) {
            if (n == 5) {
                flag = true;
            }
            List<Integer> temp = tessellateHexagon(n, startX, startY, size, world, flag);
            startX = temp.get(0);
            startY = temp.get(1);
        }

        /* replace null for nothing */
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (Objects.isNull(world[x][y])) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
        }

        ter.renderFrame(world);
    }

    /**
     * draw a hexagon wall stacked by n hexagons, in pos {startX, startY}
     * @param n number of hexagon
     * @param startX Hexagon's bottom left x position
     * @param startY Hexagon's bottom left y position
     * @param size Hexagon's size
     * @param world given world
     * @param flag indicate drawing right hand size of tessellation
     */
    public static List<Integer> tessellateHexagon(int n, int startX, int startY, int size, TETile[][] world, boolean flag) {
        List<Integer> ret = new ArrayList<>();
        int curX = startX, curY = startY;
        // bottom to top
        for (int i = 0; i < n; i++) {
            TETile t = randomTile();
            addHexagon(curX, curY, size, t, world);
            curY += 2 * size;
        }

        // left to right
        int offset = (calculateMaxWidth(size) - size) / 2;
        startX += size + offset;
        startY += flag ? size : -size;

        ret.add(startX);
        ret.add(startY);
        return ret;
    }

    /**
     * Add a Hexagon to world.
     * @param x Hexagon's bottom left x position
     * @param y Hexagon's bottom left y position
     * @param size Hexagon's size
     * @param t Hexagon's TETile
     * @param world given world
     */
    public static void addHexagon(int x, int y, int size, TETile t, TETile[][] world) {
        /* ignore Hexagon out of world's bound */

        if (size < 2) {
            throw new IllegalArgumentException("Hexagon's size should >= 2");
        }

        /* draw Hexagon */
        // 1. initialize rectangle
        // 2. max width = size + 2 * (size - 1), height = size * 2
        // 3. draw from bottom to mid, and mid to top
        int rectangleW = calculateMaxWidth(size);
        int rectangleH = 2 * size;
        TETile[][] rectangle = new TETile[rectangleW][rectangleH];
        drawHexagon(size, t, rectangle);

        /* set Hexagon on world(x, y) */
        for (int i = x; i < rectangle.length + x; i++) {
            for (int j = y; j < rectangle[0].length + y; j++) {
                if (Objects.isNull(rectangle[i-x][j-y])) {
                    continue;
                }
                world[i][j] = rectangle[i-x][j-y];
            }
        }
    }

    private static void drawHexagon(int size, TETile t, TETile[][] rectangle) {
        int maxWidth = rectangle.length;

        drawHalfHexagon(size, maxWidth, rectangle, 2, 0, t);
        drawHalfHexagon(maxWidth, maxWidth, rectangle, -2, size, t);
    }

    private static void drawHalfHexagon(int curWidth, int maxWidth, TETile[][] rectangle,
                                        int op, int startRow, TETile t) {
        for (int i = startRow; i < rectangle[0].length / 2 + startRow; i++) {
            int erase = (maxWidth - curWidth) / 2;
            for (int j = 0; j < maxWidth; j++) {
                if (j < erase || j > (maxWidth - 1 - erase)) {
                    continue;
                }

                if (j % 2 == 1) {
                    rectangle[j][i] = t;
                } else {
                    rectangle[j][i] = TETile.colorVariant(t, 1000, 1000, 1000, RANDOM);
                }
            }

            curWidth += op;
        }
    }

    private static int calculateMaxWidth(int size) {
        return size + 2 * (size - 1);
    }

    @Test
    public void testCalculateMaxWidth() {
        int expected = 7;
        assertEquals(expected, calculateMaxWidth(3));
    }

    private static TETile randomTile() {
        int number = RANDOM.nextInt(3);

        if (number == 0) {
            return Tileset.WALL;
        } else if (number == 1) {
            return Tileset.FLOWER;
        } else {
            return Tileset.TREE;
        }
    }
}
