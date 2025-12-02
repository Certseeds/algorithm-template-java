// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {
    private static final int MIN_T = 1;
    private static final int MAX_T = 100;
    private static final int MIN_N = 1;
    private static final int MAX_N = 1_000;
    private static final int MIN_L = 1;
    private static final int MAX_L = 100_000;

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var result = cal(inputData);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var fastReader = new Reader();
        final int cases = fastReader.nextInt();
        assert ((MIN_T <= cases) && (cases <= MAX_T));
        final var items = new CaseData[cases];
        for (int t = 0; t < cases; ++t) {
            final int n = fastReader.nextInt();
            assert ((MIN_N <= n) && (n <= MAX_N));
            final var lengths = new int[n];
            for (int i = 0; i < n; ++i) {
                final int len = fastReader.nextInt();
                assert ((MIN_L <= len) && (len <= MAX_L));
                lengths[i] = len;
            }
            items[t] = new CaseData(n, lengths);
        }
        return new InputData(cases, items);
    }

    public static String cal(InputData data) {
        final var builder = new StringBuilder();
        for (int t = 0; t < data.cases; ++t) {
            final var caseData = data.items[t];
            final long totalCost = solveCase(caseData);
            builder.append(totalCost);
            if (t < data.cases - 1) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }

    private static long solveCase(CaseData data) {
        if (data.n <= 1) {
            return 0;
        }
        final var pq = new PriorityQueue<Node>();
        for (int len : data.lengths) {
            pq.add(new Node(len));
        }

        long totalCost = 0;
        while (pq.size() > 1) {
            final Node left = pq.poll();
            final Node right = pq.poll();

            final long mergedWeight = left.weight + right.weight;
            totalCost += mergedWeight;

            final Node parent = new Node(mergedWeight);
            parent.left = left;
            parent.right = right;

            pq.add(parent);
        }
        return totalCost;
    }

    public static void output(String result) {
        System.out.print(result);
        System.out.print('\n');
    }

    private static final class InputData {
        private final int cases;
        private final CaseData[] items;

        private InputData(int cases, CaseData[] items) {
            this.cases = cases;
            this.items = items;
        }
    }

    private static final class CaseData {
        private final int n;
        private final int[] lengths;

        private CaseData(int n, int[] lengths) {
            this.n = n;
            this.lengths = lengths;
        }
    }

    private static final class Node implements Comparable<Node> {
        private final long weight;
        private Node left;
        private Node right;

        private Node(long weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.weight, other.weight);
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
