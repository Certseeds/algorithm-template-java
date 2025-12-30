// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {

    private static final class InputData {
        final int n;
        final int m;
        final int s;
        final Star graph;

        InputData(int n, int m, int s, Star graph) {
            this.n = n;
            this.m = m;
            this.s = s;
            this.graph = graph;
        }
    }

    private static final class TarjanState {
        final int[] dfn;
        final int[] low;
        final int[] stack;
        final boolean[] inStack;
        final int[] comp;
        int time;
        int top;
        int compCount;

        TarjanState(int n) {
            dfn = new int[n + 1];
            low = new int[n + 1];
            stack = new int[n + 1];
            inStack = new boolean[n + 1];
            comp = new int[n + 1];
            time = 0;
            top = 0;
            compCount = 0;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        final int s = input.nextInt();
        assert ((1 <= n) && (n <= 50000));
        assert ((0 <= m) && (m <= 50000));
        assert ((1 <= s) && (s <= n));

        final Star graph = new Star(n, m);

        for (int i = 0; i < m; i++) {
            final int u = input.nextInt();
            final int v = input.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            graph.addEdge(u, v);
        }

        return new InputData(n, m, s, graph);
    }

    public static int cal(InputData data) {
        final var tarjan = new TarjanState(data.n);
        for (int i = 1; i <= data.n; i++) {
            if (tarjan.dfn[i] == 0) {
                dfs(i, data.graph, tarjan);
            }
        }

        final int compS = tarjan.comp[data.s];
        final int compCount = tarjan.compCount;
        final int[] indegree = new int[compCount + 1];

        for (int u = 1; u <= data.n; u++) {
            for (int e = data.graph.head(u); e != -1; e = data.graph.next(e)) {
                final int v = data.graph.to(e);
                final int cu = tarjan.comp[u];
                final int cv = tarjan.comp[v];
                if (cu != cv) {
                    indegree[cv]++;
                }
            }
        }

        int zero = 0;
        for (int i = 1; i <= compCount; i++) {
            if (indegree[i] == 0) {
                zero++;
            }
        }

        if (indegree[compS] == 0) {
            zero--;
        }
        return zero;
    }

    private static void dfs(int u, Star graph, TarjanState state) {
        state.time++;
        state.dfn[u] = state.time;
        state.low[u] = state.time;
        state.stack[state.top++] = u;
        state.inStack[u] = true;

        for (int e = graph.head(u); e != -1; e = graph.next(e)) {
            final int v = graph.to(e);
            if (state.dfn[v] == 0) {
                dfs(v, graph, state);
                state.low[u] = Math.min(state.low[u], state.low[v]);
            } else if (state.inStack[v]) {
                state.low[u] = Math.min(state.low[u], state.dfn[v]);
            }
        }

        if (state.low[u] == state.dfn[u]) {
            state.compCount++;
            while (true) {
                final int v = state.stack[--state.top];
                state.inStack[v] = false;
                state.comp[v] = state.compCount;
                if (v == u) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final int result = cal(data);
        output(result);
    }

    public static void output(int number) {
        System.out.print(number);
        System.out.print('\n');
    }

    private static final class Star {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] next;
        private int edgeIndex;

        Star(int nodeCount, int maxEdges) {
            assert (nodeCount > 0);
            assert (maxEdges >= 0);
            this.nodeCount = nodeCount;
            this.head = new int[nodeCount + 1];
            Arrays.fill(this.head, -1);
            this.to = new int[maxEdges];
            this.next = new int[maxEdges];
            this.edgeIndex = 0;
        }

        void addEdge(int from, int toNode) {
            assert ((1 <= from) && (from <= nodeCount));
            assert ((1 <= toNode) && (toNode <= nodeCount));
            assert (edgeIndex < to.length);
            final int idx = edgeIndex;
            to[idx] = toNode;
            next[idx] = head[from];
            head[from] = idx;
            edgeIndex++;
        }

        int head(int node) {
            assert ((1 <= node) && (node <= nodeCount));
            return head[node];
        }

        int to(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return to[edgeIdx];
        }

        int next(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return next[edgeIdx];
        }
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

    }
}
