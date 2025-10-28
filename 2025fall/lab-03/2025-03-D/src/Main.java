// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var resultData = cal(inputData);
        output(resultData);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int testCaseCount = input.nextInt();
        assert ((1 <= testCaseCount) && (testCaseCount <= 10));
        final var tests = new SingleTest[testCaseCount];
        for (int index = 0; index < testCaseCount; index++) {
            final int dayCount = input.nextInt();
            assert ((1 <= dayCount) && (dayCount <= 300_000));
            final var values = new int[dayCount];
            for (int day = 0; day < dayCount; day++) {
                final int value = input.nextInt();
                assert ((0 <= value) && (value <= 300_000));
                values[day] = value;
            }
            tests[index] = new SingleTest(dayCount, values);
        }
        return new InputData(tests);
    }

    public static ResultData cal(InputData data) {
        final var tests = data.tests;
        final int testCaseCount = tests.length;
        final var medians = new int[testCaseCount][];
        for (int index = 0; index < testCaseCount; index++) {
            final var currentTest = tests[index];
            final int dayCount = currentTest.dayCount;
            final var values = currentTest.values;
            final var lower = new PriorityQueue<Integer>(Collections.reverseOrder());
            final var upper = new PriorityQueue<Integer>();
            final var currentMedians = new int[(dayCount + 1) / 2];
            int medianIndex = 0;
            for (int day = 0; day < dayCount; day++) {
                final int value = values[day];
                if (lower.isEmpty() || (value <= lower.peek())) {
                    lower.add(value);
                } else {
                    upper.add(value);
                }
                if (lower.size() < upper.size()) {
                    lower.add(upper.poll());
                }
                if (lower.size() > (upper.size() + 1)) {
                    upper.add(lower.poll());
                }
                if ((day & 1) == 0) {
                    currentMedians[medianIndex] = lower.peek();
                    medianIndex++;
                }
            }
            medians[index] = currentMedians;
        }
        return new ResultData(medians);
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        final var medians = result.medians;
        for (int index = 0; index < medians.length; index++) {
            final var caseMedians = medians[index];
            for (int medianIndex = 0; medianIndex < caseMedians.length; medianIndex++) {
                if (medianIndex > 0) {
                    builder.append(' ');
                }
                builder.append(caseMedians[medianIndex]);
            }
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

    public static final class InputData {
        private final SingleTest[] tests;

        private InputData(SingleTest[] tests) {
            this.tests = tests;
        }
    }

    public static final class SingleTest {
        private final int dayCount;
        private final int[] values;

        private SingleTest(int dayCount, int[] values) {
            this.dayCount = dayCount;
            this.values = values;
        }
    }

    public static final class ResultData {
        private final int[][] medians;

        private ResultData(int[][] medians) {
            this.medians = medians;
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
