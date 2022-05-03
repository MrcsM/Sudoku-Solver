import java.util.Arrays;
import java.util.Random;

public class Grid {
    private int width;
    private int height;

    private int strikes;
    private int maxStrikes;

    private int[][] grid;
    private int[][] solved;

    private final Random random = new Random();

    public Grid(int width, int height, int maxStrikes) {
        this.width = width;
        this.height = height;

        this.strikes = 0;
        this.maxStrikes = maxStrikes;
    }

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        this.strikes = 0;
        this.maxStrikes = 3;
    }

    public void setup() {

        grid = new int[width][height];
        solved = new int[width][height];
        int cluesGiven = 17;
        int betweenClues = random.nextInt(4) + 2;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cluesGiven != 0) {
                    betweenClues--;
                    if (betweenClues == 0) {
                        int num = random.nextInt(9) + 1;
                        while (!valid(x, y, num)) {
                            num = random.nextInt(9) + 1;
                        }
                        grid[x][y] = num;
                        cluesGiven--;
                        betweenClues = random.nextInt(4) + 2;
                    } else {
                        grid[x][y] = 0;
                    }
                } else {
                    grid[x][y] = 0;
                }
            }
        }
        // copying the grid to a new 2d array, so I can get the solved version without changing the original
        int[][] yetToBeSolved = Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
        solved = new SolvedGrid(yetToBeSolved, width, height).getGrid();
    }

    public int get(int x, int y) {
        return grid[x][y];
    }

    public void set(int x, int y, int num) {
        grid[x][y] = num;
    }

    public boolean isSolved() {

        // for the check if there's just no 0's
        int[] find = findEmpty();
        if (find == null) {
            return true;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] != solved[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] findEmpty() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == 0) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public boolean solve() {
        int[] find = findEmpty();
        int row, col;
        if (find == null) {
            return true;
        } else {
            row = find[0];
            col = find[1];
        }
        for (int i = 1; i <= 9; i++) {
            if (valid(row, col, i)) {
                grid[row][col] = i;

                if (solve()) {
                    return true;
                }

                grid[row][col] = 0;
            }
        }
        return false;
    }

    public boolean valid(int row, int col, int value) {
        for (int i = 0; i < width; i++) {
            if (grid[i][col] == value) {
                return false;
            }
        }
        for (int i = 0; i < height; i++) {
            if (grid[row][i] == value) {
                return false;
            }
        }

        int box_x = Math.floorDiv(row, 3);
        int box_y = Math.floorDiv(col, 3);
        for (int y = box_y*3; y < box_y*3 + 3; y++) {
            for (int x = box_x*3; x < box_x*3 + 3; x++) {
                if (grid[x][y] == value && x != row && y != col) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append("ROW\n"); // to symbolize rows
        String[] letters = new String[]{"C", "O", "L"}; // to symbolize columns
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x % 3 == 0 && x != 0) {
                    builder.append("  ");
                    if (y == 0) {
                        builder.insert(3, "  ROW");
                    }
                }
                builder.append(grid[x][y]);
                if (x == width - 1) {
                    builder.append(" ").append(letters[i]);
                    if (i++ >= letters.length - 1) {
                        i = 0;
                    }
                }
            }
            if (y % 3 == 2) {
                builder.append("\n");
            }
            builder.append("\n");
        }
        System.out.println(builder.toString().trim());
        System.out.println("\n");
    }

    public void printSolved() {
        StringBuilder builder = new StringBuilder();
        builder.append("ROW\n"); // to symbolize rows
        String[] letters = new String[]{"C", "O", "L"}; // to symbolize columns
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x % 3 == 0 && x != 0) {
                    builder.append("  ");
                    if (y == 0) {
                        builder.insert(3, "  ROW");
                    }
                }
                builder.append(solved[x][y]);
                if (x == width - 1) {
                    builder.append(" ").append(letters[i]);
                    if (i++ >= letters.length - 1) {
                        i = 0;
                    }
                }
            }
            if (y % 3 == 2) {
                builder.append("\n");
            }
            builder.append("\n");
        }
        System.out.println(builder.toString().trim());
        System.out.println("\n");
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public void incrementStrikes() {
        this.strikes++;
    }

    public int getStrikes() {
        return strikes;
    }

    public int getMaxStrikes() {
        return maxStrikes;
    }
}
