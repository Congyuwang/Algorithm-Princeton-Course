package assignments.eventDrivenSimulation;

/**
 * represent a 2D ball with position, velocity, and radius.
 */
abstract class Ball {
    final Coordinate position;
    final Coordinate velocity;
    final double radius;

    Ball(Coordinate position, Coordinate velocity, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive");
        }
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    /**
     * move the particle in straight line by time dt
     *
     * @param dt the time interval of this straight-line move
     */
    void move(double dt) {
        this.position.addBy(this.velocity.multiply(dt));
    }

    /**
     * draw the Ball
     */
    abstract void draw();

    /**
     * calculate time to hit another ball.
     *
     * @param b the other ball to hit
     * @return the time to hit another ball. Returns POSITIVE_INFINITY if they do not
     *         hit each other.
     */
    abstract double timeToHit(Ball b);

    /**
     * calculate time to hit a vertical wall.
     *
     * @return the time to hit a vertical wall. Returns POSITIVE_INFINITY if it does
     *         not hit a vertical wall
     */
    abstract double timeToHitXWall();

    /**
     * calculate time to hit a horizontal wall.
     *
     * @return the time to hit a horizontal wall. Returns POSITIVE_INFINITY if it
     *         does not hit a horizontal wall
     */
    abstract double timeToHitYWall();

}
