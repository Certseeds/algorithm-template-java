// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] a;
        public final int q;
        public final int[] type;
        public final int[] val;

        public TestCase(int n, int[] a, int q, int[] type, int[] val) {
            this.n = n;
            this.a = a;
            this.q = q;
            this.type = type;
            this.val = val;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final var in = new Reader();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 100000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            final int q = in.nextInt();
            assert ((1 <= q) && (q <= 100000));
            final int[] type = new int[q];
            final int[] val = new int[q];
            for (int i = 0; i < q; i++) {
                final int t = in.nextInt();
                type[i] = t;
                if (t == 1) {
                    final int x = in.nextInt();
                    val[i] = x;
                } else {
                    val[i] = 0;
                }
            }
            tests.add(new TestCase(n, a, q, type, val));
        }
        return tests;
    }

    // cal: execute operations and collect outputs for queries
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> outLines = new ArrayList<>();
        for (final var tc : inputs) {
            final PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (final var x : tc.a) pq.add(x);
            for (int i = 0; i < tc.q; i++) {
                final int t = tc.type[i];
                if (t == 1) {
                    pq.add(tc.val[i]);
                } else if (t == 2) {
                    if (!pq.isEmpty()) pq.poll();
                } else if (t == 3) {
                    if (pq.isEmpty()) {
                        outLines.add(String.valueOf(-1));
                    } else {
                        outLines.add(String.valueOf(pq.peek()));
                    }
                }
            }
        }
        return outLines;
    }

    // output: print each line and ensure newline after each
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final var line : lines) {
            sb.append(line);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) {
        output(cal(reader()));
    }

    // fast reader
    public static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        public Reader() {
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
