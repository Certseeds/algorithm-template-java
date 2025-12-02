// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final var result = cal(data);
        output(result);
    }

    private static Data reader() throws IOException {
        final var rd = new Reader();
        final int n = rd.nextInt();
        assert ((2 <= n) && (n <= 400000));
        // build adj - forward star
        final ForwardStar graph = new ForwardStar(n, (n - 1) * 2 + 5);
        for (int i = 0; i < n - 1; i++) {
            final int u = rd.nextInt();
            final int v = rd.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            graph.addEdge(u, v);
            graph.addEdge(v, u);
        }
        final boolean[] hasGiant = new boolean[n + 1];
        final int m = rd.nextInt();
        assert ((0 <= m) && (m <= n - 1));
        for (int i = 0; i < m; i++) {
            final int k = rd.nextInt();
            assert ((1 <= k) && (k <= n));
            hasGiant[k] = true;
        }
        return new Data(n, graph, hasGiant);
    }

    private static int cal(Data data) {
        int ans = 0;
        final int n = data.n;
        final ForwardStar graph = data.graph;
        final boolean[] hasGiant = data.hasGiant;
        final boolean[] visited = new boolean[n + 1];
        final int[] dist = new int[n + 1];
        // we mark 1 visited so that bfs from its neighbors only traverse their subtree
        if (n >= 1) {
            visited[1] = true;
        }
        for (int edge = graph.head(1); edge != -1; edge = graph.next(edge)) {
            final int nb = graph.to(edge);
            if (visited[nb]) {
                continue;
            }
            final int cur = bfsOnce(graph, visited, hasGiant, dist, nb);
            ans = Math.max(ans, cur);
        }
        return ans;
    }

    private static int bfsOnce(final ForwardStar graph, final boolean[] visited, final boolean[] hasGiant,
                               final int[] dist, final int root) {
        final Deque<Integer> q = new ArrayDeque<>();
        final List<Integer> ds = new ArrayList<>();

        visited[root] = true;
        dist[root] = 1; // distance (days) to city 1
        q.add(root);

        while (!q.isEmpty()) {
            final int u = q.poll();
            if (hasGiant[u]) {
                ds.add(dist[u]);
            }
            for (int e = graph.head(u); e != -1; e = graph.next(e)) {
                final int v = graph.to(e);
                if (!visited[v]) {
                    visited[v] = true;
                    dist[v] = dist[u] + 1;
                    q.add(v);
                }
            }
        }

        final int sz = ds.size();
        if (sz == 0) {
            return 0;
        }
        Collections.sort(ds); // ascending
        if (sz != 1) {
            for (int i = 1; i < sz; i++) {
                if (ds.get(i) <= ds.get(i - 1)) {
                    ds.set(i, ds.get(i - 1) + 1);
                }
            }
        }
        return ds.get(sz - 1);
    }

    private static void output(int number) {
        System.out.print(number);
        System.out.print('\n');
    }

    private static final class Data {
        final int n;
        final ForwardStar graph;
        final boolean[] hasGiant;

        Data(int n, ForwardStar graph, boolean[] hasGiant) {
            this.n = n;
            this.graph = graph;
            this.hasGiant = hasGiant;
        }
    }

    private static final class ForwardStar {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] next;
        private int edgeIndex;

        ForwardStar(int nodeCount, int maxEdges) {
            assert (nodeCount > 0);
            assert (maxEdges >= 0);
            this.nodeCount = nodeCount;
            this.head = new int[nodeCount + 1];
            Arrays.fill(this.head, -1);
            this.to = new int[maxEdges];
            this.next = new int[maxEdges];
            this.edgeIndex = 0;
        }

        int addEdge(int from, int toNode) {
            assert ((1 <= from) && (from <= nodeCount));
            assert ((1 <= toNode) && (toNode <= nodeCount));
            assert (edgeIndex < to.length);
            final int idx = edgeIndex;
            to[idx] = toNode;
            next[idx] = head[from];
            head[from] = idx;
            edgeIndex++;
            return idx;
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

    // fast reader
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                final String line = br.readLine();
                if (line == null) {
                    return null;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            final String token = next();
            assert (token != null);
            return Integer.parseInt(token);
        }
    }
}
