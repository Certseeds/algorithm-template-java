// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int m;
        public final int[][] edges; // [m][3] : u, v, w

        public TestCase(int n, int m, int[][] edges) {
            this.n = n;
            this.m = m;
            this.edges = edges;
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
            final int m = in.nextInt();
            assert ((1 <= n) && (n <= 100));
            assert ((n <= m) && (m <= n * n));
            final int[][] edges = new int[m][3];
            for (int i = 0; i < m; i++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                final int w = in.nextInt();
                // convert to 0-based indices
                edges[i][0] = u - 1;
                edges[i][1] = v - 1;
                edges[i][2] = w;
            }
            tests.add(new TestCase(n, m, edges));
        }
        return tests;
    }

    // cal: compute MST total weight for each test case using Kruskal
    public static List<Integer> cal(final List<TestCase> inputs) {
        final List<Integer> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            final int[][] edges = tc.edges;
            // sort edges by weight ascending
            Arrays.sort(edges, Comparator.comparingInt(a -> a[2]));
            final UnionFind uf = new UnionFind(n);
            long sum = 0L;
            int used = 0;
            for (int i = 0; i < m && used < n - 1; i++) {
                final int u = edges[i][0];
                final int v = edges[i][1];
                final int w = edges[i][2];
                if (uf.union(u, v)) {
                    sum += w;
                    used++;
                }
            }
            // if not connected, according to problem description graph is connected; but guard anyway
            if (used != n - 1) {
                out.add(0);
            } else {
                out.add((int) sum);
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

    // simple union-find
    private static final class UnionFind {
        private final int[] parent;
        private final int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }
}
