// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        int n;
        int m;
        int k;
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
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final var t = new TestCase();
            t.n = in.nextInt();
            t.m = in.nextInt();
            t.k = in.nextInt();
            assert ((1 <= t.n) && (t.n <= 5000));
            assert ((0 <= t.m) && (t.m <= 10000));
            assert ((0 <= t.k) && (t.k <= 10000));
            final int total = t.m + t.k;
            t.edges = new ArrayList<>(total);
            for (int i = 0; i < total; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                final int w = in.nextInt();
                assert ((1 <= x) && (x <= t.n));
                assert ((1 <= y) && (y <= t.n));
                assert ((0 <= w) && (w <= 10000));
                t.edges.add(new Edge(x - 1, y - 1, w));
            }
            tests.add(t);
        }
        return tests;
    }

    // cal: compute MST cost (Kruskal) or -1 if cannot connect
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            if (n <= 1) {
                out.add(0);
                continue;
            }
            final var edges = tc.edges;
            edges.sort(Comparator.comparingInt(e -> e.w));
            final var uf = new UnionFind(n);
            long sum = 0L;
            int used = 0;
            for (final Edge e : edges) {
                if (uf.union(e.u, e.v)) {
                    sum += e.w;
                    used++;
                    if (used == n - 1) break;
                }
            }
            if (used != n - 1) {
                out.add(-1);
            } else {
                out.add((int) sum);
            }
        }
        return out;
    }

    // output: print results (accept integers)
    public static void output(final Iterable<Integer> lines) {
        final var sb = new StringBuilder();
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
