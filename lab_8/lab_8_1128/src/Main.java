// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    public static final class Edge {
        public int to;
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
            for (int i = 0; i < m; i++) {
                this.edges[i] = new Edge();
            }
            this.head = new int[n];
            Arrays.fill(this.head, -1);
            this.edgePtr = 0;
        }

        public void addEdge(int u, int v) {
            if (u < 0 || u >= n || v < 0 || v >= n) return; // guard
            edges[edgePtr].to = v;
            edges[edgePtr].next = head[u];
            head[u] = edgePtr++;
        }
    }

    public static final class TestCase {
        public final int n;
        public final int m;
        public final int k;
        public final int[] us;
        public final int[] vs;
        public final int[] specialNodes;

        public TestCase(int n, int m, int k, int[] us, int[] vs, int[] specialNodes) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.us = us;
            this.vs = vs;
            this.specialNodes = specialNodes;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            final int k = in.nextInt();
            assert ((1 <= n) && (n <= 300000));
            assert ((1 <= m) && (m <= 400400));
            assert ((1 <= k) && (k <= n));

            final var us = new int[m];
            final var vs = new int[m];
            for (int i = 0; i < m; i++) {
                us[i] = in.nextInt() - 1;
                vs[i] = in.nextInt() - 1;
            }

            final var specialNodes = new int[k];
            for (int i = 0; i < k; i++) {
                specialNodes[i] = in.nextInt() - 1;
            }
            tests.add(new TestCase(n, m, k, us, vs, specialNodes));
        }
        return tests;
    }

    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final var tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;

            final var graph = new Graph(n, m * 2);
            for (int i = 0; i < m; i++) {
                graph.addEdge(tc.us[i], tc.vs[i]);
                graph.addEdge(tc.vs[i], tc.us[i]);
            }

            final var dist = new int[n];
            Arrays.fill(dist, -1);
            final var owner = new int[n];
            Arrays.fill(owner, -1);
            final Queue<Integer> q = new ArrayDeque<>();

            for (final int specialNode : tc.specialNodes) {
                if (dist[specialNode] == -1) {
                    dist[specialNode] = 0;
                    owner[specialNode] = specialNode;
                    q.add(specialNode);
                }
            }

            int ans = Integer.MAX_VALUE;

            while (!q.isEmpty()) {
                final int u = q.poll();

                if (dist[u] * 2 >= ans) {
                    break;
                }

                for (int e = graph.head[u]; e != -1; e = graph.edges[e].next) {
                    final int v = graph.edges[e].to;
                    if (owner[v] != -1) { // Visited
                        if (owner[v] != owner[u]) {
                            ans = Math.min(ans, dist[u] + 1 + dist[v]);
                        }
                    } else { // Not visited
                        owner[v] = owner[u];
                        dist[v] = dist[u] + 1;
                        q.add(v);
                    }
                }
            }

            if (ans == Integer.MAX_VALUE) {
                out.add(-1);
            } else {
                out.add(ans);
            }
        }
        return out;
    }

    public static void output(final List<Integer> lines) {
        final var sb = new StringBuilder();
        for (final var s : lines) {
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
