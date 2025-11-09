// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public final class Main2 {

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final var result = cal(data);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int length = input.nextInt();
        assert ((2 <= length) && (length <= 2_000_000));
        final int[] values = new int[length];
        for (int index = 0; index < length; index++) {
            final int value = input.nextInt();
            assert ((1 <= value) && (value <= 1_000_000_000));
            values[index] = value;
        }
        return new InputData(values);
    }

    public static ResultData cal(InputData data) {
        final int[] values = data.values;
        if (values.length <= 1) {
            return new ResultData(new int[0]);
        }
        final int[] differences = new int[values.length - 1];
        final var treap = new Treap();
        for (int index = values.length - 1; index >= 0; index--) {
            final int current = values[index];
            if (index < values.length - 1) {
                final Treap.Node lower = treap.floor(current);
                final Treap.Node higher = treap.ceiling(current);
                int best = Integer.MAX_VALUE;
                if (lower != null) {
                    best = Math.min(best, Math.abs(current - lower.value));
                }
                if (higher != null) {
                    best = Math.min(best, Math.abs(higher.value - current));
                }
                differences[index] = best;
            }
            treap.insert(current);
        }
        return new ResultData(differences);
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        for (int index = 0; index < result.differences.length; index++) {
            builder.append(result.differences[index]);
            if (index + 1 < result.differences.length) {
                builder.append(' ');
            }
        }
        if (result.differences.length > 0) {
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

    public static final class InputData {
        private final int[] values;

        private InputData(int[] values) {
            this.values = values;
        }
    }

    public static final class ResultData {
        private final int[] differences;

        private ResultData(int[] differences) {
            this.differences = differences;
        }
    }

    private static final class Treap {
        private static final Random RANDOM = new Random(2025);
        private Node root;

        private void insert(int value) {
            root = insert(root, value);
        }

        private Node insert(Node node, int value) {
            if (node == null) {
                return new Node(value, RANDOM.nextInt());
            }
            if (value <= node.value) {
                node.left = insert(node.left, value);
                if (node.left.priority > node.priority) {
                    node = rotateRight(node);
                }
            } else {
                node.right = insert(node.right, value);
                if (node.right.priority > node.priority) {
                    node = rotateLeft(node);
                }
            }
            return node;
        }

        private Node floor(int value) {
            Node current = root;
            Node candidate = null;
            while (current != null) {
                if (value >= current.value) {
                    candidate = current;
                    current = current.right;
                } else {
                    current = current.left;
                }
            }
            return candidate;
        }

        private Node ceiling(int value) {
            Node current = root;
            Node candidate = null;
            while (current != null) {
                if (value <= current.value) {
                    candidate = current;
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return candidate;
        }

        private Node rotateRight(Node node) {
            final Node pivot = node.left;
            node.left = pivot.right;
            pivot.right = node;
            return pivot;
        }

        private Node rotateLeft(Node node) {
            final Node pivot = node.right;
            node.right = pivot.left;
            pivot.left = node;
            return pivot;
        }

        private static final class Node {
            private final int value;
            private final int priority;
            private Node left;
            private Node right;

            private Node(int value, int priority) {
                this.value = value;
                this.priority = priority;
            }
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
        }

        String next() {
            while ((st == null) || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException exception) {
                    exception.printStackTrace();
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
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return str;
        }
    }
}
