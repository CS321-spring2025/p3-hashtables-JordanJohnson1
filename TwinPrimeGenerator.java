public class TwinPrimeGenerator {
    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max - 2; i++) {
            if (isPrime(i) && isPrime(i + 2)) return i + 2;
        }
        return -1;
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
