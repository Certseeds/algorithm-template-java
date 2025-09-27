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
        final int k;
        final int[][] edges; // m x 3: u,v,w
        final int[] must; // length k

        TestCase(final int n, final int m, final int k, final int[][] edges, final int[] must) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.edges = edges;
            this.must = must;
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
        assert ((1 <= T) && (T <= 10));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            final int k = in.nextInt();
            assert ((1 <= n) && (n <= 500));
            assert ((n <= m) && (m <= n * n));
            assert ((1 <= k) && (k <= 5));
            final var edges = new int[m][3];
            for (int i = 0; i < m; i++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                final int w = in.nextInt();
                assert ((1 <= u) && (u <= n));
                assert ((1 <= v) && (v <= n));
                assert ((1 <= w) && (w <= 1000));
                edges[i][0] = u;
                edges[i][1] = v;
                edges[i][2] = w;
            }
            final var must = new int[k];
            for (int i = 0; i < k; i++) {
                final int x = in.nextInt();
                assert ((1 <= x) && (x <= n));
                must[i] = x;
            }
            tests.add(new TestCase(n, m, k, edges, must));
        }
        return tests;
    }

    // cal: compute minimal path cost for each test case
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            // build undirected weighted graph using ForwardStar wrapper
            final ForwardStar g = new ForwardStar(n, m);
            for (int i = 0; i < m; i++) {
                final int u = tc.edges[i][0];
                final int v = tc.edges[i][1];
                final int w = tc.edges[i][2];
                g.addUndirected(u, v, w);
            }

            // prepare special nodes: start=1, required (excluding 1 and n)
            final int start = 1;
            final var requiredList = new ArrayList<Integer>();
            for (final int x : tc.must) {
                if (x == start || x == n) continue; // already covered
                if (!requiredList.contains(x)) requiredList.add(x);
            }
            final int rk = requiredList.size();

            // special nodes array: index 0 = start, 1..rk = required, last = end
            final int specialCount = 2 + rk;
            final int[] special = new int[specialCount];
            special[0] = start;
            for (int i = 0; i < rk; i++) special[1 + i] = requiredList.get(i);
            special[specialCount - 1] = n;

            // run Dijkstra from each special node
            final long INF = Long.MAX_VALUE / 4;
            final long[][] distMat = new long[specialCount][specialCount];
            final long[][] fromDist = new long[specialCount][]; // distances to all nodes
            for (int i = 0; i < specialCount; i++) {
                fromDist[i] = dijkstra(n, g, special[i]);
            }
            for (int i = 0; i < specialCount; i++) {
                for (int j = 0; j < specialCount; j++) {
                    final long d = fromDist[i][special[j]];
                    distMat[i][j] = d;
                }
            }

            // enumerate permutations of required indices (1..rk) to compute path cost 0->perm->last
            final var perm = new int[rk];
            for (int i = 0; i < rk; i++) perm[i] = i + 1; // indices into special
            long best = INF;
            if (rk == 0) {
                final long d = distMat[0][1]; // specialCount==2, special[1]=end
                if (d < INF) best = d;
            } else {
                // permute
                do {
                    long sum = 0L;
                    long d0 = distMat[0][perm[0]];
                    if (d0 >= INF) continue;
                    sum += d0;
                    boolean ok = true;
                    for (int i = 0; i < rk - 1; i++) {
                        final long d = distMat[perm[i]][perm[i + 1]];
                        if (d >= INF) {
                            ok = false;
                            break;
                        }
                        sum += d;
                    }
                    if (!ok) continue;
                    final long dl = distMat[perm[rk - 1]][specialCount - 1];
                    if (dl >= INF) continue;
                    sum += dl;
                    if (sum < best) best = sum;
                } while (nextPermutation(perm));
            }

            if (best >= INF) out.add(-1);
            else out.add((int) best);
        }
        return out;
    }

    private static boolean nextPermutation(final int[] a) {
        // lexicographic next permutation for array of ints
        int n = a.length;
        int i = n - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;
        if (i < 0) return false;
        int j = n - 1;
        while (a[j] <= a[i]) j--;
        swap(a, i, j);
        reverse(a, i + 1, n - 1);
        return true;
    }

    private static void swap(final int[] a, final int i, final int j) {
        final int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void reverse(final int[] a, int l, int r) {
        while (l < r) {
            swap(a, l++, r--);
        }
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
