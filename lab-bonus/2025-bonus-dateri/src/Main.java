// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        final int[] index;
        final int[] queries;

        TestCase(int[] index, int[] queries) {
            this.index = index;
            this.queries = queries;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var input = new Reader();
        final int t = input.nextInt();
        assert ((1 <= t) && (t <= 12));

        final var cases = new ArrayList<TestCase>(t);
        for (int tc = 0; tc < t; tc++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 200000));

            final var index = new int[n];
            for (int i = 0; i < n; i++) {
                index[i] = input.nextInt();
                assert ((1 <= index[i]) && (index[i] <= 1_000_000_000));
            }

            final int q = input.nextInt();
            assert ((1 <= q) && (q <= 200000));

            final var queries = new int[q];
            for (int i = 0; i < q; i++) {
                queries[i] = input.nextInt();
                assert ((1 <= queries[i]) && (queries[i] <= n));
            }

            cases.add(new TestCase(index, queries));
        }
        return cases;
    }

    public static List<int[]> cal(List<TestCase> cases) {
        final var results = new ArrayList<int[]>(cases.size());

        for (final var tc : cases) {
            final int n = tc.index.length;
            final var index = tc.index;

            // Precompute next greater element for each position
            // Using monotonic stack (decreasing from bottom to top)
            final var nextGreater = new int[n];
            final var stack = new ArrayDeque<Integer>(); // stores indices

            for (int i = n - 1; i >= 0; i--) {
                // Pop elements <= index[i]
                while (!stack.isEmpty() && index[stack.peek()] <= index[i]) {
                    stack.pop();
                }
                nextGreater[i] = stack.isEmpty() ? -1 : stack.peek() - i;
                stack.push(i);
            }

            final var answers = new int[tc.queries.length];
            for (int i = 0; i < tc.queries.length; i++) {
                answers[i] = nextGreater[tc.queries[i] - 1];
            }
            results.add(answers);
        }

        return results;
    }

    public static void main(String[] args) throws IOException {
        final var cases = reader();
        final var results = cal(cases);
        output(results);
    }

    public static void output(List<int[]> results) {
        final var sb = new StringBuilder();
        for (final var answers : results) {
            for (final int ans : answers) {
                sb.append(ans);
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
