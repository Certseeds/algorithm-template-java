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
        final long result = cal(inputData);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int size = input.nextInt();
        assert ((1 <= size) && (size <= 5000_000));
        final var numbers = new int[size];
        for (int index = 0; index < size; index++) {
            final int value = input.nextInt();
            assert ((0 <= value) && (value <= Integer.MAX_VALUE));
            numbers[index] = value;
        }
        return new InputData(numbers);
    }

    public static long cal(InputData inputData) {
        final var numbers = inputData.values;
        final int size = numbers.length;
        if ((size & 1) == 1) {
            final int medianIndex = size >>> 1;
            final int medianValue = selectKth(numbers, medianIndex);
            return 2L * medianValue;
        }
        final int upperIndex = size >>> 1;
        final int upperValue = selectKth(numbers, upperIndex);
        if (upperIndex == 0) {
            return 2L * upperValue;
        }
        int countLess = 0;
        int maxLess = Integer.MIN_VALUE;
        for (final int value : numbers) {
            if (value < upperValue) {
                countLess++;
                if (value > maxLess) {
                    maxLess = value;
                }
            }
        }
        if (countLess >= upperIndex) {
            return ((long) maxLess) + ((long) upperValue);
        }
        return 2L * upperValue;
    }

    public static void output(long value) {
        System.out.print(value);
        System.out.print('\n');
    }

    // Quickselect keeps expected linear time to locate order statistics.
    private static int selectKth(int[] data, int targetIndex) {
        int left = 0;
        int right = data.length - 1;
        final var random = ThreadLocalRandom.current();
        while (true) {
            if (left == right) {
                return data[left];
            }
            final int pivotIndex = random.nextInt(left, right + 1);
            final long window = partition(data, left, right, pivotIndex);
            final int low = (int) (window >>> 32);
            final int high = (int) (window & 0xFFFFFFFFL);
            if (targetIndex < low) {
                right = low - 1;
                continue;
            }
            if (targetIndex > high) {
                left = high + 1;
                continue;
            }
            return data[targetIndex];
        }
    }

    private static long partition(int[] data, int left, int right, int pivotIndex) {
        final int pivotValue = data[pivotIndex];
        swap(data, left, pivotIndex);
        int lt = left;
        int gt = right;
        int index = left;
        while (index <= gt) {
            final int value = data[index];
            if (value < pivotValue) {
                swap(data, lt, index);
                lt++;
                index++;
                continue;
            }
            if (value > pivotValue) {
                swap(data, index, gt);
                gt--;
                continue;
            }
            index++;
        }
        return (((long) lt) << 32) | (gt & 0xFFFFFFFFL);
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
        private final int[] values;

        private InputData(int[] values) {
            this.values = values;
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
