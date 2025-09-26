// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        final int n;
        final int[] xs;
        final int[] ys;
        final int[] zs;
        final int[] rs;
        final int[] s; // length 3
        final int[] t; // length 3

        TestCase(final int n, final int[] xs, final int[] ys, final int[] zs, final int[] rs, final int[] s, final int[] t) {
            this.n = n;
            this.xs = xs;
            this.ys = ys;
            this.zs = zs;
            this.rs = rs;
            this.s = s;
            this.t = t;
        }
    }

    // forward-star (链式前向星) 封装为类，支持 double 权重
    private static final class ForwardStar {
        final int n;
        final int maxE;
        final int[] head;
        final int[] to;
        final double[] wt;
        final int[] next;
        int ec = 0;

        ForwardStar(final int n, final int expectedDirectedEdges) {
            this.n = n;
            this.maxE = expectedDirectedEdges + 5;
            this.head = new int[n];
            this.to = new int[this.maxE];
            this.wt = new double[this.maxE];
            this.next = new int[this.maxE];
            Arrays.fill(this.head, -1);
        }

        void addEdge(final int u, final int v, final double w) {
            ec++; to[ec] = v; wt[ec] = w; next[ec] = head[u]; head[u] = ec;
        }

        void addUndirected(final int u, final int v, final double w) {
            addEdge(u, v, w);
            addEdge(v, u, w);
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int N = in.nextInt();
            assert ((1 <= N) && (N <= 100));
            final int[] xs = new int[N + 2];
            final int[] ys = new int[N + 2];
            final int[] zs = new int[N + 2];
            final int[] rs = new int[N + 2];
            // holes indexed 1..N
            for (int i = 1; i <= N; i++) {
                final int xi = in.nextInt();
                final int yi = in.nextInt();
                final int zi = in.nextInt();
                final int ri = in.nextInt();
                assert ((Math.abs(xi) > 0 ? Math.abs(xi) : 1) <= 1000);
                assert ((Math.abs(yi) > 0 ? Math.abs(yi) : 1) <= 1000);
                assert ((Math.abs(zi) > 0 ? Math.abs(zi) : 1) <= 1000);
                assert ((1 <= ri) && (ri <= 500));
                xs[i] = xi;
                ys[i] = yi;
                zs[i] = zi;
                rs[i] = ri;
            }
            // read S
            final int sx = in.nextInt();
            final int sy = in.nextInt();
            final int sz = in.nextInt();
            // read T
            final int tx = in.nextInt();
            final int ty = in.nextInt();
            final int tz = in.nextInt();
            // index 0 for S, N+1 for T, radii 0
            xs[0] = sx; ys[0] = sy; zs[0] = sz; rs[0] = 0;
            xs[N + 1] = tx; ys[N + 1] = ty; zs[N + 1] = tz; rs[N + 1] = 0;
            final int[] s = new int[]{sx, sy, sz};
            final int[] t = new int[]{tx, ty, tz};
            tests.add(new TestCase(N, xs, ys, zs, rs, s, t));
        }
        return tests;
    }

    // cal: compute minimal path cost for each test case using ForwardStar
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            final int N = tc.n;
            final int total = N + 2; // nodes 0..N+1

            // build complete undirected graph among total nodes using ForwardStar
            final int expectedDirectedEdges = total * (total - 1); // fully connected directed
            final ForwardStar g = new ForwardStar(total, expectedDirectedEdges);
            for (int i = 0; i < total; i++) {
                for (int j = i + 1; j < total; j++) {
                    final double dx = (double) tc.xs[i] - (double) tc.xs[j];
                    final double dy = (double) tc.ys[i] - (double) tc.ys[j];
                    final double dz = (double) tc.zs[i] - (double) tc.zs[j];
                    final double eu = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    final double gap = Math.max(0.0, eu - (double) tc.rs[i] - (double) tc.rs[j]);
                    final double w = gap * 100.0;
                    g.addUndirected(i, j, w);
                }
            }

            // run Dijkstra from node 0 to node N+1
            final double INF = Double.POSITIVE_INFINITY;
            final double[] dist = new double[total];
            Arrays.fill(dist, INF);
            dist[0] = 0.0;
            final var pq = new PriorityQueue<double[]>(Comparator.comparingDouble(a -> a[0])); // {dist, node}
            pq.add(new double[]{0.0, 0.0});
            while (!pq.isEmpty()) {
                final var cur = pq.poll();
                final double d = cur[0];
                final int u = (int) cur[1];
                if (d != dist[u]) continue;
                if (u == total - 1) break;
                for (int e = g.head[u]; e != -1; e = g.next[e]) {
                    final int v = g.to[e];
                    final double w = g.wt[e];
                    final double nd = d + w;
                    if (nd + 1e-12 < dist[v]) {
                        dist[v] = nd;
                        pq.add(new double[]{nd, (double) v});
                    }
                }
            }
            final long ans = Math.round(dist[total - 1]);
            out.add((int) ans);
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
}
