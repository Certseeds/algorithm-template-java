// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    private static final class TestCase {
        final int n;
        final int m;
        final star graph;

        TestCase(int n, int m, star graph) {
            this.n = n;
            this.m = m;
            this.graph = graph;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var input = new Reader();
        final int T = input.nextInt();
        assert ((1 <= T) && (T <= 200000));

        final var testCases = new ArrayList<TestCase>(T);
        for (int t = 0; t < T; t++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((2 <= n) && (n <= 200000));
            assert ((n - 1 <= m) && (m <= Math.min(200000, (long) n * (n - 1) / 2)));

            final var graph = new star(n, m * 2);
            for (int i = 0; i < m; i++) {
                final int u = input.nextInt();
                final int v = input.nextInt();
                assert ((1 <= u) && (u <= n));
                assert ((1 <= v) && (v <= n));
                graph.addEdge(u, v);
                graph.addEdge(v, u);
            }
            testCases.add(new TestCase(n, m, graph));
        }
        return testCases;
    }

    public static List<List<Integer>> cal(List<TestCase> testCases) {
        final var results = new ArrayList<List<Integer>>(testCases.size());
        for (final var tc : testCases) {
            // BFS to build spanning tree, then select nodes at even depths
            final var depth = new int[tc.n + 1];
            final var visited = new boolean[tc.n + 1];

            final var queue = new ArrayDeque<Integer>();
            queue.offer(1);
            visited[1] = true;
            depth[1] = 0;

            while (!queue.isEmpty()) {
                final int cur = queue.poll();
                for (int e = tc.graph.head(cur); e != -1; e = tc.graph.next(e)) {
                    final int next = tc.graph.to(e);
                    if (!visited[next]) {
                        visited[next] = true;
                        depth[next] = depth[cur] + 1;
                        queue.offer(next);
                    }
                }
            }

            // Count nodes at even and odd depths
            final var evenNodes = new ArrayList<Integer>();
            final var oddNodes = new ArrayList<Integer>();
            for (int i = 1; i <= tc.n; i++) {
                if (depth[i] % 2 == 0) {
                    evenNodes.add(i);
                } else {
                    oddNodes.add(i);
                }
            }

            // Select the smaller group (it will be <= n/2)
            // Either group forms a dominating set because:
            // - Every node is either in the set OR has a neighbor in the set (parent or child in BFS tree)
            if (evenNodes.size() <= oddNodes.size()) {
                results.add(evenNodes);
            } else {
                results.add(oddNodes);
            }
        }
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var testCases = reader();
        final var results = cal(testCases);
        output(results);
    }

    public static void output(List<List<Integer>> results) {
        final var sb = new StringBuilder();
        for (int r = 0; r < results.size(); r++) {
            final var chosen = results.get(r);
            // First line: count
            sb.append(chosen.size());
            sb.append('\n');
            // Second line: the cities
            for (int i = 0; i < chosen.size(); i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(chosen.get(i));
            }
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
