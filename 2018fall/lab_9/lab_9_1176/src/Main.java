// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] xs;
        public final int[] ys;
        public final int Q;
        public final int[][] queries;

        public TestCase(int n, int[] xs, int[] ys, int Q, int[][] queries) {
            this.n = n;
            this.xs = xs;
            this.ys = ys;
            this.Q = Q;
            this.queries = queries;
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 50));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert (1 <= n && n <= 1000);
            final int[] xs = new int[n];
            final int[] ys = new int[n];
            for (int i = 0; i < n; i++) {
                xs[i] = in.nextInt();
                ys[i] = in.nextInt();
            }
            final int Q = in.nextInt();
            assert (1 <= Q && Q <= 1000);
            final int[][] queries = new int[Q][2];
            for (int i = 0; i < Q; i++) {
                final int a = in.nextInt();
                final int b = in.nextInt();
                queries[i][0] = a - 1;
                queries[i][1] = b - 1;
            }
            tests.add(new TestCase(n, xs, ys, Q, queries));
        }
        return tests;
    }

    // cal: compute Manhattan distances for each query
    public static List<Integer> cal(final List<TestCase> inputs) {
        final List<Integer> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            for (int i = 0; i < tc.Q; i++) {
                final int u = tc.queries[i][0];
                final int v = tc.queries[i][1];
                if (u < 0 || u >= tc.n || v < 0 || v >= tc.n) {
                    out.add(0); // guard, though tests won't trigger this
                } else {
                    final int dist = Math.abs(tc.xs[u] - tc.xs[v]) + Math.abs(tc.ys[u] - tc.ys[v]);
                    out.add(dist);
                }
            }
        }
        return out;
    }

    // output: print results (accept integers)
    public static void output(final Iterable<Integer> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final Integer v : lines) {
            sb.append(v).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    // fast scanner
    private final static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                final String line = br.readLine();
                if (line == null) return "";
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
