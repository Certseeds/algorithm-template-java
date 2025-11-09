// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    private static final int MOD = 1000000007;

    // Helper class for Matrix operations
    public static final class Matrix {
        final int[][] data;
        final int m;

        Matrix(int m) {
            this.m = m;
            this.data = new int[m][m];
        }

        static Matrix add(Matrix a, Matrix b) {
            int m = a.m;
            Matrix result = new Matrix(m);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    result.data[i][j] = (a.data[i][j] + b.data[i][j]) % MOD;
                }
            }
            return result;
        }

        static Matrix subtract(Matrix a, Matrix b) {
            int m = a.m;
            Matrix result = new Matrix(m);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    result.data[i][j] = (a.data[i][j] - b.data[i][j] + MOD) % MOD;
                }
            }
            return result;
        }

        static Matrix multiply(Matrix a, Matrix b) {
            int m = a.m;
            Matrix result = new Matrix(m);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    long sum = 0;
                    for (int k = 0; k < m; k++) {
                        sum = (sum + (long) a.data[i][k] * b.data[k][j]) % MOD;
                    }
                    result.data[i][j] = (int) sum;
                }
            }
            return result;
        }
    }

    // Using a static final class for JDK 11 compatibility
    public static final class TestCase {
        final List<Matrix> matrices;
        final String expression;

        public TestCase(List<Matrix> matrices, String expression) {
            this.matrices = matrices;
            this.expression = expression;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testcases = Integer.parseInt(in.nextLine());
        final List<TestCase> cases = new ArrayList<>(testcases);
        for (int t = 0; t < testcases; t++) {
            final String[] nm = in.nextLine().split(" ");
            final int n = Integer.parseInt(nm[0]);
            final int m = Integer.parseInt(nm[1]);
            final List<Matrix> matrices = new ArrayList<>(n);
            for (int k = 0; k < n; k++) {
                final var matrix = new Matrix(m);
                for (int i = 0; i < m; i++) {
                    final var row = in.nextLine().split(" ");
                    for (int j = 0; j < m; j++) {
                        matrix.data[i][j] = Integer.parseInt(row[j]);
                    }
                }
                matrices.add(matrix);
            }
            final var expression = in.nextLine();
            cases.add(new TestCase(matrices, expression));
        }
        return cases;
    }

    public static List<Matrix> cal(List<TestCase> inputs) {
        final List<Matrix> results = new ArrayList<>();
        for (final var tc : inputs) {
            results.add(evaluateExpression(tc.expression, tc.matrices));
        }
        return results;
    }

    private static Matrix evaluateExpression(String expression, List<Matrix> matrices) {
        final Deque<Matrix> values = new ArrayDeque<>();
        final Deque<Character> ops = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                final var sb = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                int matrixIndex = Integer.parseInt(sb.toString()) - 1;
                values.push(matrices.get(matrixIndex));
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else if (c == '+' || c == '-' || c == '*') {
                while (!ops.isEmpty() && hasPrecedence(c, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*') || (op2 != '+' && op2 != '-');
    }

    private static Matrix applyOp(char op, Matrix b, Matrix a) {
        switch (op) {
            case '+':
                return Matrix.add(a, b);
            case '-':
                return Matrix.subtract(a, b);
            case '*':
                return Matrix.multiply(a, b);
        }
        return null;
    }

    public static void output(List<Matrix> decides) {
        for (final Matrix matrix : decides) {
            for (int i = 0; i < matrix.m; i++) {
                for (int j = 0; j < matrix.m; j++) {
                    System.out.print(matrix.data[i][j] + (j == matrix.m - 1 ? "" : " "));
                }
                System.out.print('\n');
            } // 不需要多余空行
        }
    }

    public static void main(String[] args) throws IOException {
        output(cal(reader()));
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
