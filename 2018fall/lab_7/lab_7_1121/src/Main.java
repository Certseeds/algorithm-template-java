// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] left;
        public final int[] right;

        public TestCase(int n, int[] left, int[] right) {
            this.n = n;
            this.left = left;
            this.right = right;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 14));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 150000));
            final int[] left = new int[n + 1];
            final int[] right = new int[n + 1];
             for (int i = 1; i <= n; i++) {
                 final int x = in.nextInt();
                 final int y = in.nextInt();
                 left[i] = x;
                 right[i] = y;
                assert ((x == 0) || ((1 <= x) && (x <= n)));
                assert ((y == 0) || ((1 <= y) && (y <= n)));
             }
            tests.add(new TestCase(n, left, right));
        }
        return tests;
    }

    // cal: check completeness for each test case
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(isComplete(tc) ? "Yes" : "No");
        }
        return out;
    }

    private static boolean isComplete(final TestCase tc) {
        final int n = tc.n;
        final int[] left = tc.left;
        final int[] right = tc.right;
        // find root (node that is not any child's index)
        final boolean[] isChild = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            if (left[i] != 0) isChild[left[i]] = true;
            if (right[i] != 0) isChild[right[i]] = true;
        }
        int root = 1;
        for (int i = 1; i <= n; i++) if (!isChild[i]) { root = i; break; }
        // BFS using int queue
        final int[] q = new int[n];
        int head = 0, tail = 0;
        q[tail++] = root;
        boolean seenNullChild = false;
        while (head < tail) {
            final int u = q[head++];
            final int l = left[u];
            final int r = right[u];
            if (l == 0) {
                seenNullChild = true;
            } else {
                if (seenNullChild) return false;
                q[tail++] = l;
            }
            if (r == 0) {
                seenNullChild = true;
            } else {
                if (seenNullChild) return false;
                q[tail++] = r;
            }
        }
        return true;
    }

    // output: print each line with newline
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            sb.append(line);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) {
        output(cal(reader()));
    }

    // fast scanner
    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
