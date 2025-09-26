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
        final int[] xs;
        final int[] ys;
        final List<int[]> sets; // each: {cost, k, v1, v2, ...} but store as [cost, v...] variable len encoded differently

        TestCase(final int n, final int m, final int[] xs, final int[] ys, final List<int[]> sets) {
            this.n = n;
            this.m = m;
            this.xs = xs;
            this.ys = ys;
            this.sets = sets;
        }
    }

    private static final class Edge implements Comparable<Edge> {
        final int u, v;
        final long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(final Edge o) {
            return Long.compare(this.w, o.w);
        }
    }

    private static final class SetAction implements Comparable<SetAction> {
        final long cost;
        final int[] verts;

        SetAction(long cost, int[] verts) {
            this.cost = cost;
            this.verts = verts;
        }

        @Override
        public int compareTo(final SetAction o) {
            return Long.compare(this.cost, o.cost);
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
            final int M = in.nextInt();
            assert ((1 <= N) && (N <= 500));
            assert ((0 <= M) && (M <= 8));
            final int[] xs = new int[N + 1];
            final int[] ys = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                final int xi = in.nextInt();
                final int yi = in.nextInt();
                assert (0 <= xi && xi <= 1000);
                assert (0 <= yi && yi <= 1000);
                xs[i] = xi;
                ys[i] = yi;
            }
            final List<int[]> sets = new ArrayList<>(M);
            for (int i = 0; i < M; i++) {
                final int num = in.nextInt();
                final int cost = in.nextInt();
                assert (0 <= num && num <= N);
                assert (1 <= cost && cost <= 1_000_000);
                final int[] verts = new int[num];
                for (int j = 0; j < num; j++) verts[j] = in.nextInt();
                // store as array with cost first? We'll keep separate structure later; here push as [cost, num, v...]
                final int[] arr = new int[1 + num];
                arr[0] = cost;
                System.arraycopy(verts, 0, arr, 1, num);
                sets.add(arr);
            }
            tests.add(new TestCase(N, M, xs, ys, sets));
        }
        return tests;
    }

    // union-find
    private static final class UF {
        final int[] p;
        final int[] r;

        UF(int n) {
            p = new int[n + 1];
            r = new int[n + 1];
            for (int i = 1; i <= n; i++) p[i] = i;
        }

        int find(int a) {
            while (p[a] != a) {
                p[a] = p[p[a]];
                a = p[a];
            }
            return a;
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (r[ra] < r[rb]) p[ra] = rb;
            else if (r[ra] > r[rb]) p[rb] = ra;
            else {
                p[rb] = ra;
                r[ra]++;
            }
            return true;
        }
    }

    // cal: compute minimal cost to connect points with optional sets
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            final int N = tc.n;
            // build all pairwise edges once; pack into primitive long to speed up sorting and iteration
            final int E = N * (N - 1) / 2;
            final long[] packed = new long[E];
            int pi = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = i + 1; j <= N; j++) {
                    final long dx = tc.xs[i] - tc.xs[j];
                    final long dy = tc.ys[i] - tc.ys[j];
                    final long w = dx * dx + dy * dy; // squared Euclidean distance
                    // pack: high bits weight, low bits u,v (10 bits each)
                    final long key = (w << 20) | ((long) i << 10) | (long) j;
                    packed[pi++] = key;
                }
            }
            Arrays.sort(packed);
            // unpack once into parallel arrays to avoid bit ops in hot loop
            final long[] wArr = new long[packed.length];
            final int[] uArr = new int[packed.length];
            final int[] vArr = new int[packed.length];
            for (int i = 0; i < packed.length; i++) {
                final long key = packed[i];
                wArr[i] = key >>> 20;
                uArr[i] = (int) ((key >>> 10) & 0x3FFL);
                vArr[i] = (int) (key & 0x3FFL);
            }

            // prepare sets array
            final var sets = new ArrayList<SetAction>(tc.sets.size());
            for (final int[] arr : tc.sets) {
                final long cost = arr.length == 0 ? 0L : (long) arr[0];
                final int num = arr.length - 1;
                if (num <= 0) {
                    sets.add(new SetAction(cost, new int[0]));
                    continue;
                }
                final int[] verts = new int[num];
                System.arraycopy(arr, 1, verts, 0, num);
                sets.add(new SetAction(cost, verts));
            }

            final int K = sets.size();
            long best = Long.MAX_VALUE;
            final int maxMask = 1 << K;
            // enumerate subsets of sets (K <= 8)
            for (int mask = 0; mask < maxMask; mask++) {
                final UF ufMask = new UF(N);
                long total = 0L;
                int comps = N; // initially each node is its own component
                // apply chosen sets
                for (int b = 0; b < K; b++) {
                    if ((mask & (1 << b)) == 0) continue;
                    final SetAction sa = sets.get(b);
                    total += sa.cost;
                    final int[] verts = sa.verts;
                    if (verts.length == 0) continue;
                    int base = verts[0];
                    for (int t = 1; t < verts.length; t++) if (ufMask.union(base, verts[t])) comps--;
                }
                // pruning: if already not better than best, skip
                if (total >= best) continue;

                if (comps == 1) { // already connected
                    if (total < best) best = total;
                    continue;
                }
                final int needed = comps - 1;

                // Kruskal over unpacked arrays, stop after needed unions
                int unions = 0; // count of successful unions
                for (int idx = 0; idx < wArr.length; idx++) {
                    final long w = wArr[idx];
                    final int u = uArr[idx];
                    final int v = vArr[idx];
                    if (ufMask.union(u, v)) {
                        total += w;
                        unions++;
                        if (unions == needed) break;
                        if (total >= best) break; // early stop if already worse
                    }
                }
                if (unions < needed) continue; // not connected
                if (total < best) best = total;
            }
            out.add((int) best);
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
