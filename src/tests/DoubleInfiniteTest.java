package tests;

public class DoubleInfiniteTest {
    public static void main(String[] args) {
        double a = Double.POSITIVE_INFINITY;
        double b = Double.POSITIVE_INFINITY;
        System.out.println(a);
        System.out.println(a + 1.0);
        System.out.println(a - 1.0);
        System.out.println(a == b);
        System.out.println(a + 1.0 == b);
        System.out.println(a + 3.21 == b);
    }
}
