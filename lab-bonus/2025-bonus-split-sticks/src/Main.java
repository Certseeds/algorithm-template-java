// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        final int n;
        final long k;
        final long[] c;

        TestCase(int n, long k, long[] c) {
            this.n = n;
            this.k = k;
            this.c = c;
        }
    }

    public static TestCase[] reader() throws IOException {
        final var input = new Reader();
        final int t = input.nextInt();
        assert ((1 <= t) && (t <= 10000));
        final var cases = new TestCase[t];
        for (int i = 0; i < t; i++) {
            final int n = input.nextInt();
            final long k = input.nextLong();
            assert ((1 <= n) && (n <= 30000));
            assert ((1 <= k) && (k <= (long) 1e12));
            final var c = new long[n];
            for (int j = 0; j < n; j++) {
                c[j] = input.nextLong();
                assert ((0 <= c[j]) && (c[j] <= (long) 1e12));
            }
            cases[i] = new TestCase(n, k, c);
        }
        return cases;
    }

    public static long[] cal(TestCase[] cases) {
        final var results = new long[cases.length];
        for (int i = 0; i < cases.length; i++) {
            results[i] = solve(cases[i]);
        }
        return results;
    }

    private static long solve(TestCase tc) {
        final int n = tc.n;
        final long k = tc.k;
        final long[] c = tc.c;

        // Binary search on answer: number of sticks per row
        long left = 0;
        long right = 0;
        for (int i = 0; i < n; i++) {
            right += c[i];
        }
        right = right / k + 1;

        while (left < right) {
            final long mid = left + (right - left + 1) / 2;
            if (canAchieve(c, n, k, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left * k;
    }

    private static boolean canAchieve(long[] c, int n, long k, long perRow) {
        if (perRow == 0) {
            return true;
        }
        // Greedy: count how many rows we can form
        // Each row can use sticks of length i and i+1 (adjacent lengths only)
        // Key: carry can only come from the previous length, not earlier
        long rows = 0;
        long prevCarry = 0; // carry from length i-1 only

        for (int i = 0; i < n; i++) {
            // prevCarry is from length i-1, c[i] is length i
            // They can be combined (diff = 1)
            final long combined = prevCarry + c[i];
            rows += combined / perRow;
            final long remainder = combined % perRow;
            
            // The remainder contains:
            // - Some from prevCarry (length i-1)
            // - Some from c[i] (length i)
            // Only the part from c[i] can carry to i+1
            // The part from prevCarry (length i-1) cannot carry to i+1 (diff would be 2)
            
            // After forming rows, remainder is at most perRow-1
            // prevCarry was at most perRow-1, c[i] could be huge
            // How much of remainder is from c[i]?
            // If combined = prevCarry + c[i], and we took floor(combined/perRow) rows,
            // remainder = combined % perRow
            // The remainder might have sticks from both lengths
            
            // To maximize rows, we should use prevCarry first
            // So remainder is the last (combined % perRow) sticks
            // If prevCarry < remainder, then remainder includes some prevCarry
            // If prevCarry >= remainder, then all of remainder is from c[i]
            
            // Actually, to carry to next, we can only carry from c[i]
            // So prevCarry for next iteration = min(remainder, c[i] % perRow)
            // No wait, this isn't right either...
            
            // Correct: remainder contains the sticks that didn't form complete rows
            // Some might be from prevCarry, some from c[i]
            // Only the ones from c[i] can carry to i+1
            // The max we can carry is min(remainder, c[i])
            // But we want to maximize rows, so we should prioritize using prevCarry
            // and carry as much c[i] as possible
            
            // If we use prevCarry first in forming rows:
            // - Use prevCarry sticks, then use (rows*perRow - prevCarry) from c[i]
            // - Remaining from c[i] = c[i] - (rows*perRow - prevCarry) = c[i] - rows*perRow + prevCarry
            // - But remainder = prevCarry + c[i] - rows*perRow = prevCarry + c[i] - rows*perRow
            // - So remaining from c[i] = remainder - (prevCarry that wasn't used)
            
            // Hmm, let's think differently:
            // remainder is the leftover after forming rows
            // It's a mix of length i-1 and length i sticks
            // For carrying to i+1, we need length i sticks only
            // The min number of length i sticks in remainder is max(0, remainder - prevCarry)
            // The max number of length i sticks in remainder is min(remainder, c[i])
            // To maximize future rows, carry as much length i as possible
            // So carry = min(remainder, c[i])
            
            prevCarry = Math.min(remainder, c[i]);
        }

        return rows >= k;
    }

    public static void main(String[] args) throws IOException {
        final var cases = reader();
        final var results = cal(cases);
        output(results);
    }

    public static void output(long[] results) {
        for (final long result : results) {
            System.out.print(result);
            System.out.print('\n');
        }
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
