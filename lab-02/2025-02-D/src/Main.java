// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var result = cal(inputData);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int size = input.nextInt();
        assert ((3 <= size) && (size <= 1_000_000));
        final var heights = new int[size];
        for (int index = 0; index < size; index++) {
            final int value = input.nextInt();
            assert ((0 <= value) && (value <= 1_000_000_000));
            heights[index] = value;
        }
        return new InputData(heights);
    }

    public static Result cal(InputData inputData) {
        final var original = inputData.heights;
        final int size = original.length;
        final var sorted = original.clone();
        Arrays.sort(sorted);
        final int quota = size / 3;
        final int threshold = sorted[quota];
        int smallCount = 0;
        while ((smallCount < size) && (sorted[smallCount] < threshold)) {
            smallCount++;
        }
        final var arrangement = new int[size];
        Arrays.fill(arrangement, -1);
        for (int index = 0; index < smallCount; index++) {
            final int position = index * 3;
            arrangement[position] = sorted[index];
        }
        int pointer = smallCount;
        for (int position = 0; position < size; position++) {
            if (arrangement[position] == -1) {
                arrangement[position] = sorted[pointer++];
            }
        }
        return new Result(threshold, arrangement);
    }

    public static void output(Result result) {
        System.out.print(result.threshold);
        System.out.print('\n');
        final var builder = new StringBuilder();
        for (int index = 0; index < result.arrangement.length; index++) {
            if (index > 0) {
                builder.append(' ');
            }
            builder.append(result.arrangement[index]);
        }
        System.out.print(builder.toString());
        System.out.print('\n');
    }

    private static final class InputData {
        private final int[] heights;

        private InputData(int[] heights) {
            this.heights = heights;
        }
    }

    private static final class Result {
        private final int threshold;
        private final int[] arrangement;

        private Result(int threshold, int[] arrangement) {
            this.threshold = threshold;
            this.arrangement = arrangement;
        }
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader bufferedReader;
        private StringTokenizer tokenizer;

        private Reader() {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        private String next() {
            try {
                while ((tokenizer == null) || !tokenizer.hasMoreElements()) {
                    final String line = bufferedReader.readLine();
                    if (line == null) {
                        return null;
                    }
                    tokenizer = new StringTokenizer(line);
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            final var token = next();
            if (token == null) {
                throw new IllegalStateException("Unexpected end of input");
            }
            return Integer.parseInt(token);
        }
    }
}
