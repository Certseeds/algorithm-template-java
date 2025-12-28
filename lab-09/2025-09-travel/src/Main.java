// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {

    private static final class InputData {
        final int n;
        final int m;
        final Star graph;

        InputData(int n, int m, Star graph) {
            this.n = n;
            this.m = m;
            this.graph = graph;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        assert ((1 <= n) && (n <= 100000));
        assert ((1 <= m) && (m <= 100000));

        final Star graph = new Star(n, m);

        for (int i = 0; i < m; i++) {
            final int u = input.nextInt();
            final int v = input.nextInt();
            final int w = input.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            assert ((1 <= w) && (w <= 1000000000));
            graph.addEdge(u, v, w);
        }

        return new InputData(n, m, graph);
    }

    public static long cal(InputData data) {
        final int n = data.n;
        final var graph = data.graph;

        final long[] dist = new long[n + 1];
        final long INF = Long.MAX_VALUE;
        Arrays.fill(dist, INF);
        dist[1] = 0;

        final PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.offer(new long[]{1, 0});

        while (!pq.isEmpty()) {
            final long[] curr = pq.poll();
            final int u = (int) curr[0];
            final long d = curr[1];

            if (d > dist[u]) {
                continue;
            }

            for (int e = graph.head(u); e != -1; e = graph.next(e)) {
                final int v = graph.to(e);
                final long newDist = d + graph.weight(e);
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new long[]{v, newDist});
                }
            }
        }

        return dist[n] == INF ? -1 : dist[n];
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final long result = cal(data);
        output(result);
    }

    public static void output(long number) {
        System.out.print(number);
        System.out.print('\n');
    }

    private static final class Star {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] weight;
        private final int[] next;
        private int edgeIndex;

        Star(int nodeCount, int maxEdges) {
            assert (nodeCount > 0);
            assert (maxEdges >= 0);
            this.nodeCount = nodeCount;
            this.head = new int[nodeCount + 1];
            Arrays.fill(this.head, -1);
            this.to = new int[maxEdges];
            this.weight = new int[maxEdges];
            this.next = new int[maxEdges];
            this.edgeIndex = 0;
        }

        int addEdge(int from, int toNode, int edgeWeight) {
            assert ((1 <= from) && (from <= nodeCount));
            assert ((1 <= toNode) && (toNode <= nodeCount));
            assert (edgeIndex < to.length);
            final int idx = edgeIndex;
            to[idx] = toNode;
            weight[idx] = edgeWeight;
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

        int weight(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return weight[edgeIdx];
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
