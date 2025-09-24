// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        int n;
        int m;
        List<Edge> edges;
    }

    private static final class Edge {
        final int u;
        final int v;
        final int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 50));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final var t = new TestCase();
            t.n = in.nextInt();
            t.m = in.nextInt();
            assert ((1 <= t.n) && (t.n <= 100));
            assert ((t.n <= t.m) && (t.m <= t.n * t.n));
            t.edges = new ArrayList<>(t.m);
            for (int i = 0; i < t.m; i++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                final int w = in.nextInt();
                assert ((1 <= u) && (u <= t.n));
                assert ((1 <= v) && (v <= t.n));
                assert ((1 <= w) && (w <= 1000));
                // convert to 0-based
                t.edges.add(new Edge(u - 1, v - 1, w));
            }
            tests.add(t);
        }
        return tests;
    }

    // cal: find minimal possible maximum edge weight M (bottleneck),
    // then among spanning trees with max edge <= M pick one with minimum total weight.
    public static List<Integer> cal(final List<TestCase> inputs) {
        final List<Integer> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            if (n <= 1) {
                out.add(0);
                continue;
            }
            final List<Edge> edges = tc.edges;

            // collect distinct weights and sort descending to find max bottleneck b
            final var weights = new TreeSet<Integer>(Comparator.reverseOrder());
            for (final Edge e : edges) {
                weights.add(e.w);
            }

            int bestB = 0;
            for (final int w : weights) {
                final UnionFind uf = new UnionFind(n);
                int comps = n;
                for (final Edge e : edges) {
                    if (e.w >= w) {
                        if (uf.union(e.u, e.v)) {
                            comps--;
                        }
                    }
                }
                if (comps == 1) {
                    bestB = w;
                    break;
                }
            }

            // now find MST using only edges with weight >= bestB, minimizing total weight
            final List<Edge> cand = new ArrayList<>();
            for (final Edge e : edges) {
                if (e.w >= bestB) {
                    cand.add(e);
                }
            }
            cand.sort(Comparator.comparingInt(e -> e.w));

            final UnionFind uf2 = new UnionFind(n);
            long sum = 0L;
            int used = 0;
            for (final Edge e : cand) {
                if (uf2.union(e.u, e.v)) {
                    sum += e.w;
                    used++;
                    if (used == n - 1) break;
                }
            }
            // sum should fit in int per constraints, but return int
            out.add((int) sum);
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
