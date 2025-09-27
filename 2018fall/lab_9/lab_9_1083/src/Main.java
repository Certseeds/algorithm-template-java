// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        final int n;
        final int[] to; // 1-indexed permutation: to[i] = destination of i-th bus line
        public TestCase(final int n, final int[] to) {
            this.n = n;
            this.to = to;
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 50));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 100000));
            final int[] to = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                to[i] = in.nextInt();
            }
            tests.add(new TestCase(n, to));
        }
        return tests;
    }

    // cal: compute maximum convenience with up to two destination changes
    public static List<Long> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Long>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int[] to = tc.to;
            final boolean[] vis = new boolean[n + 1];

            long sumSq = 0L;
            int max1 = 0, max2 = 0;

            for (int i = 1; i <= n; i++) {
                if (vis[i]) continue;
                int cnt = 0;
                int j = i;
                while (!vis[j]) {
                    vis[j] = true;
                    cnt++;
                    j = to[j];
                }
                sumSq += 1L * cnt * cnt;
                if (cnt >= max1) {
                    max2 = max1;
                    max1 = cnt;
                } else if (cnt >= max2) {
                    max2 = cnt;
                }
            }

            long ans = sumSq;
            if (max2 > 0) {
                ans += 2L * max1 * max2;
            }
            out.add(ans);
        }
        return out;
    }

    // output: print results (accept longs)
    public static void output(final Iterable<Long> lines) {
        final var sb = new StringBuilder();
        for (final Long v : lines) {
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