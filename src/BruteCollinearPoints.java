
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
        int numberOfPoints = points.length;
        for (int i1 = 0; i1 < numberOfPoints; i1++) {
            for (int i2 = i1 + 1; i2 < numberOfPoints; i2++) {
                for (int i3 = i2 + 1; i3 < numberOfPoints; i3++) {
                    for (int i4 = i3 + 1; i4 < numberOfPoints; i4++) {
                        if (points[i1] == null) {
                            throw new IllegalArgumentException("null point");
                        }
                        double slope1 = points[i1].slopeTo(points[i2]);
                        double slope2 = points[i1].slopeTo(points[i3]);
                        double slope3 = points[i1].slopeTo(points[i4]);
                        if (slope1 == slope2 && slope1 == slope3) {
                            Point min = points[i1];
                            Point max = points[i1];
                            int temp = min.compareTo(points[i2]);
                            if (temp > 0) {
                                min = points[i2];
                            } else if (temp == 0) {
                                throw new IllegalArgumentException("repeated points");
                            }
                            temp = max.compareTo(points[i2]);
                            if (temp < 0) {
                                max = points[i2];
                            } else if (temp == 0) {
                                throw new IllegalArgumentException("repeated points");
                            }
                            temp = min.compareTo(points[i3]);
                            if (temp > 0) {
                                min = points[i3];
                            } else if (temp == 0) {
                                throw new IllegalArgumentException("repeated points");
                            }
                            temp = max.compareTo(points[i3]);
                            if (temp < 0) {
                                max = points[i3];
                            } else if (temp == 0) {
                                throw new IllegalArgumentException("repeated points");
                            }
                            temp = min.compareTo(points[i4]);
                            if (temp > 0) {
                                min = points[i4];
                            } else if (temp == 0) {
                                throw new IllegalArgumentException("repeated points");
                            }
                            temp = max.compareTo(points[i4]);
                            if (temp < 0) {
                                max = points[i4];
                            } else if (temp == 0) {
                                throw new IllegalArgumentException("repeated points");
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
        LineSegment[] lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            lineSegments[i] = firstLine.lineSegment;
            firstLine = firstLine.next;
        }
        return lineSegments;
    }
}
