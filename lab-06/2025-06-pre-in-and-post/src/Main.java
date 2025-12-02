// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public final class Main {
    private static final int MIN_T = 1;
    private static final int MAX_T = 10;
    private static final int MIN_N = 1;
    private static final int MAX_N = 1_000;

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var result = cal(inputData);
        output(result);
    }

    public static List<CaseData> reader() throws IOException {
        final var fastReader = new Reader();
        final int cases = fastReader.nextInt();
        assert ((MIN_T <= cases) && (cases <= MAX_T));
        final List<CaseData> items = new ArrayList<>(cases);
        for (int t = 0; t < cases; ++t) {
            final int n = fastReader.nextInt();
            assert ((MIN_N <= n) && (n <= MAX_N));
            final int[] preorder = new int[n];
            final int[] inorder = new int[n];
            for (int i = 0; i < n; ++i) {
                final int value = fastReader.nextInt();
                assert ((1 <= value) && (value <= n));
                preorder[i] = value;
            }
            for (int i = 0; i < n; ++i) {
                final int value = fastReader.nextInt();
                assert ((1 <= value) && (value <= n));
                inorder[i] = value;
            }
            items.add(new CaseData(n, preorder, inorder));
        }
        return items;
    }

    public static String cal(List<CaseData> data) {
        final var builder = new StringBuilder();
        final List<String> result = new ArrayList<>();
        for (CaseData caseData: data) {
            final int[] postOrder = solveCase(caseData);
            for (int i = 0; i < caseData.n; ++i) {
                if (i > 0) {
                    builder.append(' ');
                }
                builder.append(postOrder[i]);
            }
            result.add(builder.toString());
        }
        return String.join("\n", result);
    }

    private static int[] solveCase(CaseData data) {
        final int n = data.n;
        final var graph = new ForwardStar(n, Math.max(1, n - 1) * 2);
        final int[] position = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            position[data.inorder[i]] = i;
        }
        buildEdges(data.preorder, 0, n - 1, 0, n - 1, position, graph);
        final int[] postOrder = new int[n];
        final var cursor = new IntWrapper();
        dfsPost(data.preorder[0], 0, graph, position, postOrder, cursor);
        return postOrder;
    }

    private static void buildEdges(int[] preorder, int preL, int preR,
                                   int inL, int inR, int[] pos, ForwardStar graph) {
        if (preL > preR) {
            return;
        }
        final int root = preorder[preL];
        final int mid = pos[root];
        final int leftSize = mid - inL;
        final int rightSize = inR - mid;
        if (leftSize > 0) {
            final int leftRootIndex = preL + 1;
            final int leftRoot = preorder[leftRootIndex];
            graph.addEdge(root, leftRoot);
            graph.addEdge(leftRoot, root);
            buildEdges(preorder, leftRootIndex, leftRootIndex + leftSize - 1,
                    inL, mid - 1, pos, graph);
        }
        if (rightSize > 0) {
            final int rightRootIndex = preL + leftSize + 1;
            final int rightRoot = preorder[rightRootIndex];
            graph.addEdge(root, rightRoot);
            graph.addEdge(rightRoot, root);
            buildEdges(preorder, rightRootIndex, preR, mid + 1, inR, pos, graph);
        }
    }

    private static void dfsPost(int node, int parent, ForwardStar graph,
                                int[] inorderPosition, int[] output, IntWrapper cursor) {
        int firstChild = 0;
        int secondChild = 0;
        for (int edge = graph.head(node); edge != -1; edge = graph.next(edge)) {
            final int child = graph.to(edge);
            if (child == parent) {
                continue;
            }
            if (firstChild == 0) {
                firstChild = child;
            } else {
                secondChild = child;
            }
        }
        if ((firstChild != 0) && (secondChild != 0)
                && (inorderPosition[firstChild] > inorderPosition[secondChild])) {
            final int temp = firstChild;
            firstChild = secondChild;
            secondChild = temp;
        }
        if (firstChild != 0) {
            dfsPost(firstChild, node, graph, inorderPosition, output, cursor);
        }
        if (secondChild != 0) {
            dfsPost(secondChild, node, graph, inorderPosition, output, cursor);
        }
        output[cursor.value] = node;
        cursor.value++;
    }

    public static void output(String data) {
        System.out.print(data);
        System.out.print('\n');
    }

    private static final class IntWrapper {
        private int value;
    }

    private static final class CaseData {
        private final int n;
        private final int[] preorder;
        private final int[] inorder;

        private CaseData(int n, int[] preorder, int[] inorder) {
            this.n = n;
            this.preorder = preorder;
            this.inorder = inorder;
        }
    }

    private static final class ForwardStar {
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

        int nextInt() {return Integer.parseInt(next());}
    }
}
