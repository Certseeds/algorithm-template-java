// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int k;
        public final int[] a;

        public TestCase(int n, int k, int[] a) {
            this.n = n;
            this.k = k;
            this.a = a;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 5));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int k = in.nextInt();
            assert ((1 <= k) && (k < n) && (n <= 1000000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
                assert ((1 <= a[i]) && (a[i] <= 1000000000));
            }
            tests.add(new TestCase(n, k, a));
        }
        return tests;
    }

    // cal: compute kth smallest for each test case
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            final int[] b = tc.a;
            Arrays.sort(b);
            final int kth = b[tc.k - 1];
            out.add(String.valueOf(kth));
        }
        return out;
    }

    // output: print each line with newline
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            sb.append(line);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) {
        output(cal(reader()));
    }

    // fast scanner
    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
