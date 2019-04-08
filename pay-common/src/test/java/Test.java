public class Test {
    public static void main(String[] args) {
        double twoToThe52 = (double)(1L << 52);
       // System.out.println(twoToThe52);

        double a = 3.6;
        double sign = Math.copySign(1.0, a); // preserve
        System.out.println(sign);
        double rint = Math.rint(3.3);
        System.out.println(rint);
    }
}
