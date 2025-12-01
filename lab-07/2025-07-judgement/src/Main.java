// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {
    private Main() {/**/}

    // 链式前向星: 用于存储有向图
    private static final class Star {
        private final int nodeCount;
        private final int[] head;
        private final int[] to;
        private final int[] next;
        private int edgeIndex;

        Star(int nodeCount, int maxEdges) {
            assert (nodeCount > 0);
            assert (maxEdges >= 0);
            this.nodeCount = nodeCount;
            this.head = new int[nodeCount + 1];
            Arrays.fill(this.head, -1);
            this.to = new int[maxEdges];
            this.next = new int[maxEdges];
            this.edgeIndex = 0;
        }

        int addEdge(int from, int toNode) {
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

        int head(int node) {
            assert ((1 <= node) && (node <= nodeCount));
            return head[node];
        }

        int to(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return to[edgeIdx];
        }

        int next(int edgeIdx) {
            assert ((0 <= edgeIdx) && (edgeIdx < edgeIndex));
            return next[edgeIdx];
        }
    }

    // 核心数据结构: 堆树 (基于链式前向星)
    private static final class HeapTree {
        private final int n;
        private final long[] values;
        private final Star graph;
        private final int[] childCount; // 每个节点的孩子数量
        private int root;

        HeapTree(int n) {
            this.n = n;
            this.values = new long[n + 1];
            this.graph = new Star(n, n - 1); // 树有 n-1 条边
            this.childCount = new int[n + 1];
            this.root = -1;
        }

        void setValue(int node, long value) {
            values[node] = value;
        }

        // 添加边: parent -> child
        void addEdge(int parent, int child, boolean[] hasParent) {
            hasParent[child] = true;
            graph.addEdge(parent, child);
            childCount[parent]++;
        }

        void findRoot(boolean[] hasParent) {
            for (int j = 1; j <= n; j++) {
                if (!hasParent[j]) {
                    root = j;
                    break;
                }
            }
        }

        // 获取节点的第一个孩子 (按添加顺序的最后一个, 因为链式前向星是逆序存储)
        // 返回 -1 表示没有孩子
        private int getFirstChild(int node) {
            if (childCount[node] == 0) {
                return -1;
            }
            // 链式前向星逆序存储, 需要找到最早添加的 (即链表最后一个)
            int edgeIdx = graph.head(node);
            if (childCount[node] == 1) {
                return graph.to(edgeIdx);
            }
            // 有两个孩子, 返回第二个添加的 (链表第一个是最后添加的, 第二个是第一个添加的)
            return graph.to(graph.next(edgeIdx));
        }

        // 获取节点的第二个孩子 (按添加顺序)
        // 返回 -1 表示没有第二个孩子
        private int getSecondChild(int node) {
            if (childCount[node] < 2) {
                return -1;
            }
            // 链式前向星逆序存储, 最后添加的在链表头部
            return graph.to(graph.head(node));
        }

        // 判断是否为完全二叉树
        boolean isCompleteBinaryTree() {
            final var queue = new ArrayDeque<Integer>();
            queue.add(root);
            int count = 0;
            boolean seenNull = false;

            while (!queue.isEmpty()) {
                final int node = queue.poll();
                count++;

                // 检查是否超过二叉树限制
                if (childCount[node] > 2) {
                    return false;
                }

                final int left = getFirstChild(node);
                final int right = getSecondChild(node);

                if (left == -1) {
                    seenNull = true;
                } else {
                    if (seenNull) {
                        return false;
                    }
                    queue.add(left);
                }

                if (right == -1) {
                    seenNull = true;
                } else {
                    if (seenNull) {
                        return false;
                    }
                    queue.add(right);
                }
            }

            return count == n;
        }

        // 判断是否满足堆序性质 (大顶堆或小顶堆)
        boolean hasHeapProperty() {
            if (n == 1) {
                return true;
            }

            final var queue = new ArrayDeque<Integer>();
            queue.add(root);
            Boolean isMaxHeap = null;

            while (!queue.isEmpty()) {
                final int node = queue.poll();
                final int left = getFirstChild(node);
                final int right = getSecondChild(node);

                if (left != -1) {
                    final var result = checkHeapOrder(node, left, isMaxHeap);
                    if (result == null) {
                        return false;
                    }
                    isMaxHeap = result;
                    queue.add(left);
                }

                if (right != -1) {
                    final var result = checkHeapOrder(node, right, isMaxHeap);
                    if (result == null) {
                        return false;
                    }
                    isMaxHeap = result;
                    queue.add(right);
                }
            }

            return true;
        }

        // 检查父子堆序关系, 返回 null 表示违反堆序, 否则返回堆类型
        private Boolean checkHeapOrder(int parent, int child, Boolean isMaxHeap) {
            if (isMaxHeap == null) {
                return values[parent] >= values[child];
            }
            if (isMaxHeap && values[parent] < values[child]) {
                return null;
            }
            if (!isMaxHeap && values[parent] > values[child]) {
                return null;
            }
            return isMaxHeap;
        }

        // 综合判断: 是否为堆
        boolean isHeap() {
            return isCompleteBinaryTree() && hasHeapProperty();
        }
    }

    private static final class InputData {
        final int t;
        final HeapTree[] trees;

        InputData(int t, HeapTree[] trees) {
            this.t = t;
            this.trees = trees;
        }
    }

    public static InputData reader() throws IOException {
        final var in = new Reader();
        final int t = in.nextInt();
        assert ((1 <= t) && (t <= 10));
        final var trees = new HeapTree[t];

        for (int i = 0; i < t; i++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 100_000));
            final var tree = new HeapTree(n);

            for (int j = 1; j <= n; j++) {
                final long val = in.nextLong();
                assert ((1 <= val) && (val <= 1_000_000_000L));
                tree.setValue(j, val);
            }

            final boolean[] hasParent = new boolean[n + 1];
            for (int j = 0; j < n - 1; j++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                assert ((1 <= x) && (x <= n));
                assert ((1 <= y) && (y <= n));
                tree.addEdge(x, y, hasParent);
            }

            tree.findRoot(hasParent);
            trees[i] = tree;
        }

        return new InputData(t, trees);
    }

    public static String[] cal(InputData data) {
        final var results = new String[data.t];
        for (int i = 0; i < data.t; i++) {
            results[i] = data.trees[i].isHeap() ? "YES" : "NO";
        }
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final String[] results = cal(data);
        output(results);
    }

    public static void output(String[] results) {
        for (int i = 0; i < results.length; i++) {
            System.out.print("Case #");
            System.out.print(i + 1);
            System.out.print(": ");
            System.out.print(results[i]);
            System.out.print('\n');
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
