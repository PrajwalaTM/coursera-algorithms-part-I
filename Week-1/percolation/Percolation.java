import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private int top;
    private int bottom;
    private int size;
    private int openSites;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("The size of the grid has to be minimum 1");
        grid = new boolean[n][n];
        top = n * n;
        bottom = n * n + 1;
        size = n;
        uf = new WeightedQuickUnionUF(size * size + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        openNeighbors(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(top, convert2Dto1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("Index is out of bounds");
    }

    private int convert2Dto1D(int row, int col) {
        checkBounds(row, col);
        return (row - 1) * size + (col - 1);
    }

    private void openNeighbors(int row, int col) {
        int index = convert2Dto1D(row, col);
        if (col < size) {
            unionIfIndexIsOpen(row, col + 1, index);
        }
        if (col > 1) {
            unionIfIndexIsOpen(row, col - 1, index);
        }

        if (row > 1) {
            unionIfIndexIsOpen(row - 1, col, index);
        } else {
            uf.union(top, index);
        }
        if (row < size) {
            unionIfIndexIsOpen(row + 1, col, index);
        } else {
            uf.union(bottom, index);
        }
    }

    private void unionIfIndexIsOpen(int row, int col, int index) {
        if (isOpen(row, col))
            uf.union(index, convert2Dto1D(row, col));
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(2, 2);
        p.open(1, 2);
        p.open(2, 3);
        p.open(3, 3);
        boolean isPercolates = p.percolates();
        System.out.println(isPercolates);
        System.out.println(p.numberOfOpenSites());
    }
}
