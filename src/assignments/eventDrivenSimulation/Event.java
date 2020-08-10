package assignments.eventDrivenSimulation;

/**
 * The Event class represents an collision event that either involves two balls
 * or one ball with a wall, or a redraw event.
 * <p>
 * The convention is the following:
 * <ul>
 * <li>eventType = 0: if both particles are null, this is a redraw command.</li>
 * <li>eventType = 1: if both particles are not null, this is a collision of
 * balls.</li>
 * <li>if the only the second particle is null, this is a collision of a ball
 * and a wall.
 * <ul>
 * <li>eventType = 2: the particle hits X-wall.</li>
 * <li>eventType = 3: the particle hits Y-wall..</li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * The Event class implements a natural order that is an reverse order of the
 * {@code time} field. So, the maximum event happens first.
 */
public class Event implements Comparable<Event> {
    public final short eventType;
    private final double time;
    public final Particle p1, p2;
    private final int collisionCount1, collisionCount2;

    /**
     * The constructor of an event.
     * <p>
     * The convention is the following:
     * <ul>
     * <li>eventType = 0: if both particles are null, this is a redraw command.</li>
     * <li>eventType = 1: if both particles are not null, this is a collision of
     * balls.</li>
     * <li>if the only the second particle is null, this is a collision of a ball
     * and a wall.
     * <ul>
     * <li>eventType = 2: the particle hits X-wall.</li>
     * <li>eventType = 3: the particle hits Y-wall..</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * create an event according to the convention mentioned above.
     *
     * @param time      time of this event in the timeLine
     * @param p1        the first particle
     * @param p2        the second particle, null if hits walls or is redraw event
     * @param eventType parse the event type as the convention demands
     */
    Event(double time, Particle p1, Particle p2, short eventType) {
        this.time = time;
        this.p1 = p1;
        this.p2 = p2;
        this.eventType = eventType;
        this.collisionCount1 = p1 == null ? 0 : p1.getCollisionCount();
        this.collisionCount2 = p2 == null ? 0 : p2.getCollisionCount();
    }

    /**
     * check whether any collision occurred for p1 or p2 before this event
     */
    final boolean isValid() {
        return switch (eventType) {
            case 1 -> p1.getCollisionCount() == collisionCount1 && p2.getCollisionCount() == collisionCount2;
            case 2, 3 -> p1.getCollisionCount() == collisionCount1;
            default -> true;
        };
    }

    /**
     * get the event time
     *
     * @return the event time
     */
    final double getTime() {
        return time;
    }

    /**
     * this is a reverse order of {@code time}, so the maximum happens first
     */
    @Override
    public int compareTo(Event o) {
        return (int) Math.signum(o.time - this.time);
    }

}
