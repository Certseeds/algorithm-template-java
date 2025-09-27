// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        final int n;
        final int[] arrivalOrder;

        public TestCase(int n, int[] arrivalOrder) {
            this.n = n;
            this.arrivalOrder = arrivalOrder;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int t = 0; t < testCases; t++) {
            final int n = in.nextInt();
            final int[] arrivalOrder = new int[n];
            for (int i = 0; i < n; i++) {
                arrivalOrder[i] = in.nextInt();
            }
            cases.add(new TestCase(n, arrivalOrder));
        }
        return cases;
    }

    public static List<int[]> cal(List<TestCase> inputs) {
        final List<int[]> results = new ArrayList<>();
        for (final var tc : inputs) {
            final Deque<Integer> stack = new ArrayDeque<>();
            final int[] output = new int[tc.n];
            int outputIndex = 0;
            int nextExpectedCard = 1;

            for (int card : tc.arrivalOrder) {
                stack.push(card);
                while (!stack.isEmpty() && stack.peek() == nextExpectedCard) {
                    output[outputIndex++] = stack.pop();
                    nextExpectedCard++;
                }
            }

            while (!stack.isEmpty()) {
                output[outputIndex++] = stack.pop();
            }
            results.add(output);
        }
        return results;
    }

    public static void output(List<int[]> results) {
        final var sb = new StringBuilder();
        for (final int[] result : results) {
            for (int i = 0; i < result.length; i++) {
                sb.append(result[i]);
                if (i < result.length - 1) {
                    sb.append(" ");
                }
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        output(cal(reader()));
    }

    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

