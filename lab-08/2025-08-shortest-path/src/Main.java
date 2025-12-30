// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    private static final class TestCase {
        final int n;
        final int m;
        final int s;
        final star graph;

        TestCase(int n, int m, int s, star graph) {
            this.n = n;
            this.m = m;
            this.s = s;
            this.graph = graph;
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
            final int s = input.nextInt();
            assert ((1 <= n) && (n <= 100000));
            assert ((1 <= m) && (m <= 500000));
            assert ((1 <= s) && (s <= n));

            final var graph = new star(n, m * 2);
            for (int i = 0; i < m; i++) {
                final int x = input.nextInt();
                final int y = input.nextInt();
                assert ((1 <= x) && (x <= n));
                assert ((1 <= y) && (y <= n));
                graph.addEdge(x, y);
                graph.addEdge(y, x);
            }
            testCases.add(new TestCase(n, m, s, graph));
        }
        return testCases;
    }

    public static List<int[]> cal(List<TestCase> testCases) {
        final var results = new ArrayList<int[]>(testCases.size());
        for (final var tc : testCases) {
            final var dist = new int[tc.n + 1];
            Arrays.fill(dist, -1);
            dist[tc.s] = 0;

            final var queue = new ArrayDeque<Integer>();
            queue.offer(tc.s);

            while (!queue.isEmpty()) {
                final int cur = queue.poll();
                for (int e = tc.graph.head(cur); e != -1; e = tc.graph.next(e)) {
                    final int next = tc.graph.to(e);
                    if (dist[next] == -1) {
                        dist[next] = dist[cur] + 1;
                        queue.offer(next);
                    }
                }
            }
            results.add(dist);
        }
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var testCases = reader();
        final var results = cal(testCases);
        output(results);
    }

    public static void output(List<int[]> results) {
        final var sb = new StringBuilder();
        for (final var dist : results) {
            for (int i = 1; i < dist.length; i++) {
                if (i > 1) {
                    sb.append(' ');
                }
                sb.append(dist[i]);
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
