// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public final class Main {
    private static final int MAX_N = 500_000;
    private static final int MAX_W = 100;
    private static final int MIN_N = 1;
    private static final long MIN_NUM = 1L;
    private static final long MAX_NUM = 2_000_000_000L;

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final long answer = cal(inputData);
        output(answer);
    }

    public static InputData reader() throws IOException {
        final var fastReader = new Reader();
        final int n = fastReader.nextInt();
        assert ((MIN_N <= n) && (n <= MAX_N));
        final long target = fastReader.nextLong();
        assert ((MIN_NUM <= target) && (target <= MAX_NUM));
        final int edgeCapacity = Math.max(1, n - 1) * 2;
        final var graph = new ForwardStar(n, edgeCapacity);
        for (int i = 0; i < n - 1; ++i) {
            final int u = fastReader.nextInt();
            final int v = fastReader.nextInt();
            final int w = fastReader.nextInt();
            assert ((1 <= u) && (u <= n));
            assert ((1 <= v) && (v <= n));
            assert ((1 <= w) && (w <= MAX_W));
            graph.addEdge(u, v, w);
            graph.addEdge(v, u, w);
        }
        return new InputData(target, graph);
    }

    public static long cal(InputData data) {
        final long target = data.target;
        final var graph = data.graph;
        final Deque<DfsState> stack = new ArrayDeque<>();
        stack.push(new DfsState(1, 0, 0L));
        long answer = 0L;
        while (!stack.isEmpty()) {
            final var state = stack.pop();
            final int node = state.node;
            final int parent = state.parent;
            final long acc = state.sum;
            boolean isLeaf = true;
            for (int edge = graph.head(node); edge != -1; edge = graph.next(edge)) {
                final int child = graph.to(edge);
                if (child == parent) {
                    continue;
                }
                isLeaf = false;
                stack.push(new DfsState(child, node, acc + graph.weight(edge)));
            }
            if (isLeaf && (acc == target)) {
                answer++;
            }
        }
        return answer;
    }

    public static void output(long number) {
        System.out.print(number);
        System.out.print('\n');
    }

    private static final class InputData {
        private final long target;
        private final ForwardStar graph;

        private InputData(long target, ForwardStar graph) {
            this.target = target;
            this.graph = graph;
        }
    }

    private static final class DfsState {
        private final int node;
        private final int parent;
        private final long sum;

        private DfsState(int nodeValue, int parentValue, long sumValue) {
            this.node = nodeValue;
            this.parent = parentValue;
            this.sum = sumValue;
        }
    }

    private static final class ForwardStar {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] weight;
        private final int[] next;
        private int edgeIndex;

        private ForwardStar(int nodeCount, int maxEdges) {
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

        private void addEdge(int from, int toNode, int w) {
            assert ((1 <= from) && (from <= nodeCount));
            assert ((1 <= toNode) && (toNode <= nodeCount));
            assert (edgeIndex < to.length);
            to[edgeIndex] = toNode;
            weight[edgeIndex] = w;
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

        private int weight(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return weight[edgeIdx];
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

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}
    }
}
