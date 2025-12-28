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
        final int p;
        final int k;
        final int s;
        final int t;
        final Star roads;
        final Star portals;

        InputData(int n, int m, int p, int k, int s, int t, Star roads, Star portals) {
            this.n = n;
            this.m = m;
            this.p = p;
            this.k = k;
            this.s = s;
            this.t = t;
            this.roads = roads;
            this.portals = portals;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        final int p = input.nextInt();
        final int k = input.nextInt();
        assert ((1 <= n) && (n <= 50000));
        assert ((1 <= m) && (m <= 50000));
        assert ((1 <= p) && (p <= 50000));
        assert ((0 <= k) && (k <= 10));

        final Star roads = new Star(n, m);

        for (int i = 0; i < m; i++) {
            final int u = input.nextInt();
            final int v = input.nextInt();
            final int w = input.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            assert ((1 <= w) && (w <= 1000000));
            roads.addEdge(u, v, w);
        }

        final Star portals = new Star(n, p);
        for (int i = 0; i < p; i++) {
            final int u = input.nextInt();
            final int v = input.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            portals.addEdge(u, v, 0);
        }

        final int s = input.nextInt();
        final int t = input.nextInt();
        assert ((1 <= s) && (s <= n));
        assert ((1 <= t) && (t <= n));

        return new InputData(n, m, p, k, s, t, roads, portals);
    }

    public static long cal(InputData data) {
        final int n = data.n;
        final int k = data.k;
        final int s = data.s;
        final int t = data.t;
        final var roads = data.roads;
        final var portals = data.portals;

        // State: (node, portals_used)
        // dist[node][portals_used] = minimum distance
        final long INF = Long.MAX_VALUE;
        final long[][] dist = new long[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[s][0] = 0;

        // Priority queue: [distance, node, portals_used]
        final PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, s, 0});

        while (!pq.isEmpty()) {
            final long[] curr = pq.poll();
            final long d = curr[0];
            final int u = (int) curr[1];
            final int used = (int) curr[2];

            if (d > dist[u][used]) {
                continue;
            }

            // Use normal roads
            for (int e = roads.head(u); e != -1; e = roads.next(e)) {
                final int v = roads.to(e);
                final long newDist = d + roads.weight(e);
                if (newDist < dist[v][used]) {
                    dist[v][used] = newDist;
                    pq.offer(new long[]{newDist, v, used});
                }
            }

            // Use portal (if we haven't used k portals yet)
            if (used < k) {
                for (int e = portals.head(u); e != -1; e = portals.next(e)) {
                    final int v = portals.to(e);
                    if (d < dist[v][used + 1]) {
                        dist[v][used + 1] = d;
                        pq.offer(new long[]{d, v, used + 1});
                    }
                }
            }
        }

        long ans = INF;
        for (int i = 0; i <= k; i++) {
            ans = Math.min(ans, dist[t][i]);
        }

        return ans == INF ? -1 : ans;
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

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
