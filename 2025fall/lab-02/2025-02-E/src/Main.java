// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {
    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var results = cal(inputData);
        output(results);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int testCaseCount = input.nextInt();
        assert ((1 <= testCaseCount) && (testCaseCount <= 10));
        final var cases = new InputCase[testCaseCount];
        for (int caseIndex = 0; caseIndex < testCaseCount; caseIndex++) {
            final int size = input.nextInt();
            assert ((1 <= size) && (size <= 100000));
            final var array = new int[size];
            for (int index = 0; index < size; index++) {
                final int value = input.nextInt();
                array[index] = value;
            }
            cases[caseIndex] = new InputCase(array);
        }
        return new InputData(cases);
    }

    public static long[] cal(InputData inputData) {
        final var cases = inputData.cases;
        final var results = new long[cases.length];
        final var temp = new int[100000];
        for (int index = 0; index < cases.length; index++) {
            final var numbers = cases[index].numbers;
            results[index] = countInversions(numbers, temp, 0, numbers.length);
        }
        return results;
    }

    public static void output(long[] results) {
        for (final var result : results) {
            System.out.print(result);
            System.out.print('\n');
        }
    }

    private static long countInversions(int[] data, int[] buffer, int left, int right) {
        final int length = right - left;
        if (length <= 1) {
            return 0L;
        }
        final int middle = left + (length >>> 1);
        final long leftCount = countInversions(data, buffer, left, middle);
        final long rightCount = countInversions(data, buffer, middle, right);
        long crossCount = 0L;
        int i = left;
        int j = middle;
        int k = left;
        while ((i < middle) && (j < right)) {
            if (data[i] <= data[j]) {
                buffer[k++] = data[i++];
            } else {
                buffer[k++] = data[j++];
                crossCount += middle - i;
            }
        }
        while (i < middle) {
            buffer[k++] = data[i++];
        }
        while (j < right) {
            buffer[k++] = data[j++];
        }
        for (int index = left; index < right; index++) {
            data[index] = buffer[index];
        }
        return leftCount + rightCount + crossCount;
    }

    private static final class InputData {
        private final InputCase[] cases;

        private InputData(InputCase[] cases) {
            this.cases = cases;
        }
    }

    private static final class InputCase {
        private final int[] numbers;

        private InputCase(int[] numbers) {
            this.numbers = numbers;
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
