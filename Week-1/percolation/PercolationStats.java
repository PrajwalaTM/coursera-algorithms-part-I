import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] p;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Given n or trials <=0");
        this.n = n;
        this.trials = trials;
        p = new double[trials];
        int trial;
        for (trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            int randomRow;
            int randomCol;
            int count = 0;
            while (!perc.percolates()) {
                do {
                    randomRow = StdRandom.uniform(n) + 1;
                    randomCol = StdRandom.uniform(n) + 1;
                } while (perc.isOpen(randomRow, randomCol));
                perc.open(randomRow, randomCol);
                count++;
            }
            p[trial] = (double) count / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(p);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(p);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return (mean - (stddev * 1.96) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return (mean + (stddev * 1.96) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("Mean is " + ps.mean());
        System.out.println("Std deviation is " + ps.stddev());
        System.out.println("Interval is " + ps.confidenceLo() + "-" + ps.confidenceHi());
    }
}
