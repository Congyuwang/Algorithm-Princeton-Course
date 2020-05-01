
public class BruteCollinearPoints {

    private int numberOfSegments;

    private Node firstLine = null;

    private class Node {
        LineSegment lineSegment;
        Node next;
    }

    /**
     * Does not apply to cases where there are five or more collinear points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("null input");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("null point");
            }
        }
        int numberOfPoints = points.length;
        for (int i1 = 0; i1 < numberOfPoints; i1++) {
            for (int i2 = i1 + 1; i2 < numberOfPoints; i2++) {
                if (points[i1].compareTo(points[i2]) == 0) {
                    throw new IllegalArgumentException("repeated points " + points[i1]);
                }
            }
        }
        for (int i1 = 0; i1 < numberOfPoints; i1++) {
            for (int i2 = i1 + 1; i2 < numberOfPoints; i2++) {
                for (int i3 = i2 + 1; i3 < numberOfPoints; i3++) {
                    for (int i4 = i3 + 1; i4 < numberOfPoints; i4++) {
                        double slope1 = points[i1].slopeTo(points[i2]);
                        double slope2 = points[i1].slopeTo(points[i3]);
                        double slope3 = points[i1].slopeTo(points[i4]);
                        if (slope1 == slope2 && slope1 == slope3) {
                            Point min = points[i1];
                            Point max = points[i1];
                            int temp = min.compareTo(points[i2]);
                            if (temp > 0) {
                                min = points[i2];
                            }
                            temp = max.compareTo(points[i2]);
                            if (temp < 0) {
                                max = points[i2];
                            }
                            temp = min.compareTo(points[i3]);
                            if (temp > 0) {
                                min = points[i3];
                            }
                            temp = max.compareTo(points[i3]);
                            if (temp < 0) {
                                max = points[i3];
                            }
                            temp = min.compareTo(points[i4]);
                            if (temp > 0) {
                                min = points[i4];
                            }
                            temp = max.compareTo(points[i4]);
                            if (temp < 0) {
                                max = points[i4];
                            }
                            if (numberOfPoints == 0) {
                                firstLine = new Node();
                                firstLine.lineSegment = new LineSegment(min, max);
                            } else {
                                Node oldFirst = firstLine;
                                firstLine = new Node();
                                firstLine.lineSegment = new LineSegment(min, max);
                                firstLine.next = oldFirst;
                            }
                            numberOfSegments++;
                        }
                    }
                }
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
        // testnull
        Point[] p = new Point[5];
        p[0] = new Point(0, 0);
        p[1] = new Point(1, 0);
        p[2] = new Point(1, 2);
        p[3] = new Point(2, 2);
        p[4] = new Point(2, 2);
        BruteCollinearPoints b = new BruteCollinearPoints(p);
        System.out.println(b.numberOfSegments);
    }
}
