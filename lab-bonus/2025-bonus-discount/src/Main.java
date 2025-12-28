// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {
    private static final long MOD = 998244353L;

    public static int reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        assert ((1 <= n) && (n <= 1000));
        return n;
    }

    public static long cal(int n) {
        // dp[i] = number of different max-heaps with i nodes
        // For a complete binary tree shape with n nodes:
        // - Root is the maximum
        // - Left subtree has l nodes, right subtree has r nodes (l + r = n - 1)
        // - dp[n] = C(n-1, l) * dp[l] * dp[r]

        if (n <= 1) {
            return 1;
        }

        // Precompute factorials and inverse factorials for combinations
        final var fact = new long[n + 1];
        final var invFact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }
        invFact[n] = modPow(fact[n], MOD - 2);
        for (int i = n - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }

        // leftSize[i] = number of nodes in left subtree for a complete binary tree of size i
        final var leftSize = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            leftSize[i] = computeLeftSize(i);
        }

        // DP
        final var dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            final int l = leftSize[i];
            final int r = i - 1 - l;
            // C(i-1, l) * dp[l] * dp[r]
            dp[i] = comb(i - 1, l, fact, invFact) * dp[l] % MOD * dp[r] % MOD;
        }

        return dp[n];
    }

    private static int computeLeftSize(int n) {
        // For a complete binary tree with n nodes, compute left subtree size
        if (n <= 1) {
            return 0;
        }
        // Height of tree: h = floor(log2(n))
        int h = 0;
        int temp = n;
        while (temp > 1) {
            temp >>= 1;
            h++;
        }
        // Full levels have 2^h - 1 nodes
        // Last level has n - (2^h - 1) = n - 2^h + 1 nodes
        final int fullNodes = (1 << h) - 1;
        final int lastLevel = n - fullNodes;
        // Max nodes in left subtree's last level: 2^(h-1)
        final int maxLeftLast = 1 << (h - 1);
        // Left subtree: full tree of height h-1 plus min(lastLevel, maxLeftLast)
        final int leftFull = (1 << (h - 1)) - 1;
        return leftFull + Math.min(lastLevel, maxLeftLast);
    }

    private static long comb(int n, int k, long[] fact, long[] invFact) {
        if (k < 0 || k > n) {
            return 0;
        }
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    private static long modPow(long base, long exp) {
        long result = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = result * base % MOD;
            }
            base = base * base % MOD;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        final int n = reader();
        final long result = cal(n);
        output(result);
    }

    public static void output(long number) {
        System.out.print(number);
        System.out.print('\n');
    }


    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
