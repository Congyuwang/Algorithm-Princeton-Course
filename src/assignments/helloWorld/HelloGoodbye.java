package assignments.helloWorld;

public class HelloGoodbye {
    public static void main(String[] args) {
        var nameA = args[0];
        var nameB = args[1];
        System.out.printf("Hello %s and %s.\n" +
                "Goodbye %s and %s.\n", nameA, nameB, nameB, nameA);
    }
}
