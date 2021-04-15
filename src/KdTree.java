import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;
public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node left;
        private Node right;
        private final boolean isVertical;

        public Node(Point2D p, RectHV rect, boolean isVertical) {
            this.p = p;
            this.rect = rect;
            this.isVertical = isVertical;
        }
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("NullPointerException");
        root = addRecursive(root, null, p, 0);
    }

    private Node addRecursive(Node current, Node parent, Point2D p, int direction) {
        if (current == null) {
            if (size++ == 0) return new Node(p, new RectHV(0, 0, 1, 1), true);
            RectHV rectOfX = parent.rect;
            if (direction < 0) {
                if (parent.isVertical)
                    rectOfX = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                            parent.p.x(), parent.rect.ymax());
                else  // bottom sub-rectangle
                    rectOfX = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                            parent.rect.xmax(), parent.p.y());
            } else if (direction > 0) {
                if (parent.isVertical)
                    rectOfX = new RectHV(parent.p.x(), parent.rect.ymin(),
                            parent.rect.xmax(), parent.rect.ymax());
                else
                    rectOfX = new RectHV(parent.rect.xmin(), parent.p.y(),
                            parent.rect.xmax(), parent.rect.ymax());
            }
            return new Node(p, rectOfX, !parent.isVertical);
        }
        int cmp = compare(p, current.p, current.isVertical);
        if (cmp < 0) current.left = addRecursive(current.left, current, p, cmp);
        else if (cmp > 0) current.right = addRecursive(current.right, current, p, cmp);
        return current;
    }

    private int compare(Point2D p, Point2D q, boolean isVertical) {
        if (p == null || q == null) throw new NullPointerException("NullPointerException");
        if (p.equals(q)) return 0;
        if (isVertical) return p.x() < q.x() ? -1 : 1;
        else return p.y() < q.y() ? -1 : 1;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("NullPointException");
        return containsRecursive(root, p);
    }

    private boolean containsRecursive(Node x, Point2D p) {
        if (x == null) return false;
        int cmp = compare(p, x.p, x.isVertical);
        if      (cmp < 0) return containsRecursive(x.left, p);
        else if (cmp > 0) return containsRecursive(x.right, p);
        else              return true;
    }

    public void draw() {
        drawRecursive(root);
    }

    private void drawRecursive(Node current) {
        if (current == null) return;
        drawRecursive(current.left);
        drawRecursive(current.right);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        current.p.draw();
        StdDraw.setPenRadius();
        if (current.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(current.p.x(), current.rect.ymin() , current.p.x(), current.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(current.rect.xmin(), current.p.y() , current.rect.xmax(), current.p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<>();
        inRangeRecursive(rect, root, list);
        return list;
    }

    private void inRangeRecursive(RectHV rect, Node current, List<Point2D> list) {
        if (current == null) return;
        if (rect.contains(current.p)) {
            list.add(current.p);
        }
        inRangeRecursive(rect, current.left, list);
        inRangeRecursive(rect, current.right, list);
    }

    public Point2D nearest(Point2D p) {
        Point2D nearest = new Point2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
        return nearestRecursive(nearest, root, p);
    }

    private Point2D nearestRecursive(Point2D nearest, Node current, Point2D p) {
        if (current == null) return nearest;
        if (current.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            nearest = current.p;
        }
        nearest = nearestRecursive(nearest, current.left, p);
        nearest = nearestRecursive(nearest, current.right, p);
        return nearest;
    }


    public static void main(String[] args) {
        // insert method
//        int n = 6;
//        Point2D[] points = new Point2D[n];
//        points[0] = new Point2D(0.7, 0.2);
//        kdtree.insert(points[0]);
//        points[1] = new Point2D(0.5, 0.4);
//        kdtree.insert(points[1]);
//        points[2] = new Point2D(0.2, 0.3);
//        kdtree.insert(points[2]);
//        points[3] = new Point2D(0.4, 0.7);
//        kdtree.insert(points[3]);
//        points[4] = new Point2D(0.9, 0.6);
//        kdtree.insert(points[4]);
//        points[5] = new Point2D(0.7, 0.2);
//        kdtree.insert(points[5]);
//
//        System.out.println("---- size of kdTree = " + kdtree.size());
//        System.out.println(kdtree.size());
//
//        // contains()
//        System.out.println("is point #3 is in the tree: " + kdtree.contains(points[3]));
//        System.out.println("is point (0.1, 0,2)) is in the tree: " + kdtree.contains(new Point2D(0.1, 0.2)));
//
//        // draw()
//        kdtree.draw();
    }
}
