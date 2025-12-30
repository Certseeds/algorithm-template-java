// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {
    private static final class InputData {
        final int n;
        final ForwardStar g;
        final int[] p;

        InputData(int n, int maxEdges) {
            this.n = n;
            this.g = new ForwardStar(n, maxEdges);
            this.p = new int[n + 1];
        }
    }

    public static InputData reader() throws IOException {
        final var in = new Reader();
        final int n = in.nextInt();
        assert ((2 <= n) && (n <= 200_000));
        final var data = new InputData(n, (n - 1) * 2);
        for (int i = 0; i < n - 1; i++) {
            final int u = in.nextInt();
            final int v = in.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            data.g.addEdge(u, v, 0);
            data.g.addEdge(v, u, 0);
        }
        for (int i = 1; i <= n; i++) {
            final int pi = in.nextInt();
            assert ((1 <= pi) && (pi <= 100_000_000));
            data.p[i] = pi;
        }
        return data;
    }

    public static long cal(InputData data) {
        final int n = data.n;
        final int[] p = data.p;
        // choose root as node with maximum p
        int root = 1;
        for (int i = 2; i <= n; i++) {
            if (p[i] > p[root]) root = i;
        }

        // build parent and order using iterative DFS
        final int[] parent = new int[n + 1];
        final int[] order = new int[n];
        int ordSize = 0;
        final int[] stack = new int[n];
        int top = 0;
        stack[top++] = root;
        parent[root] = 0;
        while (top > 0) {
            final int u = stack[--top];
            order[ordSize++] = u;
            for (int e = data.g.head(u); e != -1; e = data.g.next(e)) {
                final int v = data.g.to(e);
                if (v == parent[u]) continue;
                parent[v] = u;
                stack[top++] = v;
            }
        }

        // post-order
        final int[] mx = new int[n + 1];
        long sum = 0L;
        for (int idx = ordSize - 1; idx >= 0; idx--) {
            final int u = order[idx];
            int cntGE = 0;
            int maxChildMx = 0;

            // For choosing minimal increases
            int best1 = Integer.MAX_VALUE; // smallest delta
            int best2 = Integer.MAX_VALUE; // second smallest delta

            for (int e = data.g.head(u); e != -1; e = data.g.next(e)) {
                final int v = data.g.to(e);
                if (v == parent[u]) continue;
                final int mv = mx[v];
                if (mv >= p[u]) cntGE++;
                if (mv > maxChildMx) maxChildMx = mv;
                if (mv < p[u]) {
                    final int delta = p[u] - mv;
                    if (delta < best1) {
                        best2 = best1;
                        best1 = delta;
                    } else if (delta < best2) {
                        best2 = delta;
                    }
                }
            }

            final boolean isRoot = (u == root);
            if (!isRoot) {
                if (cntGE >= 1) {
                    // no extra cost; propagate the best available value upward
                    mx[u] = Math.max(maxChildMx, 0);
                } else {
                    // need to ensure at least one endpoint >= p[u] within subtree
                    int bestDelta = p[u]; // using e_u = p[u]
                    if (best1 != Integer.MAX_VALUE) {
                        if (best1 < bestDelta) bestDelta = best1;
                    }
                    sum += bestDelta;
                    mx[u] = p[u];
                }
            } else {
                if (cntGE >= 2) {
                    mx[u] = Math.max(maxChildMx, 0);
                } else if (cntGE == 1) {
                    // need one more
                    int bestDelta = p[u]; // using e_u = p[u]
                    if (best1 != Integer.MAX_VALUE) {
                        if (best1 < bestDelta) bestDelta = best1;
                    }
                    sum += bestDelta;
                    mx[u] = Math.max(maxChildMx, p[u]);
                } else { // cntGE == 0
                    // need two values >= p[u]
                    int d1 = p[u];
                    int d2 = Integer.MAX_VALUE;
                    // combine with child raises
                    if (best1 < d1) {
                        d2 = d1;
                        d1 = best1;
                    } else {
                        d2 = best1;
                    }
                    if (best2 < d2) {
                        d2 = best2;
                    }
                    // If there is only one child, best2 might be MAX, but we can still use root itself and that child
                    // If no children (n>=2, root cannot be leaf unless n==1 which is impossible by constraints)
                    if (d2 == Integer.MAX_VALUE) {
                        // no child below p[u] found (should not happen when cntGE==0 unless degree==1 and no children)
                        // fallback: use root and one arbitrary child with delta = p[u]
                        d2 = p[u];
                    }
                    sum += (long) d1 + (long) d2;
                    mx[u] = p[u];
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final long result = cal(data);
        output(result);
    }

    public static void output(long number) {
        System.out.print(number);
        System.out.print('\n');
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

    // inlined from lab-06/2025-06-1/src/star.java
    private static final class ForwardStar {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] weight;
        private final int[] next;
        private int edgeIndex;

        public ForwardStar(int nodeCount, int maxEdges) {
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

        public int addEdge(int from, int toNode, int edgeWeight) {
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

        public int head(int node) {
            assert ((1 <= node) && (node <= nodeCount));
            return head[node];
        }

        public int to(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return to[edgeIdx];
        }

        public int weight(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return weight[edgeIdx];
        }

        public int next(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return next[edgeIdx];
        }

        public int edgeCount() {
            return edgeIndex;
        }

        public int nodeCount() {
            return nodeCount;
        }
    }
}
