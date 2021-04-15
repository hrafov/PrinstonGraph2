import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
public class PointSET {
    private final TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("IllegalArgumentException");
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("IllegalArgumentException");
        return set.contains(p);
    }

    public void draw() {
        for (Point2D i: set) {
            i.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("IllegalArgumentException");
        List<Point2D> list = new ArrayList<>();
        for (Point2D i: set) {
            if (rect.contains(i)) list.add(i);
        }
        return list;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("IllegalArgumentException");
        if (set.isEmpty()) return null;
        Point2D nearest = new Point2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Point2D i: set) {
            if (p.distanceSquaredTo(i) < p.distanceSquaredTo(nearest)) {
                nearest = i;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        // Point2D testing
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        PointSET set = new PointSET();
        int n = 10;
        Point2D[] points = new Point2D[n];
        System.out.println("+++++ 10 random points: ++++++++++");
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            System.out.println(" x = " + x + " y = " + y);
            points[i] = new Point2D(x, y);
            set.insert(points[i]);
            set.draw();
        }

        // range method testing
        RectHV rect = new RectHV(5, 10, 40, 60);
        rect.draw();
        Iterable<Point2D> ps;
        ps = set.range(rect);
        System.out.println("=== Points in rectangle ===");
        for (Point2D i: ps) {
            System.out.println(" x = " + i.x() + " y = " + i.y());
        }

        // nearest neighbors
        Point2D middlePoint = new Point2D(50, 50);
        Point2D nearestPoint = set.nearest(middlePoint);
        System.out.println(" nearestPoint x = " + nearestPoint.x() + " nearestPoint y = " + nearestPoint.y());

        StdDraw.show();
        StdDraw.pause(100);

        int x0 = 50; // draw p = (x0, x1) in red
        int y0 = 50;
        Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();
        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 0; i < n; i++) {
            p.drawTo(points[i]);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }
}
