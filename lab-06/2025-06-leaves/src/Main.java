// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {
    private static final int MIN_N = 1;
    private static final int MAX_N = 1_000_000;

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var leaves = cal(inputData);
        output(leaves);
    }

    public static ForwardStar reader() throws IOException {
        final var fastReader = new Reader();
        final int n = fastReader.nextInt();
        assert ((MIN_N <= n) && (n <= MAX_N));
        final int edgeCapacity = Math.max(1, n - 1) * 2;
        final var graph = new ForwardStar(n, edgeCapacity);
        for (int i = 0; i < n - 1; ++i) {
            final int u = fastReader.nextInt();
            final int v = fastReader.nextInt();
            assert ((MIN_N <= u) && (u <= n));
            assert ((MIN_N <= v) && (v <= n));
            graph.addEdge(u, v);
            graph.addEdge(v, u);
        }
        return graph;
    }

    public static List<Integer> cal(ForwardStar graph) {
        final Deque<DfsState> stack = new ArrayDeque<>();
        stack.push(new DfsState(1, 0));
        final List<Integer> leaves = new ArrayList<>();
        while (!stack.isEmpty()) {
            final var state = stack.pop();
            final int node = state.node;
            final int parent = state.parent;
            int childCount = 0;
            for (int edge = graph.head(node); edge != -1; edge = graph.next(edge)) {
                final int child = graph.to(edge);
                if (child == parent) {
                    continue;
                }
                childCount++;
                stack.push(new DfsState(child, node));
            }
            if (childCount == 0) {
                leaves.add(node);
            }
        }
        leaves.sort(Integer::compareTo);
        return leaves;
    }

    public static void output(List<Integer> result) {
        System.out.print(result.stream().map(Object::toString).collect(Collectors.joining(" ")));
        System.out.print('\n');
    }

    private static final class DfsState {
        private final int node;
        private final int parent;

        DfsState(int node, int parent) {
            this.node = node;
            this.parent = parent;
        }
    }

    public static final class ForwardStar {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] next;
        private int edgeIndex;

        private ForwardStar(int nodeCount, int maxEdges) {
            assert (nodeCount > 0);
            assert (maxEdges >= 0);
            this.nodeCount = nodeCount;
            this.head = new int[nodeCount + 1];
            Arrays.fill(this.head, -1);
            this.to = new int[maxEdges];
            this.next = new int[maxEdges];
            this.edgeIndex = 0;
        }

        private void addEdge(int from, int toNode) {
            assert ((1 <= from) && (from <= nodeCount));
            assert ((1 <= toNode) && (toNode <= nodeCount));
            assert (edgeIndex < to.length);
            to[edgeIndex] = toNode;
            next[edgeIndex] = head[from];
            head[from] = edgeIndex;
            edgeIndex++;
        }

        private int head(int node) {
            assert ((1 <= node) && (node <= nodeCount));
            return head[node];
        }

        private int to(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return to[edgeIdx];
        }

        private int next(int edgeIdx) {
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
