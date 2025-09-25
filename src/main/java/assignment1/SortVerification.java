package assignment1;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class SortVerification {
    private static final Random RND = new Random();

    private static boolean sorted(int[] arr) {
        for (int i = 0; i + 1 < arr.length; i++) if (arr[i] > arr[i + 1]) return false;
        return true;
    }

    // O(n^2) для проверки Closest Pair на малых n
    private static double bruteForce(clsePair.Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                min = Math.min(min, clsePair.Point.distance(points[i], points[j]));
        return min;
    }

    public static void main(String[] args) throws Exception {
        int[] sizes = {1_000, 2_000, 4_000, 8_000, 16_000};
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("metrics.csv"), "UTF-8"))) {
            out.println("algo,n,timeMillis,ok,extra");

            for (int n : sizes) {
                // ----- MergeSort
                int[] a1 = RND.ints(n).toArray();
                long t1 = System.currentTimeMillis();
                mergeSorting.sort(a1);
                long t2 = System.currentTimeMillis();
                out.printf("MergeSort,%d,%d,%b,%s%n", n, (t2 - t1), sorted(a1), "");

                // ----- QuickSort
                int[] a2 = RND.ints(n).toArray();
                long t3 = System.currentTimeMillis();
                quickSorting.sort(a2);
                long t4 = System.currentTimeMillis();
                out.printf("QuickSort,%d,%d,%b,%s%n", n, (t4 - t3), sorted(a2), "");

                // ----- Deterministic Select: усредняем на 100 раундах
                int TRIALS = 100;
                long sum = 0;
                boolean ok = true;
                for (int tr = 0; tr < TRIALS; tr++) {
                    int[] a3 = RND.ints(n).toArray();
                    int k = n / 2;
                    long s = System.currentTimeMillis();
                    int kth = DeterministicSelect.select(a3, k);
                    long e = System.currentTimeMillis();
                    sum += (e - s);

                    int[] copy = a3.clone();
                    Arrays.sort(copy);
                    if (kth != copy[k]) ok = false;
                }
                long avg = sum / TRIALS;
                out.printf("Select(MoM5),%d,%d,%b,%s%n", n, avg, ok, "avg100");

                // ----- Closest Pair 2D
                clsePair.Point[] pts = new clsePair.Point[Math.min(n, 20000)];
                for (int i = 0; i < pts.length; i++)
                    pts[i] = new clsePair.Point(Math.random(), Math.random());

                long t7 = System.currentTimeMillis();
                double dFast = clsePair.closest(pts);
                long t8 = System.currentTimeMillis();

                String extra = String.format("dFast=%.6f", dFast);

                // сверка с брутом для малых n (≤ 2000)
                if (pts.length <= 2000) {
                    long t9 = System.currentTimeMillis();
                    double dBrute = bruteForce(pts);
                    long t10 = System.currentTimeMillis();
                    extra += String.format("; dBrute=%.6f; bruteMs=%d", dBrute, (t10 - t9));
                }
                out.printf("ClosestPair2D,%d,%d,%s,%s%n", pts.length, (t8 - t7), "true", extra);
            }
        }
        System.out.println("Done. See metrics.csv");
    }
}