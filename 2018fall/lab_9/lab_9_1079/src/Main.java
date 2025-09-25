// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        final int n;
        final int m;
        final int[][] edges;

        TestCase(final int n, final int m, final int[][] edges) {
            this.n = n;
            this.m = m;
            this.edges = edges;
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 500));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            assert ((1 <= n) && (n <= 5000));
            assert ((0 <= m) && (m <= 20000));
            final var edges = new int[m][2];
            for (int i = 0; i < m; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                assert ((1 <= x) && (x <= n));
                assert ((1 <= y) && (y <= n));
                edges[i][0] = x;
                edges[i][1] = y;
            }
            tests.add(new TestCase(n, m, edges));
        }
        return tests;
    }

    // cal: compute lexicographically smallest topological ordering or "impossible"
    public static List<String> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<String>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            // forward-star adjacency: use a small wrapper class instead of bare arrays
            final ForwardStar g = new ForwardStar(n, m);
            final int[] indeg = new int[n + 1];
            for (int i = 0; i < m; i++) {
                final int x = tc.edges[i][0];
                final int y = tc.edges[i][1];
                g.addEdge(x, y);
                indeg[y]++;
            }
            final var pq = new PriorityQueue<Integer>();
            for (int i = 1; i <= n; i++) if (indeg[i] == 0) pq.add(i);
            final var order = new ArrayList<Integer>(n);
            while (!pq.isEmpty()) {
                final int u = pq.poll();
                order.add(u);
                for (int eid = g.head[u]; eid != -1; eid = g.next[eid]) {
                    final int v = g.to[eid];
                    indeg[v]--;
                    if (indeg[v] == 0) pq.add(v);
                }
            }
            if (order.size() != n) {
                out.add("impossible");
            } else {
                final var sb = new StringBuilder();
                for (int i = 0; i < order.size(); i++) {
                    if (i > 0) sb.append(' ');
                    sb.append(order.get(i));
                }
                out.add(sb.toString());
            }
        }
        return out;
    }

    // output: print results (accept strings)
    public static void output(final Iterable<String> lines) {
        final var sb = new StringBuilder();
        for (final String v : lines) {
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

// Forward-star adjacency wrapper (chain representation)
final class ForwardStar {
    final int[] head;
    final int[] to;
    final int[] next;
    private int ptr = 0;

    ForwardStar(int n, int m) {
        head = new int[n + 1];
        Arrays.fill(head, -1);
        to = new int[m];
        next = new int[m];
    }

    void addEdge(int u, int v) {
        to[ptr] = v;
        next[ptr] = head[u];
        head[u] = ptr;
        ptr++;
    }
}
