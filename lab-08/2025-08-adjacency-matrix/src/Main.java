// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    private static final class TestCase {
        final int n;
        final int m;
        final int[][] edges;

        TestCase(int n, int m, int[][] edges) {
            this.n = n;
            this.m = m;
            this.edges = edges;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var input = new Reader();
        final int T = input.nextInt();
        assert ((1 <= T) && (T <= 50));

        final var testCases = new ArrayList<TestCase>(T);
        for (int t = 0; t < T; t++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 500));
            assert ((0 <= m) && (m <= n * n));

            final var edges = new int[m][2];
            for (int i = 0; i < m; i++) {
                edges[i][0] = input.nextInt();
                edges[i][1] = input.nextInt();
                assert ((1 <= edges[i][0]) && (edges[i][0] <= n));
                assert ((1 <= edges[i][1]) && (edges[i][1] <= n));
            }
            testCases.add(new TestCase(n, m, edges));
        }
        return testCases;
    }

    public static List<int[][]> cal(List<TestCase> testCases) {
        final var results = new ArrayList<int[][]>(testCases.size());
        for (final var tc : testCases) {
            final var matrix = new int[tc.n][tc.n];
            for (final var edge : tc.edges) {
                final int x = edge[0] - 1;
                final int y = edge[1] - 1;
                matrix[x][y] = 1;
            }
            results.add(matrix);
        }
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var testCases = reader();
        final var results = cal(testCases);
        output(results);
    }

    public static void output(List<int[][]> results) {
        final var sb = new StringBuilder();
        for (final var matrix : results) {
            final int n = matrix.length;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j > 0) {
                        sb.append(' ');
                    }
                    sb.append(matrix[i][j]);
                }
                sb.append('\n');
            }
        }
        System.out.print(sb);
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
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

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

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
