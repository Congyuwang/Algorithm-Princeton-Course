package assignments.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;
import java.util.Queue;

public class KdTree {

    private final Node root = new Node(true);
    private int size = 0;

    private static class Node {
        Point2D point;
        Node left; // upper
        Node right; // lower
        final boolean isVertical;

        Node(boolean isVertical) {
            this.isVertical = isVertical;
        }
    }

    public Point2D nearest(Point2D query) {
        if (query == null) throw new IllegalArgumentException();
        var minDistance = new Double[] {Double.POSITIVE_INFINITY};
        var nearest = new Point2D[] {root.point};
        nearest(query, root, minDistance, nearest, null);
        return nearest[0];
    }

    private void nearest(Point2D query, Node subTree, Double[] minDistance, Point2D[] nearest, Point2D parent) {
        if (subTree.point == null) return;
        double firstDistance = subTree.point.distanceSquaredTo(query);
        if (firstDistance < minDistance[0]) {
            nearest[0] = subTree.point;
            minDistance[0] = firstDistance;
        }

        if (subTree.isVertical) {
            if (isLeftTo(query, subTree.point)) {
                nearest(query, subTree.left, minDistance, nearest, null);
            } else {
                nearest(query, subTree.right, minDistance, nearest, null);
            }
        } else {
            if (isUpperTo(query, subTree.point)) {
                nearest(query, subTree.left, minDistance, nearest, null);
            } else {
                nearest(query, subTree.right, minDistance, nearest, null);
            }
        }

        // skip searching the other half if unnecessary
        if (cornerDistanceSquared(subTree, parent, query) >= minDistance[0]) return;

        if (subTree.isVertical) {
            if (isLeftTo(query, subTree.point)) {
                nearest(query, subTree.right, minDistance, nearest, subTree.point);
            } else {
                nearest(query, subTree.left, minDistance, nearest, subTree.point);
            }
        } else {
            if (isUpperTo(query, subTree.point)) {
                nearest(query, subTree.right, minDistance, nearest, subTree.point);
            } else {
                nearest(query, subTree.left, minDistance, nearest, subTree.point);
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node parent, Point2D p) {
        assert parent != null;
        if (parent.point == null) return false;
        if (parent.point.equals(p)) return true;
        if (parent.isVertical) {
            if (isLeftTo(p, parent.point)) {
                return contains(parent.left, p);
            } else {
                return contains(parent.right, p);
            }
        } else {
            if (isUpperTo(p, parent.point)) {
                return contains(parent.left, p);
            } else {
                return contains(parent.right, p);
            }
        }
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        insert(root, p);
    }

    private void insert(Node parent, Point2D p) {
        assert parent != null;
        if (parent.point == null) {
            parent.point = p;
            parent.left = new Node(!parent.isVertical);
            parent.right = new Node(!parent.isVertical);
            size++;
            return;
        }
        if (parent.point.equals(p)) return;
        if (parent.isVertical) {
            if (isLeftTo(p, parent.point)) {
                insert(parent.left, p);
            } else {
                insert(parent.right, p);
            }
        } else {
            if (isUpperTo(p, parent.point)) {
                insert(parent.left, p);
            } else {
                insert(parent.right, p);
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> container = new ArrayDeque<>();
        range(rect, root, container);
        return container;
    }

    private void range(RectHV rect, Node node, Queue<Point2D> container) {
        if (node.point == null) return;
        if (rect.contains(node.point)) container.add(node.point);
        if (node.isVertical) {
            double x = node.point.x();
            if (rect.xmin() <= x) range(rect, node.left, container);
            if (rect.xmax() >= x) range(rect, node.right, container);
        } else {
            double y = node.point.y();
            if (rect.ymin() <= y) range(rect, node.left, container);
            if (rect.ymax() >= y) range(rect, node.right, container);
        }
    }

    public void draw() {
        for (Point2D p : getPoints()) {
            p.draw();
        }
    }

    private Iterable<Point2D> getPoints() {
        Queue<Point2D> points = new ArrayDeque<>();
        enqueue(points, root);
        return points;
    }

    private static void enqueue(Queue<Point2D> points, Node n) {
        if (n.point == null) return;
        points.add(n.point);
        enqueue(points, n.left);
        enqueue(points, n.right);
    }

    private static boolean isLeftTo(Point2D p1, Point2D p2) {
        return p1.x() <= p2.x();
    }

    private static boolean isUpperTo(Point2D p1, Point2D p2) {
        return p1.y() <= p2.y();
    }

    private static double sqDist(double x0, double y0, double x1, double y1) {
        double vDis = y0 - y1;
        double hDis = x0 - x1;
        return hDis * hDis + vDis * vDis;
    }

    private static double cornerDistanceSquared(Node subTree, Point2D parent, Point2D query) {
        if (subTree.isVertical) {
            if (parent == null) {
                double vDistance = subTree.point.x() - query.x();
                return vDistance * vDistance;
            } else {
                return sqDist(subTree.point.x(), parent.y(), query.x(), query.y());
            }
        } else {
            if (parent == null) {
                double hDistance = subTree.point.y() - query.y();
                return hDistance * hDistance;
            } else {
                return sqDist(parent.x(), subTree.point.y(), query.x(), query.y());
            }
        }
    }
}
