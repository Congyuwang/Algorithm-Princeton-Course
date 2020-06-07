package assignments.eventDrivenSimulation;

import java.util.concurrent.atomic.AtomicBoolean;
import edu.princeton.cs.algs4.StdDraw;
import princeton.algo.binaryHeap.PriorityQueue;

/**
 * Collision system simulates N body collision
 */
public class CollisionSystem {

    private final PriorityQueue<Event> events = new PriorityQueue<>();
    private final Particle[] particles;
    private double clock;
    private long lastDrawTime;
    private static final short FRAME_RATE = 27;
    private static final double timeStep = 1.0 / FRAME_RATE;
    private static final short REDRAW = 0;
    private static final short COLLIDE = 1;
    private static final short HIT_X = 2;
    private static final short HIT_Y = 3;
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    /**
     * Initialize the CollisionSystem, which takes N^2 log(N) time.
     *
     * @param particles the collection of particles in the system
     */
    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();

        // add all possible collisions
        for (int i = 0; i < particles.length; i++) {
            for (int j = i; j < particles.length; j++) {
                addToEvents(particles[i].timeToHit(particles[j]), particles[i], particles[j], COLLIDE);
            }
        }

        // add all hit-wall events
        for (int i = 0; i < particles.length; i++) {
            addToEvents(particles[i].timeToHitXWall(), particles[i], null, HIT_X);
            addToEvents(particles[i].timeToHitYWall(), particles[i], null, HIT_Y);
        }

        // add the first redraw command
        events.add(new Event(0, null, null, REDRAW));
    }

    /**
     * predict is invoked when a collision happens, and adds new collision
     * predictions into Event Queue
     *
     * @param p the particle associated with this update
     */
    private final void predict(Particle p) {
        assert p != null;

        // update all collision events related to particle p
        for (int i = 0; i < particles.length; i++) {
            addToEvents(p.timeToHit(particles[i]), p, particles[i], COLLIDE);
        }
        addToEvents(p.timeToHitXWall(), p, null, HIT_X);
        addToEvents(p.timeToHitYWall(), p, null, HIT_Y);
    }

    /**
     * repaint all particles, and add a new redraw event, pause so that the redraw
     * event happens with specified frameRate
     */
    private final void redraw() {
        StdDraw.clear();
        for (Particle p : particles) {
            p.draw();
        }
        // calculate the time to pause (in milliseconds)
        double waitTime = 1000 * timeStep + lastDrawTime - System.currentTimeMillis();
        StdDraw.pause(waitTime <= 0 ? 0 : (int) waitTime);

        // update lastDrawTime
        StdDraw.show();
        lastDrawTime = System.currentTimeMillis();
        events.add(new Event(clock + timeStep, null, null, REDRAW));
    }

    /**
     * utility method to add new events only if the event will happen (i.e. time not
     * INFINITY), which reduces the size of events Queue
     *
     * @param e the event to be added
     */
    private final void addToEvents(double time, Particle p1, Particle p2, short eventType) {
        if (time != INFINITY) {
            events.add(new Event(clock + time, p1, p2, eventType));
        }
    }

    /**
     * start the simulation, which will paint the collision system in real-time.
     *
     * @param limit the maximum simulation time in seconds.
     */
    public void simulate(double limit, AtomicBoolean running) {
        while (!events.isEmpty() && running.get()) {

            // get the latest event
            Event event = events.remove();
            double eventTime = event.getTime();

            // continue if the event is invalid
            if (!event.isValid()) {
                continue;
            }

            // move the particles by the event time
            for (Particle p : particles) {
                p.move(eventTime - clock);
            }

            // update the clock and add new events
            clock = eventTime;

            switch (event.eventType) {
                case 1:
                    event.p1.BounceOffBall(event.p2);
                    predict(event.p1);
                    predict(event.p2);
                    break;
                case 2:
                    event.p1.BounceOffXWall();
                    predict(event.p1);
                    break;
                case 3:
                    event.p1.BounceOffYWall();
                    predict(event.p1);
                    break;
                default:
                    redraw();
            }

            if (clock > limit) {
                return;
            }
        }
    }

    /**
     * test client, try 10 random points
     */
    public static void main(String[] args) {

        StdDraw.setCanvasSize(600, 600);

        // enable double buffering
        StdDraw.enableDoubleBuffering();

        // the array of particles
        Particle[] particles;

        // create 10 random particles
        int n = 30;
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            particles[i] = new Particle();
        }
        StdDraw.filledCircle(0.5, 0.5, 0.02);

        // create collision system and simulate
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000, new AtomicBoolean(true));
    }
}
