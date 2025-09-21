// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class Op {
        final char type;
        final long x;

        Op(char type, long x) {
            this.type = type;
            this.x = x;
        }
    }

    public static final class TestCase {
        public final int n;
        public final long m;
        public final List<Op> ops;

        public TestCase(int n, long m, List<Op> ops) {
            this.n = n;
            this.m = m;
            this.ops = ops;
        }
    }

    // Custom Treap implementation for better performance
    static class TreapNode {
        final long val;
        final int priority;
        int size;
        int count; // number of duplicates
        TreapNode left, right;

        TreapNode(long val) {
            this.val = val;
            this.priority = new Random().nextInt();
            this.count = 1;
            this.size = 1;
        }
    }

    static class Treap {
        private TreapNode root;
        private long offset;
        private int leftCount;
        private final long minSalary;
        private static final Random rand = new Random();

        Treap(long minSalary) {
            this.minSalary = minSalary;
            this.offset = 0;
            this.leftCount = 0;
        }

        private int getSize(TreapNode node) {
            return node == null ? 0 : node.size;
        }

        private void updateSize(TreapNode node) {
            if (node != null) {
                node.size = node.count + getSize(node.left) + getSize(node.right);
            }
        }

        private TreapNode rotateRight(TreapNode node) {
            TreapNode left = node.left;
            node.left = left.right;
            left.right = node;
            updateSize(node);
            updateSize(left);
            return left;
        }

        private TreapNode rotateLeft(TreapNode node) {
            TreapNode right = node.right;
            node.right = right.left;
            right.left = node;
            updateSize(node);
            updateSize(right);
            return right;
        }

        private TreapNode insert(TreapNode node, long val) {
            if (node == null) {
                return new TreapNode(val);
            }

            if (val == node.val) {
                node.count++;
                node.size++;
                return node;
            }

            if (val < node.val) {
                node.left = insert(node.left, val);
                if (node.left.priority > node.priority) {
                    node = rotateRight(node);
                }
            } else {
                node.right = insert(node.right, val);
                if (node.right.priority > node.priority) {
                    node = rotateLeft(node);
                }
            }

            updateSize(node);
            return node;
        }

        void insert(long salary) {
            if (salary < minSalary) {
                // leftCount++;
                // 如果没有进入公司, 就不算
                return;
            }
            root = insert(root, salary - offset);
        }

        void addAll(long delta) {
            offset += delta;
        }

        private TreapNode removeBelow(TreapNode node, long threshold) {
            if (node == null) return null;

            if (node.val < threshold) {
                leftCount += node.count + getSize(node.left);
                return removeBelow(node.right, threshold);
            } else {
                node.left = removeBelow(node.left, threshold);
                updateSize(node);
                return node;
            }
        }

        void subtractAll(long delta) {
            offset -= delta;
            final long threshold = minSalary - offset;
            root = removeBelow(root, threshold);
        }

        private long findKthMax(TreapNode node, int k) {
            if (node == null) return -1;

            final int rightSize = getSize(node.right);

            if (k <= rightSize) {
                return findKthMax(node.right, k);
            } else if (k <= rightSize + node.count) {
                return node.val + offset;
            } else {
                return findKthMax(node.left, k - rightSize - node.count);
            }
        }

        long queryKthMax(int k) {
            if (k > getSize(root)) {
                return -1;
            }
            return findKthMax(root, k);
        }

        int getLeftCount() {
            return leftCount;
        }
    }


    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final long m = in.nextLong();
            assert ((1 <= n) && (n <= 100_000));
            assert ((0 <= m) && (m <= 1_000_000));
            final List<Op> ops = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                final String s = in.next();
                final char type = s.charAt(0);
                final long x = in.nextLong();
                ops.add(new Op(type, x));
            }
            tests.add(new TestCase(n, m, ops));
        }
        return tests;
    }

    // cal: process test cases and return output lines
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (TestCase tc : inputs) {
            final Treap treap = new Treap(tc.m);
            for (final Op op : tc.ops) {
                switch (op.type) {
                    case 'I':
                        treap.insert(op.x);
                        break;
                    case 'A':
                        treap.addAll(op.x);
                        break;
                    case 'S':
                        treap.subtractAll(op.x);
                        break;
                    case 'Q':
                        final long result = treap.queryKthMax((int) op.x);
                        out.add(String.valueOf(result));
                        break;
                }
            }

            out.add(String.valueOf(treap.getLeftCount()));
        }

        return out;
    }

    // output: print each result line with newline
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            sb.append(line).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        final List<TestCase> inputs = reader();
        final List<String> results = cal(inputs);
        output(results);
    }

    // fast scanner
    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
