public class SolvedGrid {

    private final int[][] grid;

    private final int width;
    private final int height;

    public SolvedGrid(int[][] grid, int width, int height) {
        this.grid = grid;
        this.width = width;
        this.height = height;
        solve();
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public int[] findEmpty() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (this.grid[x][y] == 0) {
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
                this.grid[row][col] = i;

                if (solve()) {
                    return true;
                }

                this.grid[row][col] = 0;
            }
        }
        return false;
    }

    public boolean valid(int row, int col, int value) {
        for (int i = 0; i < width; i++) {
            if (this.grid[i][col] == value) {
                return false;
            }
        }
        for (int i = 0; i < height; i++) {
            if (this.grid[row][i] == value) {
                return false;
            }
        }

        int box_x = Math.floorDiv(row, 3);
        int box_y = Math.floorDiv(col, 3);
        for (int y = box_y*3; y < box_y*3 + 3; y++) {
            for (int x = box_x*3; x < box_x*3 + 3; x++) {
                if (this.grid[x][y] == value && x != row && y != col) {
                    return false;
                }
            }
        }
        return true;
    }
}
