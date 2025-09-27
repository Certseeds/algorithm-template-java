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
        final int[][] edges; // each: {u, v, w}
        final int s;
        final int t;

        TestCase(final int n, final int m, final int[][] edges, final int s, final int t) {
            this.n = n;
            this.m = m;
            this.edges = edges;
            this.s = s;
            this.t = t;
        }
    }

    // forward-star (链式前向星) 封装为类
    private static final class ForwardStar {
        final int n;
        final int maxE;
        final int[] head;
        final int[] to;
        final int[] wt;
        final int[] next;
        int ec = 0;

        ForwardStar(final int n, final int m) {
            this.n = n;
            this.maxE = m * 2 + 5;
            this.head = new int[n + 1];
            this.to = new int[this.maxE];
            this.wt = new int[this.maxE];
            this.next = new int[this.maxE];
            Arrays.fill(this.head, -1);
        }

        void addEdge(final int u, final int v, final int w) {
            ec++;
            to[ec] = v;
            wt[ec] = w;
            next[ec] = head[u];
            head[u] = ec;
        }

        void addUndirected(final int u, final int v, final int w) {
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 50));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            assert ((1 <= n) && (n <= 6000));
            assert ((0 <= m) && (m <= 100000));
            final var edges = new int[m][3];
            for (int i = 0; i < m; i++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                final int w = in.nextInt();
                assert ((1 <= u) && (u <= n));
                assert ((1 <= v) && (v <= n));
                assert (u != v);
                assert ((1 <= w) && (w <= 100000));
                edges[i][0] = u;
                edges[i][1] = v;
                edges[i][2] = w;
            }
            final int s = in.nextInt();
            final int t = in.nextInt();
            assert ((1 <= s) && (s <= n));
            assert ((1 <= t) && (t <= n));
            tests.add(new TestCase(n, m, edges, s, t));
        }
        return tests;
    }

    // cal: compute minimal meeting time for each test case
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            // build undirected weighted graph using forward-star (链式前向星) wrapped in ForwardStar
            final ForwardStar g = new ForwardStar(n, m);
            for (int i = 0; i < m; i++) {
                final int u = tc.edges[i][0];
                final int v = tc.edges[i][1];
                final int w = tc.edges[i][2];
                g.addUndirected(u, v, w);
            }
            final long[] ds = dijkstra(n, g, tc.s);
            final long[] dt = dijkstra(n, g, tc.t);
            long best = Long.MAX_VALUE / 4;
            for (int i = 1; i <= n; i++) {
                final long a = ds[i];
                final long b = dt[i];
                if (a >= Long.MAX_VALUE / 4 || b >= Long.MAX_VALUE / 4) continue; // unreachable
                final long mx = Math.max(a, b);
                if (mx < best) best = mx;
            }
            // problem guarantees Bob can always meet Alice, but be safe
            if (best >= Long.MAX_VALUE / 4) out.add(-1);
            else out.add((int) best);
        }
        return out;
    }

    private static long[] dijkstra(final int n, final ForwardStar g, final int src) {
        final long INF = Long.MAX_VALUE / 4;
        final long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[src] = 0L;
        final var pq = new PriorityQueue<long[]>(Comparator.comparingLong(x -> x[0])); // {dist, node}
        pq.add(new long[]{0L, src});
        while (!pq.isEmpty()) {
            final var cur = pq.poll();
            final long d = cur[0];
            final int u = (int) cur[1];
            if (d != dist[u]) continue;
            for (int e = g.head[u]; e != -1; e = g.next[e]) {
                final int v = g.to[e];
                final int w = g.wt[e];
                final long nd = d + (long) w;
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.add(new long[]{nd, v});
                }
            }
        }
        return dist;
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
}
