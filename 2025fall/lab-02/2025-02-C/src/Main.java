// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final int result = cal(inputData);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int size = input.nextInt();
        final int rank = input.nextInt();
        assert ((1 <= size) && (size <= 1_000_000));
        assert ((1 <= rank) && (rank <= size));
        final var values = new int[size];
        for (int index = 0; index < size; index++) {
            final int number = input.nextInt();
            assert ((0 <= number) && (number <= 1_000_000));
            values[index] = number;
        }
        return new InputData(values, rank - 1);
    }

    public static int cal(InputData inputData) {
        final var array = inputData.array;
        return selectKth(array, inputData.targetIndex);
    }

    public static void output(int value) {
        System.out.print(value);
        System.out.print('\n');
    }

    private static int selectKth(int[] data, int targetIndex) {
        int left = 0;
        int right = data.length - 1;
        while (true) {
            if (left == right) {
                return data[left];
            }
            int pivotIndex = ThreadLocalRandom.current().nextInt(left, right + 1);
            pivotIndex = partition(data, left, right, pivotIndex);
            if (targetIndex == pivotIndex) {
                return data[targetIndex];
            }
            if (targetIndex < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int partition(int[] data, int left, int right, int pivotIndex) {
        final int pivotValue = data[pivotIndex];
        swap(data, pivotIndex, right);
        int storeIndex = left;
        for (int index = left; index < right; index++) {
            if (data[index] < pivotValue) {
                swap(data, storeIndex, index);
                storeIndex++;
            }
        }
        swap(data, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] data, int firstIndex, int secondIndex) {
        if (firstIndex == secondIndex) {
            return;
        }
        final int temp = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = temp;
    }

    private static final class InputData {
        private final int[] array;
        private final int targetIndex;

        private InputData(int[] array, int targetIndex) {
            this.array = array;
            this.targetIndex = targetIndex;
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
