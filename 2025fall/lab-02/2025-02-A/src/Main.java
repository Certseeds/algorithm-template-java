// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var mergedResults = cal(inputData);
        output(mergedResults);
    }

    public static List<TestCase> reader() throws IOException {
        final var input = new Reader();
        final int testCaseCount = input.nextInt();
        assert ((1 <= testCaseCount) && (testCaseCount <= 10));
        final var cases = new ArrayList<TestCase>(testCaseCount);
        for (int caseIndex = 0; caseIndex < testCaseCount; caseIndex++) {
            final int firstSize = input.nextInt();
            final int secondSize = input.nextInt();
            assert ((1 <= firstSize) && (firstSize <= 100_000));
            assert ((1 <= secondSize) && (secondSize <= 100_000));
            final var first = new int[firstSize];
            for (int i = 0; i < firstSize; i++) {
                final int value = input.nextInt();
                assert ((0 <= value) && (value <= 1000_000_000));
                first[i] = value;
            }
            final var second = new int[secondSize];
            for (int i = 0; i < secondSize; i++) {
                final int value = input.nextInt();
                assert ((0 <= value) && (value <= 1000_000_000));
                second[i] = value;
            }
            cases.add(new TestCase(first, second));
        }
        return cases;
    }

    public static List<int[]> cal(List<TestCase> inputData) {
        final var results = new ArrayList<int[]>(inputData.size());
        for (final var testCase : inputData) {
            results.add(mergeArrays(testCase.first, testCase.second));
        }
        return results;
    }

    public static void output(List<int[]> results) {
        final var lineBuilder = new StringBuilder();
        for (final var merged : results) {
            lineBuilder.setLength(0);
            for (int index = 0; index < merged.length; index++) {
                if (index > 0) {
                    lineBuilder.append(' ');
                }
                lineBuilder.append(merged[index]);
            }
            System.out.print(lineBuilder);
            System.out.print('\n');
        }
    }

    private static int[] mergeArrays(int[] first, int[] second) {
        final var merged = new int[first.length + second.length];
        int firstIndex = 0;
        int secondIndex = 0;
        int mergeIndex = 0;
        while ((firstIndex < first.length) && (secondIndex < second.length)) {
            if (first[firstIndex] <= second[secondIndex]) {
                merged[mergeIndex++] = first[firstIndex++];
            } else {
                merged[mergeIndex++] = second[secondIndex++];
            }
        }
        while (firstIndex < first.length) {
            merged[mergeIndex++] = first[firstIndex++];
        }
        while (secondIndex < second.length) {
            merged[mergeIndex++] = second[secondIndex++];
        }
        return merged;
    }

    private static final class TestCase {
        private final int[] first;
        private final int[] second;

        private TestCase(int[] first, int[] second) {
            this.first = first;
            this.second = second;
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
