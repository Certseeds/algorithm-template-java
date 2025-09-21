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
        public final int[][] edges;
        public final int Q;
        public final int[][] queries;

        public TestCase(int n, int m, int[][] edges, int Q, int[][] queries) {
            this.n = n;
            this.m = m;
            this.edges = edges;
            this.Q = Q;
            this.queries = queries;
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
            assert ((1 <= n) && (n <= 1000));
            assert ((0 <= m) && (m <= Math.min(n * n, 100000)));
            final int[][] edges = new int[m][2];
            for (int i = 0; i < m; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                edges[i][0] = x - 1;
                edges[i][1] = y - 1;
            }
            final int Q = in.nextInt();
            assert ((1 <= Q) && (Q <= 500));
            final int[][] queries = new int[Q][2];
            for (int i = 0; i < Q; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                queries[i][0] = x - 1;
                queries[i][1] = y - 1;
            }
            tests.add(new TestCase(n, m, edges, Q, queries));
        }
        return tests;
    }

    // cal: compute reachability using BitSet transitive closure
    public static List<String> cal_old(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final BitSet[] reach = new BitSet[n];
            {
                for (int i = 0; i < n; i++) {
                    reach[i] = new BitSet(n);
                    // node can reach itself (path of length 0)
                    reach[i].set(i);
                }
                // set direct edges
                for (int i = 0; i < tc.m; i++) {
                    final int u = tc.edges[i][0];
                    final int v = tc.edges[i][1];
                    if (u >= 0 && u < n && v >= 0 && v < n) reach[u].set(v);
                }
                // warshall-like with bitsets: for k in 0..n-1, for i if reach[i][k] then reach[i] |= reach[k]
                for (int k = 0; k < n; k++) {
                    for (int i = 0; i < n; i++) {
                        if (reach[i].get(k)) {
                            reach[i].or(reach[k]);
                        }
                    }
                }
                // answer queries
                for (int i = 0; i < tc.Q; i++) {
                    final int u = tc.queries[i][0];
                    final int v = tc.queries[i][1];
                    if (u >= 0 && u < n && v >= 0 && v < n && reach[u].get(v))
                        out.add("YES");
                    else out.add("NO");
                }
            }
        }
        return out;
    }

    // cal: compute reachability using SCC condensation + BitSet transitive closure
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            // build adjacency list
            final ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < tc.m; i++) {
                final int u = tc.edges[i][0];
                final int v = tc.edges[i][1];
                if (u >= 0 && u < n && v >= 0 && v < n) adj[u].add(v);
            }

            // Tarjan's SCC
            final int[] dfn = new int[n];
            final int[] low = new int[n];
            Arrays.fill(dfn, -1);
            final boolean[] inStack = new boolean[n];
            final int[] compId = new int[n];
            Arrays.fill(compId, -1);
            final Deque<Integer> stack = new ArrayDeque<>();
            final int[] time = new int[1]; // wrapper for mutable int
            final int[] compCnt = new int[1];

            // recursive tarjan
            final class Tarjan {
                void dfs(int u) {
                    dfn[u] = low[u] = ++time[0];
                    stack.push(u);
                    inStack[u] = true;
                    for (int v : adj[u]) {
                        if (dfn[v] == -1) {
                            dfs(v);
                            low[u] = Math.min(low[u], low[v]);
                        } else if (inStack[v]) {
                            low[u] = Math.min(low[u], dfn[v]);
                        }
                    }
                    if (low[u] == dfn[u]) {
                        while (true) {
                            final int x = stack.pop();
                            inStack[x] = false;
                            compId[x] = compCnt[0];
                            if (x == u) break;
                        }
                        compCnt[0]++;
                    }
                }
            }
            final Tarjan tarjan = new Tarjan();
            for (int i = 0; i < n; i++) {
                if (dfn[i] == -1) {
                    tarjan.dfs(i);
                }
            }

            final int C = compCnt[0];
            // build component DAG adjacency (BitSet to avoid duplicates)
            final BitSet[] compAdj = new BitSet[C];
            for (int i = 0; i < C; i++) compAdj[i] = new BitSet(C);
            for (int u = 0; u < n; u++) {
                final int cu = compId[u];
                for (int v : adj[u]) {
                    final int cv = compId[v];
                    if (cu != cv) {
                        compAdj[cu].set(cv);
                    }
                }
            }

            // compute transitive closure on component DAG
            final BitSet[] reachComp = new BitSet[C];
            for (int i = 0; i < C; i++) {
                reachComp[i] = new BitSet(C);
                reachComp[i].set(i);
                reachComp[i].or(compAdj[i]);
            }
            for (int k = 0; k < C; k++) {
                for (int i = 0; i < C; i++) {
                    if (reachComp[i].get(k)) {
                        reachComp[i].or(reachComp[k]);
                    }
                }
            }

            // answer queries by mapping nodes to components
            for (int i = 0; i < tc.Q; i++) {
                final int u = tc.queries[i][0];
                final int v = tc.queries[i][1];
                if (u >= 0 && u < n && v >= 0 && v < n) {
                    final int cu = compId[u];
                    final int cv = compId[v];
                    if (reachComp[cu].get(cv)) {
                        out.add("YES");
                    } else {
                        out.add("NO");
                    }
                } else {
                    out.add("NO");
                }
            }
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
