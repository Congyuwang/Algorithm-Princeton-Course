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

    public FastCollinearPoints(Point[] points) {
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (Point p : pointsCopy) {
            Arrays.sort(points);
            Arrays.sort(points, p.slopeOrder());
            int count = 0;
            int head = 0;
            double slopeMem = p.slopeTo(points[0]);
            for (int tail = 1; tail < points.length; tail++) {
                double thisSlope = p.slopeTo(points[tail]);
                if (slopeMem == thisSlope) {
                    count++;
                } else {
                    if (count >= TRHESH_HOLD - 2 && p.compareTo(points[head]) <= 0) {
                        if (numberOfSegments == 0) {
                            firstLine = new Node();
                            firstLine.lineSegment = new LineSegment(points[head], points[tail - 1]);
                        } else {
                            Node oldFirst = firstLine;
                            firstLine = new Node();
                            firstLine.lineSegment = new LineSegment(points[head], points[tail - 1]);
                            firstLine.next = oldFirst;
                        }
                        numberOfSegments++;
                    }
                    head = tail;
                    count = 0;
                }
                slopeMem = thisSlope;
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
            firstLine = current.next;
        }
        return lineSegments;;
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
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
