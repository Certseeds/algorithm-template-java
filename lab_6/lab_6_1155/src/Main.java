// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[][] edges; // m x 2, edges

        public TestCase(int n, int[][] edges) {
            this.n = n;
            this.edges = edges;
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
            final int m = n - 1;
            final int[][] edges = new int[m][2];
            for (int i = 0; i < m; i++) {
                final int a = in.nextInt();
                final int b = in.nextInt();
                assert ((1 <= a) && (a <= n));
                assert ((1 <= b) && (b <= n));
                edges[i][0] = a;
                edges[i][1] = b;
            }
            tests.add(new TestCase(n, edges));
        }
        return tests;
    }

    // cal: compute diameter for each test case and return output lines
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> outLines = new ArrayList<>();
        for (final var tc : inputs) {
            final int n = tc.n;
            if (n == 1) {
                outLines.add("0");
                continue;
            }
            // build adjacency
            final List<List<Integer>> g = new ArrayList<>(n + 1);
            g.add(new ArrayList<>()); // index 0 dummy
            for (int i = 1; i <= n; i++) g.add(new ArrayList<>());
            for (final var e : tc.edges) {
                final int u = e[0];
                final int v = e[1];
                g.get(u).add(v);
                g.get(v).add(u);
            }
            // first BFS from node 1 (or any existing node) to find farthest
            final int start = 1;
            final int[] res1 = bfsFar(g, start);
            int far = start;
            int maxd = -1;
            for (int i = 1; i <= n; i++) {
                if (res1[i] > maxd) {
                    maxd = res1[i];
                    far = i;
                }
            }
            // second BFS from far to get diameter
            final int[] res2 = bfsFar(g, far);
            int diam = 0;
            for (int i = 1; i <= n; i++) if (res2[i] > diam) diam = res2[i];
            outLines.add(String.valueOf(diam));
        }
        return outLines;
    }

    // BFS that returns distances from s (1-based indexing)
    private static int[] bfsFar(final List<List<Integer>> g, final int s) {
        final int n = g.size() - 1;
        final int[] dist = new int[n + 1];
        for (int i = 1; i <= n; i++) dist[i] = -1;
        final Deque<Integer> dq = new ArrayDeque<>();
        dist[s] = 0;
        dq.addLast(s);
        while (!dq.isEmpty()) {
            final int u = dq.removeFirst();
            for (final var v : g.get(u)) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    dq.addLast(v);
                }
            }
        }
        return dist;
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
