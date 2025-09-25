package assignment1;

import java.util.*;

public class clsePair {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
        public static double distance(Point a, Point b) {
            double dx = a.x - b.x, dy = a.y - b.y;
            return Math.hypot(dx, dy);
        }
    }

    public static double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] px = pts.clone(); Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Point[] py = px.clone();  Arrays.sort(py, Comparator.comparingDouble(p -> p.y));
        return closestRec(px, py, 0, px.length - 1);
    }

    private static double closestRec(Point[] px, Point[] py, int l, int r) {
        int n = r - l + 1;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = l; i <= r; i++)
                for (int j = i + 1; j <= r; j++)
                    best = Math.min(best, Point.distance(px[i], px[j]));
            Arrays.sort(px, l, r + 1, Comparator.comparingDouble(p -> p.x));
            return best;
        }
        int m = l + (r - l) / 2;
        double midx = px[m].x;

        List<Point> pyl = new ArrayList<>(), pyr = new ArrayList<>();
        for (Point p : py) { if (p.x <= midx) pyl.add(p); else pyr.add(p); }

        double dl = closestRec(px, pyl.toArray(new Point[0]), l, m);
        double dr = closestRec(px, pyr.toArray(new Point[0]), m + 1, r);
        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : py) if (Math.abs(p.x - midx) < d) strip.add(p);

        for (int i = 0; i < strip.size(); i++)
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < d; j++)
                d = Math.min(d, Point.distance(strip.get(i), strip.get(j)));
        return d;
    }
}