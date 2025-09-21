// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] a;

        public TestCase(int n, int[] a) {
            this.n = n;
            this.a = a;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 100000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            tests.add(new TestCase(n, a));
        }
        return tests;
    }

    // cal: compute inversion count (minimum adjacent swaps) for each test case
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<String>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(String.valueOf(countInversions(tc.a)));
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

    // Count inversions using coordinate compression + BIT
    private static long countInversions(final int[] a) {
        final int n = a.length;
        final int[] vals = Arrays.copyOf(a, n);
        Arrays.sort(vals);
        // coordinate compression: map value -> rank starting from 1
        final Map<Integer, Integer> rank = new HashMap<Integer, Integer>();
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || vals[i] != vals[i - 1]) {
                rank.put(vals[i], ++r);
            }
        }
        final int m = r;
        final long[] bit = new long[m + 2];
        long inv = 0L;
        // iterate from right to left: count elements smaller than current to the right
        for (int i = n - 1; i >= 0; i--) {
            final int rk = rank.get(a[i]);
            inv += sum(bit, rk - 1);
            add(bit, rk, 1);
        }
        return inv;
    }

    private static void add(final long[] bit, int i, final long delta) {
        final int n = bit.length - 1;
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    private static long sum(final long[] bit, int i) {
        long s = 0L;
        while (i > 0) {
            s += bit[i];
            i -= i & -i;
        }
        return s;
    }

    // fast scanner
    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            try {
                while (st == null || !st.hasMoreElements()) {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                }
                return st.nextToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
