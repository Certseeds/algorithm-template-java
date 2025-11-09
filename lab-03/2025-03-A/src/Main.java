// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final var result = cal(data);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int firstSize = input.nextInt();
        final int secondSize = input.nextInt();
        assert ((0 < firstSize) && (firstSize < 1_000_000));
        assert ((0 < secondSize) && (secondSize < 1_000_000));
        final long[] firstCoefficients = new long[firstSize];
        final int[] firstExponents = new int[firstSize];
        for (int index = 0; index < firstSize; index++) {
            final long coefficient = input.nextLong();
            final int exponent = input.nextInt();
            assert ((-1_000_000_000L <= coefficient) && (coefficient <= 1_000_000_000L));
            assert ((-1_000_000_000 <= exponent) && (exponent <= 1_000_000_000));
            if (index > 0) {
                assert (firstExponents[index - 1] >= exponent);
            }
            firstCoefficients[index] = coefficient;
            firstExponents[index] = exponent;
        }
        final long[] secondCoefficients = new long[secondSize];
        final int[] secondExponents = new int[secondSize];
        for (int index = 0; index < secondSize; index++) {
            final long coefficient = input.nextLong();
            final int exponent = input.nextInt();
            assert ((-1_000_000_000L <= coefficient) && (coefficient <= 1_000_000_000L));
            assert ((-1_000_000_000 <= exponent) && (exponent <= 1_000_000_000));
            if (index > 0) {
                assert (secondExponents[index - 1] >= exponent);
            }
            secondCoefficients[index] = coefficient;
            secondExponents[index] = exponent;
        }
        return new InputData(firstCoefficients, firstExponents, secondCoefficients, secondExponents);
    }

    public static ResultData cal(InputData data) {
        final var firstCoefficients = data.firstCoefficients;
        final var firstExponents = data.firstExponents;
        final var secondCoefficients = data.secondCoefficients;
        final var secondExponents = data.secondExponents;
        final int firstSize = firstCoefficients.length;
        final int secondSize = secondCoefficients.length;
        final long[] mergedCoefficients = new long[firstSize + secondSize];
        final int[] mergedExponents = new int[firstSize + secondSize];
        int firstIndex = 0;
        int secondIndex = 0;
        int mergedSize = 0;
        while ((firstIndex < firstSize) && (secondIndex < secondSize)) {
            final int exponentFirst = firstExponents[firstIndex];
            final int exponentSecond = secondExponents[secondIndex];
            if (exponentFirst == exponentSecond) {
                final long coefficient = firstCoefficients[firstIndex] + secondCoefficients[secondIndex];
                if (coefficient != 0L) {
                    mergedCoefficients[mergedSize] = coefficient;
                    mergedExponents[mergedSize] = exponentFirst;
                    mergedSize++;
                }
                firstIndex++;
                secondIndex++;
            } else if (exponentFirst > exponentSecond) {
                mergedSize = appendIfNonZero(firstCoefficients[firstIndex], exponentFirst, mergedCoefficients, mergedExponents, mergedSize);
                firstIndex++;
            } else {
                mergedSize = appendIfNonZero(secondCoefficients[secondIndex], exponentSecond, mergedCoefficients, mergedExponents, mergedSize);
                secondIndex++;
            }
        }
        while (firstIndex < firstSize) {
            mergedSize = appendIfNonZero(firstCoefficients[firstIndex], firstExponents[firstIndex], mergedCoefficients, mergedExponents, mergedSize);
            firstIndex++;
        }
        while (secondIndex < secondSize) {
            mergedSize = appendIfNonZero(secondCoefficients[secondIndex], secondExponents[secondIndex], mergedCoefficients, mergedExponents, mergedSize);
            secondIndex++;
        }
        final long[] resultCoefficients = Arrays.copyOf(mergedCoefficients, mergedSize);
        final int[] resultExponents = Arrays.copyOf(mergedExponents, mergedSize);
        return new ResultData(resultCoefficients, resultExponents);
    }

    private static int appendIfNonZero(long coefficient, int exponent, long[] coefficients, int[] exponents, int size) {
        if (coefficient == 0L) {
            return size;
        }
        coefficients[size] = coefficient;
        exponents[size] = exponent;
        return size + 1;
    }

    public static void output(ResultData result) {
        final var builder = new StringBuilder();
        builder.append(result.coefficients.length).append('\n');
        for (int index = 0; index < result.coefficients.length; index++) {
            builder.append(result.coefficients[index]).append(' ').append(result.exponents[index]).append('\n');
        }
        System.out.print(builder.toString());
    }

    public static final class InputData {
        private final long[] firstCoefficients;
        private final int[] firstExponents;
        private final long[] secondCoefficients;
        private final int[] secondExponents;

        private InputData(long[] firstCoefficients, int[] firstExponents, long[] secondCoefficients, int[] secondExponents) {
            this.firstCoefficients = firstCoefficients;
            this.firstExponents = firstExponents;
            this.secondCoefficients = secondCoefficients;
            this.secondExponents = secondExponents;
        }
    }

    public static final class ResultData {
        private final long[] coefficients;
        private final int[] exponents;

        private ResultData(long[] coefficients, int[] exponents) {
            this.coefficients = coefficients;
            this.exponents = exponents;
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
