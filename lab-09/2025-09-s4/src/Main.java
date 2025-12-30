// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    private static final class Edge implements Comparable<Edge> {
        final int u;
        final int v;
        final long weight;

        Edge(int u, int v, long weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Long.compare(this.weight, other.weight);
        }
    }

    private static final class InputData {
        final int n;
        final int m;
        final List<Edge> edges;

        InputData(int n, int m, List<Edge> edges) {
            this.n = n;
            this.m = m;
            this.edges = edges;
        }
    }

    private static final class UnionFind {
        private final int[] parent;
        private final int[] rank;

        UnionFind(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            final int px = find(x);
            final int py = find(y);
            if (px == py) {
                return false;
            }
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        assert ((1 <= n) && (n <= 200000));
        assert ((n - 1 <= m) && (m <= 200000));

        final List<Edge> edges = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            final int u = input.nextInt();
            final int v = input.nextInt();
            final long w = input.nextLong();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            assert ((-1000000000L <= w) && (w <= 1000000000L));
            edges.add(new Edge(u, v, w));
        }
        return new InputData(n, m, edges);
    }

    public static long cal(InputData data) {
        long totalWeight = 0;
        long keptWeight = 0;
        final UnionFind uf = new UnionFind(data.n);
        final List<Edge> nonNegative = new ArrayList<>();

        for (final var edge : data.edges) {
            totalWeight += edge.weight;
            if (edge.weight < 0) {
                keptWeight += edge.weight;
                uf.union(edge.u, edge.v);
            } else {
                nonNegative.add(edge);
            }
        }

        Collections.sort(nonNegative);
        for (final var edge : nonNegative) {
            if (uf.union(edge.u, edge.v)) {
                keptWeight += edge.weight;
            }
        }

        return totalWeight - keptWeight;
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final long result = cal(data);
        output(result);
    }

    public static void output(long number) {
        System.out.print(number);
        System.out.print('\n');
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

    }
}
