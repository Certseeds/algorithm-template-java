// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var results = cal(input);
        output(results);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int testCount = input.nextInt();
        assert ((1 <= testCount) && (testCount <= 5));
        final var sizes = new int[testCount];
        final var sequences = new int[testCount][];
        for (int t = 0; t < testCount; t++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 300000));
            sizes[t] = n;
            final var values = new int[n];
            final var seen = new boolean[n + 1];
            for (int i = 0; i < n; i++) {
                final int value = input.nextInt();
                assert ((1 <= value) && (value <= n));
                assert (!seen[value]);
                seen[value] = true;
                values[i] = value;
            }
            sequences[t] = values;
        }
        return new InputData(testCount, sizes, sequences);
    }

    public static int[][] cal(InputData data) {
        final var results = new int[data.testCount][];
        for (int t = 0; t < data.testCount; t++) {
            results[t] = solveCase(data.sizes[t], data.sequences[t]);
        }
        return results;
    }

    public static void output(int[][] results) {
        final var builder = new StringBuilder();
        for (int t = 0; t < results.length; t++) {
            final var line = results[t];
            for (int i = 0; i < line.length; i++) {
                if (i > 0) {
                    builder.append(' ');
                }
                builder.append(line[i]);
            }
            builder.append('\n');
        }
        System.out.print(builder.toString());
    }

    private static int[] solveCase(int n, int[] order) {
        final var minSuffix = new int[n + 1];
        minSuffix[n] = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            minSuffix[i] = Math.min(order[i], minSuffix[i + 1]);
        }
        final var stack = new int[n];
        var size = 0;
        final var answer = new int[n];
        var answerSize = 0;
        for (int i = 0; i < n; i++) {
            stack[size] = order[i];
            size++;
            // Pop while the top cannot be beaten by any future smaller card.
            while ((size > 0) && (stack[size - 1] <= minSuffix[i + 1])) {
                answer[answerSize] = stack[size - 1];
                answerSize++;
                size--;
            }
        }
        while (size > 0) {
            answer[answerSize] = stack[size - 1];
            answerSize++;
            size--;
        }
        assert (answerSize == n);
        return answer;
    }

    private static final class InputData {
        private final int testCount;
        private final int[] sizes;
        private final int[][] sequences;

        private InputData(int testCount, int[] sizes, int[][] sequences) {
            this.testCount = testCount;
            this.sizes = sizes;
            this.sequences = sequences;
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

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

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
