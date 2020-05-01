import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private static final int TRHESH_HOLD = 4;

    private int numberOfSegments;

    private Node firstLine = null;

    private class Node {
        LineSegment lineSegment;
        Node next;
    }

    /**
     * The algorithm searches for segments that contain more than four points.
     *
     * @param pts the array of points
     */
    public FastCollinearPoints(Point[] pts) {
        if (pts == null) {
            throw new IllegalArgumentException("null input");
        }
        Point[] points = pts.clone();
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("null point");
            }
        }
        Point[] pointsCopy = pts.clone();
        Arrays.sort(pointsCopy);
        for (int i = 1; i < points.length; i++) {
            if (pointsCopy[i - 1].compareTo(pointsCopy[i]) == 0) {
                throw new IllegalArgumentException("repeated points " + pointsCopy[i]);
            }
        }
        for (Point p : pointsCopy) {
            if (points.length < 4) {
                break;
            }
            Arrays.sort(points);
            Arrays.sort(points, p.slopeOrder());
            int count = 0;
            int first = 1;
            double slopeMem = p.slopeTo(points[first]);
            for (int last = 2; last < points.length; last++) {
                double thisSlope = p.slopeTo(points[last]);
                if (slopeMem == thisSlope) {
                    count++;
                } else {
                    if (count >= TRHESH_HOLD - 2 && p.compareTo(points[first]) <= 0) {
                        if (numberOfSegments == 0) {
                            firstLine = new Node();
                            firstLine.lineSegment = new LineSegment(p, points[last - 1]);
                        } else {
                            Node oldFirst = firstLine;
                            firstLine = new Node();
                            firstLine.lineSegment = new LineSegment(p, points[last - 1]);
                            firstLine.next = oldFirst;
                        }
                        numberOfSegments++;
                    }
                    first = last;
                    count = 0;
                }
                slopeMem = thisSlope;
            }
            if (count >= TRHESH_HOLD - 2 && p.compareTo(points[first]) <= 0) {
                Node oldFirst = firstLine;
                firstLine = new Node();
                firstLine.lineSegment = new LineSegment(p, points[points.length - 1]);
                firstLine.next = oldFirst;
                numberOfSegments++;
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        Node current = firstLine;
        LineSegment[] lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            lineSegments[i] = current.lineSegment;
            current = current.next;
        }
        return lineSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.02);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenRadius(0.005);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
