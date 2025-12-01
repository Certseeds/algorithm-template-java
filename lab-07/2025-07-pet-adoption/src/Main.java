// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public final class Main {
    private Main() {/**/}

    private static final class InputData {
        final int n;
        final int[] type;
        final long[] value;

        InputData(int n, int[] type, long[] value) {
            this.n = n;
            this.type = type;
            this.value = value;
        }
    }

    public static InputData reader() throws IOException {
        final var in = new Reader();
        final int n = in.nextInt();
        assert ((1 <= n) && (n <= 80000));
        final int[] type = new int[n];
        final long[] value = new long[n];
        for (int i = 0; i < n; i++) {
            type[i] = in.nextInt();
            value[i] = in.nextLong();
            assert ((type[i] == 0) || (type[i] == 1));
            assert ((0 < value[i]) && (value[i] < (1L << 31)));
        }
        return new InputData(n, type, value);
    }

    public static long cal(InputData data) {
        final var treap = new Treap();
        long totalDissatisfaction = 0L;
        // state: 0 = empty, 1 = pets in treap, 2 = adopters in treap
        int state = 0;

        for (int i = 0; i < data.n; i++) {
            final int t = data.type[i];
            final long v = data.value[i];

            if (state == 0) {
                // treap is empty, just add
                treap.insert(v);
                state = (t == 0) ? 1 : 2;
            } else if ((state == 1 && t == 0) || (state == 2 && t == 1)) {
                // same type, just add to treap
                treap.insert(v);
            } else {
                // opposite type, find closest and remove
                final long closest = treap.findClosest(v);
                totalDissatisfaction += Math.abs(v - closest);
                treap.remove(closest);
                if (treap.isEmpty()) {
                    state = 0;
                }
            }
        }
        return totalDissatisfaction;
    }

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final long result = cal(data);
        output(result);
    }

    public static void output(long number) {
        System.out.print(number);
        System.out.print('\n');
    }

    // Treap implementation for BST operations
    private static final class Treap {
        private static final Random rand = new Random(42);
        private Node root;

        private static final class Node {
            long key;
            int priority;
            Node left, right;

            Node(long key) {
                this.key = key;
                this.priority = rand.nextInt();
            }
        }

        boolean isEmpty() {
            return root == null;
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
                // found the node to remove
                if (node.left == null) {
                    return node.right;
                }
                if (node.right == null) {
                    return node.left;
                }
                // rotate down and remove
                if (node.left.priority > node.right.priority) {
                    node = rotateRight(node);
                    node.right = remove(node.right, key);
                } else {
                    node = rotateLeft(node);
                    node.left = remove(node.left, key);
                }
            }
            return node;
        }

        long findClosest(long target) {
            Long floor = null;
            Long ceil = null;
            Node cur = root;
            while (cur != null) {
                if (cur.key == target) {
                    return target;
                } else if (cur.key < target) {
                    floor = cur.key;
                    cur = cur.right;
                } else {
                    ceil = cur.key;
                    cur = cur.left;
                }
            }
            // choose the one with minimum difference, tie-break by smaller value
            if (floor == null) {
                return ceil;
            }
            if (ceil == null) {
                return floor;
            }
            final long diffFloor = target - floor;
            final long diffCeil = ceil - target;
            if (diffFloor < diffCeil) {
                return floor;
            } else if (diffCeil < diffFloor) {
                return ceil;
            } else {
                // tie: choose smaller value
                return floor;
            }
        }

        private Node rotateRight(Node node) {
            final Node left = node.left;
            node.left = left.right;
            left.right = node;
            return left;
        }

        private Node rotateLeft(Node node) {
            final Node right = node.right;
            node.right = right.left;
            right.left = node;
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
