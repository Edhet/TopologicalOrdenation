package Demos;

import java.util.Arrays;

public class EratosthenesSieveDemo implements Runnable {
    private final int max;

    public EratosthenesSieveDemo(int max) {
        this.max = max;
    }

    @Override
    public void run() {
        long primeCount = 0;
        boolean[] primes = new boolean[max + 1];
        Arrays.fill(primes, true);

        for (int p = 2; p * p <= max; p++) {
            if (primes[p]) {
                for (int i = p * p; i <= max; i += p)
                    primes[i] = false;
            }
        }

        for (int p = 2; p < max; p++)
            primeCount += primes[p] ? 1 : 0;
        System.out.println(primeCount + " primes found.");
    }
}
