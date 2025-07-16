package serhii.platonov;

import java.util.concurrent.TimeUnit;
import java.util.function.IntBinaryOperator;

public class GameOfLife {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    private static final int GENERATIONS = 10; // Number of generations to simulate
    private static final int DELAY_MS = 100;  // Delay between generations in milliseconds
    private static final String ALIVE = "■ ";
    private static final String DEAD = ". ";
//    private static final String DEAD = "□ ";

    // Pre-calculate neighbor offsets
    // this 3x3 matrix can be used to calculate the number of live neighbors for each cell
    private static final int[][] NEIGHBORS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    // Use IntBinaryOperator instead of BiFunction to avoid boxing/unboxing
    private final IntBinaryOperator neighborCounter;
    private boolean[][] grid;
    private boolean[][] nextGrid;

    // Default constructor (toroidal)
    public GameOfLife() {
        this(BoundaryType.TOROIDAL);
    }

    // Constructor with boundary type selection
    public GameOfLife(BoundaryType boundaryType) {
        grid = new boolean[HEIGHT][WIDTH];
        nextGrid = new boolean[HEIGHT][WIDTH];

        neighborCounter = switch (boundaryType) {
            case TOROIDAL -> this::countLiveNeighborsToroidal;
            case FINITE -> this::countLiveNeighborsFinite;
        };
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        game.run();
    }

    public void run() {
        initializeGrid();
        // Print initial state as Generation 0
        System.out.println("Generation: 0 (Initial State)");
        printGrid();

        for (int i = 1; i <= GENERATIONS; i++) {
            System.out.println("Generation: " + i);
            computeNextGeneration();
            printGrid();
            try {
                TimeUnit.MILLISECONDS.sleep(DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeGrid() {
        // Place a "Glider" pattern in the middle of the grid
        int midX = WIDTH / 2;
        int midY = HEIGHT / 2;

        // . X .
        // . . X
        // X X X
        grid[midY - 1][midX] = true;
        grid[midY][midX + 1] = true;
        grid[midY + 1][midX - 1] = true;
        grid[midY + 1][midX] = true;
        grid[midY + 1][midX + 1] = true;
    }

    public void printGrid() {
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                System.out.print(cell ? ALIVE : DEAD);
            }
            System.out.println();
        }
    }

    public void computeNextGeneration() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int liveNeighbors = neighborCounter.applyAsInt(x, y);

                // Apply the rules of the Game of Life
                if (grid[y][x]) {
                    // Rule 1 & 3: Any live cell with fewer than two or more than three live neighbours dies.
                    // Rule 2: Any live cell with two or three live neighbours lives on.
                    nextGrid[y][x] = (liveNeighbors == 2 || liveNeighbors == 3);
                } else {
                    // Rule 4: Any dead cell with exactly three live neighbours becomes a live cell.
                    nextGrid[y][x] = (liveNeighbors == 3);
                }
            }
        }

        // Swap grids for the next iteration
        boolean[][] temp = grid;
        grid = nextGrid;
        nextGrid = temp;
    }

    private int countLiveNeighborsToroidal(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int neighborX = (x + j + WIDTH) % WIDTH;
                int neighborY = (y + i + HEIGHT) % HEIGHT;
                if (grid[neighborY][neighborX]) count++;
            }
        }
        return count;
    }

    private int countLiveNeighborsFinite(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int neighborX = x + j;
                int neighborY = y + i;
                if (neighborY >= 0 && neighborY < HEIGHT &&
                        neighborX >= 0 && neighborX < WIDTH &&
                        grid[neighborY][neighborX]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Enum for boundary type
    public enum BoundaryType {
        TOROIDAL,  // Wrapping boundaries (default)
        FINITE     // Fixed boundaries
    }
}