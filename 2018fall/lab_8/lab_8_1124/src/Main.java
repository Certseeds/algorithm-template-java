// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int m;
        public final int[][] edges;

        public TestCase(int n, int m, int[][] edges) {
            this.n = n;
            this.m = m;
            this.edges = edges;
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            assert ((1 <= n) && (n <= 75));
            assert ((0 <= m) && (m <= 100000));
            final int[][] edges = new int[m][2];
            for (int i = 0; i < m; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                edges[i][0] = x - 1;
                edges[i][1] = y - 1;
            }
            tests.add(new TestCase(n, m, edges));
        }
        return tests;
    }

    // cal: count 4-cliques
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            final int[][] edges = tc.edges;
            // adjacency: use BitSet per node for neighbors
            final BitSet[] nbr = new BitSet[n];
            for (int i = 0; i < n; i++) {
                nbr[i] = new BitSet(n);
            }
            // avoid multi-edges and self loops
            final boolean[][] adj = new boolean[n][n];
            for (int i = 0; i < m; i++) {
                final int u = edges[i][0];
                final int v = edges[i][1];
                if (u < 0 || u >= n || v < 0 || v >= n) {
                    continue;
                }
                if (u == v) {
                    continue;
                }
                if (!adj[u][v]) {
                    adj[u][v] = adj[v][u] = true;
                    nbr[u].set(v);
                    nbr[v].set(u);
                }
            }
            // For each undirected edge (u<v), count common neighbors t = |nbr[u] & nbr[v]|; each pair of common neighbors contributes a 4-clique with u and v
            // number of 4-cliques = sum_{u<v, adj[u][v]} C(t,2) / 6? Actually each 4-clique counted for each of its 6 edges, and for an edge (u,v) it appears C(2,2)=1. So total = sum C(t,2) / 6
            long sum = 0L;
            for (int u = 0; u < n; u++) {
                for (int v = u + 1; v < n; v++) {
                    if (!adj[u][v]) {
                        continue;
                    }
                    // compute intersection count
                    final BitSet tmp = (BitSet) nbr[u].clone();
                    tmp.and(nbr[v]);
                    // enumerate common neighbors and only count pairs that are connected to each other
                    int t = tmp.cardinality();
                    if (t >= 2) {
                        // collect indices of common neighbors
                        final int[] list = new int[t];
                        int idx = 0;
                        for (int w = tmp.nextSetBit(0); w >= 0; w = tmp.nextSetBit(w + 1)) {
                            list[idx++] = w;
                        }
                        for (int i1 = 0; i1 < t; i1++) {
                            for (int j1 = i1 + 1; j1 < t; j1++) {
                                final int c = list[i1];
                                final int d = list[j1];
                                if (adj[c][d]) {
                                    sum++;
                                }
                            }
                        }
                    }
                }
            }
            final long cliques4 = sum / 6L; // divide by 6 because each 4-clique counted once per edge (6 edges)
            out.add(String.valueOf(cliques4));
        }
        return out;
    }

    // output: print results
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            sb.append(line).append('\n');
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
