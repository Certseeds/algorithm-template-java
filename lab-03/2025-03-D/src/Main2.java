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
        final int testCount = input.nextInt();
        assert ((1 <= testCount) && (testCount <= 10));
        final CaseData[] cases = new CaseData[testCount];
        for (int index = 0; index < testCount; index++) {
            final int dayCount = input.nextInt();
            assert ((1 <= dayCount) && (dayCount <= 300_000));
            final int[] values = new int[dayCount];
            for (int valueIndex = 0; valueIndex < dayCount; valueIndex++) {
                final int value = input.nextInt();
                assert ((0 <= value) && (value <= 300_000));
                values[valueIndex] = value;
            }
            cases[index] = new CaseData(values);
        }
        return new InputData(cases);
    }

    public static ResultData cal(InputData data) {
        final int[][] medians = new int[data.cases.length][];
        for (int caseIndex = 0; caseIndex < data.cases.length; caseIndex++) {
            final var caseData = data.cases[caseIndex];
            final var treap = new Treap();
            final int[] answers = new int[(caseData.values.length + 1) / 2];
            int answerIndex = 0;
            int size = 0;
            for (final int value : caseData.values) {
                treap.insert(value);
                size++;
                if ((size & 1) == 1) {
                    answers[answerIndex] = treap.getKth((size + 1) / 2);
                    answerIndex++;
                }
            }
            medians[caseIndex] = answers;
        }
        return new ResultData(medians);
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        for (int[] medians : result.medians) {
            for (int index = 0; index < medians.length; index++) {
                builder.append(medians[index]);
                if (index + 1 < medians.length) {
                    builder.append(' ');
                }
            }
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

    public static final class InputData {
        private final CaseData[] cases;

        private InputData(CaseData[] cases) {
            this.cases = cases;
        }
    }

    public static final class CaseData {
        private final int[] values;

        private CaseData(int[] values) {
            this.values = values;
        }
    }

    public static final class ResultData {
        private final int[][] medians;

        private ResultData(int[][] medians) {
            this.medians = medians;
        }
    }

    private static final class Treap {
        private static final Random RNG = new Random(1L);
        private Node root;

        private void insert(int value) {
            root = insert(root, value);
        }

        private int getKth(int index) {
            return getKth(root, index);
        }

        private Node insert(Node node, int value) {
            if (node == null) {
                return new Node(value, RNG.nextInt());
            }
            if (value == node.value) {
                node.count++;
            } else if (value < node.value) {
                node.left = insert(node.left, value);
                if (node.left.priority < node.priority) {
                    node = rotateRight(node);
                }
            } else {
                node.right = insert(node.right, value);
                if (node.right.priority < node.priority) {
                    node = rotateLeft(node);
                }
            }
            update(node);
            return node;
        }

        private int getKth(Node node, int index) {
            final int leftSize = size(node.left);
            if (index <= leftSize) {
                return getKth(node.left, index);
            }
            if (index <= leftSize + node.count) {
                return node.value;
            }
            return getKth(node.right, index - leftSize - node.count);
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

        private void update(Node node) {
            node.size = node.count + size(node.left) + size(node.right);
        }

        private int size(Node node) {
            return node == null ? 0 : node.size;
        }

        private static final class Node {
            private final int value;
            private final int priority;
            private int count;
            private int size;
            private Node left;
            private Node right;

            private Node(int value, int priority) {
                this.value = value;
                this.priority = priority;
                this.count = 1;
                this.size = 1;
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
