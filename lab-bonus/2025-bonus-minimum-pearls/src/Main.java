// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public final class Main {

    public static final class PearlString {
        final long a; // white on left
        final long b; // black on right

        PearlString(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    public static final class TestCase {
        final int n;
        final PearlString[] strings;

        TestCase(int n, PearlString[] strings) {
            this.n = n;
            this.strings = strings;
        }
    }

    public static TestCase[] reader() throws IOException {
        final var input = new Reader();
        final int t = input.nextInt();

        final var cases = new TestCase[t];
        for (int i = 0; i < t; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 100000));
            final var strings = new PearlString[n];
            for (int j = 0; j < n; j++) {
                final long a = input.nextLong();
                final long b = input.nextLong();
                assert ((a >= 0) && (b >= 0) && (a + b >= 1) && (a + b <= 10000));
                strings[j] = new PearlString(a, b);
            }
            cases[i] = new TestCase(n, strings);
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
        final var leftHeavy = new ArrayList<PearlString>();
        final var rightHeavy = new ArrayList<PearlString>();

        for (final var s : tc.strings) {
            if (s.a <= s.b) {
                leftHeavy.add(s);
            } else {
                rightHeavy.add(s);
            }
        }

        leftHeavy.sort(Comparator.comparingLong(s -> s.a));
        rightHeavy.sort((s1, s2) -> Long.compare(s2.b, s1.b));

        final var ordered = new ArrayList<PearlString>(tc.n);
        ordered.addAll(leftHeavy);
        ordered.addAll(rightHeavy);

        long unmatchedWhite = 0;
        long carryBlack = 0;
        for (final var s : ordered) {
            final long eliminated = Math.min(carryBlack, s.a);
            carryBlack -= eliminated;
            unmatchedWhite += (s.a - eliminated);
            carryBlack += s.b;
        }
        return unmatchedWhite + carryBlack;
    }

    public static void main(String[] args) throws IOException {
        final var cases = reader();
        final var results = cal(cases);
        output(results);
    }

    public static void output(long[] results) {
        final var sb = new StringBuilder();
        for (final long result : results) {
            sb.append(result);
            sb.append('\n');
        }
        System.out.print(sb);
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
