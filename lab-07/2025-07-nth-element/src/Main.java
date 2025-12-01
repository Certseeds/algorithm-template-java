// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public final class Main {
    private Main() {/**/}

    private static final class InputData {
        final int m;
        final int k;
        final long[] a;
        final int[] n;

        InputData(int m, int k, long[] a, int[] n) {
            this.m = m;
            this.k = k;
            this.a = a;
            this.n = n;
        }
    }

    public static InputData reader() throws IOException {
        final var in = new Reader();
        final int m = in.nextInt();
        final int k = in.nextInt();
        assert ((1 <= m) && (m <= 100_000));
        assert ((1 <= k) && (k <= m));
        final long[] a = new long[m];
        for (int i = 0; i < m; i++) {
            a[i] = in.nextLong();
        }
        final int queryCount = m - k + 1;
        final int[] n = new int[queryCount];
        for (int i = 0; i < queryCount; i++) {
            n[i] = in.nextInt();
            assert ((1 <= n[i]) && (n[i] <= k));
        }
        return new InputData(m, k, a, n);
    }

    public static long[] cal(InputData data) {
        final int m = data.m;
        final int k = data.k;
        final long[] a = data.a;
        final int[] n = data.n;
        final int queryCount = m - k + 1;
        final long[] results = new long[queryCount];

        final var treap = new Treap();

        // initialize first window
        for (int i = 0; i < k; i++) {
            treap.insert(a[i]);
        }
        results[0] = treap.kth(n[0]);

        // slide window
        for (int i = 1; i < queryCount; i++) {
            treap.remove(a[i - 1]);
            treap.insert(a[i + k - 1]);
            results[i] = treap.kth(n[i]);
        }

        return results;
    }

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final long[] results = cal(data);
        output(results);
    }

    public static void output(long[] results) {
        for (final long r : results) {
            System.out.print(r);
            System.out.print('\n');
        }
    }

    // Treap with size for kth element query
    private static final class Treap {
        private static final Random rand = new Random(42);
        private Node root;

        private static final class Node {
            long key;
            int priority;
            int size;
            Node left, right;

            Node(long key) {
                this.key = key;
                this.priority = rand.nextInt();
                this.size = 1;
            }
        }

        private int size(Node node) {
            return node == null ? 0 : node.size;
        }

        private void update(Node node) {
            if (node != null) {
                node.size = 1 + size(node.left) + size(node.right);
            }
        }

        void insert(long key) {
            root = insert(root, key);
        }

        private Node insert(Node node, long key) {
            if (node == null) {
                return new Node(key);
            }
            if (key < node.key) {
                node.left = insert(node.left, key);
                if (node.left.priority > node.priority) {
                    node = rotateRight(node);
                }
            } else {
                node.right = insert(node.right, key);
                if (node.right.priority > node.priority) {
                    node = rotateLeft(node);
                }
            }
            update(node);
            return node;
        }

        void remove(long key) {
            root = remove(root, key);
        }

        private Node remove(Node node, long key) {
            if (node == null) {
                return null;
            }
            if (key < node.key) {
                node.left = remove(node.left, key);
            } else if (key > node.key) {
                node.right = remove(node.right, key);
            } else {
                if (node.left == null) {
                    return node.right;
                }
                if (node.right == null) {
                    return node.left;
                }
                if (node.left.priority > node.right.priority) {
                    node = rotateRight(node);
                    node.right = remove(node.right, key);
                } else {
                    node = rotateLeft(node);
                    node.left = remove(node.left, key);
                }
            }
            update(node);
            return node;
        }

        long kth(int k) {
            return kth(root, k);
        }

        private long kth(Node node, int k) {
            final int leftSize = size(node.left);
            if (k <= leftSize) {
                return kth(node.left, k);
            } else if (k == leftSize + 1) {
                return node.key;
            } else {
                return kth(node.right, k - leftSize - 1);
            }
        }

        private Node rotateRight(Node node) {
            final Node left = node.left;
            node.left = left.right;
            left.right = node;
            update(node);
            update(left);
            return left;
        }

        private Node rotateLeft(Node node) {
            final Node right = node.right;
            node.right = right.left;
            right.left = node;
            update(node);
            update(right);
            return right;
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
