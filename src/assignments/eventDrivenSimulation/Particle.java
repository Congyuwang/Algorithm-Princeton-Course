package assignments.eventDrivenSimulation;

import java.util.Random;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

/**
 * Particle extends Ball, by adding mass and color. Add methods to calculate
 * collisions time and resolution.
 */
public class Particle extends Ball {

    private final double mass;
    private final Color color;
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private int collisionCount;
    private static final Random random = new Random();

    /**
     * create a random particle of radius
     */
    public Particle() {
        super(new Coordinate(random.nextDouble(), random.nextDouble()),
                new Coordinate(-0.5 + random.nextDouble(), -0.5 + random.nextDouble()), 0.02);
        this.mass = 0.5;
        this.color = Color.BLACK;
    }

    /**
     * Create a particle with specified position, velocity, and radius. Set the
     * default color as BLACK. Set the default mass as 1.
     *
     * @param position the position of the particle
     * @param velocity the velocity of the particle
     * @param radius   the radius of the particle
     */
    public Particle(Coordinate position, Coordinate velocity, double radius) {
        super(position, velocity, radius);
        this.mass = 1;
        this.color = Color.BLACK;
    }

    /**
     * Create a particle with specified position, velocity, radius, and mass. Set
     * the default color as BLACK.
     *
     * @param position the position of the particle
     * @param velocity the velocity of the particle
     * @param radius   the radius of the particle
     * @param mass     the mass of the particle
     */
    public Particle(Coordinate position, Coordinate velocity, double radius, double mass) {
        super(position, velocity, radius);
        if (mass <= 0) {
            throw new IllegalArgumentException("mass must be positive");
        }
        this.mass = mass;
        this.color = Color.BLACK;
    }

    /**
     * Create a particle with specified position, velocity, radius, mass, and color.
     *
     * @param position the position of the particle
     * @param velocity the velocity of the particle
     * @param radius   the radius of the particle
     * @param mass     the mass of the particle
     * @param color    the color of the particle
     */
    public Particle(Coordinate position, Coordinate velocity, double radius, double mass, Color color) {
        super(position, velocity, radius);
        if (mass <= 0) {
            throw new IllegalArgumentException("mass must be positive");
        }
        this.mass = mass;
        this.color = color;
    }

    @Override
    public final double timeToHit(Ball b) {
        Coordinate dP = b.position.subtract(position);
        Coordinate dV = b.velocity.subtract(velocity);
        double dVdP = dP.dot(dV);
        if (dVdP >= 0) {
            return INFINITY;
        }
        double distance = radius + b.radius;
        double dVdV = dV.dot(dV);
        if (dVdV == 0) {
            return INFINITY;
        }
        double dPdP = dP.dot(dP);
        double distanceSquared = squared(distance);
        if (dPdP < distanceSquared) {
            return INFINITY;
        }
        double d = squared(dVdP) - dVdV * (dPdP - distanceSquared);
        if (d < 0) {
            return INFINITY;
        }
        return -(dVdP + Math.sqrt(d)) / dVdV;
    }

    @Override
    public final double timeToHitXWall() {
        if (velocity.x > 0) {
            return (1.0 - position.x - radius) / velocity.x;
        }
        if (velocity.x < 0) {
            return (radius - position.x) / velocity.x;
        }
        return INFINITY;
    }

    @Override
    public final double timeToHitYWall() {
        if (velocity.y > 0) {
            return (1.0 - position.y - radius) / velocity.y;
        }
        if (velocity.y < 0) {
            return (radius - position.y) / velocity.y;
        }
        return INFINITY;
    }

    /**
     * the resolution of the hitting another ball.
     *
     * @param b the other ball to hit
     */
    public final void BounceOffBall(Particle p) {
        Coordinate dP = p.position.subtract(position);
        Coordinate dV = p.velocity.subtract(velocity);
        double dVdP = dP.dot(dV);
        double distance = radius + p.radius;
        double J = 2 * mass * p.mass * dVdP / (distance * (mass + p.mass));
        velocity.addBy(dP.multiply(J / distance / mass));
        p.velocity.subtractBy(dP.multiply(J / distance / p.mass));
        collisionCount++;
        p.collisionCount++;
    }

    /**
     * the resolution of the hitting a vertical wall.
     */
    public final void BounceOffXWall() {
        velocity.x = -velocity.x;
        collisionCount++;
    }

    /**
     * the resolution of the hitting a horizontal wall.
     */
    public final void BounceOffYWall() {
        velocity.y = -velocity.y;
        collisionCount++;
    }

    public final int getCollisionCount() {
        return collisionCount;
    }

    @Override
    public final void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(position.x, position.y, radius);
    }

    private static final double squared(double d) {
        return d * d;
    }

}
