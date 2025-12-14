// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    private static final class Query {
        final int x;
        final int y;

        Query(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final class TestCase {
        final int n;
        final int m;
        final star tree;
        final int root;
        final List<Query> queries;

        TestCase(int n, int m, star tree, int root, List<Query> queries) {
            this.n = n;
            this.m = m;
            this.tree = tree;
            this.root = root;
            this.queries = queries;
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
            assert ((1 <= n) && (n <= 150000));
            assert ((1 <= m) && (m <= 150000));

            final var tree = new star(n, n - 1);
            final var hasParent = new boolean[n + 1];
            for (int i = 0; i < n - 1; i++) {
                final int x = input.nextInt();
                final int y = input.nextInt();
                assert ((1 <= x) && (x <= n));
                assert ((1 <= y) && (y <= n));
                // y is the father of x
                tree.addEdge(y, x);
                hasParent[x] = true;
            }

            int root = 1;
            for (int i = 1; i <= n; i++) {
                if (!hasParent[i]) {
                    root = i;
                    break;
                }
            }

            final var queries = new ArrayList<Query>(m);
            for (int i = 0; i < m; i++) {
                final int x = input.nextInt();
                final int y = input.nextInt();
                assert ((1 <= x) && (x <= n));
                assert ((1 <= y) && (y <= n));
                queries.add(new Query(x, y));
            }
            testCases.add(new TestCase(n, m, tree, root, queries));
        }
        return testCases;
    }

    public static List<List<Boolean>> cal(List<TestCase> testCases) {
        final var results = new ArrayList<List<Boolean>>(testCases.size());
        for (final var tc : testCases) {
            // DFS to compute in-time and out-time for each node
            final var inTime = new int[tc.n + 1];
            final var outTime = new int[tc.n + 1];
            final int[] timer = {0};

            dfs(tc.root, tc.tree, inTime, outTime, timer);

            // y is ancestor of x iff inTime[y] <= inTime[x] && outTime[x] <= outTime[y]
            final var queryResults = new ArrayList<Boolean>(tc.queries.size());
            for (final var query : tc.queries) {
                final int x = query.x;
                final int y = query.y;
                final boolean isAncestor = (inTime[y] <= inTime[x]) && (outTime[x] <= outTime[y]);
                queryResults.add(isAncestor);
            }
            results.add(queryResults);
        }
        return results;
    }

    private static void dfs(int node, star tree, int[] inTime, int[] outTime, int[] timer) {
        inTime[node] = timer[0]++;
        for (int e = tree.head(node); e != -1; e = tree.next(e)) {
            final int child = tree.to(e);
            dfs(child, tree, inTime, outTime, timer);
        }
        outTime[node] = timer[0]++;
    }

    public static void main(String[] args) throws IOException {
        final var testCases = reader();
        final var results = cal(testCases);
        output(results);
    }

    public static void output(List<List<Boolean>> results) {
        final var sb = new StringBuilder();
        for (final var queryResults : results) {
            for (final var isAncestor : queryResults) {
                sb.append(isAncestor ? "Yes" : "No");
                sb.append('\n');
            }
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
