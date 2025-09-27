// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        int n, m;
        int[] U, V;
        long[] W;
        TestCase(final int n, final int m) {
            this.n = n; this.m = m;
            this.U = new int[m];
            this.V = new int[m];
            this.W = new long[m];
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
            final var t = new TestCase(n, m);
            for (int i = 0; i < m; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                final long z = in.nextInt();
                t.U[i] = x; t.V[i] = y; t.W[i] = z;
            }
            tests.add(t);
        }
        return tests;
    }

    // cal: compute sum of all-pair min-cuts in cactus graph
    public static List<Long> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Long>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(solveOne(tc));
        }
        return out;
    }

    // output: print results (accept longs)
    public static void output(final Iterable<Long> lines) {
        final var sb = new StringBuilder();
        for (final Long v : lines) {
            sb.append(v).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    private static long solveOne(final TestCase tc) {
        final int n = tc.n, m = tc.m;

        // build adjacency: store neighbor and edge-id
        final ArrayList<int[]>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            final int u = tc.U[i], v = tc.V[i];
            adj[u].add(new int[]{v, i});
            adj[v].add(new int[]{u, i});
        }

        // 1) find bridges via DFS (recursive; safe in most cases; could be replaced by iterative if needed)
        final boolean[] isBridge = new boolean[m];
        final int[] tin = new int[n + 1];
        final int[] low = new int[n + 1];
        int timer = 1;
        for (int s = 1; s <= n; s++) {
            if (tin[s] != 0) continue;
            timer = dfsBridge(s, -1, timer, adj, tin, low, isBridge);
        }

        // 2) components in the non-bridge subgraph (each is a simple cycle or singleton)
        final int[] compId = new int[n + 1];
        Arrays.fill(compId, -1);
        final ArrayList<ArrayList<Integer>> comps = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (compId[i] != -1) continue;
            final ArrayDeque<Integer> q = new ArrayDeque<>();
            q.add(i);
            compId[i] = comps.size();
            final ArrayList<Integer> lst = new ArrayList<>();
            lst.add(i);
            while (!q.isEmpty()) {
                final int u = q.poll();
                for (final int[] e : adj[u]) {
                    final int v = e[0], id = e[1];
                    if (isBridge[id]) continue;
                    if (compId[v] == -1) {
                        compId[v] = compId[u];
                        q.add(v);
                        lst.add(v);
                    }
                }
            }
            comps.add(lst);
        }

        final int compCnt = comps.size();
        final int[] compSize = new int[compCnt];
        for (int cid = 0; cid < compCnt; cid++) compSize[cid] = comps.get(cid).size();

        long ans = 0L;

        // 3) cycle components contribution
        for (int cid = 0; cid < compCnt; cid++) {
            final ArrayList<Integer> vs = comps.get(cid);
            if (vs.size() < 3) continue; // singleton or not a cycle
            // ensure it's a simple cycle (degree 2 in non-bridge subgraph)
            boolean isCycle = true;
            for (final int u : vs) {
                int deg = 0;
                for (final int[] e : adj[u]) if (!isBridge[e[1]]) deg++;
                if (deg != 2) { isCycle = false; break; }
            }
            if (!isCycle) continue;

            final long[] cycW = buildCycleWeights(vs.get(0), adj, isBridge, tc.W);
            if (cycW == null || cycW.length < 3) continue;

            ans += sumCycleOrderedMinimaUpToKMinus1(cycW); // equals unordered pair sum of two arc minima
        }

        // 4) bridge-tree contribution via DSU in descending weights
        final ArrayList<BridgeEdge> bridges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (!isBridge[i]) continue;
            final int cu = compId[tc.U[i]];
            final int cv = compId[tc.V[i]];
            bridges.add(new BridgeEdge(cu, cv, tc.W[i]));
        }
        bridges.sort((a, b) -> Long.compare(b.w, a.w)); // descending

        final DSU dsu = new DSU(compCnt);
        final long[] size = new long[compCnt];
        for (int i = 0; i < compCnt; i++) size[i] = compSize[i];

        for (final BridgeEdge be : bridges) {
            final int ru = dsu.find(be.u);
            final int rv = dsu.find(be.v);
            if (ru == rv) continue;
            ans += be.w * size[ru] * size[rv];
            final int nr = dsu.union(ru, rv);
            size[nr] = size[ru] + size[rv];
        }

        return ans;
    }

    private static int dfsBridge(final int u, final int pe, int timer,
                                 final ArrayList<int[]>[] adj, final int[] tin,
                                 final int[] low, final boolean[] isBridge) {
        tin[u] = low[u] = timer++;
        for (final int[] e : adj[u]) {
            final int v = e[0], id = e[1];
            if (id == pe) continue;
            if (tin[v] == 0) {
                timer = dfsBridge(v, id, timer, adj, tin, low, isBridge);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > tin[u]) isBridge[id] = true;
            } else {
                low[u] = Math.min(low[u], tin[v]);
            }
        }
        return timer;
    }

    // Traverse the cycle and collect edge weights in order
    private static long[] buildCycleWeights(final int start,
                                            final ArrayList<int[]>[] adj,
                                            final boolean[] isBridge,
                                            final long[] edgeW) {
        int prev = -1;
        int curr = start;
        final ArrayList<Long> ws = new ArrayList<>();
        // check degree 2 at start in non-bridge subgraph
        int deg = 0;
        for (final int[] e : adj[start]) if (!isBridge[e[1]]) deg++;
        if (deg != 2) return null;

        while (true) {
            int next = -1, eid = -1, cnt = 0;
            for (final int[] e : adj[curr]) {
                final int v = e[0], id = e[1];
                if (isBridge[id]) continue;
                cnt++;
                if (v != prev) { next = v; eid = id; }
                // if v == prev, keep as fallback in case the other edge missing (shouldn't happen in cactus)
                if (next == -1) { next = v; eid = id; }
            }
            if (cnt != 2) return null; // not a simple cycle
            ws.add(edgeW[eid]);
            prev = curr;
            curr = next;
            if (curr == start) break;
        }
        final long[] arr = new long[ws.size()];
        for (int i = 0; i < ws.size(); i++) arr[i] = ws.get(i);
        return arr;
    }

    // Sum of minima over all circular subarrays (ordered arcs) of lengths 1..k-1
    private static long sumCycleOrderedMinimaUpToKMinus1(final long[] w) {
        final int k = w.length;
        final int N = 2 * k;
        final long[] W2 = new long[N];
        for (int i = 0; i < N; i++) W2[i] = w[i % k];

        // prev strictly less, next less-or-equal in doubled array
        final int[] prevLess = new int[N];
        final int[] nextLE = new int[N];

        final ArrayDeque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            while (!st.isEmpty() && W2[st.peek()] >= W2[i]) st.pop();
            prevLess[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }
        st.clear();
        for (int i = N - 1; i >= 0; i--) {
            while (!st.isEmpty() && W2[st.peek()] > W2[i]) st.pop();
            nextLE[i] = st.isEmpty() ? N : st.peek();
            st.push(i);
        }

        long resUpToK = 0L;
        final int K = k;

        for (int i = 0; i < N; i++) {
            final int B = nextLE[i] - i; // right choices length
            // allowable window starts s are in [0..k-1]
            final int RB = Math.min(i, k - 1);
            final int LB = Math.max(Math.max(prevLess[i] + 1, i - (K - 1)), 0);
            if (LB > RB) continue;

            final int d0 = i - RB;           // smallest d = i - s
            final int x = RB - LB + 1;       // number of s (and d) values
            final int P = K - B;             // boundary where min(B, K - d) switches
            int p = P - d0 + 1;
            if (p < 0) p = 0;
            if (p > x) p = x;
            final int m = x - p;

            long sum = 0L;
            sum += (long) p * B;
            sum += (long) m * K;
            sum -= (long) m * (d0 + p);
            sum -= (long) m * (m - 1) / 2;

            resUpToK += W2[i] * sum;
        }

        // subtract length-k arcs: exactly k arcs, each minimum is the global minimum edge on the cycle
        long minW = Long.MAX_VALUE;
        for (int i = 0; i < k; i++) minW = Math.min(minW, w[i]);

        return resUpToK - minW * k;
    }

    private static final class BridgeEdge {
        int u, v; long w;
        BridgeEdge(final int u, final int v, final long w) { this.u = u; this.v = v; this.w = w; }
    }

    private static final class DSU {
        int[] p, r;
        DSU(final int n) { p = new int[n]; r = new int[n]; for (int i = 0; i < n; i++) p[i] = i; }
        int find(final int x) { return p[x] == x ? x : (p[x] = find(p[x])); }
        int union(final int a, final int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return ra;
            if (r[ra] < r[rb]) { p[ra] = rb; return rb; }
            else if (r[ra] > r[rb]) { p[rb] = ra; return ra; }
            else { p[rb] = ra; r[ra]++; return ra; }
        }
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