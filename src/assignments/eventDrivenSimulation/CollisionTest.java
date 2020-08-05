package assignments.eventDrivenSimulation;

import java.util.Scanner;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

public final class CollisionTest {

    private final Thread animation;
    private CollisionSystem system;

    CollisionTest(String fileName) {
        animation = new Thread() {
            public void run() {

                Scanner scanner = new Scanner(getClass().getResourceAsStream("data/CollisionSystems/" + fileName));
                StdDraw.setCanvasSize(600, 600);

                // enable double buffering
                StdDraw.enableDoubleBuffering();

                // the array of particles
                Particle[] particles;

                int n = scanner.nextInt();
                particles = new Particle[n];
                for (int i = 0; i < n; i++) {
                    Coordinate position = new Coordinate(scanner.nextDouble(), scanner.nextDouble());
                    Coordinate velocity = new Coordinate(25 * scanner.nextDouble(), 25 * scanner.nextDouble());
                    double radius = scanner.nextDouble();
                    double mass = scanner.nextDouble();
                    int r = scanner.nextInt();
                    int g = scanner.nextInt();
                    int b = scanner.nextInt();
                    Color color = new Color(r, g, b);
                    particles[i] = new Particle(position, velocity, radius, mass, color);
                }

                scanner.close();

                // create collision system and simulate
                system = new CollisionSystem(particles);
                system.simulate(10000);
            }
        };
    }

    public void start() {
        animation.start();
    }

    public void stop() {
        system.stop();
    }
}
