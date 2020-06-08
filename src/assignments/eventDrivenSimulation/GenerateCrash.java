package assignments.eventDrivenSimulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateCrash {
    public static void main(String[] args) {
        File file = new File("src/assignments/eventDrivenSimulation/data/CollisionSystems/crash.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("1177\n");
            double x = 0.02;
            int count = 0;
            while (x < 0.5) {
                double y = 0.02;
                while (y < 1) {
                    fileWriter.append(String.valueOf(x));
                    fileWriter.append(' ');
                    fileWriter.append(String.valueOf(y));
                    fileWriter.append(' ');
                    fileWriter.append(" 0.0 0.0 0.005 0.001 255 0 0\n");
                    count++;
                    y += 0.02;
                }
                x += 0.02;
            }
            fileWriter.append("0.75 0.5 -0.005 0.0 0.15 10000 0 255 255\n");
            System.out.println(++count);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
