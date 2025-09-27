// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] w;
        public final int[][] edges;

        public TestCase(int n, int[] w, int[][] edges) {
            this.n = n;
            this.w = w;
            this.edges = edges;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final var in = new Reader();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 100));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 10000));
            final int[] w = new int[n + 1];
            for (int i = 1; i <= n; i++) w[i] = in.nextInt();
            final int[][] edges = new int[n - 1][2];
            for (int i = 0; i < n - 1; i++) {
                final int a = in.nextInt();
                final int b = in.nextInt();
                edges[i][0] = a;
                edges[i][1] = b;
            }
            tests.add(new TestCase(n, w, edges));
        }
        return tests;
    }

    // cal: compute winner using depth-xor heuristic
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (final var tc : inputs) {
            final int n = tc.n;
            final int[] w = tc.w;
            final List<List<Integer>> g = new ArrayList<>(n + 1);
            g.add(new ArrayList<>()); // dummy for 0-index
            for (int i = 1; i <= n; i++) g.add(new ArrayList<>());
            for (final var e : tc.edges) {
                final int u = e[0], v = e[1];
                g.get(u).add(v);
                g.get(v).add(u);
            }
            // BFS from root 1 to compute depths (1-based)
            final int[] depth = new int[n + 1];
            for (int i = 1; i <= n; i++) depth[i] = -1;
            final Deque<Integer> dq = new ArrayDeque<>();
            depth[1] = 1;
            dq.addLast(1);
            while (!dq.isEmpty()) {
                final int u = dq.removeFirst();
                for (final var v : g.get(u)) {
                    if (depth[v] == -1) {
                        depth[v] = depth[u] + 1;
                        dq.addLast(v);
                    }
                }
            }
            int x = 0;
            for (int i = 1; i <= n; i++) {
                if (w[i] == 1) x ^= depth[i];
            }
            out.add(x != 0 ? "YES" : "NO");
        }
        return out;
    }

    // output
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
