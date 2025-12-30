// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    private static final long MOD = 1_000_000_007L;

    private static final class TestCase {
        final int n;
        final int m;
        final long[] a;
        final long[] b;
        final star graph;
        final int[] inDegree;

        TestCase(int n, int m, long[] a, long[] b, star graph, int[] inDegree) {
            this.n = n;
            this.m = m;
            this.a = a;
            this.b = b;
            this.graph = graph;
            this.inDegree = inDegree;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var input = new Reader();
        final int T = input.nextInt();
        assert ((1 <= T) && (T <= 10));

        final var testCases = new ArrayList<TestCase>(T);
        for (int t = 0; t < T; t++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 100000));
            assert ((1 <= m) && (m <= 100000));

            final var a = new long[n + 1];
            final var b = new long[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = input.nextLong();
                b[i] = input.nextLong();
            }

            final var graph = new star(n, m);
            final var inDegree = new int[n + 1];

            for (int i = 0; i < m; i++) {
                final int u = input.nextInt();
                final int v = input.nextInt();
                assert ((1 <= u) && (u <= n));
                assert ((1 <= v) && (v <= n));
                graph.addEdge(u, v);
                inDegree[v]++;
            }
            testCases.add(new TestCase(n, m, a, b, graph, inDegree));
        }
        return testCases;
    }

    public static List<Long> cal(List<TestCase> testCases) {
        final var results = new ArrayList<Long>(testCases.size());
        for (final var tc : testCases) {
            // sum = sum_{i,j} count(i,j) * a[i] * b[j]
            // = sum_j b[j] * sum_i count(i,j) * a[i]
            // Let cntA[j] = sum_i count(i,j) * a[i] (weighted count of paths ending at j)
            // Then we need: sum_j b[j] * cntA[j]

            // Topological sort: process nodes with in-degree 0 first
            // For each node j, cntA[j] = a[j] * (number of paths starting at j to j, which is 0)
            //                          + sum_{i->j} cntA[i]
            // Actually: cntA[j] = sum_{i->j} (cntA[i] + pathCount[i] * a[j]) where pathCount[i] = number of paths to i
            // No wait, let's think again:
            // count(i,j) = number of paths from i to j
            // cntA[j] = sum_i count(i,j) * a[i]
            // 
            // For edge u -> v:
            // count(i,v) = count(i,u) + paths that don't go through u
            // This is tricky. Let's use DP differently:
            //
            // Let pathsTo[v] = total number of paths ending at v = sum_i count(i,v)
            // Let weightedPathsTo[v] = sum_i count(i,v) * a[i]
            //
            // For node v with incoming edges from u1, u2, ...:
            // pathsTo[v] = sum_u pathsTo[u] + (number of direct edges to v that start a new path)
            // Actually, every node i can start paths. A path from i to v either:
            // - Goes through some predecessor u of v: contributes pathsTo[u] paths
            // - Or i = v directly (but count(v,v) = 0)
            //
            // Simpler: pathsTo[v] = sum_{u->v} (pathsTo[u] + 1)
            // where +1 accounts for direct edge u->v being a path of length 1
            //
            // weightedPathsTo[v] = sum_{u->v} (weightedPathsTo[u] + a[u])
            // where +a[u] accounts for paths of length 1 from u to v

            final var pathsTo = new long[tc.n + 1];       // sum_i count(i,j) for node j
            final var weightedTo = new long[tc.n + 1];    // sum_i count(i,j) * a[i] for node j

            final var inDegree = tc.inDegree.clone();
            final var queue = new ArrayDeque<Integer>();
            for (int i = 1; i <= tc.n; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }

            while (!queue.isEmpty()) {
                final int u = queue.poll();
                for (int e = tc.graph.head(u); e != -1; e = tc.graph.next(e)) {
                    final int v = tc.graph.to(e);
                    // Edge u -> v contributes:
                    // - All paths that ended at u, now extended to v
                    // - Plus a new path just from u to v (length 1)
                    pathsTo[v] = (pathsTo[v] + pathsTo[u] + 1) % MOD;
                    weightedTo[v] = (weightedTo[v] + weightedTo[u] + tc.a[u]) % MOD;

                    inDegree[v]--;
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }

            // Answer = sum_j b[j] * weightedTo[j]
            long answer = 0;
            for (int j = 1; j <= tc.n; j++) {
                answer = (answer + tc.b[j] * weightedTo[j]) % MOD;
            }
            results.add(answer);
        }
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var testCases = reader();
        final var results = cal(testCases);
        output(results);
    }

    public static void output(List<Long> results) {
        final var sb = new StringBuilder();
        for (final var result : results) {
            sb.append(result);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    private static final class star {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] next;
        private int edgeIndex;

        public star(int nodeCount, int maxEdges) {
            assert (nodeCount > 0);
            assert (maxEdges >= 0);
            this.nodeCount = nodeCount;
            this.head = new int[nodeCount + 1];
            Arrays.fill(this.head, -1);
            this.to = new int[maxEdges];
            this.next = new int[maxEdges];
            this.edgeIndex = 0;
        }

        public int addEdge(int from, int toNode) {
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

        public int head(int node) {
            assert ((1 <= node) && (node <= nodeCount));
            return head[node];
        }

        public int to(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return to[edgeIdx];
        }

        public int next(int edgeIdx) {
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

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
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

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

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
