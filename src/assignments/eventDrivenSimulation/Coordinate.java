package assignments.eventDrivenSimulation;

/**
 * The Coordinate class represents a 2D vector on a cartesian plane. It supports
 * basic operations of vector space (scaler multiplication and summation). It
 * also has an api for calculating distance.
 */
public class Coordinate {
    public double x;
    public double y;

    public static Coordinate UNIT = new Coordinate(1, 1);
    public static Coordinate ZERO = new Coordinate(0, 0);

    /**
     * Initialize a Coordinate (x, y)
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The opposite coordinate of this coordinate
     */
    public Coordinate opposite() {
        return new Coordinate(-x, -y);
    }

    /**
     * negate this coordinate
     */
    public void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Times the coordinate by a double value m. The coordinate does not change.
     *
     * @param m the scaler to be multiplied
     * @return the coordinate multiplied by m
     */
    public Coordinate multiply(double m) {
        return new Coordinate(m * x, m * y);
    }

    /**
     * scale coordinate by a double value m
     *
     * @param m the scaler to be multiplied
     */
    public void multiplyBy(double m) {
        x *= m;
        y *= m;
    }

    /**
     * calculate the sum of two coordinates. This coordinate does not change.
     *
     * @param c the coordinate to be added
     * @return the sum of the two coordinates
     */
    public Coordinate add(Coordinate c) {
        return new Coordinate(x + c.x, y + c.y);
    }

    /**
     * move this coordinate by c
     *
     * @param c the coordinate to be added
     */
    public void addBy(Coordinate c) {
        x += c.x;
        y += c.y;
    }

    /**
     * calculate the difference between two coordinates. This coordinate does not
     * change.
     *
     * @param c the coordinate to be added
     * @return the sum of the two coordinates
     */
    public Coordinate subtract(Coordinate c) {
        return add(c.opposite());
    }

    /**
     * move this coordinate by -c
     *
     * @param c the coordinate to be added
     */
    public void subtractBy(Coordinate c) {
        addBy(c.opposite());
    }

    /**
     * calculate the inner product of this coordinate with c
     *
     * @param c the other coordinate
     * @return the inner product of this coordinate with c
     */
    public double dot(Coordinate c) {
        return x * c.x + y * c.y;
    }

    /**
     * calculate the norm of this coordinate
     *
     * @return the norm of this coordinate
     */
    public double norm() {
        return Math.sqrt(this.dot(this));
    }

    /**
     * Euclidean distance to another coordinate c
     *
     * @param c the other coordinate
     * @return the Euclidean distance of this point to c
     */
    public double distanceTo(Coordinate c) {
        double dx = x - c.x;
        double dy = y - c.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
