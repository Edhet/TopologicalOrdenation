package Demos;

public final class IterativeDemo implements Runnable {
    private final int max;

    public IterativeDemo(int max) {
        this.max = max;
    }

    @Override
    public void run() {
        long primeCount = 0;
        for (int number = 2; number < max; number++) {
            boolean isPrime = true;
            for (int divisor = 2; divisor <= number / 2; divisor++) {
                if (number % divisor == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) primeCount++;
        }
        System.out.println(primeCount + " primes found.");
    }
}
