package assignments.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> set;

    public PointSET() {
        this.set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    public int size() {
        return this.set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        this.set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : this.set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new ArrayDeque<>();
        for (Point2D p : this.set) {
            if (rect.contains(p)) queue.add(p);
        }
        return queue;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D temp = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D p0 : this.set) {
            double thisDistance = p0.distanceSquaredTo(p);
            if (thisDistance < distance) {
                temp = p0;
                distance = thisDistance;
            }
        }
        return temp;
    }
}
