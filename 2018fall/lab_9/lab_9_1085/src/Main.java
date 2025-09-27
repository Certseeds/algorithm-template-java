// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        final int n, m;
        final int[] from, to;
        public TestCase(final int n, final int m, final int[] from, final int[] to) {
            this.n = n;
            this.m = m;
            this.from = from;
            this.to = to;
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
            assert (1 <= n && n <= 20000);
            assert (0 <= m && m <= 50000);
            final int[] from = new int[m];
            final int[] to = new int[m];
            for (int i = 0; i < m; i++) {
                from[i] = in.nextInt();
                to[i] = in.nextInt();
            }
            tests.add(new TestCase(n, m, from, to));
        }
        return tests;
    }

    // cal: compute minimal edges to add to make graph strongly connected, or 0 if already
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(solve(tc));
        }
        return out;
    }

    private static int solve(final TestCase tc) {
        final int n = tc.n, m = tc.m;
        // Build adjacency (forward-star)
        final int[] head = new int[n + 1];
        Arrays.fill(head, -1);
        final int[] toE = new int[m];
        final int[] nextE = new int[m];
        for (int i = 0; i < m; i++) {
            final int u = tc.from[i];
            final int v = tc.to[i];
            toE[i] = v;
            nextE[i] = head[u];
            head[u] = i;
        }

        // Tarjan SCC
        final TarjanSCC tarjan = new TarjanSCC(n, head, toE, nextE);
        final int compCnt = tarjan.run();
        final int[] comp = tarjan.comp;

        if (compCnt == 1) return 0;

        // Build condensed DAG degrees
        final int[] indeg = new int[compCnt];
        final int[] outdeg = new int[compCnt];
        for (int i = 0; i < m; i++) {
            final int cu = comp[tc.from[i]];
            final int cv = comp[tc.to[i]];
            if (cu != cv) {
                outdeg[cu]++;
                indeg[cv]++;
            }
        }

        int sources = 0, sinks = 0;
        for (int i = 0; i < compCnt; i++) {
            if (indeg[i] == 0) sources++;
            if (outdeg[i] == 0) sinks++;
        }
        return Math.max(sources, sinks);
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

    // Tarjan SCC as a dedicated class to avoid capturing local variables
    private static final class TarjanSCC {
        final int n;
        final int[] head, toE, nextE;
        final int[] dfn, low, comp, stack;
        final boolean[] inStack;
        int time = 0, top = 0, compCnt = 0;

        TarjanSCC(final int n, final int[] head, final int[] toE, final int[] nextE) {
            this.n = n;
            this.head = head;
            this.toE = toE;
            this.nextE = nextE;
            this.dfn = new int[n + 1];
            this.low = new int[n + 1];
            this.comp = new int[n + 1];
            this.inStack = new boolean[n + 1];
            this.stack = new int[n];
        }

        int run() {
            for (int u = 1; u <= n; u++) {
                if (dfn[u] == 0) dfs(u);
            }
            return compCnt;
        }

        private void dfs(final int u) {
            dfn[u] = low[u] = ++time;
            stack[top++] = u;
            inStack[u] = true;
            for (int e = head[u]; e != -1; e = nextE[e]) {
                final int v = toE[e];
                if (dfn[v] == 0) {
                    dfs(v);
                    low[u] = Math.min(low[u], low[v]);
                } else if (inStack[v]) {
                    low[u] = Math.min(low[u], dfn[v]);
                }
            }
            if (low[u] == dfn[u]) {
                while (true) {
                    final int x = stack[--top];
                    inStack[x] = false;
                    comp[x] = compCnt;
                    if (x == u) break;
                }
                compCnt++;
            }
        }
    }
}