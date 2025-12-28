// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public final class Main {

    private static final class InputData {
        final int n;
        final int m;
        final int k;
        final int c;
        final int[] colors;
        final Star graph;

        InputData(int n, int m, int k, int c, int[] colors, Star graph) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.c = c;
            this.colors = colors;
            this.graph = graph;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        final int k = input.nextInt();
        final int c = input.nextInt();
        assert ((1 <= n) && (n <= 100000));
        assert ((0 <= m) && (m <= 100000));
        assert ((1 <= c) && (c <= k) && (k <= Math.min(n, 100)));

        final int[] colors = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            colors[i] = input.nextInt();
            assert ((1 <= colors[i]) && (colors[i] <= k));
        }

        final Star graph = new Star(n, m * 2);

        for (int i = 0; i < m; i++) {
            final int u = input.nextInt();
            final int v = input.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            graph.addEdge(u, v);
            graph.addEdge(v, u);
        }

        return new InputData(n, m, k, c, colors, graph);
    }

    public static int[] cal(InputData data) {
        final int n = data.n;
        final int k = data.k;
        final int c = data.c;
        final int[] colors = data.colors;
        final var graph = data.graph;

        // Group vertices by color
        @SuppressWarnings("unchecked")
        final List<Integer>[] colorNodes = new ArrayList[k + 1];
        for (int i = 1; i <= k; i++) {
            colorNodes[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            colorNodes[colors[i]].add(i);
        }

        // Multi-source BFS: for each color, compute distance from all nodes of that color
        // dist[color][node] = minimum distance from node to any node with that color
        final int[][] dist = new int[k + 1][n + 1];
        for (int col = 1; col <= k; col++) {
            Arrays.fill(dist[col], Integer.MAX_VALUE);
        }

        final Queue<Integer> queue = new ArrayDeque<>();

        for (int col = 1; col <= k; col++) {
            // Initialize with all nodes of this color
            for (final int node : colorNodes[col]) {
                dist[col][node] = 0;
                queue.offer(node);
            }

            // BFS
            while (!queue.isEmpty()) {
                final int u = queue.poll();
                for (int e = graph.head(u); e != -1; e = graph.next(e)) {
                    final int v = graph.to(e);
                    if (dist[col][v] == Integer.MAX_VALUE) {
                        dist[col][v] = dist[col][u] + 1;
                        queue.offer(v);
                    }
                }
            }
        }

        // For each target T, find the minimum cost to collect c colors
        // Sort colors by distance and take the c smallest
        final int[] result = new int[n + 1];
        final int[] colorDist = new int[k + 1];

        for (int t = 1; t <= n; t++) {
            // Get distance from T to each color
            for (int col = 1; col <= k; col++) {
                colorDist[col] = dist[col][t];
            }

            // Sort and take c smallest
            Arrays.sort(colorDist, 1, k + 1);

            int totalCost = 0;
            for (int i = 1; i <= c; i++) {
                totalCost += colorDist[i];
            }
            result[t] = totalCost;
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final int[] result = cal(data);
        output(result);
    }

    public static void output(int[] result) {
        final int n = result.length - 1;
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1) {
                sb.append(' ');
            }
            sb.append(result[i]);
        }
        System.out.print(sb.toString());
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

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
