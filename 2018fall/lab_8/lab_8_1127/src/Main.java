// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    public static final class Edge {
        public int to;
        public int w;
        public int next;
    }

    public static final class Graph {
        private final int n;
        public final Edge[] edges;
        public final int[] head;
        private int edgePtr;

        public Graph(int n, int m) {
            this.n = n;
            this.edges = new Edge[m];
            this.head = new int[n];
            Arrays.fill(this.head, -1);
            this.edgePtr = 0;
        }

        public void addEdge(int u, int v, int w) {
            if (u < 0 || u >= n || v < 0 || v >= n) return; // guard
            edges[edgePtr] = new Edge();
            edges[edgePtr].to = v;
            edges[edgePtr].w = w;
            edges[edgePtr].next = head[u];
            head[u] = edgePtr++;
        }

    }

    public static final class TestCase {
        public final int n;
        public final int m;
        public final int[] us;
        public final int[] vs;
        public final int[] ws;

        public TestCase(int n, int m, int[] us, int[] vs, int[] ws) {
            this.n = n;
            this.m = m;
            this.us = us;
            this.vs = vs;
            this.ws = ws;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            assert ((1 <= n) && (n <= 100000));
            assert ((1 <= m) && (m <= 200000));
            final int[] us = new int[m];
            final int[] vs = new int[m];
            final int[] ws = new int[m];
            for (int i = 0; i < m; i++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                final int w = in.nextInt();
                us[i] = u - 1;
                vs[i] = v - 1;
                ws[i] = w;
            }
            tests.add(new TestCase(n, m, us, vs, ws));
        }
        return tests;
    }

    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            final int[] us = tc.us;
            final int[] vs = tc.vs;
            final int[] ws = tc.ws;

            final Graph graph = new Graph(n, m);
            final int[] indeg = new int[n];

            for (int i = 0; i < m; i++) {
                final int u = us[i];
                final int v = vs[i];
                final int ww = ws[i];
                graph.addEdge(u, v, ww);
                if (v >= 0 && v < n) { // guard for indegree
                    indeg[v]++;
                }
            }

            final long[] dist = new long[n]; // longest distance to node
            // Kahn's algorithm
            final int[] q = new int[n];
            int qh = 0, qt = 0;
            for (int i = 0; i < n; i++) {
                if (indeg[i] == 0) {
                    q[qt++] = i;
                }
            }

            final int[] head = graph.head;
            final Edge[] edges = graph.edges;

            while (qh < qt) {
                final int u = q[qh++];
                for (int e = head[u]; e != -1; e = edges[e].next) {
                    final int v = edges[e].to;
                    final long nd = dist[u] + (long) edges[e].w;
                    if (nd > dist[v]) {
                        dist[v] = nd;
                    }
                    indeg[v]--;
                    if (indeg[v] == 0) {
                        q[qt++] = v;
                    }
                }
            }
            // since graph guaranteed acyclic, all nodes should be visited
            long ans = 0L;
            for (int i = 0; i < n; i++) {
                if (dist[i] > ans) {
                    ans = dist[i];
                }
            }
            out.add(String.valueOf(ans));
        }
        return out;
    }

    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String s : lines) {
            sb.append(s).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    // fast scanner
    public static final class FastScanner {
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
